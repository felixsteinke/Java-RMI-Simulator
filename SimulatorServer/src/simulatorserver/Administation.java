/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorserver;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import simulator.interfaces.Server;
import simulator.interfaces.Client;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulator.data.container.Player;
import simulator.data.container.RaceTrack;
import simulator.data.container.Turn;

/**
 *
 * @author Felix
 *
 * TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!! some improvements to leave game how to do
 * the start of a game needs to get implemented send Turn and the refresh
 * DataPlayerBase needs to get implemented client.receiveError needs to get
 * added, for negativ Feedback on the client
 */
public class Administation {

    private static HashMap<String, Game> games = new HashMap<>();
    private static ArrayList<RaceTrack> raceTracks = new ArrayList <>();

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Administation instanceAdministation;

    //weil alles static ist, ist das eigetnlich nicht nötig (glaube ich)
    public synchronized static Administation getInstance() {                           //Object kann einmal erzeugt werden
        if (instanceAdministation == null) {
            instanceAdministation = new Administation();
        }
        return instanceAdministation;
    }

    public Administation() {
        this.raceTracks = readFile();
        System.out.println("Readed file.");
        this.raceTracks.stream().forEach(x -> System.out.println(x.getName() + x.dataToString()));
    }

    //prototyp, needs to get implemented last
    public static void sendTurn(Server source, Turn data) {
        //blockt wenn player keinen turn machen darf, sonst wird der turn verarbeitet und dann die playerdatabase refresht, dann an alle gesendet
        for (Game game : games.values()) {
            game.executorService.submit(() -> {
                for (Player player : game.getPlayerData().playerlist) {
                    if (player.getConnectedServer() == source) {
                        if (player.getCanDoMove() == true) {
                            player.getTurns().add(data);
                            game.turnCollection.add(data);
                            try {
                                game.refreshPlayerDatabase();
                            } catch (RemoteException ex) {
                                Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            });
        }
    }

    //shuld be done !!!Player needs: Name, client, color!!!!
    public static void joinGame(Server source, Client client, Player player, String gameName, String code) {
        //Methode wird auf Basis des jeweiligen GameThreads ausgeführt
        games.get(gameName).executorService.submit(() -> {
            Game game = games.get(gameName);
            try {
                if (game.getCode().equalsIgnoreCase(code)) {
                    if (game.playerData.playerlist.size() < game.getGameSize()) {
                        
                        game.playerData.playerlist.add(player);
                        
                        System.out.println("Player " + player.getUsername() + " joined " + game.getName());
                        player.getConnectedClient().receiveError("999: You (" + player.getUsername() + ") joined " + game.getName());
                        
                        shareRaceTrack(game);
                        
                        if (game.playerData.playerlist.size() == game.getGameSize()) {
                            
                            game.setGameStarted(true);
                            game.startGame();
                            System.out.println("Game: " + game.getName() + " started!");
                            game.refreshPlayerDatabase();
                        }
                    } else {
                        //Feedback für volles Game
                        System.out.println("Game was full.");
                        player.getConnectedClient().receiveError("Code was wrong - not connected!");

                    }
                } else {
                    //Feedback für falschen Code
                    System.out.println("Code was wrong");

                    player.getConnectedClient().receiveError("Code was wrong - not connected!");

                }
            } catch (RemoteException ex) {
                Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    //!!!never tested!!! maybe needs some improvements
    public static void leaveGame(Server source, Player player, String gameName) {
        games.get(gameName).executorService.submit(() -> {
            System.out.println("Player wants to leave " + gameName + ": " + player.username);
            try {
                for (Player playerVar : games.get(gameName).playerData.playerlist) {
                    if (playerVar.getConnectedServer() == source) {
                        playerVar.setAlive(false);
                        games.get(gameName).playerData.playerlist.remove(playerVar);
                        System.out.println("Player " + playerVar.username + " the Game " + gameName);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    //should be done !!!Cleaner nerver tested!!!
    public static void createGame(String gameName, int count, String code) {
        executorService.submit(() -> {
            //Clean old Games
            HashMap<String, Game> gamesCopy = new HashMap<>(games);
            for (Game game : gamesCopy.values()) {
                if (game.playerData.playerlist.size() == 0) {
                    System.out.println("Removed Game: " + game.getName());
                    games.remove(game.getName()).executorService.shutdown();
                    continue;
                }
                int activCount = 0;
                for (int i = 0; i < game.playerData.playerlist.size(); i++) {
                    if (game.playerData.playerlist.get(i).isAlive() == false) {
                        activCount++;
                    }
                    if (activCount == game.playerData.playerlist.size()) {
                        games.remove(game.getName()).executorService.shutdown();
                    }
                }
            }
            //Add new Game
            games.put(gameName, new Game(gameName, count, code));
            System.out.println("Game created: " + gameName);
        });
    }

    //should be done
    public static void sendString(Server source, String message) {
        //sucht game mit dem anfragenden Client über das Source server object, das den Client mit dem Player verbindet
        for (Game game : games.values()) {
            game.executorService.submit(() -> {
                for (Player player : game.playerData.playerlist) {
                    if (player.getConnectedServer() == source) {
                        System.out.println("Found Game for sendStringRequest: " + game.getName());
                        //Sendet jedem Player aus dem Game, außer dem SourcePlayer die Message
                        for (Player playerVar : game.playerData.playerlist) {
                            if (playerVar.getConnectedServer() != source) {
                                try {
                                    System.out.println("SendString to: " + playerVar.username);
                                    playerVar.getConnectedClient().receiveString(message);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return;
                    }
                }
            });
        }
    }

    static void showRaceTrackList(Server source) {
        for (Game game : games.values()) {
            game.executorService.submit(() -> {
                //findet das Game mit dem Spieler
                for (Player player : game.getPlayerData().playerlist) {
                    if (player.getConnectedServer() == source) {
                        try {
                            player.getConnectedClient().receiveError("222:" + createRaceTrackListString());
                        } catch (RemoteException ex) {
                            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return;
                    }
                }
            });
        }
    }

    //should be done
    static void sendRaceTrackDecision(Server source, String data) {
        for (Game game : games.values()) {
            game.executorService.submit(() -> {
                //findet das Game mit dem Spieler
                for (Player player : game.getPlayerData().playerlist) {
                    if (player.getConnectedServer() == source) {
                        //findet den richtige Track und setzt ihn dem Game ein
                        raceTracks.stream().forEach(x -> {
                            if (x.getName().equalsIgnoreCase(data)) {
                                System.out.println(game.getName() + " got a RaceTrack: " + x.getName());
                                game.setRaceTrack(x);
                            }
                        });
                        break;
                    }
                }
                try {
                    //!!!MISSING!!! wird eigentlich zu oft aufgerufen
                    shareRaceTrack(game);
                } catch (RemoteException ex) {
                    Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    //should be done
    static void sendRaceTrack(Server source, RaceTrack data) {
        //thread für mehr kapazität auf dem Server
        //executorService.submit(() -> {
            raceTracks.add(data);
            System.out.println("Server: added RaceTrack " + data.getName());
            writeArray(raceTracks);
        //});
    }

    //should be done
    public static void sendRaceTrackDelete(Server source, String data) {
        executorService.submit(() -> {
            raceTracks.stream().forEach(x -> {
                if (x.getName().equalsIgnoreCase(data)) {
                    System.out.println("RaceTrack " + x.getName() + " deleted!");
                    raceTracks.remove(x);
                }
            });
            writeArray(raceTracks);
        });
    }

    //should be done
    private static void shareRaceTrack(Game game) throws RemoteException {
        if (game.getRaceTrack() != null) {
            for (Player player : game.getPlayerData().playerlist) {
                    player.getConnectedClient().receiveRacetrack(game.getRaceTrack());
                
            }
        }
    }

    //should be done
    private static String createRaceTrackListString() {
        StringBuilder listString = new StringBuilder();
        executorService.submit(() -> {
            raceTracks.stream().forEach(x -> {
                listString.append(x.getName());
                listString.append("\n");
            });
        });
        return listString.toString();
    }

    private static void writeArray(ArrayList<RaceTrack> list) {
        ObjectOutputStream oos = null;
        try {
            //==============================================================================
            oos = new ObjectOutputStream(new FileOutputStream("racetracks.ser"));
            for (RaceTrack person : list) {
                oos.writeObject(person);
            }
            list.stream().forEach(x -> System.out.println(x.dataToString()));
            //==============================================================================
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static ArrayList<RaceTrack> readFile() {
        ArrayList<RaceTrack> tracks = new ArrayList<RaceTrack>();
        ObjectInputStream ois = null;
        try {
            //==============================================================================
            ois = new ObjectInputStream(new FileInputStream("racetracks.ser"));
            RaceTrack input;
            while ((input = (RaceTrack) ois.readObject()) != null) {
                tracks.add(input);
            }
            //==============================================================================
        } catch (EOFException e) {
            //==============================================================================
            tracks.stream().forEach(x -> System.out.println(x.dataToString()));
            return tracks;
            //==============================================================================
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}

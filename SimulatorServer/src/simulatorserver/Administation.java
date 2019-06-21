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
 * DataPlayerBase needs to get implemented client.receiveFeedback needs to get
 * added, for negativ Feedback on the client
 */
public class Administation {

    private static HashMap<String, Game> games = new HashMap<>();
    private static ArrayList<RaceTrack> raceTracks = new ArrayList<>();

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Administation instanceAdministation;

    public synchronized static Administation getInstance() {                           //Object kann einmal erzeugt werden
        if (instanceAdministation == null) {
            instanceAdministation = new Administation();
        }
        return instanceAdministation;
    }

    public Administation() {
        this.raceTracks = readFile();
    }

    //MISSING!!!!
    public static void sendTurn(Server source, Turn data) {
        for (Game game : games.values()) {
            game.executorService.submit(() -> {
                for (Player player : game.getPlayerData().playerlist) {
                    if (player.getConnectedServer() == source) {
                        //if (player.getCanDoMove() == true) {
                        player.getTurns().add(data);
                        game.turnCollection.add(data);
                        try {
                            game.refreshPlayerDatabase();
                        } catch (RemoteException ex) {
                            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        // }
                    }
                }
            });
        }
    }

    //shuld be done !!!Player needs: Name, client, color!!!!
    public static void joinGame(Server source, Client client, Player player, String gameName, String code) {
        //Methode wird auf Basis des jeweiligen GameThreads ausgeführt
        if(games.get(gameName) == null){
            executorService.submit(() -> {
                try {
                    player.getConnectedClient().receiveFeedback("111:Game does not exist.");
                } catch (RemoteException ex) {
                    Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return;
        }
        games.get(gameName).executorService.submit(() -> {
            Game game = games.get(gameName);
            try {
                if (game.getCode().equalsIgnoreCase(code)) {
                    if (game.playerData.playerlist.size() < game.getGameSize()) {

                        game.playerData.playerlist.add(player);

                        System.out.println("Server: Player " + player.getName() + " joined " + game.getName());

                        //!!!!MISSING!!! warum geht das nicht?
                        //player.getConnectedClient().receiveFeedback("999:You (" + player.getUsername() + ") joined " + game.getName());
                        shareRaceTrack(game);

                        if ((game.playerData.playerlist.size() == game.getGameSize()) && !game.isGameStarted() && game.getRaceTrack() != null) {
                            game.setGameStarted(true);
                            game.startGame();
                            System.out.println("Server: Game: " + game.getName() + " started!");
                            game.refreshPlayerDatabase();
                        }

                    } else {
                        //Feedback für volles Game
                        System.out.println("Server: Game was full.");
                        player.getConnectedClient().receiveFeedback("111:Game was full - not connected!");
                    }
                } else {
                    //Feedback für falschen Code
                    System.out.println("Server: Code was wrong");
                    player.getConnectedClient().receiveFeedback("111:Code was wrong - not connected!");

                }
            } catch (RemoteException ex) {
                Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    //FINISHED! - should test on serverObj and not on name
    public static void leaveGame(Server source, Player player, String gameName) {
        games.get(gameName).executorService.submit(() -> {
            for (Player playerVar : games.get(gameName).playerData.playerlist) {
                //!!!!!MISSING!!!! eigentlich mit source auf Server abgleichen aber das geht irgendwie nicht
                if (playerVar.getName().equalsIgnoreCase(player.getName())) {
                    playerVar.setAlive(false);
                    games.get(gameName).playerData.playerlist.remove(playerVar);
                    System.out.println("Server: Player " + playerVar.name + " left the Game " + gameName);
                    break;
                }
            }
        });

    }

    //FINISHED! - clean old games not tested
    public static void createGame(String gameName, int count, String code) {
        executorService.submit(() -> {
            //Clean old Games
            HashMap<String, Game> gamesCopy = new HashMap<>(games);
            for (Game game : gamesCopy.values()) {
                if (game.playerData.playerlist.size() == 0) {
                    System.out.println("Server: Removed Game: " + game.getName());
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
            System.out.println("Server: Game created: " + gameName);
        });
    }

    //FINISHED!
    public static void sendString(Server source, String message) {
        for (Game game : games.values()) {
            game.executorService.submit(() -> {
                for (Player player : game.playerData.playerlist) {
                    if (player.getConnectedServer() == source) {
                        for (Player playerVar : game.playerData.playerlist) {
                            if (playerVar.getConnectedServer() != source) {
                                try {
                                    System.out.println("Server: sendString to: " + playerVar.getName());
                                    playerVar.getConnectedClient().receiveString(message);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    }
                }
            });
        }
    }

    //FINISHED!
    static void showRaceTrackList(Server source) {
        for (Game game : games.values()) {
            game.executorService.submit(() -> {
                //findet das Game mit dem Spieler
                for (Player player : game.getPlayerData().playerlist) {
                    if (player.getConnectedServer() == source) {
                        try {
                            player.getConnectedClient().receiveFeedback("888:" + createRaceTrackListString());
                            System.out.println("Server: Sended Player: " + player.getName() + " RaceTrackList.");
                        } catch (RemoteException ex) {
                            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                }
            });
        }
    }

    //FINISHED!
    static void setRaceTrackForGame(Server source, String data) {
        for (Game game : games.values()) {
            game.executorService.submit(() -> {
                //findet das Game mit dem Spieler
                for (Player player : game.getPlayerData().playerlist) {

                    if (player.getConnectedServer() == source) {
                        //findet den richtige Track und setzt ihn dem Game ein

                        raceTracks.stream().forEach(raceTrack -> {
                            if (raceTrack.getName().equalsIgnoreCase(data)) {
                                System.out.println(game.getName() + " got a RaceTrack: " + raceTrack.getName());
                                game.setRaceTrack(raceTrack);
                            }
                        });

                        if ((game.playerData.playerlist.size() == game.getGameSize()) && !game.isGameStarted() && game.getRaceTrack() != null) {
                            try {
                                shareRaceTrack(game);
                                game.setGameStarted(true);
                                game.startGame();
                                System.out.println("Server: Game: " + game.getName() + " started!");
                                game.refreshPlayerDatabase();
                                return;
                            } catch (RemoteException ex) {
                                Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            try {
                                player.getConnectedClient().receiveFeedback("999:Game cant get started. (Game not full or wrong RaceTrack Input.");
                            } catch (RemoteException ex) {
                                Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
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

    //FINISHED!
    static void addRaceTrack(Server source, RaceTrack data) {
        executorService.submit(() -> {
            raceTracks.add(data);
            System.out.println("Server: RaceTrack" + data.getName() + " added! \n" + data.toString());
            writeArray(raceTracks);
        });
    }

    //should be done - not tested
    public static void deleteRaceTrack(Server source, String data) {
        executorService.submit(() -> {
            for (RaceTrack raceTrack : raceTracks) {
                if (raceTrack.getName().equalsIgnoreCase(data)) {
                    raceTracks.remove(raceTrack);
                    System.out.println("Server: RaceTrack " + raceTrack.getName() + " deleted!");
                    break;
                }
            }
            writeArray(raceTracks);
        });
    }

    //==============================================================================================
    //==============================================================================================
    //==============================================================================================
    //should be done
    private static void shareRaceTrack(Game game) throws RemoteException {
        if (game.getRaceTrack() != null) {
            for (Player player : game.getPlayerData().playerlist) {
                player.getConnectedClient().receiveRacetrack(game.getRaceTrack());

            }
            System.out.println("Server Method: Shared RaceTrack in Game: " + game.getName());
        }
    }

    //should be done
    private static String createRaceTrackListString() {
        StringBuilder listSB = new StringBuilder();
        for (RaceTrack raceTrack : raceTracks) {
            listSB.append(raceTrack.getName() + "\n");
        }
        System.out.println("Server Method: RaceTrackList created: \n" + listSB.toString());
        return listSB.toString();
    }

    private static void writeArray(ArrayList<RaceTrack> list) {
        System.out.println("RaceTracks on Server-Data:");
        list.stream().forEach(raceTrack -> System.out.println(raceTrack.getName()));
        ObjectOutputStream oos = null;
        try {
            //==============================================================================
            oos = new ObjectOutputStream(new FileOutputStream("racetracks.ser"));
            for (RaceTrack person : list) {
                oos.writeObject(person);
            }
            System.out.println("Server Method: Updated RaceTrack-File.");
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
            //tracks.stream().forEach(x -> System.out.println(x.dataToString()));
            //==============================================================================
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //==============================================================================
                ois.close();
                //tracks.stream().forEach(x -> System.out.println(x.dataToString()));
                System.out.println("Server Method: Updated RaceTracks on the Server.");
                return tracks;
                //==============================================================================
            } catch (IOException ex) {
                Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Server Method: Updating RaceTracks on Server went !!WRONG!!");
        return null;
    }

}

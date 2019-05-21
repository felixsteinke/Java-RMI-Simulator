/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorserver;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import simulator.interfaces.Server;
import simulator.interfaces.Client;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulator.data.container.Player;
import simulator.data.container.RaceTrack;
import simulator.data.container.Turn;

/**
 *
 * @author Felix
 */
public class Administation {

    //got some issues with this map...
    private static HashMap<String, Game> games = new HashMap<>();

    private static ExecutorService executorService;                             //eigentlich nicht nötig, backup plan für thread exceptions
    private static Administation instanceEngine;

    //weil alles static ist, ist das eigetnlich nicht nötig (glaube ich)
    public synchronized static Administation getInstance() {                           //Object kann einmal erzeugt werden
        if (instanceEngine == null) {
            instanceEngine = new Administation();
        }
        return instanceEngine;
    }

    private Administation() {
        executorService = Executors.newSingleThreadExecutor();
    }

    //not implmented
    static void sendRaceTrack(ServerImpl aThis, RaceTrack data) {
        //soll später RaceTrack Objects mit ObjectOutputStream in eine Datei speicher, die Datei soll beim Start des Servers geladen werden
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //not implemented
    static void sendRaceTrackDecision(ServerImpl aThis, String data) {
        //soll den String mit RaceTrack names abgleichen und diesen dann dem game adden und allen Senden
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                            game.refreshPlayerDatabase();
                        }
                    }
                }
                for (Player player : game.playerData.playerlist) {
                    if (player.getConnectedServer() != source) {
                        if (player.getActiv() == true) {
                            try {
                                player.getConnectedClient().receivePlayerDatabase(game.playerData);
                            } catch (RemoteException ex) {
                                Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            });
            //needs to get implemented
            game.refreshPlayerDatabase();
            //send player database
        }
    }

    //player should have a color, a username and a !!connectedClient!!
    public static void joinGame(Server source, Client client, Player player, String gameName, String code) {
        //Methode wird auf Basis des jeweiligen GameThreads ausgeführt
        games.get(gameName).executorService.submit(() -> {
            //vereinfachter aufruf des games
            Game game = games.get(gameName);
            //Zugangscode wird geprüft
            if (game.getCode().equalsIgnoreCase(code)) {
                //Es wird geprüft ob das game schon voll ist
                if (game.playerData.playerlist.size() < game.getGameSize()) {
                    //adde den Spieler zum Spiel
                    game.playerData.playerlist.add(player);
                    if (game.playerData.playerlist.size() == 1) {
                        System.out.println("Send RaceTrackList to " + player.getUsername());
                        try {
                            //!!!!MISSING create real DefaultRaceTrackList!!!!!
                            player.getConnectedClient().receiveRacetracksList("DefaultList");
                        } catch (RemoteException ex) {
                            Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    //Feedback für volles Game
                    System.out.println("Game was full.");
                    try {
                        player.getConnectedClient().receiveError("Code was wrong - not connected!");
                    } catch (RemoteException ex) {
                        Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                //Feedback für falschen Code
                System.out.println("Code was wrong");
                try {
                    player.getConnectedClient().receiveError("Code was wrong - not connected!");
                } catch (RemoteException ex) {
                    Logger.getLogger(Administation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static void leaveGame(Server source, Player player, String gameName) {
        games.get(gameName).executorService.submit(() -> {
            System.out.println("Player wants to leave " + gameName + ": " + player.username);
            try {
                for (Player playerVar : games.get(gameName).playerData.playerlist) {
                    if (playerVar.getConnectedServer() == source) {
                        playerVar.setActiv(false);
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

    //works!! (clean old games not tested)
    public static void createGame(String gameName, int count, String code) {
        //executorService.submit(() -> {
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
                if (game.playerData.playerlist.get(i).getActiv() == false) {
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
        //});
    }

    public static void sendString(Server source, String message) {
        //sucht game mit dem anfragenden Client über das Source server object, das den Client mit dem Player verbindet
        for (Game game : games.values()) {
            game.executorService.submit(() -> {
                for (int i = 0; i < game.playerData.playerlist.size(); i++) {
                    if (game.playerData.playerlist.get(i).getConnectedServer() == source) {
                        System.out.println("Found Game for sendStringRequest: " + game.getName());
                        //Sendet jedem Player aus dem Game, außer dem SourcePlayer die Message
                        for (Player player : game.playerData.playerlist) {
                            if (player.getConnectedServer() != source) {
                                try {
                                    System.out.println("SendString to: " + player.username);
                                    //Methode für den Client
                                    player.getConnectedClient().receiveString(message);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    //eigentlich unnötig
    public static Administation getInstanceEngine() {
        return instanceEngine;
    }

}

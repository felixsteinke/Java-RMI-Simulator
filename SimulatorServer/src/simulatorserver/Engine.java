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
public class Engine {

    //got some issues with this map...
    private static HashMap<String, Game> games = new HashMap<>();

    private static ExecutorService executorService;
    private static Engine instanceEngine;

    public synchronized static Engine getInstance() {                           //Object kann einmal erzeugt werden
        if (instanceEngine == null) {
            instanceEngine = new Engine();
        }
        return instanceEngine;
    }

    static void sendRaceTrack(ServerImpl aThis, RaceTrack data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void sendRaceTrackDecision(ServerImpl aThis, String data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Engine() {
        executorService = Executors.newSingleThreadExecutor();
    }

    //prototyp
    public static void sendTurn(Server source, Turn data) {
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
                                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            });
            //needs to get implemented
            game.refreshPlayerDatabase();
        }
    }
    
    //player should have a color, a username and a !!connectedClient!!
    public static void joinGame(Server source, Client client, Player player, String gameName, String code) {
        games.get(gameName).executorService.submit(() -> {
            if (games.get(gameName).getCode() == code) {
                for (int i = 0; i < games.get(gameName).playerData.playerlist.size(); i++) {
                    if (games.get(gameName).playerData.playerlist.get(i).getUsername() == null) {
                        player.setConnectedServer(source);
                        games.get(gameName).playerData.playerlist.set(i, player);
                        //test the game users start
                        System.out.println("Users in Game " + gameName);
                        for (Player playerVar : games.get(gameName).playerData.playerlist) {
                            System.out.println("User: " + playerVar.username);
                        }
                        //test the game users end
                        try {
                            player.getConnectedClient().receiveRacetracksList("Game: " + games.get(gameName).getName() + " Track: " + games.get(gameName).getRaceTrack().getTrackName());
                            player.getConnectedClient().receiveRacetrack(games.get(gameName).getRaceTrack());
                            System.out.println("Tried to send racetrack and racetracklist");
                        } catch (RemoteException ex) {
                            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (i == games.get(gameName).playerData.playerlist.size() - 1) {
                        System.out.println("Game was full, or something went wrong.");
                    }
                }
            }
        });
    }
    
    public static void leaveGame(Server source, Player player, String gameName) {
        games.get(gameName).executorService.submit(() -> {
            System.out.println("Player wants to leaveGame: " + player.username);
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

    public static void createGame(String gameName, int count, String code, RaceTrack raceTrack) {
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
            games.put(gameName, new Game(gameName, count, code, raceTrack));
            System.out.println("Game created: " + gameName);
        //});
    }

    public static void sendString(Server source, String message) {
        for (Game game : games.values()) {
            game.executorService.submit(() -> {
                for (int i = 0; i < game.playerData.playerlist.size(); i++) {
                    if (game.playerData.playerlist.get(i).getConnectedServer() == source) {
                        System.out.println("Found Game: " + game.getName());
                        for (Player player : game.playerData.playerlist) {
                            if (player.getConnectedServer() != source) {
                                if (player.getActiv() == true) {
                                    try {
                                        System.out.println("SendString to: " + player.username);
                                        player.getConnectedClient().receiveString(message);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public static Engine getInstanceEngine() {
        return instanceEngine;
    }

}

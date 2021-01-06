/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.server;

import simulator.data.container.Player;
import simulator.data.container.PlayerDatabase;
import simulator.data.container.RaceTrack;
import simulator.data.container.Turn;

import java.awt.*;
import java.awt.geom.Line2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felix
 *
 * TODO: !!!!!!!!!!!!!!!!!!!!!!! the refresh game Method needs to get
 * implemented the start of the game needs to get implemented
 */
public class Game {

    private String name;                //beliebig
    private int gameSize;               //theoretisch beliebig, soll aber <=5 sein
    private String code;                //passwort
    private RaceTrack raceTrack;        //soll im nachhinen festgelegt werden
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private Player winner = new Player();
    private int gameState = 1;
    public ArrayList turnCollection;
    public PlayerDatabase playerData;   //spieler müssen vom nutzer hinzugefügt werden
    public ExecutorService executorService;

    //Dieser Konsturktor wird verwendet
    public Game(String name, int gameSize, String code) {
        this.name = name;
        this.gameSize = gameSize;
        this.code = code;
        this.raceTrack = null;
        this.turnCollection = new ArrayList();
        this.playerData = new PlayerDatabase();
        this.executorService = Executors.newFixedThreadPool(gameSize);
    }

    //MISSING!!!!!!!!
    public void refreshPlayerDatabase() throws RemoteException {
        System.out.println("Game: " + this.name + " refreshedPlayerDatabase.");
        if (turnCollection.size() == getActivPlayer()) {
            turnCollection.clear();
            gameState++;
            for (Player player : playerData.playerlist) {
                if (player.isAlive()) {
                    Point oldPoint = player.getPosition();
                    updatePosition(player);
                    if (!checkPointIsValid(player.getPosition())) {
                        player.setAlive(false);
                        System.out.println("Game: " + this.name + " - Player lost: " + player.getName());
                        //<editor-fold defaultstate="collapsed" desc=" Send Lose ">
                        this.executorService.submit(() -> {
                            try {
                                player.getConnectedClient().receiveFeedback("888:You LOST!");
                            } catch (RemoteException ex) {
                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        //</editor-fold>
                        continue;
                    }
                    if (checkCrossedControlLine(oldPoint, player.getPosition())) {
                        System.out.println("Game: " + this.name + " - Player crossed ControlLine: " + player.getName());
                        player.setCrossedControlLine(true);
                    }
                    if (checkCrossedStartLine(oldPoint, player.getPosition()) && player.isCrossedControlLine()) {
                        player.setWonTheGame(true);
                        gameOver = true;
                        System.out.println("Game: " + this.name + " - Player won: " + player.getName());
                        //<editor-fold defaultstate="collapsed" desc=" Send Win ">
                        this.executorService.submit(() -> {
                            try {
                                player.getConnectedClient().receiveFeedback("888: You WON!");
                            } catch (RemoteException ex) {
                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc=" Send Turn Activation ">
                    this.executorService.submit(() -> {
                        try {
                            player.getConnectedClient().receiveFeedback("555:Lets do a turn!");
                        } catch (RemoteException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    //</editor-fold>
                }
            }
            sendDatabaseToAll();
            //<editor-fold defaultstate="collapsed" desc=" Send Game Over ">
            if (gameOver) {
                ArrayList <Player> winnerList = new ArrayList <>();
                for (Player player : playerData.playerlist) {
                    if(player.isWonTheGame()){
                        winnerList.add(player);
                    }
                }
                double winnerSpeed = 0;
                for (Player player : playerData.playerlist){
                    if(player.getTurns().get(player.getTurns().size()-1).getSpeed() > winnerSpeed){
                        winnerSpeed = player.getTurns().get(player.getTurns().size()-1).getSpeed();
                        winner = player;
                    }
                }
                for (Player player : playerData.playerlist) {
                    this.executorService.submit(() -> {
                        try {
                            player.setAlive(false);
                            player.getConnectedClient().receiveFeedback("888: Game is Over - " + winner.getName() + " won the game.");
                        } catch (RemoteException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
                playerData = new PlayerDatabase();
                gameStarted = false;
                gameState = 1;
                return;
            }
            //</editor-fold>
        }
    }
    
    //Spieler Kollision wurde nicht gemacht, durch das Speed attribut könnte man das jedoch leicht regeln, wie die Gewinner Überprüfung
    
    //optional IS WRONG
    /*
    private boolean antiCheatTool(Player player) {
        ArrayList sendedOut = player.getTurns().get(player.getTurns().size() - 1).getOldTurn().turnMoves;
        ArrayList gotIn = player.getTurns().get(player.getTurns().size() - 2).getOldTurn().turnMoves;
        if (sendedOut.equals(gotIn)) {
            return false;
        } else {
            //SHOULD BE TRUE!!!! MISSING!!!!!!! need to overthing this method
            return false;
        }
    }
    */
    public void sendDatabaseToAll() {
        for (Player player : playerData.playerlist) {
            this.executorService.submit(() -> {
                try {
                    player.getConnectedClient().receivePlayerDatabase(playerData);
                } catch (RemoteException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    private void updatePosition(Player player) {
        Turn turn = player.getTurns().get(player.getTurns().size() - 1);
        Point position = new Point(player.getPosition().x + turn.turnVektor.x, player.getPosition().y + turn.turnVektor.y);
        player.setPosition(position);
    }

    private boolean checkPointIsValid(Point position) {
        return raceTrack.getValidPoints().contains(position);
    }

    private boolean checkCrossedControlLine(Point oldPoint, Point newPoint) {
        Line2D line = new Line2D.Double(newPoint, oldPoint);
        Line2D startLine = new Line2D.Double(raceTrack.getCoordControl().get(0), raceTrack.getCoordControl().get(1));
        return startLine.intersectsLine(line);
    }

    private boolean checkCrossedStartLine(Point oldPoint, Point newPoint) {
        Line2D line = new Line2D.Double(newPoint, oldPoint);
        Line2D startLine = new Line2D.Double(raceTrack.getCoordStart().get(0), raceTrack.getCoordStart().get(1));
        return startLine.intersectsLine(line);
    }

    private int getActivPlayer() {
        int count = 0;
        for (Player player : playerData.playerlist) {
            if (player.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public void startGame() {
        System.out.println("Game: " + this.name + " started.");
        for (int i = 0; i < playerData.playerlist.size(); i++) {
            Point startPosition = raceTrack.getStartPoints().get(i);
            playerData.playerlist.get(i).setStartPosition(startPosition);
            playerData.playerlist.get(i).setPosition(startPosition);

            try {
                playerData.playerlist.get(i).getConnectedClient().receiveFeedback("555:Lets Start!");
            } catch (RemoteException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println(playerData.playerlist.get(i).getStartPosition().toString());
        }
        sendDatabaseToAll();
    }

    //<editor-fold defaultstate="collapsed" desc=" Getter & Setter ">
    public RaceTrack getRaceTrack() {
        return raceTrack;
    }

    public void setRaceTrack(RaceTrack raceTrack) {
        this.raceTrack = raceTrack;
    }

    public PlayerDatabase getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerDatabase playerData) {
        this.playerData = playerData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getGameSize() {
        return gameSize;
    }

    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }
    //</editor-fold>

}

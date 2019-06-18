/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorserver;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulator.data.container.Player;
import simulator.data.container.PlayerDatabase;
import simulator.data.container.RaceTrack;

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
            Point player0Position;
            Point player1Position;
            Point player2Position;
            Point player3Position;
            Point player4Position;

            for (int i = 0; i < playerData.playerlist.size(); i++) {
                Player player = playerData.playerlist.get(i);
                Point startPosition = player.getStartPosition();
                int[] moveVektor = new int[2];
                player.getTurns().stream().forEach(turn -> {
                    moveVektor[0] = moveVektor[0] + turn.turnVektor[0];
                    moveVektor[1] = moveVektor[1] + turn.turnVektor[1];
                });
                Point position = new Point (startPosition.x + moveVektor [0], startPosition.y + moveVektor [1]);
                if(!raceTrack.getValidPoints().contains(position)){
                    player.setAlive(false);
                    player.getConnectedClient().receiveFeedback("888:You Lost!");
                }
                switch (i) {
                    case 0:
                        player0Position = position;
                        break;
                    case 1:
                        player1Position = position;
                        break;
                    case 2:
                        player2Position = position;
                        break;
                    case 3:
                        player3Position = position;
                        break;
                    case 4:
                        player4Position = position;
                        break;
                    default:
                        System.out.println("Got more players than expected.");

                }
            }
            sendDatabaseToAll();
            
            for (Player player : playerData.playerlist) {
                if(player.isAlive()){
                    player.getConnectedClient().receiveFeedback("555:Lets do a turn!");
                }
            }
        }

        /*
        !!!!MISSING!!!!
        Durch alle Spieler Spieler gehen und schauen, ob die ArrayList turns.size == gameState ist.
        Wenn das so ist, wird diese methode ausgeführt.

        
        muss player.canDoMove weiter schieben
        muss player.isActiv prüfen, mit neuer Position compare mit ValidPoints von Racetrack
        muss antiCheat prüfen player mit Turn muss OldTurns mit ClientOldTurns prüfen
        bei allen spielern auf send Turn warten, wenn alle ihren Turn gesendet haben, verarbeiten
        bei positionskonflikt, wer mehr speed in seinem turn hatte, gewinnt die position -> der andere muss
        seinen Turn wiederholen
        wer nach zeit x keinen move gemacht hat wird inactiv und hat verloren
         */
    }

    //optional
    private boolean antiCheatTool(Player player) {
        ArrayList sendedOut = player.getTurns().get(player.getTurns().size() - 1).getOldTurn();
        ArrayList gotIn = player.getTurns().get(player.getTurns().size() - 2).getOldTurn();
        if (sendedOut.equals(gotIn)) {
            return false;
        } else {
            //SHOULD BE TRUE!!!! MISSING!!!!!!! need to overthing this method
            return false;
        }
    }

    public void sendDatabaseToAll() {
        for (Player player : playerData.playerlist) {
            try {
                player.getConnectedClient().receivePlayerDatabase(playerData);
            } catch (RemoteException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean checkPointIsValid(Point position){
        return raceTrack.getValidPoints().contains(position);
    }
    
    private boolean checkCrossedControlLine (Point oldPoint, Point newPoint){
        Line2D line = new Line2D.Double(newPoint, oldPoint);
        Line2D startLine = new Line2D.Double(raceTrack.getCoordControl().get(0), raceTrack.getCoordControl().get(1));
        return startLine.intersectsLine(line);
    }
    
    private boolean checkCrossedStartLine(Point oldPoint, Point newPoint){
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

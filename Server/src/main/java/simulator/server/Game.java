package simulator.server;

import lombok.Data;
import simulator.data.Player;
import simulator.data.PlayerDatabase;
import simulator.data.RaceTrack;
import simulator.data.Turn;

import java.awt.*;
import java.awt.geom.Line2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
public class Game {

    private String name;                //beliebig
    private int gameSize;               //theoretisch beliebig, soll aber <=5 sein
    private String code;                //passwort
    public ArrayList<Turn> turnCollection;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private Player winner = new Player();
    private int gameState = 1;
    private RaceTrack raceTrack;        //soll im nachhinein festgelegt werden
    public PlayerDatabase playerData;   //spieler müssen vom nutzer hinzugefügt werden
    public ExecutorService executorService;

    public Game(String name, int gameSize, String code) {
        this.name = name;
        this.gameSize = gameSize;
        this.code = code;
        this.raceTrack = null;
        this.turnCollection = new ArrayList<>();
        this.playerData = new PlayerDatabase();
        this.executorService = Executors.newFixedThreadPool(gameSize);
    }

    public void refreshPlayerDatabase() throws RemoteException {
        System.out.println("Game: " + this.name + " refreshedPlayerDatabase.");
        if (turnCollection.size() == getActivePlayer()) {
            turnCollection.clear();
            gameState++;
            for (Player player : playerData.playerlist) {
                if (player.isAlive()) {
                    Point oldPoint = player.getPosition();
                    updatePosition(player);
                    if (!checkPointIsValid(player.getPosition())) {
                        player.setAlive(false);
                        System.out.println("Game: " + this.name + " - Player lost: " + player.getName());
                        // send lose
                        this.executorService.submit(() -> {
                            try {
                                player.getConnectedClient().receiveFeedback("888:You LOST!");
                            } catch (RemoteException ex) {
                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
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
                        // send win
                        this.executorService.submit(() -> {
                            try {
                                player.getConnectedClient().receiveFeedback("888: You WON!");
                            } catch (RemoteException ex) {
                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                    // send turn activation
                    this.executorService.submit(() -> {
                        try {
                            player.getConnectedClient().receiveFeedback("555:Lets do a turn!");
                        } catch (RemoteException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }
            sendDatabaseToAll();
            // Game over _______________________________________________________________________________________________
            if (gameOver) {
                double winnerSpeed = 0;
                for (Player player : playerData.playerlist) {
                    if (player.getTurns().get(player.getTurns().size() - 1).getSpeed() > winnerSpeed) {
                        winnerSpeed = player.getTurns().get(player.getTurns().size() - 1).getSpeed();
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
            }
        }
    }

    //Spieler Kollision wurde nicht gemacht, durch das Speed attribut könnte man das jedoch leicht regeln, wie die Gewinner Überprüfung

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

    private int getActivePlayer() {
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
}

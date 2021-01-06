package simulator.data;

import simulator.interfaces.Client;
import simulator.interfaces.Server;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    public String name;
    private ArrayList<Turn> turns;
    private Point startPosition;
    private Point position;
    private Client connectedClient;
    private Server connectedServer;
    private int amountOfTurns = 0;
    private Color color;
    private boolean alive;
    private boolean canDoMove = false;
    private boolean wonTheGame = false;
    private boolean crossedControlLine = false;

    public Player() {
        this.name = "DefaultUser";
        this.alive = true;
        this.turns = new ArrayList <Turn>();
        turns.add(new Turn("default"));
        this.color = Color.PINK;
    }
    
    public void controlData(){
        System.out.println("Player Control:");
        System.out.println("Player: " + name);
        System.out.println("Startposition: " + startPosition.toString() + " | Position: " + position.toString());
        System.out.println("Turns: " + turns.size());
        System.out.println("Last Turn: " + turns.get(turns.size()-1).turnVektor.x + "|" + turns.get(turns.size()-1).turnVektor.y);
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Getter & Setter ">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCrossedControlLine() {
        return crossedControlLine;
    }

    public void setCrossedControlLine(boolean crossedControlLine) {
        this.crossedControlLine = crossedControlLine;
    }

    
    
    public ArrayList<Turn> getTurns() {
        return turns;
    }

    public void setTurns(ArrayList<Turn> turns) {
        this.turns = turns;
    }

    public Point getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Point startPosition) {
        this.startPosition = startPosition;
    }

    public int getAmountOfTurns() {
        return amountOfTurns;
    }

    public void setAmountOfTurns(int amountOfTurns) {
        this.amountOfTurns = amountOfTurns;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean activNew) {
        this.alive = activNew;
    } 

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean getCanDoMove() {
        return canDoMove;
    }

    public void setCanDoMove(boolean canDoMove) {
        this.canDoMove = canDoMove;
    }
    
    public Client getConnectedClient() {
        return connectedClient;
    }

    public void setConnectedClient(Client connectedClient) {
        this.connectedClient = connectedClient;
    }

    public Server getConnectedServer() {
        return connectedServer;
    }

    public void setConnectedServer(Server connectedServer) {
        this.connectedServer = connectedServer;
    }

    public boolean isWonTheGame() {
        return wonTheGame;
    }

    public void setWonTheGame(boolean wonTheGame) {
        this.wonTheGame = wonTheGame;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    //</editor-fold>
    
    
}

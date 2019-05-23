/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.data.container;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import simulator.interfaces.Client;
import simulator.interfaces.Server;

/**
 *
 * @author Felix
 */
public class Player implements Serializable {
    
    public String username;
    private ArrayList <Turn> turns;
    private Point startPosition;
    private Point position;
    private Client connectedClient;
    private Server connectedServer;
    private int amountOfTurns = 0;
    private Color color;
    private boolean activ = false;
    private boolean canDoMove = false;
    private boolean wonTheGame = false;

    public Player() {
        this.username = null;
        this.turns = new ArrayList <Turn>();
        this.color = Color.PINK;
    }

    public Player(String username, Client connectedClient, Server connectedServer) {
        this.username = username;
        this.connectedClient = connectedClient;
        this.connectedServer = connectedServer;
    }

    public Player(String username, Client connectedClient, Server connectedServer, Color color) {
        this.username = username;
        this.connectedClient = connectedClient;
        this.connectedServer = connectedServer;
        this.color = color;
    }

    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean getActiv() {
        return activ;
    }

    public void setActiv(boolean activNew) {
        if(activNew == true){
            if(username == null){
                JOptionPane.showMessageDialog(null, "Not ready to be activ, Username empty!");
                return;
            }
        }
        this.activ = activNew;
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
    
    
    
}

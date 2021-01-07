package simulator.data;

import lombok.Data;
import simulator.interfaces.Client;
import simulator.interfaces.Server;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

@Data
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
        this.turns = new ArrayList<Turn>();
        turns.add(new Turn("default"));
        this.color = Color.PINK;
    }

    public void controlData() {
        System.out.println("Player Control:");
        System.out.println("Player: " + name);
        System.out.println("Startposition: " + startPosition.toString() + " | Position: " + position.toString());
        System.out.println("Turns: " + turns.size());
        System.out.println("Last Turn: " + turns.get(turns.size() - 1).turnVektor.x + "|" + turns.get(turns.size() - 1).turnVektor.y);
    }
}

package simulator.data;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerDatabase implements Serializable {

    public ArrayList<Player> playerlist = new ArrayList<Player>();

    //so bekommt man nur die leere playerlist (client player müssen hinzugefügt werden)
    public PlayerDatabase() {
        playerlist = new ArrayList<Player>();
    }
}

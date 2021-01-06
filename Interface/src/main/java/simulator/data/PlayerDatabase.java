/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Felix
 */
public class PlayerDatabase implements Serializable {
    
    public ArrayList <Player> playerlist = new ArrayList <Player> ();
    
    //so bekommt man nur die leere playerlist (client player müssen hinzugefügt werden)
    public PlayerDatabase() {
        playerlist = new ArrayList <Player> ();
    }
    
}

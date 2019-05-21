/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.data.container;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Felix
 */
public class PlayerDatabase implements Serializable {

    private Player player1 = new Player();
    private Player player2 = new Player();
    private Player player3 = new Player();
    private Player player4 = new Player();
    private Player player5 = new Player();
    
    private Color defaultColor1 = Color.BLUE;
    private Color defaultColor2 = Color.GREEN;
    private Color defaultColor3 = Color.CYAN;
    private Color defaultColor4 = Color.PINK;
    private Color defaultColor5 = Color.WHITE;
    
    public ArrayList <Player> playerlist = new ArrayList <Player> ();
    
    public PlayerDatabase() {
        player1.setColor(Color.BLUE);
        player2.setColor(Color.GREEN);
        player3.setColor(Color.cyan);
        player4.setColor(Color.PINK);
        player5.setColor(Color.WHITE);
        
        playerlist.add(player1);
        playerlist.add(player2);
        playerlist.add(player3);
        playerlist.add(player4);
        playerlist.add(player5);
        
    }
    
    public PlayerDatabase(int i) {
        if (i==1){
            player1.setColor(defaultColor1);
            
            playerlist.add(player1);
        }else if(i == 2){
            player1.setColor(defaultColor1);
            player2.setColor(defaultColor2);
            
            playerlist.add(player1);
            playerlist.add(player2);
        }else if (i == 3){
            player1.setColor(defaultColor1);
            player2.setColor(defaultColor2);
            player3.setColor(defaultColor3);
            
            playerlist.add(player1);
            playerlist.add(player2);
            playerlist.add(player3);
        }else if (i == 4){
            player1.setColor(defaultColor1);
            player2.setColor(defaultColor2);
            player3.setColor(defaultColor3);
            player4.setColor(defaultColor4);
            
            playerlist.add(player1);
            playerlist.add(player2);
            playerlist.add(player3);
            playerlist.add(player4);
        }else{
            player1.setColor(defaultColor1);
            player2.setColor(defaultColor2);
            player3.setColor(defaultColor3);
            player4.setColor(defaultColor4);
            player5.setColor(defaultColor5);
        
            playerlist.add(player1);
            playerlist.add(player2);
            playerlist.add(player3);
            playerlist.add(player4);
            playerlist.add(player5);
        }
    }
    
    
    
    
    
}

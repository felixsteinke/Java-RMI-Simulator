/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.data.container;

import java.io.Serializable;
import simulator.data.container.Move;
import java.util.ArrayList;

/**
 *
 * @author Felix
 */
public class Turn implements Serializable {

    private ArrayList <Move> moves;
    
    public Turn(ArrayList <Move> moves) {
        this.moves = moves;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }
    
    
    
   
}

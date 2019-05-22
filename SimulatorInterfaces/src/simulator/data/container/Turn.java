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
 * 
 * TODO: !!!!!!!!!!!!!!!
 * client muss den old turn bekommen und über einen key listener einen move festlegen
 * 
 */
public class Turn implements Serializable {

    private ArrayList <Move> oldTurn;
    private Move move;
    private double speed;
    public ArrayList <Move> moves;
    public int [] turnVektor;
    
    
    public Turn(ArrayList <Move> oldTurn, Move move) {
        this.oldTurn = oldTurn;
        this.move = move;
        moves = new ArrayList (oldTurn);
        moves.add(move);
        turnVektor = new int [2];
        cleanUselessMoves();
        calcTurnVektor();
        calcSpeed();
    }

    private void calcTurnVektor() {
        int x = 0;
        int y = 0;
        for(Move move : moves){
            x = x + move.x;
            y = y + move.y;
        }
        turnVektor [0] = x;
        turnVektor [1] = y;
    }

    private void calcSpeed(){
        speed = Math.sqrt(Math.pow(turnVektor[0], 2)*Math.pow(turnVektor[1], 2));
    }
    
    private void cleanUselessMoves(){
        int count = 0;
        loop:
        while(true){
            Move moveVarOut = moves.get(count);
            //Move 5 is useless
            if(moveVarOut.getKey() == 5){
                moves.remove(moveVarOut);
                count = 0;
                break loop;
            }
            //this moves disable the other one
            for (Move moveVarIn : moves) {
                if((moveVarIn.getKey() == 1 && moveVarOut.getKey()== 9) 
                        || (moveVarIn.getKey() == 2 && moveVarOut.getKey()== 8)
                        || (moveVarIn.getKey() == 3 && moveVarOut.getKey()== 7)
                        || (moveVarIn.getKey() == 4 && moveVarOut.getKey()== 6)){
                    moves.remove(moveVarIn);
                    moves.remove(moveVarOut);
                    count = 0;
                    break loop;
                }
            }
            count++;
            //retuns of the array is completely checked
            if(count >= moves.size()){
                return;
            }
        }
    }

    public ArrayList<Move> getOldTurn() {
        return oldTurn;
    }

    public void setOldTurn(ArrayList<Move> oldTurn) {
        this.oldTurn = oldTurn;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
   
}

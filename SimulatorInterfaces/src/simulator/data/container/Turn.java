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

    private ArrayList <Move> oldMoves;
    private Move move;
    private double speed;
    public ArrayList <Move> moves;
    public int [] turnVektor = new int [2];
    
    
    public Turn(ArrayList <Move> oldMoves, Move move) {
        this.oldMoves = oldMoves;
        this.move = move;
        moves = new ArrayList (oldMoves);
        moves.add(move);
        cleanUselessMoves();
        calcTurnVektor();
        calcSpeed();
    }

    public Turn(String start){
        this.oldMoves = new ArrayList();
        this.move = new Move(5, 0);
        oldMoves.add(move);
        this.moves = new ArrayList();
        moves.add(move);
        this.speed = 0;
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

    public ArrayList<Move> getOldMoves() {
        return oldMoves;
    }

    public void setOldMoves(ArrayList<Move> oldMoves) {
        this.oldMoves = oldMoves;
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

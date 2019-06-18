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
    public ArrayList <Move> newTurn;
    public int [] turnVektor = new int [2];
    
    
    public Turn(ArrayList <Move> oldTurn, Move move) {
        this.oldTurn = oldTurn;
        this.move = move;
        newTurn = new ArrayList (oldTurn);
        newTurn.add(move);
        //cleanUselessMoves();
        calcTurnVektor();
        calcSpeed();
        showData();
    }
    
    public void showData () {
        System.out.println("Turn Vektor: [ " + turnVektor[0] + " , " + turnVektor [1] + " ]" );
        System.out.println("Speed: " + speed);
    }

    public Turn(String start){
        this.oldTurn = new ArrayList();
        this.move = new Move(5, 0);
        oldTurn.add(move);
        this.newTurn = new ArrayList();
        newTurn.add(move);
        this.speed = 0;
    }
    private void calcTurnVektor() {
        int x = 0;
        int y = 0;
        for(Move move : newTurn){
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
            Move moveVarOut = newTurn.get(count);
            //Move 5 is useless
            if(moveVarOut.getKey() == 5){
                newTurn.remove(moveVarOut);
                count = 0;
                break loop;
            }
            //this newTurn disable the other one
            for (Move moveVarIn : newTurn) {
                if((moveVarIn.getKey() == 1 && moveVarOut.getKey()== 9) 
                        || (moveVarIn.getKey() == 2 && moveVarOut.getKey()== 8)
                        || (moveVarIn.getKey() == 3 && moveVarOut.getKey()== 7)
                        || (moveVarIn.getKey() == 4 && moveVarOut.getKey()== 6)){
                    newTurn.remove(moveVarIn);
                    newTurn.remove(moveVarOut);
                    count = 0;
                    break loop;
                }
            }
            count++;
            //retuns of the array is completely checked
            if(count >= newTurn.size()){
                return;
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc=" Getter & Setter ">
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
    //</editor-fold>
   
}

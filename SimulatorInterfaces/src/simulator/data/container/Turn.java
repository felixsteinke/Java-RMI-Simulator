/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.data.container;

import java.awt.Point;
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

    private Turn oldTurn;
    private Move move;
    private double speed;
    public ArrayList <Move> turnMoves;
    public Point turnVektor;
    
    
    public Turn(Turn oldTurn, Move move) {
        this.oldTurn = oldTurn;
        this.move = move;
        this.turnMoves = new ArrayList (this.oldTurn.turnMoves);
        this.turnMoves.add(move);
        //cleanUselessMoves();
        this.turnVektor = calcTurnVektor();
        this.speed = calcSpeed();
        showData();
    }
    
    public void showData () {
        System.out.println("Turn Vektor: [ " + turnVektor.x + " , " + turnVektor.y + " ]" );
        System.out.println("Speed: " + speed);
    }

    public Turn(String start){
        this.move = new Move(5, 0);
        this.turnMoves = new ArrayList();
        turnMoves.add(move);
        this.speed = 0;
        this.turnVektor = new Point (0,0);
    }
    
    private Point calcTurnVektor() {
        int x = this.oldTurn.turnVektor.x + this.move.x;
        int y = this.oldTurn.turnVektor.y + this.move.y;
        return new Point (x,y);
    }

    private double calcSpeed(){
        return Math.sqrt(Math.pow(turnVektor.x, 2) + Math.pow(turnVektor.y, 2));
    }
    
    private void cleanUselessMoves(){
        int count = 0;
        loop:
        while(true){
            Move moveVarOut = turnMoves.get(count);
            //Move 5 is useless
            if(moveVarOut.getKey() == 5){
                turnMoves.remove(moveVarOut);
                count = 0;
                break loop;
            }
            //this turnMoves disable the other one
            for (Move moveVarIn : turnMoves) {
                if((moveVarIn.getKey() == 1 && moveVarOut.getKey()== 9) 
                        || (moveVarIn.getKey() == 2 && moveVarOut.getKey()== 8)
                        || (moveVarIn.getKey() == 3 && moveVarOut.getKey()== 7)
                        || (moveVarIn.getKey() == 4 && moveVarOut.getKey()== 6)){
                    turnMoves.remove(moveVarIn);
                    turnMoves.remove(moveVarOut);
                    count = 0;
                    break loop;
                }
            }
            count++;
            //retuns of the array is completely checked
            if(count >= turnMoves.size()){
                return;
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc=" Getter & Setter ">
    public Turn getOldTurn() {
        return oldTurn;
    }

    public void setOldTurn(Turn oldTurn) {
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

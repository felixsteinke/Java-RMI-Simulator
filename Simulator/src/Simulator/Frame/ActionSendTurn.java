/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import simulator.data.container.Move;
import simulator.data.container.Player;
import simulator.data.container.Turn;

/**
 *
 * @author Felix
 */
public class ActionSendTurn extends AbstractAction{

    private SimulatorFrame frame = SimulatorFrame.getInstance();
    @Override
    public void actionPerformed(ActionEvent e) {
        Player myPlayer = new Player();
        
        for (Player player : frame.playerDatabase.playerlist) {
            if(player.name.equalsIgnoreCase(frame.player.getName())){
                System.out.println("You are player: " + player.getName());
                myPlayer = player;
            }
        }
        
        Turn oldTurn = myPlayer.getTurns().get(myPlayer.getTurns().size() - 1);
        int moveValue = Integer.valueOf(frame.jTextField_Turn.getText());
        Turn turn = new Turn(oldTurn, new Move(moveValue, frame.getRaceTrackToPlay().getDistance()));
        try {
            frame.server.sendTurn(turn);
        } catch (RemoteException ex) {
            Logger.getLogger(ActionSendTurn.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        frame.jButton_Turn.setEnabled(false);
        frame.jLabelTurn.setVisible(false);
    }
    
}

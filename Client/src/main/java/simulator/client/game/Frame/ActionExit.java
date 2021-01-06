/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 *
 * @author Felix
 */
public class ActionExit extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        if(SimulatorFrame.getInstance().connected){
            new ActionDisconnectFromGame().actionPerformed(e);
        }
        System.exit(0);
    }
    
    public void actionPerformed(WindowEvent e) {
        if(SimulatorFrame.getInstance().connected){
            new ActionDisconnectFromGame().actionPerformed(e);
        }
        System.exit(0);
    }
    
}

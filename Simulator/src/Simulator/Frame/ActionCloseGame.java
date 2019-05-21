/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Felix
 */
public class ActionCloseGame extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        new ActionDisconnectFromGame();
        SimulatorFrame.getInstance().setTrackData(null);
        JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "Game closed.");
    }
    
}

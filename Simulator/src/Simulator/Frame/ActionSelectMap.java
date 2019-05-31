/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Felix
 */
public class ActionSelectMap extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String selection = JOptionPane.showInputDialog("Write the Map-Name:");
            SimulatorFrame.getInstance().server.setRaceTrackForGame(selection);
        } catch (RemoteException ex) {
            Logger.getLogger(ActionSelectMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

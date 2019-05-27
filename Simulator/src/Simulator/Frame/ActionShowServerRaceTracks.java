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
public class ActionShowServerRaceTracks extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!SimulatorFrame.getInstance().connected){
            JOptionPane.showMessageDialog(null, "Not connected!");
        }
        try {
            SimulatorFrame.getInstance().server.showRaceTrackList();
        } catch (RemoteException ex) {
            Logger.getLogger(ActionShowServerRaceTracks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

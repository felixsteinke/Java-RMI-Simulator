/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felix
 */
public class ActionShowServerRaceTracks extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!SimulatorFrame.getInstance().connected){
            JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "Not connected!");
            return;
        }
        try {
            SimulatorFrame.getInstance().server.showRaceTrackList();
        } catch (RemoteException ex) {
            Logger.getLogger(ActionShowServerRaceTracks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

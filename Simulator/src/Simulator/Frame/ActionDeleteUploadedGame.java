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
public class ActionDeleteUploadedGame extends AbstractAction {

    private SimulatorFrame frame = SimulatorFrame.getInstance();
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(frame.connected == true){
            String name = JOptionPane.showInputDialog(frame,"Which RaceTrack should be deleted on the Server?");
            try {
                frame.server.deleteRaceTrack(name);
            } catch (RemoteException ex) {
                Logger.getLogger(ActionDeleteUploadedGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Not connected!");
        }
        
    }
    
}

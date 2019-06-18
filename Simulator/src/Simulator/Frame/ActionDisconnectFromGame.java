/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Felix
 */
public class ActionDisconnectFromGame extends AbstractAction {

    private SimulatorFrame frame = SimulatorFrame.getInstance();
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame.connected == false) {
            JOptionPane.showMessageDialog(frame, "Not Connected");
            return;
        }

        EventQueue.invokeLater(() -> {
            try {
                String mesg = frame.player.name + " disconnected from " + frame.gameName;
                frame.server.sendString(mesg);
                frame.connection.leaveGame(frame.server,frame.player,frame.gameName);
                ///!!!!!!!MISSING!!!!!!!! warum ist das nicht exporte
                UnicastRemoteObject.unexportObject(frame.ClientImpl, false);
                frame.setRaceTrackToPlay(null);
                frame.playerDatabase = null;
                frame.connected = false;
                frame.consoleModel.addElement(mesg);
            } catch (RemoteException ex) {
                Logger.getLogger(Simulator.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
}

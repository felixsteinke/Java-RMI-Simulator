/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame.Dialog.Message;

import Simulator.Frame.SimulatorFrame;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Felix
 */
public class ActionSendButton extends AbstractAction {
    
    private SimulatorFrame frame = SimulatorFrame.getInstance();
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame.connected == false) {
            JOptionPane.showMessageDialog(null, "Not Connected");
            return;
        }
        EventQueue.invokeLater(() -> {
            try {
                String mesg = convMessage(frame.chatDialog.getjTextField_InputMesg().getText(), frame.player.name);
                frame.server.sendString(mesg);
                frame.chatModel.addElement(mesg);
            } catch (RemoteException ex) {
                Logger.getLogger(ActionSendButton.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }
    
    private static String convMessage(String origin, String username) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp + " - " + username + " : " + origin;
    }
    
}

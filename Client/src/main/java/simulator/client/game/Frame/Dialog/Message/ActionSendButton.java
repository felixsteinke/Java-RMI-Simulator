/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame.Dialog.Message;

import simulator.client.game.Frame.SimulatorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Felix
 */
public class ActionSendButton extends AbstractAction {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame.connected == false) {
            JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "Not Connected");
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

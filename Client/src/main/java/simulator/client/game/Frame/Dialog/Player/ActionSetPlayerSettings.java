/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame.Dialog.Player;

import simulator.client.game.Frame.SimulatorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Felix
 */
public class ActionSetPlayerSettings extends AbstractAction{

    private final SimulatorFrame frame = SimulatorFrame.getInstance();
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = frame.playerDialog.getjTextField_Username().getText();
        Color color = frame.playerDialog.getjColorChooser_Player().getColor();
        if(username.equalsIgnoreCase("") || username == null || color == null){
            JOptionPane.showMessageDialog(frame.playerDialog, "Invalid selection!");
            return;
        }
        frame.player.setName(username);
        frame.player.setColor(color);
        frame.playerDialog.dispose();
    }
    
}

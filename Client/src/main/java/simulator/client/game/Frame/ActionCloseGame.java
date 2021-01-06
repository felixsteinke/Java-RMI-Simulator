/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Felix
 */
public class ActionCloseGame extends AbstractAction {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame.connected == false) {
            frame.setRaceTrackToPlay(null);
        } else {
            new ActionDisconnectFromGame().actionPerformed(e);
        }
        JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "Game closed.");
    }
    
}
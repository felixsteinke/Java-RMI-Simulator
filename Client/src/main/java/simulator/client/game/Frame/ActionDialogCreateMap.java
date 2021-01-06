/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame;

import Creation.Frame.CreationFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Felix
 *
 */
public class ActionDialogCreateMap extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        String [] args = {""};
        Thread t = new Thread(() -> {
            CreationFrame.main(args);
        });
        t.start();
    }

}

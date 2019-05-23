/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import Creation.Frame.CreationFrame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Felix
 *
 */
public class ActionDialogCreateMap extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        String [] args = {"Start Creation Frame"};
        Thread t = new Thread(() -> {
            CreationFrame.main(args);
        });
        t.start();
    }

}

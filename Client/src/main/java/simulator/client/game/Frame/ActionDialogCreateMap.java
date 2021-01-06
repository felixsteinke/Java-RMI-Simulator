package simulator.client.game.Frame;

import simulator.client.creation.CreationFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActionDialogCreateMap extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] args = {""};
        Thread t = new Thread(() -> {
            CreationFrame.main(args);
        });
        t.start();
    }
}

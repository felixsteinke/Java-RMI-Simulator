package simulator.client.game.Frame;

import simulator.client.game.Frame.Dialog.Game.CreateGameLobby;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActionDialogGame extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {

        Thread t = new Thread(() -> {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(CreateGameLobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    SimulatorFrame.getInstance().gameDialog = new CreateGameLobby(SimulatorFrame.getInstance(), true);
                    SimulatorFrame.getInstance().gameDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            SimulatorFrame.getInstance().gameDialog.dispose();
                        }
                    });
                    SimulatorFrame.getInstance().gameDialog.setVisible(true);
                }
            });
        });
        t.start();
    }
}

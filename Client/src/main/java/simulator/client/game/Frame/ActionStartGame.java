package simulator.client.game.Frame;

import simulator.client.game.Frame.Dialog.Game.CreateGameLobby;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ActionStartGame extends AbstractAction implements PropertyChangeListener {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

    public ActionStartGame() {
        setEnabled(false);
        frame.addPropertyChangeListener(this);
    }

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
                    CreateGameLobby dialog = new CreateGameLobby(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            dialog.dispose();
                        }
                    });
                    dialog.setVisible(true);
                }
            });
        });
        t.start();
    }

    public boolean isEnabled() {
        return frame.getInputFile() != null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setEnabled(frame.getInputFile() != null);
    }
}

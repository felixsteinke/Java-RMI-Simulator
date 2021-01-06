package simulator.client.game.Frame;

import simulator.client.game.Frame.Dialog.Message.MessageDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActionDialogMessenger extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread t = new Thread(() -> {
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MessageDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    SimulatorFrame.getInstance().chatDialog = new MessageDialog(SimulatorFrame.getInstance(), true, SimulatorFrame.getInstance().chatModel);
                    SimulatorFrame.getInstance().chatDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            SimulatorFrame.getInstance().chatDialog.dispose();
                        }
                    });
                    SimulatorFrame.getInstance().chatDialog.setVisible(true);
                }
            });
        });
        t.start();
    }
}

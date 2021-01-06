/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame;

import simulator.client.game.Frame.Dialog.Game.CreateGameLobby;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author 82stfe1bif
 */
public class ActionStartGame extends AbstractAction implements PropertyChangeListener {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

    public ActionStartGame() {
        setEnabled(false);
        frame.addPropertyChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //<editor-fold defaultstate="collapsed" desc=" Useless Method, just here for watching PropertyChange things ">
        //get settings
        
        Thread t = new Thread(() -> {
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(CreateGameLobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(CreateGameLobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(CreateGameLobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(CreateGameLobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>


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
         
        //</editor-fold>
    }

    public boolean isEnabled() {
        return frame.getInputFile() != null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setEnabled(frame.getInputFile() != null);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame;

import simulator.client.game.Frame.Dialog.Player.CreatePlayerSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Felix
 */
public class ActionDialogPlayer extends AbstractAction {
    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if(SimulatorFrame.getInstance().connected){
            JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "Cant edit Player when you are connected.");
        }
        
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
                java.util.logging.Logger.getLogger(ActionDialogPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(ActionDialogPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(ActionDialogPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ActionDialogPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    SimulatorFrame.getInstance().playerDialog = new CreatePlayerSettings(SimulatorFrame.getInstance(), true);
                    SimulatorFrame.getInstance().playerDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            SimulatorFrame.getInstance().playerDialog.dispose();
                        }
                    });
                    SimulatorFrame.getInstance().playerDialog.setVisible(true);
                }
            });
        });
        t.start();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import Simulator.Frame.Dialog.Connect.ConnectToGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Felix
 */
public class ActionDialogConnect extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //<editor-fold defaultstate="collapsed" desc=" Action Conditions ">
        SimulatorFrame frame = SimulatorFrame.getInstance();
        /*
        if(frame.player.name != null || frame.player.name.equalsIgnoreCase("DefaultUser")){
            JOptionPane.showMessageDialog(frame, "First create a Player!");
            return;
        }
        */
        if(frame.connected){
            JOptionPane.showMessageDialog(frame, "Already Connected.");
            return;
        }
        //</editor-fold>
        
        Thread t = new Thread(() -> {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(ConnectToGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(ConnectToGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(ConnectToGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ConnectToGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the dialog */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    SimulatorFrame.getInstance().connectDialog = new ConnectToGame(frame, true);
                    SimulatorFrame.getInstance().connectDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            SimulatorFrame.getInstance().connectDialog.dispose();
                        }
                    });
                    SimulatorFrame.getInstance().connectDialog.setVisible(true);
                }
            });
        });
        t.start();
    }
}

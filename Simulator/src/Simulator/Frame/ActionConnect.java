/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import Simulator.Frame.Dialog.Connect.ConnectSettings;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Felix
 */
public class ActionConnect extends AbstractAction {

    private SimulatorFrame frame = SimulatorFrame.getInstance();
    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ActionConnect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ActionConnect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ActionConnect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ActionConnect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.connectDialog = new ConnectSettings(new javax.swing.JFrame(), true);
                frame.connectDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        frame.connectDialog.dispose();
                    }
                });
                frame.connectDialog.setVisible(true);
            }
        });
        });
        t.start();
    }
    
}

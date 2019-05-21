/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame.Dialog.Start;

import Simulator.Frame.ActionStartGame;
import Simulator.Frame.SimulatorFrame;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulator.interfaces.Client;
import simulator.interfaces.Connection;

/**
 *
 * @author Felix
 */
public class StartDialog extends javax.swing.JDialog {

    private SimulatorFrame frame = SimulatorFrame.getInstance();

    /**
     * Creates new form StartDialog
     */
    public StartDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setBounds(50, 50, 400, 250);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jRadioButton_1Player = new javax.swing.JRadioButton();
        jRadioButton_3Player = new javax.swing.JRadioButton();
        jRadioButton_2Player = new javax.swing.JRadioButton();
        jRadioButton_4Player = new javax.swing.JRadioButton();
        jTextField_Username = new javax.swing.JTextField();
        jLabel_Username = new java.awt.Label();
        jLabel_TitleStart = new java.awt.Label();
        jRadioButton_Online = new javax.swing.JRadioButton();
        jButton_Continue = new javax.swing.JButton();
        jLabel_GameName = new javax.swing.JLabel();
        jTextField_GameName = new javax.swing.JTextField();
        jLabel_GameCode = new java.awt.Label();
        jTextField_GameCode = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        buttonGroup1.add(jRadioButton_1Player);
        jRadioButton_1Player.setText("1 Player");

        buttonGroup1.add(jRadioButton_3Player);
        jRadioButton_3Player.setText("3 Player");

        buttonGroup1.add(jRadioButton_2Player);
        jRadioButton_2Player.setText("2 Player");

        buttonGroup1.add(jRadioButton_4Player);
        jRadioButton_4Player.setText("4 Player");

        jLabel_Username.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Username.setText("Username:");

        jLabel_TitleStart.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel_TitleStart.setText("Start Game Settings");

        jRadioButton_Online.setText("Online");

        jButton_Continue.setText("Continue");
        jButton_Continue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ContinueActionPerformed(evt);
            }
        });

        jLabel_GameName.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_GameName.setText("Game-Name:");
        jLabel_GameName.setToolTipText("");

        jLabel_GameCode.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_GameCode.setText("Game-Code:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_TitleStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jRadioButton_2Player)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButton_Online)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton_1Player))
                            .addComponent(jRadioButton_3Player)
                            .addComponent(jRadioButton_4Player))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton_Continue)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel_GameName)
                                        .addGap(12, 12, 12))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel_GameCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_GameCode)
                                    .addComponent(jTextField_GameName))))))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_TitleStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jRadioButton_Online)
                                .addComponent(jRadioButton_1Player))
                            .addComponent(jLabel_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jRadioButton_2Player)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel_GameCode, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jRadioButton_3Player)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel_GameName)
                                .addComponent(jTextField_GameName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton_4Player)
                            .addComponent(jButton_Continue)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField_Username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_GameCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ContinueActionPerformed
        int playerCount = 0;

        if (jRadioButton_4Player.isSelected()) {
            playerCount = 4;
        } else if (jRadioButton_3Player.isSelected()) {
            playerCount = 3;
        } else if (jRadioButton_2Player.isSelected()) {
            playerCount = 2;
        } else if (jRadioButton_1Player.isSelected()) {
            playerCount = 1;
        }

        frame.playerCount = playerCount;
        frame.player.username = jTextField_Username.getText();
        frame.gameName = jTextField_GameName.getText();
        frame.gameCode = jTextField_GameCode.getText();
        //create game and join it

            try {
                frame.registry = LocateRegistry.getRegistry(29871);
                frame.connection = (Connection) frame.registry.lookup(Connection.class.getName());
                
                frame.connection.createGame(frame.gameName, frame.playerCount, frame.gameCode);
                
                frame.clientExported = (Client) UnicastRemoteObject.exportObject(frame.ClientImpl, 0);
                frame.player.setConnectedClient(frame.ClientImpl);
                frame.server = frame.connection.joinGame(frame.ClientImpl, frame.player, frame.gameName, frame.gameCode);
                frame.player.setConnectedServer(frame.server);
                
                frame.connected = true;
                String mesg = "testMesgConnect";
                frame.server.sendString(mesg);
                frame.chatModel.addElement(mesg);
                frame.consoleList.addElement("Game created and logged in.");

            } catch (RemoteException ex) {
                Logger.getLogger(ActionStartGame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(ActionStartGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        this.dispose();
    }//GEN-LAST:event_jButton_ContinueActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton_Continue;
    private java.awt.Label jLabel_GameCode;
    private javax.swing.JLabel jLabel_GameName;
    private java.awt.Label jLabel_TitleStart;
    private java.awt.Label jLabel_Username;
    private javax.swing.JRadioButton jRadioButton_1Player;
    private javax.swing.JRadioButton jRadioButton_2Player;
    private javax.swing.JRadioButton jRadioButton_3Player;
    private javax.swing.JRadioButton jRadioButton_4Player;
    private javax.swing.JRadioButton jRadioButton_Online;
    private javax.swing.JTextField jTextField_GameCode;
    private javax.swing.JTextField jTextField_GameName;
    private javax.swing.JTextField jTextField_Username;
    // End of variables declaration//GEN-END:variables
}

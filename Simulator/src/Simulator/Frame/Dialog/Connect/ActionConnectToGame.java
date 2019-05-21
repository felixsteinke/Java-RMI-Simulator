/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame.Dialog.Connect;

import Simulator.Frame.SimulatorFrame;
import java.awt.event.ActionEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import simulator.interfaces.Client;
import simulator.interfaces.Connection;

/**
 *
 * @author Felix
 */
public class ActionConnectToGame extends AbstractAction {

    private SimulatorFrame frame = SimulatorFrame.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame.connected == true) {
            JOptionPane.showMessageDialog(null, "Already Connected");
            return;
        }
        try {
            frame.player.username = frame.connectDialog.getjTextField_Username().getText();
            frame.gameName = frame.connectDialog.getjTextField_GameName().getText();
            frame.gameCode = frame.connectDialog.getjTextField_GameCode().getText();
            
            frame.clientExported = (Client) UnicastRemoteObject.exportObject(frame.ClientImpl, 0);
            frame.player.setConnectedClient(frame.ClientImpl);
            
            frame.registry = LocateRegistry.getRegistry(29871);
            frame.connection = (Connection) frame.registry.lookup(Connection.class.getName());
            
            frame.server = frame.connection.joinGame(frame.ClientImpl, frame.player, frame.gameName, frame.gameCode);
            frame.player.setConnectedServer(frame.server);
            
            frame.connected = true;
            
            String mesg = frame.player.username + " made it in " + frame.gameName;
            frame.server.sendString(mesg);
            
            frame.connectDialog.dispose();
        } catch (RemoteException ex) {
            Logger.getLogger(Simulator.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Simulator.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

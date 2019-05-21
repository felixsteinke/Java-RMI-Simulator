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
public class ActionConnectToGamePort extends AbstractAction {
    
    private SimulatorFrame frame = SimulatorFrame.getInstance();
    private Integer port;
    private int code;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "Not Implemented");
        if(true){
            return;
        }
        //not implemented and customized now!!!
        if (frame.connected == true) {
            JOptionPane.showMessageDialog(null, "Already Connected");
            return;
        }
        //28765
        String input = JOptionPane.showInputDialog(null, "Gewünschten Port eingeben:");
        if (!input.matches("[0-9]{2,6}")){
            System.out.println("Input not valid!");
            return;
        }
        port = Integer.valueOf(input);
        System.out.println("Searching Port: " + port);
        
        try {

            frame.clientExported = (Client) UnicastRemoteObject.exportObject(frame.ClientImpl, 0);
            frame.player.setConnectedClient(frame.ClientImpl);
            frame.registry = LocateRegistry.getRegistry(port);
            frame.connection = (Connection) frame.registry.lookup(Connection.class.getName());
            frame.server = frame.connection.joinGame(frame.ClientImpl,frame.player, "testGame","123456");
            frame.player.setConnectedServer(frame.server);
            frame.connected = true;
            String mesg = "ConnectToTestPort";
            frame.server.sendString(mesg);

        } catch (RemoteException ex) {
            Logger.getLogger(Simulator.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Simulator.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

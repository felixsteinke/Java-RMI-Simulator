/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame.Dialog.Connect;

import Simulator.Frame.SimulatorFrame;
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
            JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "Already Connected");
            return;
        }
        
        String gameName = frame.connectDialog.getjTextField_GameName().getText();
        String gameCode = frame.connectDialog.getjTextField_GameCode().getText();
        
        if(gameName.equalsIgnoreCase("") || gameName == null){
            JOptionPane.showMessageDialog(frame.connectDialog, "False Input");
            return;
        }
        
        frame.gameName = gameName;
        frame.gameCode = gameCode;
        
        
        try {
            frame.registry = LocateRegistry.getRegistry(29871);
            frame.connection = (Connection) frame.registry.lookup(Connection.class.getName());
            
            frame.connect(gameName, gameCode);
            
            frame.connectDialog.dispose();
   
            
        } catch (RemoteException ex) {
            Logger.getLogger(Simulator.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Simulator.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

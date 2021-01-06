/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame.Dialog.Connect;

import simulator.client.game.Frame.SimulatorFrame;
import simulator.interfaces.Connection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Felix
 */
public class ActionConnectToGamePort extends AbstractAction {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

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
        int port = frame.calcPort();
        System.out.println("Searching Port: " + port);
        
        try {

            frame.registry = LocateRegistry.getRegistry(port);
            frame.connection = (Connection) frame.registry.lookup(Connection.class.getName());
            
            frame.connect(gameName, gameCode);
            
            frame.connectDialog.dispose();

        } catch (RemoteException ex) {
            Logger.getLogger(simulator.client.game.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(simulator.client.game.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

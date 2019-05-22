/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import simulator.data.container.PlayerDatabase;
import simulator.data.container.RaceTrack;
import simulator.interfaces.Client;

/**
 *
 * @author Peter Heusch
 */
public class ClientImpl implements Client { //old BarImpl
    
    public ClientImpl(){
    }

    @Override
    public void receiveString(String data) {
        EventQueue.invokeLater(()->{
            try {
                Thread.sleep(50);
                SimulatorFrame.getInstance().chatModel.addElement(data);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    //Prototyp
    @Override
    public void receivePlayerDatabase(PlayerDatabase data) throws RemoteException {
        EventQueue.invokeLater(()->{
            try {
                Thread.sleep(50);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SimulatorFrame.getInstance().playerDatabase = data;
    }

    @Override
    public void receiveRacetrack(RaceTrack data) throws RemoteException {
        EventQueue.invokeLater(()->{
            try {
                Thread.sleep(50);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SimulatorFrame.getInstance().setRaceTrackToPlay(data);
        SimulatorFrame.getInstance().repaint();
    }

    @Override
    public void receiveRacetracksList(String data) throws RemoteException {
        EventQueue.invokeLater(()->{
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //Dialog mit jList machen, und dann auswählen welches genommen werden soll
        String decision = JOptionPane.showInputDialog(data);
        SimulatorFrame.getInstance().consoleModel.addElement("RaceTrackList received");
        SimulatorFrame.getInstance().server.sendRaceTrackDecision(decision);
        SimulatorFrame.getInstance().consoleModel.addElement("RaceTrackDecision sended");
    }

    @Override
    public void receiveError(String data) throws RemoteException {
        EventQueue.invokeLater(()->{
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SimulatorFrame.getInstance().consoleModel.addElement(data);
    }
    
}

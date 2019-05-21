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
                Thread.sleep(250);
                SimulatorFrame.getInstance().chatModel.addElement(data);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void receivePlayerDatabase(PlayerDatabase data) throws RemoteException {
        EventQueue.invokeLater(()->{
            try {
                Thread.sleep(250);
                SimulatorFrame.getInstance().playerDatabase = data;
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void receiveRacetrack(RaceTrack data) throws RemoteException {
        EventQueue.invokeLater(()->{
            try {
                Thread.sleep(250);
                SimulatorFrame.getInstance().setTrackData(data);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void receiveRacetracksList(String data) throws RemoteException {
        EventQueue.invokeLater(()->{
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SimulatorFrame.getInstance().consoleList.addElement(data);
    }

    @Override
    public void receiveError(String data) throws RemoteException {
        EventQueue.invokeLater(()->{
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SimulatorFrame.getInstance().consoleList.addElement(data);
    }
    
}

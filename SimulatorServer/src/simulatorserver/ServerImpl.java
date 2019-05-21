/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorserver;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulator.data.container.Player;
import simulator.data.container.RaceTrack;
import simulator.data.container.Turn;
import simulator.interfaces.Server;
import simulator.interfaces.Client;

/**
 *
 * @author Peter Heusch
 */
public class ServerImpl implements Server { 
    
    Client client;

    public ServerImpl(Client client) {
        this.client = client;
    }

    @Override
    public void sendString(String data) throws RemoteException {
        try {
            String mesg = String.format("Method sendServer called from %s, data: %s",
                    RemoteServer.getClientHost(), data);
            System.out.println(mesg);
            
           Engine.sendString(this, data);
           
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendTurn(Turn data) throws RemoteException {
         try {
            String mesg = String.format("Method sendTurn called from %s, data: %s",
                     RemoteServer.getClientHost(), data);
            System.out.println(mesg);
            
            Engine.sendTurn(this, data);
            
         } catch (ServerNotActiveException ex) {
             Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public void sendRaceTrack(RaceTrack data) throws RemoteException {
        try {
            String mesg = String.format("Method sendRaceTrack called from %s, data: %s",
                     RemoteServer.getClientHost(), data);
            System.out.println(mesg);
            
            Engine.sendRaceTrack(this, data);
            
         } catch (ServerNotActiveException ex) {
             Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public void sendRaceTrackDecision(String data) throws RemoteException {
        try {
            String mesg = String.format("Method sendRaceTrackDecision called from %s, data: %s",
                     RemoteServer.getClientHost(), data);
            System.out.println(mesg);
            
            Engine.sendRaceTrackDecision(this, data);
            
         } catch (ServerNotActiveException ex) {
             Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

}

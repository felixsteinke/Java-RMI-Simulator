/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorserver;

import simulator.data.container.Player;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulator.interfaces.Server;
import simulator.interfaces.Client;
import simulator.interfaces.Connection;
import java.util.ArrayList;
import simulator.data.container.RaceTrack;

/**
 *
 * @author Peter Heusch
 */
public class ConnectionImpl implements Connection {

    @Override
    public Server joinGame(Client client, Player player, String gameName, String code) throws RemoteException {
        try {
            //Code adden
            String mesg = String.format("Method joinGame called from %s, user: %s, game %s, code: %s",
                    RemoteServer.getClientHost(), player.username, gameName, code);
            System.out.println(mesg);
            
            Server ServerFromClient = new ServerImpl(client);
            Server serverExport = (Server) UnicastRemoteObject.exportObject(ServerFromClient, 0);
            
            Engine.joinGame(ServerFromClient, client, player, gameName, code);
            //player server object zuweisen
            //Engine.add(ServerFromClient, client);
            return serverExport;
            
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ConnectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void leaveGame(Server server, Player player, String gameName) throws RemoteException {
        try {
            String mesg = String.format("Method leaveGame called from %s, user: %s, game: %s",
                    RemoteServer.getClientHost(), player.username, gameName);
            System.out.println(mesg);
            
            Engine.leaveGame(server, player, gameName);
            //Engine.remove(server);
            
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ConnectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createGame(String gameName, int count, String code, RaceTrack raceTrack) throws RemoteException {
        try {
            String mesg = String.format("Method createGAme called from %s, Gamename: %s, Playercount: %s, Code: %s, RaceTrack: %s",
                    RemoteServer.getClientHost(), gameName, count, code, raceTrack.getTrackName());
            System.out.println(mesg);
            //create "new Game()"
            Engine.createGame(gameName, count, code, raceTrack);
            
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ConnectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

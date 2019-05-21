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

    public ConnectionImpl() {
    }
    
    
    @Override
    public Server joinGame(Client client, Player player, String gameName, String code) throws RemoteException {
        try {
            //====================================================================================================
            //Request erhalten
            String mesg = String.format("Method joinGame called from %s, user: %s, game %s, code: %s",
                    RemoteServer.getClientHost(), player.username, gameName, code);
            System.out.println(mesg);
            
            //ServerObject erstellen und dem Player (Client) zuordnen
            Server serverFromClient = new ServerImpl(client);
            player.setConnectedServer(serverFromClient);
            
            //ServerObject exporten und "online" stellen
            Server serverExport = (Server) UnicastRemoteObject.exportObject(serverFromClient, 0);
            
            //Request weitergeben an die Administation
            Administation.joinGame(serverFromClient, client, player, gameName, code);
            
            //Dem Player(Client) das verbundene ServerObject geben um zu connecten
            return serverExport;
            //====================================================================================================
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ConnectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void leaveGame(Server server, Player player, String gameName) throws RemoteException {
        try {
            //====================================================================================================
            //Request erhalten
            String mesg = String.format("Method leaveGame called from %s, user: %s, game: %s",
                    RemoteServer.getClientHost(), player.username, gameName);
            System.out.println(mesg);
            
            //Request der Administation weitergeben
            Administation.leaveGame(server, player, gameName);
            
            //MISSING: Theoretisch muss das ServerObject wieder unexportet werden, hat in der Vergangenheit aber zu fehlern geführt
            //====================================================================================================
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ConnectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createGame(String gameName, int count, String code) throws RemoteException {
        try {
            //====================================================================================================
            //Request erhalten
            String mesg = String.format("Method createGAme called from %s, Gamename: %s, Playercount: %s, Code: %s",
                    RemoteServer.getClientHost(), gameName, count, code);
            System.out.println(mesg);
            
            //Request der Administaton weitergeben
            Administation.createGame(gameName, count, code);
            
            //Administation sortiert mit diesem Request auch die verbrauchten Spiele aus
            //====================================================================================================
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ConnectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

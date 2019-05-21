/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorserver;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import simulator.interfaces.Connection;

/**
 *
 * @author Peter Heusch
 */
public class Server {

    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws RemoteException, 
            AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(29871); //port verbindung
        Connection connection = new ConnectionImpl();                           // connection erstellung
        UnicastRemoteObject.exportObject(connection, 0);                // senden der connection über egal welchen port
        registry.bind(Connection.class.getName(), connection);              // zuordnung in der registry 
        Engine.getInstanceEngine();
        System.out.println("Server is running.");
        
    }
    
}

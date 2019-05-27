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
import java.util.Timer;
import java.util.TimerTask;
import simulator.interfaces.Connection;

/**
 *
 * @author felix
 * 
 * TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!
 * class should be done
 */
public class Server {
    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws RemoteException, 
            AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(29871);    // port verbindung
        Connection connection = new ConnectionImpl();                // connection erstellung
        UnicastRemoteObject.exportObject(connection, 0);             // senden der connection über egal welchen port
        registry.bind(Connection.class.getName(), connection);       // zuordnung in der registry 
        Administation.getInstance();                           // sollte nicht nötig sein
        System.out.println("Server is running.");
        
        //Falls der Server vergessen wird, dass er nicht ewig läuft (1h)
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Connection müsste theoretisch unexportet werden?
                System.exit(0);
            }
        }, 3_600_000);
    }    
    
    
}

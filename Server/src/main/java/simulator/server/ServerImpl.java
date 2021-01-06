package simulator.server;

import simulator.data.RaceTrack;
import simulator.data.Turn;
import simulator.interfaces.Client;
import simulator.interfaces.Server;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerImpl implements Server {

    //Für jedes ServerObject wird hier ein Client geaddet, der dieses überwacht, ich weiß nicht ob das noch unbedingt wichtig ist
    Client client;

    public ServerImpl(Client client) {
        this.client = client;
    }

    //Alle Interface Methoden wurden nur überschrieben und Daten an die Administration weitergegeben
    @Override
    public void sendString(String data) throws RemoteException {
        try {
            //=====================================================================
            //Request erhalten
            String msg = String.format("Method sendString called from %s, data: %s",
                    RemoteServer.getClientHost(), data);
            System.out.println(msg);

            //Request wird weitergegeben
            Administation.sendString(this, data);
            //=====================================================================
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendTurn(Turn data) throws RemoteException {
        try {
            //=====================================================================
            //Request erhalten
            String msg = String.format("Method sendTurn called from %s, data: %s",
                    RemoteServer.getClientHost(), data);
            System.out.println(msg);

            //Request wird weitergegeben
            Administation.sendTurn(this, data);
            //=====================================================================
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addRaceTrack(RaceTrack data) throws RemoteException {
        try {
            //=====================================================================
            //Request erhalten
            String msg = String.format("Method sendRaceTrack called from %s, data: %s",
                    RemoteServer.getClientHost(), data.getName());
            System.out.println(msg);

            //Request wird weitergegeben
            Administation.addRaceTrack(this, data);
            //=====================================================================
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setRaceTrackForGame(String data) throws RemoteException {
        try {
            //=====================================================================
            //Request erhalten
            String msg = String.format("Method sendRaceTrackDecision called from %s, data: %s",
                    RemoteServer.getClientHost(), data);
            System.out.println(msg);

            //Request wird weitergegeben
            Administation.setRaceTrackForGame(this, data);
            //=====================================================================
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteRaceTrack(String data) throws RemoteException {
        try {
            //=====================================================================
            //Request erhalten
            String msg = String.format("Method sendRaceTrackDelete called from %s, data: %s",
                    RemoteServer.getClientHost(), data);
            System.out.println(msg);

            //Request wird weitergegeben
            Administation.deleteRaceTrack(this, data);
            //=====================================================================
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void showRaceTrackList() throws RemoteException {
        try {
            //=====================================================================
            //Request erhalten
            String msg = String.format("Method showRaceTrackList called from %s,",
                    RemoteServer.getClientHost());
            System.out.println(msg);

            //Request wird weitergegeben
            Administation.showRaceTrackList(this);
            //=====================================================================
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

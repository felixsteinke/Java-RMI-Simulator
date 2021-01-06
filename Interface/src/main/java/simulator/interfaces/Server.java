package simulator.interfaces;

import simulator.data.RaceTrack;
import simulator.data.Turn;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void sendTurn(Turn turn) throws RemoteException;

    void sendString(String data) throws RemoteException;

    void addRaceTrack(RaceTrack data) throws RemoteException;

    void setRaceTrackForGame(String data) throws RemoteException;

    void deleteRaceTrack(String data) throws RemoteException;

    void showRaceTrackList() throws RemoteException;
}

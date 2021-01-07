package simulator.interfaces;

import simulator.data.PlayerDatabase;
import simulator.data.RaceTrack;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void receivePlayerDatabase(PlayerDatabase data) throws RemoteException;

    void receiveRacetrack(RaceTrack data) throws RemoteException;

    void receiveString(String data) throws RemoteException; //old bar

    void receiveRacetracksList(String data) throws RemoteException;

    void receiveFeedback(String data) throws RemoteException;
}

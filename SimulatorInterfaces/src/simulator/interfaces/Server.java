/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import simulator.data.container.Player;
import simulator.data.container.RaceTrack;
import simulator.data.container.Turn;


/**
 *
 * @author Felix
 */
public interface Server extends Remote {
    public void sendTurn(Turn turn) throws RemoteException;
    public void sendString(String data) throws RemoteException;
    public void addRaceTrack(RaceTrack data) throws RemoteException;
    public void setRaceTrackForGame(String data) throws RemoteException;
    public void deleteRaceTrack (String data) throws RemoteException;
    public void showRaceTrackList () throws RemoteException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import simulator.data.container.PlayerDatabase;
import simulator.data.container.RaceTrack;


/**
 *
 * @author Felix
 */
public interface Client extends Remote{
    public void receivePlayerDatabase(PlayerDatabase data) throws RemoteException;
    public void receiveRacetrack(RaceTrack data) throws RemoteException;
    public void receiveString(String data) throws RemoteException; //old bar
    public void receiveRacetracksList (String data) throws RemoteException;
    public void receiveError(String data) throws RemoteException;
}

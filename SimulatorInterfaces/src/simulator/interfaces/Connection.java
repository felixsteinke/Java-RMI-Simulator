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

/**
 *
 * @author Felix
 */
public interface Connection extends Remote {
    public Server joinGame(Client client, Player player, String gameName, String code) throws RemoteException;
    public void leaveGame(Server server, Player player, String gameName) throws RemoteException;
    public void createGame (String name, int count, String code) throws RemoteException;
}

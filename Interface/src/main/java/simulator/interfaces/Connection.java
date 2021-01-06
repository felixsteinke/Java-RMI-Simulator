package simulator.interfaces;

import simulator.data.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connection extends Remote {
    Server joinGame(Client client, Player player, String gameName, String code) throws RemoteException;

    void leaveGame(Server server, Player player, String gameName) throws RemoteException;

    void createGame(String name, int count, String code) throws RemoteException;
}

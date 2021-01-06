/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame.Dialog.Game;

import simulator.client.game.Frame.ActionStartGame;
import simulator.client.game.Frame.SimulatorFrame;
import simulator.interfaces.Connection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felix
 */
public class ActionCreateGamePort extends AbstractAction {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        String gameName = frame.gameDialog.getjTextField_GameName().getText();
        String gameCode = frame.gameDialog.getjTextField_GameCode().getText();
        int playerCount = frame.gameDialog.calcPlayerCount();

        //!!!!!MISSING!!!!!! abfrage ob die eingaben richtig sind
        if (frame.gameDialog.validateInputGameSettings(gameName, gameCode, playerCount)) {
            JOptionPane.showMessageDialog(frame.gameDialog, "No Valid Game Settings");
            return;
        }

        frame.playerCount = playerCount;
        frame.gameName = gameName;
        frame.gameCode = gameCode;
        //create game

        int port = frame.calcPort();
        try {
            frame.registry = LocateRegistry.getRegistry(port);
            frame.connection = (Connection) frame.registry.lookup(Connection.class.getName());

            frame.connection.createGame(frame.gameName, frame.playerCount, frame.gameCode);

        } catch (RemoteException ex) {
            Logger.getLogger(ActionStartGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ActionStartGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.gameDialog.dispose();
    }

}

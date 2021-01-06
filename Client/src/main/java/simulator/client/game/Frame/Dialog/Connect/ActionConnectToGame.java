package simulator.client.game.Frame.Dialog.Connect;

import simulator.client.game.Frame.SimulatorFrame;
import simulator.interfaces.Connection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionConnectToGame extends AbstractAction {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame.connected) {
            JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "Already Connected");
            return;
        }

        String gameName = frame.connectDialog.getjTextField_GameName().getText();
        String gameCode = frame.connectDialog.getjTextField_GameCode().getText();

        if (gameName.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(frame.connectDialog, "False Input");
            return;
        }

        frame.gameName = gameName;
        frame.gameCode = gameCode;

        try {
            frame.registry = LocateRegistry.getRegistry(29871);
            frame.connection = (Connection) frame.registry.lookup(Connection.class.getName());

            frame.connect(gameName, gameCode);

            frame.connectDialog.dispose();

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(simulator.client.game.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

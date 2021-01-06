package simulator.client.game.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionDisconnectFromGame extends AbstractAction {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!frame.connected) {
            JOptionPane.showMessageDialog(frame, "Not Connected");
            return;
        }

        EventQueue.invokeLater(() -> {
            try {
                String mesg = frame.player.name + " disconnected from " + frame.gameName;
                frame.server.sendString(mesg);
                frame.chatModel.addElement(mesg);
                frame.connection.leaveGame(frame.server, frame.player, frame.gameName);
                ///!!!!!!!MISSING!!!!!!!! warum ist das nicht exporte
                UnicastRemoteObject.unexportObject(frame.ClientImpl, false);
                frame.setRaceTrackToPlay(null);
                frame.playerDatabase = null;
                frame.connected = false;
                frame.consoleModel.addElement(mesg);
            } catch (RemoteException ex) {
                Logger.getLogger(simulator.client.game.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void actionPerformed(WindowEvent e) {
        if (!frame.connected) {
            JOptionPane.showMessageDialog(frame, "Not Connected");
            return;
        }

        EventQueue.invokeLater(() -> {
            try {
                String mesg = frame.player.name + " disconnected from " + frame.gameName;
                frame.server.sendString(mesg);
                frame.connection.leaveGame(frame.server, frame.player, frame.gameName);
                ///!!!!!!!MISSING!!!!!!!! warum ist das nicht exporte
                UnicastRemoteObject.unexportObject(frame.ClientImpl, false);
                frame.setRaceTrackToPlay(null);
                frame.playerDatabase = null;
                frame.connected = false;
                frame.consoleModel.addElement(mesg);
            } catch (RemoteException ex) {
                Logger.getLogger(simulator.client.game.Frame.SimulatorFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}

package simulator.client.game.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionShowServerRaceTracks extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!SimulatorFrame.getInstance().connected) {
            JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "Not connected!");
            return;
        }
        try {
            SimulatorFrame.getInstance().server.showRaceTrackList();
        } catch (RemoteException ex) {
            Logger.getLogger(ActionShowServerRaceTracks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

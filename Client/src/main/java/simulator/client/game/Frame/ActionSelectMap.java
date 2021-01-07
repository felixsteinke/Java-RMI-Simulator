package simulator.client.game.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionSelectMap extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!SimulatorFrame.getInstance().connected) {
            JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "Not connected.");
            return;
        }

        try {
            String selection = JOptionPane.showInputDialog("Write the Map-Name:");
            SimulatorFrame.getInstance().server.setRaceTrackForGame(selection);
        } catch (RemoteException ex) {
            Logger.getLogger(ActionSelectMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

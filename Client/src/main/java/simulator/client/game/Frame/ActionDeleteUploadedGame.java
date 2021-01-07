package simulator.client.game.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionDeleteUploadedGame extends AbstractAction {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame.connected) {
            String name = JOptionPane.showInputDialog(frame, "Which RaceTrack should be deleted on the Server?");
            try {
                frame.server.deleteRaceTrack(name);
            } catch (RemoteException ex) {
                Logger.getLogger(ActionDeleteUploadedGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Not connected!");
        }
    }
}

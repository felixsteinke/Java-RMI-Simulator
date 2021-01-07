package simulator.client.game.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActionCloseGame extends AbstractAction {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!frame.connected) {
            frame.setRaceTrackToPlay(null);
        } else {
            new ActionDisconnectFromGame().actionPerformed(e);
        }
        JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "Game closed.");
    }
}

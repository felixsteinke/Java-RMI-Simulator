package simulator.client.game.Frame;

import simulator.data.Move;
import simulator.data.Player;
import simulator.data.Turn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionSendTurn extends AbstractAction {

    private final SimulatorFrame frame = SimulatorFrame.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        Player myPlayer = new Player();

        for (Player player : frame.playerDatabase.playerlist) {
            if (player.name.equalsIgnoreCase(frame.player.getName())) {
                System.out.println("You are player: " + player.getName());
                myPlayer = player;
            }
        }

        Turn oldTurn = myPlayer.getTurns().get(myPlayer.getTurns().size() - 1);
        int moveValue = Integer.parseInt(frame.jTextField_Turn.getText());
        Turn turn = new Turn(oldTurn, new Move(moveValue, frame.getRaceTrackToPlay().getDistance()));
        try {
            frame.server.sendTurn(turn);
        } catch (RemoteException ex) {
            Logger.getLogger(ActionSendTurn.class.getName()).log(Level.SEVERE, null, ex);
        }

        frame.jButton_Turn.setEnabled(false);
        frame.jLabelTurn.setVisible(false);
    }
}

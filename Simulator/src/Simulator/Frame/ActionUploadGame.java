/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import simulator.data.container.RaceTrack;

/**
 *
 * @author Felix
 */
public class ActionUploadGame extends AbstractAction {

    private SimulatorFrame frame = SimulatorFrame.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        if(frame.connected == false){
            JOptionPane.showMessageDialog(frame, "Not Connected");
            return;
        }
        RaceTrack raceTrack = frame.getRaceTrackToUpload();
        
        if (validateRaceTrack(raceTrack) == true){
            try {
                frame.server.sendRaceTrack(raceTrack);
            } catch (RemoteException ex) {
                Logger.getLogger(ActionUploadGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(frame, raceTrack.getName() + " was not ready to upload!");
        }    
    }

    private boolean validateRaceTrack(RaceTrack raceTrack) {
        if (raceTrack.getName() != null
                && raceTrack.getWidthField() > 0
                && raceTrack.getHeightField() > 0
                && raceTrack.getPointsOutter() > 0
                && raceTrack.getPointsInner() > 0
                && (raceTrack.getCoordOuter().size() == raceTrack.getPointsOutter())
                && (raceTrack.getCoordInner().size() == raceTrack.getPointsInner())
                && (raceTrack.getCoordStart().size() == 2)
                /*&& (raceTrack.getCoordControl().size() == 2)*/
                && raceTrack.getDistance() > 0
                && raceTrack.getGridSize() > 0
                && raceTrack.getGapSize() > 0
                && raceTrack.getValidPoints() != null
                && raceTrack.getStartPoints() != null) {
            return true;
        } else {
            return false;
        }
    }
}

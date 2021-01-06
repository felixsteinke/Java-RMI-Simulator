/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.client.game.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Felix
 */
public class ActionHelpGuide extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        String guide = "Willkommen im Simulator! \n"
                + "Zuerst: Server starten nicht vergessen. \n\n"
                //Menu Get Started / Connection
                + "1.Schritt: Spieler erstellen. -> Get Started -> Create Player \n\n"
                + "2.Schritt: Spiel erstellen. -> Connection -> Create Game \n"
                + "3.Schritt: Zu Spiel verbinden -> Connection -> Connect -> Local Connect \n"
                + "(IP und Port Connect sind für Spiele auf externen Servern gedacht - ungetestet) \n"
                + "4.Schritt: Racetrack für Spiel festlegen. Connection -> Show RaceTrack + ../ -> Select RaceTrack \n"
                + "Spiel wird starten, sobald das Spiel  genug Spieler hat und einen RaceTrack ausgewählt ist.\n"
                + "Wenn ein Spiel beendet ist, muss Disconnected werden, und zu einem neuen Spiel connected werden. \n\n"
                + "Zusätzliche Funktionen:\n"
                + "CreationTool: für neue RaceTracks, beachten sie die TODO-Anweisungen.\n"
                + "Um die Server-RaceTrack-Datenbank zu verwalten nutzen sie die Buttons Upload und Delete.\n"
                //Menu Create Map
                + "Messenger: Nutzbar um mit anderen Spielern in einem Spiel zu kommunizieren.\n\n"
                + "Bei Problemen über 82stfe1bif@hft-stuttgart.de den Support kontaktieren.";
        JOptionPane.showMessageDialog(SimulatorFrame.getInstance() ,guide);
        
    }
    
}

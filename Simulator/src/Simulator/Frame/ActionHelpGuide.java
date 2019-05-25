/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Felix
 */
public class ActionHelpGuide extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        String guide = "Welcome to the Simulator! \n"
                //Menu Get Started / Connection
                + "Um zu starten muss man erstmal ein Player erstellen! -> Get Started -> Create Player \n"
                + "Dann kann man zu einem Spiel beitreten, oder ein eigenes Spiel erstellen -> Connection \n"
                + "(Hinweis: Die Buttons IP und Port sind für erweiterte Funktionen gedacht, nutzen sie 'Quick')\n"
                + "Nun stehen ihnen alle Möglichkeiten offen:\n"
                + "Wollen sie Spielen? Warten sie, bis das Spiel voll wird.\n"
                + "(Hinweis: Vergessen sie während dem Spiel nicht ihren Turn zu machen, sonst werden sie gekickt.)\n"
                //Menu Create Map
                + "Zusätzliche Funktion: Maps erstellen, auf den Server laden, oder diese löschen. -> CreateMap\n"
                + "-> Creation Tool: Maps erstellen / editieren / in das Programm laden.\n"
                + "-> Upload: Falls mit einem Server verbunden, die zuvor erstellte Map hochladen.\n"
                + "-> Delete: Falls mit einem Server verbunden, eine bekannte Map löschen.\n"
                //Menu Messenger
                + "Zusätzliche Funktion: Messenger, hier können alle Spieler die in einem Spiel sind kommunizieren.\n\n"
                + "Bei Problemen über 82stfe1bif@hft-stuttgart.de den Support kontaktieren.";
        JOptionPane.showMessageDialog(SimulatorFrame.getInstance(),guide);
        
    }
    
}

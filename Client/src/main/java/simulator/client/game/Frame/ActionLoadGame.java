package simulator.client.game.Frame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class ActionLoadGame extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Load CSV-File", "csv"));
        int showOpenDialog = fileChooser.showOpenDialog(SimulatorFrame.getInstance());

        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            File input = fileChooser.getSelectedFile();
            SimulatorFrame.getInstance().consoleModel.addElement("Loaded File: " + input.getName());
            SimulatorFrame.getInstance().setInputFile(input);
            SimulatorFrame.getInstance().setCsvData(input);
        } else {
            JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "No Selection");
        }
    }
}

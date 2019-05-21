/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creation.Frame;

import simulator.data.container.RaceTrack;
import com.sun.java.swing.plaf.windows.resources.windows;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Felix
 */
public class CreationFrame extends javax.swing.JFrame {

    public RaceTrack data = new RaceTrack();
    private PhasesThread manager;
    private int phase = 0;
    private boolean readyToFinish = false;
    private boolean lastPoint = false;
    private boolean saveMap = false;
    private Rectangle gameRect;
    private Rectangle screenRect;
    private ArrayList<Point> tempPoints = new ArrayList<Point>();

    /**
     * Creates new form CreationFrame
     */
    public CreationFrame() {
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        toDoMapName();
        toDoBorders();
        initComponents();
        this.manager = new PhasesThread();
        this.manager.start();

    }

    //Method important for UnDo implementation
    private void toDoPhases() {
        switch (phase) {
            case 1:
                break;
            case 2:
                toDoGetStartPoint();
                break;
            case 3:
                toDoGetPointPairOuter();
                break;
            case 4:
                toDoGetLastHelperPointOuter();
                break;
            case 5:
                toDoGetPointPairInner();
                break;
            case 6:
                toDoGetLastHelperPointInner();
                break;
            case 7:
                toDoSaveFile();
                break;
            case 8:
                toDoCloseFrame();
                break;

        }
    }

    //Phase 0
    private void toDoMapName() {
        data.setTrackName(JOptionPane.showInputDialog(rootPane, "File Name:"));
        phase = 1;
    }

    //Phase 1
    private void toDoBorders() {
        String inputString;
        inputString = JOptionPane.showInputDialog(rootPane, "Breite des Feldes:");
        data.setWidthField(Integer.valueOf(inputString));
        inputString = JOptionPane.showInputDialog(rootPane, "Höhe des Feldes:");
        data.setHeightField(Integer.valueOf(inputString));
        inputString = JOptionPane.showInputDialog(rootPane, "Rastermaß:");
        data.setGridSize(Integer.valueOf(inputString));
        phase = 2;
    }

    //Phase 2
    private void toDoGetStartPoint() {
        label_ToDo.setText("ToDo: 2 Startpunkte festlegen. (links/außen, dann rechts/innen)");
        if (tempPoints.size() == 2) {
            data.getCoordStart().add(tempPoints.get(0));
            data.getCoordStart().add(tempPoints.get(1));
            data.getCoordOuter().add(tempPoints.get(0));
            data.getCoordInner().add(tempPoints.get(1));
            tempPoints.clear();
            JOptionPane.showMessageDialog(rootPane, phase + " done");
            phase = 3;
            repaint();
        }
    }

    //Phase 3
    private void toDoGetPointPairOuter() {
        label_ToDo.setText("ToDo: Äußerer Kreis - 2 Roadpoints festlegen. (Fixpunkt, dann Hilfspunkt)");
        if (tempPoints.size() == 2) {
            data.getCoordOuter().add(tempPoints.get(1));
            data.getCoordOuter().add(tempPoints.get(0));
            tempPoints.clear();
            JOptionPane.showMessageDialog(rootPane, phase + " done");
            if (lastPoint == true) {
                phase = 4;
                lastPoint = false;
                repaint();
            }
        }
    }

    //Phase 4
    private void toDoGetLastHelperPointOuter() {
        label_ToDo.setText("ToDo: Letzer Hilfspunkt für äußeren Kreis.");
        if (tempPoints.size() == 1) {
            data.getCoordOuter().add(tempPoints.get(0));
            tempPoints.clear();
            JOptionPane.showMessageDialog(rootPane, phase + " done");
            phase = 5;
            repaint();
        }
    }

    //Phase 5
    private void toDoGetPointPairInner() {
        label_ToDo.setText("ToDo: Innerer Kreis - 2 Roadpoints festlegen. (Fixpunkt, dann Hilfspunkt)");
        if (tempPoints.size() == 2) {
            data.getCoordInner().add(tempPoints.get(1));
            data.getCoordInner().add(tempPoints.get(0));
            tempPoints.clear();
            JOptionPane.showMessageDialog(rootPane, phase + " done");
            if (lastPoint == true) {
                phase = 6;
                lastPoint = false;
                repaint();
            }
        }
    }

    //Phase 6
    private void toDoGetLastHelperPointInner() {
        label_ToDo.setText("ToDo: Letzer Hilfspunkt für inneren Kreis.");
        if (tempPoints.size() == 1) {
            data.getCoordInner().add(tempPoints.get(0));
            tempPoints.clear();
            JOptionPane.showMessageDialog(rootPane, phase + " done");
            phase = 7;
            repaint();
        }
    }

    //Phase 7
    private void toDoSaveFile() {
        label_ToDo.setText("ToDo: Map speichern?");
        if (saveMap == true) {
            data.exportFile();
            phase = 8;
        }
    }

    //Phase 8
    private void toDoCloseFrame() {
        readyToFinish = true;
        //manager.stop();
        this.dispose();
    }

    private void useUnDo() {
        JOptionPane.showMessageDialog(rootPane, "Not implemented!");
        switch (phase) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new CreationPanel();
        label_Title = new java.awt.Label();
        label_ToDo = new java.awt.Label();
        jButton_Undo = new javax.swing.JButton();
        jButton_LastPoint = new javax.swing.JButton();
        jButton_SaveMap = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calcMousePoint(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        label_Title.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        label_Title.setText("Create a new Map");

        label_ToDo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        label_ToDo.setText("ToDo:");

        jButton_Undo.setText("UnDo");
        jButton_Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_UndoActionPerformed(evt);
            }
        });

        jButton_LastPoint.setText("Set last Point");
        jButton_LastPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LastPointActionPerformed(evt);
            }
        });

        jButton_SaveMap.setText("Save Map");
        jButton_SaveMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SaveMapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(label_ToDo, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(jButton_SaveMap, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_LastPoint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_Undo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_Undo)
                            .addComponent(jButton_LastPoint)
                            .addComponent(jButton_SaveMap)))
                    .addComponent(label_ToDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void calcMousePoint(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calcMousePoint

        //if (evt.getClickCount() == 2) {
        int x = evt.getX();
        int y = evt.getY();
        if (!gameRect.contains(x, y)) {
            return;
        }
        tempPoints.add(new Point(x - gameRect.x, y - gameRect.y));
        repaint();
        //}
    }//GEN-LAST:event_calcMousePoint

    private void jButton_LastPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LastPointActionPerformed
        // TODO add your handling code here:
        lastPoint = true;
    }//GEN-LAST:event_jButton_LastPointActionPerformed

    private void jButton_UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_UndoActionPerformed
        // TODO add your handling code here:
        useUnDo();
    }//GEN-LAST:event_jButton_UndoActionPerformed

    private void jButton_SaveMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SaveMapActionPerformed
        // TODO add your handling code here:
        saveMap = true;
    }//GEN-LAST:event_jButton_SaveMapActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreationFrame().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_LastPoint;
    private javax.swing.JButton jButton_SaveMap;
    private javax.swing.JButton jButton_Undo;
    private javax.swing.JPanel jPanel1;
    private java.awt.Label label_Title;
    private java.awt.Label label_ToDo;
    // End of variables declaration//GEN-END:variables

    private class CreationPanel extends JPanel {

        public CreationPanel() {
        }

        public void paintComponent(Graphics gc) {
            super.paintComponent(gc);
            Graphics2D g2d = (Graphics2D) gc;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //GameSize
            screenRect = this.getBounds();
            gameRect = new Rectangle(screenRect.width / 2 - data.getWidthField() / 2, 0, data.getWidthField(), data.getHeightField());
            //Background
            g2d.setColor(Color.black);
            g2d.fillRect(screenRect.width / 2 - data.getWidthField() / 2, 0, data.getWidthField(), data.getHeightField());
            //Grid over Map
            g2d.setColor(Color.darkGray);
            g2d.setStroke(new BasicStroke(1.0f));
            for (int x = gameRect.x + data.getGridSize(); x < gameRect.x + data.getWidthField(); x += data.getGridSize()) {
                g2d.drawLine(x, 0, x, data.getHeightField());
            }
            for (int y = data.getGridSize(); y < data.getHeightField(); y += data.getGridSize()) {
                g2d.drawLine(gameRect.x, y, gameRect.x + data.getWidthField(), y);
            }
            if (phase < 2) {
                return;
            };

            if (phase < 3) {
                return;
            };
            //draw start line
            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(5.0f));
            g2d.drawLine(gameRect.x + data.getCoordStart().get(0).x, gameRect.y + data.getCoordStart().get(0).y,
                    gameRect.x + data.getCoordStart().get(1).x, gameRect.y + data.getCoordStart().get(0).y);
            //start outer path
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1.0f));
            GeneralPath pathFormOuter = new GeneralPath(0);
            pathFormOuter.moveTo(gameRect.x + data.getCoordOuter().get(0).x, gameRect.y + data.getCoordOuter().get(0).y);

            //start inner path
            GeneralPath pathFormInner = new GeneralPath(0);
            pathFormInner.moveTo(gameRect.x + data.getCoordInner().get(0).x, gameRect.y + data.getCoordInner().get(0).y);
            if (phase < 4) {
                return;
            };
            //draw outer path
            for (int i = 1; i < data.getCoordOuter().size() - 1; i += 2) {
                pathFormOuter.quadTo(gameRect.x + data.getCoordOuter().get(i).x, gameRect.y + data.getCoordOuter().get(i).y,
                        gameRect.x + data.getCoordOuter().get(i + 1).x, gameRect.y + data.getCoordOuter().get(i + 1).y);
            }
            if (phase < 5) {
                g2d.setColor(Color.green);
                for (Point point : data.getCoordOuter()) {
                    g2d.fillRect(point.x - 2, point.x - 2, 4, 4);
                }
                g2d.draw(pathFormOuter);
                return;
            };
            //close outer path
            pathFormOuter.quadTo(gameRect.x + data.getCoordOuter().get(data.getCoordOuter().size() - 1).x, gameRect.y + data.getCoordOuter().get(data.getCoordOuter().size() - 1).y,
                    gameRect.x + data.getCoordOuter().get(0).x, gameRect.y + data.getCoordOuter().get(0).y);
            pathFormOuter.closePath();
            g2d.draw(pathFormOuter);
            if (phase < 6) {
                return;
            };
            //draw inner path
            for (int i = 1; i < data.getCoordInner().size() - 1; i += 2) {
                pathFormInner.quadTo(gameRect.x + data.getCoordInner().get(i).x, gameRect.y + data.getCoordInner().get(i).y,
                        gameRect.x + data.getCoordInner().get(i + 1).x, gameRect.y + data.getCoordInner().get(i + 1).y);
            }
            if (phase < 7) {
                g2d.setColor(Color.green);
                for (Point point : data.getCoordInner()) {
                    g2d.fillRect(point.x - 2, point.x - 2, 4, 4);
                }
                g2d.draw(pathFormInner);
                return;
            };
            //close inner path
            pathFormInner.quadTo(gameRect.x + data.getCoordInner().get(data.getCoordInner().size() - 1).x, gameRect.y + data.getCoordInner().get(data.getCoordInner().size() - 1).y,
                    gameRect.x + data.getCoordInner().get(0).x, gameRect.y + data.getCoordInner().get(0).y);
            pathFormInner.closePath();
            g2d.draw(pathFormInner);

        }
    }

    private class PhasesThread extends Thread {

        @Override
        public void run() {
            try {
                while (!readyToFinish) {
                    toDoPhases();
                    Thread.sleep(50);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(CreationFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

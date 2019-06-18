/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creation.Frame;

import Simulator.Frame.SimulatorFrame;
import simulator.data.container.RaceTrack;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Felix
 *
 */

public class CreationFrame extends javax.swing.JFrame {

    public RaceTrack data = new RaceTrack();
    private int state = 1;
    //startLine
    private boolean state1finished = false;
    //controlLine
    private boolean state2finished = false;
    //outerLine
    private boolean state3finished = false;
    //innerLine
    private boolean state4finished = false;
    private boolean setLastPoint = false;
    //start Points
    private boolean state5finished = false;
    private Rectangle gameRect;
    private Rectangle screenRect;

    /**
     * Creates new form CreationFrame
     */
    
    public CreationFrame() {
        setExtendedState(MAXIMIZED_BOTH);
        initComponents();
        setValues();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private void setValues() {
        data.setName(jTextField_Name.getText());
        data.setWidthField(Integer.valueOf(jTextField_Width.getText()));
        data.setHeightField(Integer.valueOf(jTextField_Height.getText()));
        data.setDistance(Integer.valueOf(jTextField_Dicstance.getText()));
        data.calcOnDistance();
    }

    private void checkState1() {
        if (data.getCoordStart().size() == 2) {
            state1finished = true;
        }
    }

    private void checkState2() {
        if (data.getCoordControl().size() == 2) {
            state2finished = true;
        }
    }

    private void checkState3() {
        if (setLastPoint) {
            if (data.getCoordOuter().size() % 2 == 0) {
                state3finished = true;
                setLastPoint = false;
            }
        }
    }

    private void checkState4() {
        if (setLastPoint) {
            if (data.getCoordInner().size() % 2 == 0) {
                state4finished = true;
                setLastPoint = false;
            }
        }
    }

    private void checkState5() {
        if (data.getStartPoints().size() == 5) {
            state5finished = true;
        }
    }

    private void setToDoText() {
        switch (state) {
            case 1:
                if (state1finished) {
                    label_ToDo.setText("TODO: UNDO or CHANGE workspace.");
                } else {
                    label_ToDo.setText("TODO: Set 2 points for startline (1. left | 2. right)");
                }
                break;
            case 2:
                if (state2finished) {
                    label_ToDo.setText("TODO: UNDO or CHANGE workspace.");
                } else {
                    label_ToDo.setText("TODO: Set 2 points for ControlLine (1. from innerLine | 2. from outerLine)");
                }
                break;
            case 3:
                if (state3finished) {
                    label_ToDo.setText("TODO: UNDO or CHANGE workspace.");
                } else if (setLastPoint) {
                    label_ToDo.setText("TODO: Set last HELPERPOINT for outline.");
                } else {
                    label_ToDo.setText("TODO: Set 2 POINTS for outline (1. helper | 2. fix)");
                }
                break;
            case 4:
                if (state4finished) {
                    label_ToDo.setText("TODO: UNDO or CHANGE workspace.");
                } else if (setLastPoint) {
                    label_ToDo.setText("TODO: Set last HELPERPOINT for innerline.");
                } else {
                    label_ToDo.setText("TODO: Set 2 POINTS for innerline (1. helper | 2. fix)");
                }
                break;
            case 5:
                if (state2finished) {
                    label_ToDo.setText("TODO: UNDO or CHANGE workspace.");
                } else {
                    label_ToDo.setText("TODO: Click on 5 Rectangles to set Start Points");
                }
                break;
            default:
                label_ToDo.setText("Something went wrong.");
        }
        if (state1finished && state2finished && state3finished && state4finished && state5finished) {
            label_ToDo.setText("TODO: UNDO or SAVE the RaceTrack.");
        }
    }

    private void useUNDO() {
        setLastPoint = false;
        switch (state) {
            case 1:
                data.getCoordStart().clear();
                state1finished = false;
                break;
            case 2:
                data.getCoordControl().clear();
                state2finished = false;
                break;
            case 3:
                if (data.getCoordOuter().size() < 2) {
                    JOptionPane.showMessageDialog(this, "No Points to delete.");
                    return;
                }
                data.getCoordOuter().remove(data.getCoordOuter().size() - 1);
                state3finished = false;
                break;
            case 4:
                if (data.getCoordInner().size() < 2) {
                    JOptionPane.showMessageDialog(this, "No Points to delete.");
                    return;
                }
                data.getCoordInner().remove(data.getCoordInner().size() - 1);
                state4finished = false;
                break;
            case 5:
                data.getStartPoints().remove(data.getStartPoints().size() - 1);
                state5finished = false;
                break;
            default:
                JOptionPane.showMessageDialog(this, "Something went wrong!");
        }
    }

    private void addPoint(Point point) {
        switch (state) {
            case 1:
                checkState1();
                if (state1finished) {
                    JOptionPane.showMessageDialog(this, "State already done.");
                    return;
                }
                data.getCoordStart().add(point);
                if (data.getCoordStart().size() == 1) {
                    data.getCoordOuter().add(0, point);
                } else {
                    data.getCoordInner().add(0, point);
                }
                break;
            case 2:
                if (!state3finished || !state4finished || !state1finished) {
                    JOptionPane.showMessageDialog(this, "Finish In- and Outline first.");
                    return;
                }
                checkState2();
                if (state2finished) {
                    JOptionPane.showMessageDialog(this, "State already done.");
                    return;
                }
                Point tempPoint = new Point();
                if (data.getCoordControl().size() == 1) {
                    tempPoint = calcClosePoint(data.getCoordOuter(), point);
                } else {
                    tempPoint = calcClosePoint(data.getCoordInner(), point);
                }
                data.getCoordControl().add(tempPoint);
                break;
            case 3:
                if (!state1finished) {
                    JOptionPane.showMessageDialog(this, "Finish START LINE first.");
                }
                checkState3();
                if (state3finished) {
                    JOptionPane.showMessageDialog(this, "State already done.");
                    return;
                }
                data.getCoordOuter().add(point);
                checkState3();
                break;
            case 4:
                if (!state1finished) {
                    JOptionPane.showMessageDialog(this, "Finish START LINE first.");
                }
                checkState4();
                if (state4finished) {
                    JOptionPane.showMessageDialog(this, "State already done.");
                    return;
                }
                data.getCoordInner().add(point);
                checkState4();
                break;
            case 5:
                if (!state3finished || !state4finished || !state1finished || !state2finished) {
                    JOptionPane.showMessageDialog(this, "Finish all other things first.");
                    return;
                }
                checkState5();
                if (state5finished) {
                    JOptionPane.showMessageDialog(this, "State already done.");
                    return;
                }
                Point startPoint = calcClosePoint(data.getValidPoints(), point);
                if (!data.getStartPoints().contains(startPoint)) {
                    data.getStartPoints().add(startPoint);
                }
                break;
            default:
                JOptionPane.showMessageDialog(this, "Something went wrong!");
        }
    }

    private boolean checkReadyToSave() {
        if (!state1finished) {
            JOptionPane.showMessageDialog(this, "State 1 not finished.");
            return false;
        }
        if (!state2finished) {
            JOptionPane.showMessageDialog(this, "State 2 not finished.");
            return false;
        }
        if (!state3finished) {
            JOptionPane.showMessageDialog(this, "State 3 not finished.");
            return false;
        }
        if (!state4finished) {
            JOptionPane.showMessageDialog(this, "State 4 not finished.");
            return false;
        }
        if (!state5finished) {
            JOptionPane.showMessageDialog(this, "State 5 not finished.");
            return false;
        }
        return true;
    }

    private void deleteState() {
        setLastPoint = false;
        switch (state) {
            case 1:
                data.getCoordStart().clear();
                state1finished = false;
                break;
            case 2:
                data.getCoordControl().clear();
                state2finished = false;
                break;
            case 3:
                List<Point> deleteListOut = data.getCoordOuter().subList(1, data.getCoordOuter().size());
                data.getCoordOuter().removeAll(deleteListOut);
                state3finished = false;
                break;
            case 4:
                List<Point> deleteListIn = data.getCoordInner().subList(1, data.getCoordInner().size());
                data.getCoordInner().removeAll(deleteListIn);
                state4finished = false;
                break;
            case 5:
                data.getStartPoints().clear();
                state5finished = false;
            default:
                JOptionPane.showMessageDialog(this, "Something went wrong!");
        }
        setToDoText();
        this.repaint();
    }

    private Point calcClosePoint(ArrayList<Point> list, Point point) {
        double range = Integer.MAX_VALUE;
        double tempRange;
        Point tempPoint = new Point();

        for (Point listPoint : list) {
            if ((tempRange = Math.sqrt((Math.pow(point.x - listPoint.x, 2) + Math.pow(point.y - listPoint.y, 2)))) < range) {
                range = tempRange;
                tempPoint = listPoint;
            }
        }
        System.out.println(tempPoint.toString());
        return tempPoint;
    }

    /*<editor-fold defaultstate="collapsed" desc="Useless Method">   
    private ArrayList<Point> calcStartPoints(ArrayList<Point> validPoints, Point midPoint) {

        ArrayList<Point> startPoints = new ArrayList<Point>();
        int range = Integer.MAX_VALUE;
        int tempRange;
        Point tempPoint = new Point();

        for (int i = 0; i < 5; i++) {
            for (Point listPoint : validPoints) {
                if (!startPoints.contains(listPoint)) {
                    if (listPoint.y > midPoint.y) {
                        if ((tempRange = Math.abs((midPoint.x + midPoint.y) - (listPoint.x + listPoint.y))) < range) {
                            range = tempRange;
                            tempPoint = listPoint;
                        }
                    }
                }
                startPoints.add(tempPoint);
            }
        }
        System.out.println(startPoints.toString());
        return startPoints;
    }
     */
    //</editor-fold>
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_Work = new javax.swing.ButtonGroup();
        jPanel1 = new CreationPanel();
        label_Title = new java.awt.Label();
        label_ToDo = new java.awt.Label();
        jButton_Undo = new javax.swing.JButton();
        jButton_LastPoint = new javax.swing.JButton();
        jButton_SaveMap = new javax.swing.JButton();
        jRadioButton_Start = new javax.swing.JRadioButton();
        jRadioButton_Controll = new javax.swing.JRadioButton();
        jRadioButton_Out = new javax.swing.JRadioButton();
        jRadioButton_In = new javax.swing.JRadioButton();
        jTextField_Width = new javax.swing.JTextField();
        jTextField_Height = new javax.swing.JTextField();
        jTextField_Dicstance = new javax.swing.JTextField();
        jButton_Refresh = new javax.swing.JButton();
        jButton_Clean = new javax.swing.JButton();
        jTextField_Name = new javax.swing.JTextField();
        jButton_LoadMap = new javax.swing.JButton();
        jRadioButton_StartPoints = new javax.swing.JRadioButton();

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
            .addGap(0, 1320, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
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

        jButton_LastPoint.setText("Finish Line");
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

        buttonGroup_Work.add(jRadioButton_Start);
        jRadioButton_Start.setSelected(true);
        jRadioButton_Start.setText("Start Line");
        jRadioButton_Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonGroup_ActionPerformed_State(evt);
            }
        });

        buttonGroup_Work.add(jRadioButton_Controll);
        jRadioButton_Controll.setText("Controll Line");
        jRadioButton_Controll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonGroup_ActionPerformed_State(evt);
            }
        });

        buttonGroup_Work.add(jRadioButton_Out);
        jRadioButton_Out.setText("Out Line");
        jRadioButton_Out.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonGroup_ActionPerformed_State(evt);
            }
        });

        buttonGroup_Work.add(jRadioButton_In);
        jRadioButton_In.setText("Inner Line");
        jRadioButton_In.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonGroup_ActionPerformed_State(evt);
            }
        });

        jTextField_Width.setText("1200");
        jTextField_Width.setBorder(javax.swing.BorderFactory.createTitledBorder("Width"));

        jTextField_Height.setText("800");
        jTextField_Height.setBorder(javax.swing.BorderFactory.createTitledBorder("Height"));

        jTextField_Dicstance.setText("12");
        jTextField_Dicstance.setBorder(javax.swing.BorderFactory.createTitledBorder("Distance"));

        jButton_Refresh.setText("Refresh");
        jButton_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RefreshActionPerformed(evt);
            }
        });

        jButton_Clean.setText("Delete State");
        jButton_Clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CleanActionPerformed(evt);
            }
        });

        jTextField_Name.setText("RaceTrack");
        jTextField_Name.setBorder(javax.swing.BorderFactory.createTitledBorder("Name"));

        jButton_LoadMap.setText("Load Map");
        jButton_LoadMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LoadMapActionPerformed(evt);
            }
        });

        buttonGroup_Work.add(jRadioButton_StartPoints);
        jRadioButton_StartPoints.setText("Start Points");
        jRadioButton_StartPoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonGroup_ActionPerformed_State(evt);
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
                        .addComponent(label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(label_ToDo, javax.swing.GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE)
                        .addGap(90, 90, 90))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_Width, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField_Name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                                    .addComponent(jTextField_Height, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField_Dicstance, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jRadioButton_Start, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jButton_LoadMap, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_SaveMap, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_LastPoint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton_Clean, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_Undo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_Out, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_In, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_Controll, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_StartPoints, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton_Clean, jButton_LastPoint, jButton_LoadMap, jButton_Refresh, jButton_SaveMap, jButton_Undo, jRadioButton_Controll, jRadioButton_In, jRadioButton_Out, jRadioButton_Start, jRadioButton_StartPoints, jTextField_Dicstance, jTextField_Height, jTextField_Name, jTextField_Width});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_ToDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jTextField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Width, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Height, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Dicstance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton_Start)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton_Out)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton_In)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton_Controll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton_StartPoints)
                .addGap(21, 21, 21)
                .addComponent(jButton_Refresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Undo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Clean)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_LastPoint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_SaveMap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_LoadMap)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton_Clean, jButton_LastPoint, jButton_LoadMap, jButton_Refresh, jButton_SaveMap, jButton_Undo, jRadioButton_Controll, jRadioButton_In, jRadioButton_Out, jRadioButton_Start, jRadioButton_StartPoints, jTextField_Dicstance, jTextField_Height, jTextField_Name, jTextField_Width});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Actions">
    private void calcMousePoint(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calcMousePoint

        //if (evt.getClickCount() == 2) {
        int x = evt.getX();
        int y = evt.getY();
        if (!gameRect.contains(x, y)) {
            return;
        }
        addPoint(new Point(x - gameRect.x, y - gameRect.y));
        checkState1();
        checkState2();
        checkState5();
        setToDoText();
        repaint();
        //}
    }//GEN-LAST:event_calcMousePoint

    private void jButton_LastPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LastPointActionPerformed
        // TODO add your handling code here:
        setLastPoint = true;
        checkState1();
        checkState2();
        switch (state) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                checkState3();
                break;
            case 4:
                checkState4();
                break;
            case 5:
                break;
            default:
                JOptionPane.showMessageDialog(this, "Something went wrong.");
        }
        setToDoText();
        repaint();
    }//GEN-LAST:event_jButton_LastPointActionPerformed

    private void jButton_UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_UndoActionPerformed
        // TODO add your handling code here:
        useUNDO();
        setToDoText();
        this.repaint();
    }//GEN-LAST:event_jButton_UndoActionPerformed

    private void jButton_SaveMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SaveMapActionPerformed
        // TODO add your handling code here:
        if (checkReadyToSave()) {
            setValues();
            JOptionPane.showMessageDialog(this, data.dataToString());
            data.exportFile();
            this.dispose();
            SimulatorFrame.getInstance().setRaceTrackToUpload(data);
        } else {
            JOptionPane.showMessageDialog(this, "Something went wrong.");
        }
    }//GEN-LAST:event_jButton_SaveMapActionPerformed

    private void jButton_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RefreshActionPerformed
        checkState1();
        checkState2();
        checkState3();
        checkState4();
        checkState5();
        setToDoText();
        setValues();
        this.repaint();
    }//GEN-LAST:event_jButton_RefreshActionPerformed

    private void jRadioButtonGroup_ActionPerformed_State(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonGroup_ActionPerformed_State
        if (jRadioButton_Start.isSelected()) {
            state = 1;
        } else if (jRadioButton_Controll.isSelected()) {
            state = 2;
        } else if (jRadioButton_Out.isSelected()) {
            state = 3;
        } else if (jRadioButton_In.isSelected()) {
            state = 4;
        } else if (jRadioButton_StartPoints.isSelected()) {
            state = 5;
        } else {
            JOptionPane.showMessageDialog(this, "Something went wrong.");
        }
        setToDoText();
    }//GEN-LAST:event_jRadioButtonGroup_ActionPerformed_State

    private void jButton_CleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CleanActionPerformed
        deleteState();
        setToDoText();
        this.repaint();
    }//GEN-LAST:event_jButton_CleanActionPerformed

    private void jButton_LoadMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LoadMapActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Load CSV-File", "csv"));
        int showOpenDialog = fileChooser.showOpenDialog(SimulatorFrame.getInstance());

        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            File input = fileChooser.getSelectedFile();
            this.data = new RaceTrack(input);
            jTextField_Name.setText(data.getName());
            jTextField_Width.setText("" + data.getWidthField());
            jTextField_Height.setText("" + data.getHeightField());
            jTextField_Dicstance.setText("" + data.getDistance());
            jButton_RefreshActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(SimulatorFrame.getInstance(), "No Selection");
        }
    }//GEN-LAST:event_jButton_LoadMapActionPerformed

    // </editor-fold> 
    
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
                new CreationFrame();

            }
        }
        );
    }

    public void windowClosing(java.awt.event.WindowEvent e) {
        this.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_Work;
    private javax.swing.JButton jButton_Clean;
    private javax.swing.JButton jButton_LastPoint;
    private javax.swing.JButton jButton_LoadMap;
    private javax.swing.JButton jButton_Refresh;
    private javax.swing.JButton jButton_SaveMap;
    private javax.swing.JButton jButton_Undo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton_Controll;
    private javax.swing.JRadioButton jRadioButton_In;
    private javax.swing.JRadioButton jRadioButton_Out;
    private javax.swing.JRadioButton jRadioButton_Start;
    private javax.swing.JRadioButton jRadioButton_StartPoints;
    private javax.swing.JTextField jTextField_Dicstance;
    private javax.swing.JTextField jTextField_Height;
    private javax.swing.JTextField jTextField_Name;
    private javax.swing.JTextField jTextField_Width;
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
            drawAllPoints(g2d);
            //<editor-fold defaultstate="collapsed" desc=" State 1 - StartLine ">
            //State 1 ======================================================================
            if (state1finished) {
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(5.0f));
                g2d.drawLine(gameRect.x + data.getCoordStart().get(0).x, gameRect.y + data.getCoordStart().get(0).y,
                        gameRect.x + data.getCoordStart().get(1).x, gameRect.y + data.getCoordStart().get(1).y);
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc=" State 2 - ControlLine ">
            //State 2 ======================================================================
            if (state2finished) {
                g2d.setColor(Color.DARK_GRAY);
                g2d.setStroke(new BasicStroke(5.0f));
                g2d.drawLine(gameRect.x + data.getCoordControl().get(0).x, gameRect.y + data.getCoordControl().get(0).y,
                        gameRect.x + data.getCoordControl().get(1).x, gameRect.y + data.getCoordControl().get(1).y);

            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc=" State 3 - Outline ">
            //State 3 ======================================================================
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1.0f));
            GeneralPath pathFormOuter = new GeneralPath(0);
            if (!data.getCoordOuter().isEmpty()) {
                //start outer path
                pathFormOuter.moveTo(gameRect.x + data.getCoordOuter().get(0).x, gameRect.y + data.getCoordOuter().get(0).y);

                //draw outer path
                for (int i = 1; i < data.getCoordOuter().size() - 1; i += 2) {
                    pathFormOuter.quadTo(gameRect.x + data.getCoordOuter().get(i).x, gameRect.y + data.getCoordOuter().get(i).y,
                            gameRect.x + data.getCoordOuter().get(i + 1).x, gameRect.y + data.getCoordOuter().get(i + 1).y);
                }

                if (state3finished) {
                    //close outer path
                    pathFormOuter.quadTo(gameRect.x + data.getCoordOuter().get(data.getCoordOuter().size() - 1).x, gameRect.y + data.getCoordOuter().get(data.getCoordOuter().size() - 1).y,
                            gameRect.x + data.getCoordOuter().get(0).x, gameRect.y + data.getCoordOuter().get(0).y);
                    pathFormOuter.closePath();
                    g2d.draw(pathFormOuter);
                } else {
                    g2d.draw(pathFormOuter);
                }

            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc=" State 4 - Inline ">
            //State 4 ======================================================================
            GeneralPath pathFormInner = new GeneralPath(0);
            if (!data.getCoordInner().isEmpty()) {
                //start inner path
                pathFormInner.moveTo(gameRect.x + data.getCoordInner().get(0).x, gameRect.y + data.getCoordInner().get(0).y);

                //draw inner path
                for (int i = 1; i < data.getCoordInner().size() - 1; i += 2) {
                    pathFormInner.quadTo(gameRect.x + data.getCoordInner().get(i).x, gameRect.y + data.getCoordInner().get(i).y,
                            gameRect.x + data.getCoordInner().get(i + 1).x, gameRect.y + data.getCoordInner().get(i + 1).y);
                }

                if (state4finished) {
                    //close inner path
                    pathFormInner.quadTo(gameRect.x + data.getCoordInner().get(data.getCoordInner().size() - 1).x, gameRect.y + data.getCoordInner().get(data.getCoordInner().size() - 1).y,
                            gameRect.x + data.getCoordInner().get(0).x, gameRect.y + data.getCoordInner().get(0).y);
                    pathFormInner.closePath();
                    g2d.draw(pathFormInner);
                } else {
                    g2d.draw(pathFormInner);
                }

            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc=" After State 4 (State 5) - Rectangles ">
            //Finished  1 - 2 - 3 - 4 ======================================================================
            if (state1finished && state2finished && state3finished && state4finished) {
                g2d.setColor(Color.RED);
                data.setValidPoints(new ArrayList<Point>());
                for (int x = gameRect.x + data.getGapSize(); x <= gameRect.x + gameRect.width - data.getGridSize(); x += data.getGridSize() + data.getGapSize()) {
                    for (int y = gameRect.y + data.getGapSize(); y <= gameRect.y + gameRect.height - data.getGridSize(); y += data.getGridSize() + data.getGapSize()) {
                        if (!pathFormOuter.contains(x + data.getGridSize() / 2, y + data.getGridSize() / 2) || pathFormInner.contains(x + data.getGridSize() / 2, y + data.getGridSize() / 2)) {
                            continue;
                        }
                        data.getValidPoints().add(new Point(x - gameRect.x, y - gameRect.y));
                        g2d.fillRect(x, y, data.getGridSize(), data.getGridSize());
                    }
                }
                //<editor-fold defaultstate="collapsed" desc=" State 5 - StartPoints ">
                //State 5 ======================================================================
                g2d.setColor(Color.YELLOW);
                for (Point startPoint : data.getStartPoints()) {
                    g2d.fillOval(gameRect.x + startPoint.x, gameRect.y + startPoint.y, data.getGridSize(), data.getGridSize());
                }
                //</editor-fold>
            }
            //</editor-fold>
        }

    }

    private void drawAllPoints(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        if (!data.getCoordStart().isEmpty()) {
            drawArrayPoints(g2d, data.getCoordStart());
        }
        if (!data.getCoordControl().isEmpty()) {
            drawArrayPoints(g2d, data.getCoordControl());
        }
        if (!data.getCoordOuter().isEmpty()) {
            drawArrayPoints(g2d, data.getCoordOuter());
        }
        if (!data.getCoordInner().isEmpty()) {
            drawArrayPoints(g2d, data.getCoordInner());
        }
    }

    private void drawArrayPoints(Graphics2D g2d, ArrayList<Point> list) {
        list.stream().forEach(point -> g2d.fillOval(point.x - 2 + gameRect.x, point.y + gameRect.y - 2, 4, 4));
    }
}

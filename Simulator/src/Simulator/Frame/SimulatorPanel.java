/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator.Frame;

import simulator.data.container.RaceTrack;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author 82stfe1bif
 * 
 * TODO: !!!!!!!!!!!!!!!
 * playerDatabase needs to get painted
 */
public class SimulatorPanel extends JPanel {
    
    private RaceTrack data;
    private BufferedImage image;
    private int repaintCount = 0;

    public SimulatorPanel() {
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        Graphics2D g2d = (Graphics2D) gc;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Size Screen
        Rectangle screenRect = this.getBounds();

        if ((data = SimulatorFrame.getInstance().getRaceTrackToPlay()) == null) {
            SimulatorFrame.getInstance().consoleModel.addElement("Simulator Panel has no data.");
            try {
                image = ImageIO.read(new File("./Titelbild.PNG"));
                gc.drawImage(image, 0, 0, screenRect.width, screenRect.height, this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }
        repaintCount++;
        //Size Game
        Rectangle gameRect = new Rectangle(screenRect.width / 2 - data.getWidthField() / 2, 0, data.getWidthField(), data.getHeightField());
        //Background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(gameRect.x, gameRect.y, gameRect.width, gameRect.height);

        //Line Outer
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1.0f));

        ArrayList<Point> pointsOuter = data.getCoordOuter();
        GeneralPath pathFormOuter = new GeneralPath(0);
        pathFormOuter.moveTo(gameRect.x + pointsOuter.get(0).x, gameRect.y + pointsOuter.get(0).y);
        for (int i = 1; i < data.getPointsOutter() - 1; i += 2) {
            pathFormOuter.quadTo(gameRect.x + pointsOuter.get(i).x, gameRect.y + pointsOuter.get(i).y,
                    gameRect.x + pointsOuter.get(i + 1).x, gameRect.y + pointsOuter.get(i + 1).y);
        }
        pathFormOuter.quadTo(gameRect.x + pointsOuter.get(data.getPointsOutter() - 1).x, gameRect.y + pointsOuter.get(data.getPointsOutter() - 1).y,
                gameRect.x + pointsOuter.get(0).x, gameRect.y + pointsOuter.get(0).y);
        pathFormOuter.closePath();
        g2d.draw(pathFormOuter);

        //Line Inner
        ArrayList<Point> pointsInner = data.getCoordInner();
        GeneralPath pathFormInner = new GeneralPath(0);
        pathFormInner.moveTo(gameRect.x + pointsInner.get(0).x, gameRect.y + pointsInner.get(0).y);
        for (int j = 1; j < data.getPointsInner() - 1; j += 2) {
            pathFormInner.quadTo(gameRect.x + pointsInner.get(j).x, gameRect.y + pointsInner.get(j).y,
                    gameRect.x + pointsInner.get(j + 1).x, gameRect.y + pointsInner.get(j + 1).y);
        }
        pathFormInner.quadTo(gameRect.x + pointsInner.get(data.getPointsInner() - 1).x, gameRect.y + pointsInner.get(data.getPointsInner() - 1).y,
                gameRect.x + pointsInner.get(0).x, gameRect.y + pointsInner.get(0).y);
        pathFormInner.closePath();
        g2d.draw(pathFormInner);

        //Map rectangles
        g2d.setColor(Color.RED);
        for (int x = gameRect.x + data.getGapSize(); x <= gameRect.x + gameRect.width - data.getGridSize(); x += data.getGridSize() + data.getGapSize()) {
            for (int y = gameRect.y + data.getGapSize(); y <= gameRect.y + gameRect.height - data.getGridSize(); y += data.getGridSize() + data.getGapSize()) {
                if (!pathFormOuter.contains(x + data.getGridSize() / 2, y + data.getGridSize() / 2) || pathFormInner.contains(x + data.getGridSize() / 2, y + data.getGridSize() / 2)) {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fillRect(x, y, data.getGridSize(), data.getGridSize());
                    continue;
                }
                g2d.setColor(Color.RED);
                g2d.fillRect(x, y, data.getGridSize(), data.getGridSize());
            }
        }
        //paint start
        ArrayList<Point> pointsStart = data.getCoordStart();
        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(5.0f));
        g2d.drawLine(gameRect.x + pointsStart.get(0).x, gameRect.y + pointsStart.get(0).y,
                gameRect.x + pointsStart.get(1).x, gameRect.y + pointsStart.get(1).y);
        SimulatorFrame.getInstance().consoleModel.addElement("Simulator Panel inizalized.");
        //
            /*
            while (localStartPoints.size() < 5) {
                distance = distance + data.getDistance() + (data.getDistance() / 2);
                int size = localStartPoints.size();
                loop:
                for (int i = 0; i < 5 - size; i++) {
                    for (Point point : localValidPoints) {
                        if (Math.abs(point.x - midStart.x) <= distance || Math.abs(point.y - midStart.y) <= distance || Math.abs(midStart.x - point.x) <= distance) {
                            if (point.y >= midStart.y || point.x <= pointsStart.get(1).x) {
                                localValidPoints.remove(point);
                                localStartPoints.add(point);
                                continue loop;
                            }
                        }
                    }
                }
            }
            */
            //save the start points the first time the map gets loaded
        }
        
        /*
        for (int i = 0; i < SimulatorFrame.getInstance().activPlayer; i++) {
            if (SimulatorFrame.getInstance().playerDatabase.playerlist.get(i).getActiv() == true) {
                g2d.setColor(SimulatorFrame.getInstance().playerDatabase.playerlist.get(i).getColor());
                Point playerPosition = SimulatorFrame.getInstance().playerDatabase.playerlist.get(i).getPosition();
                g2d.fillOval(playerPosition.x, playerPosition.y, data.getGridSize(), data.getGridSize());
            }

        }
        */

    

}

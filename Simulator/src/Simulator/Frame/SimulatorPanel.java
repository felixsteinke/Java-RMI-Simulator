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
import simulator.data.container.Player;
import simulator.data.container.Turn;

/**
 *
 * @author 82stfe1bif
 *
 * TODO: !!!!!!!!!!!!!!! playerDatabase needs to get painted
 */
public class SimulatorPanel extends JPanel {

    private RaceTrack data;
    private BufferedImage image;
    private Rectangle gameRect;

    public SimulatorPanel() {
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        Graphics2D g2d = (Graphics2D) gc;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Size Screen
        Rectangle screenRect = this.getBounds();

        //<editor-fold defaultstate="collapsed" desc=" Startpicture ">
        if ((data = SimulatorFrame.getInstance().getRaceTrackToPlay()) == null) {
            try {
                image = ImageIO.read(new File("./Titelbild.PNG"));
                gc.drawImage(image, 0, 0, screenRect.width, screenRect.height, this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }
        //</editor-fold>

        //Size Game
        gameRect = new Rectangle(screenRect.width / 2 - data.getWidthField() / 2, 0, data.getWidthField(), data.getHeightField());

        //Background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(gameRect.x, gameRect.y, gameRect.width, gameRect.height);

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1.0f));

        //<editor-fold defaultstate="collapsed" desc=" Outline ">
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
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Inline ">
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
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Rectangles ">
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
        //</editor-fold>

        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(5.0f));

        //<editor-fold defaultstate="collapsed" desc=" Startline ">
        ArrayList<Point> pointsStart = data.getCoordStart();
        g2d.drawLine(gameRect.x + pointsStart.get(0).x, gameRect.y + pointsStart.get(0).y,
                gameRect.x + pointsStart.get(1).x, gameRect.y + pointsStart.get(1).y);
        //</editor-fold>

        paintPlayerData(g2d);

    }

    private void paintPlayerData(Graphics2D g2d) {
        if (SimulatorFrame.getInstance().playerDatabase.playerlist != null && !SimulatorFrame.getInstance().playerDatabase.playerlist.isEmpty()) {
            for (Player player : SimulatorFrame.getInstance().playerDatabase.playerlist) {
                paintPlayer(g2d, player);
            }
        }
    }

    private void paintPlayer(Graphics2D g2d, Player player) {
        g2d.setColor(player.getColor());
        g2d.setStroke(new BasicStroke(2.0f));
        //Points definition
        Point startPosition = new Point(player.getStartPosition().x + gameRect.x, player.getStartPosition().y + gameRect.y);
        Point position = new Point(player.getPosition().x + gameRect.x, player.getPosition().y + gameRect.y);
        
        //paint Points
        g2d.fillRect(startPosition.x, startPosition.y, data.getGridSize(), data.getGridSize());
        g2d.fillOval(position.x, position.y, data.getGridSize(), data.getGridSize());
        
        //paint your next Turn
        if (player.getName().equalsIgnoreCase(SimulatorFrame.getInstance().player.getName())) {
            Turn lastTurn = player.getTurns().get(player.getTurns().size() - 1);
            Point nextPosition = new Point (position.x + lastTurn.turnVektor.x, position.y + lastTurn.turnVektor.y);
            g2d.drawLine(position.x + data.getGridSize()/2, position.y + data.getGridSize()/2, nextPosition.x + data.getGridSize()/2, nextPosition.y + data.getGridSize()/2);
        }
    }

}

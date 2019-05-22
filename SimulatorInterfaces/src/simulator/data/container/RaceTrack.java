/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.data.container;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 82stfe1bif
 * 
 * !!!!!!!!!!!!!!!!new start line can produce some issues!!!!!!!!!!!!!!!!
 */
public class RaceTrack implements Serializable{
    
    transient private File input;
    private String name;
    private int widthField;
    private int heightField;
    private int pointsOutter;
    private ArrayList <Point> coordOuter;
    private int pointsInner;
    private ArrayList <Point> coordInner;
    private int distance;
    private ArrayList <Point> coordStart;
    private ArrayList <Point> coordControl;
    private int gridSize;
    private int gapSize;
    private ArrayList <Point> validPoints;
    private ArrayList <Point> startPoints;
    
    public RaceTrack(File input) {
        this.input = input;
        this.name = input.getName();
        decode();
        this.validPoints = new ArrayList <Point> ();
        this.startPoints = new ArrayList <Point> ();
    }
    
    public RaceTrack() {
        this.coordInner = new ArrayList <Point> ();
        this.coordOuter = new ArrayList <Point> ();
        this.coordStart = new ArrayList <Point> ();
        this.coordControl = new ArrayList <Point> ();
        this.validPoints = new ArrayList <Point> ();
        this.startPoints = new ArrayList <Point> ();
    }
    
    public void exportFile(){
        try{
            PrintWriter writer = new PrintWriter(new File(name + ".csv"));
            writer.write(dataToString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RaceTrack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String dataToString() {
        this.pointsOutter = coordOuter.size();
        this.pointsInner = coordInner.size();
        //Build Format String
        StringBuilder csvString = new StringBuilder();
        csvString.append(widthField + "," + heightField + "\n");
        csvString.append(pointsOutter + "\n");
        csvString.append(pointsArraytoString(coordOuter) + "\n");
        csvString.append(pointsInner + "\n");
        csvString.append(pointsArraytoString(coordInner) + "\n");
        csvString.append(distance + "\n");
        csvString.append(pointsArraytoString(coordStart) + "\n");
        csvString.append(pointsArraytoString(coordControl));
        return csvString.toString();
    }
    
    private String pointsArraytoString(ArrayList<Point> list) {
        StringBuilder arrayString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            arrayString.append(list.get(i).x + "," + list.get(i).y);
            if (i < list.size() - 1) {
                arrayString.append(",");
            }
        }
        return arrayString.toString();
    }
    
    private void decode (){  
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            ArrayList <String> lines = new ArrayList <String> ();
            String line;
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
            String [] splitLine = lines.get(0).split(",");
            widthField = Integer.valueOf(splitLine[0]);
            heightField = Integer.valueOf(splitLine[1]);
            pointsOutter = Integer.valueOf(lines.get(1));
            coordOuter = createPointArray(lines.get(2),pointsOutter);
            pointsInner = Integer.valueOf(lines.get(3));
            coordInner = createPointArray(lines.get(4),pointsInner);
            distance = Integer.valueOf(lines.get(5));
            gridSize = (distance/3)*2;
            gapSize = (distance/3);
            coordStart = createPointArray(lines.get(6),2);
            coordControl = createPointArray(lines.get(7),2);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RaceTrack.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RaceTrack.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private ArrayList <Point> createPointArray (String line, int length){
        ArrayList <Point> points = new ArrayList<Point>();
        String [] split = line.split(",");
        for(int i = 0; i< (length*2); i+=2){
            points.add(createPoint(split[i],split[i+1]));
        }
        return points;
    }
    
    private Point createPoint (String x, String y){
        Point point = new Point();
        point.x = Integer.valueOf(x);
        point.y = Integer.valueOf(y);
        return point;
    }
    
    
    public int getWidthField() {
        return widthField;
    }

    public int getHeightField() {
        return heightField;
    }

    public int getPointsOutter() {
        return pointsOutter;
    }

    public ArrayList <Point> getCoordOuter() {
        return coordOuter;
    }

    public int getPointsInner() {
        return pointsInner;
    }

    public ArrayList <Point> getCoordInner() {
        return coordInner;
    }

    public int getGridSize() {
        return gridSize;
    }

    public ArrayList <Point> getCoordStart() {
        return coordStart;
    }

    public int getGapSize() {
        return gapSize;
    }

    public File getInput() {
        return input;
    }

    public void setInput(File input) {
        this.input = input;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidthField(int widthField) {
        this.widthField = widthField;
    }

    public void setHeightField(int heightField) {
        this.heightField = heightField;
    }

    public void setPointsOutter(int pointsOutter) {
        this.pointsOutter = pointsOutter;
    }

    public void setCoordOuter(ArrayList<Point> coordOuter) {
        this.coordOuter = coordOuter;
    }

    public void setPointsInner(int pointsInner) {
        this.pointsInner = pointsInner;
    }

    public void setCoordInner(ArrayList<Point> coordInner) {
        this.coordInner = coordInner;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void setCoordStart(ArrayList<Point> coordStart) {
        this.coordStart = coordStart;
    }

    public void setGapSize(int gapSize) {
        this.gapSize = gapSize;
    }

    public ArrayList<Point> getValidPoints() {
        return validPoints;
    }

    public void setValidPoints(ArrayList<Point> validPoints) {
        this.validPoints = validPoints;
    }

    public ArrayList<Point> getStartPoints() {
        return startPoints;
    }

    public void setStartPoints(ArrayList<Point> startPoints) {
        this.startPoints = startPoints;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public ArrayList<Point> getCoordControl() {
        return coordControl;
    }

    public void setCoordControl(ArrayList<Point> coordControl) {
        this.coordControl = coordControl;
    }
    
    
}

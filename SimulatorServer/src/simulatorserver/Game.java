/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import simulator.data.container.PlayerDatabase;
import simulator.data.container.RaceTrack;


/**
 *
 * @author Felix
 * 
 * TODO: !!!!!!!!!!!!!!!!!!!!!!!
 * the refresh game Method needs to get implemented
 * the start of the game needs to get implemented
 */
public class Game {
    
    private String name;                //beliebig
    private int gameSize;               //theoretisch beliebig, soll aber <=5 sein
    private String code;                //passwort
    private RaceTrack raceTrack;        //soll im nachhinen festgelegt werden
    public PlayerDatabase playerData;   //spieler müssen vom nutzer hinzugefügt werden
    public ExecutorService executorService;

    //Dieser Konsturktor wird verwendet
    public Game(String name, int gameSize, String code) {
        this.name = name;
        this.gameSize = gameSize;
        this.code = code;
        this.raceTrack = null;
        this.playerData = new PlayerDatabase();
        this.executorService = Executors.newFixedThreadPool(gameSize);
    }
    
    //Rest von altem Ansatz
    /*
    public Game(String name, int count, String code, RaceTrack raceTrack) {
        this.name = name;
        this.playerData = new PlayerDatabase(count);
        this.code = code;
        this.raceTrack = raceTrack;
        executorService = Executors.newFixedThreadPool(count);
    }
    */
    public void refreshPlayerDatabase (){
        /*
        !!!!MISSING!!!!
        Hier muss die ganze Spiel Logik rein

        
        muss player.canDoMove weiter schieben
        muss player.isActiv prüfen, mit neuer Position compare mit ValidPoints von Racetrack
        muss antiCheat prüfen player mit Turn muss OldTurns mit ClientOldTurns prüfen
        bei allen spielern auf send Turn warten, wenn alle ihren Turn gesendet haben, verarbeiten
        bei positionskonflikt, wer mehr speed in seinem turn hatte, gewinnt die position -> der andere muss
        seinen Turn wiederholen
        wer nach zeit x keinen move gemacht hat wird inactiv und hat verloren
        */
    }
    
    public RaceTrack getRaceTrack() {
        return raceTrack;
    }

    public void setRaceTrack(RaceTrack raceTrack) {
        this.raceTrack = raceTrack;
    }

    public PlayerDatabase getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerDatabase playerData) {
        this.playerData = playerData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getGameSize() {
        return gameSize;
    }

    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }
    
    
    
}

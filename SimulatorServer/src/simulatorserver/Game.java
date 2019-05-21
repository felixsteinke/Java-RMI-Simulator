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
 */
public class Game {
    
    private String name;
    private RaceTrack raceTrack;
    public PlayerDatabase playerData;
    private String code;
    ExecutorService executorService;

    public Game(String name, int count, String code) {
        this.name = name;
        this.playerData = new PlayerDatabase(count);
        this.code = code;
    }
    
    public Game(String name, int count, String code, RaceTrack raceTrack) {
        this.name = name;
        this.playerData = new PlayerDatabase(count);
        this.code = code;
        this.raceTrack = raceTrack;
        executorService = Executors.newSingleThreadExecutor();
    }

    public void refreshPlayerDatabase (){
        
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
    
    
    
}

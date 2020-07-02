/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Isaia
 */
public class Round {
    private int roundId;
    private String guess;
    private Timestamp timeOfGuess = Timestamp.valueOf(LocalDateTime.now());
    private String result;
    private int gameId;

    
    public Round(){
        
    }
    
    // this constructor is used exclusively in tests
    public Round(String guess, String result, int gameId){
        this.guess = guess;
        this.result = result;
        this.gameId = gameId;
    }
    /**
     * @return the roundId
     */
    public int getRoundId() {
        return roundId;
    }

    /**
     * @param roundId the roundId to set
     */
    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    /**
     * @return the guess
     */
    public String getGuess() {
        return guess;
    }

    /**
     * @param guess the guess to set
     */
    public void setGuess(String guess) {
        this.guess = guess;
    }

    /**
     * @return the timeOfGuess
     */
    public Timestamp getTimeOfGuess() {
        return timeOfGuess;
    }

    /**
     * @param timeOfGuess the timeOfGuess to set
     */
    public void setTimeOfGuess(Timestamp timeOfGuess) {
        this.timeOfGuess = timeOfGuess;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return the gameId
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    
    
}

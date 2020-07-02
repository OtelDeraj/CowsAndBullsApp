/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Isaia
 */
public class Guess {
    
    @Size(min=4, max=4, message="guess must be exactly 4 characters long.")
    @Pattern(regexp="[0-9]+", message="guess must contain only numbers.")
    private String guessString;
    
    private int gameId;
    
    //for testing purposes
    public Guess(String guess, int id){
        this.guessString = guess;
        this.gameId = id;
    }

    public Guess() {
        
    }

    /**
     * @return the guessString
     */
    public String getGuessString() {
        return guessString;
    }

    /**
     * @param guessString the guessString to set
     */
    public void setGuessString(String guessString) {
        this.guessString = guessString;
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

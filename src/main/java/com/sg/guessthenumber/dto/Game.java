/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dto;

/**
 *
 * @author Isaia
 */
public class Game {
    private int gameId;
    private String answer;
    private boolean gameOver = false;
    
    public Game(){
        
    }
    
    public Game(String answer, boolean gameOver){
        this.answer = answer;
        this.gameOver = gameOver;
    }
    
    // this is for tests
    public Game(int gameId, String answer, boolean gameOver){
        this.gameId = gameId;
        this.answer = answer;
        this.gameOver = gameOver;
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

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the gameOver
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * @param gameOver the gameOver to set
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

}

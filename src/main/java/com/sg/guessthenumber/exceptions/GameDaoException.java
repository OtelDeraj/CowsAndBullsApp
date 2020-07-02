/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.exceptions;

/**
 *
 * @author Isaia
 */
public class GameDaoException extends Exception {
    
    public GameDaoException(String message){
        super(message);
    }
    
    public GameDaoException(String message, Throwable cause){
        super(message, cause);
    }
}

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
public class EmptyResultSetException extends Exception {
    
    public EmptyResultSetException(String message){
        super(message);
    }
    
    public EmptyResultSetException(String message, Throwable cause){
        super(message, cause);
    }
    
}

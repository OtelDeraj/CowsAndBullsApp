/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.handlers;

import com.sg.guessthenumber.exceptions.BadUpdateException;
import com.sg.guessthenumber.exceptions.EmptyResultSetException;
import com.sg.guessthenumber.exceptions.GameDaoException;
import com.sg.guessthenumber.exceptions.InvalidInputException;
import com.sg.guessthenumber.exceptions.RoundDaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Isaia
 */
@RestControllerAdvice
public class GTNExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(BadUpdateException.class)
    public final ResponseEntity<BadUpdateException> handleBadUpdateException(BadUpdateException ex){
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(EmptyResultSetException.class)
    public final ResponseEntity<EmptyResultSetException> handleBadUpdateException(EmptyResultSetException ex){
        return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(GameDaoException.class)
    public final ResponseEntity<GameDaoException> handleBadUpdateException(GameDaoException ex){
        return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidInputException.class)
    public final ResponseEntity<InvalidInputException> handleBadUpdateException(InvalidInputException ex){
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(RoundDaoException.class)
    public final ResponseEntity<RoundDaoException> handleBadUpdateException(RoundDaoException ex){
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
}

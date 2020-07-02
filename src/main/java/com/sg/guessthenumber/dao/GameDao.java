/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.exceptions.BadUpdateException;
import com.sg.guessthenumber.exceptions.GameDaoException;
import com.sg.guessthenumber.exceptions.InvalidInputException;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Isaia
 */
@Component
public interface GameDao {
    
    Game newGame(Game toStart) throws GameDaoException, InvalidInputException;
    
    List<Game> getAllGames() throws GameDaoException;
    
    Game getGameById(int id)throws GameDaoException;

    public void updateGameOver(int gameId) throws BadUpdateException;
    
    
}

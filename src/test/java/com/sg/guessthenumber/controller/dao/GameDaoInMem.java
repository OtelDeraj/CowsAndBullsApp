/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.controller.dao;

import com.sg.guessthenumber.dao.GameDao;
import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.exceptions.BadUpdateException;
import com.sg.guessthenumber.exceptions.GameDaoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("memory")
public class GameDaoInMem implements GameDao {
    
    List<Game> allGames = new ArrayList<>();

    @Override
    public Game newGame(Game toStart) throws GameDaoException {
        toStart.setGameId(allGames.size() + 1);
        allGames.add(toStart);
        return toStart;
    }

    @Override
    public List<Game> getAllGames() throws GameDaoException {
        return allGames;
    }

    @Override
    public Game getGameById(int id) throws GameDaoException {
        List<Game> toCheck = allGames.stream().filter(g -> g.getGameId() == id).collect(Collectors.toList());
        if(toCheck.size() == 0){
            throw new GameDaoException("There were no games with that ID");
        }
        return toCheck.get(0);
    }

    @Override
    public void updateGameOver(int gameId) throws BadUpdateException {
        boolean updated = false;
        for(Game g: allGames){
            if(g.getGameId() == gameId){
                g.setGameOver(true);
                updated = true;
            }
        }
        if(!updated){
            throw new BadUpdateException("Zero rows affected. Boo hoo.");
        }
    }
    
    public void setUp(){
        allGames.clear();
        Game game1 = new Game(1, "2357", false);
        Game game2 = new Game(2, "1234", true);
        
        allGames.add(game1);
        allGames.add(game2);
    }
    
}

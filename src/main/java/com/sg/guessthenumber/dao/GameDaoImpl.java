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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("database")
public class GameDaoImpl implements GameDao {

    @Autowired
    JdbcTemplate template;

    @Override
    @Transactional
    public Game newGame(Game toStart) throws GameDaoException, InvalidInputException {
        // checks for null objects and answers to prevent uploading to database
        if(toStart == null) throw new GameDaoException("New Game cannot be null.");
        if(toStart.getAnswer() == null) throw new InvalidInputException("Game answer cannot be null.");
        
        try{
        template.update("INSERT INTO Games(answer, gameOver) VALUES (?, ?)",
                toStart.getAnswer(), toStart.isGameOver());
        int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        toStart.setGameId(newId);
        return toStart;
        } catch(DataAccessException ex){
            throw new GameDaoException("Failed to reach database.");
        }
    }

    @Override
    public List<Game> getAllGames() throws GameDaoException {
        try{
        List<Game> allGames = template.query("SELECT * FROM Games", new GameMapper());
        if(allGames.size() == 0) throw new GameDaoException("No Games Found");
        return allGames;
        } catch(DataAccessException ex){
            throw new GameDaoException("SQL error has occured");
        }
    }

    @Override
    public Game getGameById(int id) throws GameDaoException {
        try{
        return template.queryForObject("SELECT * FROM Games WHERE gameId = ?", new GameMapper(), id);
        } catch (DataAccessException ex){
            throw new GameDaoException("No existing game for associated ID");
        }
    }

    @Override
    @Transactional
    public void updateGameOver(int gameId) throws BadUpdateException {
        
        int affectedRows = template.update("UPDATE Games SET gameOver = True WHERE gameId = ?", gameId);
        
        if(affectedRows < 1){
            throw new BadUpdateException("No rows affected by update.");
        }
        if(affectedRows > 1){
            throw new BadUpdateException("More than one row affected by update. Whoops");
        }
    }

    private static class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {

            Game toReturn = new Game();
            toReturn.setGameId(rs.getInt("gameId"));
            toReturn.setAnswer(rs.getString("answer"));
            toReturn.setGameOver(rs.getBoolean("gameOver"));
            
            return toReturn;
        }

    }

}

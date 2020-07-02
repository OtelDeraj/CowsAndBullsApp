/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.dto.Round;
import com.sg.guessthenumber.exceptions.RoundDaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("database")
public class RoundDaoImpl implements RoundDao{
    
    @Autowired
    JdbcTemplate template;

    @Override
    @Transactional
    public Round newRound(Round toAdd) throws RoundDaoException {
        try{
        template.update("INSERT INTO Rounds(guess, timeOfGuess, result, gameId) VALUES (?, ?, ?, ?)",
                toAdd.getGuess(), toAdd.getTimeOfGuess(), toAdd.getResult(), toAdd.getGameId());
        int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        toAdd.setRoundId(newId);
        return toAdd;
        } catch(DataAccessException ex){
            throw new RoundDaoException("Failed to reach database.");
        }
    }

    @Override
    public List<Round> getRoundsByGameId(int id) throws RoundDaoException {
        try{
        List<Round> allRounds = template.query("SELECT * FROM Rounds WHERE gameId = ? ORDER BY timeOfGuess DESC", new RoundMapper(), id);
        if(allRounds.size() == 0) throw new RoundDaoException("Found no rounds for that ID");
        return allRounds;
        } catch(DataAccessException ex){
            throw new RoundDaoException("SQL error", ex.getCause());
        }
    }
    
    private static class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            
            Round toReturn = new Round();
            toReturn.setRoundId(rs.getInt("roundId"));
            toReturn.setGuess(rs.getString("guess"));
            toReturn.setTimeOfGuess(rs.getTimestamp("timeOfGuess"));
            toReturn.setResult(rs.getString("result"));
            toReturn.setGameId(rs.getInt("gameId"));
            
            return toReturn;
        }
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.controller.dao;

import com.sg.guessthenumber.dao.RoundDao;
import com.sg.guessthenumber.dto.Round;
import com.sg.guessthenumber.exceptions.RoundDaoException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Isaia
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("database")
public class RoundDaoImplTest {

    @Autowired
    RoundDao rDao;

    @Autowired
    JdbcTemplate template;

    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Rounds");
        template.update("ALTER TABLE Rounds auto_increment = 1");
        template.update("INSERT INTO Rounds(guess, timeOfGuess, result, gameId) VALUES"
                + "('1234', '2020-07-16 12:34:20', 'p: 2 - e: 0', '1'), "
                + "('2531', '2020-07-16 12:35:02', 'p: 2 - e: 1', '1'), "
                + "('2375', '2020-07-16 12:35:35', 'p: 2 - e: 2', '1'), "
                + "('2357', '2020-07-16 12:36:12', 'p: 0 - e: 4', '1');");
    } // "p: " + partial + " - e: " + exact

    /**
     * Test of newRound method, of class RoundDaoImpl.
     */
    @Test
    public void testNewRoundGoldenPath() {
        try {
            Round toAdd = new Round("1234", "p: 2 - e: 0", 2);
            Round returned = rDao.newRound(toAdd);
            assertEquals(5, returned.getRoundId());
            assertEquals(toAdd.getGuess(), returned.getGuess());
            assertEquals(toAdd.getTimeOfGuess(), returned.getTimeOfGuess());
            assertEquals(toAdd.getResult(), returned.getResult());
            assertEquals(toAdd.getGameId(), returned.getGameId());
        } catch (RoundDaoException ex) {
            fail("Should not hit RoundDaoException in golden path.");
        }
    }
    
    @Test
    public void testNewRoundNoConnection() {
        try {
            Round toAdd = new Round("1234", "p: 2 - e: 0", 2);
            Round returned = rDao.newRound(toAdd);
            // still not sure how to test a failed connection. for now I am just leaving this here.
        } catch (RoundDaoException ex) {
            fail("Should not hit RoundDaoException in golden path.");
        }
    }

    /**
     * Test of getRoundsByGameId method, of class RoundDaoImpl.
     */
    @Test
    public void testGetRoundsByGameIdGoldenPath() {
        try {
            List<Round> allRounds = rDao.getRoundsByGameId(1);
            Round last = allRounds.get(0);
            Round first = allRounds.get(allRounds.size() - 1);
            assertEquals(4, allRounds.size());
            assertEquals(1, first.getRoundId());
            assertEquals("1234", first.getGuess());
            assertEquals(1, first.getGameId());
            assertEquals(Timestamp.valueOf("2020-07-16 12:34:20"), first.getTimeOfGuess());
            assertEquals("p: 2 - e: 0", first.getResult());
            assertEquals(4, last.getRoundId());
            assertEquals("2357", last.getGuess());
            assertEquals(1, last.getGameId());
            assertEquals(Timestamp.valueOf("2020-07-16 12:36:12"), last.getTimeOfGuess());
            assertEquals("p: 0 - e: 4", last.getResult());
        } catch (RoundDaoException ex) {
            fail("Should not hit RoundDaoException in golden path.");
        }
    }
    
    @Test
    public void testGetRoundsByGameIdBadId() {
        try {
            List<Round> allRounds = rDao.getRoundsByGameId(0);
            fail("Should have hit RoundDaoException.");
        } catch (RoundDaoException ex) {
            
        }
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.service;

import com.sg.guessthenumber.controller.dao.GameDaoInMem;
import com.sg.guessthenumber.controller.dao.RoundDaoInMem;
import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.dto.Guess;
import com.sg.guessthenumber.dto.Round;
import com.sg.guessthenumber.exceptions.BadUpdateException;
import com.sg.guessthenumber.exceptions.EmptyResultSetException;
import com.sg.guessthenumber.exceptions.GameDaoException;
import com.sg.guessthenumber.exceptions.InvalidInputException;
import com.sg.guessthenumber.exceptions.RoundDaoException;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// Notes From David: Flesh out the Service tests, need inMemDaos for tests to run proper
/**
 *
 * @author Isaia
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("memory")
public class GTNServiceTest {

    @Autowired
    GTNService service;
    @Autowired
    GameDaoInMem gdao;
    @Autowired
    RoundDaoInMem rdao;

    public GTNServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        gdao.setUp();
        rdao.setUp();

    }

    @AfterEach
    public void tearDown() {
    }

    
    /**
     * Test of checkGuess method, of class GTNService.
     */
    @Test
    public void testCheckGuessGoldenPath() {
        try {
            Guess toTest = new Guess("1234", 1);
            Round toCheck = service.checkGuess(toTest);

            assertEquals(toTest.getGameId(), toCheck.getGameId());
            assertEquals(toTest.getGuessString(), toCheck.getGuess());
            assertEquals("p: 2 - e: 0", toCheck.getResult());

        } catch (GameDaoException ex) {
            fail("Should not hit GameDaoException in golden path.");
        } catch (InvalidInputException ex) {
            fail("Should not hit InvalidInputException in golden path.");
        } catch (RoundDaoException ex) {
            fail("Should not hit RoundDaoException in golden path.");
        } catch (BadUpdateException ex) {
            fail("Should not hit BadUpdateException in golden path.");
        }
    }

    @Test
    public void testCheckGuessBadLength() throws GameDaoException, RoundDaoException, BadUpdateException  {
        try {
            Guess toTest = new Guess("124", 1);
            service.checkGuess(toTest);
            fail("Should have hit InvalidInputException.");
        } catch (InvalidInputException ex) {

        }
    }

    @Test
    public void testCheckGuessRepeats() throws GameDaoException, RoundDaoException, BadUpdateException {
        try {
            Guess toTest = new Guess("1224", 1);
            service.checkGuess(toTest);
            fail("Should have hit InvalidInputException.");
        } catch (InvalidInputException ex) {
            
        }
    }
    
    @Test
    public void testCheckGuessBadGameId() throws InvalidInputException, RoundDaoException, BadUpdateException {
        try {
            Guess toTest = new Guess("1234", -1);
            service.checkGuess(toTest);
            fail("Should have hit GameDaoException");
        } catch (GameDaoException ex) {
            
        }
    }

    /**
     * Test of getGameById method, of class GTNService.
     */
    @Test
    public void testGetGameByIdGameNotOver() {
        try {
            Game toCheck = service.getGameById(1);
            assertEquals(1, toCheck.getGameId());
            assertFalse(toCheck.isGameOver());
            assertEquals("****", toCheck.getAnswer());
        } catch (GameDaoException ex) {
            fail("Should not hit GameDaoException.");
        }
    }

    @Test
    public void testGetGameByIdGameOver() {
        try {
            Game toCheck = service.getGameById(2);
            assertEquals(2, toCheck.getGameId());
            assertTrue(toCheck.isGameOver());
            assertEquals("1234", toCheck.getAnswer());
        } catch (GameDaoException ex) {
            fail("Should not hit GameDaoException.");
        }
    }
    
    @Test
    public void testGetGameByIdBadId() {
        try {
            service.getGameById(-1);
            fail("Should have hit GameDaoException");
        } catch (GameDaoException ex) {
            
        }
        
    }

    /**
     * Test of getAllGames method, of class GTNService.
     */
    @Test
    public void testGetAllGames() {     // (1, "2357", false); first
        try {                           // (2, "1234", true); last
            List<Game> allGames = service.getAllGames();
            Game first = allGames.get(0);
            Game last = allGames.get(allGames.size() - 1);
            assertEquals(2, allGames.size());
            assertEquals(1, first.getGameId());
            assertEquals("****", first.getAnswer());
            assertFalse(first.isGameOver());
            assertEquals(2, last.getGameId());
            assertEquals("1234", last.getAnswer());
            assertTrue(last.isGameOver());

            for (Game g : allGames) {
                if (!g.isGameOver()) {
                    assertEquals("****", g.getAnswer());
                }
            }
        } catch (GameDaoException ex) {
            fail("Should not hit GameDaoException.");
        }
    }

    /**
     * Test of getRoundsForGameId method, of class GTNService.
     */
    @Test
    public void testGetRoundsForGameIdGoldenPath() {    // ("1234", "p: 2 - e: 0", 1); first 
        try {                                           // ("2531", "p: 2 - e: 1", 1); last
            List<Round> toCheck = service.getRoundsForGameId(1);
            Round first = toCheck.get(0);
            Round last = toCheck.get(toCheck.size() - 1);
            assertEquals(2, toCheck.size());
            assertEquals(1, first.getRoundId());
            assertEquals(1, first.getGameId());
            assertEquals("1234", first.getGuess());
            assertEquals("p: 2 - e: 0", first.getResult());
            assertEquals(2, last.getRoundId());
            assertEquals(1, last.getGameId());
            assertEquals("2531", last.getGuess());
            assertEquals("p: 2 - e: 1", last.getResult());
                    
        } catch (RoundDaoException ex) {
            fail("Should not hit RoundDaoException in golden path.");
        } catch (EmptyResultSetException ex) {
            fail("Should not hit EmptyResultSetException in golden path.");
        } catch (GameDaoException ex) {
            fail("Should not hit GameDaoException in golden path.");
        } catch (InvalidInputException ex) {
            fail("Should not hit InvalidInputException in golden path.");
        }
    }

    @Test
    public void testGetRoundsForGameIdInvalidId() throws RoundDaoException, EmptyResultSetException, GameDaoException {
        try {
            service.getRoundsForGameId(0);
            fail("Should have hit InvalidInputException.");
        } catch (InvalidInputException ex) {

        }
    }
    
    @Test
    public void testUpdateGameOverGoldenPath() throws GameDaoException {    // (1, "2357", false); first
        try {
            gdao.updateGameOver(1);
            Game toCheck = gdao.getGameById(1);
            assertEquals(1, toCheck.getGameId());
            assertEquals("2357", toCheck.getAnswer());
            assertTrue(toCheck.isGameOver());
        } catch (BadUpdateException ex) {
            fail("Should not hit BadUpdateException in golden path test.");
        }
    }
    
    @Test
    public void testUpdateGameOverBadId() {
        try {
            gdao.updateGameOver(-1);
            fail("Should hit BadUpdateException");
        } catch (BadUpdateException ex) {
            
        }
    }

}

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
import com.sg.guessthenumber.exceptions.InvalidInputException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
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
public class GameDaoImplTest {

    @Autowired
    GameDao gDao;

    @Autowired
    JdbcTemplate template;

    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Rounds");
        template.update("DELETE FROM Games");

        template.update("ALTER TABLE Games auto_increment = 1");

        template.update("INSERT INTO Games(answer, gameOver) VALUES"
                + "('1245', 0), ('5908', 0), ('8963', 0), ('1986', 0);");
    }

    /**
     * Test of newGame method, of class GameDaoImpl.
     */
    @Test
    public void testNewGameGoldenPath() {
        try {
            Game toAdd = new Game("3412", false);
            Game returned = gDao.newGame(toAdd);
            assertEquals(5, returned.getGameId());
            assertEquals(toAdd.getAnswer(), returned.getAnswer());
            assertEquals(toAdd.isGameOver(), returned.isGameOver());
        } catch (GameDaoException ex) {
            fail("Should not hit GameDaoException in golden path.");
        } catch (InvalidInputException ex) {
            fail("Should not hit InvalidInputException in golden path.");
        }
    }
    
    @Test
    public void testNewGameNullAnswer() throws GameDaoException {
        try {
            Game toAdd = new Game(null, false);
            Game returned = gDao.newGame(toAdd);
            fail("Should have hit InvalidInputException.");
        } catch (InvalidInputException ex) {
            
        }
    }
    
    @Test
    public void testNewGameNullGame() throws InvalidInputException {
        try {
            Game toAdd = null;
            Game returned = gDao.newGame(toAdd);
            fail("Should have hit GameDaoException.");
        } catch (GameDaoException ex) {
            
        }
    }

    /**
     * Test of getAllGames method, of class GameDaoImpl.
     */
    @Test
    public void testGetAllGamesGoldenPath() {
        try {
            List<Game> allGames = gDao.getAllGames();
            Game first = allGames.get(0);
            Game last = allGames.get(allGames.size() - 1);
            assertEquals(4, allGames.size());
            assertEquals(1, first.getGameId());
            assertEquals("1245", first.getAnswer());
            assertFalse(first.isGameOver());
            assertEquals(4, last.getGameId());
            assertEquals("1986", last.getAnswer());
            assertFalse(last.isGameOver());
        } catch (GameDaoException ex) {
            fail("Should not hit GameDaoException in golden path.");
        }
    }

    @Test
    public void testGetAllGamesEmptyReturn() {
        try { // clear out games before doing this code
            template.update("DELETE FROM Games"); // This should clear the pre test inserts for an empty list
            List<Game> allGames = gDao.getAllGames();
            fail("Should have hit GameDaoException.");
        } catch (GameDaoException ex) {

        }
    }

    /**
     * Test of getGameById method, of class GameDaoImpl.
     */
    @Test
    public void testGetGameByIdGoldenPath() {
        try {
            Game toTest = gDao.getGameById(1);
            assertEquals(1, toTest.getGameId());
            assertEquals("1245", toTest.getAnswer());
            assertFalse(toTest.isGameOver());
        } catch (GameDaoException ex) {
            fail("Should not hit GameDaoException in golden path.");
        }
    }

    @Test
    public void testGetGameByIdBadId() {
        try {
            Game toTest = gDao.getGameById(8);
            fail("Should have hit GameDaoException due to non existent ID");
        } catch (GameDaoException ex) {

        }
    }

    /**
     * Test of updateGameOver method, of class GameDaoImpl.
     */
    @Test
    public void testUpdateGameOverGoldenPath() {
        try {
            gDao.updateGameOver(1);
            Game updated = gDao.getGameById(1);
            assertEquals(1, updated.getGameId());
            assertEquals("1245", updated.getAnswer());
            assertTrue(updated.isGameOver());

        } catch (BadUpdateException ex) {
            fail("Should not hit BadUpdateException in golden path.");
        } catch (GameDaoException ex) {
            fail("Looks like getGameById failed in the updateGameOver test. Whoops.");
        }
    }

    @Test
    public void testUpdateGameOverBadId() {
        try {
            gDao.updateGameOver(0);
            fail("Should have hit BadUpdateException.");

        } catch (BadUpdateException ex) {

        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.controller;

import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.dto.Guess;
import com.sg.guessthenumber.dto.Round;
import com.sg.guessthenumber.exceptions.BadUpdateException;
import com.sg.guessthenumber.exceptions.EmptyResultSetException;
import com.sg.guessthenumber.exceptions.GameDaoException;
import com.sg.guessthenumber.exceptions.InvalidInputException;
import com.sg.guessthenumber.exceptions.RoundDaoException;
import com.sg.guessthenumber.service.GTNService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Isaia
 */
@RestController
@RequestMapping("/api")
public class GTNController {

    @Autowired
    GTNService service;

//    @PostMapping("/test")  --- this is an example TODO: Remove later.
//    public Game whateverYouWantDoesntMatter(@RequestBody Game game) {
//        System.out.println(game);
//        return game;
//    }
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int newGame() throws GameDaoException, InvalidInputException {

        Game toReturn = service.generateGame();
        // This method creates a new game, generates an answer,
        // sets status to in progress, will return created
        // gameId.
        return toReturn.getGameId();

    }

    @PostMapping("/guess")
    public Round makeGuess(@RequestBody Guess input) throws GameDaoException, InvalidInputException, RoundDaoException, BadUpdateException {

        // {guess: '', gameId:''}
        // Takes in JSON guess and gameId
        // calculates results of the guess against the chosen game answer
        return service.checkGuess(input);
        // sets gameOver to true if guess == answer
        // returns Round object with result.

    }

    @GetMapping("/games")
    public List<Game> listAllGames() throws GameDaoException {

        return service.getAllGames();

    }

    @GetMapping("/games/{gameid}")
    public Game getGameById(@PathVariable int gameid) throws GameDaoException {
        // Returns a Game object based on provided ID,

        return service.getGameById(gameid);

        // in progress does not display answer.
    }

    @GetMapping("/rounds/{gameid}")
    public List<Round> getRoundsByGameId(@PathVariable int gameid) throws RoundDaoException, EmptyResultSetException, GameDaoException, InvalidInputException {
        // Returns a list of rounds for specified game SORTED by time.
        return service.getRoundsForGameId(gameid);

    }
}

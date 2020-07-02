/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.service;

import com.sg.guessthenumber.dao.GameDao;
import com.sg.guessthenumber.dao.RoundDao;
import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.dto.Guess;
import com.sg.guessthenumber.dto.Round;
import com.sg.guessthenumber.exceptions.BadUpdateException;
import com.sg.guessthenumber.exceptions.EmptyResultSetException;
import com.sg.guessthenumber.exceptions.GameDaoException;
import com.sg.guessthenumber.exceptions.InvalidInputException;
import com.sg.guessthenumber.exceptions.RoundDaoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Isaia
 */
@Service
public class GTNService {

    @Autowired
    GameDao gdao;
    @Autowired
    RoundDao rdao;

    // --- new game feature path
    public Game generateGame() throws GameDaoException, InvalidInputException {
        Game toAdd = new Game();
        toAdd.setAnswer(generateAnswer());
        toAdd.setGameOver(false);
        return gdao.newGame(toAdd);
    }

    private String generateAnswer() {
        List<Integer> numbers = new ArrayList<>();
        String answer = "";
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        for (int j = 0; j < 4; j++) {
            answer = answer + numbers.get(j);
        }
        return answer;
    }
    // -------------------------------

    // --- make guess feature path
    public Round checkGuess(Guess input) throws GameDaoException, InvalidInputException, RoundDaoException, BadUpdateException {
        Game chosenGame = gdao.getGameById(input.getGameId());
        validateGuess(input.getGuessString());
        return compareAnswerAndGuess(chosenGame, input.getGuessString());
    }

    private void validateGuess(String guessString) throws InvalidInputException {
        if (guessString.length() != 4) {
            throw new InvalidInputException("Guesses must be exactly 4 characters long.");
        }
        char[] guessArray = guessString.toCharArray();
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                if (guessArray[i] == guessArray[j]) {
                    throw new InvalidInputException("Cannot repeat numbers in a guess.");
                }
            }
        }
    }

    private Round compareAnswerAndGuess(Game chosenGame, String guessString) throws RoundDaoException, BadUpdateException {
        Round toReturn = new Round();
        toReturn.setGuess(guessString);
        toReturn.setGameId(chosenGame.getGameId());
        int partial = 0;
        int exact = 0;
        char[] answerArray = chosenGame.getAnswer().toCharArray();
        char[] guessArray = guessString.toCharArray();
        for (int i = 0; i < answerArray.length; i++) {
            for (int j = 0; j < guessArray.length; j++) {
                if (guessArray[j] == answerArray[i]) {
                    if (i == j) {
                        exact++;
                    } else {
                        partial++;
                    }
                }
            }
        }
        // here is where we will call a gdao update method to mark game as over if exact == 4
        if (exact == 4) {
            gdao.updateGameOver(chosenGame.getGameId());
        }
        toReturn.setResult("p: " + partial + " - e: " + exact);
        return rdao.newRound(toReturn);
    }
    // -----------------------------------

    // --- get game by gameId feature path
    public Game getGameById(int id) throws GameDaoException {
        Game toCheck = gdao.getGameById(id);
        return hideAnswer(toCheck);
    }

    // --- display all games feature path
    public List<Game> getAllGames() throws GameDaoException {
        List<Game> allGames = gdao.getAllGames();
        for (Game g : allGames) {
            hideAnswer(g);
        }
        return allGames;
    }

    private Game hideAnswer(Game toCheck) {
        if (!toCheck.isGameOver()) {
            toCheck.setAnswer("****");
        }
        return toCheck;
    }
    // ---------------------------------

    // get rounds based on game id feature path
    public List<Round> getRoundsForGameId(int gameid) throws RoundDaoException, EmptyResultSetException, GameDaoException, InvalidInputException {
        validateGameId(gameid);
        List<Round> allRounds = rdao.getRoundsByGameId(gameid);
        return allRounds;
    }

    private void validateGameId(int gameid) throws GameDaoException, InvalidInputException {
        boolean isValid = false;
        List<Game> allGames = gdao.getAllGames();
        for (Game g : allGames) {
            if (g.getGameId() == gameid) {
                isValid = true;
            }
        }
        if (!isValid) {
            throw new InvalidInputException("Chosen game id does not exist.");
        }
    }

}

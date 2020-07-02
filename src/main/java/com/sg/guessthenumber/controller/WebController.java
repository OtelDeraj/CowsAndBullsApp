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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Isaia
 */
@Controller
public class WebController {

    @Autowired
    GTNService serv;

    @GetMapping({"games", ""})
    public String displayGames(Model pageModel) throws GameDaoException {
        List<Game> allGames = serv.getAllGames();
        pageModel.addAttribute("games", allGames);
        return "games";
    }

    @GetMapping("games/{gameId}")
    public String displayRounds(Model pageModel, @PathVariable Integer gameId) throws EmptyResultSetException, GameDaoException, InvalidInputException {
        List<Round> roundsForGame = new ArrayList<>();
        try {
            roundsForGame = serv.getRoundsForGameId(gameId);
        } catch (RoundDaoException ex) {
            
        }
        pageModel.addAttribute("rounds", roundsForGame);
        Guess toPass = new Guess();
        toPass.setGameId(gameId);
//        pageModel.addAttribute("gameId", gameId);
        pageModel.addAttribute("guess", toPass);
        return "gamedetails";
    }

//    @GetMapping("guess")
//    public String displayMakeGuess(Model pageModel){
//        pageModel.addAttribute("round", null);
//        
//        return "guess";
//    }
    @PostMapping("games/{gameId}")
    public String makeGuess(@Valid Guess input, BindingResult result, Model pageModel) throws GameDaoException, InvalidInputException, RoundDaoException, BadUpdateException, EmptyResultSetException {
        String path = "games/" + input.getGameId();
        pageModel.addAttribute("guess", input);
        if (result.hasErrors()) {
//            pageModel.addAttribute("round", null);
            List<Round> roundsForGame = serv.getRoundsForGameId(input.getGameId());
            pageModel.addAttribute("rounds", roundsForGame);
            return "gamedetails";
        }
        serv.checkGuess(input);
//        pageModel.addAttribute("round", latestRound);
        return "redirect:/" + path;

    }
}

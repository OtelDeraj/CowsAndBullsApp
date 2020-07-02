/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.controller.dao;

import com.sg.guessthenumber.dao.RoundDao;
import com.sg.guessthenumber.dto.Round;
import com.sg.guessthenumber.exceptions.RoundDaoException;
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
public class RoundDaoInMem implements RoundDao {
    
    List<Round> allRounds = new ArrayList<>();

    @Override
    public Round newRound(Round toAdd) throws RoundDaoException {
        toAdd.setRoundId(allRounds.size() + 1);
        allRounds.add(toAdd);
        return toAdd;
    }

    @Override
    public List<Round> getRoundsByGameId(int id) throws RoundDaoException {
        List<Round> toReturn = allRounds.stream().filter(r->r.getGameId() == id).collect(Collectors.toList());
        if(toReturn.size() == 0){
            throw new RoundDaoException("There are no rounds for that Game ID");
        }
        return toReturn;
    }
    
    public void setUp(){
        allRounds.clear();
        Round round1 = new Round("1234", "p: 2 - e: 0", 1);
        round1.setRoundId(1);
        Round round2 = new Round("2531", "p: 2 - e: 1", 1);
        round2.setRoundId(2);
        Round round3 = new Round("1234", "p: 0 - e: 4", 2);
        round3.setRoundId(3);
        
        allRounds.add(round1);
        allRounds.add(round2);
        allRounds.add(round3);
    }
    
}

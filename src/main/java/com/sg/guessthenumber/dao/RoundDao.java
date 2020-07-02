/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.dto.Round;
import com.sg.guessthenumber.exceptions.RoundDaoException;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Isaia
 */
@Component
public interface RoundDao {
    
    Round newRound(Round toAdd) throws RoundDaoException;
    
    List<Round> getRoundsByGameId(int id) throws RoundDaoException;
    
}

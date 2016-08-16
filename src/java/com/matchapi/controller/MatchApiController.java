package com.matchapi.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.matchapi.model.Players;
import com.matchapi.simulator.MatchSimulator;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author jona
 */
@RestController
public class MatchApiController {

    Players players = new Players();

    //-------------------Retrieve All Players--------------------------------------------------------
    /**
     * API for getting all the player details
     *
     * @return
     */
    @RequestMapping(value = "/player/", method = RequestMethod.GET, produces = "application/json")
    public JSONArray getAllPlayers() {
        return players.getAllPlayers();
    }

    //-------------------Retrieve Single Player--------------------------------------------------------
    /**
     * API for viewing player details
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET, produces = "application/json")
    public JSONObject viewPlayer(@PathVariable int id) {
        return players.viewPlayer(id);
    }

    //-------------------Create a Player--------------------------------------------------------
    /**
     * API for creating a new player
     *
     * @param object
     * @return
     */
    @RequestMapping(value = "/player/", method = RequestMethod.POST, produces = "application/json")
    public JSONArray addPlayer(@RequestBody JSONObject object) {
        return players.addPlayer(object);
    }

    //------------------- Update a Player --------------------------------------------------------
    /**
     * API for updating a existing player
     *
     * @param id
     * @param object
     * @return
     */
    @RequestMapping(value = "/player/{id}", method = RequestMethod.PUT, produces = "application/json")
    public JSONArray viewPlayer(@PathVariable int id, @RequestBody JSONObject object) {
        return players.editPlayer(object, id);
    }

    //------------------- Delete A Player --------------------------------------------------------
    /**
     * API for deleting a player
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public JSONArray deletePlayer(@PathVariable int id) {
        return players.deletePlayer(id);
    }

    //--------------------GET MATCH RESULT --------------------------------------------------------
    /**
     * API for getting the match simulation
     *
     * @param target
     * @return
     */
    @RequestMapping(value = "/run/", method = RequestMethod.GET, produces = "application/json")
    public JSONArray runGame(@RequestParam(value = "target", defaultValue = "40") String target) {
        MatchSimulator match = new MatchSimulator(players.getAllPlayers());
        return match.runGame(Integer.parseInt(target));
    }
}

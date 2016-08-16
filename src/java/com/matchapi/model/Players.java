package com.matchapi.model;

import com.matchapi.util.Utils;
import java.util.Objects;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Wrapper class for the player class, Also contains the CRUD methods for player data 
 * 
 * @author jona
 */
public class Players {

    JSONArray players = new JSONArray();
    JSONArray idsList = new JSONArray();
    private static final int MAX_PLAYERS = 4;

    /**
     * Constructor method, loads the data from file if exists
     * 
     */
    public Players() {
        players = Utils.getInstance().loadDataFromFile();
        idsList = loadIdsList();
    }
    
    /**
     * Returns the array of all players in the system
     * 
     * @return 
     */
    public JSONArray getAllPlayers(){
        return players;
    }
    
    /**
     * Function for adding a new player, checks if max_player limit is maintained and if unique id is given for a new entry
     * 
     * @param data
     * @return 
     */
    public JSONArray addPlayer(JSONObject data) {
        try {
            if (checkMaxLimit()) {
                System.out.println("Sorry max players exceeded");
                return players;
            }
            if (checkIfIdUnique(new Long((String) data.get("id")))) {
                System.out.println("Sorry id not unique");
                return players;
            }
            
            //Calling the player class constructor
            Player player = new Player(new Long((String)data.get("id")),
                    (String) data.get("name"),
                    Integer.parseInt((String) data.get("0")    ),
                    Integer.parseInt((String) data.get("1")    ),
                    Integer.parseInt((String) data.get("2")    ),
                    Integer.parseInt((String) data.get("3")    ),
                    Integer.parseInt((String) data.get("4")    ),
                    Integer.parseInt((String) data.get("5")    ),
                    Integer.parseInt((String) data.get("6")    ),
                    Integer.parseInt((String) data.get("out")) );

            players.add(player.getAsJson());

            Utils.getInstance().updateDataToFile(players);//Update new entry to the file

            return players;
        } catch (Exception e) {
            System.out.println("Sorry error occured in adding new player");
            e.printStackTrace();
        }

        return players;
    }
    
    /**
     * Function for viewing the player details given the id of the player
     * 
     * @param id
     * @return 
     */
    public JSONObject viewPlayer(int id) {

        try {
            for (Object player : players) {
                if (Objects.equals((Long)((JSONObject) player).get("id"), (long) id)) {
                    return (JSONObject) player;
                }
            }
        } catch (Exception e) {
            System.out.println("Sorry player with id = " + id + " not found");
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * Function for editing the player data, Basically deletes the given player and adds it as a fresh entry
     * 
     * @param data
     * @param id
     * @return 
     */
    public JSONArray editPlayer(JSONObject data, int id) {

        deletePlayer(id);
        return addPlayer(data);
    }
    
    /**
     * Function for deleting the player given the player id
     * 
     * @param id
     * @return 
     */
    public JSONArray deletePlayer(int id) {
        try {
            int index = MAX_PLAYERS+1;//Index cannot exceed MAX_PLAYERS+1 

            for (int i = 0; i < players.size(); i++) {
                if (Objects.equals((Long)((JSONObject) players.get(i)).get("id"), (long) id)) {
                    index = i;
                }
            }

            if (index != MAX_PLAYERS+1) {
                players.remove(index);
            } else {
                throw new Exception();
            }

            Utils.getInstance().updateDataToFile(players);

            return players;
        } catch (Exception e) {
            System.out.println("Sorry player with id = " + id + " not found");
            e.printStackTrace();
        }

        return players;
    }
    
    /**
     * Function for checking the max limit of the players
     * 
     * @return 
     */
    private Boolean checkMaxLimit() {
        return players.size() >= MAX_PLAYERS;
    }
    
    /**
     * Loads the player ids onto the memory, for faster checking unique id value
     * 
     * @return 
     */
    private JSONArray loadIdsList() {
        JSONArray ids = new JSONArray();

        players.stream().forEach((player) -> {
            ids.add((Long)(((JSONObject) player).get("id")));
        });

        return ids;
    }

    private Boolean checkIfIdUnique(Long id) {
        return idsList.contains(id);
    }
}

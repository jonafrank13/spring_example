package com.matchapi.simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *Match simulator class, this class helps in simulating the entire match 
 * 
 * @author jona
 */
public class MatchSimulator {

    private static final int MAX_PLAYERS = 4;
    private static final int MAX_BALLS = 24;// 4 overs, 6*4=24 balls remaining
    private static JSONArray players = new JSONArray();
    private static JSONObject currentPlayerStats = new JSONObject();
    
    /**
     * MatchSimulator class Constructor, accepts the players data in order to simulate the match
     * 
     * @param players 
     */
    public MatchSimulator(JSONArray players) {
        MatchSimulator.players = players;
    }

    /**
     * Returns a random no between 1-100
     *
     * @return
     */
    private int getRandomNo() {
        return (new Random()).nextInt(101);
    }
    
    /**
     * Get current ball result
     * 
     * @return 
     */
    private String getBall() {
        int cumulativeProbability = 0;
        int currentBall = getRandomNo();

        for (Object key : currentPlayerStats.keySet()) {
            cumulativeProbability += Integer.parseInt(currentPlayerStats.get((String) key).toString());
            if (currentBall <= cumulativeProbability) {
                return (String) key;
            }
        }

        return "0";
    }
    
    /**
     * Load the probability statistics for the current player in crease
     * 
     * @param currentPlayer 
     */
    private void loadCurrentPlayerStats(int currentPlayer,JSONArray currentPlayers) {
        currentPlayerStats = new JSONObject();

        JSONObject player = (JSONObject) currentPlayers.get(currentPlayer);

        currentPlayerStats.put("0"   , player.get("0")   );
        currentPlayerStats.put("1"   , player.get("1")   );
        currentPlayerStats.put("2"   , player.get("2")   );
        currentPlayerStats.put("3"   , player.get("3")   );
        currentPlayerStats.put("4"   , player.get("4")   );
        currentPlayerStats.put("5"   , player.get("5")   );
        currentPlayerStats.put("6"   , player.get("6")   );
        currentPlayerStats.put("out" , player.get("out") );
    }
    
    /**
     * Rotate the strike in case of odd score or if the player is out
     * 
     * @param result
     * @return 
     */
    private Boolean rotatePlayer(String result) {
        return result.equals("out") || result.equals("1") || result.equals("3") || result.equals("5");//Returns true when player needs to be rotated (either if out or scores odd runs)
    }
    
    /**
     * Main running method for simulating the entire match
     * 
     * @param target
     * @return 
     */
    public JSONArray runGame(int target,JSONArray currentPlayers,ArrayList playerQueue,int max_players,int max_balls) {
        JSONArray balls = new JSONArray();

        int wickets = 0;//Initializing no of wickets as 0
        int totalScore = 0;//Initializing total scored runs as 0

        while (wickets < max_players-1 && totalScore <= target && balls.size() < max_balls) { //Run the game till wickets are available and when the target is not yet reached and balls remaining
            
            int currentPlayer = Integer.parseInt((String) playerQueue.get(0)); //Initiate the current player index
            
            JSONObject ball = new JSONObject();

            loadCurrentPlayerStats(currentPlayer,currentPlayers);//Load the probablity stats for the player

            String result = getBall();//Get the result of the current ball
            
            ball.put(((JSONObject) currentPlayers.get(currentPlayer)).get("id"), result);//Add the result to the final output
            
            if (result.equals("out")) {//If the batsman is out increment wickets and remove the batsman from the queue and reset the currentPlayer index
                wickets++;   
                playerQueue.remove(Integer.toString(currentPlayer));
                currentPlayer = Integer.parseInt((String) playerQueue.get(0));
            } else {//else just increment the totalscore
                totalScore += Integer.parseInt(result);
            }

            if (rotatePlayer(result) && wickets < max_players-1) {//Check if players need to be rotated and also if wickets are available
                Collections.swap(playerQueue, 0, 1);
            }

            balls.add(ball);
        }

        return balls;
    }
    
    /**
     * Overloaded runGame method, this uses the defaults for problem statement 1 
     * 
     * @param target
     * @return 
     */
    public JSONArray runGame(int target) {
       
        ArrayList playerQueue = new ArrayList(Arrays.asList("0","1","2","3"));//Maintaining the player queue the one getting out will be removed

        return runGame(target,players,playerQueue,MAX_PLAYERS,MAX_BALLS);
    }
}

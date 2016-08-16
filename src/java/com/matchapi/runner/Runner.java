package com.matchapi.runner;

import com.matchapi.model.Players;
import com.matchapi.simulator.MatchSimulator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * THIS IS A RUNNER FOR THE PROBLEM STATEMENTS
 *
 * NOTE THAT THE DEFAULT FILE PATH FOR LOADING/SAVING THE PLAYER DATA IS
 * E:\players.json CHANGE IT IF REQUIRED IN UTILS CLASS
 *
 * @author jona
 */
public class Runner {

    static Players players = new Players();
    static final int TARGET = 40;

    public static void main(String args[]) {
        System.out.println("All players :");

        JSONArray playersList = players.getAllPlayers();

        System.out.println(playersList);

        System.out.println("Player 1 :");

        System.out.println(players.viewPlayer(1));

        //This SECTION is a runner for PROBLEM 1----------------------------------------------------------------
        System.out.println("------------------------------PROBLEM 1----------------------------------------------");

        MatchSimulator match = new MatchSimulator(players.getAllPlayers());

        JSONArray matchResult = match.runGame(TARGET);

        System.out.println("Match output :");

        System.out.println(matchResult);

        matchCommentry(matchResult);

        //This SECTION is a runner for PROBLEM 2-----------------------------------------------------------------
        System.out.println("------------------------------PROBLEM 2----------------------------------------------");
        JSONArray lengaburuPlayers = loadLengaburuPlayers();
        JSONArray enchaiPlayers = loadEnchaiPlayers();

        System.out.println("Lengaburu players :");
        System.out.println(lengaburuPlayers);
        System.out.println("Enchai players :");
        System.out.println(enchaiPlayers);

        JSONArray lengaburuResult = match.runGame(9999, lengaburuPlayers, new ArrayList(Arrays.asList("0", "1")), 2, 6);//Reusing the overloaded function in MatchSimulator. Setting the target as 9999(Relative Infinity) as there is no limit to the first innings

        int newTarget = lengaburuCommentary(lengaburuResult, lengaburuPlayers);

        JSONArray enchaiResult = match.runGame(newTarget, enchaiPlayers, new ArrayList(Arrays.asList("0", "1")), 2, 6);//Stting the target for the enchai innings as the score obtained by lengaburu

        enchaiCommentary(enchaiResult, enchaiPlayers, newTarget);
    }

    public static void matchCommentry(JSONArray matchResult) {
        System.out.println("####MATCH SUMMARY####");

        int totalScore = 0;
        int over = 0;
        int balls = 0;
        int wickets = 0;
        HashMap playerScores = new HashMap();

        for (Object obj : matchResult) {
            if (balls % 6 == 0) {
                System.out.println((4 - over) + " Overs remaining " + (TARGET - totalScore) + " runs to win");
            }

            JSONObject jsonObj = (JSONObject) obj;
            for (Object key : jsonObj.keySet()) {

                String ky = key.toString();
                int score = 0;
                String playerName = (String) players.viewPlayer(Integer.parseInt(ky)).get("name");

                try {
                    score = Integer.parseInt(jsonObj.get(key).toString());
                } catch (Exception e) {
                    wickets++;
                    System.out.println(over + "." + ((balls % 6) + 1) + " " + playerName + " is OUT!");
                    balls++;
                    over = balls / 6;
                    continue;
                }

                if (playerScores.containsKey(ky)) {
                    int playScore = (int) playerScores.get(ky);
                    playScore += score;
                    playerScores.put(ky, playScore);
                } else {
                    playerScores.put(ky, score);
                }

                totalScore += score;

                System.out.println(over + "." + ((balls % 6) + 1) + " " + playerName + " Scores " + score + " run(s)");

                balls++;
                over = balls / 6;
            }
        }

        System.out.println("TOTAL = " + totalScore + "/" + wickets);

        if (totalScore > TARGET) {
            System.out.println("Lengaburu won by " + (4 - wickets) + " wickets, with " + (24 - balls) + " balls remaining.");
        } else if (totalScore == TARGET) {
            System.out.println("HOLY COW!! It's a tie.... I doubt this case will ever be called");
        } else {
            System.out.println("Lengaburu lost by " + (TARGET - totalScore) + " runs.");
        }

        for (Object key : playerScores.keySet()) {
            String playerName = (String) players.viewPlayer(Integer.parseInt((String) key)).get("name");
            System.out.println(playerName + " - " + playerScores.get((String) key));
        }
    }

    private static int lengaburuCommentary(JSONArray matchResult, JSONArray lengaburuPlayers) {
        System.out.println("####LENGABURU INNINGS####");
        System.out.println("Lengaburu result :");
        System.out.println(matchResult);

        int totalScore = 0;
        int balls = 0;
        int wickets = 0;

        HashMap playerScores = new HashMap();

        for (Object obj : matchResult) {
            JSONObject jsonObj = (JSONObject) obj;
            for (Object key : jsonObj.keySet()) {

                String ky = key.toString();
                int score = 0;
                String playerName = (String) ((JSONObject) lengaburuPlayers.get(Integer.parseInt(ky) - 1)).get("name");

                try {
                    score = Integer.parseInt(jsonObj.get(key).toString());
                } catch (Exception e) {
                    wickets++;
                    System.out.println("0." + (balls + 1) + " " + playerName + " is OUT!");
                    balls++;
                    continue;
                }

                if (playerScores.containsKey(ky)) {
                    int playScore = (int) playerScores.get(ky);
                    playScore += score;
                    playerScores.put(ky, playScore);
                } else {
                    playerScores.put(ky, score);
                }

                totalScore += score;

                System.out.println("0." + (balls + 1) + " " + playerName + " Scores " + score + " run(s)");

                balls++;
            }
        }

        System.out.println("TOTAL = " + totalScore + "/" + wickets);

        for (Object key : playerScores.keySet()) {
            String playerName = (String) ((JSONObject) lengaburuPlayers.get(Integer.parseInt((String) key) - 1)).get("name");
            System.out.println(playerName + " - " + playerScores.get((String) key));
        }

        return totalScore;
    }

    private static void enchaiCommentary(JSONArray matchResult, JSONArray enchaiPlayers, int newTarget) {
        System.out.println("####ENCHAI INNINGS####");
        System.out.println("Enchai result :");
        System.out.println(matchResult);

        int totalScore = 0;
        int balls = 0;
        int wickets = 0;

        HashMap playerScores = new HashMap();

        for (Object obj : matchResult) {
            JSONObject jsonObj = (JSONObject) obj;
            for (Object key : jsonObj.keySet()) {

                String ky = key.toString();
                int score = 0;
                String playerName = (String) ((JSONObject) enchaiPlayers.get(Integer.parseInt(ky) - 1)).get("name");

                try {
                    score = Integer.parseInt(jsonObj.get(key).toString());
                } catch (Exception e) {
                    wickets++;
                    System.out.println("0." + (balls + 1) + " " + playerName + " is OUT!");
                    balls++;
                    continue;
                }

                if (playerScores.containsKey(ky)) {
                    int playScore = (int) playerScores.get(ky);
                    playScore += score;
                    playerScores.put(ky, playScore);
                } else {
                    playerScores.put(ky, score);
                }

                totalScore += score;

                System.out.println("0." + (balls + 1) + " " + playerName + " Scores " + score + " run(s)");

                balls++;
            }
        }

        System.out.println("TOTAL = " + totalScore + "/" + wickets);

        for (Object key : playerScores.keySet()) {
            String playerName = (String) ((JSONObject) enchaiPlayers.get(Integer.parseInt((String) key) - 1)).get("name");
            System.out.println(playerName + " - " + playerScores.get((String) key));
        }

        if (totalScore > newTarget) {
            System.out.println("Enchai won by " + (2 - wickets) + " wickets, with " + (6 - balls) + " balls remaining.");
        } else if (totalScore == newTarget) {
            System.out.println("HOLY COW!! It's a tie.... I again doubt this case will ever be called");
        } else {
            System.out.println("Lengaburu won by " + (newTarget - totalScore) + " runs.");
        }
    }

    private static JSONArray loadLengaburuPlayers() {
       //Hard coding the player stats for problem 2, Can be run in the usual flow by CRUD operations.. But this saves time and showcases another method of running the game
        //Also does not change the loaded values in the memory
        JSONArray lengaburuPlayers = new JSONArray();

        JSONObject kiratBoli = new JSONObject();
        JSONObject nsNodhi = new JSONObject();

        kiratBoli.put("id", 1l);
        kiratBoli.put("name", "Kirat Boli");
        kiratBoli.put("0", 5);
        kiratBoli.put("1", 10);
        kiratBoli.put("2", 25);
        kiratBoli.put("3", 10);
        kiratBoli.put("4", 25);
        kiratBoli.put("5", 1);
        kiratBoli.put("6", 14);
        kiratBoli.put("out", 10);

        nsNodhi.put("id", 2l);
        nsNodhi.put("name", "N.S Nodhi");
        nsNodhi.put("0", 5);
        nsNodhi.put("1", 15);
        nsNodhi.put("2", 15);
        nsNodhi.put("3", 10);
        nsNodhi.put("4", 20);
        nsNodhi.put("5", 1);
        nsNodhi.put("6", 19);
        nsNodhi.put("out", 15);

        lengaburuPlayers.add(kiratBoli);
        lengaburuPlayers.add(nsNodhi);

        return lengaburuPlayers;
    }

    private static JSONArray loadEnchaiPlayers() {
       //Hard coding the player stats for problem 2, Can be run in the usual flow by CRUD operations.. But this saves time and showcases another method of running the game
        //Also does not change the loaded values in the memory
        JSONArray enchaiPlayers = new JSONArray();

        JSONObject dbVellyers = new JSONObject();
        JSONObject hMamla = new JSONObject();

        dbVellyers.put("id", 1l);
        dbVellyers.put("name", "DB Vellyers");
        dbVellyers.put("0", 5);
        dbVellyers.put("1", 10);
        dbVellyers.put("2", 25);
        dbVellyers.put("3", 10);
        dbVellyers.put("4", 25);
        dbVellyers.put("5", 1);
        dbVellyers.put("6", 14);
        dbVellyers.put("out", 10);

        hMamla.put("id", 2l);
        hMamla.put("name", "H Mamla");
        hMamla.put("0", 10);
        hMamla.put("1", 15);
        hMamla.put("2", 15);
        hMamla.put("3", 10);
        hMamla.put("4", 20);
        hMamla.put("5", 1);
        hMamla.put("6", 19);
        hMamla.put("out", 15);

        enchaiPlayers.add(dbVellyers);
        enchaiPlayers.add(hMamla);

        return enchaiPlayers;
    }
}

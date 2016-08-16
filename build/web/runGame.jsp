<%-- 
    Document   : runGame
    Author     : jona
    Comments   : CLONE OF THE RUNNER CLASS FOR EASY OUTPUT VIEWING
    Url        : http://localhost:8080/MatchApi/runGame.jsp?target=<define_target>
--%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.matchapi.simulator.MatchSimulator"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="com.matchapi.model.Players"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Game Simulator</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div>
            <h1>Game Summary :</h1>
            <%
                String target = (String)request.getParameter("target");
                try
                {
                    TARGET = Integer.parseInt(target);
                }
                catch(Exception e){}
                main(out);
            %>
    </div>
    </body>
</html>
<%!
    static Players players = new Players();
    static int TARGET = 40;

    public static void main(javax.servlet.jsp.JspWriter out) throws Exception {
        out.println("All players :"+"</br></br>");

        JSONArray playersList = players.getAllPlayers();

        out.println(playersList+"</br></br>");

        out.println("Player 1 :"+"</br></br>");

        out.println(players.viewPlayer(1)+"</br></br>");

        //This SECTION is a runner for PROBLEM 1----------------------------------------------------------------
        out.println("<h3>------------------------------PROBLEM 1----------------------------------------------</h3>"+"</br></br>");

        MatchSimulator match = new MatchSimulator(players.getAllPlayers());
        
        out.println("<h4>GAME TARGET = "+TARGET+"</h4></br><br>");

        JSONArray matchResult = match.runGame(TARGET);

        out.println("Match output :"+"</br></br>");

        out.println(matchResult+"</br></br>");

        matchCommentry(matchResult,out);

        //This SECTION is a runner for PROBLEM 2-----------------------------------------------------------------
        out.println("<h3>------------------------------PROBLEM 2----------------------------------------------</h3>"+"</br></br>");
        JSONArray lengaburuPlayers = loadLengaburuPlayers();
        JSONArray enchaiPlayers = loadEnchaiPlayers();

        out.println("Lengaburu players :"+"</br></br>");
        out.println(lengaburuPlayers+"</br></br>");
        out.println("Enchai players :"+"</br></br>");
        out.println(enchaiPlayers+"</br></br>");

        JSONArray lengaburuResult = match.runGame(9999, lengaburuPlayers, new ArrayList(Arrays.asList("0", "1")), 2, 6);//Reusing the overloaded function in MatchSimulator. Setting the target as 9999(Relative Infinity) as there is no limit to the first innings

        int newTarget = lengaburuCommentary(lengaburuResult, lengaburuPlayers,out);

        JSONArray enchaiResult = match.runGame(newTarget, enchaiPlayers, new ArrayList(Arrays.asList("0", "1")), 2, 6);//Stting the target for the enchai innings as the score obtained by lengaburu

        enchaiCommentary(enchaiResult, enchaiPlayers, newTarget,out);
    }

    public static void matchCommentry(JSONArray matchResult,javax.servlet.jsp.JspWriter out) throws Exception{
        out.println("<h5>####MATCH SUMMARY####</h5>"+"</br></br>");

        int totalScore = 0;
        int over = 0;
        int balls = 0;
        int wickets = 0;
        HashMap playerScores = new HashMap();

        for (Object obj : matchResult) {
            if (balls % 6 == 0) {
                out.println((4 - over) + " Overs remaining " + (TARGET - totalScore) + " runs to win"+"</br></br>");
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
                    out.println(over + "." + ((balls % 6) + 1) + " " + playerName + " is OUT!"+"</br></br>");
                    balls++;
                    over = balls / 6;
                    continue;
                }

                if (playerScores.containsKey(ky)) {
                    int playScore = Integer.parseInt(playerScores.get(ky).toString());
                    playScore += score;
                    playerScores.put(ky, playScore);
                } else {
                    playerScores.put(ky, score);
                }

                totalScore += score;

                out.println(over + "." + ((balls % 6) + 1) + " " + playerName + " Scores " + score + " run(s)"+"</br></br>");

                balls++;
                over = balls / 6;
            }
        }

        out.println("<h4>TOTAL = " + totalScore + "/" + wickets+"</h4></br></br>");

        if (totalScore > TARGET) {
            out.println("<h4>Lengaburu won by " + (4 - wickets) + " wickets, with " + (24 - balls) + " balls remaining."+"</h4></br></br>");
        } else if (totalScore == TARGET) {
            out.println("<h4>HOLY COW!! It's a tie.... I doubt this case will ever be called"+"</h4></br></br>");
        } else {
            out.println("<h4>Lengaburu lost by " + (TARGET - totalScore) + " runs."+"</h4></br></br>");
        }

        for (Object key : playerScores.keySet()) {
            String playerName = (String) players.viewPlayer(Integer.parseInt((String) key)).get("name");
            out.println(playerName + " - " + playerScores.get((String) key)+"</br></br>");
        }
    }

    private static int lengaburuCommentary(JSONArray matchResult, JSONArray lengaburuPlayers,javax.servlet.jsp.JspWriter out) throws Exception {
        out.println("<h4>####LENGABURU INNINGS####</h4>"+"</br></br>");
        out.println("Lengaburu result :"+"</br></br>");
        out.println(matchResult+"</br></br>");

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
                    out.println("0." + (balls + 1) + " " + playerName + " is OUT!"+"</br></br>");
                    balls++;
                    continue;
                }

                if (playerScores.containsKey(ky)) {
                    int playScore = Integer.parseInt(playerScores.get(ky).toString());
                    playScore += score;
                    playerScores.put(ky, playScore);
                } else {
                    playerScores.put(ky, score);
                }

                totalScore += score;

                out.println("0." + (balls + 1) + " " + playerName + " Scores " + score + " run(s)"+"</br></br>");

                balls++;
            }
        }

        out.println("<h4>TOTAL = " + totalScore + "/" + wickets+"</h4></br></br>");

        for (Object key : playerScores.keySet()) {
            String playerName = (String) ((JSONObject) lengaburuPlayers.get(Integer.parseInt((String) key) - 1)).get("name");
            out.println(playerName + " - " + playerScores.get((String) key)+"</br></br>");
        }

        return totalScore;
    }

    private static void enchaiCommentary(JSONArray matchResult, JSONArray enchaiPlayers, int newTarget,javax.servlet.jsp.JspWriter out) throws Exception{
        out.println("<h4>####ENCHAI INNINGS####</h4>"+"</br></br>");
        out.println("Enchai result :"+"</br></br>");
        out.println(matchResult+"</br></br>");

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
                    out.println("0." + (balls + 1) + " " + playerName + " is OUT!"+"</br></br>");
                    balls++;
                    continue;
                }

                if (playerScores.containsKey(ky)) {
                    int playScore = Integer.parseInt(playerScores.get(ky).toString());
                    playScore += score;
                    playerScores.put(ky, playScore);
                } else {
                    playerScores.put(ky, score);
                }

                totalScore += score;

                out.println("0." + (balls + 1) + " " + playerName + " Scores " + score + " run(s)"+"</br></br>");

                balls++;
            }
        }

        out.println("<h4>TOTAL = " + totalScore + "/" + wickets+"<h4></br></br>");

        for (Object key : playerScores.keySet()) {
            String playerName = (String) ((JSONObject) enchaiPlayers.get(Integer.parseInt((String) key) - 1)).get("name");
            out.println(playerName + " - " + playerScores.get((String) key)+"</br></br>");
        }

        if (totalScore > newTarget) {
            out.println("<h4>Enchai won by " + (2 - wickets) + " wickets, with " + (6 - balls) + " balls remaining."+"</h4></br></br>");
        } else if (totalScore == newTarget) {
            out.println("<h4>HOLY COW!! It's a tie.... I again doubt this case will ever be called"+"</h4></br></br>");
        } else {
            out.println("<h4>Lengaburu won by " + (newTarget - totalScore) + " runs."+"</h4></br></br>");
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
%>
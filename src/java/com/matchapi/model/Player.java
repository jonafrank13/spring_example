package com.matchapi.model;

import org.json.simple.JSONObject;

/**
 * Player model class, contains all data relevant to a player
 * 
 * @author jona
 */
public class Player {
    
    private Long id;
    private String name;
    //The probablity for each score
    private int dot;
    private int one;
    private int two;
    private int three;
    private int four;
    private int five;
    private int six;
    private int out;
    
    /**
     * Constructor for the Player model
     * @param id
     * @param name
     * @param dot
     * @param one
     * @param two
     * @param three
     * @param four
     * @param five
     * @param six
     * @param out 
     */
    public Player(Long id, String name, int dot, int one, int two, int three, int four, int five, int six, int out) {
        this.id = id;
        this.name = name;
        this.dot = dot;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.out = out;
    }
    
    //Getter-Setter for this class
    
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDot() {
        return dot;
    }

    public void setDot(int dot) {
        this.dot = dot;
    }

    public float getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public float getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public float getThree() {
        return three;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public float getFour() {
        return four;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public float getFive() {
        return five;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public float getSix() {
        return six;
    }

    public void setSix(int six) {
        this.six = six;
    }

    public float getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }
    
    /**
     * Get data as json
     * 
     * @return 
     */
    public JSONObject getAsJson()
    {
        JSONObject returnData = new JSONObject();
        
        returnData.put("id"   ,this.id   );
        returnData.put("name" ,this.name );
        returnData.put("0"    ,this.dot  );
        returnData.put("1"    ,this.one  );
        returnData.put("2"    ,this.two  );
        returnData.put("3"    ,this.three);
        returnData.put("4"    ,this.four );
        returnData.put("5"    ,this.five );
        returnData.put("6"    ,this.six  );
        returnData.put("out"  ,this.out  );
        
        return returnData;        
    }
}
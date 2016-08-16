package com.matchapi.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Utils class, mainly for loading/saving data to a file
 *
 * @author jona
 */
public class Utils {

    private static final String FILE_NAME = "e:/players.json";//File location for saving/loading data
    private static Utils utils = null;
    
    /**
     * Instance method for getting a single instance of the class
     * 
     * @return 
     */
    public static Utils getInstance(){
        if(utils == null)
        {
            utils = new Utils();
        }
        
        return utils;
    }
    
    /**
     * Dumps the JSON data to a file
     * 
     * @param data
     * @return 
     */
    public Boolean updateDataToFile(JSONArray data) {
        try {
            FileWriter file = new FileWriter(FILE_NAME);
            file.write(data.toJSONString());
            file.flush();
            file.close();

            return true;
        } catch (Exception e) {

            System.out.println("Error occured in writing file");
            e.printStackTrace();
        }

        return false;
    }
    
    /**
     * Loads JSON data from file
     * 
     * @return 
     */
    public JSONArray loadDataFromFile() {
        JSONParser parser = new JSONParser();
        try {
            File file = new File(FILE_NAME);
            
            if(!file.exists())
            {
                file.createNewFile();
                PrintWriter writer = new PrintWriter(FILE_NAME, "UTF-8");
                writer.println("[]");
                writer.close();
            } 
            
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(FILE_NAME));

            return jsonArray;
        } catch (IOException | ParseException e) {
            System.out.println("Error occured in loading file");
            e.printStackTrace();
        }
        
        return new JSONArray();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zain
 */

public class DeckData {
    //List of rows containing data about main deck cards
    private static Map<String, DataRow> cards;
    private static BufferedReader deck_buffer;
    private static int SIZE_COPIES_COUNTED = 0;
    
    
    /*Load deck file and extract IDs*/
    public static void loadDeckData(String deck_path){  
        cards = new HashMap<>();
        boolean is_main_deck; 
        
        is_main_deck = false;
        
        
        deck_buffer = DeckReader.GetDeckData(deck_path);
        try 
        {
            String line;
            while ( (line = deck_buffer.readLine()) != null)
            {
                //After main it comes extra deck, a.k.a. end iterations
                if(line.equals("#extra")) break;
                
                if(is_main_deck){
                    if(!cards.containsKey(line))
                    {
                        cards.put(line, new DataRow(line, 1)); //add card id and 1 as the number of copies
                    }
                    else
                    {
                        //if id already exists, increase number of copies +1                      
                        cards.put(line,
                            new DataRow(line, cards.get(line).GetCopies() + 1));
                    }
                    
                    SIZE_COPIES_COUNTED++;
                }
                //If current line is #main, then add IDs
                if(line.equals("#main")) is_main_deck = true;
            }
        } catch (IOException ex) 
        {
            Logger.getLogger(DeckData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void LoadDataBase(String path){
        DBConnection.open(path);
        
        String query;
        ResultSet rs = null;
        
        for(Map.Entry<String, DataRow> card : cards.entrySet()){
            try {
                query = "SELECT name, desc FROM texts WHERE id=" + card.getKey() + ";";
                
                rs = DBConnection.ExecuteQuery(query);
                
                while(rs.next())
                {
                    cards.put(card.getKey(), 
                        new DataRow(card.getKey(), rs.getString("name"),
                            rs.getString("desc"), card.getValue().GetCopies()));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DeckData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
        DBConnection.close();
    }
    
    
    public static String getCardName(String id){
        for(Map.Entry<String, DataRow> card : cards.entrySet())
            if(card.getKey().equals(id)) {
                return card.getValue().GetName();
                
            }

        return null;
    }
    
    public static String getCardId(String name){
        for(Map.Entry<String, DataRow> card : cards.entrySet())
            if(card.getValue().GetName().equals(name))
                return card.getKey();
        return null;
    }
    
    public static String getCardDescription(String id){
        for(Map.Entry<String, DataRow> card : cards.entrySet())
            if(card.getKey().equals(id)) return card.getValue().GetDescription();

        return null;
    }
    
    public static int getNumberOfCopies  (String id){
        for(Map.Entry<String, DataRow> card : cards.entrySet())
            if(card.getKey().equals(id)) return card.getValue().GetCopies();

        return 0;
    }
    
    public static int size(){
        return cards.size();
    }
    
    public static int sizeCopiesIncluded(){
        return SIZE_COPIES_COUNTED;
    }
    
    public static Set<String> getIdSet(){
        Set<String> ids = new HashSet<>();
        
        for(Map.Entry<String, DataRow> current : cards.entrySet())
            ids.add(current.getKey());
        
        return ids;
    }
    
    public static void printAllData(){
        System.out.println("[[[[");
        for(Map.Entry<String, DataRow> current : cards.entrySet()){
            System.out.println("      Name:" + current.getValue().GetName() + " | ID:" + current.getKey() +
                " | COPIES:" + current.getValue().GetCopies() + " | DESC:" + current.getValue().desc + "\n");
        }
        System.out.println("]]]]]\n");
    }
    
    
    //This internal class stores rows from the query
    private static class DataRow{
        private String id;
        private String name;
        private String desc;
        private int copies;
        
        DataRow( String card_id, String card_name, String card_desc , int copies)
        {
            this.id = card_id;
            this.name = card_name;
            this.desc = card_desc;
            this.copies = copies;
        }
        
        //If you only know card id and number of copies
        DataRow(String card_id, int copies)
        {
            this.id = card_id;
            this.copies = copies;
            this.name = null;
            this.desc = null;
        }
        
        public void SetName(String name){
            this.name = name;
        }
        
        public void SetDescription(String card_desc){
            this.desc = card_desc;
        }
        
        public String GetId(){
            return this.id;
        }
        
        public String GetName(){
            return this.name;
        }
        
        public String GetDescription(){
            return this.desc;
        }
        
        public void SetCopies(int copies){
            this.copies = copies;
        }
        
        public int GetCopies(){
            return this.copies;
        }
    }
    
}
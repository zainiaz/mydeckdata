/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zain
 */
public class DeckReader {
    private static FileReader deckReader;
    private static BufferedReader buffered;
    
    public static BufferedReader GetDeckData(String deck_path) {
        System.out.println("Inside DeckReader, looking for " + deck_path);
        try {
            deckReader = new FileReader( new File(deck_path) );
            buffered = new BufferedReader(deckReader);
            
            try {
                System.out.println(buffered.readLine());
            } catch (IOException ex) {
                Logger.getLogger(DeckReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                deckReader.close();
            } catch (IOException ex) {
                Logger.getLogger(DeckReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return buffered;
            
        } catch (FileNotFoundException fileNotFoundEx) {
            Logger.getLogger(DeckReader.class.getName()).log(Level.SEVERE, null, ex);
            try {
                deckReader.close();
            } catch (IOException ioException) {
                Logger.getLogger(DeckReader.class.getName()).log(Level.SEVERE, null, ioException);
            } 
        }
        return null;
    }

}

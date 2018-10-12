/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.HashSet;
import java.util.Set;
import java.math.BigInteger;
import java.lang.instrument.Instrumentation;
import java.util.Scanner;

/**
 *
 * @author zain
 */
public class Calcs 
{
    private static final String PURE = "[P]";
    private static final String ALONG = "[ALNG]";
    private static final String SEARCH = "[SRCHS]";
    private static final String ONE = "[1]";
    private static final String TWO = "[2]";
    private static final String THREE = "[3]";
    private static final String END = "\n";
    
    public static String GetCalcs(int deck_s, int hand_s, int copies, String main_card, Set<String> draw_along, Set<String> searchers)
    {   

        String s;
        int aux; //To use it when it's needed
        DeckData.printAllData();
        //System.out.println("-------->Card: " + DeckData.getCardName(main_card) + " ID: " + main_card);
        s = PURE + ONE + String.valueOf(hyp_dist(deck_s, copies, hand_s, 1)) + END;
        
       
        //System.out.println(s);
        
        //There's 2 or more copies of it
        if(copies >= 2)
            s += PURE + TWO + String.valueOf(hyp_dist(deck_s, copies, hand_s, 2)) + END;
        
        //System.out.println(s);
        
        //There's 3 copies of it
        if(copies == 3)
            s += PURE + THREE + String.valueOf(hyp_dist(deck_s, copies, hand_s, 3)) + END;
        
        
        if(searchers != null){
            int s_copies = 0;
            
            //Aux will store the number of searchers
            for(String current : searchers) 
                s_copies += DeckData.getNumberOfCopies(current);
            
            s += END;
            s += SEARCH + ONE + String.valueOf(hyp_dist(deck_s, s_copies + copies, hand_s, 1)) + END;
            
            s += SEARCH + TWO + String.valueOf(hyp_dist(deck_s, s_copies + copies, hand_s, 2)) + END;
                
            if( (copies + s_copies) >= 3)
                s += SEARCH + THREE + String.valueOf(hyp_dist(deck_s, s_copies + copies, hand_s, 3)) + END;
        }
        
        if(draw_along != null){
            s += END;
            
            draw_along.add(main_card);
            
            double prob = 1;
            int i = 0;
            
            for (String current : draw_along){
                System.out.println("SUM PROB:" + prob);
                System.out.println("ADD PROB:" + hyp_dist(deck_s - i, DeckData.getNumberOfCopies(current), hand_s - i, 1));
                prob *= hyp_dist(deck_s - i, DeckData.getNumberOfCopies(current), hand_s - i, 1);
                i++;
            }
            
            System.out.println(prob);
            s += PURE + ALONG + ONE + String.valueOf(prob) + END;
        }
        
        
        return s;
    }
    
    //Calculate the hypergeometric distribution 
    //(probability to draw certain card from a deck)
    // N = Population, K = Total copies of card, n = Draws, k = copies wanted
    private static double hyp_dist(int N, int K, int n, int k){
        /*
        * Combination = KCk * (N-K)C(n-k)
        *               ----------------
        *                      NCn
        */
        double up = comb(K, k) * (comb(N - K, n - k));
        double down = comb(N, n);
        
        //System.out.println("UP: " + up);
        //System.out.println("DOWN: " + down);
        //System.out.println("Result: " + up/down);
        
        return (up/down);
       
    }
    
    //Combination
    private static double comb(int n, int r){
        BigInteger result = BigInteger.valueOf(1);
    
        /*
        * Combination =     n!
        *               ---------
        *                r!(n-r)!
        */
        result = result.multiply( fact(n).divide( fact(r).multiply(fact(n-r))) );
        
        //System.out.println("Combinatio of " + n + " C " + r + ": " + result);
        return result.doubleValue();
    }
    
    private static BigInteger fact(int n){
        BigInteger r = BigInteger.ONE;
        for(int i = n; i > 0; i--) r = r.multiply(BigInteger.valueOf(i));
        //System.out.println("Factorial of " + n + ": " + r);
        return r;
    }
}

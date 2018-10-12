/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zain
 */
public class DBConnection {
    private static Connection conn;
    private static Statement stmt;
    
    public static boolean open(String path){
        //System.out.println("In open db");
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + path);
            //System.out.println("Not opened");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static void close(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ResultSet ExecuteQuery(String query){
        try {
            stmt = conn.createStatement();
            //rs = stmt.executeQuery("SELECT name FROM texts WHERE id=" + id +";");
            return stmt.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}

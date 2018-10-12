/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import view.SingleCardWindow;

/**
 *
 * @author zain
 */
public class Card{
    private String id;
    private JLabel image;
    private String card_name;
    private String card_desc;
    private int copies;
    private ImageIcon icon;
    private boolean in_single_window;
    
    public Card() {
        id = null;
        image = null;
        card_name = null;
        card_desc = null;
        icon = null;
        copies = 0;
        in_single_window = false;
    }
    
    public Card(String id, String card_name, String card_description, int number_of_copies, ImageIcon image){
        this.id = id;
        this.card_name = card_name;
        this.card_desc = card_description;
        this.icon = image;
        this.image = new JLabel(image);
        
        this.copies = number_of_copies;
        this.image.setToolTipText(this.card_name + " @ " + this.copies);
        this.image.addMouseListener(new CardMouseListener(this));
    }
    
    public String GetID(){
        return this.id;
    }
    
    public String GetCardName(){
        return this.card_name;
    }
    
    public String GetCardDescription(){
        return this.card_desc;
    }
    
    public JLabel GetImage(){
        return this.image;
    }
    
    public boolean inSingleWindow(){
        return this.in_single_window;
    }
    
    public void setInSingleWindow(boolean value){
        this.in_single_window = value;
    }
    
    public int GetNumberCopies(){
        return this.copies;
    }
    

    private class CardMouseListener implements MouseListener{
        Card card_pointer;
        public CardMouseListener(Card card){
            this.card_pointer = card;
            //System.out.println("Card pointer: " + this.card_pointer);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            
            System.out.println("Card inside event: " + this.card_pointer.GetCardName());
            if(!this.card_pointer.inSingleWindow()) {
                //System.out.println("OK, let's open it");
                //System.out.println("Inside card event------");
                SingleCardWindow w = new SingleCardWindow(this.card_pointer);
                this.card_pointer.setInSingleWindow(true);
            }
            else {
                System.out.println("Is executing: ");
            } 
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}

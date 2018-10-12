/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


/**
 *
 * @author zain
 */
public abstract class Window extends JFrame {
    protected JPanel upPanel;
    protected JPanel cardsPanel;
    protected JPanel buttonPanel;
    
    
    public Window(){
       this.setLayout(new BorderLayout());
    }

}

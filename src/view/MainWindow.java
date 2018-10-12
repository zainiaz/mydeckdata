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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import src.Card;
import src.DeckData;
/**
 *
 * @author zain
 */
public class MainWindow extends Window implements ActionListener{
    private JButton openButton;
    private final int WEIGHT = 700, HEIGHT = 500;
    private Map<String, Card> cards;
    private List<JPanel> pic_containers;
    private String deck_path;
    private String cdb_path;
    
    private JFileChooser file_chooser;
    
    
    public MainWindow(int weight, int height){
        deck_path = "./deck/L.D.Y. ShouldBe.ydk";
        cdb_path = "./cards.cdb";
        
        StartGUI();
        
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(weight, height);
        this.setVisible(true);

    }
    
    private void StartGUI(){
        super.upPanel = new JPanel();
        super.upPanel.setBackground(Color.LIGHT_GRAY);
        super.upPanel.setLayout(new BorderLayout());
        this.add(super.upPanel, BorderLayout.NORTH);
        
        super.buttonPanel = new JPanel();
        super.buttonPanel.setBackground(Color.red);
        super.buttonPanel.setLayout(new BorderLayout());
        this.add(super.buttonPanel, BorderLayout.SOUTH);
        
        buttonPanel.add(new JButton("BOTON"), BorderLayout.WEST);
        
        addFileChooser();
        
        LoadCardsIcons(deck_path);
 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == openButton){
            System.out.println("Cool");
        }
    }
    
    public void addFileChooser(){
        openButton = new JButton("Open Deck File ...");
        openButton.addActionListener(this);
        openButton.setVisible(true);
        
        upPanel.add(openButton, BorderLayout.WEST);
        
        
        file_chooser = new JFileChooser();
        
        file_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }
    
    private void LoadCardsIcons(String deck_path){
        loadDeckData();
        loadDataBase();
        cards = new HashMap<>();   
        pic_containers = new ArrayList<>();
        
        int ARRAY_SIZE = BestSquareSize(DeckData.size());

        cardsPanel = new JPanel(new GridLayout(ARRAY_SIZE, ARRAY_SIZE, 0, 0));
        
        JPanel aux_panel;
        Set<String> ids = DeckData.getIdSet();
        for(String id : ids){
            cards.put(id, new Card(id, DeckData.getCardName(id),
                DeckData.getCardDescription(id), DeckData.getNumberOfCopies(id),
                new ImageIcon("./pics/" + id + ".jpg")));
    
            aux_panel = new JPanel(new BorderLayout());
            aux_panel.add(cards.get(id).GetImage(), BorderLayout.PAGE_START);
            
            this.cardsPanel.add(aux_panel);
            
            this.pic_containers.add(aux_panel);
        }
        
        this.add(cardsPanel, BorderLayout.CENTER);
    }
    
    private void loadDeckData(){
        DeckData.loadDeckData(deck_path);
    }
    
    private void loadDataBase(){
        DeckData.LoadDataBase(cdb_path);
    }
    
    private void setDeckPath(String deck_path){
        this.deck_path = deck_path;
    }
    
    //For future updates
    private void setDataBasePath(String cdb_path){
        this.cdb_path = cdb_path;
    }
    
    /*
    This method returns the best dimension for a
    JPanel where it will be showing the cards
    */
    private int BestSquareSize(int number){
        int sq_number = (int) (Math.sqrt(number) * 10);
        return (int) Math.ceil(sq_number / 10.0);
    }
    
}

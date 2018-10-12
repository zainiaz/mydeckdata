/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.control.CheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import src.Calcs;
import src.Card;
import src.DeckData;

/**
 *
 * @author zain
 * 
 * Window layout:
 * _________________________________________
 * |            |            |              |
 * |            |            |              |
 * |            |            |              |
 * |  up_panel  | card_panel | button_panel |
 * |            |            |              |
 * |            |            |              |
 * |____________|____________|______________|
 * 
 */
public class SingleCardWindow extends Window{
    private final int WIDTH = 760, HEIGHT = 500;
    private final String[] HAND_SIZES = {"2", "3", "4", "5", "6", "7", "8"};
    private Set<String> OTHER_CARDS_IDS;
    private final int GAP = 4;
    
    //Pointer to Card object
    private Card card;
    
    private String inf_color;
    private String defaul_color;
  
    private JPanel main_panel;
    //Physical components
    // 1 - Image (inside Card objects)
    
    // 2 - Card description
    private JScrollPane comp_card_desc_scrollp;
    private JTextArea comp_card_desc_area;
    
    //3 & 4 - Copies
    private JLabel comp_num_copies_label;
    private SpinnerNumberModel num_copies_model;
    private JSpinner comp_num_copies_spin;
    
    //5 & 6 - Direct searcher 1
    private JLabel comp_searcher01_label;
    private JCheckBox comp_searcher01_check;
    
    //7 & 8 - Direct searcher 2
    private JLabel comp_searcher02_label;
    private JCheckBox comp_searcher02_check;
    
    //9 & 10 - Direct searcher 3
    private JLabel comp_searcher03_label;
    private JCheckBox comp_searcher03_check;
    
    //11 & 12 - Draw along table
    private JLabel comp_draw_along_label;
    private TableModel table_model;
    private JTable comp_draw_along_table;
    private JScrollPane comp_draw_along_scroll;
    
    //13 & 14 - Buttons
    private JButton comp_calc_button;
    private JButton comp_to_clip_button;
    
    //15 & 16 - Deck size
    private JLabel comp_deck_size_label;
    private JSpinner comp_deck_size_spin;
    private SpinnerNumberModel deck_size_model;
    
    //17 & 18 & 19 - Select direct searchers
    private JComboBox comp_selected_searcher01_combo;
    private JComboBox comp_selected_searcher02_combo;
    private JComboBox comp_selected_searcher03_combo;
    
    //20 - Hand size
    private JLabel comp_hand_size_label;
    private JComboBox comp_hand_size_combo;
    
    //21 - Calc results
    private JTextArea comp_results_area;
    private JScrollPane comp_result_scrollp;
    
    //Sub containers
    private JPanel middle_subpanels[];
    private JPanel middle_button_panel; //Aux
    
    private JPanel down_subpanels[];
    private JPanel down_deck_size_aux; //Aux
    private JPanel down_hand_size_aux; //Aux
    
    public SingleCardWindow(Card card) {
        this.card = card;
        
        //System.out.println("Inside SINGLECARDWINDOW- ");
        DeckData.printAllData();
        
        //System.out.println("Card window: " + this.card.GetCardName() + "" + this.card.GetCardDescription());
        
        this.defaul_color = "#191970";
        this.inf_color = "#708090";
        LoadOtherCardsIds();
        
        PrepareGUI();
        
        WindowListener exit_listener = new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Quitting");
                card.setInSingleWindow(false);
            }
        };
        
        this.addWindowListener(exit_listener);
        this.pack();
        this.setResizable(false);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
    }
    
    private void PrepareGUI(){
        PrepareLabels();
        PreparePanels();
        PrepareTextAreas();
        PrepareCheckBoxes();
        PrepareSpinners();
        PrepareComboBoxes();
        PrepareButtons();
        
        PrepareWindow();
        
        
    }
    
    private void PrepareUpPanel(){
        //System.out.println("Card: " + this.card.GetCardName());
        //System.out.println("Number of copies: " + this.card.GetNumberCopies());
        //SpinnerNumberModel (focused, min, max, step)
        
        
        /*
        * ____________
        * |           |
        * |   IMAGE   |
        * |           |
        * |   EFFECT  |
        * |___________|
        */
        
    }
    
    private void PrepareLabels(){
        /*
            Just to remember how to use html on JLabels
            this.comp_num_copies_label = new JLabel(
            "<html>" +
            "<font color=" + this.defaul_color + "> ~ Card: </font>" +
            "<font color=" + this.inf_color + ">" + this.card.GetCardName() + "</font>"+
            "</html>"
        );*/
        this.comp_num_copies_label = new JLabel("   copies in a");
        this.comp_deck_size_label = new JLabel("   card deck");
        this.comp_searcher01_label = new JLabel("Searcher?");
        this.comp_searcher02_label = new JLabel("Searcher?");
        this.comp_searcher03_label = new JLabel("Searcher?");
        this.comp_draw_along_label = new JLabel("Draw along:");
        this.comp_hand_size_label = new JLabel("Hand size");
    }
    
    private void PrepareTextAreas(){
        this.comp_card_desc_area = new JTextArea(this.card.GetCardDescription());
        this.comp_card_desc_area.setWrapStyleWord(true);
        this.comp_card_desc_area.setLineWrap(true);
        this.comp_card_desc_area.setEditable(false);
        this.comp_card_desc_scrollp = new JScrollPane(this.comp_card_desc_area,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        this.comp_results_area = new JTextArea("---NO CALCS TO SHOW---");
        this.comp_results_area.setWrapStyleWord(true);
        this.comp_results_area.setLineWrap(true);
        this.comp_results_area.setEditable(false);
        this.comp_result_scrollp = new JScrollPane(this.comp_results_area,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        this.table_model = new CardTableModel(this.card.GetID());
        this.comp_draw_along_table = new JTable(this.table_model);
        
        this.comp_draw_along_scroll = new JScrollPane(this.comp_draw_along_table);
    }
    
    private void PrepareCheckBoxes(){
        this.comp_searcher01_check = new JCheckBox();
        this.comp_searcher01_check.addActionListener(new CustomActionListener());
        
        this.comp_searcher02_check = new JCheckBox();
        this.comp_searcher02_check.addActionListener(new CustomActionListener());
        
        this.comp_searcher03_check = new JCheckBox();
        this.comp_searcher03_check.addActionListener(new CustomActionListener());
    }
    
    private void PrepareSpinners(){
        // (focused, min, max, step)
        this.num_copies_model = new SpinnerNumberModel(this.card.GetNumberCopies(), 1, 3, 1);
        this.comp_num_copies_spin = new JSpinner(this.num_copies_model);
        
        this.deck_size_model = new SpinnerNumberModel(DeckData.sizeCopiesIncluded(), 20, 60, 1);
        this.comp_deck_size_spin = new JSpinner(this.deck_size_model);
    }
    
    private void PrepareComboBoxes(){
        this.comp_hand_size_combo = new JComboBox(this.HAND_SIZES);
        this.comp_selected_searcher01_combo = new JComboBox();
        this.comp_selected_searcher02_combo = new JComboBox();
        this.comp_selected_searcher03_combo = new JComboBox();
        
        for(String card : OTHER_CARDS_IDS){
            comp_selected_searcher01_combo.addItem(DeckData.getCardName(card));
            comp_selected_searcher02_combo.addItem(DeckData.getCardName(card));
            comp_selected_searcher03_combo.addItem(DeckData.getCardName(card));
        }
        
        this.comp_selected_searcher01_combo.setVisible(false);
        this.comp_selected_searcher02_combo.setVisible(false);
        this.comp_selected_searcher03_combo.setVisible(false);
        
        this.comp_hand_size_combo.setSelectedItem("5");
    }
    
    private void PrepareButtons(){
        this.comp_calc_button = new JButton("Do CALCS!!!");
        this.comp_to_clip_button = new JButton("To Clipboard");
        
        this.comp_calc_button.addActionListener(new CustomActionListener());
        this.comp_to_clip_button.addActionListener(new CustomActionListener());
    }
    
    private void PreparePanels(){
        //Prepare MAIN PANEL
        this.main_panel = new JPanel(new GridLayout(1,3, GAP*2, GAP*2));
        
        //Prepare upPanel
        super.upPanel = new JPanel(new GridLayout(2, 1, GAP, GAP));
    
        //Prepare SUPER cardsPanel and it's subpanels
        super.cardsPanel = new JPanel(new GridLayout(2, 1, GAP, GAP));
        
        this.middle_subpanels = new JPanel[2];
        this.middle_subpanels[0] = new JPanel(new GridLayout(5,2, GAP, GAP));
        this.middle_subpanels[1] = new JPanel(new GridLayout(2, 1, GAP, GAP));
        
        this.middle_button_panel = new JPanel(new GridLayout(1, 2, GAP, GAP));
        
        //Prepare buttonPanel and it's subpanels
        super.buttonPanel = new JPanel(new GridLayout(2, 1, GAP, GAP));
        
        this.down_subpanels = new JPanel[2];
        this.down_subpanels[0] = new JPanel(new GridLayout(5, 1, GAP, GAP));
        this.down_subpanels[1] = new JPanel(new GridLayout(1, 1, GAP, GAP));
        
        this.down_deck_size_aux = new JPanel(new GridLayout(1, 2, GAP, GAP));
        this.down_hand_size_aux = new JPanel(new GridLayout(1, 2, GAP, GAP));
        
    }
    
    private void PrepareWindow(){
        /*upPanel instantiation
        *  _______________
        * |               |
        * |     IMAGE     |
        * |______ ________|
        * |               |
        * |     DESC      |
        * |_______________|
        *
        * GridLayout(rows, columns)
        */
        super.upPanel.add(this.card.GetImage());
        super.upPanel.add(this.comp_card_desc_scrollp);
            
        /*
        * cardsPanel & MIDDLE SUBPANEL
        *  _________________
        * |__LABEL_|__SPIN__| }
        * |__LABEL_|_CHECK__| }
        * |__LABEL_|_CHECK__| } middle_subpanels[0] 5x2
        * |__LABEL_|_CHECK__| }
        * |______LABEL______| }
        * |                 |    }
        * |      TABLE      |    }
        * |_________________|    } middle_subpanel[1] 3x1
        * |        |        |    }
        * |  CALC  |  COPY  |    }
        * |________|________|    }
        */
        this.middle_subpanels[0].add(this.comp_num_copies_spin);
        this.middle_subpanels[0].add(this.comp_num_copies_label);
        this.middle_subpanels[0].add(this.comp_searcher01_label);
        this.middle_subpanels[0].add(this.comp_searcher01_check);
        this.middle_subpanels[0].add(this.comp_searcher02_label);
        this.middle_subpanels[0].add(this.comp_searcher02_check);
        this.middle_subpanels[0].add(this.comp_searcher03_label);
        this.middle_subpanels[0].add(this.comp_searcher03_check);
        this.middle_subpanels[0].add(this.comp_draw_along_label);
        
        this.middle_subpanels[1].add(this.comp_draw_along_scroll);
        this.middle_button_panel.add(this.comp_calc_button);
        this.middle_button_panel.add(this.comp_to_clip_button);
        this.middle_subpanels[1].add(this.middle_button_panel);
        
        super.cardsPanel.add(this.middle_subpanels[0]);
        super.cardsPanel.add(this.middle_subpanels[1]);
        
        /*
        * buttonPanel & DOWNPANEL
        *  __________________
        * |__LABEL__|__SPIN___| }
        * |______COMBO________| } 
        * |______COMBO________| } down_subpanels[0] 5x1
        * |______COMBO________| }
        * |___HAND__|__COMBO__| } 
        * |                   |   } down_subpanel[1] 2x1
        * |_______TEXT________|   }
        * 
        */
        this.down_deck_size_aux.add(this.comp_deck_size_spin);
        this.down_deck_size_aux.add(this.comp_deck_size_label);
        this.down_subpanels[0].add(this.down_deck_size_aux);
        this.down_subpanels[0].add(this.comp_selected_searcher01_combo);
        this.down_subpanels[0].add(this.comp_selected_searcher02_combo);
        this.down_subpanels[0].add(this.comp_selected_searcher03_combo);
        
        
        this.down_hand_size_aux.add(this.comp_hand_size_label);
        this.down_hand_size_aux.add(this.comp_hand_size_combo);
        this.down_subpanels[0].add(this.down_hand_size_aux);

        this.down_subpanels[1].add(this.comp_result_scrollp);
        
        super.buttonPanel.add(this.down_subpanels[0]);
        super.buttonPanel.add(this.down_subpanels[1]);
        
        
        //ADD 3 MAIN PANELS TO WINDOW
        this.main_panel.add(super.upPanel);
        this.main_panel.add(super.cardsPanel);
        this.main_panel.add(super.buttonPanel);
        this.add(this.main_panel, BorderLayout.CENTER);
    }
    
    private void LoadOtherCardsIds(){
        this.OTHER_CARDS_IDS = DeckData.getIdSet();
        this.OTHER_CARDS_IDS.remove(this.card.GetID());
    }
    
    
    
    private class CardTableModel extends AbstractTableModel {
        private Object rowData[][];
        private String columnNames[] = { "Name", "Copies", "Add" };

        public CardTableModel(String except){
            this.rowData = new Object[OTHER_CARDS_IDS.size()][3];
            
            int i = 0;
            for(String current : OTHER_CARDS_IDS){
                this.rowData[i][0] = DeckData.getCardName(current);
                this.rowData[i][1] = DeckData.getNumberOfCopies(current);
                this.rowData[i][2] = false;
                i++;
            }
        }
        

        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int column) {
            return columnNames[column];
        }

        public int getRowCount() {
            return rowData.length;
        }

        public Object getValueAt(int row, int column) {
            return rowData[row][column];
        }

        public Class getColumnClass(int column) {
            return (getValueAt(0, column).getClass());
        }

        public void setValueAt(Object value, int row, int column) {
            rowData[row][column] = value;
        }

        public boolean isCellEditable(int row, int column) {
            return (column != 0);
        }   
    }
    
    private class CustomActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            
            //Get pointer address and determine which elemt called ActionListener
            Object o = e.getSource();
            if(comp_searcher01_check == o)
            {
                if(comp_selected_searcher01_combo.isVisible()) 
                    comp_selected_searcher01_combo.setVisible(false);
                else comp_selected_searcher01_combo.setVisible(true);
            }
            else if(comp_searcher02_check == o)
            {
                if(comp_selected_searcher02_combo.isVisible()) 
                    comp_selected_searcher02_combo.setVisible(false);
                else comp_selected_searcher02_combo.setVisible(true);
            }
            else if(comp_searcher03_check == o)
            {
                if(comp_selected_searcher03_combo.isVisible()) 
                    comp_selected_searcher03_combo.setVisible(false);
                else comp_selected_searcher03_combo.setVisible(true);
            }
            else if(comp_calc_button == o){                
                //DeckData.printAllData();
                //int deck_s, int hand_s, String main_card, Set<String> draw_along, Set<String> searchers
                System.out.println("HERE");
                System.out.println("Deck size: " + (int)comp_deck_size_spin.getValue());
                System.out.println("Hand size: " + Integer.parseInt(comp_hand_size_combo.getSelectedItem().toString()));
                System.out.println("card id:" + card.GetID());
                
                Set<String> da = getDrawAlong();
                Set<String> se = getDirectSearchers();
                
                comp_results_area.setText(Calcs.GetCalcs( (int)comp_deck_size_spin.getValue(), 
                        Integer.parseInt((String)comp_hand_size_combo.getSelectedItem()), 
                        (int)comp_num_copies_spin.getValue() , card.GetID(), da, se));
            }
        }
        
        //Returns a set of cards ids, those the user wants to be drawn along 
        private Set<String> getDrawAlong(){
            /*
              | NAME | COPIES | CHECKED |
            
               __ i __
              |       |
             j|       |
              |_______|
            */
            
            Set<String> da = new HashSet<>(); 
            
            for (int i = 0; i < comp_draw_along_table.getRowCount(); i++){
                if((boolean)comp_draw_along_table.getModel().getValueAt(i, 2)){
                    da.add(DeckData.getCardId((String)comp_draw_along_table.getModel().getValueAt(i, 0)));
                }
            }
            
            if(da.isEmpty()) return null;
            else return da;
        }
        
        //Returns a set of direct searchers
        private Set<String> getDirectSearchers(){
            Set<String> se = new HashSet<>();
            
            if(comp_selected_searcher01_combo.isVisible())
                se.add(DeckData.getCardId((String)comp_selected_searcher01_combo.getSelectedItem()));
            
            if(comp_selected_searcher02_combo.isVisible())
                if(!se.contains( DeckData.getCardId( (String)comp_selected_searcher02_combo.getSelectedItem())))
                {
                    se.add(DeckData.getCardId((String)comp_selected_searcher02_combo.getSelectedItem()));
                }
            
            
            if(comp_selected_searcher03_combo.isVisible())
                if(!se.contains( DeckData.getCardId( (String)comp_selected_searcher03_combo.getSelectedItem())))
                {
                    se.add(DeckData.getCardId( (String)comp_selected_searcher03_combo.getSelectedItem()));
                }
                    
            if(se.isEmpty()) return null;
            else return se;
        }
    }
}

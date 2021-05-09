import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.stream.IntStream;

/**
 * This class renders the card game gui.
 * 
 * @author Manyan Chan
 */
public class CardGameGUI {
    // Labels initialed
    JLabel label_Image1 = new JLabel();
    JLabel label_Image2 = new JLabel();
    JLabel label_Image3 = new JLabel();
    JLabel label_Image4 = new JLabel();
    JLabel label_Image5 = new JLabel();
    JLabel label_Image6 = new JLabel();
    JLabel[] label_Images = { label_Image1, label_Image2, label_Image3, label_Image4, label_Image5, label_Image6 };
    JLabel label_Bet = new JLabel("Bet: $");
    JLabel label_Info = new JLabel();
    JLabel label_Money = new JLabel();

    // Buttons initialized
    JButton btn_Card1 = new JButton("Replace Card 1");
    JButton btn_Card2 = new JButton("Replace Card 2");
    JButton btn_Card3 = new JButton("Replace Card 3");
    JButton btn_Start = new JButton("Start");
    JButton btn_Result = new JButton("Result");

    // TextField initialized
    JTextField txt_Inputbet = new JTextField(10);

    // ImageIcon initialized
    ImageIcon image1 = new ImageIcon("Images/card_back.gif");
    ImageIcon image2 = new ImageIcon("Images/card_back.gif");
    ImageIcon image3 = new ImageIcon("Images/card_back.gif");
    ImageIcon image4 = new ImageIcon("Images/card_back.gif");
    ImageIcon image5 = new ImageIcon("Images/card_back.gif");
    ImageIcon image6 = new ImageIcon("Images/card_back.gif");
    ImageIcon[] images = { image1, image2, image3, image4, image5, image6 };

    // Panel initialized
    JPanel MainPanel = new JPanel();
    JPanel DealerPanel = new JPanel();
    JPanel PlayerPanel = new JPanel();
    JPanel RpCardBtnPanel = new JPanel();
    JPanel ButtonPanel = new JPanel();
    JPanel InfoPanel = new JPanel();

    // Menubar initialized
    JMenuBar MenuBar = new JMenuBar();
    JMenu menuControl = new JMenu("Control");
    JMenuItem menuHelp = new JMenuItem("Help");
    JMenuItem mItemQuit = new JMenuItem("Quit");

    // template Strings
    String infoInit = "Please place you bet! ";
    String infoBet = "Your current bet is: $%d ";
    String moneyTotal = "Amount of money you have: $%d";

    // create new game object
    CardGameCore gme = new CardGameCore();

    public void go() {
        // Set label_images' icons
        for (int i = 0; i < images.length; i++) {
            label_Images[i].setIcon(images[i]);
        }

        // Set info and money
        gme.setMoney(100);
        label_Info.setText(infoInit);
        label_Money.setText(String.format(moneyTotal, gme.getMoney()));

        // Add Listeners
        mItemQuit.addActionListener(new QuitListener());
        menuHelp.addActionListener(new RuleListener());
        btn_Start.addActionListener(new StartListener());
        btn_Card1.addActionListener(new RPCard1Listener());
        btn_Card2.addActionListener(new RPCard2Listener());
        btn_Card3.addActionListener(new RPCard3Listener());
        btn_Result.addActionListener(new ResultListener());

        // Allocate visual elements
        DealerPanel.add(label_Image1);
        DealerPanel.add(label_Image2);
        DealerPanel.add(label_Image3);
        PlayerPanel.add(label_Image4);
        PlayerPanel.add(label_Image5);
        PlayerPanel.add(label_Image6);
        RpCardBtnPanel.add(btn_Card1);
        RpCardBtnPanel.add(btn_Card2);
        RpCardBtnPanel.add(btn_Card3);
        ButtonPanel.add(label_Bet);
        ButtonPanel.add(txt_Inputbet);
        ButtonPanel.add(btn_Start);
        ButtonPanel.add(btn_Result);
        InfoPanel.add(label_Info);
        InfoPanel.add(label_Money);
        DealerPanel.setBackground(Color.GREEN);
        PlayerPanel.setBackground(Color.GREEN);
        RpCardBtnPanel.setBackground(Color.GREEN);
        MainPanel.setLayout(new GridLayout(5, 1));
        MainPanel.add(DealerPanel);
        MainPanel.add(PlayerPanel);
        MainPanel.add(RpCardBtnPanel);
        MainPanel.add(ButtonPanel);
        MainPanel.add(InfoPanel);
        menuControl.add(mItemQuit);
        MenuBar.add(menuControl);
        MenuBar.add(menuHelp);

        // Render frame
        JFrame frame = new JFrame();
        frame.setJMenuBar(MenuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(MainPanel);
        frame.setTitle("A Simple Card Game");
        frame.setSize(500, 700);
        frame.setVisible(true);
    }

    /**
     * This class is responsible for the start button functionality.
     */
    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (gme.getGameState() == 0) {
                Integer value = parseInt(txt_Inputbet.getText());
                if (value == null) {
                    JFrame pop_up = new JFrame();
                    pop_up.setSize(500, 700);
                    JOptionPane.showMessageDialog(pop_up, "WARNING: The bet you place must be a positive integer!");
                } else if (value > gme.getMoney()) {
                    JFrame pop_up = new JFrame();
                    pop_up.setSize(500, 700);
                    JOptionPane.showMessageDialog(pop_up,
                            "WARNING: The bet you place must be lower than money you have!");
                } else {
                    gme.startRound(value);
                    label_Info.setText(String.format(infoBet, value));
                    Card[] deck = gme.getCards();
                    for (int i = 3; i < 6; i++) {
                        label_Images[i].setIcon(deck[i].Image());
                    }
                }
                txt_Inputbet.setText("");
            }
        }
    }

    /**
     * This class is responsible for the result button functionality
     */
    class ResultListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (gme.getGameState() == 1) {
                for (int i = 0; i < 3; i++) {
                    label_Images[i].setIcon(gme.getCards()[i].Image());
                }
                int result = gme.evaluate();
                if (result == 0) { // Dealer wins
                    gme.setMoney(gme.getMoney() - gme.getBet());
                    if (gme.getMoney() == 0) {
                        gme.setGameState(2); // game over
                        label_Info.setText("You have no more money! ");
                        label_Money.setText("Please start a new game!");
                        JFrame pop_up = new JFrame();
                        pop_up.setSize(500, 700);
                        String text = "Game Over!\n" + "You have no more money!\n" + "Please start a new game!";
                        JOptionPane.showMessageDialog(pop_up, text);
                    } else {
                        JFrame pop_up = new JFrame();
                        pop_up.setSize(500, 700);
                        JOptionPane.showMessageDialog(pop_up, "Sorry! The dealer wins this round!");
                        gme.setGameState(0);
                        label_Info.setText(infoInit);
                        label_Money.setText(String.format(moneyTotal, gme.getMoney()));
                    }
                } else { // Player wins
                    gme.setMoney(gme.getBet() + gme.getMoney());
                    JFrame pop_up = new JFrame();
                    pop_up.setSize(500, 700);
                    JOptionPane.showMessageDialog(pop_up, "Congratulations: You win this round");
                    gme.setGameState(0);
                    label_Info.setText(infoInit);
                    label_Money.setText(String.format(moneyTotal, gme.getMoney()));
                }
                for (int i = 0; i < images.length; i++) {
                    label_Images[i].setIcon(images[i]);
                }
            }
        }
    }

    /**
     * This class is responsible for the replace card button 1 functionality
     */
    class RPCard1Listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int[] counter = gme.getCounter();
            int sum = IntStream.of(counter).sum();
            if (sum < 2 && counter[0] != 1 && gme.getGameState() == 1) {
                counter[0] = 1;
                gme.swapCards(3, 6 + sum);
                Card[] deck = gme.getCards();
                for (int i = 3; i < 6; i++) {
                    label_Images[i].setIcon(deck[i].Image());
                }
                gme.setCounter(counter);
            }
        }
    }

    /**
     * This class is responsible for the replace card button 2 functionality
     */
    class RPCard2Listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int[] counter = gme.getCounter();
            int sum = IntStream.of(counter).sum();
            if (sum < 2 && counter[1] != 1 && gme.getGameState() == 1) {
                counter[1] = 1;
                gme.swapCards(4, 6 + sum);
                Card[] deck = gme.getCards();
                for (int i = 3; i < 6; i++) {
                    label_Images[i].setIcon(deck[i].Image());
                }
                gme.setCounter(counter);
            }
        }
    }

    /**
     * This class is responsible for the replace card button 3 functionality
     */
    class RPCard3Listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int[] counter = gme.getCounter();
            int sum = IntStream.of(counter).sum();
            if (sum < 2 && counter[2] != 1 && gme.getGameState() == 1) {
                counter[2] = 1;
                gme.swapCards(5, 6 + sum);
                Card[] deck = gme.getCards();
                for (int i = 3; i < 6; i++) {
                    label_Images[i].setIcon(deck[i].Image());
                }
                gme.setCounter(counter);
            }
        }
    }

    /**
     * This class is responsible for the quit button functionality
     */
    class QuitListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    /**
     * This class is responsible for the help button functionality
     */
    class RuleListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String rule = "J, Q, K are regarded as special cards.\n" + "Rule 1: The one with more special cards wins.\n"
                    + "Rule 2: If both have the same number of special cards, add the face values of the other card(s) and take the remainder after dividing the sum by 10.\n"
                    + "The one with a bigger remainder wins. (Note: Ace = 1).\n"
                    + "Rule 3: The dealer wins if both rule 1 and rule 2 cannot distinguish the winner.";
            JFrame pop_up = new JFrame();
            pop_up.setSize(500, 700);
            JOptionPane.showMessageDialog(pop_up, rule);
        }
    }

    /**
     * This method returns an Integer if the input string can be type casted to a
     * positive integer. Otherwise, the method return null.
     * 
     * @param str
     * @return Integer
     */
    public Integer parseInt(String str) {
        try {
            Integer value = Integer.parseInt(str);
            if (value > 0) { // Valid input
                return value;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
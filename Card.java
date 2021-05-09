import javax.swing.ImageIcon;

public class Card {
    private int suit; // suit: 1 - 4 {1:Club, 2:Spade, 3:Diamond, 4:Heart}
    private int number; // 1 - 13
    private int special; // 1: special; 0:not special

    /**
     * This method returns the suit value
     * 
     * @return int
     */
    public int getSuit() {
        return suit;
    }

    /**
     * This method returns the special value
     * 
     * @return int
     */
    public int getSpecial() {
        return special;
    }

    /**
     * This method returns the number value
     * 
     * @return int
     */
    public int getNumber() {
        return number;
    }

    /**
     * This method returns an ImageIcon base on the number value and suit value
     * 
     * @return ImageIcon
     */
    public ImageIcon Image() {
        String path = "Images/card_" + String.valueOf(suit) + String.valueOf(number) + ".gif";
        return (new ImageIcon(path));
    }

    /**
     * This method create a new Card object with an integer input
     * 
     * @param input
     */
    public Card(int input) {
        suit = input / 13 + 1;
        number = input % 13 + 1;
        special = number > 10 ? 1 : 0;
    }
}
import java.util.Random;

public class CardGameCore {
    private int money;
    private int bet;
    private Card[] deckOfCards = new Card[8];
    private int[] counter = { 0, 0, 0 };
    private int gameState = 0; // 0: initial, 1: pressed start, 2: game over

    CardGameCore() {
        generateNewDeck();
    }

    /**
     * This method starts a game round by setting game state to 1, creates a new
     * deck, sets the bet and reset the counter for replacing cards
     * 
     * @param bet
     */
    public void startRound(int bet) {
        bet = this.bet;
        resetCounter();
        generateNewDeck();
        gameState = 1;
    }

    public void resetCounter() {
        for (int i = 0; i < 3; i++) {
            counter[i] = 0;
        }
    }

    /**
     * This method randomly generates a deck of cards and put the save the first 8
     * to the Card array
     */
    public void generateNewDeck() {
        int[] tempList = new int[52];
        for (int i = 0; i < tempList.length; i++) {
            tempList[i] = i;
        }
        Random rand = new Random();
        for (int i = 0; i < tempList.length; i++) { // randomly swap positions of numbers
            int randIndex = rand.nextInt(tempList.length);
            int temp = tempList[randIndex];
            tempList[randIndex] = tempList[i];
            tempList[i] = temp;
        }
        for (int i = 0; i < 8; i++) { // create 8 cards
            deckOfCards[i] = new Card(tempList[i]);
        }
    }

    /**
     * This method evalute whether the player or the dealer wins
     * 
     * @return int
     */
    public int evaluate() {
        int playerWin = 1;
        int dealerWin = 0;
        int dealerSp = 0;
        int playerSP = 0;
        for (int i = 0; i < 3; i++)
            dealerSp += deckOfCards[i].getSpecial();
        for (int i = 3; i < 6; i++)
            playerSP += deckOfCards[i].getSpecial();
        if (dealerSp > playerSP)
            return dealerWin;
        else if (playerSP > dealerSp)
            return playerWin;
        else {
            int playerFV = 0;
            int dealerFV = 0;
            for (int i = 0; i < 3; i++) {
                if (deckOfCards[i].getNumber() < 11)
                    dealerFV += deckOfCards[i].getNumber();
            }
            for (int i = 3; i < 6; i++) {
                if (deckOfCards[i].getNumber() < 11)
                    playerFV += deckOfCards[i].getNumber();
            }
            playerFV %= 10;
            dealerFV %= 10;
            if (playerFV > dealerFV)
                return playerWin;
            else
                return dealerWin;
        }
    }

    /**
     * This method returns the Money value
     * 
     * @return int
     */
    public int getMoney() {
        return money;
    }

    /**
     * This method returns the Bet value
     * 
     * @return int
     */
    public int getBet() {
        return bet;
    }

    /**
     * This method returns the deckOfCard array
     * 
     * @return Card[]
     */
    public Card[] getCards() {
        return deckOfCards;
    }

    /**
     * This method returns the counter array
     * 
     * @return int[]
     */
    public int[] getCounter() {
        return counter;
    }

    /**
     * This method sets the counter array
     * 
     * @param i
     */
    public void setCounter(int[] i) {
        counter = i;
    }

    /**
     * This method sets the Money int
     * 
     * @param i
     */
    public void setMoney(int i) {
        money = i;
    }

    /**
     * This method sets the gameState int
     * 
     * @param i
     */
    public void setGameState(int i) {
        gameState = i;
    }

    /**
     * This method swaps the positions of two Card in an Card array, built for
     * replacing cards
     * 
     * @param i
     * @param j
     */
    public void swapCards(int i, int j) {
        Card temp = deckOfCards[i];
        deckOfCards[i] = deckOfCards[j];
        deckOfCards[j] = temp;
    }

    /**
     * This method returns the gameState value
     * 
     * @return int
     */
    public int getGameState() {
        return gameState;
    }
}

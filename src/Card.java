public class Card {
    private final String cardNumber;
    private final String PIN;
    private int balance;

    public Card(String cardNumber, String PIN, int balance) {
        this.cardNumber = cardNumber;
        this.PIN = PIN;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPIN() {
        return PIN;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    @Override
    public String toString(){
        return cardNumber + " " + PIN + " " + balance;
    }
}

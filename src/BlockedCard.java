import java.time.LocalDate;

public class BlockedCard {
    private String cardNumber;
    private LocalDate date;

    public BlockedCard(String cardNumber, LocalDate date) {
        this.cardNumber = cardNumber;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    @Override
    public String toString(){
        return cardNumber + " " + date;
    }
}

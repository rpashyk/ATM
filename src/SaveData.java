import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SaveData {
    public static void SaveCardsData(List<Card> cards) {
        String fileName = "data/Cards.txt";
        try(FileWriter writer = new FileWriter(fileName, false)) {
            for(Card card: cards){
                writer.write(card + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Error 'SaveCardsData()': " + e.getMessage());
        }
    }

    public static void SaveBlockedCardsData(List<BlockedCard> blocked) {

        String fileName = "data/BlockedCards.txt";
        try (FileWriter writer = new FileWriter(fileName, false)){
            for(BlockedCard card: blocked){
                writer.write(card + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error 'SaveBlockedCardsData()': " + e.getMessage());
        }

    }

    public static void SaveMoneyData(Map<Integer, Integer> listOfMoney) {
        String fileName = "data/Money.txt";
        try (FileWriter writer = new FileWriter(fileName, false)){
            for (int key : listOfMoney.keySet()) {
//                System.out.println(key + " " + listOfMoney.get(key));
                writer.write(key + " " + listOfMoney.get(key) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error 'SaveMoneyData()': " + e.getMessage());
        }
    }
}

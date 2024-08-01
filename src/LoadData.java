import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class LoadData {
    public static List<Card> LoadCardsData(){
        List<Card> cards = new ArrayList<>();
        String fileName = "data/Cards.txt";
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fr);
            String line;
            while((line = bf.readLine()) != null){
                String[] spl = line.split(" ");
                cards.add(new Card(spl[0], spl[1], Integer.parseInt(spl[2])));
            }
            bf.close();
            fr.close();
        }
        catch (IOException e){
            System.out.println("Error 'LoadCardsData()': " + e.getMessage());
        }
        return cards;
    }

    public static List<BlockedCard> LoadBlockedCardsData(){
        List<BlockedCard> blocked = new ArrayList<>();
        String fileName = "data/BlockedCards.txt";
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fr);
            String line;
            while((line = bf.readLine()) != null){
                String[] spl = line.split(" ");
                if(LocalDate.parse(spl[1]).equals(LocalDate.now())){
                    blocked.add(new BlockedCard(spl[0], LocalDate.parse(spl[1])));
                }
            }
            bf.close();
            fr.close();
        }
        catch (IOException e){
            System.out.println("Error 'LoadBlockedCardsData()': " + e.getMessage());
        }

        return blocked;
    }

    public static TreeMap<Integer, Integer> LoadMoneyData(){
        HashMap<Integer, Integer> money = new HashMap<>();
        String fileName = "data/Money.txt";
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fr);
            String line;
            while((line = bf.readLine()) != null){
                String[] spl = line.split(" ");
                money.put(Integer.parseInt(spl[0]), Integer.parseInt(spl[1]));
            }
            bf.close();
            fr.close();
        }
        catch (IOException e){
            System.out.println("Error 'LoadMoneyData()': " + e.getMessage());
        }

        return new TreeMap<Integer, Integer>(money);
    }
}

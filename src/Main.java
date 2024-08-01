import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //Считываем данные из файлов
        List<Card> cards = LoadData.LoadCardsData();
        List<BlockedCard> blocked = LoadData.LoadBlockedCardsData();
        Map<Integer, Integer> money = LoadData.LoadMoneyData();

        //do{ //сначала хотел добавить цикл, но потом передумал
        Menu.start(cards, blocked, money);

        //Сохраняем данные в файл
        SaveData.SaveCardsData(cards);
        SaveData.SaveBlockedCardsData(blocked);
        SaveData.SaveMoneyData(money);
        //while(true);
    }
}

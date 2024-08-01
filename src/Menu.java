import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class Menu {
    public static void start(List<Card> listOfCards, List<BlockedCard> listOfBlockedCards, Map<Integer, Integer> listOfMoney ){
        Scanner sc = new Scanner(System.in);
        System.out.println("Здравствуйте!");
        Card card;

        try{
            do {
                System.out.print("Введите номер карты (XXXX-XXXX-XXXX-XXXX) или 0 для выхода: ");
                String in = sc.next();
                if(in.equals("0")) {
                    System.out.println("Досвидания!");
                    return;
                }
                if(!Pattern.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$", in)){
                    System.out.println("Неверный формат ввода.");
                    continue;
                }
                card = isTheCardExist(in, listOfCards);
                if(card == null){
                    addCard(listOfCards, in);
                }
                else if(isTheCardBlocked(card, listOfBlockedCards)){
                    System.out.println("Ваша карта заблокирована");
                }
                else {
                    break;
                }
            }while (true);

            for(int i = 0; i < 3; i++) {
                System.out.print("Введите PIN: ");
                String pin = sc.next();
                if (!card.getPIN().equals(pin)) {
                    System.out.print("Неверный PIN. ");
                } else {
                    menu(card, listOfMoney);
                    break;
                }
                if (i == 2) {
                    System.out.println("Вы ввели неверный пароль 3 раза. Ваша карта заблокирована");
                    listOfBlockedCards.add(new BlockedCard(card.getCardNumber(), LocalDate.now()));
                    start(listOfCards, listOfBlockedCards, listOfMoney);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    private static void addCard(List<Card> listOfCards, String cardNumber){
        System.out.print("Такой карты не существует. ");

        if(algorithmLuhn(cardNumber)){
            Scanner sc = new Scanner(System.in);

            do {
                System.out.println("Желаете добавить карту? (y - да, n - нет)");
                String in = sc.next();
                if (in.equals("y")) {
                    String PIN = generatePIN();
                    System.out.println("Номер Вашей карты: " + cardNumber);
                    System.out.println("Ваш PIN-код: " + PIN);
                    listOfCards.add(new Card(cardNumber, PIN, 0));
                    break;
                } else if (in.equals("n")) {
                    break;
                } else System.out.println("Неверный ввод");
            }while (true);
        }
        else System.out.println("Номер не соответствует алгоритму Луна");

    }
    private static String generatePIN(){
        Random random = new Random();
        int pin = random.nextInt(10000);
        StringBuilder sb = new StringBuilder();
        sb.append(pin);
        while(sb.length() < 4){
            sb.insert(0, '0');
        }
        return sb.toString();
    }
    private static boolean algorithmLuhn(String card){
        card = card.replace("-", "");

        int sum = 0;
        for(int i = 0; i < card.length(); i++){
            int temp = Integer.parseInt(card.substring(i,i + 1));
            if(i % 2 == 0){
                temp *= 2;
                sum += temp >= 10? (temp % 10 + temp / 10): temp;
            }
            else {
                sum += temp;
            }
        }
        return sum % 10 == 0;
    }

    private static void menu(Card card, Map<Integer, Integer> listOfMoney){
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        do{
            System.out.println("-----------------------------------------\n" +
                    "1. Баланс\t" +
                    "2. Вывод средств\n" +
                    "3. Пополнение\t" +
                    "4. Выход");
            System.out.print("Введите номер операции: ");
            String in = sc.next();
            switch (in){
                case "1": checkBalance(card); break;
                case "2": withdraw(card, listOfMoney); break;
                case "3": refill(card); break;
                case "4":
                    System.out.println("Досвидания!");
                    flag = false;
                    break;
                default:
                    System.out.println("Такого пункта нет.");
            }
        }while(flag);
    }
    private static void checkBalance(Card card){
        System.out.println("Баланс: " + card.getBalance());
    }
    private static void withdraw(Card card, Map<Integer, Integer> listOfMoney){
        if(card.getBalance() < 1) {
            System.out.print("Вывод средств недоступен. ");
            checkBalance(card);
            return;
        }

        //сумма в банкомате
        int sum = 0;
        for (int key : listOfMoney.keySet()) {
            sum += key * listOfMoney.get(key);
        }

        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        int in;
        do{
            System.out.print("Введите сумму для вывода: ");

            try {
                in = Integer.parseInt(sc.next());
                if(in == 0) break;
                if(in < 1){
                    System.out.println("Неверное значение. Введите положительное число");
                }
                else if(in > sum){
                    System.out.println("В банкомате нет столько денег. " +
                            "Приносим свои извинения :(");
                }
                else if(in > card.getBalance()){
                    System.out.println("Недостаточно средств.");
                }
                else if(!algorithm(listOfMoney, in)){
                    card.setBalance(card.getBalance() - in);
                    flag = false;
                }

            }catch(Exception ex) {
                System.out.println("Неверный ввод. Error: " + ex.getMessage());
            }
        }while (flag);
    }

    private static boolean algorithm(Map<Integer, Integer> listOfMoney, int amount){
        List<Integer> list = new ArrayList<>();
        for(int key: listOfMoney.keySet()){
            for(int i = 0; i < listOfMoney.get(key); i++){
                list.add(key);
            }
        }
        Collections.reverse(list);

        HashMap<Integer, Integer> hmap = new HashMap<>();
        hmap.put(0,0);
        for (int a: list){
            HashMap<Integer, Integer> temp = new HashMap<>();

            for(int key: hmap.keySet()){
                int newKey = key + a;

                if(newKey > amount) continue;

                if(!hmap.containsKey(newKey)){
                    temp.put(newKey, a);
                }
            }
            hmap.putAll(temp);
        }

        if(hmap.containsKey(amount)) {
            System.out.println("Вам выдало купюры:");
            do {
                int temp = hmap.get(amount);
                System.out.print(temp + " ");
                listOfMoney.put(temp, listOfMoney.get(temp) - 1);
                amount -= temp;
            } while (amount > 0);
            System.out.println();

        }
        else{
            System.out.println("Нет подходщих купюр.\n" +
                    "Имеются купюры:");
            for (int key : listOfMoney.keySet()) {
                if(listOfMoney.get(key) > 0)
                    System.out.print(key + " ");
            }
            System.out.println();
            return true;
        }
        return false;
    }
    private static void refill(Card card){
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        do{
            System.out.print("Введите сумму пополнения: ");
            int in;
            try {
                in = Integer.parseInt(sc.next());
                if(in < 0 || in > 1000000){
                    System.out.println("Неверный значение");
                }
                else{
                    card.setBalance(card.getBalance() + in);
                    flag = false;
                }
            }catch(Exception ex) {
                System.out.println("Неверный ввод. Error: " + ex.getMessage());
            }
        }while (flag);
    }

    private static Card isTheCardExist(String cardNum, List<Card> cards){
        cardNum = cardNum.replace("-", "");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 16; i++){
            sb.append(cardNum.charAt(i));
            if(i == 3 || i == 7 || i == 11)
                sb.append('-');
        }
        cardNum = sb.toString();
        for(Card card: cards){
            if(card.getCardNumber().equals(cardNum)){
                return card;
            }
        }
        return null;
    }
    private static boolean isTheCardBlocked(Card card, List<BlockedCard> listOfBlockedCards){
        for(BlockedCard bCard : listOfBlockedCards){
            if(bCard.getCardNumber().equals(card.getCardNumber())){
                LocalDate ld = LocalDate.now();
                if(ld.equals(bCard.getDate())){
                    return true;
                }
            }
        }
        return false;
    }

}

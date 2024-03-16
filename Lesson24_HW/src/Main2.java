import java.util.*;

public class Main2 {
    public static final String NAME = "[А-я ]+";
    public static final String NUM_REG = "(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})";
    private static final Map<String, Set<String>> phoneBook = new TreeMap<>();


    public static void main(String[] args) {

        System.out.println("\t***Телефонный справочник***");
        System.out.println("Вводите номер телефона в формате +7*** *** ** **" +
                "\nИмя вводите русскими буквами" +
                "\nКоманды: \"LIST\" - распечатать список абонентов , \"CLEAR\" - очистить список,\n" +
                "\"STOP\" - остановить программу" +
                "\n");
        while (true) {

            System.out.print("\nВведите имя, номер или команду: ");
            String input = new Scanner(System.in).nextLine().trim();
            if (input.equalsIgnoreCase("stop")) {
                System.out.println("\nСпасибо, что воспользовались нашим телефонным справочником." +
                        "\n\t\t\t\tДо свидания!");
                return;
            }
            if (input.equalsIgnoreCase("clear")) {
                phoneBook.clear();
                System.out.println("Список очищен");
                continue;
            }
            if (input.equalsIgnoreCase("list")) {
                printAll();
                continue;
            }
            if (input.matches(NAME)) {
                addByName(input);
                continue;
            }
            String number = numberCheck(input);
            if (number.equals("wrong")) {
                System.out.println("Неверный ввод!");
            } else {
                addByNumber(number);
            }
        }
    }

    public static void addByNumber(String number) {
        for (Map.Entry<String, Set<String>> num : phoneBook.entrySet()) {
            if (num.getValue().contains(number)) {
                System.out.println("Номер " + number + " есть в справочнике у абонента " + num.getKey());
                return;
            }
        }
        System.out.println("Номера " + number + " нет в справочнике. Введите имя абонента для номера " + number);
        String name = new Scanner(System.in).nextLine();
        if(name.matches(NAME)) {
            name = name.toUpperCase();
            addAll(name, number);
            return;
        }
        System.out.println("Неправильный ввод!");
    }


    public static String numberCheck(String number) {
        number = number.replaceAll("\\D+", "");
        if (number.length() == 11) {
            number = number.replaceAll(NUM_REG, "+7($2)$3-$4-$5");
            return number;
        }
        return "wrong";
    }

    public static void addByName(String name) {
        name = name.toUpperCase();
        if (phoneBook.containsKey(name)) {
            System.out.println("Абонент " + name + " есть в справочнике. Добавьте новый номер для абонента " + name);
            while (true) {
                String number = new Scanner(System.in).nextLine();
                number = numberCheck(number);
                System.out.println(number);
                if (!number.equals("wrong")) {
                    for (Map.Entry<String, Set<String>> numbers : phoneBook.entrySet()) {
                        if (numbers.getValue().contains(number)) {
                            System.out.println("Номер " + number + " уже есть в справочнике");
                            return;
                        }
                    }
                    phoneBook.get(name).add(number);
                    System.out.println("Номер " + number + " добавлен абоненту " + name);
                    return;

                } else {
                    System.out.println("Неверный ввод. Введите номер ещё раз");
                }
            }
        } else {
            System.out.println("Абонента " + name + " нет в справочнике. Введите номер для абонента " + name);
            while (true) {
                String number = new Scanner(System.in).nextLine();
                number = numberCheck(number);
                if (!number.equals("wrong")) {
                    for (Map.Entry<String, Set<String>> numbers : phoneBook.entrySet()) {
                        if (numbers.getValue().contains(number)) {
                            System.out.println("Такой номер уже есть в справочнике у абонента " + numbers.getKey());
                            return;
                        }
                    }
                    addAll(name, number);

                    return;
                } else {
                    System.out.println("Неверный ввод. Введите номер ещё раз:");
                }
            }
        }
    }


    public static void addAll(String name, String number) {
        if (phoneBook.containsKey(name)) {
            phoneBook.get(name).add(number);
            System.out.println("Номер " + number + " добавлен абоненту " + name);
            return;
        }
        Set<String> numbers = new TreeSet<>();
        numbers.add(number);
        phoneBook.put(name, numbers);
        System.out.println("Абонент " + name + " добавлен в справочник");
        System.out.println("Номер " + number + " добавлен абоненту " + name);
    }


    public static void printAll() {
        if (phoneBook.isEmpty()) {
            System.out.println("Список пуст!");
            return;
        }
        System.out.println("\n\t***печать списка***");
        for (Map.Entry<String, Set<String>> names : phoneBook.entrySet()) {                  //проверяю всю мапу, получаю все ключи и значения
                         // System.out.println(names.getKey() + " " + names.getValue());     //печатаю ключи и их значения(все сразу)
            System.out.println(names.getKey());                                               //печатаю ключи
            for (String s : names.getValue()) {                                               //проверяю все значения в сете
    System.out.println("\t" + s);                                                             //печатаю значения сета по одному
            }
        }

        // for (String nums = names.getValue()) {//проверяю значения
        //   System.out.println(nums);//печатаю значения

    }
}



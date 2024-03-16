import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static final String NUMBER = "(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})";
    public static final String NAME = "[А-я ]+";
    private static final Map<String, String> phoneBook = new TreeMap<>();

    public static void main(String[] args) {

        System.out.println("\t***Телефонный справочник***");
        System.out.println("Вводите номер телефона в формате +7*** *** ** ** или 8*** *** ** **" +
                "\nИмя вводите русскими буквами" +
                "\nКоманды: \"LIST\" - распечатать список абонентов и \"STOP\" - остановить программу" +
                "\n\"BACK\" - вернуться в начало программы\n");

        while (true) {
            System.out.print("Введите имя, номер или команду: ");
            String input = new Scanner(System.in).nextLine().trim();
            if (input.equalsIgnoreCase("stop")) {
                System.out.println("\nСпасибо, что воспользовались нашим телефонным справочником." +
                        "\n\t\t\t\tДо свидания!");
                return;
            }
            if (input.equalsIgnoreCase("list")) {
                printAll();
                continue;
            }
            if (input.matches(NAME)) {
                nameCheck(input);
                continue;
            }
            String number = numberCheck(input);
            if (number.equals("wrong")) {
                System.out.println("Неверный ввод!");
            } else {
                addNumber(number);
            }
        }
    }

    public static String numberCheck(String number) {
        number = number.replaceAll("\\D+", "").trim();
        if (number.length() == 11 && (number.charAt(0) == '7' || number.charAt(0) == '8')) {
            return number.replaceAll(NUMBER, "+7($2)$3-$4-$5");
        }
        return "wrong";
    }


    public static void nameCheck(String inputName) {
        String name = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
        if (phoneBook.containsKey(name)) {
            System.out.println("\t" + name + " - " + phoneBook.get(name));
        } else {
            System.out.println("Имени " + "\"" + name + "\"" + " нет в справочнике. Введите номер для этого имени:");
            while (true) {
                String number = new Scanner(System.in).nextLine().trim();
                if (number.equalsIgnoreCase("back")) {
                    return;
                }
                String number2 = numberCheck(number);
                if (number2.equals("wrong")) {
                    System.out.println("Неверный ввод! Введите номер ещё раз:");
                } else if (phoneBook.containsValue(number2)) {
                    System.out.println("Такой номер уже есть в справочнике" +
                            "\nВведите другой номер:");
                } else {
                    add(name, number2);
                    return;
                }
            }
        }
    }

    public static void add(String name, String number) {
        if (phoneBook.containsKey(name)) {
            System.out.println("Имя " + name + " eсть в справочнике. Изменить старый номер на новый? 1-да 2-нет");
            String yes = new Scanner(System.in).next();
            if (yes.equals("1")) {
                phoneBook.put(name, number);
                System.out.println("На имя " + name + " записан новый номер " + number);
                return;
            }
            System.out.println("На имя " + name + " оставлен старый номер");
            return;
        }
        phoneBook.put(name, number);
        System.out.println("Имя " + name + " внесено в справочник на номер " + number);
    }


    public static void addNumber(String number) {
        if (phoneBook.containsValue(number)) {
            for (String printName : phoneBook.keySet()) {
                if (phoneBook.get(printName).equalsIgnoreCase(number)) {
                    System.out.println("\t" + printName + " - " + number);
                }
            }
        } else {
            System.out.println("Номера " + number + " нет в справочнике. Введите имя для этого номера:");
            while (true) {
                String name = new Scanner(System.in).nextLine().trim();
                if (name.equalsIgnoreCase("back")) {
                    return;
                } else if (name.matches(NAME)) {
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    add(name, number);
                    return;
                } else
                    System.out.print("Введите имя русскими буквами: ");
            }
        }
    }

    public static void printAll() {
        if (phoneBook.isEmpty()) {
            System.out.println("Список пуст!");
            return;
        }
        for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
            System.out.println("\t" + entry.getKey() + " - " + entry.getValue());
        }
    }
}
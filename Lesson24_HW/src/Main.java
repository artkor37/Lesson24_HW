import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

            public static final String CORRECT_NUMBER = "[\\d()+ -]+";
            public static final String CORRECT_NAME = "[А-я ]+";
            private static boolean isContinue = false;
            private static final Map<String, String> phoneBook = new TreeMap<>();

            public static void main(String[] args) {
                System.out.println("\t***Телефонный справочник***");
                System.out.println("Вводите номер телефона в формате +7*** *** ** ** или 8*** *** ** **" +
                        "\nИмя русскими буквами" +
                        "\nКоманды: \"list\" - распечатать список абонентов и \"stop\" - остановить программу\n");
                while (!isContinue) {
                    System.out.print("Введите имя, номер или команду:");
                    String input = new Scanner(System.in).nextLine();
                    inputCheck(input);

                }
            }

            public static void inputCheck(String input) {
                if (input.equalsIgnoreCase("stop")) {
                    System.out.println("Спасибо, что воспользовались нашим телефонным справочником." +
                            "\nДо свидания");
                    isContinue = true;
                } else if (input.equalsIgnoreCase("list")) {
                    printAll();
                } else if (input.matches(CORRECT_NUMBER)) {
                    numberCheck(input);
                } else if (input.matches(CORRECT_NAME)) {
                    addName(input);
                } else
                    wrongInput();
            }

            public static void add(String name, String number) {
                phoneBook.put(name, number);
                System.out.println("Имя " + name + " внесено в справочник на номер " + number);
            }

            public static void addName(String inputName) {
                String name = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
                if (phoneBook.containsKey(name)) {
                    System.out.println(name + " - " + phoneBook.get(name));
                } else {
                    System.out.println("Имени " + "\"" + name + "\"" + " нет в справочнике. Введите номер для этого имени:");
                    while (true) {
                        String number = new Scanner(System.in).nextLine().trim();
                        if (number.equalsIgnoreCase("back")) {
                            return;
                        }
                        number = number.replaceAll("\\D+", "");
                        if (number.length() == 11 && (number.charAt(0) == '7' || number.charAt(0) == '8')) {
                            number = number.replaceAll("(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})", "+7($2)$3-$4-$5");
                            System.out.println(number);
                            if (phoneBook.containsValue(number)) {
                                System.out.println("Такой номер уже есть в справочнике" +
                                        "\nВведите другой номер или команду \"back\", чтобы вернуться в начало");
                            } else {
                                add(name, number);
                                return;
                            }
                        } else {
                            wrongInput();
                            System.out.println("Введите номер еще раз или команду \"back\", чтобы вернуться в начало\"");
                        }
                    }
                }
            }

            public static void addNumber(String number) {
                if (phoneBook.containsValue(number)) {
                    String name = "";
                    for (String name2 : phoneBook.keySet()) {
                        if (phoneBook.get(name2).equalsIgnoreCase(number)) {
                            name = name2;
                        }
                    }
                    System.out.println(name + " - " + number);

                } else {
                    System.out.println("Номера " + number + " нет в справочнике. Введите имя для этого номера:");
                    while (true) {
                        String name = new Scanner(System.in).nextLine().trim();
                        if (name.matches(CORRECT_NAME)) {
                            name = name.substring(0, 1).toUpperCase() + name.substring(1);
                            add(name, number);
                            //System.out.println("Номер " + number + " внесен в базу на имя " + name1);
                            return;
                        } else
                            System.out.print("Введите имя русскими буквами: ");
                    }
                }
            }

            public static String numberNameCheck(String number) {
                number = number.replaceAll("\\D+", "");
                if (number.length() == 11 && (number.charAt(0) == '7' || number.charAt(0) == '8')) {
                    number = number.replaceAll("(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})", "+7($2)$3-$4-$5");
                    System.out.println(number);
                    return number;
                } else
                    wrongInput();
                return number;
            }

            public static void numberCheck(String number) {
                number = number.replaceAll("\\D+", "");
                if (number.length() == 11 && (number.charAt(0) == '7' || number.charAt(0) == '8')) {
                    number = number.replaceAll("(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})", "+7($2)$3-$4-$5");
                    System.out.println(number);
                    addNumber(number);
                } else
                    wrongInput();
            }

            public static void printAll() {
                if (phoneBook.isEmpty()) {
                    System.out.println("Список пуст!");
                }
                for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                }
            }


            public static void wrongInput() {
                System.out.println("Неверный ввод!");
            }
        }
package sakenov.cryptoanalyzer;

import sakenov.cryptoanalyzer.entity.Result;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ConsoleRunner {
    private static final String HELLO = ("Привет! Это шифр Цезаря.\n" + "Чтобы остановить выполнение программы введите «exit».");

    private static final String SELECT_COMMAND = ("Выберите действие:\n" +
              "1. Шифрование с помощью ключа\n" +
              "2. Расшифровать с помощью ключа\n" +
              "3. Расшифровать используя brute force\n" +
              "Или нажмите Enter, чтобы выбрать значение по умолчанию — Шифрование.");

    private static final String SRC_ENCODE = "Пожалуйста, введите исходный файл \n" + "или нажмите Enter, если вы хотите выбрать файл по умолчанию (texts\\text.txt)";
    private static final String SRC_DECODE = "Пожалуйста, введите исходный файл \n" + "или нажмите Enter, если вы хотите выбрать файл по умолчанию (texts\\encoded.txt)";
    private static final String SRC_BRUTE_FORCE = "Пожалуйста, введите исходный файл \n" + "или нажмите Enter, если вы хотите выбрать файл по умолчанию (texts\\encoded.txt)";
    private static final String DEST_ENCRYPT = "Пожалуйста, введите файл назначения \nили нажмите Enter, если вы хотите выбрать файл по умолчанию (texts\\encoded.txt)";
    private static final String DEST_DECRYPT = "Пожалуйста, введите файл назначения \nили нажмите Enter, если вы хотите выбрать файл по умолчанию (texts\\decoded.txt)";
    private static final String DEST_BRUTE_FORCE = "Пожалуйста, введите файл назначения \nили нажмите Enter, если вы хотите выбрать файл по умолчанию (texts\\BruteForceResult.txt)";
    private static final String KEY_ENCRYPT = "Пожалуйста, введите ключ для шифрования или нажмите Enter, если вы хотите выбрать ключ по умолчанию (2)";
    private static final String KEY_DECRYPT = "Пожалуйста, введите ключ для расшифрования или нажмите Enter, если вы хотите выбрать ключ по умолчанию (2)";

    private static Scanner console;
    private static String[] parameters;
    private static String param0;
    private static String param1;
    private static String param2;
    private static String param3;

    public static void main(String[] args) {
        Application application = new Application();
        console = new Scanner(System.in);
        System.out.println(HELLO);
        do {
            selectParam0(console);
            selectParam1(console); // исходный файл
            selectParam2(console); // конечный файл
            selectParam3(console); // ключ
            parametersBuilder();
            Result result = application.run(parameters);
            System.out.println(result);
        } while (true);
    }

    public static void selectParam0(Scanner console) {
        System.out.println(SELECT_COMMAND);
        String selection = console.nextLine();
        try {
            if (selection.isEmpty()) {
                param0 = "encrypt";
            } else if (selection.equalsIgnoreCase("exit")) {
                console.close();
                System.exit(0);
            } else {
                int i = Integer.parseInt(selection);
                if (i == 1) {
                    param0 = "encrypt";
                } else if (i == 2) {
                    param0 = "decrypt";
                } else if (i == 3) {
                    param0 = "bruteforce";
                } else {
                    System.out.println("такое действие отсутствует.");
                    selectParam0(console);
                }
            }
        } catch (Exception e) {
            System.out.println("Вы ввели неверное значение");
            selectParam0(console);
        }
    }

    public static void selectParam1(Scanner console) {
        if (param0.equalsIgnoreCase("encrypt")) {
            System.out.println(SRC_ENCODE);
        } else if (param0.equalsIgnoreCase("decrypt")) {
            System.out.println(SRC_DECODE);
        } else if (param0.equalsIgnoreCase("bruteforce")) {
            System.out.println(SRC_BRUTE_FORCE);
        }
        String selection = console.nextLine();
        try {
            if (selection.isEmpty() && param0.equalsIgnoreCase("encrypt")) {
                param1 = "texts\\text.txt";
            } else if (selection.isEmpty() && param0.equalsIgnoreCase("decrypt")) {
                param1 = "texts\\encoded.txt";
            } else if (selection.equalsIgnoreCase("bruteforce")) {
                param1 = "texts\\encoded.txt";
            } else if (selection.equalsIgnoreCase("exit")) {
                console.close();
                System.exit(0);
            } else {
                Path path = Path.of(selection);
                if (Files.isRegularFile(path)) {
                    param1 = selection;
                } else {
                    if (param0.equalsIgnoreCase("encrypt")) {
                        System.out.println("Выбрано значение по умолчанию: texts\\text.txt");
                        param1 = "texts\\text.txt";
                    } else if (selection.isEmpty() && param0.equalsIgnoreCase("decrypt")) {
                        System.out.println("Выбрано значение по умолчанию: texts\\encoded.txt");
                        param1 = "texts\\encoded.txt";
                    } else if (selection.isEmpty() && param0.equalsIgnoreCase("bruteforce")) {
                        param1 = "texts\\encoded.txt";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Вы ввели неверный путь");
            selectParam1(console);
        }
    }

    public static void selectParam2(Scanner console) {
        if (param0.equalsIgnoreCase("encrypt")) {
            System.out.println(DEST_ENCRYPT);
        } else if (param0.equalsIgnoreCase("decrypt")) {
            System.out.println(DEST_DECRYPT);
        } else if (param0.equalsIgnoreCase("bruteforce")) {
            System.out.println(DEST_BRUTE_FORCE);
        }
        String selection = console.nextLine();
        try {
            if (selection.isEmpty() && param0.equalsIgnoreCase("encrypt")) {
                param2 = "texts\\encoded.txt";
            } else if (selection.isEmpty() && param0.equalsIgnoreCase("decrypt")) {
                param2 = "texts\\decoded.txt";
            } else if (selection.isEmpty() && param0.equalsIgnoreCase("bruteforce")) {
                param2 = "texts\\BruteForceResult.txt";
            } else if (selection.equalsIgnoreCase("exit")) {
                console.close();
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Вы ввели неверный путь");
            selectParam2(console);
        }
    }

    public static void selectParam3(Scanner console) {
        if (param0.equalsIgnoreCase("encrypt")) {
            System.out.println(KEY_ENCRYPT);
        } else if (param0.equalsIgnoreCase("decrypt")) {
            System.out.println(KEY_DECRYPT);
        } else if (param0.equalsIgnoreCase("bruteforce")) {
            param3 = null;
            System.out.println("нажмите Enter");
        }
        String selection = console.nextLine();
        try {
            if (selection.isEmpty() && param0.equalsIgnoreCase("encrypt")) {
                param3 = "2";
            } else if (selection.isEmpty() && param0.equalsIgnoreCase("decrypt")) {
                param3 = "2";
            } else if (selection.isEmpty() && param0.equalsIgnoreCase("bruteforce")) {
                param3 = null;
            } else if (selection.equalsIgnoreCase("exit")) {
                console.close();
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Вы ввели неверный путь");
            selectParam3(console);
        }
    }

    private static void parametersBuilder() {
        parameters = new String[]{param0, param1, param2, param3,};
    }
}
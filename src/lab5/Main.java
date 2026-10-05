package lab5;
import java.util.Map;
import java.util.Scanner;
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n            МЕНЮ ЛАБОРАТОРНОЇ №5 ");
            System.out.println("1. Завдання 1: Рядок з максимальною кількістю слів у файлі");
            System.out.println("2. Завдання 3: Шифрування та дешифрування файлу (FilterWriter/Reader)");
            System.out.println("3. Завдання 4: Підрахунок тегів за URL (Сортування)");
            System.out.println("4. Вимоги 2-3: Серіалізація та десеріалізація об'єкта TagCounter");
            System.out.println("0. Вихід");
            System.out.print("Виберіть опцію: ");
            try { String input = scanner.nextLine();
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> handleTask1();
                    case 2 -> handleTask3();
                    case 3 -> handleTask4();
                    case 4 -> handleSerialization();
                    case 0 -> {
                        System.out.println("Завершення роботи.");
                        return;
                    }
                    default -> System.out.println(" Некоректний вибір. Спробуйте ще раз.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Помилка введення: Введіть числове значення!");
            } catch (Exception e) {
                System.out.println(" Виникла помилка: " + e.getMessage());
            }
        }
    }
    private static void handleTask1() {
        try {
            System.out.print("Введіть шлях до файлу для аналізу: ");
            String filePath = scanner.nextLine();
            String resultLine = FileService.findLineWithMaxWords(filePath);
            System.out.println(" Рядок з найбільшою кількістю слів:");
            System.out.println("\"" + resultLine + "\"");
        } catch (Exception e) {
            System.out.println(" Помилка під час обробки файлу: " + e.getMessage()); }
    }
    private static void handleTask3() {
        try {
            System.out.print("Введіть шлях до початкового текстового файлу: ");
            String inputPath = scanner.nextLine();
            System.out.print("Введіть шлях для збереження зашифрованого файлу: ");
            String outputPath = scanner.nextLine();
            System.out.print("Введіть один ключовий символ для шифрування: ");
            String keyStr = scanner.nextLine();
            if (keyStr.isEmpty()) {
                System.out.println(" Ключовий символ не може бути порожнім!");
                return;}
            char key = keyStr.charAt(0);
            FileService.encryptFile(inputPath, outputPath, key);
            System.out.println(" Файл успішно зашифровано у: " + outputPath);
            System.out.println("\n--- Перевірка дешифрування ---");
            String decryptedContent = FileService.decryptFile(outputPath, key);
            System.out.println(" Розшифрований вміст файлу:");
            System.out.println(decryptedContent);
        } catch (Exception e) {
            System.out.println(" Помилка при шифруванні/дешифруванні: " + e.getMessage()); }
    }
    private static void handleTask4() {
        try {
            System.out.print("Введіть URL сторінки: ");
            String url = scanner.nextLine();
            Map<String, Integer> tagMap = TagParserService.parseAndCountTags(url);
            if (tagMap.isEmpty()) {
                System.out.println(" Тегів на сторінці не знайдено або сторінка порожня.");
                return; }
            TagParserService.printSortedByTagName(tagMap);
            TagParserService.printSortedByFrequency(tagMap);
        } catch (Exception e) {
            System.out.println(" Помилка при аналізі URL: " + e.getMessage());  }
    }
    private static void handleSerialization() {
        try {
            System.out.print("Введіть URL для аналізу та серіалізації: ");
            String url = scanner.nextLine();
            Map<String, Integer> tagMap = TagParserService.parseAndCountTags(url);
            TagCounter tagCounter = new TagCounter(url, tagMap);
            System.out.print("Введіть шлях та ім'я файлу для збереження (.ser): ");
            String filePath = scanner.nextLine();
            FileService.serializeObject(tagCounter, filePath);
            System.out.println("\n--- Читання даних з файлу ---");
            TagCounter deserialized = (TagCounter) FileService.deserializeObject(filePath);
            System.out.println("Успішно прочитано об'єкт: " + deserialized);
            TagParserService.printSortedByFrequency(deserialized.getTagFrequencies());
        } catch (Exception e) {
            System.out.println(" Помилка серіалізації/десеріалізації: " + e.getMessage()); }
    }
}
package lab5;
import java.io.*;
import java.nio.charset.StandardCharsets;
public class FileService {
    // 1. Серіалізація об'єкта (Вимога 2-3)
    public static void serializeObject(Object obj, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(obj);
            System.out.println(" Об'єкт успішно серіалізовано у файл: " + filePath);}
    }
    // Десеріалізація об'єкта
    public static Object deserializeObject(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();}
    }
    // 2. Завдання 1: Пошук рядка з максимальною кількістю слів
    public static String findLineWithMaxWords(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не знайдено: " + filePath); }

        String maxWordsLine = "";
        int maxWordCount = -1;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Видаляємо зайві пробіли та розбиваємо по пробіленим символам
                String trimmed = line.trim();
                String[] words = trimmed.isEmpty() ? new String[0] : trimmed.split("\\s+");
                int wordCount = words.length;
                if (wordCount > maxWordCount) {
                    maxWordCount = wordCount;
                    maxWordsLine = line;      }
            }
        }
        System.out.println("Максимальна кількість слів у знайденому рядку: " + (maxWordCount == -1 ? 0 : maxWordCount));
        return maxWordsLine;
    }
    // 3. Завдання 3: Запис encrypted файлу з FilterWriter
    public static void encryptFile(String inputPath, String outputPath, char key) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputPath), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new EncryptFilterWriter(
                             new OutputStreamWriter(new FileOutputStream(outputPath), StandardCharsets.UTF_8), key))) {
            int c;
            while ((c = reader.read()) != -1) { writer.write(c);}
        }
    }
    // 4. Завдання 3: Читання decrypted файлу з FilterReader
    public static String decryptFile(String inputPath, char key) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new DecryptFilterReader(
                        new InputStreamReader(new FileInputStream(inputPath), StandardCharsets.UTF_8), key))) {
            int c;
            while ((c = reader.read()) != -1) {
                result.append((char) c);      }
        }
        return result.toString();
    }
}
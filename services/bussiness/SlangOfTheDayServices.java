package services.bussiness;

import models.SlangEntry;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class SlangOfTheDayServices {
    private static final String FILE_PATH = "data/slang_of_the_day.txt";

    public SlangEntry getSlangOfTheDay(SlangDictionary dictionary) {
        LocalDate today = LocalDate.now();

        // Đọc dữ liệu từ file
        String[] data = readDataFromFile();
        // data[0] = date, data[1] = slang

        // Kiểm tra ngày
        if (data != null) {
            LocalDate fileDate = null;
            try {
                fileDate = LocalDate.parse(data[0]);
            } catch (Exception e) {
                // File bị lỗi, coi như ngày cũ
            }

            // Nếu ngày trong file trùng với hôm nay
            if (fileDate != null && fileDate.equals(today)) {
                // Lấy slang từ từ điển và trả về
                return dictionary.findSlang(data[1]);
            }
        }

        // Nếu không có file, hoặc đã qua ngày mới -> Random slang mới
        SlangEntry newSlang = dictionary.getRandomSlangWord();
        if (newSlang == null) {
            return null; // Từ điển rỗng
        }

        // Lưu slang mới và ngày hôm nay vào file
        writeDataToFile(today.toString(), newSlang.getSlang());

        // Trả về slang mới
        return newSlang;
    }

    private String[] readDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String date = reader.readLine();
            String slang = reader.readLine();

            if (date != null && slang != null) {
                return new String[] { date, slang };
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file Slang of the day: " + e.getMessage());
        }
        return null;
    }

    private void writeDataToFile(String date, String slang) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(date);
            writer.newLine();
            writer.write(slang);
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file Slang of the day: " + e.getMessage());

        }
    }
}

package services.bussiness;

import models.SlangEntry;
import java.util.ArrayList;
import java.util.List;

public class SlangParser implements IParsable<SlangEntry> {

    // Đặt public static final để TextDao cũng có thể truy cập nếu cần
    public static final String SLANG_MEANING_DELIMITER = "`";
    private static final String MEANING_SEPARATOR = "\\|";

    @Override
    public SlangEntry parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        // Tách bằng dấu ` đầu tiên, giới hạn 2 phần tử
        String[] parts = line.split(SLANG_MEANING_DELIMITER, 2);

        // Phải có 2 phần
        if (parts.length < 2) {
            return null;
        }

        String slang = parts[0].trim();
        if (slang.isEmpty()) {
            return null;
        }

        String meaningsStr = parts[1].trim();
        List<String> meanings = new ArrayList<>();

        for (String meaning : meaningsStr.split(MEANING_SEPARATOR)) {
            String trimmedMeaning = meaning.trim();
            if (!trimmedMeaning.isEmpty()) {
                meanings.add(trimmedMeaning);
            }
        }

        if (meanings.isEmpty()) {
            return null;
        }

        return new SlangEntry(slang, meanings);
    }

}
package services.dataaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import models.SlangEntry;
import services.bussiness.IParsable;
import services.bussiness.SlangParser;

public class TextDao implements IDao<SlangEntry> {
    private final String sourceFilePath = "data/slang.txt";
    private final BinaryDao binaryDao = new BinaryDao();

    @Override
    public List<SlangEntry> getAll() {
        Map<String, SlangEntry> slangMap = new LinkedHashMap<>();
        IParsable<SlangEntry> slangParser = new SlangParser();
        String lastSlangKey = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();

                if (trimmedLine.isEmpty() || trimmedLine.startsWith("Slag`Meaning")) {
                    lastSlangKey = null;
                    continue;
                }

                if (trimmedLine.contains(SlangParser.SLANG_MEANING_DELIMITER)) {
                    SlangEntry newEntry = slangParser.parse(trimmedLine);

                    if (newEntry == null) {
                        lastSlangKey = null;
                        continue;
                    }

                    String slangKey = newEntry.getSlang();
                    if (slangMap.containsKey(slangKey)) {

                        // Lấy entry cũ
                        SlangEntry existingEntry = slangMap.get(slangKey);

                        // Tạo một List MỚI (mutable) từ list cũ (immutable)
                        List<String> combinedMeanings = new ArrayList<>(existingEntry.getMeanings());

                        // Thêm các nghĩa mới vào list MỚI
                        for (String meaning : newEntry.getMeanings()) {
                            if (!combinedMeanings.contains(meaning)) {
                                combinedMeanings.add(meaning);
                            }
                        }

                        // Tạo một SlangEntry MỚI với danh sách nghĩa đã gộp
                        SlangEntry updatedEntry = new SlangEntry(slangKey, combinedMeanings);

                        // Thay thế entry cũ trong Map
                        slangMap.put(slangKey, updatedEntry);

                    } else {
                        slangMap.put(slangKey, newEntry);
                    }
                    lastSlangKey = slangKey;

                } else if (lastSlangKey != null) {
                    SlangEntry existingEntry = slangMap.get(lastSlangKey);
                    if (existingEntry != null) {

                        String newMeaning = cleanOrphanLine(trimmedLine);

                        if (!newMeaning.isEmpty() && !existingEntry.getMeanings().contains(newMeaning)) {
                            // Tạo List MỚI (mutable) từ list cũ (immutable)
                            List<String> combinedMeanings = new ArrayList<>(existingEntry.getMeanings());

                            // Thêm nghĩa mới vào
                            combinedMeanings.add(newMeaning);

                            // Tạo SlangEntry MỚI
                            SlangEntry updatedEntry = new SlangEntry(existingEntry.getSlang(), combinedMeanings);

                            // Thay thế trong Map
                            slangMap.put(lastSlangKey, updatedEntry);
                        }
                    }
                } else {
                    lastSlangKey = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SlangEntry> entries = new ArrayList<>(slangMap.values());
        if (!entries.isEmpty()) {
            binaryDao.saveAll(entries);
        }
        return entries;
    }

    private String cleanOrphanLine(String line) {
        if (line.startsWith("[")) {
            int closingBracketIndex = line.indexOf("]");
            if (closingBracketIndex != -1) {
                return line.substring(closingBracketIndex + 1).trim();
            }
        }
        return line;
    }

    @Override
    public void save(SlangEntry entity) {
        throw new UnsupportedOperationException("Use BinaryDao for save operations");
    }

    @Override
    public void saveAll(List<SlangEntry> entities) {
        throw new UnsupportedOperationException("Use BinaryDao for saveAll operations");
    }

    @Override
    public List<SlangEntry> resetData() {
        return getAll();
    }

    @Override
    public void delete(SlangEntry entity) {
        throw new UnsupportedOperationException("Use BinaryDao for delete operations");
    }
}
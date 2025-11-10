package services.dataaccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import models.SlangEntry; // Đảm bảo SlangEntry có constructor (String, List<String>)
import services.bussiness.IParsable;
import services.bussiness.SlangParser;

public class TextDao implements IDao<SlangEntry> {
    private final String sourceFilePath = "data/slang.txt";
    private final String dataFilePath = "data/slang.dat";

    @Override
    public List<SlangEntry> getAll() {
        File dataFile = new File(dataFilePath);
        if (dataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                @SuppressWarnings("unchecked")
                List<SlangEntry> entries = (List<SlangEntry>) ois.readObject();
                return entries;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, SlangEntry> slangMap = new LinkedHashMap<>();
        IParsable<SlangEntry> slangParser = new SlangParser();
        String lastSlangKey = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = cleanOrphanLine(line);

                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#") || trimmedLine.startsWith("Slang`Meaning") || trimmedLine.startsWith("Slag`Meaning")) {
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
                        // === THAY ĐỔI LỚN 1 (Gộp key trùng) ===
                        
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
                        
                        // === THAY ĐỔI LỚN 2 (Gộp dòng mồ côi) ===
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
            saveAll(entries);
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
        List<SlangEntry> entries = getAll();
        
        boolean updated = false;
        for (int i = 0; i < entries.size(); i++) {
            // Giả sử SlangEntry có getSlang()
            if (entries.get(i).getSlang().equals(entity.getSlang())) { 
                entries.set(i, entity);
                updated = true;
                break;
            }
        }
        if (!updated) {
            entries.add(entity);
        }
        
        saveAll(entries);
    }

    @Override
    public void saveAll(List<SlangEntry> entities) {
        File dataFile = new File(dataFilePath);
        File parentDir = dataFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFilePath))) {
            oos.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
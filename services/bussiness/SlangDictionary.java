package services.bussiness;

import models.SlangEntry;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Random;
import services.dataaccess.TextDao;

public class SlangDictionary {
    private Map<String, SlangEntry> slangMap;
    private Map<String, List<String>> meaningsMap;
    private List<String> history;

    public SlangDictionary() {
        slangMap = new HashMap<>();
        meaningsMap = new HashMap<>();
        history = new ArrayList<>();
    }

    public SlangDictionary(List<SlangEntry> entries) {
        this(); // Gọi constructor mặc định để khởi tạo các map và list
        for (SlangEntry entry : entries) {
            slangMap.put(entry.getSlang(), entry);
            for (String meaning : entry.getMeanings()) {
                meaningsMap.computeIfAbsent(meaning, k -> new ArrayList<>()).add(entry.getSlang());
            }
        }
    }

    public SlangEntry findSlang(String slang) {
        SlangEntry entry = slangMap.get(slang);
        if (entry != null) {
            history.add(slang);
        }
        return entry;
    }

    public List<String> findMeaning(String meaning) {
        List<String> slangs = meaningsMap.getOrDefault(meaning, new ArrayList<>());
        if (!slangs.isEmpty()) {
            return slangs;
        }
        return new ArrayList<>();
    }

    public List<String> getHistory() {
        return history;
    }

    public boolean containsSlang(String slang) {
        return slangMap.containsKey(slang);
    }

    public boolean addNewSlangWord(String slang, List<String> meanings) {
        SlangEntry entry = new SlangEntry(slang, meanings);
        slangMap.put(slang, entry);

        for (String meaning : meanings) {
            // Thêm slang vào danh sách các slang tương ứng với nghĩa này trong meaningsMap
            meaningsMap.computeIfAbsent(meaning, k -> new ArrayList<>()).add(slang);
        }
        TextDao dao = new TextDao();
        dao.save(entry);
        return true;
    }

    public boolean overwriteSlangWord(String slang, List<String> newMeanings) {
        // Xóa entry cũ
        SlangEntry oldEntry = slangMap.get(slang);
        if (oldEntry == null)
            return false; // Không có gì để ghi đè

        List<String> oldMeanings = oldEntry.getMeanings();
        for (String oldMeaning : oldMeanings) {
            List<String> slangsForMeaning = meaningsMap.get(oldMeaning);
            if (slangsForMeaning != null) {
                slangsForMeaning.remove(slang);
                if (slangsForMeaning.isEmpty()) {
                    meaningsMap.remove(oldMeaning);
                }
            }
        }

        // Thêm entry mới
        SlangEntry newEntry = new SlangEntry(slang, newMeanings);
        slangMap.put(slang, newEntry); // Ghi đè trong slangMap

        // Cập nhật meaningsMap
        for (String newMeaning : newMeanings) {
            meaningsMap.computeIfAbsent(newMeaning, k -> new ArrayList<>()).add(slang);
        }
        TextDao dao = new TextDao();
        dao.save(newEntry);
        return true;
    }

    public boolean appendDuplicateMeanings(String slang, List<String> newMeanings) {
        SlangEntry entry = slangMap.get(slang);
        if (entry == null)
            return false; // Không có slang để thêm

        List<String> existingMeanings = entry.getMeanings();
        Set<String> meaningsToAdd = new HashSet<>(newMeanings);
        meaningsToAdd.removeAll(existingMeanings); // Lọc ra ý nghĩa mới thực sự

        if (meaningsToAdd.isEmpty()) {
            return false; // Không có gì mới để thêm
        }

        for (String meaningToAdd : meaningsToAdd) {
            existingMeanings.add(meaningToAdd);
            meaningsMap.computeIfAbsent(meaningToAdd, k -> new ArrayList<>()).add(slang);
        }
        TextDao dao = new TextDao();
        dao.save(entry);
        return true;
    }

    public boolean editSlangWord(String oldSlang, String newSlang, List<String> newMeanings) {
        if (!slangMap.containsKey(oldSlang)) {
            return false;
        }

        if (!oldSlang.equals(newSlang) && slangMap.containsKey(newSlang)) {
            return false;
        }

        SlangEntry oldEntry = slangMap.remove(oldSlang);

        List<String> oldMeanings = oldEntry.getMeanings();
        for (String oldMeaning : oldMeanings) {
            List<String> slangsForMeaning = meaningsMap.get(oldMeaning);
            if (slangsForMeaning != null) {
                slangsForMeaning.remove(oldSlang);
                if (slangsForMeaning.isEmpty()) {
                    meaningsMap.remove(oldMeaning);
                }
            }
        }

        // Thêm slang mới (Logic của bạn đã đúng)
        SlangEntry newEntry = new SlangEntry(newSlang, newMeanings);
        slangMap.put(newSlang, newEntry);
        for (String newMeaning : newMeanings) {
            meaningsMap.computeIfAbsent(newMeaning, k -> new ArrayList<>()).add(newSlang);
        }
        TextDao dao = new TextDao();
        dao.save(newEntry);
        return true;
    }

    public boolean deleteSlangWord(String slang) {
        SlangEntry entry = slangMap.remove(slang);
        if (entry == null) {
            return false; // Không tìm thấy slang để xóa
        }

        List<String> meanings = entry.getMeanings();
        for (String meaning : meanings) {
            List<String> slangsForMeaning = meaningsMap.get(meaning);
            if (slangsForMeaning != null) {
                slangsForMeaning.remove(slang);
                if (slangsForMeaning.isEmpty()) {
                    meaningsMap.remove(meaning);
                }
            }
        }
        TextDao dao = new TextDao();
        dao.delete(entry);
        return true;
    }

    public SlangEntry getRandomSlangWord() {
        if (slangMap.isEmpty()) {
            return null; // Từ điển rỗng
        }
        List<String> keys = new ArrayList<>(slangMap.keySet());
        Random rand = new Random();
        String randomKey = keys.get(rand.nextInt(keys.size()));
        return slangMap.get(randomKey);
    }
}

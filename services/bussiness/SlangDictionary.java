package services.bussiness;
import models.SlangEntry;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class SlangDictionary {
    private Map<String, SlangEntry> slangMap;
    private Map<String, List<String>> meaningsMap;
    private List<String> history;

    public SlangDictionary() {
        slangMap = new HashMap<>();
        meaningsMap = new HashMap<>();
        history = new ArrayList<>();
    }

    void findSlangWord(String slang) {
        // Implementation goes here
        SlangEntry entry = slangMap.get(slang);
        if (entry != null) {
            history.add(slang);
            System.out.println("Slang: " + slang + ", Meanings: " + entry.getMeanings());
        } else {
            System.out.println("Slang word not found.");
        }
    }

    void findMeaning(String meaning) {
        // Implementation goes here
        List<String> slangs = meaningsMap.get(meaning);
        if (slangs != null) {
            for (String slang : slangs) {
                history.add(slang);
            }
            System.out.println("Meanings: " + meaning + ", Slangs: " + slangs);
        } else {
            System.out.println("Meaning not found.");
        }
    }

    List<String> getHistory() {
        return history;
    }
    

    void addSlangWord(String slang, List<String> meanings) {
        // Implementation goes here
        SlangEntry entry = new SlangEntry(slang, meanings);
        slangMap.put(slang, entry);
        for (String meaning : meanings) {
            meaningsMap.computeIfAbsent(meaning, k -> new ArrayList<>()).add(slang);
        }

    }
    
}

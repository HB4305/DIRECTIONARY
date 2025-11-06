package services.bussiness;
import models.SlangEntry;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class SlangDictionary {
    private Map<String, SlangEntry> slangMap;
    private Map<String, List<String>> meaningsMap;

    public SlangDictionary() {
        slangMap = new HashMap<>();
        meaningsMap = new HashMap<>();
    }

    void addSlangEntry(SlangEntry entry) {
        slangMap.put(entry.getSlang(), entry);
        meaningsMap.put(entry.getSlang(), entry.getMeanings());
    }

    
}

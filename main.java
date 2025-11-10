import services.dataaccess.TextDao;
import models.SlangEntry;
import services.bussiness.SlangDictionary;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        TextDao dao = new TextDao();
        List<SlangEntry> allSlangs = dao.getAll();
        
        SlangDictionary dictionary = new SlangDictionary(allSlangs);
        SlangEntry entry = dictionary.findSlang("4TW");
        if (entry != null) {
            System.out.println("Slang: " + entry.getSlang());
            System.out.println("Meanings: " + entry.getMeanings());
        } else {
            System.out.println("Slang not found.");
        }
    
    }
}

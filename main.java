import services.dataaccess.TextDao;
import models.SlangEntry;
import java.util.List;

public class main {
    public static void main(String[] args) {
        TextDao dao = new TextDao();
        List<SlangEntry> allSlangs = dao.getAll();
        
        System.out.println("First 20 slang words:\n");
        System.out.println("==============================================================");
        
        int count = 0;
        for (SlangEntry entry : allSlangs) {
            if (count >= 20) break;
            
            System.out.println((count + 1) + ". " + entry.getSlang());
            System.out.println("   Meanings: " + String.join(", ", entry.getMeanings()));
            System.out.println();
            
            count++;
        }
        
        System.out.println("==============================================================");
        System.out.println("Total slang words in dictionary: " + allSlangs.size());
    }
}

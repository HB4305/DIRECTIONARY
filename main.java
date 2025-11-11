import services.dataaccess.TextDao;
import models.SlangEntry;
import services.bussiness.SlangDictionary;
import java.util.List;
import services.bussiness.Quiz;

public class Main {
    public static void main(String[] args) {
        TextDao dao = new TextDao();
        List<SlangEntry> allSlangs = dao.getAll();
        System.out.println("Total slang entries loaded: " + allSlangs.size());
        // Quiz quiz = new Quiz(allSlangs);
        // Quiz.QuizQuestion question = quiz.generateMeaningToSlangQuiz();
        // System.out.println(question.getQuestion());
        // List<String> options = question.getOptions();
        // for (String option : options) {
        // System.out.println(" - " + option);
        // }
        SlangDictionary dictionary = new SlangDictionary(allSlangs);
        String searchSlang = "#1";
        SlangEntry entry = dictionary.findSlang(searchSlang);
        if (entry != null) {
            System.out.println("Meanings for slang '" + searchSlang + "': " + entry.getMeanings());
        } else {
            System.out.println("Slang '" + searchSlang + "' not found.");
        }

    }
}

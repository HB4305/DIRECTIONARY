import services.dataaccess.TextDao;
import models.SlangEntry;
import services.bussiness.SlangDictionary;
import java.util.List;
import services.bussiness.Quiz;


public class Main {
    public static void main(String[] args) {
        TextDao dao = new TextDao();
        List<SlangEntry> allSlangs = dao.getAll();
        Quiz quiz = new Quiz(allSlangs);
        Quiz.QuizQuestion question = quiz.generateMeaningToSlangQuiz();
        System.out.println(question.getQuestion());
        List<String> options = question.getOptions();
        for (String option : options) {
            System.out.println(" - " + option);
        }
    }
}

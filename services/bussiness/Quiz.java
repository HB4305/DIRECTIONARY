package services.bussiness;

import models.SlangEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class Quiz {
    private final List<SlangEntry> slangDictionary;
    private final Random random;
    private static final int NUMBER_OF_OPTIONS = 4;

    public Quiz(List<SlangEntry> slangDictionary) {
        if (slangDictionary == null || slangDictionary.size() < NUMBER_OF_OPTIONS) {
            throw new IllegalArgumentException("Dictionary must contain at least " + NUMBER_OF_OPTIONS + " entries.");
        }
        this.slangDictionary = slangDictionary;
        this.random = new Random();
    }

    public QuizQuestion generateSlangToMeaningQuiz() {
        // Chọn một SlangEntry ngẫu nhiên làm câu hỏi
        SlangEntry correctEntry = getRandomEntry();
        String questionText = "What is the meaning of the slang word: **" + correctEntry.getSlang() + "**?";

        // Chọn đáp án đúng (một nghĩa ngẫu nhiên từ entry đúng)
        String correctAnswer = correctEntry.getMeanings().get(random.nextInt(correctEntry.getMeanings().size()));

        // Chọn các đáp án sai duy nhất (distractors)
        Set<String> options = new HashSet<>();
        options.add(correctAnswer);

        // Lấy nghĩa từ các từ lóng khác, khác với từ đúng
        while (options.size() < NUMBER_OF_OPTIONS) {
            SlangEntry wrongEntry = getRandomEntry();
            // Đảm bảo nghĩa sai không phải từ đúng
            if (!wrongEntry.getSlang().equals(correctEntry.getSlang()) && !wrongEntry.getMeanings().isEmpty()) {
                // Chọn một nghĩa ngẫu nhiên từ entry sai
                String wrongMeaning = wrongEntry.getMeanings().get(random.nextInt(wrongEntry.getMeanings().size()));
                options.add(wrongMeaning);
            }
        }

        List<String> shuffledOptions = new ArrayList<>(options);
        Collections.shuffle(shuffledOptions);

        return new QuizQuestion(questionText, shuffledOptions, correctAnswer);
    }

    public QuizQuestion generateMeaningToSlangQuiz() {
        // Chọn một SlangEntry ngẫu nhiên làm câu hỏi
        SlangEntry correctEntry = getRandomEntry();

        // Chọn đáp án đúng (từ lóng)
        String correctAnswer = correctEntry.getSlang();

        // Chọn câu hỏi (một nghĩa ngẫu nhiên từ entry đúng)
        String meaningQuestion = correctEntry.getMeanings().get(random.nextInt(correctEntry.getMeanings().size()));
        String questionText = "Which slang word has the meaning: **" + meaningQuestion + "**?";

        // Chọn các đáp án sai duy nhất (distractors)
        Set<String> options = new HashSet<>();
        options.add(correctAnswer);

        // Lấy từ lóng từ các entry khác, khác với từ đúng
        while (options.size() < NUMBER_OF_OPTIONS) {
            SlangEntry wrongEntry = getRandomEntry();
            // Đảm bảo từ lóng sai không phải từ đúng
            if (!wrongEntry.getSlang().equals(correctEntry.getSlang())) {
                options.add(wrongEntry.getSlang());
            }
        }

        List<String> shuffledOptions = new ArrayList<>(options);
        Collections.shuffle(shuffledOptions);

        return new QuizQuestion(questionText, shuffledOptions, correctAnswer);
    }

    // Lấy một SlangEntry ngẫu nhiên từ từ điển
    private SlangEntry getRandomEntry() {
        return slangDictionary.get(random.nextInt(slangDictionary.size()));
    }

    // --- Inner Class để đại diện cho câu hỏi quiz ---

    public static class QuizQuestion {
        private final String question;
        private final List<String> options;
        private final String correctAnswer;

        public QuizQuestion(String question, List<String> options, String correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public String getQuestionText() {
            return question;
        }

        public List<String> getOptions() {
            return Collections.unmodifiableList(options);
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }

}
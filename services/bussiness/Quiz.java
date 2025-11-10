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

    /**
     * Initializes the Quiz with a list of all slang entries.
     * @param slangDictionary The complete list of SlangEntry objects.
     */
    public Quiz(List<SlangEntry> slangDictionary) {
        if (slangDictionary == null || slangDictionary.size() < NUMBER_OF_OPTIONS) {
            throw new IllegalArgumentException("Dictionary must contain at least " + NUMBER_OF_OPTIONS + " entries.");
        }
        this.slangDictionary = slangDictionary;
        this.random = new Random();
    }

    // --- Quiz Type 1: Slang -> Meaning (Question 10) ---

    /**
     * Generates a quiz question: Slang word, choose the correct definition.
     * @return A QuizQuestion object containing the question and options.
     */
    public QuizQuestion generateSlangToMeaningQuiz() {
        // 1. Select the correct SlangEntry
        SlangEntry correctEntry = getRandomEntry();
        String questionText = "What is the meaning of the slang word: **" + correctEntry.getSlang() + "**?";

        // 2. Select the correct answer (a random meaning from the correct entry)
        String correctAnswer = correctEntry.getMeanings().get(random.nextInt(correctEntry.getMeanings().size()));

        // 3. Select unique wrong answers (distractors)
        Set<String> options = new HashSet<>();
        options.add(correctAnswer);

        // Get meanings from other, distinct slang words
        while (options.size() < NUMBER_OF_OPTIONS) {
            SlangEntry wrongEntry = getRandomEntry();
            // Ensure the wrong entry is not the correct one and has meanings
            if (!wrongEntry.getSlang().equals(correctEntry.getSlang()) && !wrongEntry.getMeanings().isEmpty()) {
                // Pick a random meaning from the wrong entry
                String wrongMeaning = wrongEntry.getMeanings().get(random.nextInt(wrongEntry.getMeanings().size()));
                options.add(wrongMeaning);
            }
        }

        List<String> shuffledOptions = new ArrayList<>(options);
        Collections.shuffle(shuffledOptions);

        return new QuizQuestion(questionText, shuffledOptions, correctAnswer);
    }

    // --- Quiz Type 2: Meaning -> Slang (Question 11) ---

    /**
     * Generates a quiz question: Definition, choose the correct slang word.
     * @return A QuizQuestion object containing the question and options.
     */
    public QuizQuestion generateMeaningToSlangQuiz() {
        // 1. Select the correct SlangEntry
        SlangEntry correctEntry = getRandomEntry();

        // 2. Select the correct answer (the slang word)
        String correctAnswer = correctEntry.getSlang();

        // 3. Select the question (a random meaning from the correct entry)
        String meaningQuestion = correctEntry.getMeanings().get(random.nextInt(correctEntry.getMeanings().size()));
        String questionText = "Which slang word has the meaning: **" + meaningQuestion + "**?";

        // 4. Select unique wrong answers (distractors)
        Set<String> options = new HashSet<>();
        options.add(correctAnswer);

        // Get slangs from other, distinct entries
        while (options.size() < NUMBER_OF_OPTIONS) {
            SlangEntry wrongEntry = getRandomEntry();
            // Ensure the wrong slang is not the correct one
            if (!wrongEntry.getSlang().equals(correctEntry.getSlang())) {
                options.add(wrongEntry.getSlang());
            }
        }

        List<String> shuffledOptions = new ArrayList<>(options);
        Collections.shuffle(shuffledOptions);

        return new QuizQuestion(questionText, shuffledOptions, correctAnswer);
    }


    /**
     * Helper method to get a random SlangEntry from the dictionary.
     */
    private SlangEntry getRandomEntry() {
        return slangDictionary.get(random.nextInt(slangDictionary.size()));
    }

    // --- Inner Class to hold a complete question ---

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
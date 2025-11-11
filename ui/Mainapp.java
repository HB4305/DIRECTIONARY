package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.SlangEntry;
import services.bussiness.Quiz;
import services.bussiness.SlangDictionary;
import services.dataaccess.TextDao;

import java.util.*;

public class Mainapp extends Application {
    private SlangDictionary dictionary;
    private List<SlangEntry> allSlangs;
    private TextDao dao;
    private Quiz quiz;
    private StackPane rootPane;
    private VBox mainContent;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize data
        dao = new TextDao();
        allSlangs = dao.getAll();
        dictionary = new SlangDictionary(allSlangs);
        quiz = new Quiz(allSlangs);

        // Initialize UI
        primaryStage.setTitle("Slang Dictionary Application");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);

        rootPane = new StackPane();
        mainContent = createMainLayout();
        rootPane.getChildren().add(mainContent);

        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createMainLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1;");

        // Header
        HBox header = createHeader();
        layout.getChildren().add(header);

        // Separator
        Separator sep = new Separator();
        layout.getChildren().add(sep);

        // Content area (will be replaced based on menu selection)
        BorderPane contentArea = new BorderPane();
        contentArea.setCenter(createWelcomePanel());
        layout.getChildren().add(contentArea);

        return layout;
    }

    private HBox createHeader() {
        HBox header = new HBox(10);
        header.setStyle("-fx-background-color: #2c3e50; -fx-padding: 10;");
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("📚 SLANG DICTIONARY");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: white;");

        Label infoLabel = new Label("Total entries: " + allSlangs.size());
        infoLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #ecf0f1;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(title, spacer, infoLabel);
        return header;
    }

    private VBox createMainMenuPanel() {
        VBox menuPanel = new VBox(15);
        menuPanel.setPadding(new Insets(30));
        menuPanel.setAlignment(Pos.TOP_CENTER);
        menuPanel.setStyle("-fx-font-size: 14;");

        Label menuTitle = new Label("MAIN MENU");
        menuTitle.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        VBox buttonsBox = new VBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(20));

        // Create menu buttons with proper styling
        Button btn1 = createMenuButton("🔍 Search by Slang Word", () -> showSearchSlangPanel());
        Button btn2 = createMenuButton("🔎 Search by Definition", () -> showSearchDefinitionPanel());
        Button btn3 = createMenuButton("📋 View Search History", () -> showHistoryPanel());
        Button btn4 = createMenuButton("➕ Add New Slang Word", () -> showAddSlangPanel());
        Button btn5 = createMenuButton("✏️  Edit Slang Word", () -> showEditSlangPanel());
        Button btn6 = createMenuButton("🗑️  Delete Slang Word", () -> showDeleteSlangPanel());
        Button btn7 = createMenuButton("🔄 Reset Dictionary", () -> showResetPanel());
        Button btn8 = createMenuButton("⭐ Slang of the Day", () -> showSlangOfTheDayPanel());
        Button btn9 = createMenuButton("❓ Quiz: Guess Meaning", () -> showQuiz1Panel());
        Button btn10 = createMenuButton("❓ Quiz: Guess Slang", () -> showQuiz2Panel());

        buttonsBox.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10);

        ScrollPane scrollPane = new ScrollPane(buttonsBox);
        scrollPane.setFitToWidth(true);

        menuPanel.getChildren().addAll(menuTitle, scrollPane);
        return menuPanel;
    }

    private Button createMenuButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.setStyle("-fx-font-size: 14; -fx-padding: 12 20; -fx-background-color: #3498db; " +
                "-fx-text-fill: white; -fx-border-radius: 5; -fx-cursor: hand;");
        btn.setMinWidth(250);
        btn.setOnMouseEntered(
                e -> btn.setStyle("-fx-font-size: 14; -fx-padding: 12 20; -fx-background-color: #2980b9; " +
                        "-fx-text-fill: white; -fx-border-radius: 5; -fx-cursor: hand;"));
        btn.setOnMouseExited(
                e -> btn.setStyle("-fx-font-size: 14; -fx-padding: 12 20; -fx-background-color: #3498db; " +
                        "-fx-text-fill: white; -fx-border-radius: 5; -fx-cursor: hand;"));
        btn.setOnAction(e -> action.run());
        return btn;
    }

    private VBox createWelcomePanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #ecf0f1;");

        Label welcome = new Label("Welcome to Slang Dictionary");
        welcome.setStyle("-fx-font-size: 32; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label subtitle = new Label("Select an option from the menu below to get started");
        subtitle.setStyle("-fx-font-size: 16; -fx-text-fill: #7f8c8d;");

        VBox menuPanel = createMainMenuPanel();

        panel.getChildren().addAll(welcome, subtitle, menuPanel);
        return panel;
    }

    // ============ 1. Search by Slang Word ============
    private void showSearchSlangPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: white;");

        Label title = createTitle("🔍 Search by Slang Word");
        panel.getChildren().add(title);

        HBox inputBox = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Enter slang word...");
        searchField.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        searchField.setPrefWidth(300);

        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefHeight(300);

        searchBtn.setOnAction(e -> {
            String slang = searchField.getText().trim();
            if (slang.isEmpty()) {
                resultArea.setText("Please enter a slang word.");
                return;
            }

            SlangEntry entry = dictionary.findSlang(slang);
            if (entry != null) {
                resultArea.setText("Slang: " + entry.getSlang() + "\n\nMeanings:\n" +
                        String.join("\n", entry.getMeanings()));
            } else {
                resultArea.setText("Slang '" + slang + "' not found in dictionary.");
            }
        });

        inputBox.getChildren().addAll(searchField, searchBtn);
        panel.getChildren().addAll(inputBox, new Label("Results:"), resultArea);
        panel.getChildren().add(createBackButton());

        updateMainContent(panel);
    }

    // ============ 2. Search by Definition ============
    private void showSearchDefinitionPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: white;");

        Label title = createTitle("🔎 Search by Definition");
        panel.getChildren().add(title);

        HBox inputBox = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Enter keyword...");
        searchField.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        searchField.setPrefWidth(300);

        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefHeight(300);

        searchBtn.setOnAction(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                resultArea.setText("Please enter a keyword.");
                return;
            }

            List<String> results = new ArrayList<>();
            for (SlangEntry entry : allSlangs) {
                for (String meaning : entry.getMeanings()) {
                    if (meaning.toLowerCase().contains(keyword)) {
                        results.add("• " + entry.getSlang() + ": " + meaning);
                        break;
                    }
                }
            }

            if (results.isEmpty()) {
                resultArea.setText("No slang words found with definition containing '" + keyword + "'.");
            } else {
                resultArea.setText(String.join("\n", results));
            }
        });

        inputBox.getChildren().addAll(searchField, searchBtn);
        panel.getChildren().addAll(inputBox, new Label("Results:"), resultArea);
        panel.getChildren().add(createBackButton());

        updateMainContent(panel);
    }

    // ============ 3. View Search History ============
    private void showHistoryPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: white;");

        Label title = createTitle("📋 Search History");
        panel.getChildren().add(title);

        List<String> history = dictionary.getHistory();
        TextArea historyArea = new TextArea();
        historyArea.setEditable(false);
        historyArea.setWrapText(true);
        historyArea.setPrefHeight(350);

        if (history.isEmpty()) {
            historyArea.setText("No search history yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append((i + 1)).append(". ").append(history.get(i)).append("\n");
            }
            historyArea.setText(sb.toString());
        }

        Button clearBtn = new Button("Clear History");
        clearBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");
        clearBtn.setOnAction(e -> {
            history.clear();
            historyArea.setText("History cleared.");
        });

        panel.getChildren().addAll(historyArea, clearBtn);
        panel.getChildren().add(createBackButton());

        updateMainContent(panel);
    }

    // ============ 4. Add New Slang Word ============
    private void showAddSlangPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: white;");

        Label title = createTitle("➕ Add New Slang Word");
        panel.getChildren().add(title);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(15));

        Label slangLabel = new Label("Slang Word:");
        TextField slangField = new TextField();
        slangField.setStyle("-fx-padding: 8;");

        Label meaningLabel = new Label("Meanings (one per line):");
        TextArea meaningArea = new TextArea();
        meaningArea.setStyle("-fx-padding: 8;");
        meaningArea.setPrefHeight(150);
        meaningArea.setWrapText(true);

        grid.add(slangLabel, 0, 0);
        grid.add(slangField, 1, 0);
        grid.add(meaningLabel, 0, 1);
        grid.add(meaningArea, 1, 1);

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 12;");

        Button addBtn = new Button("Add Slang");
        addBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");

        addBtn.setOnAction(e -> {
            String slang = slangField.getText().trim();
            String meanings = meaningArea.getText().trim();

            if (slang.isEmpty() || meanings.isEmpty()) {
                showAlert("Error", "Please fill in both fields.", Alert.AlertType.ERROR);
                return;
            }

            List<String> meaningsList = Arrays.asList(meanings.split("\n"));
            meaningsList.replaceAll(String::trim);

            if (dictionary.containsSlang(slang)) {
                // Show dialog for duplicate handling
                showDuplicateDialog(slang, meaningsList, slangField, meaningArea, statusLabel);
            } else {
                dictionary.addNewSlangWord(slang, meaningsList);
                allSlangs.add(new SlangEntry(slang, meaningsList));
                statusLabel.setText("✓ Slang word '" + slang + "' added successfully!");
                slangField.clear();
                meaningArea.clear();
            }
        });

        panel.getChildren().addAll(grid, statusLabel, addBtn);
        panel.getChildren().add(createBackButton());

        updateMainContent(panel);
    }

    private void showDuplicateDialog(String slang, List<String> newMeanings,
            TextField slangField, TextArea meaningArea, Label statusLabel) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Duplicate Slang Word");
        dialog.setHeaderText("Slang word '" + slang + "' already exists!");
        dialog.setContentText("Choose an option:\n1. Overwrite existing meanings\n2. Append new meanings");

        ButtonType overwrite = new ButtonType("Overwrite");
        ButtonType append = new ButtonType("Append");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getButtonTypes().setAll(overwrite, append, cancel);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() == overwrite) {
                dictionary.overwriteSlangWord(slang, newMeanings);
                statusLabel.setText("✓ Slang word '" + slang + "' overwritten successfully!");
                slangField.clear();
                meaningArea.clear();
            } else if (result.get() == append) {
                if (dictionary.appendDuplicateMeanings(slang, newMeanings)) {
                    statusLabel.setText("✓ New meanings added to '" + slang + "'!");
                    slangField.clear();
                    meaningArea.clear();
                } else {
                    showAlert("Info", "No new meanings to append.", Alert.AlertType.INFORMATION);
                }
            }
        }
    }

    // ============ 5. Edit Slang Word ============
    private void showEditSlangPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: white;");

        Label title = createTitle("✏️  Edit Slang Word");
        panel.getChildren().add(title);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(15));

        Label oldSlangLabel = new Label("Current Slang Word:");
        TextField oldSlangField = new TextField();
        oldSlangField.setStyle("-fx-padding: 8;");

        Label newSlangLabel = new Label("New Slang Word:");
        TextField newSlangField = new TextField();
        newSlangField.setStyle("-fx-padding: 8;");

        Label meaningLabel = new Label("New Meanings (one per line):");
        TextArea meaningArea = new TextArea();
        meaningArea.setStyle("-fx-padding: 8;");
        meaningArea.setPrefHeight(150);
        meaningArea.setWrapText(true);

        Button loadBtn = new Button("Load Current");
        loadBtn.setStyle("-fx-font-size: 11; -fx-padding: 6 15;");

        loadBtn.setOnAction(e -> {
            String oldSlang = oldSlangField.getText().trim();
            if (oldSlang.isEmpty()) {
                showAlert("Error", "Please enter current slang word.", Alert.AlertType.ERROR);
                return;
            }

            SlangEntry entry = dictionary.findSlang(oldSlang);
            if (entry != null) {
                newSlangField.setText(oldSlang);
                meaningArea.setText(String.join("\n", entry.getMeanings()));
            } else {
                showAlert("Error", "Slang word not found.", Alert.AlertType.ERROR);
            }
        });

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 12;");

        Button editBtn = new Button("Update Slang");
        editBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");

        editBtn.setOnAction(e -> {
            String oldSlang = oldSlangField.getText().trim();
            String newSlang = newSlangField.getText().trim();
            String meanings = meaningArea.getText().trim();

            if (oldSlang.isEmpty() || newSlang.isEmpty() || meanings.isEmpty()) {
                showAlert("Error", "Please fill in all fields.", Alert.AlertType.ERROR);
                return;
            }

            List<String> meaningsList = Arrays.asList(meanings.split("\n"));
            meaningsList.replaceAll(String::trim);

            if (dictionary.editSlangWord(oldSlang, newSlang, meaningsList)) {
                statusLabel.setText("✓ Slang word updated successfully!");
                oldSlangField.clear();
                newSlangField.clear();
                meaningArea.clear();
            } else {
                showAlert("Error", "Failed to update slang word.", Alert.AlertType.ERROR);
            }
        });

        grid.add(oldSlangLabel, 0, 0);
        grid.add(oldSlangField, 1, 0);
        grid.add(loadBtn, 2, 0);
        grid.add(newSlangLabel, 0, 1);
        grid.add(newSlangField, 1, 1);
        grid.add(meaningLabel, 0, 2);
        grid.add(meaningArea, 1, 2);

        panel.getChildren().addAll(grid, statusLabel, editBtn);
        panel.getChildren().add(createBackButton());

        updateMainContent(panel);
    }

    // ============ 6. Delete Slang Word ============
    private void showDeleteSlangPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: white;");

        Label title = createTitle("🗑️  Delete Slang Word");
        panel.getChildren().add(title);

        HBox inputBox = new HBox(10);
        TextField slangField = new TextField();
        slangField.setPromptText("Enter slang word to delete...");
        slangField.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        slangField.setPrefWidth(300);

        TextArea displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setWrapText(true);
        displayArea.setPrefHeight(200);

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12;");

        Button loadBtn = new Button("Load");
        loadBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");

        loadBtn.setOnAction(e -> {
            String slang = slangField.getText().trim();
            if (slang.isEmpty()) {
                showAlert("Error", "Please enter a slang word.", Alert.AlertType.ERROR);
                return;
            }

            SlangEntry entry = dictionary.findSlang(slang);
            if (entry != null) {
                displayArea.setText("Slang: " + entry.getSlang() + "\n\nMeanings:\n" +
                        String.join("\n", entry.getMeanings()));
                statusLabel.setText("");
            } else {
                displayArea.setText("Slang '" + slang + "' not found.");
                statusLabel.setText("");
            }
        });

        Button deleteBtn = new Button("Delete This Slang");
        deleteBtn
                .setStyle("-fx-font-size: 12; -fx-padding: 8 25; -fx-background-color: #e74c3c; -fx-text-fill: white;");

        deleteBtn.setOnAction(e -> {
            String slang = slangField.getText().trim();
            if (slang.isEmpty()) {
                showAlert("Error", "Please enter a slang word.", Alert.AlertType.ERROR);
                return;
            }

            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirm Delete");
            confirmDialog.setHeaderText("Are you sure?");
            confirmDialog.setContentText("Delete slang word '" + slang + "'? This action cannot be undone.");

            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (dictionary.deleteSlangWord(slang)) {
                    allSlangs.removeIf(entry -> entry.getSlang().equals(slang));
                    statusLabel.setText("✓ Slang word '" + slang + "' deleted successfully!");
                    displayArea.clear();
                    slangField.clear();
                } else {
                    showAlert("Error", "Failed to delete slang word.", Alert.AlertType.ERROR);
                }
            }
        });

        inputBox.getChildren().addAll(slangField, loadBtn);
        panel.getChildren().addAll(inputBox, displayArea, statusLabel, deleteBtn);
        panel.getChildren().add(createBackButton());

        updateMainContent(panel);
    }

    // ============ 7. Reset Dictionary ============
    private void showResetPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: white;");

        Label title = createTitle("🔄 Reset Dictionary");
        panel.getChildren().add(title);

        Label message = new Label("This will reset the dictionary to its original state.\nAll changes will be lost!");
        message.setStyle("-fx-font-size: 14; -fx-text-fill: #e74c3c; -fx-text-alignment: center;");

        Button confirmBtn = new Button("Confirm Reset");
        confirmBtn.setStyle(
                "-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #e74c3c; -fx-text-fill: white;");

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 12;");

        confirmBtn.setOnAction(e -> {
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirm Reset");
            confirmDialog.setHeaderText("Are you absolutely sure?");
            confirmDialog.setContentText("All changes will be permanently lost. Reset the dictionary?");

            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                dao = new TextDao();
                allSlangs = dao.getAll();
                dictionary = new SlangDictionary(allSlangs);
                quiz = new Quiz(allSlangs);
                statusLabel.setText("✓ Dictionary reset to original state!");
            }
        });

        panel.getChildren().addAll(message, confirmBtn, statusLabel);
        panel.getChildren().add(createBackButton());

        updateMainContent(panel);
    }

    // ============ 8. Slang of the Day ============
    private void showSlangOfTheDayPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(40));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: white;");

        Label title = createTitle("⭐ Slang of the Day");
        panel.getChildren().add(title);

        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(30));
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle(
                "-fx-border-color: #3498db; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: #ecf0f1;");

        SlangEntry randomSlang = dictionary.getRandomSlangWord();

        if (randomSlang == null) {
            Label noDataLabel = new Label("No slang words available.");
            contentBox.getChildren().add(noDataLabel);
        } else {
            Label slangLabel = new Label(randomSlang.getSlang());
            slangLabel.setStyle("-fx-font-size: 36; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            Label meaningTitleLabel = new Label("Meanings:");
            meaningTitleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #34495e;");

            VBox meaningsBox = new VBox(8);
            for (String meaning : randomSlang.getMeanings()) {
                Label meaningLabel = new Label("• " + meaning);
                meaningLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #2c3e50; -fx-wrap-text: true;");
                meaningsBox.getChildren().add(meaningLabel);
            }

            contentBox.getChildren().addAll(slangLabel, meaningTitleLabel, meaningsBox);
        }

        Button newBtn = new Button("Show Another");
        newBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");
        newBtn.setOnAction(e -> showSlangOfTheDayPanel());

        panel.getChildren().addAll(contentBox, newBtn);
        panel.getChildren().add(createBackButton());

        updateMainContent(panel);
    }

    // ============ 9. Quiz: Slang -> Meaning ============
    private void showQuiz1Panel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: white;");

        Label title = createTitle("❓ Quiz: Guess the Meaning");
        panel.getChildren().add(title);

        Quiz.QuizQuestion question = quiz.generateSlangToMeaningQuiz();

        Label questionLabel = new Label(question.getQuestion());
        questionLabel
                .setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-wrap-text: true;");
        questionLabel.setWrapText(true);

        VBox optionsBox = new VBox(10);
        optionsBox.setPadding(new Insets(20));
        optionsBox.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-border-radius: 3;");

        ToggleGroup group = new ToggleGroup();
        List<RadioButton> buttons = new ArrayList<>();

        for (String option : question.getOptions()) {
            RadioButton rb = new RadioButton(option);
            rb.setToggleGroup(group);
            rb.setStyle("-fx-font-size: 13;");
            buttons.add(rb);
            optionsBox.getChildren().add(rb);
        }

        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        Button submitBtn = new Button("Submit Answer");
        submitBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");

        submitBtn.setOnAction(e -> {
            RadioButton selected = (RadioButton) group.getSelectedToggle();
            if (selected == null) {
                showAlert("Error", "Please select an answer.", Alert.AlertType.WARNING);
                return;
            }

            String selectedAnswer = selected.getText();
            if (selectedAnswer.equals(question.getCorrectAnswer())) {
                feedbackLabel.setText("✓ Correct! The answer is: " + question.getCorrectAnswer());
                feedbackLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
            } else {
                feedbackLabel.setText("✗ Wrong! The correct answer is: " + question.getCorrectAnswer());
                feedbackLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            }
        });

        Button nextBtn = new Button("Next Question");
        nextBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");
        nextBtn.setOnAction(e -> showQuiz1Panel());

        panel.getChildren().addAll(questionLabel, optionsBox, submitBtn, feedbackLabel, nextBtn);
        panel.getChildren().add(createBackButton());

        updateMainContent(panel);
    }

    // ============ 10. Quiz: Meaning -> Slang ============
    private void showQuiz2Panel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: white;");

        Label title = createTitle("❓ Quiz: Guess the Slang Word");
        panel.getChildren().add(title);

        Quiz.QuizQuestion question = quiz.generateMeaningToSlangQuiz();

        Label questionLabel = new Label(question.getQuestion());
        questionLabel
                .setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-wrap-text: true;");
        questionLabel.setWrapText(true);

        VBox optionsBox = new VBox(10);
        optionsBox.setPadding(new Insets(20));
        optionsBox.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-border-radius: 3;");

        ToggleGroup group = new ToggleGroup();
        List<RadioButton> buttons = new ArrayList<>();

        for (String option : question.getOptions()) {
            RadioButton rb = new RadioButton(option);
            rb.setToggleGroup(group);
            rb.setStyle("-fx-font-size: 13;");
            buttons.add(rb);
            optionsBox.getChildren().add(rb);
        }

        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        Button submitBtn = new Button("Submit Answer");
        submitBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");

        submitBtn.setOnAction(e -> {
            RadioButton selected = (RadioButton) group.getSelectedToggle();
            if (selected == null) {
                showAlert("Error", "Please select an answer.", Alert.AlertType.WARNING);
                return;
            }

            String selectedAnswer = selected.getText();
            if (selectedAnswer.equals(question.getCorrectAnswer())) {
                feedbackLabel.setText("✓ Correct! The answer is: " + question.getCorrectAnswer());
                feedbackLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
            } else {
                feedbackLabel.setText("✗ Wrong! The correct answer is: " + question.getCorrectAnswer());
                feedbackLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            }
        });

        Button nextBtn = new Button("Next Question");
        nextBtn.setStyle("-fx-font-size: 12; -fx-padding: 8 25;");
        nextBtn.setOnAction(e -> showQuiz2Panel());

        panel.getChildren().addAll(questionLabel, optionsBox, submitBtn, feedbackLabel, nextBtn);
        panel.getChildren().add(createBackButton());

        updateMainContent(panel);
    }

    // ============ Helper Methods ============
    private void updateMainContent(VBox newContent) {
        ScrollPane scrollPane = new ScrollPane(newContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");
        mainContent.getChildren().remove(2);
        mainContent.getChildren().add(scrollPane);
    }

    private Label createTitle(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        return label;
    }

    private Button createBackButton() {
        Button btn = new Button("← Back to Menu");
        btn.setStyle("-fx-font-size: 12; -fx-padding: 8 20;");
        btn.setOnAction(e -> updateMainContent(createMainMenuPanel()));
        return btn;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

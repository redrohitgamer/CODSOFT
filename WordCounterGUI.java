package jfxsetup;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordCounterGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Enhanced Word Counter");

        Label inputLabel = new Label("Enter text or choose a file:");
        TextArea inputTextArea = new TextArea();
        Button browseButton = new Button("Browse");
        Button countButton = new Button("Count Words");

        ListView<String> wordListView = new ListView<>();

        VBox root = new VBox(10, inputLabel, inputTextArea, browseButton, countButton, wordListView);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        browseButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose a Text File");
            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                String fileContent = readFile(file);
                inputTextArea.setText(fileContent);
            }
        });

        countButton.setOnAction(event -> {
            String inputText = inputTextArea.getText();
            String[] words = inputText.split("[\\s\\p{Punct}]+");

            Map<String, Integer> wordFrequencyMap = new HashMap<>();
            for (String word : words) {
                wordFrequencyMap.put(word.toLowerCase(), wordFrequencyMap.getOrDefault(word.toLowerCase(), 0) + 1);
            }

            wordListView.getItems().clear();
            for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
                wordListView.getItems().add(entry.getKey() + ": " + entry.getValue());
            }
        });
    }

    public static String readFile(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file.");
        }
        return content.toString();
    }

    // Add more enhancements like ignoring common words and statistics
}

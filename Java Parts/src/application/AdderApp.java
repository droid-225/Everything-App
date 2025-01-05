package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AdderApp extends Application {

    private static final String JSON_PATH = "../Shared/data.json";
    private static final String PYTHON_SCRIPT = "../Python Parts/adder.py";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Adder App");

        VBox layout = new VBox(10);

        // Input fields
        TextField num1Field = new TextField();
        num1Field.setPromptText("Enter first number");

        TextField num2Field = new TextField();
        num2Field.setPromptText("Enter second number");

        // Label to display the result
        Label resultLabel = new Label("Result: ");

        // Button to run the operation
        Button runButton = new Button("Run Adder");
        runButton.setOnAction(e -> {
            try {
                // Get user input
                int num1 = Integer.parseInt(num1Field.getText());
                int num2 = Integer.parseInt(num2Field.getText());

                // Write to JSON file
                JSONObject data = new JSONObject();
                data.put("operation", "add");
                data.put("num1", num1);
                data.put("num2", num2);
                writeJsonToFile(data, JSON_PATH);

                // Run the Python program
                runPythonScript();
                
                try
                {
                    Thread.sleep(11); // Sleep for one second
                }
                catch (InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                
                // Read the result from JSON file
                JSONObject resultData = readJsonFromFile(JSON_PATH);
                if ("done".equals(resultData.getString("operation"))) {
                    resultLabel.setText("Result: " + resultData.getInt("result"));
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
            } catch (Exception ex) {
                resultLabel.setText("An error occurred: " + ex.getMessage());
            }
        });

        // Add components to the layout
        layout.getChildren().addAll(num1Field, num2Field, runButton, resultLabel);

        // Set the scene and show the stage
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void runPythonScript() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("python", PYTHON_SCRIPT);
        processBuilder.start();
    }

    private void writeJsonToFile(JSONObject jsonObject, String filePath) throws IOException {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonObject.toString());
        }
    }

    private JSONObject readJsonFromFile(String filePath) throws IOException {
        try (FileReader reader = new FileReader(new File(filePath))) {
            StringBuilder jsonText = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                jsonText.append((char) i);
            }
            return new JSONObject(jsonText.toString());
        }
    }
}

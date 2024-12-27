package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AdderApp extends Application {

    @Override
    public void start(Stage stage) {
        // Create UI components
        TextField num1Field = new TextField();
        num1Field.setPromptText("Enter first number");

        TextField num2Field = new TextField();
        num2Field.setPromptText("Enter second number");

        Button addButton = new Button("Add");
        Label resultLabel = new Label();

        // Add button click handler
        addButton.setOnAction(event -> {
            try {
                double num1 = Double.parseDouble(num1Field.getText());
                double num2 = Double.parseDouble(num2Field.getText());

                // Call the FastAPI server and get the result
                String result = callAdderAPI(num1, num2);
                resultLabel.setText("Result: " + result);
            } catch (NumberFormatException e) {
                resultLabel.setText("Please enter valid numbers.");
            } catch (Exception e) {
                resultLabel.setText("Error: " + e.getMessage());
            }
        });

        // Layout
        VBox layout = new VBox(10, num1Field, num2Field, addButton, resultLabel);
        layout.setPadding(new Insets(15));

        // Scene and Stage
        Scene scene = new Scene(layout, 300, 200);
        stage.setTitle("Adder Application");
        stage.setScene(scene);
        stage.show();
    }

    // Method to call the FastAPI server
    private String callAdderAPI(double num1, double num2) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Create JSON payload
        String jsonPayload = String.format("{\"num1\": %f, \"num2\": %f}", num1, num2);

        // Build HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://127.0.0.1:8000/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        // Send request and get response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Extract result from the response
        return response.body();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

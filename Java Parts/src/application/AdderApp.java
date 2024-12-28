package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;

public class AdderApp {
    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Adder App");

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Input fields
        TextField num1Field = new TextField();
        TextField num2Field = new TextField();
        Label resultLabel = new Label();

        // Buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            try {
                int num1 = Integer.parseInt(num1Field.getText());
                int num2 = Integer.parseInt(num2Field.getText());
                int result = callAdderApi(num1, num2); // Call FastAPI backend
                resultLabel.setText("Result: " + result);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
            }
        });

        // Add nodes to grid
        grid.add(new Label("Number 1:"), 0, 0);
        grid.add(num1Field, 1, 0);
        grid.add(new Label("Number 2:"), 0, 1);
        grid.add(num2Field, 1, 1);
        grid.add(addButton, 1, 2);
        grid.add(resultLabel, 1, 3);

        // Show the scene
        stage.setScene(new Scene(grid, 300, 200));
        stage.show();
    }

    private int callAdderApi(int num1, int num2) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://127.0.0.1:8000/add";
        String jsonBody = String.format("{\"num1\": %d, \"num2\": %d}", num1, num2);

        RequestBody body = RequestBody.create(
                jsonBody, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return Integer.parseInt(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

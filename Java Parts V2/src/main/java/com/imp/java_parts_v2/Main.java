package com.imp.java_parts_v2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Everything App");

            // Main layout for the dashboard
            VBox layout = new VBox(20);

            // Button to open the Adder App
            Button adderButton = new Button("Adder App");
            adderButton.setOnAction(e -> openAdderApp());

            // Add buttons to layout
            layout.getChildren().addAll(adderButton);

            // Create and set the scene
            Scene scene = new Scene(layout, 400, 300);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAdderApp() {
        // Launch Adder App
        AdderApp adderApp = new AdderApp();
        adderApp.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

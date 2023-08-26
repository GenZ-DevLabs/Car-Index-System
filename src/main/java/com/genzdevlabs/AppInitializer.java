package com.genzdevlabs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/StartForm.fxml")))));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

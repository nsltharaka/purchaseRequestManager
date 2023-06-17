package com;

import java.io.IOException;

import com.controllers.SceneController;
import com.service.db.DBConnection;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        SceneController.getInitialScene(stage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void init() throws Exception {
        // initialize the database properties
        DBConnection.testConnection();
    }

    public static void main(String[] args) {
        launch();
    }

}
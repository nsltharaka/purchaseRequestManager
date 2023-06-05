package com.controllers;

import java.io.IOException;
import java.util.Optional;

import com.util.helpers.IconProvider;
import com.util.helpers.ScenePath;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneController {

    private static double x;
    private static double y;

    private static Stage loginStage;
    private static Parent root;

    private static void controlDrag(Stage stage) {

        stage.setMaximized(false);

        root.setOnMousePressed(event -> {
            x = stage.getX() - event.getScreenX();
            y = stage.getY() - event.getScreenY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + x);
            stage.setY(event.getScreenY() + y);
        });
    }

    private static void setAnchorPaneConstrains(Node child) {
        AnchorPane.setTopAnchor(child, 0.0);
        AnchorPane.setRightAnchor(child, 0.0);
        AnchorPane.setBottomAnchor(child, 0.0);
        AnchorPane.setLeftAnchor(child, 0.0);
    }

    public static void getInitialScene(Stage stage) throws IOException {
        // get the login screen
        root = FXMLLoader.load(SceneController.class.getResource(ScenePath.LOGIN.getPath()));

        // create the scene
        Scene scene = new Scene(root);

        // show on the stage
        controlDrag(stage);
        stage.setScene(scene);
        stage.getIcons().addAll(IconProvider.getWindowIcons());
        stage.centerOnScreen();
        stage.show();
    }

    public static void getMainScene(Stage loginStage) throws IOException {
        SceneController.loginStage = loginStage;

        root = FXMLLoader.load(SceneController.class.getResource(ScenePath.HOME.getPath()));
        Scene scene = new Scene(root);

        Stage stage = new Stage();

        stage.setOnCloseRequest(event -> {
            logOutAndClose(stage);
            event.consume();
        });

        controlDrag(stage);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().addAll(IconProvider.getWindowIcons());
        stage.centerOnScreen();
        stage.show();
    }

    public static void change(AnchorPane pane, String path) throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource(path));

        // make root takes the full width and the height of the parent.
        setAnchorPaneConstrains(root);

        pane.getChildren().clear();
        pane.getChildren().add(root);

    }

    public static void close(Stage stage) {
        stage.close();
    }

    public static void logOutAndClose(Stage stage) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to log out?");

        Optional<ButtonType> alertResult = alert.showAndWait();
        alertResult.ifPresent(btnType -> {
            if (btnType == ButtonType.OK) {
                stage.close();
                loginStage.show();
            }
        });

    }

}

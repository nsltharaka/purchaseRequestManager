package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.controllers.dialogControllers.PriceQuotationDialog;
import com.util.helpers.ScenePath;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeScreenController {

    private @FXML ResourceBundle resources;
    private @FXML URL location;
    private @FXML Button btnClose;
    private @FXML Button btnMaximize;
    private @FXML Button btnMinimize;
    private @FXML Button btnDashBoard;
    private @FXML Button btnItems;
    private @FXML Button btnPurchaseRequests;
    private @FXML Button btnPriceQuotations;
    private @FXML Button btnPurchaseOrders;
    private @FXML Button btnReports;
    private @FXML Label lblWindowTitle;
    private @FXML AnchorPane mainPanel;
    private @FXML VBox buttonContainer;

    @FXML
    void getDashBoardWindow(ActionEvent event) throws IOException {
        SceneController.change(mainPanel, ScenePath.DashBoard.getPath());
        removeActive();
        btnDashBoard.getStyleClass().add("active");
    }

    @FXML
    void getRequestedItemsWindow(ActionEvent event) throws IOException {
        SceneController.change(mainPanel, ScenePath.REQUESTED_ITEMS.getPath());
        removeActive();
        btnItems.getStyleClass().add("active");
    }

    @FXML
    void getPurchaseRequestsWindow(ActionEvent event) throws IOException {
        SceneController.change(mainPanel, ScenePath.PURCHASE_REQUEST.getPath());
        removeActive();
        btnPurchaseRequests.getStyleClass().add("active");
    }

    @FXML
    void getPriceQuotationsWindow(ActionEvent event) throws IOException {
        SceneController.change(mainPanel, ScenePath.PRICE_QUOTATION.getPath());
        removeActive();
        btnPriceQuotations.getStyleClass().add("active");
    }

    @FXML
    void getPurchaseOrdersWindow(ActionEvent event) throws IOException {
        SceneController.change(mainPanel, ScenePath.PURCHASE_ORDER.getPath());
        removeActive();
        btnPurchaseOrders.getStyleClass().add("active");
    }

    @FXML
    void getReportsWindow(ActionEvent event) throws IOException {
        SceneController.change(mainPanel, ScenePath.REPORTS.getPath());
        removeActive();
        btnReports.getStyleClass().add("active");
    }

    @FXML
    void handleLogOut(ActionEvent event) {
        SceneController.logOutAndClose((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    void handleWindowAction(ActionEvent event) {

        var source = event.getSource();

        if (source == btnClose) {
            handleLogOut(event);

        } else if (source == btnMaximize) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setMaximized(!stage.maximizedProperty().getValue());

        } else {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setIconified(true);
        }
    }

    @FXML
    void initialize() throws IOException {
        // assume dashboard button is clicked at the startup
        getDashBoardWindow(new ActionEvent(btnDashBoard, null));
    }

    private void removeActive() {
        btnDashBoard.getStyleClass().remove("active");
        btnItems.getStyleClass().remove("active");
        btnPurchaseRequests.getStyleClass().remove("active");
        btnPriceQuotations.getStyleClass().remove("active");
        btnPurchaseOrders.getStyleClass().remove("active");
        btnReports.getStyleClass().remove("active");
    }

}
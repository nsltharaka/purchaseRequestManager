package com.controllers.dialogControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.util.PurchaseRequestStatus;
import com.util.helpers.DialogPath;
import com.util.helpers.Tuple;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;

public class PurchaseRequestUpdateStatusDialog extends Dialog<Tuple<PurchaseRequestStatus, String>> {

    public static class Controller {
        private @FXML ResourceBundle resources;
        private @FXML URL location;
        private @FXML ComboBox<PurchaseRequestStatus> cmbItemStatus;
        private @FXML TextArea txtComment;

        @FXML
        void initialize() {
            cmbItemStatus.getItems().setAll(PurchaseRequestStatus.values());
        }
    }

    private Controller controller;

    public PurchaseRequestUpdateStatusDialog() {
        super();
        this.setTitle("Update Status");
        this.setDialogPane(loadFXML());
    }

    private DialogPane loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DialogPath.PR_UPDATE_STATUS_DIALOG.getPath()));

        try {
            DialogPane root = loader.load();
            controller = loader.getController();

            return root;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

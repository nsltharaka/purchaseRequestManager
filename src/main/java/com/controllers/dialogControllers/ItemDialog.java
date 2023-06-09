package com.controllers.dialogControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.dto.ItemDTO;
import com.util.helpers.DialogPath;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class ItemDialog extends Dialog<ItemDTO> {

    public static class Controller {
        private @FXML ResourceBundle resources;
        private @FXML URL location;
        public @FXML TextField txtItemDescription;
        public @FXML TextField txtItemName;
        public @FXML TextField txtItemUnit;
        public @FXML TextField txtRequestedQuantity;
        public @FXML ComboBox<String> cmbItemCategory;

        @FXML
        void initialize() {

            cmbItemCategory.getItems().addAll(
                    "STATIONARY",
                    "WELFARE",
                    "PRODUCTION",
                    "SAFETY",
                    "COMPUTER_PARTS",
                    "OTHER");

            // making sure that quantity contains only numbers
            txtRequestedQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^(?!0\\d)(\\d{0,5})(\\.\\d{0,2})?$")) {
                    txtRequestedQuantity.setText("0");
                }
            });
        }

    }

    private ItemDTO itemDTO;
    private Controller controller;

    public ItemDialog() {
        this(new ItemDTO());
    }

    public ItemDialog(ItemDTO dto) {
        super();
        this.itemDTO = dto;
        this.setTitle("Item");
        this.setDialogPane(loadFXML());
        setPropertyBindings();
        this.setResultConverter(this::resultConverter);

        var button = this.getDialogPane().lookupButton(ButtonType.APPLY);
        button.disableProperty().bind(isInValid());

    }

    private BooleanBinding isInValid() {

        return controller.txtItemName.textProperty().isEmpty()
                .or(controller.txtRequestedQuantity.textProperty().isEmpty())
                .or(controller.txtItemUnit.textProperty().isEmpty());
    }

    private void setPropertyBindings() {

        controller.txtItemName.textProperty().bindBidirectional(itemDTO.itemName);
        controller.txtItemDescription.textProperty().bindBidirectional(itemDTO.itemDescription);
        controller.txtItemUnit.textProperty().bindBidirectional(itemDTO.quantityUnit);
        controller.cmbItemCategory.valueProperty().bindBidirectional(itemDTO.itemCategory);
        controller.txtRequestedQuantity.textProperty().bindBidirectional(itemDTO.itemQuantity,
                new NumberStringConverter());
    }

    private DialogPane loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DialogPath.ITEM_DIALOG.getPath()));

        try {
            DialogPane root = loader.load();
            controller = loader.getController();

            return root;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ItemDTO resultConverter(ButtonType buttonType) {

        if (buttonType == ButtonType.APPLY) {

            return itemDTO;
        }

        return null;

    }

    public void setPropertiesWithoutBinding(ItemDTO dto) {
        itemDTO.itemName.set(dto.itemName.get());
        itemDTO.itemDescription.set(dto.itemDescription.get());
        itemDTO.itemQuantity.set(dto.itemQuantity.get());
        itemDTO.quantityUnit.set(dto.quantityUnit.get());
        itemDTO.itemCategory.set(dto.itemCategory.get());
    }
}

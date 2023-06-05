package com.util.dialog;

import java.io.IOException;

import com.controllers.dialogControllers.ItemDialogController;
import com.model.dto.ItemDTO;
import com.util.helpers.DialogPath;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class ItemDialog extends Dialog<ItemDTO> {

    private ItemDTO itemDTO;
    private ItemDialogController controller;

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

        // using a formatter to do the validation on enter or unfocus.
        // defaults to 0 if parser failed to convert.
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), 0);
        controller.txtRequestedQuantity.setTextFormatter(formatter);
        formatter.valueProperty()
                .bindBidirectional(itemDTO.itemQuantity.asObject());

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
    }
}

package com.controllers.dialogControllers;

import java.io.IOException;
import java.util.List;

import com.model.dto.ItemDTO;
import com.model.dto.PriceQuotationDTO;
import com.util.helpers.DialogPath;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PriceQuotationDialog extends Dialog<PriceQuotationDTO> {

    public static class PriceQuotationController {

        private @FXML CheckBox chkSelectedSupplier;

        private @FXML TableView<ItemDTO> tblItems;
        private @FXML TableColumn<ItemDTO, String> columnItemDescription;
        private @FXML TableColumn<ItemDTO, String> columnItemName;
        private @FXML TableColumn<ItemDTO, Integer> columnItemQuantity;
        private @FXML TableColumn<ItemDTO, String> columnItemUnit;

        private @FXML TextField txtSupplierName;
        private @FXML TextArea txtSupplierAddress;
        private @FXML TextField txtQuotedPrice;

        @FXML
        void initialize() {

            columnItemName.setCellValueFactory(param -> param.getValue().itemName);
            columnItemDescription.setCellValueFactory(param -> param.getValue().itemDescription);
            columnItemUnit.setCellValueFactory(param -> param.getValue().quantityUnit);
            columnItemQuantity.setCellValueFactory(param -> param.getValue().itemQuantity.asObject());

        }

    }

    private PriceQuotationController controller;
    private List<ItemDTO> itemList;

    public PriceQuotationDialog(List<ItemDTO> itemList) {
        super();
        this.itemList = itemList;
        this.setTitle("Price Quotation");
        this.setDialogPane(loadFXML());

        setPropertyBindings();
        this.setResultConverter(this::resultConverter);
    }

    private void setPropertyBindings() {

    }

    private PriceQuotationDTO resultConverter(ButtonType buttonType) {
        return null;

    }

    private DialogPane loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DialogPath.PRICE_QUOTATION_DIALOG.getPath()));

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

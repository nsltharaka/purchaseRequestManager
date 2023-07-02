package com.controllers.dialogControllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.model.dto.ItemDTO;
import com.model.dto.PriceQuotationDTO;
import com.util.UserRole;
import com.util.helpers.CurrentUser;
import com.util.helpers.DialogPath;
import com.util.helpers.Tuple;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

            setTableProperties();
        }

        private void setTableProperties() {
            columnItemName.setCellValueFactory(param -> param.getValue().itemName);
            columnItemDescription.setCellValueFactory(param -> param.getValue().itemDescription);
            columnItemUnit.setCellValueFactory(param -> param.getValue().quantityUnit);
            columnItemQuantity.setCellValueFactory(param -> param.getValue().itemQuantity.asObject());
        }

    }

    private PriceQuotationController controller;
    private SimpleListProperty<ItemDTO> itemList = new SimpleListProperty<>(FXCollections.observableArrayList());

    private PriceQuotationDTO priceQuotationDTO;
    private Map<ItemDTO, Tuple<Double, Double>> map;

    public PriceQuotationDialog(ObservableList<ItemDTO> itemList,
            ButtonType... buttonTypes) {

        this(new PriceQuotationDTO(), itemList, buttonTypes);
    }

    public PriceQuotationDialog(PriceQuotationDTO priceQuotationDTO, ObservableList<ItemDTO> itemList,
            ButtonType... buttonTypes) {
        super();
        this.priceQuotationDTO = priceQuotationDTO;
        this.setTitle("Price Quotation");
        this.setDialogPane(loadFXML());
        this.getDialogPane().getButtonTypes().addAll(buttonTypes);
        this.setResultConverter(this::resultConverter);

        map = new HashMap<>();

        priceQuotationDTO = new PriceQuotationDTO();
        setItems(itemList);

        setPropertyBindings();
        handleMapBindings();
    }

    private void handleMapBindings() {

        itemList.forEach(i -> {
            map.put(i, new Tuple<Double, Double>(0.0, 0.0));
        });

        controller.tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            controller.txtQuotedPrice.setText(map.get(newValue).value_0.toString());
        });

        controller.txtQuotedPrice.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("^(?!0\\d)(\\d{0,5})(\\.\\d{0,2})?$")) {
                controller.txtQuotedPrice.setText(oldValue);
            }

            var i = controller.tblItems.getSelectionModel().selectedItemProperty().get();
            map.compute(i, (item, tpl) -> {

                double price = Double.parseDouble(newValue);
                return new Tuple<Double, Double>(
                        price,
                        item.itemQuantity.get() * price);

            });

        });

    }

    private void setPropertyBindings() {

        // table items
        controller.tblItems.itemsProperty().bind(itemList);
        // checkbox visibility
        controller.chkSelectedSupplier.visibleProperty()
                .bind(CurrentUser.getCurrentUser().userRole.isEqualTo(UserRole.MANAGER));

        controller.txtQuotedPrice.disableProperty()
                .bind(controller.tblItems.getSelectionModel().selectedItemProperty().isNull());

        // Price quotation property bindings
        controller.txtSupplierName.textProperty().bindBidirectional(priceQuotationDTO.supplierName);
        controller.txtSupplierAddress.textProperty().bindBidirectional(priceQuotationDTO.supplierAddress);
        controller.chkSelectedSupplier.selectedProperty().bindBidirectional(priceQuotationDTO.isApproved);

        Node button;
        if ((button = this.getDialogPane().lookupButton(ButtonType.APPLY)) != null) {
            button.disableProperty()
                    .bind(validate());
        }
    }

    private ObservableValue<? extends Boolean> validate() {

        return controller.txtSupplierName.textProperty().isEmpty()
                .or(controller.txtSupplierAddress.textProperty().isEmpty())
                .asObject();
    }

    private PriceQuotationDTO resultConverter(ButtonType buttonType) {

        if (!buttonType.equals(ButtonType.APPLY))
            return null;

        map.forEach((k, v) -> {
            priceQuotationDTO.item_quotedPrice.put(k.itemId.get(), v.value_0);
        });

        double sum = map.values().stream()
                .map(t -> t.value_1)
                .collect(Collectors.summarizingDouble(t -> t)).getSum();

        priceQuotationDTO.setQuotedTotal(sum);
        return priceQuotationDTO;

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

    public void setPriceQuotationDTO(PriceQuotationDTO priceQuotationDTO) {
        // this.priceQuotationDTO
        // .setIsApproved(priceQuotationDTO.isApproved.get())
        // .setPriceQuotationId(priceQuotationDTO.priceQuotationId.get())
        // .setPriceQuotationReportId(priceQuotationDTO.priceQuotationReportId.get())
        // .setQuotedTotal(priceQuotationDTO.quotedTotal.get())
        // .setSupplierAddress(priceQuotationDTO.supplierAddress.get())
        // .setSupplierName(priceQuotationDTO.supplierName.get())
        // .setIsApproved(priceQuotationDTO.isApproved.get());

        this.priceQuotationDTO = priceQuotationDTO;

        for (var en : map.entrySet()) {
            for (var ent : priceQuotationDTO.item_quotedPrice.entrySet()) {
                if (en.getKey().itemId.get().equals(ent.getKey())) {
                    en.getValue().value_0 = ent.getValue();
                }
            }
        }

        setPropertyBindings();

    }

    private void setItems(ObservableList<ItemDTO> list) {
        this.itemList.setAll(list);
    }

}
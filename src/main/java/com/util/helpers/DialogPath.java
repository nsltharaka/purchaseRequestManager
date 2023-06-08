package com.util.helpers;

public enum DialogPath {

    PURCHASE_REQUEST_DIALOG("/view/dialogs/PurchaseRequestDialog.fxml"),
    ITEM_DIALOG("/view/dialogs/ItemDialog.fxml"),
    PRICE_QUOTATION_DIALOG("/view/dialogs/PriceQuotationDialog.fxml"),
    ITEM_UPDATE_STATUS_DIALOG("/view/dialogs/ItemUpdateStatusDialog.fxml");

    private String path;

    private DialogPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

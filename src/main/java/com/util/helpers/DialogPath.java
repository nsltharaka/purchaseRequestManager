package com.util.helpers;

public enum DialogPath {

    PURCHASE_REQUEST_DIALOG("/view/dialogs/PurchaseRequestDialog.fxml"),
    ITEM_DIALOG("/view/dialogs/ItemDialog.fxml");

    private String path;

    private DialogPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

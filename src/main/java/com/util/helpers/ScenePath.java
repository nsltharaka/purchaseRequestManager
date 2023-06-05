package com.util.helpers;

public enum ScenePath {
    LOGIN("/view/LoginRoot.fxml"),
    HOME("/view/HomeRoot.fxml"),
    DashBoard("/view/DashBoard.fxml"),
    PURCHASE_REQUEST("/view/PurchaseRequest.fxml"),
    PRICE_QUOTATION("/view/PriceQuotation.fxml"),
    PURCHASE_ORDER("/view/PurchaseOrder.fxml"),
    REPORTS("/view/Report.fxml"),
    REQUESTED_ITEMS("/view/RequestedItems.fxml");

    private String path;

    private ScenePath(String scenePath) {
        this.path = scenePath;
    }

    public String getPath() {
        return path;
    }

}

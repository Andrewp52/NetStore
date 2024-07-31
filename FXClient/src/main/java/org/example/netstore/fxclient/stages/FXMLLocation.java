package org.example.netstore.fxclient.stages;

public enum FXMLLocation {
    MAIN("fxml/MainWindow.fxml"),
    LOGIN("fxml/LoginWindow.fxml"),
    ENTER_STRING("fxml/EnterStringWindow.fxml"),
    NOTIFICATION("fxml/NotificationWindow.fxml"),
    EXCHANGE("fxml/ExchangeWindow.fxml");

    private final String path;

    FXMLLocation(String path) {
        this.path = path;
    }


    @Override
    public String toString() {
        return this.path;
    }
}

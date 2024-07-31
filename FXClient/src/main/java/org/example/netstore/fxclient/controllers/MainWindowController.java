package org.example.netstore.fxclient.controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.example.netstore.common.dto.UserDto;
import org.example.netstore.fxclient.controllers.filebrowsertable.FileBrowserTableInitializer;
import org.example.netstore.fxclient.netclient.NetClient;
import org.example.netstore.fxclient.netclient.NetClientFactory;
import org.example.netstore.fxclient.services.browsers.FileBrowser;
import org.example.netstore.fxclient.services.browsers.local.LocalFileBrowser;
import org.example.netstore.fxclient.services.browsers.remote.RemoteFileBrowser;
import org.example.netstore.fxclient.services.exchange.ProgressMonitoringExchangerImpl;
import org.example.netstore.fxclient.services.fileviewers.DesktopRemoteViewer;
import org.example.netstore.fxclient.services.fileviewers.DesktopViewer;
import org.example.netstore.fxclient.stages.EnterStringStage;
import org.example.netstore.fxclient.stages.ExchangeWindowStage;
import org.example.netstore.fxclient.stages.LoginWindowStage;
import org.example.netstore.fxclient.stages.notifications.ErrorWindowStage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML public Label connectionStatusLabel;
    @FXML public MenuItem deleteProfileMenuItem;
    @FXML public MenuItem changePasswordMenuItem;
    @FXML public MenuItem editProfileMenuItem;
    @FXML public MenuItem connectMenuItem;
    @FXML public Label currentOperationLabel;
    @FXML public SplitPane splitPane;
    @FXML public TableView<File> remoteBrowserTable;
    @FXML public TableView<File> localBrowserTable;
    @FXML public ToolBar localToolbar;
    @FXML public ToolBar remoteToolbar;
    private NetClient client;
    private FileBrowser localBrowser;
    private FileBrowser remoteBrowser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setControlsToDisconnectedState();
        connectAndAuthenticate();
        this.client = NetClientFactory.getClient();
        this.localBrowser = new LocalFileBrowser();
        this.remoteBrowser = new RemoteFileBrowser(client);
        FileBrowserTableInitializer.init(
                this.localBrowserTable,
                this.localBrowser,
                new DesktopViewer()
        );
        FileBrowserTableInitializer.init(
                this.remoteBrowserTable,
                this.remoteBrowser,
                new DesktopRemoteViewer(this.client)
        );
        setControlsToConnectedState();
    }


    private void connect() {
        try {
            this.client.connect();
            setControlsToConnectedState();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void disconnect() {
        this.client.disconnect();
        setControlsToDisconnectedState();
    }

    private void setControlsToConnectedState() {
        this.connectMenuItem.setText("Disconnect");
        this.connectMenuItem.setOnAction(e -> this.disconnect());
        this.connectionStatusLabel.setText("Connected");
        this.changePasswordMenuItem.setDisable(false);
        this.editProfileMenuItem.setDisable(false);
        this.deleteProfileMenuItem.setDisable(false);
        this.splitPane.setDisable(false);
    }

    private void setControlsToDisconnectedState() {
        this.connectMenuItem.setText("Connect");
        this.connectMenuItem.setOnAction(e -> this.connect());
        this.connectionStatusLabel.setText("Not connected");
        this.changePasswordMenuItem.setDisable(true);
        this.editProfileMenuItem.setDisable(true);
        this.deleteProfileMenuItem.setDisable(true);
        this.splitPane.setDisable(true);
    }

    public void connectMenuItemClicked(ActionEvent actionEvent) {
        connect();
    }

    public void closeMenuItemClicked(ActionEvent actionEvent) {
        disconnect();
        ((Stage) connectionStatusLabel.getScene().getWindow()).close();
    }

    public void editProfileMenuItemClicked(ActionEvent actionEvent) {
    }

    public void changePasswordMenuItemClicked(ActionEvent actionEvent) {
    }

    public void deleteProfileMenuItemClicked(ActionEvent actionEvent) {
    }

    public void connectionSettingsMenuItemClicked(ActionEvent actionEvent) {
    }

    public void aboutMenuItemClicked(ActionEvent actionEvent) {
    }

    public void copyButtonClicked(ActionEvent actionEvent) {
    }

    public void cutButtonClicked(ActionEvent actionEvent) {
    }

    public void pasteButtonClicked(ActionEvent actionEvent) {
    }

    public void renameButtonClicked(ActionEvent actionEvent) {
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
    }

    public void newDirButtonClicked(ActionEvent actionEvent) throws IOException {
        try {
            String path = new EnterStringStage("Create directory", "New directory path").display();
            switch (((Button) actionEvent.getSource()).getId()) {
                case "localNewDirButton" -> addToBrowserTable(localBrowserTable, localBrowser.mkDir(path));
                case "remoteNewDirButton" -> addToBrowserTable(remoteBrowserTable, remoteBrowser.mkDir(path));
            }
        } catch (IOException e) {
            new ErrorWindowStage("New directory error", e.getMessage()).showAndWait();
        }
    }

    public void homeButtonClicked(ActionEvent actionEvent) {
        updateBrowserTable(remoteBrowserTable, remoteBrowser.dir("", true));
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        switch (((Button) actionEvent.getSource()).getId()) {
            case "localBackButton" -> updateBrowserTable(localBrowserTable, localBrowser.back());
            case "remoteBackButton" -> updateBrowserTable(remoteBrowserTable, remoteBrowser.back());
        }
    }

    private void connectAndAuthenticate() {
        try {
            LoginWindowStage lws = new LoginWindowStage();
            UserDto userDto;
            while ((userDto = lws.display()) == null && !lws.isCancelled()) ;
            if (userDto == null || lws.isCancelled()) {
                Platform.exit();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addToBrowserTable(TableView<File> browserTable, File file) {
        if (file == null) {
            return;
        }
        browserTable.getItems().add(file);
        browserTable.sort();
    }

    private void updateBrowserTable(TableView<File> browserTable, List<? extends File> files) {
        browserTable.getItems().setAll(files);
        browserTable.sort();
    }

    public void updatePanelsOnShow() {
        updateBrowserTable(localBrowserTable, localBrowser.dir());
        updateBrowserTable(remoteBrowserTable, remoteBrowser.dir());
    }

    public void download(ActionEvent actionEvent) throws IOException {
        try {
            new ExchangeWindowStage(
                    new ProgressMonitoringExchangerImpl(localBrowser, remoteBrowser),
                    remoteBrowserTable.getSelectionModel().getSelectedItems(),
                    true
            ).showAndWait();
            updateBrowserTable(localBrowserTable, localBrowser.dir());
        } catch (IOException e) {
            new ErrorWindowStage("Download error", e.getMessage());
        }
    }

    public void upload(ActionEvent actionEvent) throws IOException {
        try {
            new ExchangeWindowStage(
                    new ProgressMonitoringExchangerImpl(localBrowser, remoteBrowser),
                    localBrowserTable.getSelectionModel().getSelectedItems(),
                    false
            ).showAndWait();
            updateBrowserTable(remoteBrowserTable, remoteBrowser.dir());
        } catch (IOException e) {
            new ErrorWindowStage("Upload error", e.getMessage());
        }
    }
}

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public class Controller {
    private static final String USER = System.getProperty("user.name");
    private ObservableList<TableRow> rowData = FXCollections.observableArrayList();
    @FXML
    public CheckBox chromeCacheCB;
    @FXML
    public Button cleanButton;
    @FXML
    public CheckBox chromeHistoryCB;
    @FXML
    public CheckBox chromeCookiesCB;
    @FXML
    public CheckBox googleChromeCB;
    @FXML
    public CheckBox systemCB;
    @FXML
    public CheckBox recycleBinCB;
    @FXML
    public CheckBox tempFilesCB;
    @FXML
    public TableView<TableRow> tableView;
    @FXML
    public TableColumn<TableRow, String> picId;
    @FXML
    public TableColumn<TableRow, String> categoryId;
    @FXML
    public TableColumn<TableRow, Double> sizeId;


    @FXML
    void initialize(){
        chromeCookiesCB.setDisable(true);
        chromeHistoryCB.setDisable(true);
        chromeCacheCB.setDisable(true);
        recycleBinCB.setDisable(true);
        tempFilesCB.setDisable(true);

        picId.setCellValueFactory(new PropertyValueFactory<TableRow, String>("pic"));
        categoryId.setCellValueFactory(new PropertyValueFactory<TableRow, String>("rowName"));
        sizeId.setCellValueFactory(new PropertyValueFactory<TableRow, Double>("size"));


        cleanButton.setOnAction((e)->{
            if (cleanButton.getText().equals("Analize")) {
                if (chromeCacheCB.isSelected()) {
                    File chromeCache = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cache");
                    Collection<File> fileCollection = FileUtils.listFiles(chromeCache, null, true);
                    Long sizeOfChromeCache = fileCollection.stream().map(a -> a.length()).reduce((a, b) -> a + b).orElse(0L);
                    double rounded = rounder(sizeOfChromeCache / 1024.0);
                    rowData.add(new TableRow("r", "Google Chrome Cache", rounded));
                }

                if (chromeHistoryCB.isSelected()){
                    File chromeHistory = new File("C:\\Users\\"+ USER +"\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\History");
                    long sizeOfChromeHistory = chromeHistory.length();
                    double roundedChromeHistory = rounder(sizeOfChromeHistory/1024.0);
                    rowData.add(new TableRow("r", "Google Chrome History", roundedChromeHistory));
                }

                if (chromeCookiesCB.isSelected()){
                    File chromeCookies = new File("C:\\Users\\"+ USER+"\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cookies");
                    long sizeOfChromeCookies = chromeCookies.length();
                    double roundedChromeCookies = rounder(sizeOfChromeCookies / 1024.0);
                    rowData.add(new TableRow("r", "Google Chrome Cookies", roundedChromeCookies));
                }

                if (recycleBinCB.isSelected()){
                    File recycleBin = new File("C:\\$Recycle.Bin");
                    Collection<File> recycleBinCollection = FileUtils.listFiles(recycleBin, null, true);
                    Long sizeOfRecycleBin = recycleBinCollection.stream().map(a -> a.length()).reduce((a, b) -> a + b).orElse(0L);
                    double roundedRecycleBin = rounder(sizeOfRecycleBin / 1024.0);
                    rowData.add(new TableRow("r", "Recycle Bin", roundedRecycleBin));
                }

                if (tempFilesCB.isSelected()){
                    File tempFiles = new File("C:\\Users\\"+USER+"\\AppData\\Local\\Temp");
                    Collection<File> tempFilesCollection = FileUtils.listFiles(tempFiles, null, true);
                    Long sizeOfTempFiles = tempFilesCollection.stream().map(a -> a.length()).reduce((a, b) -> a + b).orElse(0L);
                    double roundedTempFiles = rounder(sizeOfTempFiles / 1024.0);
                    rowData.add(new TableRow("r", "Temporary Files", roundedTempFiles));
                }

                tableView.setItems(rowData);
                cleanButton.setText("Clean");
                cleanButton.setStyle("-fx-background-color:#bbff99");
            } else {
                if(chromeCacheCB.isSelected()){
                    File chromeCache = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cache");
                    Collection<File> fileCollection = FileUtils.listFiles(chromeCache, null, true);
                    fileCollection.forEach(a->a.delete());
                }

                if (chromeHistoryCB.isSelected()){
                    deleteChromeHistoryCb(true);
                }

                if (chromeCookiesCB.isSelected()){
                    File chromeCookies = new File("C:\\Users\\"+ USER+"\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cookies");
                    try {
                        FileUtils.forceDelete(chromeCookies);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                if (recycleBinCB.isSelected()){
                    File recycleBin = new File("C:\\$Recycle.Bin");
                    Collection<File> recycleBinCollection = FileUtils.listFiles(recycleBin, null, true);
                    recycleBinCollection.forEach(a->a.delete());
                }

                if (tempFilesCB.isSelected()){
                    File tempFiles = new File("C:\\Users\\"+USER+"\\AppData\\Local\\Temp");
                    Collection<File> tempFilesCollection = FileUtils.listFiles(tempFiles, null, true);
                    tempFilesCollection.forEach(a->a.delete());
                }
                rowData.clear();
                cleanButton.setText("Analize");
                cleanButton.setStyle("-fx-background-color: #cce6ff");
            }
        });
    }

    private void showConfirmation() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Google Chrome History Problem");
        alert.setHeaderText("Close Google Chrome and all his processes and try again.");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("brick.png").toString()));

        ButtonType repeat = new ButtonType("Repeat");
        ButtonType skip = new ButtonType("Skip");

        // Remove default ButtonTypes
        alert.getButtonTypes().clear();

        alert.getButtonTypes().addAll(repeat, skip);

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
            deleteChromeHistoryCb(false);
        } else if (option.get() == repeat) {
            deleteChromeHistoryCb(true);
        } else if (option.get() == skip) {
            deleteChromeHistoryCb(false);
        } else {
            deleteChromeHistoryCb(false);
        }
    }

    private void deleteChromeHistoryCb(boolean showDialogOnFail) {
        if (chromeHistoryCB.isSelected()) {
            File chromeHistory = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\History");
            try {
                FileUtils.forceDelete(chromeHistory);
            } catch (IOException e1) {
                if (showDialogOnFail) {
                    showConfirmation();
                }
                e1.printStackTrace();
            }
        }
    }

    private double rounder(double number){
        double v = number * 1000;
        long round = Math.round(v);
        double res = round / 1000.0;
        return res;
    }

    @FXML
    public void onClickMethodChrome(MouseEvent mouseEvent) {
        if (googleChromeCB.isSelected()){
        chromeCookiesCB.setDisable(false);
        chromeHistoryCB.setDisable(false);
        chromeCacheCB.setDisable(false);
        chromeCacheCB.setSelected(true);
        }else {
            chromeCookiesCB.setDisable(true);
            chromeHistoryCB.setDisable(true);
            chromeCacheCB.setDisable(true);
            chromeCacheCB.setSelected(false);
            chromeCookiesCB.setSelected(false);
            chromeHistoryCB.setSelected(false);
        }

    }

    @FXML
    public void onClickMethodSystem(MouseEvent mouseEvent) {
        if (systemCB.isSelected()){
            recycleBinCB.setDisable(false);
            recycleBinCB.setSelected(true);
            tempFilesCB.setDisable(false);
            tempFilesCB.setSelected(true);
        }else{
            recycleBinCB.setSelected(false);
            recycleBinCB.setDisable(true);
            tempFilesCB.setSelected(false);
            tempFilesCB.setDisable(true);
        }
    }
}

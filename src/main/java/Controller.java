import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
                    Long sizeOfCache = fileCollection.stream().map(a -> a.length()).reduce((a, b) -> a + b).orElse(0L);
                    double rounded = rounder(sizeOfCache / 1024.0);
                    rowData.add(new TableRow("r", "Google Chrome Cache", rounded));
                }


                tableView.setItems(rowData);
                cleanButton.setText("Clean");
                cleanButton.setStyle("-fx-background-color:red");
            } else {
                if(chromeCacheCB.isSelected()){
                    File chromeCache = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cache");
                    Collection<File> fileCollection = FileUtils.listFiles(chromeCache, null, true);
                    fileCollection.forEach(a->a.delete());
                    Long sizeOfCache = fileCollection.stream().map(a -> a.length()).reduce((a, b) -> a + b).orElse(0L);

                }

                if (chromeHistoryCB.isSelected()){
                    File chromeHistory = new File("C:\\Users\\"+ USER+"\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\History");
                    try {
                        FileUtils.forceDelete(chromeHistory);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
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
                cleanButton.setText("Analize");
                cleanButton.setStyle("-fx-background-color:yellow");
            }
        });
    }

    public double rounder(double number){
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

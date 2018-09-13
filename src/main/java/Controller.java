import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public class Controller {
    private ObservableList<TableRow> rowData = FXCollections.observableArrayList();
    private static final String USER = System.getProperty("user.name");
    private double totalAmount = 0.0;
    private int boxesChecked = 0;
    private DoubleBinding doubleBinding = new SimpleDoubleProperty(0.0d).add(0);
    CustomImage chromeImg =  new CustomImage(new ImageView(new Image(getClass().getResource("brick.png").toString())));
    @FXML
    public ProgressBar progressBar;
    @FXML
    public CheckBox clipboardCB;
    @FXML
    public CheckBox firefoxCB;
    @FXML
    public CheckBox firefoxCacheCB;
    @FXML
    public CheckBox firefoxHistoryCB;
    @FXML
    public CheckBox firefoxCookiesCB;
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
    public TableColumn<TableRow, CustomImage> picId;
    @FXML
    public TableColumn<TableRow, String> categoryId;
    @FXML
    public TableColumn<TableRow, String> sizeId;


    @FXML
    void initialize(){
        chromeCookiesCB.setDisable(true);
        chromeHistoryCB.setDisable(true);
        chromeCacheCB.setDisable(true);
        recycleBinCB.setDisable(true);
        tempFilesCB.setDisable(true);
        clipboardCB.setDisable(true);
        firefoxCacheCB.setDisable(true);
        firefoxCookiesCB.setDisable(true);
        firefoxHistoryCB.setDisable(true);
        progressBar.progressProperty().bind(doubleBinding);

        picId.setCellValueFactory(new PropertyValueFactory<TableRow, CustomImage>("pic"));
        categoryId.setCellValueFactory(new PropertyValueFactory<TableRow, String>("rowName"));
        sizeId.setCellValueFactory(new PropertyValueFactory<TableRow, String>("size"));


        cleanButton.setOnAction((e)->{
            if (cleanButton.getText().equals("Analize")) {
                boxesChecked=0;
                doubleBinding = new SimpleDoubleProperty(0.0d).add(0);
                progressBar.progressProperty().bind(doubleBinding);
                double totalFirefoxCache = 0;
                double totalFirefoxCookies = 0;
                double totalFirefoxHistory = 0;

                if (chromeCacheCB.isSelected()) {
                    boxesChecked+=1;
                    File chromeCache = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cache");
                    Collection<File> fileCollection = FileUtils.listFiles(chromeCache, null, true);
                    Long sizeOfChromeCache = fileCollection.stream().map(a -> a.length()).reduce((a, b) -> a + b).orElse(0L);
                    totalAmount += sizeOfChromeCache;
                    Size size = rounder(sizeOfChromeCache / 1024.0);
                    double rounded = size.getSize();
                    rowData.add(new TableRow(chromeImg, "Google Chrome Cache", rounded + size.getType()));
                    totalAmount += rounded;
                }

                if (chromeHistoryCB.isSelected()){
                    boxesChecked+=1;
                    File chromeHistory = new File("C:\\Users\\"+ USER +"\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\History");
                    long sizeOfChromeHistory = chromeHistory.length();
                    totalAmount += sizeOfChromeHistory;
                    Size size = rounder(sizeOfChromeHistory / 1024.0);
                    double roundedChromeHistory = size.getSize();
                    rowData.add(new TableRow(chromeImg, "Google Chrome History", roundedChromeHistory + size.getType()));
                    totalAmount += roundedChromeHistory;
                }

                if (chromeCookiesCB.isSelected()){
                    boxesChecked+=1;
                    File chromeCookies = new File("C:\\Users\\"+ USER+"\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cookies");
                    long sizeOfChromeCookies = chromeCookies.length();
                    totalAmount += sizeOfChromeCookies;
                    Size size = rounder(sizeOfChromeCookies / 1024.0);
                    double roundedChromeCookies = size.getSize();
                    rowData.add(new TableRow(chromeImg, "Google Chrome Cookies", roundedChromeCookies + size.getType()));
                    totalAmount += roundedChromeCookies;
                }

                if (recycleBinCB.isSelected()){
                    boxesChecked+=1;
                    File recycleBin = new File("C:\\$Recycle.Bin");
                    Collection<File> recycleBinCollection = FileUtils.listFiles(recycleBin, null, true);
                    Long sizeOfRecycleBin = recycleBinCollection.stream().map(a -> a.length()).reduce((a, b) -> a + b).orElse(0L);
                    totalAmount += sizeOfRecycleBin;
                    Size size = rounder(sizeOfRecycleBin / 1024.0);
                    double roundedRecycleBin = size.getSize();
                    rowData.add(new TableRow(chromeImg, "Recycle Bin", roundedRecycleBin + size.getType()));
                    totalAmount += roundedRecycleBin;
                }

                if (tempFilesCB.isSelected()){
                    boxesChecked+=1;
                    File tempFiles = new File("C:\\Users\\"+USER+"\\AppData\\Local\\Temp");
                    Collection<File> tempFilesCollection = FileUtils.listFiles(tempFiles, null, true);
                    Long sizeOfTempFiles = tempFilesCollection.stream().map(a -> a.length()).reduce((a, b) -> a + b).orElse(0L);
                    totalAmount += sizeOfTempFiles;
                    Size size = rounder(sizeOfTempFiles / 1024.0);
                    double roundedTempFiles = size.getSize();
                    rowData.add(new TableRow(chromeImg, "Temporary Files", roundedTempFiles + size.getType()));
                    totalAmount += roundedTempFiles;
                }

                if (clipboardCB.isSelected()){
                    boxesChecked+=1;
                    //TBD
                }

                if (firefoxCacheCB.isSelected()){
                    boxesChecked+=1;
                    Size size = new Size();
                    File file = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Mozilla\\Firefox\\Profiles");
                    String[] directories = file.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File current, String name) {
                            return new File(current, name).isDirectory();
                        }
                    });
                    for (int i = 0; i < directories.length; i++) {
                        String name = directories[i];
                        File firefoxCache = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Mozilla\\Firefox\\Profiles\\" + name + "\\cache2");
                        Collection<File> firefoxCacheCollection = FileUtils.listFiles(firefoxCache, null, true);
                        Long sizeOfFirefoxCache = firefoxCacheCollection.stream().map(a -> a.length()).reduce((a, b) -> a + b).orElse(0L);
                        totalAmount += sizeOfFirefoxCache;
                        size = rounder(sizeOfFirefoxCache / 1024.0);
                        double roundedFirefoxCache = size.getSize();
                        totalFirefoxCache += roundedFirefoxCache;
                    }
                    rowData.add(new TableRow(chromeImg, "Firefox Cache", totalFirefoxCache + size.getType()));
                    totalAmount += totalFirefoxCache;
                }

                if (firefoxCookiesCB.isSelected()){
                    boxesChecked+=1;
                    Size size = new Size();
                    File file = new File("C:\\Users\\" + USER + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles");
                    String[] directories = file.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File current, String name) {
                            return new File(current, name).isDirectory();
                        }
                    });
                    for (int i = 0; i < directories.length; i++) {
                        String name = directories[i];
                        File firefoxCookies = new File("C:\\Users\\" + USER + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\" + name + "\\cookies.sqlite");
                        long sizeOfFirefoxCookies = firefoxCookies.length();
                        totalAmount += sizeOfFirefoxCookies;
                        size = rounder(sizeOfFirefoxCookies / 1024.0);
                        double roundedFirefoxCookies = size.getSize();
                        totalFirefoxCookies += roundedFirefoxCookies;
                    }
                    rowData.add(new TableRow(chromeImg, "Firefox Cookies", totalFirefoxCookies + size.getType()));
                    totalAmount += totalFirefoxCookies;
                }

                if (firefoxHistoryCB.isSelected()){
                    boxesChecked+=1;
                    Size size = new Size();
                    File file = new File("C:\\Users\\" + USER + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles");
                    String[] directories = file.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File current, String name) {
                            return new File(current, name).isDirectory();
                        }
                    });
                    for (int i = 0; i < directories.length; i++) {
                        String name = directories[i];
                        File firefoxHistory = new File("C:\\Users\\" + USER + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\" + name + "\\places.sqlite");
                        long sizeOfFirefoxHistory = firefoxHistory.length();
                        totalAmount += sizeOfFirefoxHistory;
                        size = rounder(sizeOfFirefoxHistory / 1024.0);
                        double roundedFirefoxHistory = size.getSize();
                        totalFirefoxHistory += roundedFirefoxHistory;
                    }
                    rowData.add(new TableRow(chromeImg, "Firefox History", totalFirefoxHistory + size.getType()));
                    totalAmount += totalFirefoxHistory;
                }

                Size totalSize = rounder(totalAmount / 1024.0);
                System.out.println(boxesChecked);
                tableView.setItems(rowData);
                cleanButton.setText("Clean(" + totalSize.getSize() + totalSize.getType() + ")");
                cleanButton.setStyle("-fx-background-color:#bbff99");
            } else {
                new Thread(()-> {
                    cleaning();
                }).start();
                rowData.clear();
                cleanButton.setText("Analize");
                cleanButton.setStyle("-fx-background-color: #cce6ff");


            }
        });
    }

    private void cleaning() {
        if(chromeCacheCB.isSelected()){
            File chromeCache = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cache");
            Collection<File> fileCollection = FileUtils.listFiles(chromeCache, null, true);
            fileCollection.forEach(a->a.delete());
            updateProgressBar();
            threadPause();
        }

        if (chromeHistoryCB.isSelected()){
            deleteChromeHistoryCb(true);
            updateProgressBar();
            threadPause();
        }

        if (chromeCookiesCB.isSelected()){
            File chromeCookies = new File("C:\\Users\\"+ USER+"\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cookies");
            try {
                FileUtils.forceDelete(chromeCookies);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            updateProgressBar();
            threadPause();
        }

        if (recycleBinCB.isSelected()){
            File recycleBin = new File("C:\\$Recycle.Bin");
            Collection<File> recycleBinCollection = FileUtils.listFiles(recycleBin, null, true);
            recycleBinCollection.forEach(a->a.delete());
            updateProgressBar();
            threadPause();
        }

        if (tempFilesCB.isSelected()){
            File tempFiles = new File("C:\\Users\\"+USER+"\\AppData\\Local\\Temp");
            Collection<File> tempFilesCollection = FileUtils.listFiles(tempFiles, null, true);
            tempFilesCollection.forEach(a->a.delete());
            updateProgressBar();
            threadPause();
        }

        if (clipboardCB.isSelected()){
            try {
                Process p = Runtime.getRuntime().exec("cmd /c echo.|clip");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            updateProgressBar();
            threadPause();
        }

        if (firefoxCacheCB.isSelected()){
            File file = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Mozilla\\Firefox\\Profiles");
            String[] directories = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    return new File(current, name).isDirectory();
                }
            });
            for (int i = 0; i < directories.length; i++) {
                String name = directories[i];
                File firefoxCache = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Mozilla\\Firefox\\Profiles\\" + name + "\\cache2");
                Collection<File> firefoxCacheCollection = FileUtils.listFiles(firefoxCache, null, true);
                firefoxCacheCollection.forEach(a->a.delete());
            }
            updateProgressBar();
            threadPause();
        }

        if (firefoxCookiesCB.isSelected()){
           deleteFirefoxCookies(true);
           updateProgressBar();
           threadPause();
        }

        if (firefoxHistoryCB.isSelected()){
            deleteFirefoxHistory(true);
            updateProgressBar();
            threadPause();
        }


    }

    private void showChromeConfirmation() {
        Platform.runLater(() -> {
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
            alert.showAndWait().ifPresent(type -> {
                if (type == repeat) {
                    deleteChromeHistoryCb(true);
                } else if (type == skip) {
                    deleteChromeHistoryCb(false);
                } else {
                    deleteChromeHistoryCb(false);
                }
            });
        });
    }

    private void showFirefoxHistoryConfirmation() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Firefox History Problem");
            alert.setHeaderText("Close Firefox and all his processes and try again.");

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
                deleteFirefoxHistory(false);
            } else if (option.get() == repeat) {
                deleteFirefoxHistory(true);
            } else if (option.get() == skip) {
                deleteFirefoxHistory(false);
            } else {
                deleteFirefoxHistory(false);
            }
        });
    }

    private void showFirefoxCookiesConfirmation() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Firefox Cookies Problem");
            alert.setHeaderText("Close Firefox and all his processes and try again.");

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
                deleteFirefoxCookies(false);
            } else if (option.get() == repeat) {
                deleteFirefoxCookies(true);
            } else if (option.get() == skip) {
                deleteFirefoxCookies(false);
            } else {
                deleteFirefoxCookies(false);
            }
        });
    }

    private void deleteFirefoxCookies(boolean showDialogOnFail) {
        File file = new File("C:\\Users\\" + USER + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles");
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        for (int i = 0; i < directories.length; i++) {
            String name = directories[i];
            File firefoxCookies = new File("C:\\Users\\" + USER + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\" + name + "\\cookies.sqlite");
            try {
                FileUtils.forceDelete(firefoxCookies);
            } catch (IOException e1) {
                if (showDialogOnFail) {
                    showFirefoxCookiesConfirmation();
                }
                e1.printStackTrace();
            }
        }
    }

    private void deleteFirefoxHistory(boolean showDialogOnFail) {
            File file = new File("C:\\Users\\" + USER + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles");
            String[] directories = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    return new File(current, name).isDirectory();
                }
            });
            for (int i = 0; i < directories.length; i++) {
                String name = directories[i];
                File firefoxHistory = new File("C:\\Users\\" + USER + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\" + name + "\\places.sqlite");
                try {
                    FileUtils.forceDelete(firefoxHistory);
                } catch (IOException e1) {
                    if (showDialogOnFail) {
                        showFirefoxHistoryConfirmation();
                    }
                    e1.printStackTrace();
                }
            }
    }

    private void deleteChromeHistoryCb(boolean showDialogOnFail) {
            File chromeHistory = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\History");
            try {
                FileUtils.forceDelete(chromeHistory);
            } catch (IOException e1) {
                if (showDialogOnFail) {
                    showChromeConfirmation();
                }
                e1.printStackTrace();
            }
    }

    private Size rounder(double number){
        if (number >= 1024) {
            number /= 1024;
            double v = number * 100;
            long round = Math.round(v);
            double res = round / 100.0;
            Size size = new Size(res, "Mb");
            return size;
        } else if (number>=1048576) {
            number /= 1048576;
            double v = number * 100;
            long round = Math.round(v);
            double res = round / 100.0;
            Size size = new Size(res, "GB");
            return size;
        } else {
            double v = number * 1000;
            long round = Math.round(v);
            double res = round / 1000.0;
            Size size = new Size(res, "kb");
            return size;
        }
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
            clipboardCB.setDisable(false);
        }else{
            recycleBinCB.setSelected(false);
            recycleBinCB.setDisable(true);
            tempFilesCB.setSelected(false);
            tempFilesCB.setDisable(true);
            clipboardCB.setSelected(false);
            clipboardCB.setDisable(true);
        }
    }

    public void onClickMethodFirefox(MouseEvent mouseEvent) {
        if (firefoxCB.isSelected()){
            firefoxCacheCB.setDisable(false);
            firefoxCookiesCB.setDisable(false);
            firefoxHistoryCB.setDisable(false);
            firefoxCacheCB.setSelected(true);
        } else {
          firefoxCacheCB.setSelected(false);
          firefoxCookiesCB.setSelected(false);
          firefoxHistoryCB.setSelected(false);
          firefoxCacheCB.setDisable(true);
          firefoxCookiesCB.setDisable(true);
          firefoxHistoryCB.setDisable(true);
        }
    }
    public void updateProgressBar() {
        new Thread(()-> {
            doubleBinding = doubleBinding.add((double) 1 / boxesChecked);
            progressBar.progressProperty().unbind();
            progressBar.progressProperty().bind(doubleBinding);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        /*DoubleBinding add = doubleBinding.add((double) 1 / boxesChecked);
        System.out.println(add.get());
        System.out.println(doubleBinding.toString());
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(add);
        threadPause();*/

    }

    public void threadPause() {
        Thread th = Thread.currentThread( );
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

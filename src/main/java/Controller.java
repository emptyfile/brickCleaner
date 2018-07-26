import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

public class Controller {
    private static final String USER = System.getProperty("user.name");
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
    void initialize(){
        chromeCookiesCB.setDisable(true);
        chromeHistoryCB.setDisable(true);
        chromeCacheCB.setDisable(true);
        recycleBinCB.setDisable(true);
        tempFilesCB.setDisable(true);


        cleanButton.setOnAction((e)->{

            if(chromeCacheCB.isSelected()){
                File chromeCache = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cache");
                Collection<File> fileCollection = FileUtils.listFiles(chromeCache, null, true);
                fileCollection.forEach(a->a.delete());
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
        });
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

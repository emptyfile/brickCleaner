import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;

public class Controller {
    private static final String USER = System.getProperty("user.name");
    @FXML
    public CheckBox chromeCacheCB;
    @FXML
    public Button cleanButton;
    @FXML
    void initialize(){
        cleanButton.setOnAction((e)->{
            if(chromeCacheCB.isSelected()){
                File chromeCache = new File("C:\\Users\\" + USER + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cache");
                Collection<File> fileCollection = FileUtils.listFiles(chromeCache, null, true);
                fileCollection.forEach(a->a.delete());
            }
        });
    }
}

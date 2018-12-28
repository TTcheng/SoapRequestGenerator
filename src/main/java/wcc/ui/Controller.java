package wcc.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wcc.components.WsdlRequestGenerator;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox rootBox;

    @FXML
    private JFXTextArea showArea;

    @FXML
    private HBox inputBox;

    @FXML
    private JFXTextField url;

    @FXML
    private JFXButton reqBtn;
    @FXML
    private JFXButton resBtn;
    @FXML
    private JFXButton allBtn;
    @FXML
    private JFXButton clearBtn;

    private WsdlRequestGenerator generator;

    @FXML
    private void generate(ActionEvent event) {
        Object source = event.getSource();
        if (source instanceof JFXButton) {
            JFXButton btn = (JFXButton) source;
            switch (btn.getId()) {
                case "reqBtn":
                    generator.setCreateRequest(true);
                    generator.setCreateResponse(false);
                    break;
                case "resBtn":
                    generator.setCreateRequest(false);
                    generator.setCreateResponse(true);
                    break;
                case "allBtn":
                    generator.setCreateRequest(true);
                    generator.setCreateResponse(true);
                    break;
                default:
                    break;
            }
            String wsdlUrl = this.url.getText();
            if (wsdlUrl == null || "".equals(wsdlUrl.trim())) {
                alert("You must input an URL!");
                return;
            }
            try {
                String result = generator.generate(wsdlUrl);
                showArea.appendText(result);
            } catch (Exception e) {
                showArea.appendText("===========ERROR===========\n");
                logger.error("GenerateFailed", e);
            }
        }
    }

    private void alert(String msg) {
        Alert info = new Alert(Alert.AlertType.INFORMATION, msg);
        //info.setDialogPane(alertPan);
        info.show();
    }

    @FXML
    public void clear(ActionEvent event) {
        setShowInfo("");
    }

    public void setShowInfo(String info) {
        showArea.setText(info);
    }

    public void appendShowInfo(String info) {
        showArea.appendText(info + "\n");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showArea.setEditable(false);
        generator = new WsdlRequestGenerator();
    }
}

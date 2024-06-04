package hotel;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import admin.getData;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button admin_lgnbtn;

    @FXML
    private PasswordField admin_password;

    @FXML
    private ComboBox<String> admin_user;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;
    
    
    private void errorMessage(String message){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        
    }
    
    private void succesMessage(String message){
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("information Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void loginAccount() throws ClassNotFoundException, SQLException, Exception{
          if(admin_username.getText().isEmpty() || admin_password.getText().isEmpty() ){
                errorMessage("tolong isi dengan benar");
            }else{
                 String selectData = "SELECT * FROM admin WHERE username = ? and password = ?";
                       connect = Connect.connectDB();
          
        
        
        try{
          prepare = connect.prepareStatement(selectData);
          prepare.setString(1, admin_username.getText());
          prepare.setString(2, admin_password.getText());
          
          result = prepare.executeQuery();
          
          if(result.next()){
              
              getData.username = admin_username.getText();
              succesMessage("Login Succesfully!");
              
                  Parent root = FXMLLoader.load(getClass().getResource("/admin/adminMain.fxml"));
              
              Stage stage = new Stage();
              stage.setTitle("Admin Main Form");
              stage.setScene(new Scene(root));
              
              stage.show();
              
              admin_lgnbtn.getScene().getWindow().hide();
          }else{
              errorMessage("Incorrect Username/Password");
              
          }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    }
    
    public void switchForm() throws IOException {
        try {
            Parent root = null;
            if (admin_user.getSelectionModel().getSelectedItem() != null) {
                if (admin_user.getSelectionModel().getSelectedItem().equals("Costumers Portal")) {
                    root = FXMLLoader.load(getClass().getResource("CostumerPortal.fxml"));
                } else {
                    root = FXMLLoader.load(getClass().getResource("AdminPortal.fxml"));
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                admin_user.getScene().getWindow().hide();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectUser() {
        List<String> listU = new ArrayList<>();

        for (String data : users.users) {
            listU.add(data);
        }

        ObservableList<String> listData = FXCollections.observableArrayList(listU);
        admin_user.setItems(listData);
    }

    @FXML
    private TextField admin_username;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectUser();
        
        admin_user.setOnAction((event) -> {
            try {
                switchForm();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel;

import user.CostumerMainFormController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Boas
 */
public class CostumerPortalController implements Initializable {

    
    @FXML
    private Button costumer_lgnbtn;

    @FXML
    private PasswordField costumer_password;

 
    @FXML
    private ComboBox<String> costumer_select;
    
      
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;
    
    
    private void errorMessage(String message){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        
    }
    
    private void succesMessage(String message){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("information Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void loginAccount() throws ClassNotFoundException, SQLException, Exception{
          if(costumer_username.getText().isEmpty() || costumer_password.getText().isEmpty() ){
                errorMessage("tolong isi dengan benar");
            }else{
                 String selectData = "SELECT * FROM costumer WHERE username = ? and password = ?";
                       connect = Connect.connectDB();          
                
        try{
          prepare = connect.prepareStatement(selectData);
          prepare.setString(1, costumer_username.getText());
          prepare.setString(2, costumer_password.getText());
          
          result = prepare.executeQuery();
          
          if(result.next()){
               String loggedInUsername = costumer_username.getText();
              succesMessage("Login Succesfully!");
              
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/CustomerPage.fxml"));
    Parent root = loader.load();

                  
              Stage stage = new Stage();
              stage.setTitle("Admin Main Form");
              stage.setScene(new Scene(root));
              
              stage.show();
              
              costumer_lgnbtn.getScene().getWindow().hide();
              
    CostumerMainFormController costumerMainFormController = loader.getController();
    costumerMainFormController.setLoggedInUsername(loggedInUsername);

                  
          }else{
              errorMessage("Incorrect Username/Password");
              
          }
             
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    }
    

    public void switchForm() {
        try {
            Parent root = null;
            if (costumer_select.getSelectionModel().getSelectedItem() != null) {
                if (costumer_select.getSelectionModel().getSelectedItem().equals("Admin Portal")) {
                    root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                } else {
                    root = FXMLLoader.load(getClass().getResource("CostumerPortal.fxml"));
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                  costumer_select.getScene().getWindow().hide();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectuser() {
        List<String> listU = new ArrayList<>();

        for (String data : users.users) {
            listU.add(data);
        }

        ObservableList<String> listData = FXCollections.observableArrayList(listU);
        costumer_select.setItems(listData);
    }


    @FXML
    private TextField costumer_username;
    
    
        @FXML
    private Button register;
        
       

        
   
    @FXML
    void signup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterCostumer.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectuser();
            costumer_select.setOnAction((event) -> {
            switchForm();
        });
    }

    private void Resource(String fxmlDocumenrfxml) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}

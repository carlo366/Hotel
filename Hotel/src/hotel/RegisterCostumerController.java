package hotel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterCostumerController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    private Connection connect;
    private PreparedStatement prepare;
    private Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void registerButtonClicked() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            errorMessage("Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorMessage("Password and Confirm Password do not match.");
            return;
        }

        try {
            connect = Connect.connectDB();

            String insertData = "INSERT INTO costumer (username, password, address, phone) VALUES (?, ?, ?, ?)";
            prepare = connect.prepareStatement(insertData);
            prepare.setString(1, username);
            prepare.setString(2, password);
            prepare.setString(3, address);
            prepare.setString(4, phone);

            int result = prepare.executeUpdate();

            if (result > 0) {
                succesMessage("Registration successful!");

                // Redirect to CostumerPortal.fxml
                redirectToCostumerPortal();
            } else {
                errorMessage("Registration failed. Please try again.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            errorMessage("Error occurred during registration.");
        } finally {
            try {
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void errorMessage(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void succesMessage(String message) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
     
        @FXML
    private Button login;

        
   
    @FXML
    void signin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CostumerPortal.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToCostumerPortal() {
        try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CostumerPortal.fxml"));
            Parent root = loader.load();

         
            Stage stage = new Stage();
            stage.setTitle("Customer Portal");
            stage.setScene(new Scene(root));

      
            ((Stage) usernameField.getScene().getWindow()).close();

  
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

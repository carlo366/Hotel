/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import com.mysql.cj.xdevapi.Statement;
import hotel.Connect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.sql.SQLException;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
/**
 *
 * @author Boas
 */
public class adminMainForm implements Initializable {
        private Alert alert;
                  private Connection connect = null;
    private PreparedStatement prepare = null;
    private ObservableList<Tipe> data;
    
    private String query;
    private ResultSet resultset = null;
    private Tipe tipee;
    private Statement statement;
    
    @FXML
    private TextField addHotel_;

    @FXML
    private TextField addHotel_Harga;

    @FXML
    private Button addHotel_clear;

    @FXML
    private TableColumn<?, ?> addHotel_colfasilitas;

    @FXML
    private TableColumn<?, ?> addHotel_colnokamar;

    @FXML
    private Button addHotel_delete;

    @FXML
    private TextField addHotel_fasilitas;

    @FXML
    private TableColumn<?, ?> addHotel_harga;

    @FXML
    private ImageView addHotel_imageView;

    @FXML
    private Button addHotel_import;

    @FXML
    private Button add_tipebtn;
    
    @FXML
    private Button addHotel_insert;

    @FXML
    private TextField addHotel_search;

    @FXML
    private TableView<?> addHotel_tableview;

    @FXML
    private TableColumn<?, ?> addHotel_tipe;

    @FXML
    private Button addHotel_update;

    @FXML
    private Button add_hotel_btn;

    @FXML
    private AnchorPane add_hotel_form;

    @FXML
    private Button close;

    @FXML
    private Button dasboard_btn;

    @FXML
    private AnchorPane dasboard_form;
    
    
    @FXML
    private AnchorPane addtipe;

    
    @FXML
    private Button minimize;

    @FXML
    private Label tiotal_hotel;

    @FXML
    private ComboBox<?> tipe;

    @FXML
    private Label username;
    
        @FXML
    private AnchorPane topForm;

     
    @FXML
    private Button addtipe_delete1;

    @FXML
    private Button addtipe_insert1;
    
     @FXML
    private Button addtipe_clear1;


    @FXML
    private TextField addtipe_search1;
    
    
    @FXML
    private TextField addtipe_1;

    @FXML
    private TableView<Tipe> addtipe_tableview1;
    
    @FXML
    private Button logoutButton;
         
    
    @FXML
    private TableColumn<Tipe , String> addtipe_coltipekamar;
    
    @FXML
    private Button addtipe_update1;
    
    
    private void setCell(){
        addtipe_coltipekamar.setCellValueFactory(new PropertyValueFactory<>("tipe_kamar"));
    }
  private void loadDataFromDatabase() throws SQLException, Exception {
    try {
        connect = Connect.connectDB();
        PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM tipe_kamar");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
//            data.add(new Tipe(resultSet.getString("nama_tipe")));
        }

        addtipe_tableview1.setItems(data);
    } finally {
        // Tutup statement dan resultset di sini jika diperlukan
    }
    
    
    
}

    
      @FXML
    private void addTipeButtonClicked() throws Exception {
        String tipeName = addtipe_1.getText();

        if (tipeName.isEmpty()) {
            errorMessage("Please enter a tipe name.");
            return;
        }

        try {
            connect = Connect.connectDB();

            String insertData = "INSERT INTO tipe (nama_tipe) VALUES (?)";
            prepare = connect.prepareStatement(insertData);
            prepare.setString(1, tipeName);

            int result = prepare.executeUpdate();

            if (result > 0) {
                succesMessage("Tipe added successfully!");
                // You can add additional handling if needed, such as clearing the text field.
            } else {
                errorMessage("Failed to add tipe. Please try again.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            errorMessage("Error occurred while adding tipe.");
        } finally {
            // ... (your existing finally block)
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
    public void close(){
        System.exit(0);
        
    }
    public void minimize(){
        Stage stage = (Stage)topForm.getScene().getWindow();
        stage.setIconified(true);
    } 
    
    public void displayUsername(){
        username.setText(getData.username);
    }
    
     public void swichForm(ActionEvent event){
            if(event.getSource() == dasboard_btn){
                dasboard_form.setVisible(true);
                add_hotel_form.setVisible(false);
                addtipe.setVisible(false);
            }else if(event.getSource() == add_hotel_btn){
                dasboard_form.setVisible(false);
                add_hotel_form.setVisible(true);
                addtipe.setVisible(false);
            }else if(event.getSource() == add_tipebtn){
                dasboard_form.setVisible(false);
                add_hotel_form.setVisible(false);
                addtipe.setVisible(true);
        }
     }
     
     
     

     
     
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername(); 
        data = FXCollections.observableArrayList();
        setCell();
            try {
                loadDataFromDatabase();
            }  catch (Exception ex) {
                Logger.getLogger(adminMainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    
            
            
            }
}
    

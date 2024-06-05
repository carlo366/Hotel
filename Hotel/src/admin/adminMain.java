    package admin;

import com.mysql.cj.xdevapi.Statement;
import hotel.Connect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.sql.SQLException;
import java.util.Optional;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import admin.Tipe;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class adminMain implements Initializable {
            private Alert alert;
                  private Connection connect = null;
   private PreparedStatement preparedStatement;
   private ObservableList<Tipe> data;    
   private String query;
   private ResultSet resultset = null;
   private Tipe tipee;
   private Statement statement;

  @FXML
  private AnchorPane topForm;
  
      @FXML
    private TableColumn<Kamar, String> addHotel_Bungbed;

    @FXML
    private Button updatekamar;
  @FXML
  private TableColumn<Kamar, String> addHotel_colfasilitas;
  
  @FXML
private ComboBox<String> selecttipe;

      @FXML
      private Button deletekamar;
      
      @FXML
      private Button clearkamar;
      
     @FXML
     private TextField addnokamar;
     
     @FXML 
     private TextField addkamarfasilitas;
     
     @FXML
private TextField addkamarhrg;
     @FXML
     private Button kamarimport;
  
  @FXML
  private TableColumn<Kamar, String> addHotel_colnokamar;

  @FXML
  private TableColumn<Kamar, String> addHotel_harga;

    @FXML
    private AnchorPane addbungbedview;

    @FXML
    private Button addbungbeddd;
  
  @FXML
  private ImageView addHotel_imageView;

    @FXML
    private Button importimg;

    @FXML
    private TextField addHotel_search;
    

          @FXML
    private Button clearbungbed;

     @FXML 
     private TextField addbungbed;
         
    @FXML
    private TableColumn<Bungbed, String> colnamabungbed;

        @FXML
    private Button deletebungbed;

    @FXML
    private Button insertbungbed;

   @FXML
private TableView<Bungbed> tabelbungbed; // Sesuaikan dengan nama tabel Anda
private ObservableList<Bungbed> bungbedData = FXCollections.observableArrayList();

          @FXML
    private Button updatebungbed;



    @FXML
    private TableView<Kamar> addHotel_tableview;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private TableColumn<Kamar, String> addHotel_tipe;
    
        @FXML
    private TableColumn<Kamar, ImageView> addHotel_gambar;
        
            @FXML
    private ComboBox<String> bungbedcombo;

    @FXML
    private AnchorPane addtipe;

    @FXML
    private TextField addtipe_1;

    @FXML
    private Button addtipe_clear1;
    
    @FXML
    private TextField username;

    @FXML
    private TableColumn<Kamar, String> addtipe_coltipekamar;

    @FXML
    private Button addtipe_delete1;
    
    
    @FXML
private Button addtipeclear1;
    
    @FXML
    private Button addtipe_insert1;

    @FXML
    private TextField addtipe_search1;
    
    @FXML
private Button addtipedelete1;
    
    
private ObservableList<Kamar> kamarData = FXCollections.observableArrayList();
    
    @FXML
    private TableView<?> addtipe_tableview1;

      @FXML
    private TableView<Tipe> addHotel_tableview1;
      
       @FXML
    private TableColumn<Tipe,String> addHotel_colfasilitas1;
    
    @FXML
    private Button addtipe_update1;
    
        @FXML
    private Button tipeupdate1;
        

    @FXML
    private Button dasboard_btn;

    @FXML
    private AnchorPane dasboard_form;

    @FXML
    private Button add_hotel_btn;

    @FXML
    private AnchorPane add_hotel_form;

    @FXML
    private Button add_tipebtn;
    
        @FXML
    private Button inserttipe_btn;
                
    @FXML
private void addTipeButtonClicked() throws Exception {
    String tipeName = addtipe_1.getText();

    if (tipeName.isEmpty()) {
        errorMessage("Please enter a tipe name.");
        return;
    }

    try {
        connect = Connect.connectDB();

        // Check if the tipe with the given name already exists
        String checkQuery = "SELECT COUNT(*) FROM tipe WHERE nama_tipe = ?";
        preparedStatement = connect.prepareStatement(checkQuery);
        preparedStatement.setString(1, tipeName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        int count = resultSet.getInt(1);

        if (count > 0) {
            errorMessage("Tipe with the given name already exists.");
            return;
        }

        // If not, proceed with the insert operation
        String insertData = "INSERT INTO tipe (nama_tipe) VALUES (?)";
        preparedStatement = connect.prepareStatement(insertData);
        preparedStatement.setString(1, tipeName);

        int result = preparedStatement.executeUpdate();

        if (result > 0) {
            succesMessage("Tipe added successfully!");
              addHotel_tableview1.getItems().clear();
           fetchDataFromDatabase();
        } else {
            errorMessage("Failed to add tipe. Please try again.");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while adding tipe.");
    } finally {
       selecttipe.getItems().clear();
       populateComboBox();
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
    
//    public void displayUsername(){
//        username.setText(getData.username);
//    }

    @FXML
    void swichForm(ActionEvent event) {
        if (event.getSource() == dasboard_btn) {
            dasboard_form.setVisible(true);
            add_hotel_form.setVisible(false);
            addtipe.setVisible(false);
             addbungbedview.setVisible(false);
        } else if (event.getSource() == add_hotel_btn) {
            dasboard_form.setVisible(false);
            add_hotel_form.setVisible(true);
            addtipe.setVisible(false);
             addbungbedview.setVisible(false);
        } else if (event.getSource() == add_tipebtn) {
            dasboard_form.setVisible(false);
            add_hotel_form.setVisible(false);
            addtipe.setVisible(true);
             addbungbedview.setVisible(false);
        }else if (event.getSource() == addbungbeddd) {
            dasboard_form.setVisible(false);
            add_hotel_form.setVisible(false);
            addtipe.setVisible(false);
             addbungbedview.setVisible(true);
        }
    }
    
    
@FXML
private void logout() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Konfirmasi Logout");
    alert.setHeaderText("Anda yakin ingin logout?");
    alert.setContentText("Pilih OK untuk logout, atau Cancel untuk batal.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
            System.out.println("Loading adminMain.fxml...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/FXMLDocument.fxml"));
            Parent view = loader.load();
            System.out.println("FXMLDocumentController.fxml loaded successfully");

            Scene scene = new Scene(view);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Close the current window (admin window)
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


  private void fetchDataFromDatabase() throws Exception {
            try {
                 connect = Connect.connectDB();
                String query = "SELECT * FROM tipe";
                preparedStatement = connect.prepareStatement(query);
                resultset = preparedStatement.executeQuery();

                while (resultset.next()) {
                    String nama_tipe = resultset.getString("nama_tipe");


                    // Add each row as an Item object to the TableView
                  addHotel_tableview1.getItems().add(new Tipe(nama_tipe));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
  
@FXML
private void deleteTipeButtonClicked() throws Exception {
    Tipe selectedTipe = addHotel_tableview1.getSelectionModel().getSelectedItem();

    if (selectedTipe == null) {
        errorMessage("Please select a tipe to delete.");
        return;
    }

    try {
        connect = Connect.connectDB();

        String deleteData = "DELETE FROM tipe WHERE nama_tipe = ?";
        preparedStatement = connect.prepareStatement(deleteData);
        preparedStatement.setString(1, selectedTipe.getNamaTipe());

        int result = preparedStatement.executeUpdate();

        if (result > 0) {
            succesMessage("Tipe deleted successfully!");
            addHotel_tableview1.getItems().clear();
            fetchDataFromDatabase(); // Refresh the table after delete
        } else {
            errorMessage("Failed to delete tipe. Please try again.");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while deleting tipe.");
    } finally {
    }
}

@FXML
private void updateTipeButtonClicked() throws Exception {
    Tipe selectedTipe = addHotel_tableview1.getSelectionModel().getSelectedItem();
    String newTipeName = addtipe_1.getText();

    if (selectedTipe == null) {
        errorMessage("Please select a tipe to update.");
        return;
    }

    if (newTipeName.isEmpty()) {
        errorMessage("Please enter a new tipe name.");
        return;
    }

    try {
        connect = Connect.connectDB();

        // Check if the new tipe name already exists (excluding the selected tipe)
        String checkQuery = "SELECT COUNT(*) FROM tipe WHERE nama_tipe = ? AND nama_tipe != ?";
        preparedStatement = connect.prepareStatement(checkQuery);
        preparedStatement.setString(1, newTipeName);
        preparedStatement.setString(2, selectedTipe.getNamaTipe());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        int count = resultSet.getInt(1);

        if (count > 0) {
            errorMessage("Tipe with the new name already exists.");
            return;
        }

        // If not, proceed with the update operation
        String updateData = "UPDATE tipe SET nama_tipe = ? WHERE nama_tipe = ?";
        preparedStatement = connect.prepareStatement(updateData);
        preparedStatement.setString(1, newTipeName);
        preparedStatement.setString(2, selectedTipe.getNamaTipe());

        int result = preparedStatement.executeUpdate();

        if (result > 0) {
            succesMessage("Tipe updated successfully!");
            addHotel_tableview1.getItems().clear();
            fetchDataFromDatabase(); // Refresh the table after update
        } else {
            errorMessage("Failed to update tipe. Please try again.");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while updating tipe.");
    } finally {
      
    }
}

 

  @FXML
private void clearTipeButtonClicked() {
    addtipe_1.clear(); // Clear the text field

    // Clear the selection in the table
    addHotel_tableview1.getSelectionModel().clearSelection();
}
  

private void populateComboBox() throws Exception {
    try {
        connect = Connect.connectDB();
        String query = "SELECT nama_tipe FROM tipe";
        preparedStatement = connect.prepareStatement(query);
        resultset = preparedStatement.executeQuery();

        ObservableList<String> tipeList = FXCollections.observableArrayList();

        while (resultset.next()) {
            String namaTipe = resultset.getString("nama_tipe");
            tipeList.add(namaTipe);
        }

        selecttipe.setItems(tipeList);
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while populating ComboBox.");
    }
}


private void populateBungbedComboBox() throws Exception {
    try {
        connect = Connect.connectDB();
        String query = "SELECT nama_bungbed FROM bungbed";
        preparedStatement = connect.prepareStatement(query);
        resultset = preparedStatement.executeQuery();

        ObservableList<String> bungbedList = FXCollections.observableArrayList();

        while (resultset.next()) {
            String namaBungbed = resultset.getString("nama_bungbed");
            bungbedList.add(namaBungbed);
        }

        bungbedcombo.setItems(bungbedList);
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while populating ComboBox.");
    }
}

@Override
public void initialize(URL location, ResourceBundle resources) {
    try {
        fetchDataFromDatabase();
        addEventHandlers();
        fetchDataBungbedFromDatabase();
        addEventHandlersForKamar();
        fetchDataKamarFromDatabase();
        populateBungbedComboBox();  // Add this line to populate the ComboBox
        addEventHandlersbungbed();
        populateComboBox();
    } catch (Exception ex) {
        Logger.getLogger(adminMain.class.getName()).log(Level.SEVERE, null, ex);
    }

    addHotel_colfasilitas1.setCellValueFactory(new PropertyValueFactory<>("namaTipe"));
    addHotel_colnokamar.setCellValueFactory(new PropertyValueFactory<>("nokamar"));
    addHotel_colfasilitas.setCellValueFactory(new PropertyValueFactory<>("fasilitas"));
    addHotel_tipe.setCellValueFactory(new PropertyValueFactory<>("tipe"));
    addHotel_harga.setCellValueFactory(new PropertyValueFactory<>("harga"));
    colnamabungbed.setCellValueFactory(new PropertyValueFactory<>("namaBungbed"));
    addHotel_Bungbed.setCellValueFactory(new PropertyValueFactory<>("bungbed"));

    addHotel_tableview.setItems(kamarData);
}

private void addEventHandlers() {
    addHotel_tableview1.setOnMouseClicked(event -> {
        handleItemSelection();
    });
}

@FXML
private void insertKamarButtonClicked() throws Exception {
    String nokamar = addnokamar.getText();
    String fasilitas = addkamarfasilitas.getText();
    String tipe = selecttipe.getValue(); // Assuming String is the data type of tipe
    String harga = addkamarhrg.getText();
    String selectedBungbed = bungbedcombo.getValue(); // Assuming String is the data type of bungbed

    if (nokamar.isEmpty() || fasilitas.isEmpty() || tipe == null || harga.isEmpty() || selectedBungbed == null) {
        errorMessage("Please fill in all the fields.");
        return;
    }

    // Use try-with-resources to automatically close the connection and statement
    try (Connection connect = Connect.connectDB()) {
        // Check if nokamar already exists in the database
        if (isNokamarExists(connect, nokamar)) {
            errorMessage("Data with the same nokamar already exists. Please use a different nokamar.");
            return;
        }

        // Insert data into kamar
        try (PreparedStatement preparedStatement = connect.prepareStatement(
                "INSERT INTO kamar (nokamar, fasilitas, tipe, harga, nama_bungbed) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, nokamar);
            preparedStatement.setString(2, fasilitas);
            preparedStatement.setString(3, tipe);
            preparedStatement.setString(4, harga);
            preparedStatement.setString(5, selectedBungbed);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                succesMessage("Data inserted into kamar successfully!");
            } else {
                errorMessage("Failed to insert data into kamar. Please try again.");
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while inserting data into kamar.");
    } finally {
        // Clear elements after addition
        addHotel_tableview.getItems().clear();
        fetchDataKamarFromDatabase();
    }
}

// Helper method to check if nokamar already exists in the database
private boolean isNokamarExists(Connection connect, String nokamar) throws SQLException {
    String query = "SELECT COUNT(*) FROM kamar WHERE nokamar = ?";
    try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
        preparedStatement.setString(1, nokamar);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }
    return false;
}

private void fetchDataKamarFromDatabase() throws Exception {
    try {
        connect = Connect.connectDB();
        String query = "SELECT * FROM kamar";
        preparedStatement = connect.prepareStatement(query);
        resultset = preparedStatement.executeQuery();

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        while (resultset.next()) {
            String nokamar = resultset.getString("nokamar");
            String fasilitas = resultset.getString("fasilitas");
            String tipe = resultset.getString("tipe");
            String harga = currencyFormat.format(resultset.getDouble("harga"));
            String bungbed = resultset.getString("nama_bungbed");

            kamarData.add(new Kamar(nokamar, fasilitas, tipe, harga, bungbed));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
private void addEventHandlersForKamar() {
    addHotel_tableview.setOnMouseClicked(event -> {
        Kamar selectedKamar = addHotel_tableview.getSelectionModel().getSelectedItem();
        if (selectedKamar != null) {
            // Set nilai field sesuai dengan data yang dipilih dari tabel
            addnokamar.setText(selectedKamar.getNokamar());
            addkamarfasilitas.setText(selectedKamar.getFasilitas());
            selecttipe.setValue(selectedKamar.getTipe());

            // Hilangkan karakter non-angka dari harga
            String cleanedHarga = selectedKamar.getHarga().replaceAll("[^\\d]", "");
            addkamarhrg.setText(cleanedHarga);

            bungbedcombo.setValue(selectedKamar.getBungbed());
        }
    });
}

@FXML
private void insertBungbedButtonClicked() throws Exception {
    String namaBungbed = addbungbed.getText();

    if (namaBungbed.isEmpty()) {
        errorMessage("Please enter a bungbed name.");
        return;
    }

    try {
        connect = Connect.connectDB();


        String checkQuery = "SELECT COUNT(*) FROM bungbed WHERE nama_bungbed = ?";
        preparedStatement = connect.prepareStatement(checkQuery);
        preparedStatement.setString(1, namaBungbed);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        int count = resultSet.getInt(1);

        if (count > 0) {
            errorMessage("Bungbed with the given name already exists.");
            return;
        }
        String insertData = "INSERT INTO bungbed (nama_bungbed) VALUES (?)";
        preparedStatement = connect.prepareStatement(insertData);
        preparedStatement.setString(1, namaBungbed);

        int result = preparedStatement.executeUpdate();

        if (result > 0) {
            succesMessage("Bungbed added successfully!");
            tabelbungbed.getItems().clear();
            fetchDataBungbedFromDatabase(); 
        } else {
            errorMessage("Failed to add bungbed. Please try again.");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while adding bungbed.");
    } finally {
        bungbedcombo.getItems().clear();
        populateBungbedComboBox();
       
    }
}



private void fetchDataBungbedFromDatabase() throws Exception {
    try {
        connect = Connect.connectDB();
        String query = "SELECT * FROM bungbed";
        preparedStatement = connect.prepareStatement(query);
        resultset = preparedStatement.executeQuery();

        // Bersihkan bungbedData sebelum menambahkan item baru
        bungbedData.clear();

        while (resultset.next()) {
            String namaBungbed = resultset.getString("nama_bungbed");

            // Add each row as a Bungbed object to the TableView
            bungbedData.add(new Bungbed(namaBungbed));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Tetapkan data ke TableView setelah selesai mengumpulkan data
        tabelbungbed.setItems(bungbedData);
    }
}

private void handleItemSelection() {
    Tipe selectedBarang = addHotel_tableview1.getSelectionModel().getSelectedItem();
    if (selectedBarang != null) {
        addtipe_1.setText(selectedBarang.getNamaTipe());
    }    
  }

private void handleItembungbedSelection() {
    Bungbed selectedBungbed = tabelbungbed.getSelectionModel().getSelectedItem();
    if (selectedBungbed != null) {
        addbungbed.setText(selectedBungbed.getNamaBungbed());
    }
}

private void addEventHandlersbungbed() {
    tabelbungbed.setOnMouseClicked(event -> {
        handleItembungbedSelection();
    });
}




@FXML
private void deleteBungbedButtonClicked() throws Exception {
    Bungbed selectedBungbed = tabelbungbed.getSelectionModel().getSelectedItem();

    if (selectedBungbed == null) {
        errorMessage("Please select a bungbed to delete.");
        return;
    }

    try {
        connect = Connect.connectDB();

        String deleteData = "DELETE FROM bungbed WHERE nama_bungbed = ?";
        preparedStatement = connect.prepareStatement(deleteData);
        preparedStatement.setString(1, selectedBungbed.getNamaBungbed());

        int result = preparedStatement.executeUpdate();

        if (result > 0) {
            succesMessage("Bungbed deleted successfully!");
            tabelbungbed.getItems().clear();
            fetchDataBungbedFromDatabase(); // Refresh the table after delete
        } else {
            errorMessage("Failed to delete bungbed. Please try again.");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while deleting bungbed.");
    } finally {
        // Close resources if needed
    }
}
@FXML
private void updateBungbedButtonClicked() throws Exception {
    Bungbed selectedBungbed = tabelbungbed.getSelectionModel().getSelectedItem();
    String newBungbedName = addbungbed.getText();

    if (selectedBungbed == null) {
        errorMessage("Please select a bungbed to update.");
        return;
    }

    if (newBungbedName.isEmpty()) {
        errorMessage("Please enter a new bungbed name.");
        return;
    }

    try {
        connect = Connect.connectDB();

        // Debugging: Print the values for debugging
        System.out.println("New Bungbed Name: " + newBungbedName);
        System.out.println("Selected Bungbed Name: " + selectedBungbed.getNamaBungbed());

        // Check if the new bungbed name already exists (excluding the selected bungbed)
        String checkQuery = "SELECT COUNT(*) FROM bungbed WHERE nama_bungbed = ? AND nama_bungbed != ?";
        try (PreparedStatement checkStatement = connect.prepareStatement(checkQuery)) {
            checkStatement.setString(1, newBungbedName);
            checkStatement.setString(2, selectedBungbed.getNamaBungbed());
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();

                int count = resultSet.getInt(1);

                if (count > 0) {
                    errorMessage("Bungbed with the new name already exists.");
                    return;
                }
            }
        }

        // If not, proceed with the update operation
        String updateData = "UPDATE bungbed SET nama_bungbed = ? WHERE nama_bungbed = ?";
        try (PreparedStatement updateStatement = connect.prepareStatement(updateData)) {
            updateStatement.setString(1, newBungbedName);
            updateStatement.setString(2, selectedBungbed.getNamaBungbed());

            int result = updateStatement.executeUpdate();

            if (result > 0) {
                succesMessage("Bungbed updated successfully!");
                tabelbungbed.getItems().clear();
                fetchDataBungbedFromDatabase(); // Refresh the table after update
            } else {
                errorMessage("Failed to update bungbed. Please try again.");
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while updating bungbed.");
    } finally {
        // Close resources if needed
    }
}


@FXML
private void updateKamarButtonClicked() throws Exception {
    // Dapatkan nilai dari elemen antarmuka pengguna
    String nokamar = addnokamar.getText();
    String fasilitas = addkamarfasilitas.getText();
    String tipe = selecttipe.getValue(); // Assuming String is the data type of tipe
    String harga = addkamarhrg.getText();
    String selectedBungbed = bungbedcombo.getValue(); // Assuming String is the data type of bungbed

    // Pastikan tidak ada nilai yang kosong
    if (nokamar.isEmpty() || fasilitas.isEmpty() || tipe == null || harga.isEmpty() || selectedBungbed == null) {
        errorMessage("Please fill in all the fields.");
        return;
    }

    // Gunakan try-with-resources untuk secara otomatis menutup koneksi dan statement
    try (Connection connect = Connect.connectDB()) {
        // Periksa apakah nokamar sudah ada sebelumnya
        if (!isuNokamarExists(connect, nokamar)) {
            errorMessage("Data with the given nokamar does not exist. Update operation requires an existing nokamar.");
            return;
        }

        // Update data kamar
        try (PreparedStatement preparedStatement = connect.prepareStatement(
                "UPDATE kamar SET fasilitas = ?, tipe = ?, harga = ?, nama_bungbed = ? WHERE nokamar = ?")) {

            preparedStatement.setString(1, fasilitas);
            preparedStatement.setString(2, tipe);
            preparedStatement.setString(3, harga);
            preparedStatement.setString(4, selectedBungbed);
            preparedStatement.setString(5, nokamar);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                succesMessage("Data updated successfully!");
            } else {
                errorMessage("Failed to update data. Please try again.");
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while updating data.");
    } finally {
        addHotel_tableview.getItems().clear();
        fetchDataKamarFromDatabase();
    }
}

// Metode bantu untuk memeriksa apakah nokamar sudah ada dalam database
private boolean isuNokamarExists(Connection connect, String nokamar) throws SQLException {
    String query = "SELECT COUNT(*) FROM kamar WHERE nokamar = ?";
    try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
        preparedStatement.setString(1, nokamar);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }
    return false;
}

@FXML
private void deleteKamarButtonClicked() throws Exception {
    // Dapatkan item kamar yang dipilih dari tabel
    Kamar selectedKamar = addHotel_tableview.getSelectionModel().getSelectedItem();

    // Pastikan kamar yang dipilih tidak null
    if (selectedKamar == null) {
        errorMessage("Please select a kamar to delete.");
        return;
    }

    try {
        // Buat koneksi ke database
        connect = Connect.connectDB();

        // Query untuk menghapus kamar berdasarkan nomor kamar
        String deleteQuery = "DELETE FROM kamar WHERE nokamar = ?";
        try (PreparedStatement preparedStatement = connect.prepareStatement(deleteQuery)) {
            // Set parameter dengan nomor kamar yang dipilih
            preparedStatement.setString(1, selectedKamar.getNokamar());

            // Eksekusi query delete
            int result = preparedStatement.executeUpdate();

            // Cek apakah kamar berhasil dihapus
            if (result > 0) {
                succesMessage("Kamar deleted successfully!");
                addHotel_tableview.getItems().remove(selectedKamar); // Hapus item dari tabel UI
            } else {
                errorMessage("Failed to delete kamar. Please try again.");
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while deleting kamar.");
    } finally {
        // Tutup koneksi jika diperlukan
    }
}

@FXML
private void clearKamarButtonClicked() {
    // Bersihkan isian pada elemen-elemen input
    addnokamar.clear();
    addkamarfasilitas.clear();
    selecttipe.getSelectionModel().clearSelection();
    addkamarhrg.clear();
    bungbedcombo.getSelectionModel().clearSelection();
}
@FXML
private void clearBungbedButtonClicked() {
    addbungbed.clear(); // Clear the text field

    // Clear the selection in the table
    tabelbungbed.getSelectionModel().clearSelection();
}
 }

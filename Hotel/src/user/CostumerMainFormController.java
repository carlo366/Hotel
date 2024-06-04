package user;

import hotel.Connect;
import java.io.IOException;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import user.Kamar;

public class CostumerMainFormController implements Initializable {
    
    

    @FXML
    private TableView<Kamar> addHotel_tableview;

    @FXML
    private TableColumn<Kamar, String> addHotel_colnokamar;
      @FXML
    private TableColumn<Pesan, String> statuscol;

        @FXML
    private TableColumn<Pesan, String> addHotel_colnokamar2;
    
     @FXML 
     private Button logoutButton;
    @FXML
    private TableView<Pesan> tablepesan;

    @FXML
    private AnchorPane addcheckin;
    
        @FXML
    private Button btncheckin;

         @FXML
    private Button btncheckout;
         
         @FXML
    private AnchorPane addcheckout;
    
 
    @FXML
    private TableColumn<Kamar, String> addHotel_colfasilitas;

    @FXML
    private TableColumn<Kamar, String> addHotel_tipe;

    @FXML
    private TableColumn<Kamar, String> addHotel_harga;

    @FXML
    private TableColumn<Kamar, String> addHotel_Bungbed;
    
    
        @FXML
    private TableColumn<Pesan, String> addHotel_colfasilitas2;

    @FXML
    private TableColumn<Pesan, String> addHotel_tipe2;

    @FXML
    private TableColumn<Pesan, String> addHotel_harga2;

    @FXML
    private TableColumn<Pesan, String> addHotel_Bungbed2;


    @FXML
    private Button add_hotel_btn;

    @FXML
    private AnchorPane add_hotel_form;

    @FXML
    private Label nameusername;

    @FXML
    private Label fasilitas;

    @FXML
    private Label harga;

    @FXML
    private Label bungbed;

    @FXML
    private Label nokamar;

    @FXML
    private Label tipe;

    @FXML
    private Button pesan;

    @FXML
    private DatePicker tanggalpesan;

    @FXML
    private AnchorPane navFormPane;

    private String loggedInUsername;

    private ObservableList<Kamar> kamarData = FXCollections.observableArrayList();
    private ObservableList<Pesan> PesanData = FXCollections.observableArrayList();
    
        public void setLoggedInUsername(String loggedInUsername) {
        // Assuming you have a Label in the FXML file with the fx:id="usernameLabel"
        nameusername.setText(loggedInUsername);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fetchDataKamarFromDatabase();
            fetchDatapesanFromDatabase();
            setupDatePickers();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setupTableColumns();
setupTablepesanColumns();
        addHotel_tableview.setItems(kamarData);
        tablepesan.setItems(PesanData);

        addHotel_tableview.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                handleRowClick();
            }
        });
    }

    private void fetchDatapesanFromDatabase() throws Exception {
    try (Connection connect = Connect.connectDB();
         PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM pesan");
         ResultSet resultSet = preparedStatement.executeQuery()) {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        while (resultSet.next()) {
            String nokamar = resultSet.getString("nokamar");
            String fasilitas = resultSet.getString("fasilitas");
            String tipe = resultSet.getString("tipe");
            String bungbed = resultSet.getString("nama_bungbed");
            String hargaString = resultSet.getString("harga");

            // Hapus simbol mata uang dan pemisah grup
            String cleanHargaString = hargaString.replaceAll("[^\\d.]", "");

            String status = resultSet.getString("status");

            PesanData.add(new Pesan(nokamar, fasilitas, tipe, bungbed, cleanHargaString, status));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    private void setupDatePickers() {
        setupDatePicker(tanggalpesan);
    }

    private void setupDatePicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
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
            System.out.println("Loading CostumerMain.fxml...");
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

    
     @FXML
    void swichForm(ActionEvent event) {
        if (event.getSource() == btncheckin) {
            addcheckin.setVisible(true);
            addcheckout.setVisible(false);

        } else if (event.getSource() == btncheckout) {
           addcheckin.setVisible(false);
            addcheckout.setVisible(true);
    }
    }
@FXML
private void handleCheckoutButton(ActionEvent event) throws Exception {
    // Dapatkan objek Pesan yang dipilih dari tabel pesan
    Pesan selectedPesan = tablepesan.getSelectionModel().getSelectedItem();

    // Periksa apakah ada Pesan yang dipilih
    if (selectedPesan != null) {
        // Setel status Pesan menjadi "checkout"
        selectedPesan.setStatus("checkout");

        // Perbarui status checkout di database
        updateStatusToCheckout(selectedPesan.getNokamar());

        // Perbarui tampilan tabel pesan
        tablepesan.refresh();
    }
}



private void updateStatusToCheckout(String nokamar) throws Exception {
    // Gunakan try-with-resources untuk otomatis menutup koneksi dan pernyataan
    try (Connection connect = Connect.connectDB()) {
        // Update status menjadi "checkout" di tabel pesan
        String updateQuery = "UPDATE pesan SET status = 'checkout' WHERE nokamar = ?";
        try (PreparedStatement preparedStatement = connect.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, nokamar);
            preparedStatement.executeUpdate();
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        errorMessage("Error occurred while updating status to checkout.");
    }
}

    
    private void fetchDataKamarFromDatabase() throws Exception {
        try (Connection connect = Connect.connectDB();
             PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM kamar");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

            while (resultSet.next()) {
                String nokamar = resultSet.getString("nokamar");
                String fasilitas = resultSet.getString("fasilitas");
                String tipe = resultSet.getString("tipe");
                String harga = currencyFormat.format(resultSet.getDouble("harga"));
                String bungbed = resultSet.getString("nama_bungbed");

                kamarData.add(new Kamar(nokamar, fasilitas, tipe, harga, bungbed));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupTableColumns() {
        addHotel_colnokamar.setCellValueFactory(new PropertyValueFactory<>("nokamar"));
        addHotel_colfasilitas.setCellValueFactory(new PropertyValueFactory<>("fasilitas"));
        addHotel_tipe.setCellValueFactory(new PropertyValueFactory<>("tipe"));
        addHotel_harga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        addHotel_Bungbed.setCellValueFactory(new PropertyValueFactory<>("bungbed"));
    }
    
private void setupTablepesanColumns() {
   addHotel_colnokamar2.setCellValueFactory(new PropertyValueFactory<>("nokamar"));
        addHotel_colfasilitas2.setCellValueFactory(new PropertyValueFactory<>("fasilitas"));
        addHotel_tipe2.setCellValueFactory(new PropertyValueFactory<>("tipe"));
        addHotel_harga2.setCellValueFactory(new PropertyValueFactory<>("harga"));
        addHotel_Bungbed2.setCellValueFactory(new PropertyValueFactory<>("bungbed"));
        statuscol.setCellValueFactory(new PropertyValueFactory<>("status"));
}

private void handleRowClick() {
    Kamar selectedKamar = addHotel_tableview.getSelectionModel().getSelectedItem();

    if (selectedKamar != null) {
        nokamar.setText(selectedKamar.getNokamar());
        fasilitas.setText(selectedKamar.getFasilitas());
        tipe.setText(selectedKamar.getTipe());   
        harga.setText(selectedKamar.getHarga());
        bungbed.setText(selectedKamar.getBungbed());
    }
}

@FXML
private void insertKamarButtonClicked() throws Exception {
    String selectedusername = nameusername.getText();
    String selectednokamar = nokamar.getText();
    String selectedfasilitas = fasilitas.getText();
    String selectedtipe = tipe.getText(); // Assuming String is the data type of tipe
    String selectedharga = harga.getText();
    String selectedBungbed = addHotel_Bungbed.getText(); // Assuming String is the data type of bungbed

    if (selectedusername.isEmpty() || selectednokamar.isEmpty() || selectedfasilitas.isEmpty() || selectedtipe.isEmpty() || selectedharga.isEmpty() || selectedBungbed.isEmpty() || tanggalpesan.getValue() == null) {
        errorMessage("Please fill in all the fields.");
        return;
    }

    // Use try-with-resources to automatically close the connection and statement
    try (Connection connect = Connect.connectDB()) {
        // Check if nokamar already exists in the database
        if (isNokamarExists(connect, selectedusername ,selectednokamar)) {
            errorMessage("Data with the same nokamar already exists. Please use a different nokamar.");
            return;
        }

        // Insert data into kamar
        try (PreparedStatement preparedStatement = connect.prepareStatement(
                "INSERT INTO pesan (username, nokamar, fasilitas, tipe, harga, nama_bungbed, checkin) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, selectedusername);
            preparedStatement.setString(2, selectednokamar);
            preparedStatement.setString(3, selectedfasilitas);
            preparedStatement.setString(4, selectedtipe);
            preparedStatement.setString(5, selectedharga);
            preparedStatement.setString(6, selectedBungbed);
            
            // Get the selected date from the DatePicker
            LocalDate checkinDate = tanggalpesan.getValue();
            
            // Convert the LocalDate to SQL Date
            java.sql.Date sqlCheckinDate = java.sql.Date.valueOf(checkinDate);
            
            preparedStatement.setDate(7, sqlCheckinDate);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                showAlert("Data inserted into kamar successfully!");
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
        tablepesan.getItems().clear();
        fetchDataKamarFromDatabase();
            fetchDatapesanFromDatabase();
    }
}

private boolean isNokamarExists(Connection connect, String username, String nokamar) throws SQLException {
    String query = "SELECT COUNT(*) FROM pesan WHERE username = ? AND nokamar = ? AND status = 'checkin'";
    try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, nokamar);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }
    return false;
}


    // Fungsi ini akan dipanggil saat tombol btncheckout diklik

    

private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

private void errorMessage(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

}

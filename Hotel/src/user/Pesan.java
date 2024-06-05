package user;

public class Pesan {
    private String nokamar;
    private String fasilitas;
    private String tipe;
    private String harga;
    private String bungbed;
    private String status;

    public Pesan(String nokamar, String fasilitas, String tipe, String harga, String bungbed , String status) {
        this.nokamar = nokamar;
        this.fasilitas = fasilitas;
        this.tipe = tipe;
        this.harga = harga;
        this.bungbed = bungbed;
        this.status=status;
    }
        public String getStatus() {
    return status;


    }

    public String getNokamar() {
        return nokamar;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public String getTipe() {
        return tipe;
    }

    public String getHarga() {
        return harga;
    }

    public String getBungbed() {
        return bungbed;
    }
    
    public void setStatus(String status){
        this.status=status;
    }
}

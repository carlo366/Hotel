/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

/**
 *
 * @author Boas
 */
public class Kamar {
    private String nokamar;
    private String fasilitas;
    private String tipe;
    private String harga;
        private String bungbed;


    public Kamar(String nokamar, String fasilitas, String tipe, String harga,String bungbed ) {
        this.nokamar = nokamar;
        this.fasilitas = fasilitas;
        this.tipe = tipe;
        this.harga = harga;
        this.bungbed = bungbed;
    
    }

    public String getBungbed(){
        return bungbed;
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

}

package tugas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class tugas1 {
    public static void main(String[] args) {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader(isr);

        try {
            System.out.print("Masukkan Nama Anda: ");
            String nama = input.readLine();

            System.out.print("Masukkan Alamat Anda: ");
            String alamat = input.readLine();

            System.out.print("Masukkan Nomor Telepon: ");
            String nomorTelepon = input.readLine();

            // Remove spaces and non-digits from the phone number
            nomorTelepon = nomorTelepon.replaceAll("\\s+", "").replaceAll("\\D+", "");

            String output = "Halo " + nama + ", alamatmu di " + alamat + "\nNomor teleponmu adalah " + nomorTelepon;

            System.out.println(output);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan dalam input. Pastikan Anda memasukkan data yang benar.");
        }
    }
}

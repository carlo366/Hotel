package tugas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class tugas2 {
    public static void main(String[] args) {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader(isr);

        try {
            System.out.print("Masukkan Nama: ");
            String nama = input.readLine();

            System.out.print("Jumlah mata kuliah: ");
            int jumlahMataKuliah = Integer.parseInt(input.readLine());

            int[] listNilai = new int[jumlahMataKuliah];

            System.out.println("Input:");

            for (int i = 1; i <= jumlahMataKuliah; i++) {
                System.out.print("Nilai MK" + i + " : ");
                listNilai[i - 1] = Integer.parseInt(input.readLine());
            }

            int totalNilai = 0;
            for (int nilai : listNilai) {
                totalNilai += nilai;
            }

            double rataRata = (double) totalNilai / jumlahMataKuliah;

            System.out.println("Output:");
            System.out.println("Masukkan Nama: " + nama);
            System.out.println("Jumlah mata kuliah: " + jumlahMataKuliah);

            for (int i = 0; i < jumlahMataKuliah; i++) {
                System.out.println("MK" + (i + 1) + ": " + listNilai[i]);
            }

            System.out.println("Rata-rata: " + rataRata);
        } catch (IOException | NumberFormatException e) {
            System.out.println("ayo pastikan kamu menginput dengan benar!");
        }
    }
}

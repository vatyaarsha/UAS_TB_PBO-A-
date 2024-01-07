import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

public class Pelanggan extends DataPelanggan {
    static final String DB_URL = "jdbc:mysql://localhost:3306/dbcoffe_shop";
    static final String USER = "root";
    static final String PASS = "";
    static Connection conn;
    static ResultSet rs;

    static List<Pelanggan> pelangganList = new ArrayList<>();

    // Method to insert data into the database using isiDataPelanggan
    public void isiDataPelanggan() {
        Scanner scanStr = new Scanner(System.in);
        Scanner scanIn = new Scanner(System.in);

        System.out.print("Masukkan no faktur = ");
        No_faktur = scanStr.next();
        System.out.print("Masukkan nama pelanggan = ");
        nama_Pelanggan = scanStr.next();
        System.out.print("Masukkan no HP = ");
        noHP_Pelanggan = scanStr.next();
        System.out.print("Masukkan alamat = ");
        alamat_Pelanggan = scanStr.next();
        System.out.print("Masukkan nama menu = ");
        namaMenu = scanStr.next();
        System.out.print("Masukkan jenis menu = ");
        jenisMenu = scanStr.next();
        System.out.print("Masukkan harga menu = ");
        hargaMenu = scanIn.nextInt();
        System.out.print("Masukkan jumlah menu = ");
        jumlahMenu = scanIn.nextInt();

        Pelanggan pelanggan = new Pelanggan();
        pelanggan.No_faktur = No_faktur;
        pelanggan.nama_Pelanggan = nama_Pelanggan;
        pelanggan.noHP_Pelanggan = noHP_Pelanggan;
        pelanggan.alamat_Pelanggan = alamat_Pelanggan;
        pelanggan.namaMenu = namaMenu;
        pelanggan.jenisMenu = jenisMenu;
        pelanggan.hargaMenu = hargaMenu;
        pelanggan.jumlahMenu = jumlahMenu;

        Integer totalBayar = hargaMenu * jumlahMenu;
        this.totalBayar = totalBayar;
        pelanggan.totalBayar = totalBayar;
        pelangganList.add(pelanggan);
    }
    public void tampilkanSemuaData() {
        System.out.println("+--------------------------------+");
        System.out.println("|    DATA PELANGGAN Coffee Shop Alam   |");
        System.out.println("+--------------------------------+");
        Integer totalBayar = hargaMenu * jumlahMenu;
        this.totalBayar = totalBayar;
        for (Pelanggan pelanggan : pelangganList) {
            System.out.println("No Faktur: " + pelanggan.No_faktur);
            System.out.println("Nama: " + pelanggan.nama_Pelanggan);
            System.out.println("No HP: " + pelanggan.noHP_Pelanggan);
            System.out.println("Alamat: " + pelanggan.alamat_Pelanggan);
            System.out.println("Jenis: " + pelanggan.jenisMenu);
            System.out.println("Menu: " + pelanggan.namaMenu);
            System.out.println("Harga: " + pelanggan.hargaMenu);
            System.out.println("Jumlah: " + pelanggan.jumlahMenu);
            System.out.println("Total: " + pelanggan.totalBayar);
            System.out.println("----------------------------------");
        }
    }
     // method untuk mencetak struk
    @Override
    public void Struk() throws Exception {
        Integer totalBayar = hargaMenu * jumlahMenu;
        this.totalBayar = totalBayar;

        // date
        Date date = new Date();
        SimpleDateFormat hari = new SimpleDateFormat("'Hari/Tanggal \t:' EEEEEEEEEE dd-MM-yy");
        SimpleDateFormat jam = new SimpleDateFormat("'Waktu \t\t:' hh:mm:ss z");

        System.out.println("----------- Coffee Shop Alam -----------");
        System.out.println(hari.format(date));
        System.out.println(jam.format(date));
        System.out.println("Faktur \t        : " + No_faktur);
        System.out.println("====================================");
        System.out.println("---------- DATA PELANGGAN ----------");
        System.out.println("Nama \t        : " + nama_Pelanggan);
        System.out.println("No HP \t\t: " + noHP_Pelanggan);
        System.out.println("Alamat \t\t: " + alamat_Pelanggan);
        System.out.println("------ DATA PEMBELIAN BARANG -------");
        System.out.println("Jenis \t      : " + jenisMenu);
        System.out.println("Menu \t      : " + namaMenu);
        System.out.println("Harga \t\t  : " + hargaMenu);
        System.out.println("Jumlah \t\t: " + jumlahMenu);
        System.out.println("Total  \t        : " + this.totalBayar);
        System.out.println("------------------------------------");
        System.out.println("Kasir \t\t: Vatya Arsha M\n");

        // method string
        System.out.println("toUpperCase\t: " + nama_Pelanggan.toUpperCase());
        System.out.println("length\t\t: " + nama_Pelanggan.length());

        // Call the insertDataNew method to insert data into the database
        insertDataNew();
    }

    public void insertDataNew() {
        try {
            // Explicitly load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
                String sql = "INSERT INTO identitas_struk (Faktur, Nama, NoHP, Alamat, Jenis, Menu, Harga, Jumlah, Total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, No_faktur);
                    pstmt.setString(2, nama_Pelanggan);
                    pstmt.setString(3, noHP_Pelanggan);
                    pstmt.setString(4, alamat_Pelanggan);
                    pstmt.setString(5, jenisMenu);
                    pstmt.setString(6, namaMenu);
                    pstmt.setInt(7, hargaMenu);
                    pstmt.setInt(8, jumlahMenu);
                    pstmt.setInt(9, totalBayar);

                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("\nData inserted successfully into 'identitas_struk' table!");
                    } else {
                        System.out.println("\nFailed to insert data into 'identitas_struk' table.");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    public void tampilkanData() throws Exception {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM identitas_struk";
            rs = stmt.executeQuery(sql);

            System.out.println("+--------------------------------+");
            System.out.println("|    DATA PELANGGAN Coffee Shop Alam   |");
            System.out.println("+--------------------------------+");

            while (rs.next()) {
                String No_faktur = rs.getString("Faktur");
                String nama_Pelanggan = rs.getString("Nama");
                String noHP_Pelanggan = rs.getString("NoHP");
                String alamat_Pelanggan = rs.getString("Alamat");
                String namaMenu = rs.getString("Jenis");
                String jenisMenu = rs.getString("Menu");
                String hargaMenu = rs.getString("Harga");
                String jumlahMenu = rs.getString("Jumlah");
                String totalBayar = rs.getString("Total");

                System.out.println(String.format("%s. %s -- %s -- (%s)", No_faktur, nama_Pelanggan, noHP_Pelanggan, alamat_Pelanggan, namaMenu, jenisMenu, hargaMenu, jumlahMenu, totalBayar));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateData() throws Exception {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            Scanner scanStr = new Scanner(System.in);
            Scanner scanIn = new Scanner(System.in);

            System.out.print("Masukkan Faktur yang akan diubah = ");
            No_faktur = scanStr.next();
            System.out.print("Masukkan Nama = ");
            nama_Pelanggan = scanStr.next();
            System.out.print("Masukkan No HP = ");
            noHP_Pelanggan = scanStr.next();
            System.out.print("Masukkan Alamat = ");
            alamat_Pelanggan = scanStr.next();
            System.out.print("Masukkan Jenis = ");
            namaMenu = scanStr.next();
            System.out.print("Masukkan Menu = ");
            jenisMenu = scanStr.next();
            System.out.print("Masukkan Harga = ");
            hargaMenu = scanIn.nextInt();
            System.out.print("Masukkan Jumlah = ");
            jumlahMenu = scanIn.nextInt();

            Integer totalBayar = hargaMenu * jumlahMenu;
            this.totalBayar = totalBayar;

            String sql = "UPDATE identitas_struk SET Nama=?, NoHP=?, Alamat=?, Jenis=?, Menu=?, Harga=?, Jumlah=?, Total=? WHERE Faktur=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nama_Pelanggan);
                pstmt.setString(2, noHP_Pelanggan);
                pstmt.setString(3, alamat_Pelanggan);
                pstmt.setString(4, jenisMenu);
                pstmt.setString(5, namaMenu);
                pstmt.setInt(6, hargaMenu);
                pstmt.setInt(7, jumlahMenu);
                pstmt.setInt(8, totalBayar);
                pstmt.setString(9, No_faktur);

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteData() throws Exception {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            Scanner scannerDel = new Scanner(System.in);
            // ambil input dari user
            System.out.print("Faktur yang mau dihapus : ");
            String no_faktur = (scannerDel.nextLine());

            // buat query hapus
            String sql = "DELETE FROM identitas_struk WHERE Faktur=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, no_faktur);
                pstmt.executeUpdate();
            }

            System.out.println("Data telah terhapus...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        Pelanggan pelanggan = new Pelanggan();
        try {
            pelanggan.isiDataPelanggan();
            pelanggan.Struk();
            pelanggan.tampilkanData();
            pelanggan.updateData();
            pelanggan.deleteData();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


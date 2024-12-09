package com.example.dbConnection;

import com.example.sekiller.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

    public static final String DB_URL = "jdbc:postgresql://localhost:5432/sekilappDb";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "3213725";


    public void sekilleriVeritabaninaEkle(List<Sekil> sekiller) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            connection.setAutoCommit(false);

            try {
                for (Sekil sekil : sekiller) {

                    String sekilEkleQuery = "INSERT INTO Sekiller DEFAULT VALUES RETURNING id";
                    try (PreparedStatement sekilStmt = connection.prepareStatement(sekilEkleQuery);
                         ResultSet rs = sekilStmt.executeQuery()) {

                        if (rs.next()) {
                            sekil.setId(rs.getInt(1));
                        } else {
                            throw new SQLException("Sekiller tablosuna ID eklenemedi!");
                        }
                    }


                    if (sekil instanceof Kare kare) {
                        String insertQuery = "INSERT INTO Kare (id, boyut, symbol) VALUES (?, ?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, kare.getId());
                            insertStmt.setInt(2, kare.getBoyut());
                            insertStmt.setString(3, String.valueOf(kare.getSymbol()));
                            insertStmt.executeUpdate();
                        }
                    } else if (sekil instanceof Dikdortgen dikdortgen) {
                        String insertQuery = "INSERT INTO Dikdortgen (id, yukseklik, genislik, symbol) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, dikdortgen.getId());
                            insertStmt.setInt(2, dikdortgen.getYukseklik());
                            insertStmt.setInt(3, dikdortgen.getGenislik());
                            insertStmt.setString(4, String.valueOf(dikdortgen.getSymbol()));
                            insertStmt.executeUpdate();
                        }
                    } else if (sekil instanceof Daire daire) {
                        String insertQuery = "INSERT INTO Daire (id, cap, symbol) VALUES (?, ?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, daire.getId());
                            insertStmt.setInt(2, daire.getCap());
                            insertStmt.setString(3, String.valueOf(daire.getSymbol()));
                            insertStmt.executeUpdate();
                        }
                    } else if (sekil instanceof Ucgen ucgen) {
                        String insertQuery = "INSERT INTO Ucgen (id, yukseklik, symbol) VALUES (?, ?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, ucgen.getId());
                            insertStmt.setInt(2, ucgen.getYukseklik());
                            insertStmt.setString(3, String.valueOf(ucgen.getSymbol()));
                            insertStmt.executeUpdate();
                        }
                    } else {
                        throw new IllegalArgumentException("Bilinmeyen şekil türü: " + sekil.getClass().getSimpleName());
                    }
                }
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }


    public List<Sekil> veritabanindanSekilleriGetir() throws SQLException {
        List<Sekil> sekiller = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sekillerQuery = "SELECT id FROM Sekiller";
            try (PreparedStatement sekilStmt = connection.prepareStatement(sekillerQuery);
                 ResultSet rs = sekilStmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    Sekil sekil = getKare(connection, id);

                    if (sekil == null) {
                        sekil = getDikdortgen(connection, id);
                    }
                    if (sekil == null) {
                        sekil = getDaire(connection, id);
                    }
                    if (sekil == null) {
                        sekil = getUcgen(connection, id);
                    }

                    if (sekil != null) {
                        sekiller.add(sekil);
                    } else {
                        System.err.println("Şekil bulunamadı: ID = " + id);
                    }
                }
            }
        }
        return sekiller;
    }


    private Kare getKare(Connection connection, int id) throws SQLException {
        String kareQuery = "SELECT boyut, symbol FROM Kare WHERE id = ?";
        try (PreparedStatement kareStmt = connection.prepareStatement(kareQuery)) {
            kareStmt.setInt(1, id);
            try (ResultSet kareRs = kareStmt.executeQuery()) {
                if (kareRs.next()) {
                    int boyut = kareRs.getInt("boyut");
                    String symbolStr = kareRs.getString("symbol");
                    char symbol = symbolStr != null && !symbolStr.isEmpty() ? symbolStr.charAt(0) : ' ';
                    return new Kare(id, boyut, symbol);
                }
            }
        }
        return null;
    }

    private Dikdortgen getDikdortgen(Connection connection, int id) throws SQLException {
        String dikdortgenQuery = "SELECT yukseklik, genislik, symbol FROM Dikdortgen WHERE id = ?";
        try (PreparedStatement dikdortgenStmt = connection.prepareStatement(dikdortgenQuery)) {
            dikdortgenStmt.setInt(1, id);
            try (ResultSet dikdortgenRs = dikdortgenStmt.executeQuery()) {
                if (dikdortgenRs.next()) {
                    int yukseklik = dikdortgenRs.getInt("yukseklik");
                    int genislik = dikdortgenRs.getInt("genislik");
                    String symbolStr = dikdortgenRs.getString("symbol");
                    char symbol = symbolStr != null && !symbolStr.isEmpty() ? symbolStr.charAt(0) : ' ';
                    return new Dikdortgen(id, yukseklik, genislik, symbol);
                }
            }
        }
        return null;
    }

    private Daire getDaire(Connection connection, int id) throws SQLException {
        String daireQuery = "SELECT cap, symbol FROM Daire WHERE id = ?";
        try (PreparedStatement daireStmt = connection.prepareStatement(daireQuery)) {
            daireStmt.setInt(1, id);
            try (ResultSet daireRs = daireStmt.executeQuery()) {
                if (daireRs.next()) {
                    int cap = daireRs.getInt("cap");
                    String symbolStr = daireRs.getString("symbol");
                    char symbol = symbolStr != null && !symbolStr.isEmpty() ? symbolStr.charAt(0) : ' ';
                    return new Daire(id, cap, symbol);
                }
            }
        }
        return null;
    }

    private Ucgen getUcgen(Connection connection, int id) throws SQLException {
        String ucgenQuery = "SELECT yukseklik, symbol FROM Ucgen WHERE id = ?";
        try (PreparedStatement ucgenStmt = connection.prepareStatement(ucgenQuery)) {
            ucgenStmt.setInt(1, id);
            try (ResultSet ucgenRs = ucgenStmt.executeQuery()) {
                if (ucgenRs.next()) {
                    int yukseklik = ucgenRs.getInt("yukseklik");
                    String symbolStr = ucgenRs.getString("symbol");
                    char symbol = symbolStr != null && !symbolStr.isEmpty() ? symbolStr.charAt(0) : ' ';
                    return new Ucgen(id, yukseklik, symbol);
                }
            }
        }
        return null;
    }

    public void veritabaniniSifirla() throws SQLException {

        String[] tableNames = {"Ucgen", "Daire", "Dikdortgen", "Kare", "Sekiller"};

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            connection.setAutoCommit(false);

            try {
                for (String tableName : tableNames) {

                    String query = "TRUNCATE TABLE " + tableName + " CASCADE";
                    try (PreparedStatement stmt = connection.prepareStatement(query)) {
                        stmt.executeUpdate();
                        System.out.println(tableName + " tablosu temizlendi.");
                    }
                }
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }



}

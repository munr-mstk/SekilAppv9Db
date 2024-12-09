package com.example.sekiller;

import com.example.log.LogUtil;

public class Ucgen implements Sekil{

    private String TYPE = "Ucgen";
    private int id;
    private int yukseklik;
    private char symbol;

    private static final char DEFAULT_SYMBOL = '*';

    // Varsayılan yapıcı metot
    public Ucgen(int yukseklik, char symbol) {
        this.yukseklik = yukseklik;
        this.symbol = symbol != 0 ? symbol : DEFAULT_SYMBOL;
    }

    // Tüm alanları içeren yapıcı metot
    public Ucgen(String TYPE, int id, int yukseklik, char symbol) {
        this.TYPE = TYPE;
        this.id = id;
        this.yukseklik = yukseklik;
        this.symbol = symbol != 0 ? symbol : DEFAULT_SYMBOL;
    }

    public Ucgen(int id, int yukseklik, char symbol) {
        this.id = id;
        this.yukseklik = yukseklik;
        this.symbol = symbol != 0 ? symbol : DEFAULT_SYMBOL;
    }

    public String getType() {
        return TYPE;
    }

    public int getId() {
        return id;
    }

    public int getYukseklik() {
        return yukseklik;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public void setId(int anInt) {
        this.id = anInt;

    }

    @Override
    public void ciz() {
        // Üçgeni çizmek için döngü
        for (int i = 0; i < yukseklik; i++) {
            for (int j = 0; j < yukseklik - i - 1; j++) {
                System.out.print("  ");
            }
            for (int j = 0; j < 2 * i + 1; j++) {
                System.out.print(symbol + " ");
            }
            System.out.println();
        }

        // Alan ve çevre loglama
        LogUtil.log("Üçgen alanı: " + alanHesapla());
        LogUtil.log("Üçgen çevresi: " + cevreHesapla());
    }

    @Override
    public void sembolDegistir(char yeniSembol) {
        this.symbol = yeniSembol;
    }

    @Override
    public double alanHesapla() {
        int taban = 2 * (yukseklik - 1) + 1;
        return (taban * yukseklik) / 2.0;
    }

    @Override
    public double cevreHesapla() {
        int taban = 2 * (yukseklik - 1) + 1;
        return taban + 2 * Math.sqrt(Math.pow(taban / 2.0, 2) + Math.pow(yukseklik, 2));
    }

    @Override
    public String toString() {
        return String.format("%s - ID:  %d, Yükseklik: %d, Sembol: %c", TYPE, id, yukseklik, symbol);
    }
}

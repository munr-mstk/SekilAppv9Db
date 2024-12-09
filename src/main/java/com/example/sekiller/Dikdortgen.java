package com.example.sekiller;

import com.example.log.LogUtil;

public class Dikdortgen implements Sekil{

    private static final char DEFAULT_SYMBOL = '*';
    private static final String TYPE = "Dikdortgen";

    private int id;
    private int yukseklik;
    private int genislik;
    private char symbol;

    // Constructor
    public Dikdortgen(int id, int yukseklik, int genislik, char symbol) {
        if (yukseklik <= 0 || genislik <= 0) {
            throw new IllegalArgumentException("Yükseklik ve genişlik 0 veya negatif olamaz.");
        }
        this.id = id;
        this.yukseklik = yukseklik;
        this.genislik = genislik;
        this.symbol = symbol != 0 ? symbol : DEFAULT_SYMBOL;
    }

    // Getters
    public String getType() {
        return TYPE;
    }

    public int getId() {
        return id;
    }

    public int getYukseklik() {
        return yukseklik;
    }

    public int getGenislik() {
        return genislik;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public void setId(int anInt) {

    }

    @Override
    public void ciz() {
        for (int i = 0; i < yukseklik; i++) {
            for (int j = 0; j < genislik; j++) {
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
        LogUtil.log("Dikdörtgen alanı: " + alanHesapla());
        LogUtil.log("Dikdörtgen çevresi: " + cevreHesapla());
    }

    @Override
    public void sembolDegistir(char yeniSembol) {
        this.symbol = yeniSembol;
    }

    @Override
    public double alanHesapla() {
        return yukseklik * genislik;
    }

    @Override
    public double cevreHesapla() {
        return 2 * (genislik + yukseklik);
    }

    @Override
    public String toString() {
        return String.format("%s - ID: %d, Genişlik: %d, Yükseklik: %d, Sembol: %c", TYPE, id, genislik, yukseklik, symbol);
    }
}

package com.example.sekiller;

import com.example.log.LogUtil;

public class Kare implements Sekil {

    private static final char DEFAULT_SYMBOL = '*';
    private static final String TYPE = "Kare";

    private int id;
    private int boyut;
    private char symbol;


    public Kare(int id, int boyut, char symbol) {
        if (boyut <= 0) {
            throw new IllegalArgumentException("Boyut 0 veya negatif olamaz.");
        }
        this.id = id;
        this.boyut = boyut;
        this.symbol = (symbol != 0) ? symbol : DEFAULT_SYMBOL;
    }


    public Kare(int id, int boyut) {
        this(id, boyut, DEFAULT_SYMBOL);
    }


    public String getType() {
        return TYPE;
    }

    public int getId() {
        return id;
    }

    public int getBoyut() {
        return boyut;
    }

    public char getSymbol() {
        return symbol;
    }


    @Override
    public void setId(int id) {
        this.id = id;
    }


    @Override
    public void ciz() {
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
        LogUtil.log("Kare alanı: " + alanHesapla());
        LogUtil.log("Kare çevresi: " + cevreHesapla());
    }


    @Override
    public void sembolDegistir(char yeniSembol) {
        this.symbol = yeniSembol;
    }


    @Override
    public double alanHesapla() {
        return boyut * boyut;
    }


    @Override
    public double cevreHesapla() {
        return 4 * boyut;
    }


    @Override
    public String toString() {
        return String.format("%s - ID: %d, Boyut: %d, Sembol: %c", TYPE, id, boyut, symbol);
    }
}

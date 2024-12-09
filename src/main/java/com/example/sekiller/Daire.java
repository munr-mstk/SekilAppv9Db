package com.example.sekiller;

import com.example.log.LogUtil;

public class Daire implements Sekil {

    private static final char DEFAULT_SYMBOL = '*';
    private static final String TYPE = "Daire";

    private int id;
    private int cap;
    private char symbol;

    // Constructor
    public Daire(int id, int cap, char symbol) {
        if (cap <= 0) {
            throw new IllegalArgumentException("Çap 0 veya negatif olamaz.");
        }
        this.id = id;
        this.cap = cap;
        this.symbol = (symbol != 0) ? symbol : DEFAULT_SYMBOL;
    }

    public Daire(int cap, char symbol) {
        this(0, cap, symbol);
    }

    // Getters
    public String getType() {
        return TYPE;
    }

    public int getId() {
        return id;
    }

    public int getCap() {
        return cap;
    }

    public char getSymbol() {
        return symbol;
    }

    // Setters
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void ciz() {
        double r = cap / 2.0;

        for (int i = 0; i <= cap; i++) {
            for (int j = 0; j <= cap; j++) {
                double distance = Math.sqrt(Math.pow(i - r, 2) + Math.pow(j - r, 2));
                if (distance > r - 0.5 && distance < r + 0.5) {
                    System.out.print(symbol + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        LogUtil.log("Daire alanı: " + alanHesapla());
        LogUtil.log("Daire çevresi: " + cevreHesapla());
    }

    @Override
    public void sembolDegistir(char yeniSembol) {
        this.symbol = yeniSembol;
    }

    @Override
    public double alanHesapla() {
        double r = cap / 2.0;
        return Math.PI * r * r;
    }

    @Override
    public double cevreHesapla() {
        return Math.PI * cap;
    }

    @Override
    public String toString() {
        return String.format("%s - ID: %d, Çap: %d, Sembol: %c", TYPE, id, cap, symbol);
    }
}
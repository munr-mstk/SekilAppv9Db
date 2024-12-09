package com.example;

import com.example.dbConnection.DbManager;
import com.example.manager.InputManager;
import com.example.manager.OutputManager;
import com.example.sekiller.*;

import java.util.ArrayList;
import java.util.List;


public class Main {
    private static final List<Sekil> sekilListesi = new ArrayList<>();
    public static void main(String[] args) {

        Sekil seciliSekil = null;
        char sembol = '*';
        while (true) {
            try {
                OutputManager.print("\nSeçenekler:");
                OutputManager.print("1: Küçük Kare çiz");
                OutputManager.print("2: İstediğin boyutta kare çiz ve listeye ekle");
                OutputManager.print("3: Daire çiz ve listeye ekle");
                OutputManager.print("4: Üçgen çiz ve listeye ekle");
                OutputManager.print("5: Dikdortgen çiz ve listeye ekle");
                OutputManager.print("6: Toplam alan ve çevre bilgilerini göster");
                OutputManager.print("7: Listeyi temizle");
                OutputManager.print("8: Yeni sembol belirle");
                OutputManager.print("9: Şekilleri veritabanına ekle");
                OutputManager.print("10: Şekilleri veritabanından oku");
                OutputManager.print("11: Veritabanı içeriklerini sıfırla");
                OutputManager.print("12: Çıkış");
                OutputManager.printWithPrompt("Seçiminiz: ");

                int secim = InputManager.readValidatedSelection("");

                switch (secim) {
                    case 1:

                        seciliSekil = new Kare(1, 3);
                        sekilListesi.add(seciliSekil);
                        seciliSekil.ciz();
                        break;
                    case 2:

                        int kareBoyut = InputManager.readUnrestrictedInt("Kare boyutunu girin: ");
                        seciliSekil = new Kare(2, kareBoyut);
                        sekilListesi.add(seciliSekil);
                        seciliSekil.ciz();
                        break;
                    case 3:
                        int cap = InputManager.readUnrestrictedInt("Dairenin çapını girin: ");
                        seciliSekil = new Daire(cap, sembol);
                        sekilListesi.add(seciliSekil);
                        seciliSekil.ciz();
                        break;


                    case 4:

                        int yukselik = InputManager.readUnrestrictedInt("Üçgenin yüksekliğini girin: ");
                        seciliSekil = new Ucgen(yukselik, sembol);
                        sekilListesi.add(seciliSekil);
                        seciliSekil.ciz();

                        break;
                    case 5:

                        int genislik = InputManager.readUnrestrictedInt("Dikdörtgen genişliğini girin: ");
                        int yukseklik = InputManager.readUnrestrictedInt("Dikdörtgen yüksekliğini girin: ");
                        seciliSekil = new Dikdortgen(3, yukseklik, genislik, sembol);
                        sekilListesi.add(seciliSekil);
                        seciliSekil.ciz();
                        break;
                    case 6:

                        hesaplaToplamAlanVeCevre();
                        break;
                    case 7:

                        sekilListesi.clear();
                        OutputManager.print("Şekil listesi sıfırlandı.");
                        break;
                    case 8:

                        sembol = InputManager.readChar("Yeni sembolü girin: ");
                        OutputManager.print("Yeni sembol belirlendi: " + sembol);
                        break;
                    case 9:

                        if (sekilListesi.isEmpty()) {
                            OutputManager.print("Şekil listesi boş. Lütfen önce şekiller ekleyin.");
                        } else {
                            try {
                                DbManager dbManager = new DbManager();

                                dbManager.sekilleriVeritabaninaEkle(sekilListesi);
                                OutputManager.print("Şekiller başarıyla veritabanına eklendi.");
                            } catch (Exception e) {
                                OutputManager.print("Veritabanına ekleme sırasında bir hata oluştu: " + e.getMessage());
                            }
                        }
                        break;
                    case 10:
                        try {
                            DbManager dbManager = new DbManager();
                            List<Sekil> dbSekiller = dbManager.veritabanindanSekilleriGetir();

                            sekilListesi.clear();
                            sekilListesi.addAll(dbSekiller);

                            OutputManager.print("Veritabanından okunan şekiller:");
                            for (Sekil sekil : sekilListesi) {
                                OutputManager.print(sekil.toString());
                            }
                        } catch (Exception e) {
                            OutputManager.print("Veritabanından okuma sırasında bir hata oluştu: " + e.getMessage());
                        }
                        break;
                    case 11:
                        try {
                            DbManager dbManager = new DbManager();
                            dbManager.veritabaniniSifirla();
                            OutputManager.print("Veritabanı başarıyla sıfırlandı!");
                        } catch (Exception e) {
                            OutputManager.print("Veritabanını sıfırlama sırasında bir hata oluştu: " + e.getMessage());
                        }
                        break;


                    case 12:

                        OutputManager.print("Programdan çıkılıyor...");
                        InputManager.closeScanner();
                        return;
                    default:
                        OutputManager.print("Geçersiz seçim. Lütfen 1 ile 11 arasında bir seçim yapın.");
                }
            } catch (Exception e) {
                OutputManager.print("Bir hata oluştu: " + e.getMessage());
            }
        }
    }

    private static void hesaplaToplamAlanVeCevre() {
        double toplamAlan = 0;
        double toplamCevre = 0;

        for (Sekil sekil : sekilListesi) {
            toplamAlan += sekil.alanHesapla();
            toplamCevre += sekil.cevreHesapla();
        }

        OutputManager.print("Toplam Alan: " + toplamAlan);
        OutputManager.print("Toplam Çevre: " + toplamCevre);
    }
    }

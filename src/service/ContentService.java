package service;

import java.awt.Desktop;
import java.io.File;

public class ContentService {

    // Metodu 'boolean' yaptık ki sonucu Main'e bildirebilelim
    public boolean openContent(String filePath) {
        try {
            // Dosya yolu boş mu kontrolü
            if (filePath == null || filePath.trim().isEmpty()) {
                System.out.println(">> HATA: Bu ders için tanımlanmış bir dosya yolu yok!");
                return false;
            }

            File file = new File(filePath);

            if (!file.exists()) {
                System.err.println(">> HATA: Dosya bilgisayarında bulunamadı! Yol: " + filePath);
                return false; // Başarısız oldu, false dön
            }

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                System.out.println(">> Dosya açılıyor: " + file.getName());
                desktop.open(file);
                return true; // Başarıyla açıldı, true dön
            } else {
                System.out.println(">> Sistem dosya açmayı desteklemiyor.");
                return false;
            }
        } catch (Exception e) {
            System.err.println(">> Dosya açılırken hata oluştu: " + e.getMessage());
            return false; // Hata oldu, false dön
        }
    }
}
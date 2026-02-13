package service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class CertificateService {

    // void yerine String yaptık
    public String createCertificate(String studentName, String courseName) {
        try {
            int width = 900;
            int height = 600;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            // --- TASARIM (AYNI) ---
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(new Color(0, 50, 100));
            g2d.setStroke(new BasicStroke(20));
            g2d.drawRect(0, 0, width, height);

            g2d.setFont(new Font("Serif", Font.BOLD, 48));
            g2d.drawString("CERTIFICATE OF COMPLETION", 50, 150);

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 30));
            g2d.drawString("This certifies that", 300, 250);

            g2d.setFont(new Font("SansSerif", Font.BOLD, 40));
            g2d.drawString(studentName, 250, 320);

            g2d.setFont(new Font("Arial", Font.PLAIN, 25));
            g2d.drawString("Has successfully completed the course:", 200, 400);

            g2d.setColor(new Color(200, 0, 0));
            g2d.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 35));
            g2d.drawString(courseName, 200, 460);

            g2d.dispose();

            // Dosya isimlendirme
            String cleanStudentName = studentName.replaceAll("\\s+", "_");
            String cleanCourseName = courseName.replaceAll("\\s+", "_");
            String fileName = "Sertifika_" + cleanStudentName + "_" + cleanCourseName + ".png";

            File file = new File(fileName);
            ImageIO.write(image, "png", file);

            System.out.println(">> [SİSTEM]: Sertifika başarıyla kaydedildi: " + file.getAbsolutePath());

            // YENİ: Dosyanın tam yolunu geri döndürüyoruz
            return file.getAbsolutePath();

        } catch (Exception e) {
            System.err.println("Sertifika oluşturulurken hata: " + e.getMessage());
            return null;
        }
    }
}
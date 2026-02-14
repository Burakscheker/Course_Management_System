package data;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class MockDatabase {
    public static List<User> users = new ArrayList<>();
    public static List<Course> courses = new ArrayList<>();
    public static List<Enrollment> enrollments = new ArrayList<>();

    static {
        // --- KULLANICILAR --
        Admin admin = new Admin("admin", "Sistem Yöneticisi");
        users.add(admin);
        users.add(new Student("burak", "Burak Şeker"));

        // --- KURS 1: JAVA ---
        Instructor javaHoca = new Instructor("hoca1", "Dr. Burak Java Şeker");
        Course javaCourse = new Course("Sıfırdan Zirveye Java Başlangıç", javaHoca);

        // Dosyalar "materyaller" klasöründe aranır
        javaCourse.addLesson(new Lesson("1. Java Giriş ve Kurulum", "PDF", "materyaller/java1.pdf"));
        javaCourse.addLesson(new Lesson("2. Eclipse ve Proje Oluşturma", "PDF", "materyaller/java2.pdf"));
        javaCourse.addLesson(new Lesson("3. Kod Yapısı ve Merhaba Dünya", "PDF", "materyaller/java3.pdf"));
        javaCourse.addLesson(new Lesson("4. Değişkenler ve Kontrol Yapıları", "PDF", "materyaller/java4.pdf"));

        courses.add(javaCourse);

        // --- KURS 2: PYTHON ---
        Instructor pyHoca = new Instructor("hoca2", "Dr. Burak Python Şeker");
        Course pythonCourse = new Course("Python ile Veri Bilimi ve Yapay Zeka", pyHoca);

        pythonCourse.addLesson(new Lesson("1. Python ve Anaconda Kurulumu", "PDF", "materyaller/python1.pdf"));
        pythonCourse.addLesson(new Lesson("2. Anaconda Navigator ve Spyder", "PDF", "materyaller/python2.pdf"));
        pythonCourse.addLesson(new Lesson("3. Jupyter Notebook ve Arayüz", "PDF", "materyaller/python3.pdf"));
        pythonCourse.addLesson(new Lesson("4. Veri Tipleri ve Operatörler", "PDF", "materyaller/python4.pdf"));
        pythonCourse.addLesson(new Lesson("5. Fonksiyonlar ve Döngüler", "PDF", "materyaller/python5.pdf"));
        pythonCourse.addLesson(new Lesson("6. Pandas ve CSV Dosyaları", "PDF", "materyaller/python6.pdf"));
        pythonCourse.addLesson(new Lesson("7. Veri Analizi ve İstatistik", "PDF", "materyaller/python7.pdf"));

        courses.add(pythonCourse);
    }
}
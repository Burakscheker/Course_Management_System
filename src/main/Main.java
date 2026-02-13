package main;

import data.MockDatabase;
import model.*;
import service.CertificateService;
import service.ContentService;
import service.CourseManager;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static CourseManager courseManager = new CourseManager();
    private static ContentService contentService = new ContentService();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;

    public static void main(String[] args) {
        System.out.println("=== KURUMSAL EĞİTİM SİSTEMİ (LMS) ===");

        while (true) {
            System.out.print("\nKullanıcı Adı (Çıkış için 'exit'): ");
            String username = scanner.nextLine().trim();

            if (username.equalsIgnoreCase("exit")) break;

            // Login metodunu çağırdık, artık o kendi içinde hallediyor
            login(username);

            if (currentUser != null) {
                if (currentUser instanceof Admin) {
                    adminMenu();
                } else if (currentUser instanceof Student) {
                    studentMenu((Student) currentUser);
                }
                currentUser = null;
            }
        }
    }

    // --- BURASI DEĞİŞTİ: OTOMATİK KAYIT SİSTEMİ ---
    private static void login(String username) {
        // 1. Önce veritabanında bu kişi var mı diye bakıyoruz
        for (User u : MockDatabase.users) {
            if (u.getUsername().equals(username)) {
                currentUser = u;
                System.out.println("Hoşgeldin, " + u.getFullName());
                return; // Bulduysak çıkıyoruz
            }
        }

        // 2. Eğer döngü bitti ve return olmadıysa, kullanıcı YOK demektir.
        // O zaman yeni kayıt oluşturalım:
        System.out.println(">> Bu kullanıcı adı sistemde bulunamadı.");
        System.out.println(">> Yeni kayıt oluşturuluyor...");

        System.out.print("Lütfen Adınızı Soyadınızı girin (Sertifikada yazacak): ");
        String fullName = scanner.nextLine();

        // Yeni öğrenciyi oluşturuyoruz
        Student newStudent = new Student(username, fullName);

        // Veritabanına (Listeye) ekliyoruz
        MockDatabase.users.add(newStudent);

        // Giriş yapmış sayıyoruz
        currentUser = newStudent;

        System.out.println("Kayıt Başarılı! Aramıza hoşgeldin, " + fullName);
    }

    // --- DİĞER KISIMLAR AYNI KALIYOR ---

    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- YÖNETİCİ PANELİ ---");
            System.out.println("1. Tüm Kullanıcıları ve Durumlarını Listele");
            System.out.println("2. Kursları ve Kayıtlı Öğrencileri Listele");
            System.out.println("3. Yeni Kurs Ekle");
            System.out.println("4. Kursa Materyal (Ders) Ekle");
            System.out.println("0. Çıkış Yap");
            System.out.print("Seçim: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Hatalı giriş!");
                continue;
            }

            if (choice == 0) break;

            switch (choice) {
                case 1:
                    System.out.println("\n--- ÖĞRENCİ RAPORLARI ---");
                    for (Enrollment e : MockDatabase.enrollments) {
                        System.out.println("Öğrenci: " + e.getStudent().getFullName());
                        System.out.println("  Kurs: " + e.getCourse().getTitle());
                        System.out.println("  İlerleme: %" + e.getProgressPercentage());
                        System.out.println("  Sertifika: " + (e.isCertified() ? "VAR" : "YOK"));
                        System.out.println("-----------------------------");
                    }
                    if (MockDatabase.enrollments.isEmpty()) System.out.println("Henüz kayıtlı öğrenci yok.");
                    break;

                case 2:
                    System.out.println("\n--- KURS LİSTESİ ---");
                    for (Course c : MockDatabase.courses) {
                        System.out.println("KURS: " + c.getTitle() + " (Eğitmen: " + c.getInstructor().getFullName() + ")");
                        System.out.println("  -> Kayıtlı Öğrenciler:");
                        boolean found = false;
                        for (Enrollment e : MockDatabase.enrollments) {
                            if (e.getCourse().equals(c)) {
                                System.out.println("     - " + e.getStudent().getFullName() + " (Durum: %" + e.getProgressPercentage() + ")");
                                found = true;
                            }
                        }
                        if (!found) System.out.println("     (Henüz kimse kayıtlı değil)");
                    }
                    break;

                case 3:
                    System.out.print("Yeni Kurs Adı: ");
                    String cName = scanner.nextLine();
                    Course newCourse = new Course(cName, new Instructor("admin", "Sistem Admini"));
                    MockDatabase.courses.add(newCourse);
                    System.out.println("Kurs oluşturuldu.");
                    break;

                case 4:
                    System.out.println("Hangi kursa ders eklenecek?");
                    for (int i = 0; i < MockDatabase.courses.size(); i++)
                        System.out.println((i + 1) + ". " + MockDatabase.courses.get(i).getTitle());

                    int cIdx = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (cIdx >= 0 && cIdx < MockDatabase.courses.size()) {
                        System.out.print("Ders Başlığı: ");
                        String lTitle = scanner.nextLine();
                        System.out.print("Dosya Yolu (Örn: materyaller/ders.pdf): ");
                        String lPath = scanner.nextLine();

                        MockDatabase.courses.get(cIdx).addLesson(new Lesson(lTitle, "Doküman", lPath));
                        System.out.println("Ders ve içerik eklendi.");
                    }
                    break;
            }
        }
    }

    private static void studentMenu(Student student) {
        while (true) {
            System.out.println("\n--- ÖĞRENCİ PANELİ (" + student.getFullName() + ") ---");
            System.out.println("1. Kurs Katalogu");
            System.out.println("2. Kursa Kaydol");
            System.out.println("3. Derslerimi Görüntüle ve İçerik Aç");
            System.out.println("4. Sertifikalarımı Görüntüle");
            System.out.println("0. Çıkış Yap");
            System.out.print("Seçim: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Hatalı giriş!");
                continue;
            }

            if (choice == 0) break;

            switch (choice) {
                case 1:
                    System.out.println("\n--- KURS KATALOGU ---");
                    for (Course c : MockDatabase.courses)
                        System.out.println("- " + c.getTitle());
                    break;
                case 2:
                    System.out.println("\n--- KAYIT OLUNACAK KURS ---");
                    for (int i = 0; i < MockDatabase.courses.size(); i++)
                        System.out.println((i + 1) + ". " + MockDatabase.courses.get(i).getTitle());

                    System.out.print("Kurs No: ");
                    int enrollIdx = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (enrollIdx >= 0 && enrollIdx < MockDatabase.courses.size()) {
                        courseManager.enrollStudent(student, MockDatabase.courses.get(enrollIdx));
                    }
                    break;
                case 3:
                    processStudentCourse(student);
                    break;
                case 4:
                    viewCertificates(student);
                    break;
            }
        }
    }

    private static void viewCertificates(Student student) {
        System.out.println("\n--- KAZANILAN SERTİFİKALAR ---");
        List<Enrollment> certifiedEnrollments = new ArrayList<>();

        for(Enrollment e : MockDatabase.enrollments) {
            if(e.getStudent().getUsername().equals(student.getUsername()) && e.isCertified()) {
                certifiedEnrollments.add(e);
            }
        }

        if(certifiedEnrollments.isEmpty()) {
            System.out.println("Henüz kazanılmış bir sertifikan yok. Çalışmaya devam!");
            return;
        }

        for(int i=0; i<certifiedEnrollments.size(); i++) {
            System.out.println((i+1) + ". " + certifiedEnrollments.get(i).getCourse().getTitle());
        }

        System.out.print("Görüntülemek istediğin sertifika no (Geri dön: 0): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice > 0 && choice <= certifiedEnrollments.size()) {
            String path = certifiedEnrollments.get(choice-1).getCertificatePath();
            System.out.println("Sertifika açılıyor...");
            if (path != null) {
                contentService.openContent(path);
            } else {
                System.out.println("HATA: Sertifika dosyası bulunamadı.");
            }
        }
    }

    private static void processStudentCourse(Student student) {
        System.out.println("\n--- KAYITLI KURSLARIN ---");
        List<Enrollment> myEnrollments = new ArrayList<>();
        int count = 1;

        for (Enrollment e : MockDatabase.enrollments) {
            if (e.getStudent().getUsername().equals(student.getUsername())) {
                myEnrollments.add(e);
                System.out.println(count + ". " + e.getCourse().getTitle() +
                        " (İlerleme: %" + e.getProgressPercentage() + ")");
                count++;
            }
        }

        if (myEnrollments.isEmpty()) {
            System.out.println("Henüz hiçbir kursa kayıtlı değilsin.");
            return;
        }

        System.out.print("Girmek istediğin kursun sıra numarası: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            return;
        }

        if (choice < 1 || choice > myEnrollments.size()) {
            System.out.println("Geçersiz seçim.");
            return;
        }

        Enrollment activeEnrollment = myEnrollments.get(choice - 1);
        Course c = activeEnrollment.getCourse();

        System.out.println("\n--- " + c.getTitle().toUpperCase() + " DERSLERİ ---");
        System.out.println("Sertifika Durumu: " + (activeEnrollment.isCertified() ? "ALINDI" : "ALINMADI"));

        List<Lesson> lessons = c.getLessons();
        if (lessons.isEmpty()) {
            System.out.println("Bu kursta henüz ders içeriği yok.");
            return;
        }

        for (int i = 0; i < lessons.size(); i++) {
            System.out.println((i + 1) + ". " + lessons.get(i).getTitle());
        }

        System.out.print("İçeriğini açmak istediğin ders no (Çıkış için 0): ");
        int lIdx = scanner.nextInt() - 1;
        scanner.nextLine();

        if (lIdx == -1) return;

        if (lIdx >= 0 && lIdx < lessons.size()) {
            Lesson selectedLesson = lessons.get(lIdx);

            boolean isOpened = contentService.openContent(selectedLesson.getFilePath());

            if (isOpened) {
                courseManager.completeLesson(activeEnrollment, selectedLesson);
            } else {
                System.out.println("!!! Dosya açılamadığı için ders tamamlandı sayılmadı.");
            }
        } else {
            System.out.println("Geçersiz ders numarası.");
        }
    }
}
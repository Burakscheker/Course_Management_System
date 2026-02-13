package service;

import data.MockDatabase;
import model.*;
import java.util.List;

public class CourseManager {
    private CertificateService certificateService;

    public CourseManager() {
        this.certificateService = new CertificateService();
    }

    public void enrollStudent(Student student, Course course) {
        Enrollment newEnrollment = new Enrollment(student, course);
        MockDatabase.enrollments.add(newEnrollment);
        System.out.println(student.getFullName() + ", " + course.getTitle() + " kursuna başarıyla kaydoldu.");
    }

    public void completeLesson(Enrollment enrollment, Lesson lesson) {
        enrollment.markLessonComplete(lesson);
        System.out.println("Ders tamamlandı: " + lesson.getTitle());

        if (enrollment.isCourseFinished() && !enrollment.isCertified()) {
            System.out.println("Tebrikler! Kursun tamamını bitirdiniz.");
            enrollment.setCertified(true);

            // YENİ: Sertifikayı oluşturup yolunu kaydediyoruz
            String certPath = certificateService.createCertificate(
                    enrollment.getStudent().getFullName(),
                    enrollment.getCourse().getTitle()
            );

            enrollment.setCertificatePath(certPath);
        }
    }

    public List<Course> getAllCourses() { return MockDatabase.courses; }

    public Enrollment getEnrollment(Student student, Course course) {
        for (Enrollment e : MockDatabase.enrollments) {
            if (e.getStudent().equals(student) && e.getCourse().equals(course)) {
                return e;
            }
        }
        return null;
    }
}
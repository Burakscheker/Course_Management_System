package model;

import java.util.HashSet;
import java.util.Set;

public class Enrollment {
    private Student student;
    private Course course;
    private Set<Lesson> completedLessons;
    private boolean isCertified;
    private String certificatePath; // <-- YENİ: Dosya yolunu burada tutacağız

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.completedLessons = new HashSet<>();
        this.isCertified = false;
    }

    public void markLessonComplete(Lesson lesson) {
        completedLessons.add(lesson);
    }

    public boolean isCourseFinished() {
        return !course.getLessons().isEmpty() &&
                completedLessons.size() == course.getLessons().size();
    }

    public int getProgressPercentage() {
        if (course.getLessons().isEmpty()) return 0;
        return (completedLessons.size() * 100) / course.getLessons().size();
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public boolean isCertified() { return isCertified; }
    public void setCertified(boolean certified) { isCertified = certified; }

    // YENİ GETTER VE SETTER
    public String getCertificatePath() { return certificatePath; }
    public void setCertificatePath(String certificatePath) { this.certificatePath = certificatePath; }
}
package model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String title;
    private Instructor instructor;
    private List<Lesson> lessons;

    public Course(String title, Instructor instructor) {
        this.title = title;
        this.instructor = instructor;
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public List<Lesson> getLessons() { return lessons; }
    public String getTitle() { return title; }
    public Instructor getInstructor() { return instructor; }
}
package model;

public class Lesson {
    private String title;
    private String type; // Video, PDF
    private String filePath; // <-- YENİ: Dosyanın bilgisayardaki adresi

    public Lesson(String title, String type, String filePath) {
        this.title = title;
        this.type = type;
        this.filePath = filePath;
    }

    public String getTitle() { return title; }
    public String getFilePath() { return filePath; } // <-- YENİ GETTER

    @Override
    public String toString() {
        return "[" + type + "] " + title;
    }
}
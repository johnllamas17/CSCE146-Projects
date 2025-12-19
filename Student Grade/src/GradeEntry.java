import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GradeEntry {

    private final StringProperty studentId = new SimpleStringProperty();
    private final StringProperty studentName = new SimpleStringProperty();
    private final StringProperty course = new SimpleStringProperty();
    private final StringProperty itemName = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final DoubleProperty score = new SimpleDoubleProperty();
    private final DoubleProperty maxScore = new SimpleDoubleProperty();

    public GradeEntry(String studentId, String studentName,
                      String course, String itemName, String category,
                      double score, double maxScore) {
        setStudentId(studentId);
        setStudentName(studentName);
        setCourse(course);
        setItemName(itemName);
        setCategory(category);
        setScore(score);
        setMaxScore(maxScore);
    }

    public String getStudentId() { return studentId.get(); }
    public void setStudentId(String value) { studentId.set(value); }
    public StringProperty studentIdProperty() { return studentId; }

    public String getStudentName() { return studentName.get(); }
    public void setStudentName(String value) { studentName.set(value); }
    public StringProperty studentNameProperty() { return studentName; }

    public String getCourse() { return course.get(); }
    public void setCourse(String value) { course.set(value); }
    public StringProperty courseProperty() { return course; }

    public String getItemName() { return itemName.get(); }
    public void setItemName(String value) { itemName.set(value); }
    public StringProperty itemNameProperty() { return itemName; }

    public String getCategory() { return category.get(); }
    public void setCategory(String value) { category.set(value); }
    public StringProperty categoryProperty() { return category; }

    public double getScore() { return score.get(); }
    public void setScore(double value) { score.set(value); }
    public DoubleProperty scoreProperty() { return score; }

    public double getMaxScore() { return maxScore.get(); }
    public void setMaxScore(double value) { maxScore.set(value); }
    public DoubleProperty maxScoreProperty() { return maxScore; }

    public double getPercent() {
        return maxScore.get() <= 0 ? 0.0 : (score.get() / maxScore.get()) * 100.0;
    }

    public String getLetter() {
        return letterForPercent(getPercent());
    }

    public static String letterForPercent(double p) {
        if (p >= 90) return "A";
        if (p >= 80) return "B";
        if (p >= 70) return "C";
        if (p >= 60) return "D";
        return "F";
    }
}
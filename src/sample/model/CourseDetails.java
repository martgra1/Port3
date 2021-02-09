package sample.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CourseDetails {

    private SimpleStringProperty courseName;
    private SimpleIntegerProperty studentGrade;
    private SimpleDoubleProperty averageGrade;

    public String getCourseName() {
        return courseName.get();
    }

    public SimpleStringProperty courseNameProperty() {
        return courseName;
    }

    public int getStudentGrade() {
        return studentGrade.get();
    }

    public SimpleIntegerProperty studentGradeProperty() {
        return studentGrade;
    }

    public double getAverageGrade() {
        return averageGrade.get();
    }

    public SimpleDoubleProperty averageGradeProperty() {
        return averageGrade;
    }

    public CourseDetails(String courseName, Integer studentGrade, Double averageGrade) {
        this.courseName = new SimpleStringProperty(courseName);
        this.studentGrade = new SimpleIntegerProperty(studentGrade);
        this.averageGrade = new SimpleDoubleProperty(averageGrade);
    }
}
package sample.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PersonDetails {
    private SimpleStringProperty personName;
    private SimpleIntegerProperty courseGrade;
    private SimpleDoubleProperty personAverage;

    public String getPersonName() {
        return personName.get();
    }

    public SimpleStringProperty personNameProperty() {
        return personName;
    }

    public int getCourseGrade() {
        return courseGrade.get();
    }

    public SimpleIntegerProperty courseGradeProperty() {
        return courseGrade;
    }

    public double getPersonAverage() {
        return personAverage.get();
    }

    public SimpleDoubleProperty personAverageProperty() {
        return personAverage;
    }

    public PersonDetails(String personName, Integer courseGrade, Double personAverage) {
        this.personName = new SimpleStringProperty(personName);
        this.courseGrade = new SimpleIntegerProperty(courseGrade);
        this.personAverage = new SimpleDoubleProperty(personAverage);
    }
}

package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Course;
import sample.model.CourseDetails;
import sample.model.Person;
import sample.model.PersonDetails;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // Student tab
    @FXML
    private TableView<PersonDetails> personTableView;

    @FXML
    private TableColumn<PersonDetails, String> personNameColumn;
    @FXML
    private TableColumn<PersonDetails, Integer> courseGradeColumn;
    @FXML
    private TableColumn<PersonDetails, Double> personAverageColumn;

    @FXML
    private ChoiceBox<Course> courseChoiceBox;
    private final ObservableList<Course> courseChoiceBoxData = FXCollections.observableArrayList();

    // Course tab
    @FXML
    private TableView<CourseDetails> courseTableView;

    @FXML
    private TableColumn<CourseDetails, String> courseNameColumn;
    @FXML
    private TableColumn<CourseDetails, Integer> studentGradeColumn;
    @FXML
    private TableColumn<CourseDetails, Double> courseAverageColumn;


    @FXML
    private ChoiceBox<Person> personChoiceBox;
    private final ObservableList<Person> personChoiceBoxData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Person choice box
        populatePersonChoiceBox();
        personChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number value, Number newValue) {
                populateCourseTable(newValue);
            }
        });
        personChoiceBox.setItems(personChoiceBoxData);

        // Course choice box
        populateCourseChoiceBox();
        courseChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
                populatePersonTable(newValue);
            }
        });
        courseChoiceBox.setItems(courseChoiceBoxData);
    }

    private void populateCourseChoiceBox() {
        if (courseChoiceBoxData.size() > 0) {
            // List already populated.
            return;
        }

        Connection conn = null;
        try {
            String url = "jdbc:sqlite:D:/Users/Martin/IdeaProjects/Port3/reg.db";
            conn = DriverManager.getConnection(url);

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM course");

            while (rs.next()) {
                courseChoiceBoxData.add(new Course(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            {
                try {
                    assert conn != null;
                    conn.close();
                } catch (SQLException d) {
                }
            }
        }
    }

    private void populatePersonChoiceBox() {
        if (personChoiceBoxData.size() > 0) {
            // List already populated.
            return;
        }

        Connection conn = null;
        try {
            String url = "jdbc:sqlite:D:/Users/Martin/IdeaProjects/Port3/reg.db";
            conn = DriverManager.getConnection(url);

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM person");

            while (rs.next()) {
                personChoiceBoxData.add(new Person(rs.getInt(4), rs.getString(1), rs.getString(2)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            {
                try {
                    assert conn != null;
                    conn.close();
                } catch (SQLException d) {
                }
            }
        }
    }

    private void populatePersonTable(Number chosenCourseIndex) {
        personNameColumn.setCellValueFactory(new PropertyValueFactory<>("personName"));
        courseGradeColumn.setCellValueFactory(new PropertyValueFactory<>("courseGrade"));
        personAverageColumn.setCellValueFactory(new PropertyValueFactory<>("personAverage"));

        ObservableList<PersonDetails> data = FXCollections.observableArrayList();

        Course chosenCourse = courseChoiceBoxData.get(chosenCourseIndex.intValue());

        Connection conn = null;
        try {
            String url = "jdbc:sqlite:D:/Users/Martin/IdeaProjects/Port3/reg.db";
            conn = DriverManager.getConnection(url);

            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT personInfo.firstname, personInfo.lastname, personInfo.grade, personAverage.avgGrade FROM " +
                            "(SELECT person.Id as personId, person.firstname, person.lastname, grade.grade " +
                            "FROM person JOIN grade ON person.Id = grade.personId WHERE grade.courseId = " + chosenCourse.getId() + ") as personInfo" +
                            " JOIN " +
                            "(SELECT personId, AVG(grade.grade) as avgGrade FROM grade GROUP BY personId) as personAverage" +
                            " ON personInfo.personId = personAverage.personId");

            while (rs.next()) {
                data.add(new PersonDetails(String.format("%s %s", rs.getString(1), rs.getString(2)),
                        rs.getInt(3),
                        rs.getDouble(4)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            {
                try {
                    assert conn != null;
                    conn.close();
                } catch (SQLException d) {
                }
            }
        }

        personTableView.setItems(data);
    }

    private void populateCourseTable(Number chosenPersonIndex) {
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        studentGradeColumn.setCellValueFactory(new PropertyValueFactory<>("studentGrade"));
        courseAverageColumn.setCellValueFactory(new PropertyValueFactory<>("averageGrade"));

        ObservableList<CourseDetails> data = FXCollections.observableArrayList();

        Connection conn = null;
        try {
            String url = "jdbc:sqlite:D:/Users/Martin/IdeaProjects/Port3/reg.db";
            conn = DriverManager.getConnection(url);

            Person chosenPerson = personChoiceBoxData.get(chosenPersonIndex.intValue());

            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT courseInfo.name, courseInfo.grade, avgGrade.avgCourseGrade FROM " +
                            "(SELECT * FROM grade JOIN course ON grade.courseId = course.Id WHERE personId = " + chosenPerson.getId() + ") as courseInfo" +
                            " JOIN " +
                            "(SELECT courseId, AVG(grade) as avgCourseGrade FROM grade GROUP BY courseId) as avgGrade " +
                            "ON courseInfo.courseId = avgGrade.courseId");

            while (rs.next()) {
                data.add(new CourseDetails(rs.getString(1), rs.getInt(2), rs.getDouble(3)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            {
                try {
                    assert conn != null;
                    conn.close();
                } catch (SQLException d) {
                }
            }
        }

        courseTableView.setItems(data);
    }
}

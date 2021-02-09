package sample;
import javafx.beans.property.SimpleStringProperty;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.Connection;

import static java.sql.DriverManager.getConnection;

public class DatabaseConnection {


    public static Connection Databaseconn() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:D:/Users/Martin/IdeaProjects/Port3/reg.db";
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) ;
            {
                try {
                    conn.close();
                } catch (SQLException d) {
                    d.printStackTrace();
                }
            }
        }
        return conn;
    }
}

























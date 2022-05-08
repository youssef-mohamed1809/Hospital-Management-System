package com.hospital_management_system;

import java.sql.*;

public class DatabaseManager {
        public Connection connection;
        public Statement statement;
        public ResultSet resultSet;

        public DatabaseManager() throws SQLException {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
            statement = connection.createStatement();
        }
    }

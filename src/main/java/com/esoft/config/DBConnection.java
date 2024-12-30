package com.esoft.config;

import com.esoft.models.Database;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.*;

public class DBConnection {
    // Singleton Connection instance
    private static Connection connection;

    // Get a connection to the database
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        Database.getUrl(),
                        Database.getUsername(),
                        Database.getPassword()
                );
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("MySQL driver not found!", e);
            }
        }
        return connection;
    }

    // Executes an update query (insert, update, delete)
    public static int executeUpdate(@NotNull String query, @NotNull String[] params) {
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            setPreparedStatementParams(ps, params);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error executing update: " + e.getMessage(), e);
        }
    }

    // Executes a select query and returns the ResultSet
    public static ResultSet executeSearch(@NotNull String query) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            return ps.executeQuery(); // Caller must close ResultSet and Statement
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error executing search: " + e.getMessage(), e);
        }
    }

    // Helper method to set the parameters for PreparedStatement
    private static void setPreparedStatementParams(PreparedStatement ps, String[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setString(i + 1, params[i]);
        }
    }

    // Executes SQL script from a file in resources
    public static void executeSQLScriptFromResources(@NotNull String filePath) {
        try (InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IOException("File not found: " + filePath);
            }

            String line;
            StringBuilder sqlQuery = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }

                sqlQuery.append(line).append(" ");

                // Execute the statement if it ends with a semicolon
                if (line.endsWith(";")) {
                    executeUpdate(sqlQuery.toString(), new String[]{});
                    sqlQuery.setLength(0);  // Clear the buffer
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error executing SQL script: " + e.getMessage(), e);
        }
    }

    // Close the singleton connection
    public static void closeConnectio() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing the connection: " + e.getMessage(), e);
        }
    }
}

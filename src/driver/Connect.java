package driver;

import java.sql.*;

public class Connect {

    private static volatile Connect instance = null;

    private final String username = "root";
    private final String password = "";
    private final String database = "stellarfest";
    private final String host = "localhost:3306";
    private final String connectionString = String.format("jdbc:mysql://%s/%s", host, database);

    private Connection connection = null;

    private Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    public static Connect getInstance() {
        if (instance == null) {
            synchronized (Connect.class) {
                if (instance == null) {
                    instance = new Connect();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close connection: " + e.getMessage());
            }
        }
    }

    public Results executeQuery(String query, Object... parameters) {
        try {
            PreparedStatement preparedStatement = prepareStatement(query, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            return new Results(preparedStatement, resultSet);
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
            return null;
        }
    }

    public int executeUpdate(String query, Object... parameters) {
        try (PreparedStatement preparedStatement = prepareStatement(query, parameters)) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Update execution failed: " + e.getMessage());
            return 0;
        }
    }

    private PreparedStatement prepareStatement(String query, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
        return preparedStatement;
    }

}

package ru.vtb.learning.lesson7;

import java.sql.*;

public class Dal {
    private String url;
    private Connection connection;

    public Dal(String url) {
        this.url = url;
    }

    public void createTable(Class entityType){
        try {
            connect();

            // можно проверить, что таблица уще есть
            String createTableScript = ScriptGenerator.getCreateTableScript(entityType);
            Statement statement = connection.createStatement();
            statement.execute(createTableScript);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect();
        }

    }

    private void disconnect() {
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    // Можно взять Object и через рефлексию получить значения полей и подставить их в скрипт, но это
    // очень долго с кучей проверок.
    public void save(Employee entity){
        try {
            connect();
            String createObjectScript = ScriptGenerator.getCreateObjectPrepareStatementScript(entity);
            PreparedStatement preparedStatement = connection.prepareStatement(createObjectScript);
            preparedStatement.setInt(1, entity.id);
            preparedStatement.setString(2, entity.name);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection(url);
    }
}

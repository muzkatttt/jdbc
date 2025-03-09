package com.muzkat.jdbc.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchRunner {
    public static void main(String[] args) throws SQLException {

        long employeeId = 7;
        var deleteEmployeeSql = "DELETE FROM employee WHERE id = " + employeeId;
        Connection connection = null;
        Statement statement = null;

        try {
            connection = ConnectionManager.open();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.addBatch(deleteEmployeeSql);

            var ints = statement.executeBatch();

            connection.commit();
        } catch (Exception e){
            if(connection != null){
                connection.rollback();
            }
            throw e;
        } finally {
            if(connection != null){
                connection.close();
            }
        }

    }
}


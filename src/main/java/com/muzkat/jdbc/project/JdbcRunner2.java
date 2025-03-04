package com.muzkat.jdbc.project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner2 {
    public static void main(String[] args) throws SQLException {
        String employeeId = "2";
        var result = getEmployeeById(employeeId);
        System.out.println(result);

    }

    private static List<Long> getEmployeeById(String employeeId) throws SQLException {
        String sql = """
                SELECT id
                FROM employee
                WHERE id = '%s'
                """.formatted(employeeId);
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(resultSet.getLong("id"));

//                // если идентификатор может быть null, то лучше использовать:
//                result.add(resultSet.getObject("id", Long.class)); // NULL safe
//            }
            }
            return result;
        }
    }
}

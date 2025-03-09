package com.muzkat.jdbc.project;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcPreparedStatementRunner {
    public static void main(String[] args) throws SQLException {
        Long employeeId = 2L;
        var result = getEmployeeById(employeeId);
        System.out.println(result);



        try {
            checkMetaData();
        }
        finally {
            ConnectionPoolManager.closePool();
        }



        /* этот блок кода к примеру из семинара
        var resultGetEmployeeBetween = getEmployeeBetween(LocalDate.of(2010, 10, 1).atStartOfDay(),
                LocalDate.now().atStartOfDay());
        System.out.println(resultGetEmployeeBetween);
    }

    //метод из примера, но в моей таблице employee нет дат
    private static List<Long> getEmployeeBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = """
                SELECT id 
                FROM flight
                WHERE departure date BETWEEN ? AND ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             // установка значений вместо ? в запросе sql
             var preparedStatement = connection.prepareStatement(sql)) {
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // чтобы избежать лишних действий по boxing/unboxing
                // лучше использовать метод resultSet.getObject()
                result.add(resultSet.getLong("id"));
            }
        }
        return new ArrayList<>();
    }
*/
    }

    // данный метод позволяет вывести названия таблиц, схем и т.д. из подключенной БД в консоль
    private static void checkMetaData() throws SQLException {
        try (var connection = ConnectionManager.open()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while(catalogs.next()){
                var catalog = catalogs.getString(1);
                //System.out.println(catalogs.getString(1));
                var schemas = metaData.getSchemas();
                while (schemas.next()) {
                    var schema = schemas.getString("TABLE_SCHEM");
                    var tables = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"});
                    if(schema.equals("public")){
                        while (tables.next()) {
                            System.out.println(tables.getString("TABLE_NAME"));
                        }

                    }
                }
            }
        }
    }

    private static List<Long> getEmployeeById(Long employeeId) throws SQLException {
        // в запросах с PreparedStatement используется ? для обозначения всех неизвестных параметров
        String sql = """
                SELECT id
                FROM employee
                WHERE id = ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            // все методы с PreparedStatement принимают аргументы только присоздании var statement = connection.prepareStatement(sql)
            preparedStatement.setLong(1, employeeId);

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getLong("id"));

            }
            return result;
        }
    }
}

package com.muzkat.jdbc.project;

import org.postgresql.Driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;
        // для DDL команд используется statement.execute(sql)
        // для DML команд - executeUpdate() - для INSERT UPDATE DELETE операций
        // для executeQuery() - для SELECT
//        String sql = """
//                CREATE TABLE IF NOT EXISTS info (
//                id SERIAL PRIMARY KEY,
//                data TEXT NOT NULL
//                );
//                """;
        String sql = """
                INSERT INTO info (data)
                VALUES
                ('autogeneration');
                """;

//        String sql = """
//                UPDATE info
//                SET data = 'TestTest'
//                WHERE id = 5
//                RETURNING *
//                """;

//        String sql = """
//                SELECT *
//                FROM employee;
//                """;

        // для запросов SELECT,
//        try(var connection = ConnectionManager.open();
//            // ResultSet.TYPE_SCROLL_INSENSITIVE - разрешает перемещаться не только вниз, но и по разным направлениям
//            // ResultSet.CONCUR_UPDATABLE - разрешает изменять строки - не рекомендуется
//            // var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
//                var statement = connection.createStatement()){
//            System.out.println(connection.getSchema());
//            System.out.println(connection.getTransactionIsolation());
//            var executeResult = statement.execute(sql);
////            while(executeResult.next()){
////                System.out.println(executeResult.getLong(1)); // нумерация столбцов в sql начиннается с 1
////                System.out.println(executeResult.getString("name_of_column"));
////                System.out.println(executeResult.getDecimal("string_text"));
////            }
//            // вернет количество измененных строк в таблицу info
//                System.out.println(statement.getUpdateCount());
//            //System.out.println(executeResult);
//        };

        // для запросов UPDATE и DELETE используется statement.execute(sql)
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            System.out.println(connection.getSchema());
            System.out.println(connection.getTransactionIsolation());
            var executeResult = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                var generatedId = generatedKeys.getInt("id"); // можно проставить 1 - порядковый номер в ResultSet
                System.out.println(generatedId);
            }
            // вернет количество измененных строк в таблицу info
            System.out.println(statement.getUpdateCount());
            //System.out.println(executeResult);

        }

    }
}

package com.muzkat.jdbc.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;

public class BlobRunner {

    public static void main(String[] args) throws SQLException, IOException {
        saveImage();
        getImage();
    }

    // метод работы с blob  в Oracle
//    private static void saveImage() throws SQLException, IOException {
//        var sql = """
//                UPDATE employee
//                SET image = ?
//                WHERE id = 10
//                """;
//        try(var connection = ConnectionManager.open();
//            var preparedStatement = connection.prepareStatement(sql, 7)){
//            connection.setAutoCommit(false);
//            var blob = connection.createBlob();
//            blob.setBytes(1, Files.readAllBytes(Path.of("resources", "human.png")));
//
//            preparedStatement.setBlob(1, blob);
//            preparedStatement.executeUpdate();
//            connection.commit();
//        }

    private static void saveImage() throws SQLException, IOException {
        var sql = """
                UPDATE employee
                SET image = ?
                WHERE id = 1
                """;
        try(var connection = ConnectionManager.open();
            var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setBytes(
                    1,
                    Files.readAllBytes(Path.of("resources", "human.png")));
            preparedStatement.executeUpdate();
        }

    }

    private static void getImage() throws SQLException, IOException {
        var sql = """
                SELECT image
                FROM employee
                WHERE id = ?
                """;
        try(var connection = ConnectionManager.open();
            var preparedStatement = connection.prepareStatement(sql, 1)){
            preparedStatement.setInt(1, 10);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                var image = resultSet.getBytes("image");
                Files.write(Path.of("resources", "new_human.png"),
                        image, StandardOpenOption.CREATE);
            }
        }
    }

}

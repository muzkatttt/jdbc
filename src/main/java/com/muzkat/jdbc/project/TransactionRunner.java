package com.muzkat.jdbc.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
//        var deleteEmployeeSql = "DELETE FROM employee WHERE id = ?";
//        long employeeId = 9;
//        try (var connection = ConnectionManager.open();
//            var deleteEmployeeStatement = connection.prepareStatement(deleteEmployeeSql, 1)){
//            deleteEmployeeStatement.setLong(1, employeeId);
//
//            deleteEmployeeStatement.executeUpdate();
//        }
//    }

        // код из примера с билетами
        long flightId = 9;
        var deleteFlightSql = "DELETE FROM flight WHERE id = ?";
        var deleteTicketSql = "DELETE FROM ticket WHERE flight_id = ?";
        Connection connection = null;
        PreparedStatement deleteFlightStatement = null;
        PreparedStatement deleteTicketStatement = null;
        try {
            connection = ConnectionManager.open();
            deleteFlightStatement = connection.prepareStatement(deleteFlightSql);
            deleteTicketStatement =connection.prepareStatement(deleteTicketSql);


            deleteFlightStatement.setLong(1, flightId);
            deleteTicketStatement.setLong(1, flightId);

            deleteTicketStatement.executeUpdate();
            if(true){
                throw new RuntimeException("bug...");
            }
            deleteFlightStatement.executeUpdate();
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
            if(deleteFlightStatement !=null){
                deleteFlightStatement.close();
            }
            if(deleteTicketStatement != null) {
                deleteTicketStatement.close();
            }
        }

    }
}

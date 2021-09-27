package com.dmdev.jdbc.starter;


import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
      /*  Long flightId = 3L;
        var result = getTicetsIdByFlightId(flightId);
        System.out.println(result);*/
        //   var result = getFlightsBettwen(LocalDate.of(2020, 6, 13).atStartOfDay(), LocalDate.of(2020, 6, 15).atTime(11, 15));
        //  System.out.println(result);
       try {
           checkMetaData();
       }finally {
           ConnectionManager.closePool();
       }

    }

    public static void checkMetaData() throws SQLException {
        try (var connection = ConnectionManager.get()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                var catalog = catalogs.getString(1);
                var schemas = metaData.getSchemas();
                while (schemas.next()) {
                    var schema = schemas.getString("TABLE_SCHEM");
                    var tables = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"});
                    if (schema.equals("public")) {
                        while (tables.next()) {
                            System.out.println(tables.getString("TABLE_NAME"));

                        }
                    }
                }
            }

        }
    }
    private static List<Long> getFlightsBettwen(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = """
                SELECT id 
                FROM flight 
                WHERE departure_date BETWEEN ? AND ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFetchSize(5);
            preparedStatement.setQueryTimeout(5);
            preparedStatement.setMaxRows(4);
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class));
            }

        }
        return result;
    }

    private static List<Long> getTicetsIdByFlightId(Long flightId) throws SQLException {
        String sql = """
                              
                SELECT id 
                FROM ticket 
                WHERE flight_id = ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, flightId);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class));
            }

        }
        return result;

    }

}
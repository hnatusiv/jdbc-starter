package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Blob;
import java.sql.SQLException;

import static java.nio.file.Files.readAllBytes;

public class BlobRunner {
    public static void main(String[] args) throws SQLException, IOException {
        //binary large object (blob) , for Postgres  - bytea
        //certalarge large object( clob),for Postgres  - TEXT

       // saveImage();
        getImage();
    }
    private  static void getImage() throws SQLException, IOException {
        var sql = """
                SELECT image
                FROM aircraft
                WHERE id= ?
                """;
        try (var connection   =  ConnectionManager.get();
       var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,1);
        var resultSet   =preparedStatement.executeQuery();
        if (resultSet.next()){
         var image  = resultSet.getBytes("image");
          Files.write(Path.of("resources", " boing777_new.jpg"), image, StandardOpenOption.CREATE);

        }

        }

    }
    private static void saveImage() throws SQLException, IOException {
        var sql = """
                UPDATE aircraft
                SET image =?
                WHERE id=1
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
              preparedStatement.setBytes(1,Files.readAllBytes(Path.of("resources", "Boeing777-200.jpg")));
              preparedStatement.executeUpdate();


        }
    }

  /* private static void saveImage() throws SQLException, IOException {
        var sql = """
                UPDATE aircraft
                SET image =?
                WHERE id=1
                """;
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            var blob = connection.createBlob();
            blob.setBytes(1, Files.readAllBytes(Path.of("resources", "Boeing777-200.jpg")));

            preparedStatement.setBlob(1, blob);
            preparedStatement.executeUpdate();
          connection.commit();
        }
    }*/
}

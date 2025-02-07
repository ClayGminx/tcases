package org.cornutum.tcases.openapi.db;

import java.sql.*;

public class SqliteQuery {

    public static <T> T getRandomValue(String table, String field) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.setQueryTimeout(30);

            String sql = String.format("SELECT %s FROM %s ORDER BY RANDOM() LIMIT 1", field, table);
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return (T) rs.getObject(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败！", e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:/Users/joseph/Downloads/tcases/petstore.db");
    }
}

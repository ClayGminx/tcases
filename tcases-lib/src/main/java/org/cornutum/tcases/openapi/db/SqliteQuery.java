package org.cornutum.tcases.openapi.db;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SqliteQuery {

    public static Object getRandomValue(String table, String field) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.setQueryTimeout(30);

            String sql = String.format("SELECT %s FROM %s ORDER BY RANDOM() LIMIT 1", field, table);
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败！", e);
        }
    }

    /**
     * 从数据库查询不存在的值
     */
    public static Long getNotExistingValue(String table, String field, Long min, Long max) {
        // 从大到小，一个一个区间地找
        min = min == null ? Long.MIN_VALUE : min;
        max = max == null ? Long.MAX_VALUE : max;
        long length = 100, right = max, left = right - length;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.setQueryTimeout(30);

            while (left >= min) {
                String sql = String.format("SELECT %s FROM %s WHERE %s >= %s AND %s <= %s",
                        field, table, field, left, field, right);
                ResultSet rs = statement.executeQuery(sql);
                Set<Long> values = new HashSet<>();
                while (rs.next()) {
                    values.add(rs.getLong(1));
                }
                for (long i = left; i <= right; i++) {
                    if (!values.contains(i)) {
                        return i;
                    }
                }

                right -= length;
                left -= length;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败！", e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:/Users/joseph/Downloads/tcases/petstore.db");
    }

    public static void main(String[] args) {
        Long l = getNotExistingValue("pet", "id", 1L, 200L);
        System.out.println(l);
    }
}

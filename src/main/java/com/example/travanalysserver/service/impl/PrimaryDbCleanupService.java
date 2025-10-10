package com.example.travanalysserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrimaryDbCleanupService {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void truncateAllExceptEmailAndSyncMeta() {
        truncateAllExcept(Set.of("emailadress", "sync_meta", "likes"));
    }

    private void truncateAllExcept(Set<String> keep) {
        try (Connection c = dataSource.getConnection()) {
            String product = c.getMetaData().getDatabaseProductName().toLowerCase(Locale.ROOT);

            List<String> tables = listTables(c, product).stream()
                    .map(String::toLowerCase)
                    .filter(t -> !keep.contains(t))
                    .filter(t -> !t.startsWith("flyway") && !t.equals("hibernate_sequence"))
                    .collect(Collectors.toList());

            if (tables.isEmpty()) return;

            if (product.contains("postgresql")) {
                String joined = tables.stream()
                        .map(t -> "\"" + t + "\"")
                        .collect(Collectors.joining(", "));
                jdbcTemplate.execute("TRUNCATE TABLE " + joined + " RESTART IDENTITY CASCADE");
            } else if (product.contains("mysql") || product.contains("mariadb")) {
                jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0");
                for (String t : tables) {
                    jdbcTemplate.execute("TRUNCATE TABLE `" + t + "`");
                }
                jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=1");
            } else if (product.contains("h2")) {
                for (String t : tables) {
                    jdbcTemplate.execute("TRUNCATE TABLE " + t);
                }
            } else { // fallback – funkar “överallt”, men långsammare
                for (String t : tables) {
                    jdbcTemplate.execute("DELETE FROM " + t);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Cleanup failed", ex);
        }
    }

    private List<String> listTables(Connection c, String product) throws SQLException {
        String sql;
        if (product.contains("postgresql")) {
            sql = "select tablename from pg_tables where schemaname = current_schema()";
        } else if (product.contains("mysql") || product.contains("mariadb")) {
            sql = "select table_name from information_schema.tables where table_schema = database()";
        } else if (product.contains("h2")) {
            sql = "select table_name from information_schema.tables where table_schema = schema()";
        } else {
            sql = null;
        }

        if (sql == null) {
            List<String> out = new ArrayList<>();
            try (ResultSet rs = c.getMetaData().getTables(null, null, "%", new String[]{"TABLE"})) {
                while (rs.next()) out.add(rs.getString("TABLE_NAME"));
            }
            return out;
        }

        try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            List<String> out = new ArrayList<>();
            while (rs.next()) out.add(rs.getString(1));
            return out;
        }
    }
}


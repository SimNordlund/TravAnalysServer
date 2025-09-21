package com.example.travanalysserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate; //Changed!
import org.springframework.stereotype.Service;   //Changed!
import org.springframework.transaction.annotation.Transactional; //Changed!

import javax.sql.DataSource; //Changed!
import java.sql.*;           //Changed!
import java.util.*;          //Changed!
import java.util.stream.Collectors; //Changed!

@Service //Changed!
@RequiredArgsConstructor //Changed!
public class PrimaryDbCleanupService { //Changed!

    private final DataSource dataSource;   //Changed!
    private final JdbcTemplate jdbcTemplate; //Changed!

    @Transactional //Changed!
    public void truncateAllExceptEmailAndSyncMeta() { //Changed!
        truncateAllExcept(Set.of("emailadress", "sync_meta")); //Changed!
    }

    private void truncateAllExcept(Set<String> keep) { //Changed!
        try (Connection c = dataSource.getConnection()) { //Changed!
            String product = c.getMetaData().getDatabaseProductName().toLowerCase(Locale.ROOT); //Changed!

            List<String> tables = listTables(c, product).stream() //Changed!
                    .map(String::toLowerCase) //Changed!
                    .filter(t -> !keep.contains(t)) //Changed!
                    .filter(t -> !t.startsWith("flyway") && !t.equals("hibernate_sequence")) //Changed!
                    .collect(Collectors.toList()); //Changed!

            if (tables.isEmpty()) return; //Changed!

            if (product.contains("postgresql")) { //Changed!
                String joined = tables.stream()
                        .map(t -> "\"" + t + "\"")
                        .collect(Collectors.joining(", ")); //Changed!
                jdbcTemplate.execute("TRUNCATE TABLE " + joined + " RESTART IDENTITY CASCADE"); //Changed!
            } else if (product.contains("mysql") || product.contains("mariadb")) { //Changed!
                jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0"); //Changed!
                for (String t : tables) { //Changed!
                    jdbcTemplate.execute("TRUNCATE TABLE `" + t + "`"); //Changed!
                }
                jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=1"); //Changed!
            } else if (product.contains("h2")) { //Changed!
                for (String t : tables) { //Changed!
                    jdbcTemplate.execute("TRUNCATE TABLE " + t); //Changed!
                }
            } else { // fallback – funkar “överallt”, men långsammare //Changed!
                for (String t : tables) { //Changed!
                    jdbcTemplate.execute("DELETE FROM " + t); //Changed!
                }
            }
        } catch (SQLException ex) { //Changed!
            throw new RuntimeException("Cleanup failed", ex); //Changed!
        }
    }

    private List<String> listTables(Connection c, String product) throws SQLException { //Changed!
        String sql;
        if (product.contains("postgresql")) { //Changed!
            sql = "select tablename from pg_tables where schemaname = current_schema()";
        } else if (product.contains("mysql") || product.contains("mariadb")) { //Changed!
            sql = "select table_name from information_schema.tables where table_schema = database()";
        } else if (product.contains("h2")) { //Changed!
            sql = "select table_name from information_schema.tables where table_schema = schema()";
        } else {
            sql = null; //Changed!
        }

        if (sql == null) { //Changed!
            List<String> out = new ArrayList<>(); //Changed!
            try (ResultSet rs = c.getMetaData().getTables(null, null, "%", new String[]{"TABLE"})) { //Changed!
                while (rs.next()) out.add(rs.getString("TABLE_NAME")); //Changed!
            }
            return out; //Changed!
        }

        try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) { //Changed!
            List<String> out = new ArrayList<>(); //Changed!
            while (rs.next()) out.add(rs.getString(1)); //Changed!
            return out; //Changed!
        }
    }
}


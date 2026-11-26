package org.example.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBD {

    private static final Properties config = carregarConfig();

    private static Properties carregarConfig() {
        Properties props = new Properties();
        try (InputStream in = ConexaoBD.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException("Arquivo db.properties não encontrado em src/main/resources.");
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler o db.properties.", e);
        }
        return props;
    }

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(
                config.getProperty("db.url"),
                config.getProperty("db.usuario"),
                config.getProperty("db.senha"));
    }
}
package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL = "jdbc:mysql://localhost:3306/gerador_provas";
    private static final String USUARIO = "root";
    private static final String SENHA = "brn4headaB_1@";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
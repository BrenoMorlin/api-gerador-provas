package org.example.dao;

import org.example.model.Questao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestaoDAO {

    public void salvar(Questao questao) throws SQLException {
        String sql = "INSERT INTO questao (enunciado, materia, alternativa_a, alternativa_b, alternativa_c, alternativa_d, resposta_correta) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, questao.getEnunciado());
            stmt.setString(2, questao.getMateria());
            stmt.setString(3, questao.getAlternativaA());
            stmt.setString(4, questao.getAlternativaB());
            stmt.setString(5, questao.getAlternativaC());
            stmt.setString(6, questao.getAlternativaD());
            stmt.setString(7, questao.getRespostaCorreta());

            stmt.executeUpdate();
        }
    }

    public List<Questao> listarTodas() throws SQLException {
        List<Questao> questoes = new ArrayList<>();
        String sql = "SELECT * FROM questao";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                questoes.add(montarQuestao(rs));
            }
        }
        return questoes;
    }

    public Questao buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM questao WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarQuestao(rs);
                }
            }
        }
        return null;
    }

    public void atualizar(Questao questao) throws SQLException {
        String sql = "UPDATE questao SET enunciado=?, materia=?, alternativa_a=?, alternativa_b=?, alternativa_c=?, alternativa_d=?, resposta_correta=? WHERE id=?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, questao.getEnunciado());
            stmt.setString(2, questao.getMateria());
            stmt.setString(3, questao.getAlternativaA());
            stmt.setString(4, questao.getAlternativaB());
            stmt.setString(5, questao.getAlternativaC());
            stmt.setString(6, questao.getAlternativaD());
            stmt.setString(7, questao.getRespostaCorreta());
            stmt.setInt(8, questao.getId());

            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM questao WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public List<Questao> listarPorMateria(String materia, int quantidade) throws SQLException {
        List<Questao> questoes = new ArrayList<>();
        String sql = "SELECT * FROM questao WHERE materia = ? ORDER BY RAND() LIMIT ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, materia);
            stmt.setInt(2, quantidade);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questoes.add(montarQuestao(rs));
                }
            }
        }
        return questoes;
    }

    public List<String> listarMaterias() throws SQLException {
        List<String> materias = new ArrayList<>();
        String sql = "SELECT DISTINCT materia FROM questao ORDER BY materia";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                materias.add(rs.getString("materia"));
            }
        }
        return materias;
    }


    private Questao montarQuestao(ResultSet rs) throws SQLException {
        Questao questao = new Questao();
        questao.setId(rs.getInt("id"));
        questao.setEnunciado(rs.getString("enunciado"));
        questao.setMateria(rs.getString("materia"));
        questao.setAlternativaA(rs.getString("alternativa_a"));
        questao.setAlternativaB(rs.getString("alternativa_b"));
        questao.setAlternativaC(rs.getString("alternativa_c"));
        questao.setAlternativaD(rs.getString("alternativa_d"));
        questao.setRespostaCorreta(rs.getString("resposta_correta"));
        return questao;
    }
}
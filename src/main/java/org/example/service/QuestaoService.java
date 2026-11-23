package org.example.service;

import org.example.dao.QuestaoDAO;
import org.example.model.Questao;

import java.sql.SQLException;
import java.util.List;

public class QuestaoService {

    private final QuestaoDAO dao = new QuestaoDAO();

    public void salvar(Questao questao) throws SQLException {
        validar(questao);
        dao.salvar(questao);
    }

    public List<Questao> listarTodas() throws SQLException {
        return dao.listarTodas();
    }

    public List<Questao> gerarProva(String materia, int quantidade) throws SQLException {
        return dao.listarPorMateria(materia, quantidade);
    }

    public List<String> listarMaterias() throws SQLException {
        return dao.listarMaterias();
    }

    public Questao buscarPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public void atualizar(Questao questao) throws SQLException {
        validar(questao);
        dao.atualizar(questao);
    }

    public void excluir(int id) throws SQLException {
        dao.excluir(id);
    }

    private void validar(Questao questao) {
        if (questao.getEnunciado() == null || questao.getEnunciado().isBlank()) {
            throw new IllegalArgumentException("O enunciado não pode ficar vazio.");
        }
        if (questao.getMateria() == null || questao.getMateria().isBlank()) {
            throw new IllegalArgumentException("A matéria não pode ficar vazia.");
        }
        if (questao.getRespostaCorreta() == null ||
                !questao.getRespostaCorreta().matches("[ABCDabcd]")) {
            throw new IllegalArgumentException("A resposta correta deve ser A, B, C ou D.");
        }
    }
}

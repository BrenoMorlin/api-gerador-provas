package org.example.controller;

import org.example.model.Questao;
import org.example.service.QuestaoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class QuestaoController extends HttpServlet {

    private final QuestaoService service = new QuestaoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        if (acao == null) acao = "listar";

        try {
            switch (acao) {
                case "novo" -> request.getRequestDispatcher("/formulario.jsp").forward(request, response);

                case "editar" -> {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Questao questao = service.buscarPorId(id);
                    request.setAttribute("questao", questao);
                    request.getRequestDispatcher("/formulario.jsp").forward(request, response);
                }

                case "excluir" -> {
                    int id = Integer.parseInt(request.getParameter("id"));
                    service.excluir(id);
                    response.sendRedirect("questoes");
                }
                case "gerar" -> {
                    List<String> materias = service.listarMaterias();
                    request.setAttribute("materias", materias);
                    request.getRequestDispatcher("/gerarProva.jsp").forward(request, response);
                }


                default -> {
                    List<Questao> questoes = service.listarTodas();
                    request.setAttribute("questoes", questoes);
                    request.getRequestDispatcher("/listagem.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao acessar o banco de dados", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if ("gerarProva".equals(acao)) {
            try {
                String materia = request.getParameter("materia");
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));

                List<Questao> prova = service.gerarProva(materia, quantidade);
                request.setAttribute("prova", prova);
                request.setAttribute("materia", materia);
                request.getRequestDispatcher("/prova.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Erro ao acessar o banco de dados", e);
            }
            return;
        }
        String idParam = request.getParameter("id");

        Questao questao = new Questao();
        questao.setEnunciado(request.getParameter("enunciado"));
        questao.setMateria(request.getParameter("materia"));
        questao.setAlternativaA(request.getParameter("alternativaA"));
        questao.setAlternativaB(request.getParameter("alternativaB"));
        questao.setAlternativaC(request.getParameter("alternativaC"));
        questao.setAlternativaD(request.getParameter("alternativaD"));
        questao.setRespostaCorreta(request.getParameter("respostaCorreta"));

        try {
            if (idParam != null && !idParam.isBlank()) {
                questao.setId(Integer.parseInt(idParam));
                service.atualizar(questao);
            } else {
                service.salvar(questao);
            }
            response.sendRedirect("questoes");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("questao", questao);
            request.getRequestDispatcher("/formulario.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Erro ao acessar o banco de dados", e);
        }
    }
}
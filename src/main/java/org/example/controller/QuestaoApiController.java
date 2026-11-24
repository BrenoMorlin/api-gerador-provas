package org.example.controller;

import com.google.gson.Gson;
import org.example.model.Questao;
import org.example.service.QuestaoService;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestaoApiController extends HttpServlet {

    private final QuestaoService service = new QuestaoService();
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)

            throws IOException {

        String path = request.getPathInfo();

        try {
            if (path == null || path.equals("/")) {
                List<Questao> questoes = service.listarTodas();
                enviarJson(response, 200, questoes);
            } else {
                int id = Integer.parseInt(path.substring(1));
                Questao questao = service.buscarPorId(id);

                if (questao == null) {
                    enviarErro(response, 404, "Questão não encontrada.");
                } else {
                    enviarJson(response, 200, questao);
                }
            }
        } catch (NumberFormatException e) {
            enviarErro(response, 400, "O id precisa ser um número.");
        } catch (SQLException e) {
            enviarErro(response, 500, "Erro ao acessar o banco de dados.");
        }


    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Questao questao = gson.fromJson(request.getReader(), Questao.class);
            if (questao == null) {
                enviarErro(response, 400, "Corpo da requisição vazio.");
                return;
            }
            service.salvar(questao);
            enviarJson(response, 201, questao);
        } catch (IllegalArgumentException e) {
            enviarErro(response, 400, e.getMessage());
        } catch (JsonSyntaxException e) {
            enviarErro(response, 400, "JSON inválido.");
        } catch (SQLException e) {
            enviarErro(response, 500, "Erro ao acessar o banco de dados.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String path = request.getPathInfo();
        if (path == null || path.equals("/")) {
            enviarErro(response, 400, "Informe o id na URL.");
            return;
        }
        try {
            int id = Integer.parseInt(path.substring(1));
            if (service.buscarPorId(id) == null) {
                enviarErro(response, 404, "Questão não encontrada.");
                return;
            }
            Questao questao = gson.fromJson(request.getReader(), Questao.class);
            if (questao == null) {
                enviarErro(response, 400, "Corpo da requisição vazio.");
                return;
            }
            questao.setId(id);
            service.atualizar(questao);
            enviarJson(response, 200, questao);
        } catch (NumberFormatException e) {
            enviarErro(response, 400, "O id precisa ser um número.");
        } catch (IllegalArgumentException e) {
            enviarErro(response, 400, e.getMessage());
        } catch (JsonSyntaxException e) {
            enviarErro(response, 400, "JSON inválido.");
        } catch (SQLException e) {
            enviarErro(response, 500, "Erro ao acessar o banco de dados.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String path = request.getPathInfo();
        if (path == null || path.equals("/")) {
            enviarErro(response, 400, "Informe o id na URL.");
            return;
        }
        try {
            int id = Integer.parseInt(path.substring(1));
            if (service.buscarPorId(id) == null) {
                enviarErro(response, 404, "Questão não encontrada.");
                return;
            }
            service.excluir(id);
            response.setStatus(204);
        } catch (NumberFormatException e) {
            enviarErro(response, 400, "O id precisa ser um número.");
        } catch (SQLException e) {
            enviarErro(response, 500, "Erro ao acessar o banco de dados.");
        }
    }

    private void enviarJson(HttpServletResponse response, int status, Object corpo)
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(corpo));
    }

    private void enviarErro(HttpServletResponse response, int status, String mensagem)
            throws IOException {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", mensagem);
        enviarJson(response, status, erro);
    }
}
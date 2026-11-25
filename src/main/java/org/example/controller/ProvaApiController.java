package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.Questao;
import org.example.service.QuestaoService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProvaApiController extends HttpServlet {

    private final QuestaoService service = new QuestaoService();
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String materia = request.getParameter("materia");
        String quantidadeParam = request.getParameter("quantidade");

        if (materia == null || materia.isBlank() || quantidadeParam == null) {
            enviarErro(response, 400, "Informe os parâmetros materia e quantidade.");
            return;
        }

        try {
            int quantidade = Integer.parseInt(quantidadeParam);
            if (quantidade <= 0) {
                enviarErro(response, 400, "A quantidade precisa ser maior que zero.");
                return;
            }

            List<Questao> prova = service.gerarProva(materia, quantidade);

            if (prova.isEmpty()) {
                enviarErro(response, 404, "Nenhuma questão encontrada para essa matéria.");
            } else {
                enviarJson(response, 200, prova);
            }
        } catch (NumberFormatException e) {
            enviarErro(response, 400, "A quantidade precisa ser um número.");
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
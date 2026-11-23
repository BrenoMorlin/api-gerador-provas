<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Questões Cadastradas</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        a { margin-right: 10px; }
    </style>
</head>
<body>

<h1>Questões Cadastradas</h1>

<a href="questoes?acao=novo">+ Nova Questão</a>
<a href="questoes?acao=gerar">Gerar Prova</a>

<table>
    <tr>
        <th>Enunciado</th>
        <th>Matéria</th>
        <th>Resposta Correta</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="questao" items="${questoes}">
        <tr>
            <td>${questao.enunciado}</td>
            <td>${questao.materia}</td>
            <td>${questao.respostaCorreta}</td>
            <td>
                <a href="questoes?acao=editar&id=${questao.id}">Editar</a>
                <a href="questoes?acao=excluir&id=${questao.id}"
                   onclick="return confirm('Tem certeza que deseja excluir?')">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
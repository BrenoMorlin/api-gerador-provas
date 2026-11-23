<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prova Gerada</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .questao { margin-bottom: 25px; }
        .alternativas { margin-left: 20px; }
    </style>
</head>
<body>

<h1>Prova de ${materia}</h1>

<c:forEach var="questao" items="${prova}" varStatus="loop">
    <div class="questao">
        <p><strong>${loop.index + 1}. ${questao.enunciado}</strong></p>
        <div class="alternativas">
            <p>A) ${questao.alternativaA}</p>
            <p>B) ${questao.alternativaB}</p>
            <p>C) ${questao.alternativaC}</p>
            <p>D) ${questao.alternativaD}</p>
        </div>
    </div>
</c:forEach>

<a href="questoes?acao=gerar">Gerar outra prova</a>
<a href="questoes">Voltar para listagem</a>

</body>
</html>
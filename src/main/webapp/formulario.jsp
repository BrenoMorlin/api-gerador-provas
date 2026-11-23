<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro de Questão</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; max-width: 500px; }
        label { display: block; margin-top: 12px; font-weight: bold; }
        input[type=text], select, textarea { width: 100%; padding: 6px; margin-top: 4px; }
        button { margin-top: 16px; padding: 8px 16px; }
        .erro { color: red; font-weight: bold; }
    </style>
</head>
<body>

<h1>${questao != null ? "Editar Questão" : "Nova Questão"}</h1>

<c:if test="${erro != null}">
    <p class="erro">${erro}</p>
</c:if>

<form method="post" action="questoes">

    <input type="hidden" name="id" value="${questao.id}">

    <label>Enunciado</label>
    <textarea name="enunciado" rows="3" required>${questao.enunciado}</textarea>

    <label>Matéria</label>
    <input type="text" name="materia" value="${questao.materia}" required>

    <label>Alternativa A</label>
    <input type="text" name="alternativaA" value="${questao.alternativaA}" required>

    <label>Alternativa B</label>
    <input type="text" name="alternativaB" value="${questao.alternativaB}" required>

    <label>Alternativa C</label>
    <input type="text" name="alternativaC" value="${questao.alternativaC}" required>

    <label>Alternativa D</label>
    <input type="text" name="alternativaD" value="${questao.alternativaD}" required>

    <label>Resposta Correta</label>
    <select name="respostaCorreta" required>
        <option value="">Selecione</option>
        <option value="A" ${questao.respostaCorreta == "A" ? "selected" : ""}>A</option>
        <option value="B" ${questao.respostaCorreta == "B" ? "selected" : ""}>B</option>
        <option value="C" ${questao.respostaCorreta == "C" ? "selected" : ""}>C</option>
        <option value="D" ${questao.respostaCorreta == "D" ? "selected" : ""}>D</option>
    </select>

    <button type="submit">Salvar</button>
</form>

<br>
<a href="questoes">Voltar para a listagem</a>

</body>
</html>
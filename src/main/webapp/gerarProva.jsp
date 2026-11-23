<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gerar Prova</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        label { display: block; margin-top: 15px; }
        select, input { padding: 6px; margin-top: 4px; }
        button { margin-top: 20px; padding: 8px 16px; }
    </style>
</head>
<body>

<h1>Gerar Prova</h1>

<form action="questoes" method="post">
    <input type="hidden" name="acao" value="gerarProva">

    <label>Matéria:
        <select name="materia" required>
            <c:forEach var="m" items="${materias}">
                <option value="${m}">${m}</option>
            </c:forEach>
        </select>
    </label>

    <label>Quantidade de questões:
        <input type="number" name="quantidade" min="1" required>
    </label>

    <button type="submit">Gerar</button>
</form>

<br>
<a href="questoes">Voltar para listagem</a>

</body>
</html>
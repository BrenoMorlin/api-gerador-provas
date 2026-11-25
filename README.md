# API do Gerador de Provas

API REST em Java para cadastrar questões de prova, feita com Servlets, padrão MVC e camadas Controller, Service e DAO. Os dados ficam em um banco MySQL.

## Tecnologias

- Java 21
- Maven
- Servlets (javax.servlet) com Tomcat 7 embutido (plugin do Maven)
- MySQL
- Gson (conversão de objetos para JSON)

## Estrutura do projeto

- `model`: a classe `Questao`, que representa uma questão
- `dao`: acesso ao banco de dados (`QuestaoDAO` e `ConexaoBD`)
- `service`: regras de negócio e validações (`QuestaoService`)
- `controller`: recebe as requisições e devolve JSON (`QuestaoApiController`)

## Como rodar

1. Instale o JDK 21 e o MySQL.
2. Crie o banco e a tabela:

```sql
CREATE DATABASE gerador_provas;
USE gerador_provas;

CREATE TABLE questao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    enunciado TEXT NOT NULL,
    materia VARCHAR(50) NOT NULL,
    alternativa_a VARCHAR(255) NOT NULL,
    alternativa_b VARCHAR(255) NOT NULL,
    alternativa_c VARCHAR(255) NOT NULL,
    alternativa_d VARCHAR(255) NOT NULL,
    resposta_correta CHAR(1) NOT NULL
);
```

3. Em `ConexaoBD.java`, ajuste o usuário e a senha do seu MySQL.
4. Suba o servidor: `mvn tomcat7:run`
5. A API fica em `http://localhost:8080/api/questoes`

## Endpoints

| Método | Rota | O que faz | Status |
|---|---|---|---|
| GET | `/api/questoes` | Lista todas as questões | 200 |
| GET | `/api/questoes/{id}` | Busca uma questão | 200, 404 |
| POST | `/api/questoes` | Cadastra uma questão | 201, 400 |
| PUT | `/api/questoes/{id}` | Atualiza uma questão | 200, 400, 404 |
| DELETE | `/api/questoes/{id}` | Exclui uma questão | 204, 404 |
| GET | `/api/provas?materia=X&quantidade=N` | Gera uma prova com questões sorteadas da matéria | 200, 400, 404 |
### Exemplo de corpo (POST e PUT)

```json
{
  "enunciado": "Qual é a capital do Brasil?",
  "materia": "Geografia",
  "alternativaA": "São Paulo",
  "alternativaB": "Brasília",
  "alternativaC": "Rio de Janeiro",
  "alternativaD": "Salvador",
  "respostaCorreta": "B"
}
```

### Erros

Os erros voltam em JSON, por exemplo:

```json
{ "erro": "Questão não encontrada." }
```

A `respostaCorreta` precisa ser A, B, C ou D, e o enunciado e a matéria não podem ficar vazios (status 400).    
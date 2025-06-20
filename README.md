# 📚 EstudAI - Plataforma de Aprendizado com IA

EstudAI é uma API backend desenvolvida em **Java com Spring Boot** que permite aos usuários enviar textos ou documentos (.txt/.pdf), e em seguida:

- Gera um **resumo automático** do conteúdo
- Cria **perguntas de múltipla escolha**
- Armazena tudo em um banco de dados para consultas futuras

---

## ⚙️ Tecnologias Utilizadas

- Java 17+
- Spring Boot
    - Web
    - Data JPA
- MySQL
- Apache Tika (extração de texto de arquivos)
- OpenAI GPT-3.5 (via API)
- WebClient (HTTP client do Spring)
- Lombok

---

## 🧠 Funcionalidades

- [x] Upload de texto ou arquivo PDF/TXT
- [x] Extração e análise do conteúdo
- [x] Resumo gerado com IA
- [x] Geração de perguntas com alternativas
- [x] Persistência de dados com JPA
- [x] Endpoints REST para leitura

---

## 📦 Estrutura do Projeto

```
src/
├── controller
├── service
├── domain
├── repository
├── dto
```
---

## 🔐 Requisitos

- Java 17+
- Maven
- MySQL
- Conta com API Key da OpenAI

---

## 🚀 Como Executar

### 1. Clone o projeto
```bash
git clone https://github.com/seuusuario/estudai.git
cd estudai
```

### 2. Configure o banco de dados no application.properites
spring.datasource.url<br>
spring.datasource.url.username<br>
spring.datasource.url.password<br>

openai.api-key=sua-api-key<br>
openai.api-key.model=escolha um modelo<br>

## 📬 Endpoints
### 🔹 `POST /api/conteudos`

Envia um conteúdo em **formato JSON** para ser processado (resumo + perguntas).

#### 📥 Corpo da requisição (`application/json`)
```json
{
  "titulo": "A Revolução Francesa",
  "texto": "Texto completo aqui..."
}
```

#### 📤`Resposta (200 OK)`

```json
{
  "id": 1,
  "titulo": "A Revolução Francesa",
  "resumo": "Resumo gerado pela IA...",
  "textoOriginal": "Texto completo...",
  "criadoEm": "2025-06-19T10:55:00",
  "perguntas": [
    {
      "enunciado": "Quem liderou o período do Terror?",
      "alternativaCorreta": "Robespierre",
      "alternativasIncorretas": ["Napoleão", "Luís XVI", "Voltaire"]
    }
  ]
}
```

### `🔹 POST /api/conteudos/upload`

Envia um arquivo PDF ou TXT com título via form-data.

#### 📥 Requisição

**Content-Type:** `multipart/form-data`

| Campo   | Tipo    | Obrigatório | Descrição             |
|---------|---------|-------------|-----------------------|
| titulo  | Texto   | Sim         | Título do conteúdo    |
| arquivo | Arquivo | Sim         | Arquivo .pdf ou .txt  |

#### 📤`Resposta (200 OK)`

```json
{
  "id": 2,
  "titulo": "Documento PDF Enviado",
  "resumo": "...",
  "textoOriginal": "...",
  "criadoEm": "2025-06-19T11:00:00",
  "perguntas": [ ... ]
}
```

### `🔹 GET /api/conteudos`
Retorna uma lista com todos os conteúdos processados de forma resumida.
#### 📤`Resposta (200 OK)`
```json
[
  {
    "id": 1,
    "titulo": "A Revolução Francesa",
    "criadoEm": "2025-06-19T10:55:00"
  },
  {
    "id": 2,
    "titulo": "Documento PDF Enviado",
    "criadoEm": "2025-06-19T11:00:00"
  }
]
```

### `🔹 GET /api/conteudos/{id}`
Retorna o conteúdo completo, incluindo resumo e perguntas.

#### `🔗 Parâmetro de caminho`
- id: ID do conteúdo salvo (ex: 1, 2)
#### 📤`Resposta (200 OK)`
```json
{
  "titulo": "A Revolução Francesa",
  "textoOriginal": "...",
  "resumo": "...",
  "criadoEm": "2025-06-19T10:55:00",
  "perguntas": [
    {
      "enunciado": "Quem liderou o período do Terror?",
      "alternativaCorreta": "Robespierre",
      "alternativasIncorretas": [
        "Napoleão",
        "Luís XVI",
        "Voltaire"
      ]
    }
  ]
}
```
# 🎓 Academic System API

API RESTful para gerenciamento de um sistema acadêmico, permitindo o cadastro de alunos e disciplinas, matrículas, atribuição de notas e autenticação de usuários. Este projeto foi desenvolvido como parte do Assessment para a disciplina de Desenvolvimento de Serviços com Spring Boot.

![Java](https://img.shields.io/badge/Java-21-blue?style=plastic=openjdk)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=plastic=spring)  
![Maven](https://img.shields.io/badge/Maven-4.0-red?style=plastic=apachemaven)  
![Security](https://img.shields.io/badge/Security-JWT-blueviolet?style=plastic=jsonwebtokens)  
![Tests](https://img.shields.io/badge/Tests-JUnit5%20%26%20Mockito-yellowgreen?style=plastic)  
![Coverage](https://img.shields.io/badge/Coverage-80%25-brightgreen?style=plastic=jacoco)  
![Deploy](https://img.shields.io/badge/Deploy-Render-lightgrey?style=plastic=render)  

---

## 🔗 Tabela de Conteúdos

- [🚀 Sobre o Projeto](#sobre-o-projeto)
- [🛠️ Tecnologias Utilizadas](#tecnologias-utilizadas)
- [🗺️ Modelo de Dados (DER)](#modelo-de-dados-der)
- [⚙️ Configuração do Ambiente](#configuração-do-ambiente)
- [⏯️ Executando a Aplicação](#executando-a-aplicação)
- [✅ Executando os Testes](#executando-os-testes)
- [📖 Endpoints da API](#endpoints-da-api)
- [🔁 Requisições e Respostas da API](#requisições-e-respostas-da-api)
- [☁️ Deploy](#deploy)

---

<h2 id="sobre-o-projeto">🚀 Sobre o Projeto</h2>

A **Academic System API** oferece uma solução completa para as operações básicas de uma instituição de ensino. As principais funcionalidades são:
-   Cadastro e listagem de Alunos.
-   Cadastro e listagem de Disciplinas.
-   Matrícula de um Aluno em uma ou mais Disciplinas.
-   Atribuição de notas para Alunos em Disciplinas.
-   Geração de relatórios de Alunos aprovados e reprovados.
-   Sistema de autenticação seguro via JWT para o usuário "Professor".

---

<h2 id="tecnologias-utilizadas">🛠️ Tecnologias Utilizadas</h2>

-   **Java 21**: Linguagem de programação principal.
-   **Spring Boot**: Framework para criação da aplicação.
-   **Spring Web**: Para a construção dos endpoints RESTful.
-   **Spring Data JPA**: Para a persistência de dados.
-   **Spring Security**: Para autenticação e autorização via JWT.
-   **H2 Database**: Banco de dados em memória para o ambiente de desenvolvimento e testes.
-   **Maven**: Gerenciador de dependências e build.
-   **Docker**: Para a conteinerização da aplicação e deploy.
-   **Lombok**: Para a redução de código boilerplate.
-   **JaCoCo**: Para a análise de cobertura de testes.
-   **JUnit 5 & Mockito**: Para a escrita de testes unitários e de integração.

---

<h2 id="modelo-de-dados-der">🗺️ Modelo de Dados (DER)</h2>

O modelo relacional da aplicação foi projetado para normalizar os dados e garantir a integridade referencial entre as entidades.

```
/docs
└── DER.png
```
![Diagrama Entidade-Relacionamento](https://github.com/LeandroMedvedev/academic-system-api/blob/032ed26ab25be25a2c5bf2a12445bff2111c79a2/docs/DER.png)

---

<h2 id="configuração-do-ambiente">⚙️ Configuração do Ambiente</h2>

Siga os passos abaixo para configurar o ambiente de desenvolvimento local.

#### **Pré-requisitos**

-   Git
-   Java 21 (JDK)
-   Maven 3.8+

#### **Passo a Passo**

1.  **Clone o repositório:**
    ```bash
    git clone git@github.com:LeandroMedvedev/academic-system-api.git
    
    cd academic-system-api
    ```

2.  **Crie o arquivo de configuração de desenvolvimento:**
    O projeto utiliza um arquivo de template para as configurações locais.
    ```bash
    # Na raiz do projeto, copie o template
    cp src/main/resources/application-dev.yml.template src/main/resources/application-dev.yml
    ```
    *Obs: Para este projeto, o arquivo já contém os valores padrão para o H2 e não precisa de edição, mas em um projeto real, você preencheria suas credenciais neste passo.*  

3.  **Compile e instale as dependências:**
    ```bash
    mvn clean install
    ```

---

<h2 id="executando-a-aplicação">⏯️ Executando a Aplicação</h2>

Para iniciar a API localmente, execute o seguinte comando:

```bash
mvn spring-boot:run
```
A API estará disponível em `http://localhost:8080`.

-   **H2 Console:** Para visualizar o banco de dados em memória, acesse `http://localhost:8080/h2-console` no seu navegador.
-   **JDBC URL:** `jdbc:h2:mem:academicsystem`
-   **Username:** `sa`
-   **Password:** `password`

---

<h2 id="executando-os-testes">✅ Executando os Testes</h2>

Para executar todos os testes (unitários e de integração) e validar a cobertura de código, utilize o comando `verify` do Maven.

```bash
mvn clean verify
```

O build irá falhar se a cobertura de testes for inferior a 80%. O relatório detalhado de cobertura pode ser encontrado em `target/site/jacoco/index.html`.

---

<h2 id="endpoints-da-api">📡 Endpoints da API</h2>

A seguir, a lista completa de todos os endpoints disponíveis na `v1` da API.

### 🔑 Autenticação (`/login`)

| Método | Endpoint | Protegido | Descrição                                                                   |
| :----- | :------- | :-------- | :-------------------------------------------------------------------------- |
| `POST` | `/login` | Não       | Autentica um usuário (`professor`) e retorna um token de acesso JWT na resposta. |

### 👨‍🎓 Alunos (`/students`)

| Método | Endpoint         | Protegido | Descrição                                    |
| :----- | :--------------- | :-------- | :------------------------------------------- |
| `POST` | `/api/v1/students` | **Sim** | Cadastra um novo aluno no sistema.           |
| `GET`  | `/api/v1/students` | **Sim** | Retorna uma lista com todos os alunos cadastrados. |
| `GET`  | `/api/v1/students/{id}` | **Sim** | Busca e retorna os dados de um aluno específico pelo seu ID. |


### 📚 Disciplinas (`/courses`)

| Método | Endpoint                               | Protegido | Descrição                                                      |
| :----- | :--------------------------------------- | :-------- | :--------------------------------------------------------------- |
| `POST` | `/api/v1/courses`                        | **Sim** | Cadastra uma nova disciplina no sistema.                       |
| `GET`  | `/api/v1/courses`                        | **Sim** | Retorna uma lista com todas as disciplinas cadastradas.          |
| `GET`  | `/api/v1/courses/{id}`                   | **Sim** | Busca e retorna os dados de uma disciplina específica pelo seu ID. |
| `GET`  | `/api/v1/courses/{id}/approved-students` | **Sim** | Retorna uma lista dos alunos aprovados (nota >= 7) na disciplina. |
| `GET`  | `/api/v1/courses/{id}/disapproved-students` | **Sim** | Retorna uma lista dos alunos reprovados (nota < 7) na disciplina. |

### 📝 Matrículas (`/enrollments`)

| Método | Endpoint                                          | Protegido | Descrição                                                                |
| :----- | :------------------------------------------------ | :-------- | :------------------------------------------------------------------------- |
| `POST` | `/api/v1/enrollments`                             | **Sim** | Cria uma nova matrícula, associando um aluno a uma disciplina.             |
| `PUT`  | `/api/v1/enrollments/student/{sId}/course/{cId}/grade` | **Sim** | Atribui ou atualiza a nota de um aluno em uma disciplina específica. |

---

<h2 id="requisições-e-respostas-da-api">📖 Requisições e Respostas da API</h2>

**Pré-requisito:** Antes de chamar os endpoints protegidos, você precisa obter um token.

### **1. Autenticação**

#### 1.1 Obter Token de Acesso

**➡️ Requisição:**

```bash
curl -X POST https://academic-system-api.onrender.com/login \
-H "Content-Type: application/json" \
-d '{
    "username": "professor",
    "password": "123456"
}'
```

**⬅️ Resposta (`200 OK`):**

```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJBY2FkZW1pYyBTeXN0ZW0gQVBJIiwic3ViIjoicHJvZmVzc29yIiwiZXhwIjoxNzU4ODg2MzcxfQ.abcdef123..."
}
```

*(Guarde este token para as próximas chamadas)*

-----

### **2. Alunos (`/api/v1/students`)**

*(**Token JWT** é obrigatório para todos os endpoints abaixo)*

#### 2.1 Criar um Novo Aluno

**➡️ Requisição:**

```bash
TOKEN="seu_token_jwt_aqui"
curl -X POST https://academic-system-api.onrender.com/api/v1/students \
-H "Authorization: Bearer $TOKEN" \
-H "Content-Type: application/json" \
-d '{
    "name": "Doug Heffernan",
    "cpf": "111.111.111-11",
    "email": "doug@ips.com",
    "phoneNumber": "555-0101",
    "address": "3121 Aberdeen Road, Queens, NY"
}'
```

**⬅️ Resposta (`201 Created`):**

```json
{
    "id": 1,
    "name": "Doug Heffernan",
    "cpf": "111.111.111-11",
    "email": "doug@ips.com"
}
```

#### 2.2 Listar Todos os Alunos

**➡️ Requisição:**

```bash
TOKEN="seu_token_jwt_aqui"
curl -X GET https://academic-system-api.onrender.com/api/v1/students \
-H "Authorization: Bearer $TOKEN"
```

**⬅️ Resposta (`200 OK`):**

```json
[
    {
        "id": 1,
        "name": "Doug Heffernan",
        "cpf": "111.111.111-11",
        "email": "doug@ips.com"
    }
]
```

#### 2.3 Buscar Aluno por ID

**➡️ Requisição:**

```bash
TOKEN="seu_token_jwt_aqui"
curl -X GET https://academic-system-api.onrender.com/api/v1/students/1 \
-H "Authorization: Bearer $TOKEN"
```

**⬅️ Resposta (`200 OK`):**

```json
{
    "id": 1,
    "name": "Doug Heffernan",
    "cpf": "111.111.111-11",
    "email": "doug@ips.com"
}
```

-----

### **3. Disciplinas (`/api/v1/courses`)**

*(**Token JWT** é obrigatório para todos os endpoints abaixo)*

#### 3.1 Criar uma Nova Disciplina

**➡️ Requisição:**

```bash
TOKEN="seu_token_jwt_aqui"
curl -X POST https://academic-system-api.onrender.com/api/v1/courses \
-H "Authorization: Bearer $TOKEN" \
-H "Content-Type: application/json" \
-d '{
    "name": "Leis de Contrato para Secretárias",
    "code": "LAW202"
}'
```

**⬅️ Resposta (`201 Created`):**

```json
{
    "id": 1,
    "name": "Leis de Contrato para Secretárias",
    "code": "LAW202"
}
```

#### 3.2 Listar Todas as Disciplinas

**➡️ Requisição:**

```bash
TOKEN="seu_token_jwt_aqui"
curl -X GET https://academic-system-api.onrender.com/api/v1/courses \
-H "Authorization: Bearer $TOKEN"
```

**⬅️ Resposta (`200 OK`):**

```json
[
    {
        "id": 1,
        "name": "Leis de Contrato para Secretárias",
        "code": "LAW202"
    }
]
```

#### 3.3 Buscar Disciplina por ID

**➡️ Requisição:**

```bash
TOKEN="seu_token_jwt_aqui"
curl -X GET https://academic-system-api.onrender.com/api/v1/courses/1 \
-H "Authorization: Bearer $TOKEN"
```

**⬅️ Resposta (`200 OK`):**

```json
{
    "id": 1,
    "name": "Leis de Contrato para Secretárias",
    "code": "LAW202"
}
```

-----

### **4. Matrículas e Notas (`/api/v1/enrollments`)**

*(**Token JWT** é obrigatório para todos os endpoints abaixo)*

*(Vamos primeiro criar a aluna Carrie Heffernan para usar nestes exemplos)*
`POST /api/v1/students` com `{"name": "Carrie Heffernan", "cpf": "222.222.222-22", "email": "carrie@lawfirm.com"}` -\> **Recebe ID 2**

#### 4.1 Matricular Aluno em Disciplina

**➡️ Requisição:** (Matricular Carrie (ID 2) na disciplina de Leis (ID 1))

```bash
TOKEN="seu_token_jwt_aqui"
curl -X POST https://academic-system-api.onrender.com/api/v1/enrollments \
-H "Authorization: Bearer $TOKEN" \
-H "Content-Type: application/json" \
-d '{
    "studentId": 2,
    "courseId": 1
}'
```

**⬅️ Resposta (`204 No Content`):** (Nenhum corpo de resposta)

#### 4.2 Atribuir Nota a uma Matrícula

**➡️ Requisição:** (Dar nota 9.8 para a Carrie (ID 2) em Leis (ID 1))

```bash
TOKEN="seu_token_jwt_aqui"
curl -X PUT https://academic-system-api.onrender.com/api/v1/enrollments/student/2/course/1/grade \
-H "Authorization: Bearer $TOKEN" \
-H "Content-Type: application/json" \
-d '{
    "grade": 9.8
}'
```

**⬅️ Resposta (`204 No Content`):** (Nenhum corpo de resposta)

-----

### **5. Relatórios (dentro de `/api/v1/courses`)**

*(**Token JWT** é obrigatório para todos os endpoints abaixo)*

#### 5.1 Listar Alunos Aprovados em uma Disciplina

*(Considerando que a nota da Carrie é 9.8, ela está aprovada)*

**➡️ Requisição:**

```bash
TOKEN="seu_token_jwt_aqui"
curl -X GET https://academic-system-api.onrender.com/api/v1/courses/1/approved-students \
-H "Authorization: Bearer $TOKEN"
```

**⬅️ Resposta (`200 OK`):**

```json
[
    {
        "id": 2,
        "name": "Carrie Heffernan",
        "cpf": "222.222.222-22",
        "email": "carrie@lawfirm.com"
    }
]
```

#### 5.2 Listar Alunos Reprovados em uma Disciplina

*(Vamos adicionar o Arthur com nota baixa para este exemplo)*
`POST /api/v1/students` com `{"name": "Arthur Spooner", "cpf": "333.333.333-33", "email": "arthur@inthebasement.com"}` -\> **Recebe ID 3**
`POST /api/v1/enrollments` com `{"studentId": 3, "courseId": 1}`
`PUT /api/v1/enrollments/student/3/course/1/grade` com `{"grade": 4.2}`

**➡️ Requisição:**

```bash
TOKEN="seu_token_jwt_aqui"
curl -X GET https://academic-system-api.onrender.com/api/v1/courses/1/disapproved-students \
-H "Authorization: Bearer $TOKEN"
```

**⬅️ Resposta (`200 OK`):**

```json
[
    {
        "id": 3,
        "name": "Arthur Spooner",
        "cpf": "333.333.333-33",
        "email": "arthur@inthebasement.com"
    }
]
```

---

<h2 id="deploy">☁️ Deploy</h2>

A aplicação foi implantada na plataforma **Render** utilizando Docker.

**URL da Aplicação:** [https://academic-system-api.onrender.com](https://academic-system-api.onrender.com)

A API está ativa e pode ser consumida seguindo a documentação acima. Lembre-se que, por utilizar um banco H2 em memória, os dados são zerados sempre que o serviço reinicia.

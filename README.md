# caiosystems - Backend API

Esta documentação fornece uma visão da API backend caiosystems, focando em seus recursos, endpoints e configurações.

## Sumário
1. [Visão Geral](#1-visão-geral)
2. [Tecnologias Principais](#2-tecnologias-principais)
3. [Entidade Principal (`UserClient`)](#3-entidade-principal-userclient)
4. [Endpoints da API (`/user`)](#4-endpoints-da-api-user)
    - [4.1. `POST /user/register`](#41-post-userregister)
    - [4.2. `GET /user/{id}`](#42-get-userid)
    - [4.3. `GET /user`](#43-get-user)
    - [4.4. `PUT /user`](#44-put-user)
    - [4.5. `DELETE /user/{id}`](#45-delete-userid)
    - [4.6. `GET /user/auth`](#46-get-userauth)
    - [4.7. `POST /user/login`](#47-post-userlogin)
    - [4.8. `POST /logout`](#48-post-logout)
5. [Tratamento de Erros](#5-tratamento-de-erros)
6. [Configurações Essenciais (application.properties)](#6-configurações-essenciais-applicationproperties)
7. [Executando Localmente](#7-executando-a-aplicação)

---

### 1. Visão Geral

A aplicação `caiosystems` é um serviço backend desenvolvido em Spring Boot, com o objetivo principal de gerenciar usuários e validar credenciais usando Spring Security. A API oferece endpoints para registro, autenticação e operações CRUD básicas em usuários.

### 2. Tecnologias Principais

*   **Framework**: Spring Boot 3.x
*   **Linguagem**: Java 21
*   **Persistência**: Spring Data JPA
*   **Banco de Dados**: PostgreSQL (configurável via variáveis de ambiente)
*   **Segurança**: Spring Security (autenticação baseada em formulário/sessão, BCrypt para senhas)
*   **Build Tool**: Maven

### 3. Entidade Principal (`UserClient`)

Representa um usuário no sistema.

*   **id**: `Long` (Gerado automaticamente)
*   **username**: `String` (Email, único, obrigatório)
*   **password**: `String` (Obrigatório, mínimo 8 caracteres, criptografado com BCrypt)

### 4. Endpoints da API (`/user`)

A API base para usuários é `/user`.

#### 4.1. `POST /user/register`

*   **Descrição**: Registra um novo usuário no sistema.
*   **Método**: `POST`
*   **Corpo da Requisição**: `UserClient` (JSON)
    ```json
    {
        "username": "email@example.com",
        "password": "senhaSegura123"
    }
    ```
*   **Resposta**: `200 OK` com o `UserClient` registrado.

#### 4.2. `GET /user/{id}`

*   **Descrição**: Busca um usuário pelo ID. Requer autenticação.
*   **Método**: `GET`
*   **Parâmetros de Path**: `id` (Long)
*   **Resposta**: `200 OK` com o `UserClient` encontrado.

#### 4.3. `GET /user`

*   **Descrição**: Lista todos os usuários. Requer autenticação.
*   **Método**: `GET`
*   **Resposta**: `200 OK` com uma lista de `UserClient`.

#### 4.4. `PUT /user`

*   **Descrição**: Atualiza um usuário existente. Requer autenticação.
*   **Método**: `PUT`
*   **Corpo da Requisição**: `UserClient` (JSON) - O `id` do usuário deve ser enviado como parâmetro de consulta.
*   **Parâmetros de Query**: `id` (Long)
    ```http
    PUT /user?id=1
    Content-Type: application/json
    {
        "username": "novo_email@example.com",
        "password": "novaSenhaSegura123"
    }
    ```
*   **Resposta**: `200 OK` com o `UserClient` atualizado.

#### 4.5. `DELETE /user/{id}`

*   **Descrição**: Exclui um usuário pelo ID. Requer autenticação.
*   **Método**: `DELETE`
*   **Parâmetros de Path**: `id` (Long)
*   **Resposta**: `200 OK` com mensagem de sucesso.

#### 4.6. `GET /user/auth`

*   **Descrição**: Verifica se o usuário atual está autenticado.
*   **Método**: `GET`
*   **Resposta**: `200 OK` com `true` se autenticado, `false` caso contrário.

#### 4.7. `POST /user/login`

*   **Descrição**: Endpoint de login para autenticação via formulário.
*   **Método**: `POST`
*   **Corpo da Requisição**: `application/x-www-form-urlencoded`
    ```
    username=email@example.com&password=senhaSegura123
    ```
*   **Resposta**:
    *   `200 OK` com `true` no corpo em caso de sucesso.
    *   `401 Unauthorized` com JSON de erro em caso de falha.

#### 4.8. `POST /logout`

*   **Descrição**: Realiza o logout do usuário, invalidando a sessão e removendo cookies.
*   **Método**: `POST`
*   **Resposta**: `200 OK`.

### 5. Tratamento de Erros

A API retorna respostas padronizadas em JSON para diferentes tipos de erros, incluindo:

*   `404 Not Found`: Recurso não encontrado (e.g., usuário com ID inexistente).
*   `409 Conflict`: Violação de integridade de dados (e.g., email duplicado).
*   `400 Bad Request`: Erros de validação (e.g., senha muito curta, formato de email inválido) ou formato de requisição incorreto.
*   `401 Unauthorized`: Falha de autenticação ou credenciais inválidas.
*   `405 Method Not Allowed`: Método HTTP não permitido para o endpoint.

Exemplo de resposta de erro:
```json
{
    "timestamp": "2023-10-27T10:30:00.000000",
    "status": 400,
    "error": "Bad Request",
    "message": "Invalid password lenght, minimum 8 characters required",
    "path": "/user/register"
}
```

### 6. Configurações Essenciais applicationproperties

*   Nome da Aplicação:
    *   `spring.application.name`:`caiosystems`
*   Configurações de Banco de Dados (variáveis de ambiente):
    *   `SPRING_DATASOURCE_URL`
    *   `SPRING_DATASOURCE_USERNAME`
    *   `SPRING_DATASOURCE_PASSWORD`
    *   `SPRING_DATASOURCE_DRIVER_CLASS_NAME`
* JPA (atualização do banco de dados):
    *   `spring.jpa.hibernate.ddl-auto`:`update`
*   CORS - URL permitida para requisições CORS, obrigatório para o frontend (variável de ambiente):
    *   `CLIENT_URL`

### 7. Executando a Aplicação

*   A aplicação utiliza um Dockerfile multi-stage para construir e empacotar a aplicação em uma imagem Docker.
*   Execução Local para Desenvolvimento (mvnw spring-boot:run):
    *   Navegue até a raiz do projeto: `cd /caminho/para/seu/projeto`
    *   Dê permissão de execução ao script mvnw (se necessário): `chmod +x mvnw`
    *   Inicie a aplicação: `mvnw spring-boot:run`

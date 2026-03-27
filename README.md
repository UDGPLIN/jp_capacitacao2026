# API de Gestão de Produtos e Estoque

API REST desenvolvida em Java com Spring Boot para gerenciamento de produtos, categorias e controle de estoque.

O sistema permite:
- Cadastro de produtos e categorias
- Associação entre produto e categoria
- Controle de estoque com histórico de movimentações
- Validações de regras de negócio

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Oracle Database
- Lombok
- Swagger (OpenAPI)
- JUnit + Mockito

## Funcionalidades

### Produtos
- Criar produto
- Atualizar produto
- Buscar por ID
- Listar todos

### Categorias
- Criar categoria (sem duplicidade)
- Atualizar categoria
- Listar categorias

### Estoque
- Adicionar estoque
- Remover estoque
- Histórico de movimentações
- Consulta por produto

### Regras de negócio
- Nome obrigatório
- Preço maior que zero
- Não permite estoque negativo
- Categoria não pode ser duplicada

## Endpoints

### Produtos
POST /produtos
GET /produtos
GET /produtos/{id}

### Categorias
POST /categorias
GET /categorias

### Estoque
POST /inventory/{productId}/add?quantity=10
POST /inventory/{productId}/remove?quantity=5
GET  /inventory/{productId}

## Como executar

1. Clonar o repositório
2. Configurar o banco Oracle
3. Rodar a aplicação
4. Acessar Swagger:
   http://localhost:9090/swagger-ui.html

## Testes

Os testes foram implementados utilizando JUnit e Mockito, validando regras de negócio como:

- Adição de estoque
- Tratamento de erro para produto inexistente
# WebChamados

Sistema web de gestão de chamados internos com atribuição automática de responsáveis.

## Sobre o projeto

O WebChamados é uma aplicação web desenvolvida para resolver um problema comum em ambientes administrativos: a gestão descentralizada de pedidos internos. Antes do sistema, solicitações chegavam por WhatsApp, e-mail ou pessoalmente, eram anotadas em cadernos e frequentemente esquecidas ou mal distribuídas entre a equipe de suporte.

A aplicação centraliza a abertura, o acompanhamento e a resolução de chamados internos, oferecendo:

- Cadastro de chamados com título, descrição, prioridade e status
- Atribuição manual ou automática de responsáveis
- Distribuição inteligente de carga de trabalho entre a equipe de suporte
- Listagem com filtros para acompanhamento em tempo real

## Tecnologias utilizadas

### Backend
- **Java 25** — LTS mais recente, com melhorias de performance e novos recursos de linguagem
- **Spring Boot 4.1** — Framework consolidado para APIs REST em Java, com ecossistema maduro e ampla adoção no mercado
- **Spring Data JPA + Hibernate** — Abstração de persistência que reduz boilerplate e facilita a manutenção
- **PostgreSQL** — Banco de dados relacional robusto, open source e amplamente utilizado em produção

### Frontend
- **React** — Biblioteca de UI baseada em componentes, com grande ecossistema e mercado de trabalho consolidado
- **Vite** — Ferramenta de build moderna que oferece hot reload instantâneo e tempo de build reduzido em relação ao Create React App
- **Tailwind CSS** — Framework utilitário que permite construir interfaces consistentes rapidamente sem escrever CSS customizado

### Documentação de API
- **SpringDoc OpenAPI (Swagger UI)** — Geração automática de documentação interativa da API a partir das anotações do código

## Pré-requisitos

Certifique-se de ter instalado em sua máquina:

- Java 25+
- Maven 3.9+
- Node.js 24+
- npm 11+
- PostgreSQL 14+

## Instalação e execução

### 1. Clone o repositório

```bash
git clone https://github.com/lsivolella/WebChamados.git
cd WebChamados
```

### 2. Configure o banco de dados

Conecte-se ao PostgreSQL como superusuário:

```bash
psql -U postgres
```

Em seguida, execute os seguintes comandos para a criação e configuração do banco:

```sql
CREATE DATABASE chamados_db;
CREATE USER webchamados WITH PASSWORD 'sua_senha';
GRANT ALL PRIVILEGES ON DATABASE chamados_db TO webchamados;
```

Saia do psql com `\q`.

### 3. Configure as variáveis do backend

Edite o arquivo `backend/src/main/resources/application.properties` com as credenciais definidas acima:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/chamados_db
spring.datasource.username=webchamados
spring.datasource.password=sua_senha
```

### 4. Suba o backend

```bash
cd backend
./mvnw spring-boot:run
```

O servidor estará disponível em `http://localhost:8080`.  
A documentação interativa da API estará disponível em `http://localhost:8080/swagger-ui/index.html`.

### 5. Instale as dependências do frontend

```bash
cd ../frontend
npm install
```

### 6. Suba o frontend

```bash
npm run dev
```

A aplicação estará disponível em `http://localhost:5173`.

## Decisões arquiteturais

<!-- TODO: preencher conforme o projeto avançar -->

## Referências e bibliotecas externas

- [Spring Initializr](https://start.spring.io) — geração do projeto base do backend
- [SpringDoc OpenAPI](https://springdoc.org) — documentação automática da API
- [Tailwind CSS](https://tailwindcss.com) — framework de estilização
- [Vite](https://vitejs.dev) — ferramenta de build do frontend
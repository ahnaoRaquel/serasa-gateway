# 🏦 Gateway Serasa - Sistema de Consultas e Gerenciamento de Pessoas

##  Descrição do Projeto

O **Gateway Serasa** é uma aplicação Java com **Spring Boot** que simula um sistema de **consulta de dados de crédito**.  
Ele integra dados **mockados** (para simulação) com **persistência em banco de dados**, permitindo:

- Consultar informações de **pessoas físicas** e **jurídicas**.
- Gerenciar **restrições financeiras** (ex.: dívidas, cheques sem fundo).
- Filtrar **dívidas e restrições ativas**.
- **Ativar ou desativar** pessoas no sistema (controle lógico de status).
- Consultar **em lote** múltiplos documentos.
- Consultar pessoas por **nome** ou por **intervalo de datas de restrições**.
- **Histórico de consultas**: toda vez que um documento é consultado, é registrada uma entrada com data/hora.
- Tratamento robusto de erros via **exceções customizadas** e **Global Exception Handler**.

---

##  Estrutura de Funcionalidades

### Consultas
- `GET /api/consulta/{documento}` – Consulta de pessoa por documento.
  - Caso a pessoa não exista no banco, é buscada no **MockService** e **salva no banco**.
  - Apenas **pessoas ativas** são retornadas.
- `POST /api/consulta/lote` – Consulta de várias pessoas por documentos.
- Filtros aplicados para retornar **apenas dívidas e restrições ativas**.

###  Ativar/Desativar Pessoas
- `PUT /api/pessoas/{documento}/ativar` – Ativa pessoa no sistema.
- `DELETE /api/pessoas/{documento}/desativar` – Desativa pessoa no sistema.

### Histórico de Consultas (Nova Funcionalidade - TDD)
- Cada consulta a uma pessoa (via `/api/consulta/{documento}`) é automaticamente registrada no **ConsultaHistorico**.
- As informações salvas são:
  - `documento` da pessoa consultada
  - `dataConsulta` (data/hora da consulta)
  - Referência à `Pessoa` consultada
- Permite auditoria e análise das consultas realizadas.

---

## 🏗️ Arquitetura do Projeto

A arquitetura foi planejada para **manter a separação de responsabilidades, reuso e escalabilidade**. O projeto está dividido nos seguintes módulos:

### 📦 1. `common-domain`
- **Responsabilidade:** contém as **entidades JPA** e **enums** usados em todo o sistema.  
- **Decisão:** manter as entidades **livres de anotações de API e validações externas**, preservando a pureza do modelo de domínio.  
- **Benefício:** facilita o reuso em outros módulos sem dependências desnecessárias.

---

### 🌐 2. `external-api-client`
- **Responsabilidade:** concentra **toda a comunicação com APIs externas**, usando **OpenFeign**.
- **Decisão:** manter os **DTOs externos** e os **Feign Clients** separados para evitar acoplamento da aplicação a serviços de terceiros.
- **Benefício:** se a API do Serasa mudar ou for substituída, basta atualizar este módulo.

---

### 🚀 3. `main-application`
- **Responsabilidade:** 
  - Camada de **serviços**, **repositórios** (Spring Data JPA), **controladores REST** e **configuração de segurança**.
  - Contém os **DTOs da própria API** (Request e Response).
- **Decisão:** centralizar aqui a lógica de negócios e manter as camadas bem definidas.
- **Benefício:** simplifica manutenção e testes.

---

## 🔐 Segurança

A segurança foi implementada com **Spring Security 6** utilizando:

1. **Autenticação HTTP Basic**
   - Usuários e roles são **gerenciados em memória** (ideal para ambiente de desenvolvimento).
   - Senhas codificadas com **BCryptPasswordEncoder**.

2. **Autorização baseada em URL/Request Matching**
   - Endpoints protegidos por roles:
     - `ADMIN`: pode ativar/desativar pessoas.
     - `ADMIN` e `USER`: podem consultar informações.
   - Todas as outras requisições exigem autenticação.

3. **Segurança por padrão**
   - Nenhum endpoint público é exposto sem autenticação.

4. **Mensagens customizadas**
   - Resposta **401 Unauthorized** para usuários não autenticados.
   - Resposta **403 Forbidden** com mensagem em JSON para usuários autenticados sem permissão.

---

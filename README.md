# 🏦 Gateway Serasa - Sistema de Consultas e Gerenciamento de Pessoas

##  Descrição do Projeto

O **Gateway Serasa** é uma aplicação Java com **Spring Boot** que simula um sistema de **consulta de dados de crédito** (inspirado no Serasa).  
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

## Principais Classes

### Modelos (Entities)
- **`Pessoa`** – Representa a pessoa física ou jurídica, com campos como `documento`, `nome`, `ativo`, `dividas`, `restricoes`.
- **`Restricao`** – Guarda informações sobre restrições financeiras (ex.: cheque sem fundo).
- **`Divida`** – Registra dívidas da pessoa.
- **`ConsultaHistorico`** – **Nova entidade** que armazena cada consulta feita no sistema.

### Serviços
- **`ConsultaSerasaService`**
  - Responsável pela lógica de consultas de documentos.
  - Integra com **MockService** caso pessoa não exista no banco.
  - **Novo:** registra automaticamente cada consulta no `ConsultaHistorico`.
- **`PessoaService`**
  - Lógica para ativar e desativar pessoas.
- **`ConsultaHistoricoService`**
  - Contém as regras para salvar e listar o histórico de consultas.
- **`MockService`**
  - Fornece dados simulados para testes quando a pessoa não existe no banco.

---

## Funcionalidade Escolhida: **Histórico de Consultas**

Escolhemos implementar o **Histórico de Consultas** pois é uma funcionalidade **essencial para sistemas de crédito**:  
- Permite rastrear **quando, quem e quantas vezes** uma pessoa foi consultada.
- É relevante para **auditorias, relatórios** e segurança da informação.
- Se integra de forma natural ao fluxo já existente de consultas no `ConsultaSerasaService`.



# EcoLogic - API Back-End

O **EcoLogic** é uma plataforma de inteligência analítica e auditoria de consumo escolar voltada para o controle preciso de desperdícios, gerenciamento de estoque, monitoramento de consumos por setor, projeção de metas financeiras e geração de relatórios analíticos de sustentabilidade.

## 🚀 Objetivo do Sistema
Garantir a eficiência na gestão de recursos escolares, otimizando compras, prevenindo desperdícios de alimentos e materiais de consumo, e fornecendo dados consolidados e preditivos (como alertas de anomalias e burn rate) para tomada de decisão da diretoria.

## 🛠️ Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3.x / 4.x**
- **Maven**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Lombok**
- **Spring Web**
- **Validation**
- **Spring Security** (Estrutura base injetada)

## 🏢 Arquitetura Adotada
O projeto adota uma **Arquitetura Modular por Domínio** aliada às melhores práticas de separação de conceitos do ecossistema Spring. Cada domínio/módulo possui total independência de suas camadas de negócio, permitindo fácil escalabilidade, desacoplamento e manutenção.

### Estrutura de Pastas de um Domínio Padrão
- `controller/`: Camada de exposição dos endpoints REST.
- `dto/`: Objetos de transferência de dados divididos estritamente em `request` e `response`.
- `entity/`: Modelagem das entidades mapeadas via JPA.
- `repository/`: Interfaces de comunicação com o banco de dados (herdando JpaRepository).
- `service/`: Camada contendo os contratos e implementações de regras de negócio.
- `mapper/`: Camada responsável pelas conversões entre Entidades e DTOs.

## 📦 Explicação dos Módulos Principais
- **usuario:** Gerenciamento dos atores do ecossistema, distinguindo Diretores e Administradores de Setor.
- **setor:** Centralizador do sistema, vinculando as metas, consumos e seus respectivos gestores.
- **produto & categoria:** Controle do catálogo de itens utilizáveis na instituição.
- **estoque:** Controle quantitativo de armazenamento e movimentações física de produtos.
- **consumo:** Registro detalhado de saídas de materiais ou mantimentos associados a um setor e responsável.
- **meta:** Definição de limites financeiros e conversão direta de desperdícios em valores monetários reais.
- **relatorio & auditoria:** Geração de relatórios operacionais consolidados e alinhamento com a **ODS 12** (Consumo e Produção Responsáveis).
- **notificacao:** Disparo de avisos internos e alertas do sistema.

## ⚙️ Como Rodar o Projeto

1. Certifique-se de possuir o **JDK 21** e o **Maven** instalados na sua máquina.
2. Certifique-se de que possui uma instância ativa do **PostgreSQL** e crie um banco de dados chamado `ecologic_db`.
3. Ajuste as propriedades de usuário e senha do banco no arquivo `src/main/resources/application.properties` se necessário.
4. Abra o projeto na sua IDE de preferência (recomendado: **IntelliJ IDEA**).
5. Deixe a IDE importar as dependências do Maven (`pom.xml`).
6. Execute a classe principal `EcoLogicBackEndApplication.java`.

## 📌 Padrões Adotados
- Nomenclatura das pastas estruturais e módulos escrita totalmente em **Português**.
- Criação estrita de interfaces para isolamento da camada de serviço (`Service` -> `ServiceImpl`).
- Validação agressiva na entrada de dados utilizando a especificação do `Jakarta Validation`.
- Isolamento total de configurações globais em módulos dedicados (`config`, `security`, `infraestrutura`).
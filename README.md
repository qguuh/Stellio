# Stellio

Sistema desktop de gestão para lojas, desenvolvido em **Java**, pensado para gerentes acompanharem clientes, fornecedores e o estoque de produtos em um só lugar.

## ✨ Funcionalidades

- **Cadastro de Clientes** — nome, telefone, e-mail, CPF e endereço com busca automática de CEP.

- **Cadastro de Fornecedores** — nome, CNPJ, telefone, e-mail e site.

- **Cadastro de Produtos** — código de barras, categoria, fornecedor vinculado, preço de custo/venda e controle de estoque (quantidade atual e estoque mínimo).

- **Busca avançada com sugestões** — autocomplete em tempo real ao digitar o nome de clientes, fornecedores ou produtos, com navegação pelo teclado ou mouse.
- **Leitor de código de barras** — campo preparado para uso com leitor fiscal.

- **Dashboard de estoque** — painel com alerta de produtos com estoque baixo ou zerado.

- **Relatórios em PDF** — geração de relatórios de Clientes, Fornecedores e Produtos, com tabelas formatadas e dados sempre atualizados.

## 🛠️ Tecnologias

- **Java**
- **MySQL**
- **JDBC**
- **FlatLaf**
- **OpenPDF**

## 📂 Estrutura do projeto

```
src/
├── controller/     Regras de negócio e acesso ao banco (Cliente, Fornecedor, Produto)
├── model/          Classes de domínio (Cliente, Fornecedor, Produto)
├── utils/          Máscaras (CPF, CNPJ, CEP, Telefone) e validadores
├── database/       Conexão com o banco de dados
└── view/           Interface gráfica (Main.java)
```

## 🚀 Como rodar o projeto

### Pré-requisitos
- [MySQL](https://downloads.mysql.com/archives/workbench/) **(Recomendável usar a versão 8.0.47)**
- [XAMPP](https://www.apachefriends.org/pt_br/download.html)
- [Eclipse IDE](https://www.eclipse.org/downloads/packages/)

### Passos
1. Obtenha o repositório: **[Clique aqui para baixar o projeto](https://github.com/qguuh/stellio/archive/refs/heads/main.zip)**

2. Crie o banco de dados no MySQL e importe o script SQL disponível na pasta **[MySQL](https://github.com/qguuh/stellio-saves/blob/main/MySQL/stellio.sql)**
3. No painel do XAMPP, localize a linha do MySQL e clique no botão Start na coluna Actions.
4. No Eclipse, importe o projeto como um projeto Java existente.
5. Execute a classe `Main.java`.

## 📝 Licença

Este projeto é de uso livre para fins de estudo. Sinta-se à vontade para explorar, sugerir melhorias ou reportar problemas.

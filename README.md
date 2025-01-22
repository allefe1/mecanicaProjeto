Sistema de Gerenciamento de Oficina Mecânica

Este é um projeto desenvolvido em Java utilizando JavaFX com arquitetura MVC (Model-View-Controller) e banco de dados SQLite. O sistema tem como objetivo facilitar o gerenciamento de uma oficina mecânica, permitindo o cadastro de clientes, veículos e serviços realizados.

Estrutura do Projeto

O projeto está organizado em pacotes para seguir a arquitetura MVC e garantir maior organização do código:

application: Contém a classe principal para inicializar a aplicação.

models: Contém as classes que representam os modelos do sistema (Cliente, Veiculo, Servico, etc.).

controller: Contém os controladores responsáveis pela interação entre a interface gráfica e a lógica do sistema.

DAO: Contém as classes para a comunicação com o banco de dados (Data Access Object).

views: Contém os arquivos FXML e outros recursos relacionados à interface gráfica.

resources: Contém os arquivos de configuração, imagens e outros recursos necessários para o funcionamento da aplicação.

Funcionalidades

Cadastro de clientes com informações básicas como nome, telefone e endereço.

Cadastro de veículos associados aos clientes.

Registro de serviços realizados em veículos.

Visualização, edição e remoção de registros.

Tecnologias Utilizadas

Java: Linguagem de programação principal do projeto.

JavaFX: Framework para construção da interface gráfica.

SQLite: Banco de dados utilizado para armazenar as informações do sistema.

Eclipse IDE: Ambiente de desenvolvimento integrado utilizado para o desenvolvimento do projeto.

Configuração do Ambiente

Certifique-se de ter o Java JDK instalado (versão 8 ou superior).

Clone este repositório em sua máquina local:

git clone https://github.com/seu-usuario/nome-do-repositorio.git

Abra o projeto no Eclipse IDE.

Execute a classe principal localizada no pacote application para iniciar o sistema.

Banco de Dados

O banco de dados SQLite foi configurado utilizando um plugin no Eclipse, e o código SQL para criação das tabelas está incluído no projeto.

Capturas de Tela

Dashboard Principal



Dashboard de Serviços



Diagramas

Diagrama de Relação do Banco de Dados



Diagrama de Classes



Contribuições

Contribuições são bem-vindas! Caso tenha interesse em colaborar com o projeto, sinta-se à vontade para abrir issues ou enviar pull requests.


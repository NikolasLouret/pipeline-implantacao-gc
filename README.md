# API Clinica Médica

![Capa do Projeto](https://picsum.photos/850/280)

## Sobre o Projeto

Este trabalho prático tem como objetivo desenvolver uma API para gerenciar pacientes em uma clínica médica, como parte da disciplina de Gerência de Configuração e Evolução de Software. O projeto visa aplicar conceitos teóricos aprendidos, especialmente no contexto de integração contínua, automação de processos e entrega contínua de software, através do desenvolvimento de uma pipeline de implantação.

## Índice/Sumário

* [Sobre o Projeto](#sobre-o-projeto)
* [Requisitos Funcionais](#requisitos-funcionais)
* [Arquitetura](#arquitetura)
* [Instruções de Uso](#instruções-de-uso)
* [Tecnologias Usadas](#tecnologias-usadas)
* [Código Fonte](#código-fonte)
* [Testes](#testes)
* [Implantação](#implantação)
* [Contribuição](#contribuição)
* [Autores](#autores)
* [Licença](#licença)

## Introdução

O projeto desenvolvido visa implementar uma API para o gerenciamento de pacientes, utilizando a stack de tecnologias Java, Maven, Spring Boot e outras ferramentas modernas para garantir uma aplicação robusta e eficiente. Este documento detalha as etapas do desenvolvimento, incluindo a configuração do ambiente, o processo de implantação contínua e os testes realizados.

## Requisitos Funcionais 

:white_check_mark: Cadastrar pacientes.  
:white_check_mark: Listar todos os pacientes.  
:white_check_mark: Buscar paciente pelo ID.  
:white_check_mark: Editar um paciente.  
:white_check_mark: Deletar um paciente.  
:white_check_mark: Calcular IMC do paciente.  
:white_check_mark: Classificar IMC do paciente.  

## Arquitetura

A arquitetura do sistema é baseada no padrão **MVC** (Model-View-Controller), proporcionando uma separação clara entre as diferentes camadas da aplicação. Para a manipulação dos dados, foi criado:

- **Models**: Representa as entidades da aplicação.
- **Repositories**: Interface com o banco de dados, utilizando `H2 Database`.
- **Services**: Contém a lógica de negócios principal.
- **Controllers**: Definição dos `endpoints` para acesso aos dados baseado no padrão da API REST.

Foi criada também a classe `PatientDTO` para otimizar a comunicação entre o client e o servidor.

## Instruções de Uso

Para utilizar a API, siga os passos abaixo:

### Configurar o Ambiente de Desenvolvimento

<details><summary><b>Mostrar instuções</b></summary>
	
1. Certifique-se de ter o [JDK](https://www.oracle.com/br/java/technologies/downloads/) (Java Development Kit) instalado.
2. Clone o repositório do GitHub:
   ```sh
   git clone https://github.com/NikolasLouret/pipeline-implantacao-gc
   ```
3. Navegue até o diretório do projeto:
   ```sh
   cd pipeline-implantacao-gc/api-clinica-medica
   ```
4. Execute o comando Maven para construir o projeto:
   ```sh
   mvn clean package
   ```
5. Inicie a aplicação:
   ```sh
   mvn spring-boot:run
   ```

</details>
  
### Realizar Testes

<details><summary><b>Mostrar instuções</b></summary>
	
   Para executar testes unitários e de integração, basta executar o seguinte comando:
   ```sh
   mvn test
   ```

</details>

### Acessar a Documentação:
   A API possui documentação Swagger que pode ser acessada em `http://localhost:8080/api-docs`



## Tecnologias Usadas
   As principais tecnologias, frameworks e bibliotecas utilizadas no desenvolvimento da API incluem:
- [Java](https://www.java.com/pt-BR/): Linguagem de programação.
- [Spring Boot](https://maven.apache.org/): Framework para criação de aplicações standalone.
- [Maven](https://maven.apache.org/): Gerenciador de dependências e automação de build.
- [H2 Database](https://h2database.com/html/main.html): Banco de dados em memória para desenvolvimento e testes.
- [Lombock](https://projectlombok.org/): Biblioteca para reduzir boilerplate no código Java.
- [JUnit 5](https://junit.org/junit5/): Framework para testes unitários.
- [Cucumber](https://cucumber.io/): Framework para testes de aceitação.
- [Docker](https://www.docker.com/): Ferramenta para criação de contêineres e gerenciamento de ambientes.
- [Swagger](https://swagger.io/): Ferramenta para criação da documentação da API.

## Código Fonte
   O código fonte do projeto está disponível no repositório GitHub [Pipeline Implantação GC](https://github.com/NikolasLouret/pipeline-implantacao-gc). Para clonar, utilize o seguinte comando:
   ```sh
   git clone https://github.com/NikolasLouret/pipeline-implantacao-gc
   ```

## Testes
   - **Testes Unitários:** Realizados com [JUnit 5](https://junit.org/junit5/) para garantir que cada componente da aplicação funcione isoladamente.
   - **Testes de Integração:** Também com [JUnit 5](https://junit.org/junit5/), para validar a interação entre diferentes componentes do sistema.
   - **Testes de Aceitação:** Realizados com [Cucumber](https://cucumber.io/), para assegurar que o sistema atenda aos requisitos do usuário final.

## Implantação
A implantação da API pode ser realizada utilizando [Docker](https://www.docker.com/). Siga os passos abaixo para implantar a aplicação em um ambiente de produção:
	
### 1. Construir a imagem Docker:

<details><summary><b>Mostrar instuções</b></summary>
	
#### `Opção 1: DockerHub`
   
> É recomendado **utilizar** a imagem do DockerHub

&nbsp;&nbsp;&nbsp;&nbsp;É possível acessar a imagem Docker hospedada no [DockerHub](https://hub.docker.com/r/nikolaslouret/patient-api) e fazer o `pull` da imagem com o comando:
```sh
docker pull nikolaslouret/patient-api
```

#### `Opção 2: Local`
&nbsp;&nbsp;&nbsp;&nbsp;É possível também construir a imagem localmente, utilizando o comando:
```sh
docker build -t apiclinica .
```

</details>

### 2. Executar o `container` Docker:

<details><summary><b>Mostrar instuções</b></summary>
	
&nbsp;&nbsp;&nbsp;&nbsp;A execução do `container` é realizada através do comando:
```sh
docker run -p 8080:8080 apiclinica
```

</details>

### 3. Acessar a API:

<details><summary><b>Mostrar instuções</b></summary>

&nbsp;&nbsp;&nbsp;&nbsp;O acesso da API é realizado através da `url`:
```sh
http://localhost:8080/api/v1/patients
```

</details>

## Contribuição
Leia o arquivo [CONTRIBUTING.md](CONTRIBUTING.md) para saber detalhes sobre o nosso código de conduta e o processo de envio de solicitações `pull` (*Pull Request*) para nós.

## Autores
- [Nikolas Louret](https://github.com/NikolasLouret)

## Licença
Este projeto está licenciado sob a Licença MIT,  consulte o arquivo [LICENSE.md](LICENSE.md) para mais detalhes.

# 🐾 FindPet Backend

Backend da aplicação **FindPet**, uma API REST desenvolvida em **Java com Spring Boot** para gerenciar os dados do sistema de adoção responsável de animais.

O projeto aplica conceitos de **controllers, services, repositories, DTOs, tratamento de exceções, banco de dados e métodos HTTP**.

---

## 💡 Sobre o projeto

O **FindPet** tem como objetivo conectar pessoas a animais que precisam de um lar, promovendo uma adoção mais segura, organizada e responsável.

Este repositório contém a parte backend da aplicação, responsável por processar regras de negócio, persistir dados e fornecer endpoints para integração com o frontend.

---

## 🚀 Tecnologias utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- ModelMapper
- Bean Validation
- Postman
- Git e GitHub

---

## 🏗️ Arquitetura

O backend foi organizado por domínio para facilitar a manutenção e a expansão futura do projeto.

```txt
src/main/java/com/findpet/findpet_backend
│
├── usuario
│   ├── controller
│   ├── dto
│   ├── model
│   ├── repository
│   └── service
│
├── infrastructure
│   ├── exception
│   └── mapper
│
└── FindpetBackendApplication.java
```

### Responsabilidades das camadas

- **Controller:** recebe as requisições HTTP.
- **Service:** concentra as regras de negócio.
- **Repository:** acessa o banco de dados.
- **Model:** representa as entidades do sistema.
- **DTO:** controla os dados de entrada e saída da API.
- **Infrastructure:** contém recursos globais, como tratamento de exceções e conversão de objetos.

---

## 🔐 DTOs

O projeto utiliza DTOs para controlar os dados recebidos e retornados pela API.

Isso evita que informações sensíveis, como senha, sejam expostas nas respostas.

Exemplo de resposta de usuário:

```json
{
  "id": 1,
  "nome": "Ketlin Oliveira",
  "email": "ketlin@email.com"
}
```

---

## ⚠️ Tratamento de exceções

O backend possui tratamento global de exceções com `@RestControllerAdvice` e `@ExceptionHandler`.

Também foi criada a `BusinessException`, usada para representar erros de regra de negócio, como:

- Email já cadastrado
- Usuário não encontrado
- Senha incorreta
- Dados inválidos

As respostas de erro são padronizadas com `ErrorResponseDTO`.

---

## 📡 Endpoints principais

Base URL:

```txt
http://localhost:8080/api/usuarios
```

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/cadastro` | Cadastra um usuário |
| POST | `/login` | Realiza login |
| GET | `/` | Lista usuários |
| GET | `/{id}` | Busca usuário por ID |
| PUT | `/{id}` | Atualiza usuário |
| DELETE | `/{id}` | Remove usuário |

---

## 🧪 Testes

Os endpoints foram testados utilizando o **Postman**, validando os principais métodos HTTP:

- GET
- POST
- PUT
- DELETE

Também foram testados cenários de sucesso e erro.

---

## 🗄️ Banco de dados

Durante o desenvolvimento, foi utilizado o banco em memória **H2 Database**.

Console H2:

```txt
http://localhost:8080/h2-console
```

JDBC URL:

```txt
jdbc:h2:mem:findpetdb
```

---

## ▶️ Como executar

Clone o repositório:

```bash
git clone https://github.com/KetlinOlliveira/backend-findpet.git
```

Entre na pasta do projeto:

```bash
cd backend-findpet
```

Execute a aplicação:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw spring-boot:run
```

A API será iniciada em:

```txt
http://localhost:8080
```

---


## 👩‍💻 Desenvolvedora

Desenvolvido por **Ketlin Oliveira**.

GitHub: [KetlinOlliveira](https://github.com/KetlinOlliveira)

---

## 🐾 Status

```txt
🚧 Em desenvolvimento
```

> Todo lar que acolhe um animal torna-se mais completo.

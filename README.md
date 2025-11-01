# 🚀 Sistema de Cursos - Backend & Frontend (Monorepo)

Este repositório contém o código-fonte para um sistema de gestão de cursos. Ele está estruturado como um monorepo, utilizando **Java/Spring Boot** para ambos os módulos e **Tailwind CSS** para o desenvolvimento de assets do frontend.

## 🛠️ Pré-requisitos

Certifique-se de que os seguintes itens estão instalados em sua máquina de desenvolvimento:

* **Java Development Kit (JDK) 17+**
* **Apache Maven (3.x)**
* **Node.js (18+ LTS)** e **npm**
* **Docker** e **Docker Compose** (Necessário para o banco de dados do Backend)

## 📁 Estrutura do Projeto

O projeto está dividido em dois módulos principais:

| Módulo | Descrição | Tecnologias Chave | Porta Padrão |
| :--- | :--- | :--- | :--- |
| **`back_sistema_curso/`** | API REST principal, lida com dados e lógica de negócio. | Spring Boot, Maven, Docker Compose (DB) | **8080** |
| **`front_sistema_curso/`** | Frontend/BFF que serve páginas e lida com a apresentação. | Spring Boot, Maven, Tailwind CSS | **8082** |

## ⚙️ Inicialização do Ambiente (Desenvolvimento)
Você precisará de **três terminais** abertos simultaneamente.

#### 1. Iniciar o Banco de Dados (DB Docker)
O banco de dados (configurado no `back_sistema_curso/docker-compose.yml`) deve ser iniciado primeiro.

```bash
cd back_sistema_curso
docker-compose up -d
```

#### 2. Iniciar o Backend (Porta 8080)
Abra o Terminal 1 e execute:

```bash
cd back_sistema_curso
mvn spring-boot:run
```
### 3. Iniciar o Frontend/BFF (Porta 8082)
Abra o Terminal 2 e execute:

```bash
cd front_sistema_curso
mvn spring-boot:run
```
Nota: Se a porta 8082 não for assumida automaticamente pelo seu projeto, adicione a flag: mvn spring-boot:run -Dserver.port=8082.

### 4. Iniciar o Tailwind CSS Watcher
Abra o Terminal 3 para que o Tailwind CSS monitore as alterações nos arquivos do frontend e gere o CSS em tempo real.

```bash
cd front_sistema_curso/src/frontend
npm run tailwind:dev
```

## 💻 Acessando a Aplicação

Com todos os serviços rodando:

| Serviço | Porta | URL |
| :--- | :--- | :--- |
| **Frontend/BFF** | 8082 | `http://localhost:8082` |
| **Backend API** | 8080 | `http://localhost:8080` |

---

## 🛑 Como Parar os Serviços

1.  Pressione `Ctrl + C` em cada terminal onde os processos `mvn` e `npm` estão rodando.
2.  Para parar e remover os containers do banco de dados:
    ```bash
    cd back_sistema_curso
    docker-compose down
    ```

---

## 🤝 Contribuições

Sinta-se à vontade para abrir *issues* ou enviar *Pull Requests*.

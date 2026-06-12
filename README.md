# MyCar - Oficina de Carros

Aplicação web Spring Boot MVC para gerenciamento de oficina de carros, desenvolvida com tecnologias modernas.

## 🚗 Sobre o Projeto

MyCar é uma solução web para gestão de serviços de oficina mecânica, permitindo:
- Dashboard com informações gerais
- Página de relatórios
- Interface responsiva com SB Admin 2 template
- Integração com banco de dados MySQL

## 🛠️ Tecnologias Utilizadas

- **Framework**: Spring Boot 3.5.7
- **Linguagem**: Java 21
- **Template Engine**: Thymeleaf 3.1.3
- **Banco de Dados**: MySQL 8.0
- **Build Tool**: Maven 3.9.9
- **Server**: Apache Tomcat 10.1.48
- **Pool de Conexão**: HikariCP
- **UI Framework**: Bootstrap 4 com SB Admin 2

## 📋 Pré-requisitos

- Java 21 ou superior
- Maven 3.9.9 ou superior
- MySQL 8.0 ou superior
- Git

## 🚀 Como Executar

### 1. Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/mycar.git
cd mycar
```

### 2. Configurar Banco de Dados

Crie uma base de dados MySQL:
```sql
CREATE DATABASE mycar;
```

Atualize as credenciais no arquivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mycar?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Compilar o Projeto
```bash
mvn clean install
```

### 4. Executar a Aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: **http://localhost:9092**

## 📁 Estrutura do Projeto

```
SpringWeb/
├── src/
│   ├── main/
│   │   ├── java/com/web/mycar/
│   │   │   └── contollers/
│   │   │       └── HomeController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/          # Arquivos CSS, JS, imagens
│   │       │   └── vendor/      # Bootstrap, jQuery, FontAwesome
│   │       └── templates/
│   │           ├── compartilhado/
│   │           │   ├── topo.html       # Header reutilizável
│   │           │   └── rodape.html     # Footer reutilizável
│   │           └── home/
│   │               ├── index.html      # Dashboard principal
│   │               └── ralatorios.html # Página de relatórios
│   └── test/
├── pom.xml
└── README.md
```

## 🗂️ Componentes Principais

### HomeController
Controlador principal que gerencia as rotas:
- `GET /` ou `/index` → Dashboard
- `GET /relatorios` → Página de Relatórios

### Templates Thymeleaf
- **topo.html**: Navbar com branding "MyCar - Oficina de Carros"
- **rodape.html**: Footer com informações da empresa
- **index.html**: Dashboard com cards e gráficos
- **ralatorios.html**: Página de relatórios

## ⚙️ Configurações

### application.properties
```properties
# Servidor
server.port=9092

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/mycar?...
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update

# Thymeleaf
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# DevTools
spring.devtools.restart.enabled=true
```

## 🔄 Workflow Git

Cada atualização importante gera um commit com mensagem descritiva em Português:
```bash
git commit -m "feat: adicionar nova funcionalidade"
git commit -m "fix: corrigir bug na página X"
git commit -m "refactor: organizar templates"
```

## 📝 Contribuindo

1. Faça um Fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adicionar MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT.

## 👤 Autor

José Neto de Oliveira Silva

## 📞 Contato

joseneto200462@gmail.com 
Charllangs souza de oliveira
+5562985744360

---

**Status**: ✅ Em desenvolvimento e pronto para uso

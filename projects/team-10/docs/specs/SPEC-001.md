# SPEC-001 — Tela de Login e Apresentação do Alerta de Fogo


## 1. Context


Durante períodos de estiagem e alto índice de queimadas, a população precisa de um meio rápido e confiável para registrar ocorrências, consultar informações preventivas e receber orientações sobre riscos de incêndio. O aplicativo Alerta de Fogo tem como objetivo apoiar a conscientização ambiental e facilitar, futuramente, a comunicação de focos de incêndio e queimadas em áreas urbanas e rurais.
A tela inicial representa a porta de entrada do sistema, apresentando a identidade visual do aplicativo, permitindo o acesso de usuários cadastrados e fornecendo informações básicas sobre a finalidade da plataforma e seus benefícios para a prevenção e monitoramento de queimadas.

## 2. Task


Desenvolver a interface gráfica da tela inicial de Login e Acesso do aplicativo Alerta de Fogo utilizando Kotlin e Jetpack Compose com Material Design 3.
A tela deve apresentar a identidade visual do aplicativo, campos de entrada para E-mail e Senha, botão principal de autenticação ("Entrar"), botão informativo ("Sobre o Aplicativo") contendo informações sobre a prevenção de queimadas e funcionamento da plataforma.

## 3. Functional Requirements

- **FR-01 —** Exibir no cabeçalho o nome "Alerta de Fogo", logotipo do aplicativo, subtítulo informativo e mensagem de conscientização ambiental.

- **FR-02 —** Disponibilizar campos de texto para inserção de E-mail e Senha, com rótulos visíveis e proteção dos caracteres digitados na senha.

- **FR-03 —** Fornecer botão de ação primária ("Entrar") destacado visualmente para acesso ao sistema.

- **FR-04 —** Disponibilizar botão informativo ("Sobre o Aplicativo") que acione uma caixa de diálogo modal (AlertDialog) contendo informações sobre prevenção de queimadas, importância do monitoramento ambiental e objetivos da plataforma.

## 4. Constraints

- **Tecnologias:** Kotlin 2.x, Jetpack Compose com Material Design 3.
- **Versão do Android:** `minSdk = 24`, `compileSdk = 37`, `targetSdk = 37`.
- **Isolamento de Diretório:** Alterações restritas exclusivamente à pasta `projects/team-10/`.
- **Restrição de Escopo:** Esta sprint contempla apenas a interface visual da tela de Login. Funcionalidades de autenticação real, banco de dados, geolocalização, abertura de alertas e notificações serão implementadas em sprints futuras.

## 5. Acceptance Criteria

- **AC-01 —** O cabeçalho com o nome "Alerta de Fogo", identidade visual do aplicativo e mensagem de conscientização ambiental está visível no topo da tela.

- **AC-02 —** Os campos de entrada de E-mail e Senha estão visíveis e permitem digitação interativa pelo usuário.

- **AC-03 —** O botão ("Entrar") possui destaque visual, contraste adequado e responde ao toque.

- **AC-04 —** O diálogo informativo apresenta informações sobre riscos de queimadas, prevenção de incêndios e objetivo do sistema de alertas.

- **AC-05 —** O botão ("Solicitar Cadastro") está presente na tela para novos usuários.

- **AC-06 —** O layout utiliza Column, Spacer e Modifier sem sobreposição de componentes ou cortes na tela.

- **AC-07 —** O projeto compila no Android Studio / Gradle sem erros e executa no emulador/dispositivo sem travamentos.

## 6. Validation

1. Compilar o projeto no Gradle;
2. Executar no emulador AVD ou celular físico; 
3. Digitar nos campos de E-mail e Senha;
4. Testar o clique no botão 'Entrar', 'Sobre o Aplicativo';
5. Tirar screenshot e salvar em `projects/team-10/evidence/sprint-01/first-screen.png`.

## 7. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `LoginScreen.kt` (Header Column, Logo, Text) | AC-01 | `first-screen.png` |
| FR-02 | `LoginScreen.kt` (OutlinedTextFields email e password) | AC-02 | `first-screen-interactive.png` |
| FR-03 | `LoginScreen.kt` (Button "Entrar") | AC-03 | `first-screen.png` |
| FR-04 | `LoginScreen.kt` (OutlinedButton "Sobre o Aplicativo" + AlertDialog) | AC-04 | `first-screen-about-dialog.png` |

## 8. Validation Results

| Acceptance Criterion | Result | Notes                                                                                                 |
| -------------------- | ------ | ----------------------------------------------------------------------------------------------------- |
| AC-01                | PASS   | Cabeçalho visível com logotipo oficial, título Alerta de Fogo.                                   |
| AC-02                | PASS   | Campos de E-mail e Senha funcionais com digitação e máscara de senha.                                 |
| AC-03                | PASS   | Botão ("Entrar") destacado com alto contraste e clique funcional.                                     |
| AC-04                | PASS   | Botão ("Sobre o Aplicativo") abre modal com informações gerais de queimadas  |
| AC-05                | PASS   | Diálogo informativo apresenta orientações sobre riscos de queimadas, prevenção de incêndios e uso do aplicativo.         |
| AC-06                | PASS   | Layout estruturado com Column e Spacer, sem sobreposição ou corte de componentes na tela              |
| AC-07                | PASS   | Compilação com Gradle OK (`./gradlew assembleDebug`) e execução sem erros no emulador                 |

### Environment Used for Validation

- **Device:** Android Studio Emulator Pixel 8
- **Android version / API:** API 37
- **Build result:** PASS (`./gradlew assembleDebug` - BUILD SUCCESSFUL)
- **Application execution:** PASS (Execução contínua sem crash)

## 9. AI Usage

### Summary Table

| Item                           | Team response                                                                                                             |
| ------------------------------ | ------------------------------------------------------------------------------------------------------------------------- |
| LLM/tool used                  | Google Antigravity (Gemini).                                                                                              |
| Task supported by the LLM      | Estruturação finicial da especificação SPEC-001 adaptada para tela de Login.                                              |
| Main suggestion received       | Formulação técnica dos requisitos funcionais (FR-01 a FR-05) e critérios de aceitação mensuráveis (AC-01 a AC-07).        |
| What the team changed manually |Adaptação completa para o contexto de monitoramento e prevenção de queimadas|
| How the result was validated   | Validação visual e funcional dos requisitos no emulador Android Studio .                                           |

### Tool
Google Antigravity (Gemini).

### Purpose

A ferramenta foi utilizada como apoio na organização estrutural da documentação da sprint e na revisão dos requisitos funcionais.


### Generated Content

A IA auxiliou na criação da estrutura inicial do documento, sugerindo a organização de seções, requisitos funcionais, critérios de aceitação e validação do sistema.

### Human Changes

A equipe adaptou integralmente a especificação para o projeto Alerta de Fogo, definindo os objetivos do aplicativo, os fluxos da tela inicial, as informações de conscientização ambiental e os critérios de validação relacionados à prevenção de queimadas.


---

## 10. Out of Scope

- 
- Autenticação real de usuários;
- Banco de dados Room ou SQLite;
- Integração com APIs externas;
- Geolocalização de focos de incêndio;
- Notificações em tempo real;
- Cadastro funcional de usuários;
- Painel administrativo;
- Abertura e acompanhamento de ocorrências.


---

## 11. Deliverables

- `projects/team-10/SPRINT-01.md` — Documento de Definição de Produto e objetivos da Sprint;
- `projects/team-10/docs/specs/SPEC-001.md` — Arquivo de especificação formal da tela inicial;
- `projects/team-10/app/` — Código-fonte da tela implementada em Kotlin e Jetpack Compose;
- `projects/team-10/evidence/sprint-01/first-screen.png` — Screenshot da tela em execução (padrão do curso).

---

## 12. Status

- [x] Specification completed
- [x] Implementation completed
- [x] Acceptance criteria validated
- [x] Code reviewed
- [x] Commit created
- [x] Pull Request submitted

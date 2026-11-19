# SPRINT 07 — Final Integration, Quality & Delivery

## Team Identification

- **Team:** Team 05
- **Project:** SARC (Sistema de Acionamento e Resposta Cívico-Militar)
- **Institution:** Escola Estadual Cívico-Militar Maria de Lima Cadidé
- **Members:**
  - Matheus Gabriel de Morais Barros ([@MatheusGabriel-25](https://github.com/MatheusGabriel-25))
  - Paulo Vitor ([@PAULOSANTOS1309](https://github.com/PAULOSANTOS1309))

---

## 1. Sprint Goal & Scope

Consolidar todas as entregas das Sprints 01 a 06 em um aplicativo integrado, estável e sem regressões, revisar todas as especificações (`SPEC-001` a `SPEC-006`), completar o `README.md` do time, higienizar o repositório e produzir as evidências finais da disciplina.

> **Sem funcionalidade principal nova:** conforme o edital da Sprint 07, nenhuma nova tela ou funcionalidade principal foi adicionada nesta etapa; o foco é validação ponta a ponta, consistência e documentação final.

Detalhes completos e rastreabilidade em [`docs/specs/SPEC-007.md`](docs/specs/SPEC-007.md).

---

## 2. Review of All Previous SPECs

| Specification | Main feature | Current status | Evidence |
| --- | --- | --- | --- |
| `SPEC-001` | Tela de Login e identidade institucional (`WelcomeScreen.kt`) | **PASS** *(revalidada no fluxo final)* | `evidence/sprint-01/first-screen.png`, `evidence/sprint-07/final-validation.md` |
| `SPEC-002` | Modais interativos ("Sobre o SARC" e "Solicitação de Cadastro") | **PASS** *(revalidada no fluxo final)* | `evidence/sprint-02/before-interaction.png`, `evidence/sprint-02/after-interaction.png`, `evidence/sprint-07/final-validation.md` |
| `SPEC-003` | Navegação Login ↔ Painel do Docente (`MainActivity.kt`, `HomeDocenteScreen.kt`) | **PASS** *(revalidada no fluxo final)* | `evidence/sprint-03/screen-a.png`, `evidence/sprint-03/screen-b.png`, `evidence/sprint-07/final-validation.md` |
| `SPEC-004` | Formulário "Novo acionamento" e validação (`NovoAcionamentoScreen.kt`) | **PASS** *(revalidada no fluxo final)* | `evidence/sprint-04/invalid-input.png`, `evidence/sprint-04/valid-input.png`, `evidence/sprint-07/final-feature.png` |
| `SPEC-005` | Persistência local com Room (`AcionamentoDatabase.kt`, lista e "Resolvido") | **PASS** *(revalidada após `force-stop`)* | `evidence/sprint-05/data-created.png`, `evidence/sprint-05/data-restored.png`, `evidence/sprint-07/final-home.png` |
| `SPEC-006` | Notificação local nativa e fallback defensivo (`NotificacaoSarc.kt`) | **PASS** *(permitida e negada revalidadas)* | `evidence/sprint-06/integration-success.png`, `evidence/sprint-06/integration-alternative-state.png`, `evidence/sprint-07/final-feature.png` |
| `SPEC-007` | Qualidade final, regressão de 9 passos, limpeza e entrega | **PASS** *(validada no `Pixel_8_API_37`)* | `evidence/sprint-07/final-home.png`, `evidence/sprint-07/final-feature.png`, `evidence/sprint-07/final-validation.md` |

---

## 3. Regression Testing (9-Step Checklist)

> **Status:** **PASS (9/9)** — Executado a partir de instalação limpa no emulador `Pixel_8_API_37` (Android 17 / API 37) em 18/11/2026; registro completo em [`evidence/sprint-07/final-validation.md`](evidence/sprint-07/final-validation.md).

| Passo | Cenário do Edital | Resultado |
| :---: | :--- | :---: |
| 1 | Abertura limpa do aplicativo (`WelcomeScreen`) | **PASS** |
| 2 | Usabilidade da tela inicial (e-mail, senha, visibilidade, modais) | **PASS** |
| 3 | Navegação (`Login → Painel do Docente → Novo acionamento → Painel → Sair`) | **PASS** |
| 4 | Estado/interação (cartões expansíveis, guia de 3 etapas, botão `Resolvido` → `✓ Resolvido`) | **PASS** |
| 5 | Validação de formulário (campos obrigatórios vazios e limite de 100 caracteres) | **PASS** |
| 6 | Persistência local (`sarc.db` sobrevive ao fechamento completo e reabertura) | **PASS** |
| 7 | Integração de dispositivo (notificação permitida e fallback com aviso amarelo quando negada) | **PASS** |
| 8 | Botão Voltar do Android (fecha diálogos e retorna entre telas corretamente) | **PASS** |
| 9 | Estabilidade (`0` ocorrências de `FATAL EXCEPTION` no `logcat`) | **PASS** |

---

## 4. Usability, Consistency & Repository Cleanup

- **Usabilidade e consistência:** revisão do código em `app/src/main/java/com/team05/sarc/` confirmou rótulos em português padronizados, altura mínima de toque (`48.dp`) nos botões principais, tratamento de teclado virtual (`imePadding()`) no formulário e contraste adequado (`SarcNavyDark` / aviso amarelo `#664D03` sobre `#FFF3CD`).
- **Higienização do repositório:** confirmar antes do commit final que `.gradle/`, `.idea/`, `build/`, `local.properties` e eventuais chaves/segredos não estão rastreados pelo Git (`git status --ignored` e busca em `app/src`).
- **README do time:** criado em [`projects/team-05/README.md`](README.md) com nome do produto, problema, público-alvo, funcionalidades, tecnologias, instruções de build/execução, configurações, limitações conhecidas e integrantes.

---

## 5. Known Limitations

1. **Autenticação demonstrativa:** a tela inicial de login não valida credenciais em servidor externo.
2. **Armazenamento exclusivamente local:** os acionamentos são gravados em `sarc.db` no aparelho do docente, sem envio em tempo real para outro dispositivo (Coordenação Militar).
3. **Listas estáticas de turmas e salas:** turmas (`6º A`–`9º B`) e salas (`01`–`08`) são fixas nesta versão acadêmica.
4. **Pedido de permissão único por abertura:** se o docente negar `POST_NOTIFICATIONS`, o app não repete o prompt na mesma sessão (comportamento intencional; pode ser reativado nas Configurações do Android).

---

## 6. Acceptance Criteria (Curso)

- [x] **AC-01** — `SPEC-007.md` existe (`docs/specs/SPEC-007.md`).
- [x] **AC-02** — Todas as especificações anteriores revisadas e revalidadas no fluxo final.
- [x] **AC-03** — Fluxo principal do produto funciona a partir da abertura limpa.
- [x] **AC-04** — Navegação funciona ponta a ponta.
- [x] **AC-05** — Interações de estado funcionam sem falhas.
- [x] **AC-06** — Validação de formulário funciona conforme a `SPEC-004`.
- [x] **AC-07** — Persistência funciona conforme a `SPEC-005`.
- [x] **AC-08** — Integração de notificação funciona ou degrada de forma defensiva conforme a `SPEC-006`.
- [x] **AC-09** — Nenhum crash crítico no fluxo testado (`0 FATAL EXCEPTION`).
- [x] **AC-10** — Evidências finais reais completas em `evidence/sprint-07/`.
- [x] **AC-11** — `projects/team-05/README.md` completo (revisar junto da entrega).
- [x] **AC-12** — Nenhum segredo ou pasta de build versionado (validar com `git status`).
- [x] **AC-13** — Requisitos rastreados ao código e evidências nas SPECs 001 a 007.
- [x] **AC-14** — Todos os membros sabem explicar o projeto (`Problem → Product goal → SPEC → Implementation → Validation → Evidence`).

---

## 7. Evidence

- `evidence/sprint-07/final-home.png` *(Painel do Docente após `force-stop` e reabertura, exibindo histórico persistido no Room com item pendente e item `✓ Resolvido`)*
- `evidence/sprint-07/final-feature.png` *(Novo acionamento preenchido com diálogo `"Acionamento pronto"`, `"✅ Notificação enviada ao aparelho."` e notificação heads-up `"SARC — Acionamento registrado"`)*
- `evidence/sprint-07/final-validation.md` *(Registro completo do dispositivo `Pixel_8_API_37`, build e execução real dos 9 passos de regressão)*

---

## 8. AI Usage

| Item | Team response |
| --- | --- |
| LLM/tool used | Claude Code (Anthropic) e Google Antigravity (Gemini) |
| Task supported by the LLM | Revisar o edital da Sprint 07, auditar as `SPEC-001` a `SPEC-006` frente ao código, completar a `SPEC-007.md`, revisar o `README.md`, montar o checklist de regressão de 9 passos, executar a bateria no emulador via `adb`/`android` CLI e preencher `final-validation.md` e `SPRINT-07.md` |
| Main suggestion received | Manter zero funcionalidade nova na Sprint 07, consolidar a rastreabilidade de `SPEC-001` a `SPEC-007` e validar o fluxo completo a partir de instalação limpa no emulador com monitoramento contínuo do `logcat` |
| What the team changed manually | O Matheus verificou e validou cada etapa (planejamento, execução no `Pixel_8_API_37`, higienização do repositório), revisou tudo o que foi feito e aprovou visualmente os prints finais (`final-home.png` e `final-feature.png`) antes da abertura do PR |
| How the result was validated | `./gradlew assembleDebug` (`BUILD SUCCESSFUL in 18s`); instalação limpa no `Pixel_8_API_37` (Android 17 / API 37); execução dos 9 passos de regressão com monitoramento contínuo de `logcat` (8.984 linhas, `0` `FATAL EXCEPTION`, `0` ANRs); checagem de higienização com `git status --ignored` |

---

## 9. Deliverables

- `projects/team-05/app/`
- `projects/team-05/README.md`
- `projects/team-05/SPRINT-07.md`
- `projects/team-05/docs/specs/SPEC-001.md` ... `SPEC-007.md`
- `projects/team-05/evidence/sprint-07/final-home.png`
- `projects/team-05/evidence/sprint-07/final-feature.png`
- `projects/team-05/evidence/sprint-07/final-validation.md`

---

## 10. Final Definition of Done

- [x] `SPEC-007.md` completa e validada.
- [x] Testes de regressão (9 passos) executados no emulador.
- [x] Defeitos críticos corrigidos ou documentados.
- [x] `README.md` revisado e completo.
- [x] Evidências finais reais e completas em `evidence/sprint-07/`.
- [x] Repositório limpo (sem `build/`, `.gradle/`, `.idea/`, `local.properties` ou segredos).
- [x] Uso de IA documentado e finalizado.
- [ ] PR final submetido até **23/11/2026** (`[Sprint 07] Team 05 — Final Integration, Quality & Delivery`).

# SPEC-007: Qualidade Final, Integração Completa e Entrega (SARC)

> **Team:** Team 05  
> **Sprint:** Sprint 07 (Final Integration, Quality & Delivery)  
> **Status:** Validated (Ready for PR)  
> **Related Sprint:** `SPRINT-07.md`  
> **Base:** `SPEC-001.md` a `SPEC-006.md`

---

## 1. Context

**Problem:**

Ao longo das Sprints 01 a 06, o SARC ganhou identidade visual, modais informativos, navegação entre telas, formulário com validação, persistência local com Room e notificações nativas no aparelho. Antes da entrega final da disciplina, é necessário comprovar que todas essas partes funcionam juntas de ponta a ponta sem regressões, sem arquivos temporários ou segredos no repositório e com documentação suficiente para qualquer pessoa compilar, rodar e entender o projeto.

**User / Actor:**

O **docente** da Escola Cadidé (usuário final do protótipo) e a **equipe / avaliador** (que precisam validar, compilar e demonstrar o aplicativo completo).

**Usage Context:**

Uso completo do aplicativo a partir de uma instalação limpa no aparelho/emulador: login, consulta às informações institucionais, navegação até o Painel do Docente, envio de acionamentos (válidos e inválidos), persistência após fechamento e notificação local (permitida e bloqueada), além do fluxo de retorno pelo botão Voltar do Android.

---

## 2. Objective

O objetivo desta especificação é validar a integração completa do SARC, garantir consistência visual e estabilidade sem crashes críticos em toda a jornada do usuário, revisar todas as especificações anteriores (`SPEC-001` a `SPEC-006`) e consolidar a documentação e evidências finais de entrega (`README.md`, `SPRINT-07.md` e `evidence/sprint-07/`), sem adicionar novas funcionalidades principais.

---

## 3. User Scenario

**Given** o aplicativo SARC recém-instalado no aparelho/emulador,  
**When** o docente abre o app, consulta os modais da tela inicial, entra no Painel do Docente, testa validações de erro, envia um acionamento válido, fecha e reabre o app, marca um acionamento como resolvido, testa o envio com notificação permitida e bloqueada e utiliza o botão Voltar do Android,  
**Then** todos os fluxos funcionam sem travamentos ou crashes (`FATAL EXCEPTION`), os dados permanecem salvos no Room, a interface mantém consistência visual e todas as evidências finais comprovam o funcionamento integral.

---

## 4. Functional Requirements

### FR-01: Fluxo inicial e interação da tela de boas-vindas (SPEC-001 e SPEC-002)

A partir de uma execução limpa, o aplicativo deve abrir na tela inicial (`WelcomeScreen.kt`) com identidade institucional da Escola Cadidé, exibir os campos de acesso e manter funcionais e legíveis os modais *"Sobre o SARC"* (com as 4 faixas de prioridade expansíveis) e *"Solicitar Cadastro"*.

### FR-02: Navegação e botão Voltar do Android (SPEC-003)

A navegação entre `WelcomeScreen`, `HomeDocenteScreen` e `NovoAcionamentoScreen` (via `NavHost` em `MainActivity.kt`) deve ocorrer sem falhas, e o botão Voltar do sistema Android deve fechar diálogos abertos ou retornar à tela esperada sem encerrar o app indevidamente durante o fluxo ativo.

### FR-03: Validação de formulário e feedback de erro (SPEC-004)

Na tela `NovoAcionamentoScreen.kt`, tentativas de envio com campos obrigatórios vazios (nível, turma ou sala) ou situação acima de 100 caracteres devem bloquear o envio e exibir mensagens claras ao usuário, sem gravar registros inválidos nem disparar notificações.

### FR-04: Persistência local contínua (SPEC-005)

Acionamentos válidos gravados via `AcionamentoDatabase.kt` devem aparecer na seção *"Meus acionamentos"* do Painel do Docente, aceitar a marcação de *"Resolvido"* e permanecer salvos após fechar completamente e reabrir o aplicativo.

### FR-05: Integração de notificação e fallback defensivo (SPEC-006)

Com a permissão concedida, o envio válido deve disparar a notificação local (`NotificacaoSarc.kt`) e exibir confirmação de sucesso no diálogo; com a permissão negada ou notificações desativadas, o app não deve travar, devendo exibir o aviso defensivo em amarelo e salvar o registro normalmente no Room.

### FR-06: Consistência de interface e zero crash na jornada completa

Todas as telas devem manter alinhamento, espaçamentos, rótulos em português consistentes, estados vazios legíveis e nenhum `FATAL EXCEPTION` no `logcat` durante a execução da bateria completa de regressão (9 passos do edital). Ajustes permitidos nesta Sprint limitam-se a correções pontuais de defeitos, texto ou layout caso algum `FAIL` apareça na revisão.

### FR-07: Higienização do repositório e documentação final

O diretório `projects/team-05/` não deve conter pastas geradas (`.gradle/`, `.idea/`, `build/`, `local.properties`) nem segredos/tokens, e deve incluir o `README.md` completo do time e as evidências finais em `evidence/sprint-07/`.

---

## 5. Constraints

### Required Technologies

- Kotlin e Jetpack Compose (Material Design 3)
- Room + KSP (já configurados no projeto)
- AndroidX Core (`NotificationCompat` / `NotificationManagerCompat`, já configurados)
- Gradle Wrapper (`./gradlew`) e Android Studio / Emulador `Pixel_8_API_37`

### Project Constraints

- **Nenhuma funcionalidade principal nova** pode ser adicionada nesta Sprint.
- Manter `minSdk = 24`, `compileSdk = 37`, `targetSdk = 37` e `applicationId = "com.team05.sarc"`.
- Manter o código e a documentação estritamente dentro de `projects/team-05/`.
- Evidências e resultados de teste devem ser reais, obtidos após a última alteração de código.

### Files or Modules That Must Not Be Modified

- Pastas de outros times (`projects/team-XX` diferentes de `team-05`) e arquivos globais do curso.
- Estrutura arquitetural principal do app (permitidas apenas correções pontuais se a regressão detectar algum defeito).

---

## 6. Out of Scope

- Perfis separados (Monitor Militar / Gestão) e telas novas além das já existentes.
- Backend remoto, API REST, Firebase/Supabase ou envio de notificações entre aparelhos diferentes.
- Autenticação real por matrícula/e-mail institucional ou relatórios em PDF.
- Redesign completo do aplicativo de última hora.

---

## 7. Acceptance Criteria

### AC-01: Revisão completa das especificações anteriores
**Related requirement:** FR-01 a FR-05  
**Condition:** As especificações `SPEC-001` a `SPEC-006` foram revisadas e avaliadas como `PASS` (ou com limitação conhecida documentada) na tabela de `SPRINT-07.md`.

### AC-02: Jornada completa de regressão aprovada (9 passos)
**Related requirement:** FR-01 a FR-06  
**Condition:** Executando a partir de instalação limpa no emulador `Pixel_8_API_37`, os 9 passos do checklist do edital (abertura → tela inicial → navegação → estados/modais → validação → persistência → notificação aceita/negada → botão Voltar → estabilidade) passam sem erro.

### AC-03: Compilação limpa e execução sem crash
**Related requirement:** FR-06  
**Condition:** `./gradlew assembleDebug` conclui com `BUILD SUCCESSFUL` e o `logcat` registra `0` ocorrências de `FATAL EXCEPTION` durante toda a bateria de testes.

### AC-04: Consistência visual e limpeza do repositório
**Related requirement:** FR-06, FR-07  
**Condition:** Telas e textos estão consistentes e legíveis; `git status` e árvore de `projects/team-05/` não contêm `.gradle/`, `.idea/`, `build/`, `local.properties` nem chaves/senhas.

### AC-05: Documentação e evidências finais completas
**Related requirement:** FR-07  
**Condition:** Existem e estão preenchidos com dados reais: `projects/team-05/README.md`, `projects/team-05/SPRINT-07.md`, `projects/team-05/docs/specs/SPEC-007.md`, `evidence/sprint-07/final-home.png`, `evidence/sprint-07/final-feature.png` e `evidence/sprint-07/final-validation.md`.

---

## 8. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `WelcomeScreen.kt` | AC-01, AC-02 | `evidence/sprint-07/final-home.png` |
| FR-02 | `MainActivity.kt` (`NavHost`) | AC-01, AC-02 | `evidence/sprint-07/final-validation.md` |
| FR-03 | `NovoAcionamentoScreen.kt` | AC-01, AC-02 | `evidence/sprint-07/final-feature.png` |
| FR-04 | `AcionamentoDatabase.kt`, `HomeDocenteScreen.kt` | AC-01, AC-02 | `evidence/sprint-07/final-home.png` |
| FR-05 | `NotificacaoSarc.kt`, `MainActivity.kt`, `NovoAcionamentoScreen.kt` | AC-01, AC-02 | `evidence/sprint-07/final-validation.md` |
| FR-06 | Fluxo completo (`app/src/main/java/com/team05/sarc/`) | AC-02, AC-03, AC-04 | `evidence/sprint-07/final-validation.md` |
| FR-07 | `projects/team-05/README.md`, `.gitignore`, `SPRINT-07.md` | AC-04, AC-05 | `evidence/sprint-07/final-validation.md` |

---

## 9. Implementation Plan

### Components to Create or Modify

- `projects/team-05/docs/specs/SPEC-007.md` (novo)
- `projects/team-05/README.md` (novo — visão geral do produto, stack, build, limitações conhecidas e equipe)
- `projects/team-05/SPRINT-07.md` (novo — revisão `SPEC-001` a `SPEC-007`, tabela de IA e critérios finais)
- `projects/team-05/evidence/sprint-07/final-home.png`, `final-feature.png` e `final-validation.md` (novos)
- Arquivos em `app/` somente se a bateria de regressão identificar algum defeito ou ajuste fino necessário.

### Expected Validation Flow

```text
Instalação limpa no Pixel_8_API_37
  ↓
Execução dos 9 passos de regressão (SPEC-001 a SPEC-006)
  ↓
Correção pontual (se houver FAIL) + novo build ./gradlew assembleDebug
  ↓
Captura de final-home.png, final-feature.png e log em final-validation.md
  ↓
Revisão do README.md, limpeza do Git e preenchimento do SPRINT-07.md
```

---

## 10. Validation Plan

1. Sincronizar a branch `main` e criar `team-05/sprint-07`.
2. Compilar o APK com `./gradlew assembleDebug` e conferir ausência de arquivos proibidos no Git.
3. Instalar o app limpo no emulador `Pixel_8_API_37` e executar o checklist de 9 passos monitorando o `logcat`.
4. Revisar cada `SPEC` (`001` a `006`) comparando o código atual com os requisitos originais.
5. Capturar `final-home.png` e `final-feature.png` e registrar o dispositivo e resultados reais em `final-validation.md`.

---

## 11. Validation Results

> **Nota:** Resultados preenchidos a partir da execução real no emulador `Pixel_8_API_37` após o build final de 18/11/2026.

| Acceptance Criterion | Result | Notes |
| --- | --- | --- |
| AC-01 | PASS | `SPEC-001` a `SPEC-006` revisadas e revalidadas como `PASS` na tabela de `SPRINT-07.md` |
| AC-02 | PASS | Bateria completa de regressão (9 passos) executada a partir de instalação limpa no `Pixel_8_API_37` (`final-validation.md`) |
| AC-03 | PASS | `./gradlew assembleDebug` concluído com `BUILD SUCCESSFUL in 18s`; `logcat` (8.984 linhas) com `0` ocorrências de `FATAL EXCEPTION` e `0` ANRs |
| AC-04 | PASS | Interfaces e textos em português consistentes; `git status --ignored` confirma `.gradle/`, `build/`, `local.properties` ignorados e `0` segredos/permissão `INTERNET` em `app/src/` |
| AC-05 | PASS | Documentação e evidências completas em `README.md`, `SPRINT-07.md`, `SPEC-007.md`, `final-home.png`, `final-feature.png` e `final-validation.md` |

### Environment Used for Validation

- **Device:** Android Studio Emulator — Pixel 8 (`Pixel_8_API_37`, serial `emulator-5554`, `sdk_gphone64_x86_64`)
- **Android version / API:** Android 17 (API 37)
- **Build result:** `BUILD SUCCESSFUL in 18s` (`./gradlew assembleDebug`)
- **Application execution:** `PASS` nos 9 passos de regressão monitorados via `logcat` (`0` crashes)

---

## 12. Evidence

Arquivos obrigatórios a gerar após o último build em `projects/team-05/evidence/sprint-07/`:

- `evidence/sprint-07/final-home.png` — Tela principal / Painel do Docente em estado final validado
- `evidence/sprint-07/final-feature.png` — Funcionalidade principal em execução (`Novo acionamento` / confirmação ou histórico integrado)
- `evidence/sprint-07/final-validation.md` — Registro do dispositivo, cenário de testes de 9 passos e resultados reais

---

## 13. AI-Assisted Development

### AI Tool(s)

- **Tool:** Claude Code (Anthropic) e Google Antigravity (Gemini)

### How AI Was Used

- [x] Understanding the requirement
- [x] Refining the specification
- [ ] Generating implementation suggestions *(sem funcionalidade nova na Sprint 07)*
- [x] Explaining code
- [ ] Debugging
- [ ] Refactoring
- [x] Generating test ideas
- [x] Reviewing acceptance criteria

### Prompt or Request Summary

A equipe utilizou apoio da IA para revisar o edital `sprints/SPRINT-07.md`, auditar as especificações anteriores (`SPEC-001` a `SPEC-006`) frente ao código atual, estruturar a `SPEC-007.md`, revisar o `README.md`, montar o roteiro de regressão de 9 passos e preparar a estrutura do relatório `SPRINT-07.md`.

### AI-Generated or Suggested Content

A IA propôs a estrutura da `SPEC-007.md` (requisitos FR-01 a FR-07 e critérios AC-01 a AC-05 focados em qualidade e não-regressão, sem nova feature), o rascunho do `README.md` e o esqueleto do `SPRINT-07.md` com a tabela de rastreabilidade das SPECs 001–007.

### Human Review and Changes

O Matheus verificou e validou cada etapa do processo (planejamento, `./gradlew assembleDebug`, os 9 passos de regressão no emulador `Pixel_8_API_37` com monitoramento do `logcat`, a limpeza do Git e os prints finais), revisando e aprovando tudo o que foi feito antes da entrega do PR (nenhuma alteração adicional de código Kotlin foi necessária durante a regressão).

### AI Validation

- [x] O conteúdo gerado pela IA foi revisado antes de ser usado.
- [x] A equipe entende e sabe explicar todas as partes do aplicativo (`AC-14`).
- [x] A implementação final foi compilada e executada após a última revisão.
- [x] Todos os critérios de aceite foram validados manualmente no emulador.
- [x] Nenhuma funcionalidade principal nova foi adicionada na Sprint 07.

---

## 14. Deliverables

- [x] `projects/team-05/app/` (código integrado das Sprints 01 a 06)
- [x] `projects/team-05/README.md` (completo e revisado)
- [x] `projects/team-05/SPRINT-07.md` (atualizado com os resultados reais da regressão)
- [x] `projects/team-05/docs/specs/SPEC-007.md` (esta especificação)
- [x] `projects/team-05/evidence/sprint-07/final-home.png`
- [x] `projects/team-05/evidence/sprint-07/final-feature.png`
- [x] `projects/team-05/evidence/sprint-07/final-validation.md`

---

## 15. Specification Status

- [x] Contexto e objetivo claros.
- [x] Requisitos funcionais completos.
- [x] Restrições documentadas.
- [x] Itens fora de escopo documentados.
- [x] Critérios de aceite mensuráveis.
- [x] Rastreabilidade completa.
- [x] Implementação das Sprints 01–06 revisada frente ao código.
- [x] Resultados de validação real documentados.
- [x] Evidências reais incluídas.
- [x] Uso de IA finalizado após os testes.
- [x] Todos os membros sabem explicar o projeto (`Problem → Product goal → SPEC → Implementation → Validation → Evidence`).

# SPEC-006: Notificação de Acionamento no Aparelho

> **Team:** Team 05  
> **Sprint:** Sprint 06 (External or Device Integration)  
> **Status:** Validated  
> **Related Sprint:** `SPRINT-06.md`  
> **Base:** `SPEC-005.md` (histórico de acionamentos salvo com Room)

---

## 1. Context

**Problem:**

Hoje, quando o professor envia um acionamento, a confirmação só aparece dentro do aplicativo, no diálogo "Acionamento pronto". Se ele sai do app ou bloqueia a tela, não fica nenhum sinal visível de que o acionamento foi registrado.

**User / Actor:**

O **docente** da Escola Cadidé.

**Usage Context:**

Em sala de aula, o professor envia o acionamento e volta a atenção para a turma. Ele precisa de uma confirmação rápida, que fique visível na barra de notificações do aparelho mesmo fora do aplicativo.

---

## 2. Objective

O objetivo desta funcionalidade é integrar o SARC ao sistema de notificações do Android: ao enviar um acionamento válido, o aplicativo dispara uma notificação nativa com a mensagem do protocolo. Se as notificações estiverem bloqueadas, o aplicativo continua funcionando, salva o acionamento normalmente e avisa o professor na tela.

---

## 3. Integration Boundary

| Item | Descrição |
| --- | --- |
| **Capacidade usada** | Notificações locais do Android (`NotificationManagerCompat` e `NotificationCompat`, da biblioteca AndroidX Core que o projeto já usa). |
| **Por que o produto precisa** | Dá ao professor um comprovante visível do acionamento na barra do aparelho, mesmo depois de sair do app. É o mesmo canal que, numa versão futura, vai avisar o Monitor Militar. |
| **O que o app pede** | A permissão `POST_NOTIFICATIONS` (perguntada pelo Android 13 ou mais novo; nas versões 7 a 12 ela vem concedida na instalação). |
| **O que o app envia** | Uma notificação com o título "SARC — Acionamento registrado" e o texto `[NÍVEL] \| [TURMA] \| Sala [SALA] \| [SITUAÇÃO]`. |
| **O que o app recebe** | A resposta do usuário ao pedido de permissão (concedida ou negada) e se as notificações do app estão ligadas (`areNotificationsEnabled()`). |
| **O que pode falhar** | O professor nega a permissão; o professor desliga as notificações do SARC nas Configurações; o Android bloqueia o canal. Nos três casos, a notificação não aparece. |
| **Internet e segredos** | Nenhum. A notificação é gerada no próprio aparelho, sem servidor, sem chave de API e sem token. |

---

## 4. User Scenario

**Given** que o professor entrou no Painel do Docente pela primeira vez em um Android 13 ou mais novo,  
**When** o Painel abre,  
**Then** o Android pergunta se o SARC pode enviar notificações.

**Given** que as notificações do SARC estão permitidas,  
**When** o professor envia um acionamento válido,  
**Then** aparece uma notificação "SARC — Acionamento registrado" com a mensagem do protocolo, e o diálogo "Acionamento pronto" mostra "✅ Notificação enviada ao aparelho.".

**Given** que o professor negou a permissão ou desligou as notificações do SARC,  
**When** ele envia um acionamento válido,  
**Then** nenhuma notificação aparece, o acionamento é salvo no histórico normalmente, e o diálogo mostra em amarelo "⚠️ Notificações desativadas: o acionamento foi salvo, mas sem alerta no aparelho.".

**Given** que existe uma notificação do SARC na barra,  
**When** o professor toca nela,  
**Then** o SARC abre.

---

## 5. Functional Requirements

### FR-01: Permissão e canal

O aplicativo deve declarar a permissão `POST_NOTIFICATIONS` no `AndroidManifest.xml` e criar um canal de notificação chamado "Acionamentos SARC", com importância alta. No Android 13 ou mais novo, ao abrir o Painel do Docente, o aplicativo deve pedir a permissão caso ela ainda não tenha sido concedida, uma única vez por abertura do app (voltar ao Painel depois de negar não repete o pedido).

### FR-02: Disparar a notificação ao enviar

Ao tocar em "Enviar" com o formulário válido, ao mesmo tempo em que salva o acionamento (SPEC-005), o aplicativo deve verificar se as notificações estão ligadas e, se estiverem, disparar a notificação com o título "SARC — Acionamento registrado" e a mensagem do protocolo. Formulário com erro não dispara nada.

### FR-03: Estado de sucesso

Quando a notificação é disparada, o diálogo "Acionamento pronto" deve mostrar, abaixo da mensagem, a linha "✅ Notificação enviada ao aparelho.". Tocar na notificação abre o SARC.

### FR-04: Estado alternativo (notificações bloqueadas)

Se a permissão for negada, ou se as notificações do SARC estiverem desligadas nas Configurações, o aplicativo não deve travar nem perguntar de novo a cada envio. Ele salva o acionamento normalmente, não dispara a notificação e mostra no diálogo, em amarelo, "⚠️ Notificações desativadas: o acionamento foi salvo, mas sem alerta no aparelho.".

---

## 6. Constraints

### Required Technologies

- Kotlin
- Jetpack Compose (Material 3)
- AndroidX Core (`NotificationCompat` e `NotificationManagerCompat`, já presentes no projeto)
- Activity Compose (`rememberLauncherForActivityResult`, já presente no projeto)
- Android Studio

### Project Constraints

- `minSdk` continua 24. O pedido de permissão só acontece no Android 13 (API 33) ou mais novo; nas versões anteriores o app só verifica se as notificações estão ligadas.
- Nenhuma biblioteca nova.
- Sem internet, sem servidor, sem chave de API, sem token (o app não declara a permissão `INTERNET`).
- Não há estado de "carregando": a notificação é gerada no próprio aparelho, na hora.
- Todo o trabalho fica dentro de `projects/team-05/`.

### Files or Modules That Must Not Be Modified

- Login (`WelcomeScreen.kt`) e tema (`ui/theme/`).
- As regras de validação do formulário (`validar(...)` da Sprint 04).
- O banco de dados (`data/AcionamentoDatabase.kt` da Sprint 05).

---

## 7. Out of Scope

- Enviar o acionamento para o celular do Monitor Militar ou da Coordenação (exige servidor; fica para depois da disciplina).
- Notificações com som ou vibração diferentes por nível de urgência.
- Botões de ação dentro da notificação (por exemplo, "Marcar como resolvido").
- Tela de configurações dentro do app ou atalho para as Configurações do Android.
- Notificações agendadas ou lembretes.

---

## 8. Acceptance Criteria

### AC-01: Pedido de permissão

**Related requirement:** FR-01

**Condition:**  
Com o app recém-instalado no emulador (Android 13 ou mais novo), entrar no Painel mostra o pedido de permissão do Android. Aceitar ou negar não fecha nem trava o app.

### AC-02: Notificação enviada (sucesso)

**Related requirement:** FR-01, FR-02, FR-03

**Condition:**  
Com a permissão concedida, enviar um acionamento válido mostra a notificação "SARC — Acionamento registrado" com a mensagem do protocolo, e o diálogo mostra "✅ Notificação enviada ao aparelho.". Tocar na notificação abre o SARC. Evidência: `integration-success.png`.

### AC-03: Permissão negada (estado alternativo)

**Related requirement:** FR-02, FR-04

**Condition:**  
Com a permissão negada, enviar um acionamento válido não mostra notificação, o diálogo mostra o aviso amarelo "⚠️ Notificações desativadas..." e o acionamento aparece em "Meus acionamentos". Evidência: `integration-alternative-state.png`.

### AC-04: Notificações desligadas nas Configurações

**Related requirement:** FR-04

**Condition:**  
Depois de conceder a permissão, desligar as notificações do SARC nas Configurações do Android e enviar um acionamento válido produz o mesmo resultado do AC-03, sem crash.

### AC-05: Sem segredos

**Related requirement:** todos

**Condition:**  
O código e o repositório não têm chave de API, senha, token nem a permissão `INTERNET`.

### AC-06: Não-regressão

**Related requirement:** todos

**Condition:**  
O login, os 2 modais da Sprint 02, Entrar e Sair, o voltar do Android, a validação do formulário da Sprint 04 e o histórico com "Resolvido" da Sprint 05 continuam funcionando.

### AC-07: Compila e é estável

**Related requirement:** todos

**Condition:**  
`./gradlew assembleDebug` termina sem erro, e 10 envios seguidos (com notificações permitidas e depois bloqueadas) não geram `FATAL EXCEPTION` no logcat.

---

## 9. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `AndroidManifest.xml` L7 (`POST_NOTIFICATIONS`); `NotificacaoSarc.kt` L15-21 (`criarCanal`); `MainActivity.kt` L54 (cria o canal), L73 (`pediuPermissao`) e L90-99 (pedido no Painel) | AC-01 | `evidence/sprint-06/integration-success.png` |
| FR-02 | `NotificacaoSarc.kt` L24-48 (`enviar`; L26 confere `areNotificationsEnabled()`); `MainActivity.kt` L116-122 (`onEnviar` chama `salvar` em coroutine e, em seguida, `enviar`) | AC-02, AC-03 | `evidence/sprint-06/integration-success.png` |
| FR-03 | `NotificacaoSarc.kt` L30-39 (`PendingIntent` que abre o app ao tocar); `NovoAcionamentoScreen.kt` L149 (`notificou = onEnviar(...)`) e L166 (linha "✅" no diálogo) | AC-02 | `evidence/sprint-06/integration-success.png` |
| FR-04 | `NotificacaoSarc.kt` L26 (devolve `false` se bloqueada) e L46-48 (`catch SecurityException`); `NovoAcionamentoScreen.kt` L167-169 (aviso "⚠️" amarelo no diálogo) | AC-03, AC-04 | `evidence/sprint-06/integration-alternative-state.png` |

Cobertura dos 10 critérios do professor: AC-01 (SPEC existe) por este arquivo; AC-02 (relevante para o produto) pela seção 3; AC-03 (o app aciona a integração) por FR-02; AC-04 (sucesso observável) por FR-03 e pelo nosso AC-02; AC-05 (estado alternativo tratado) por FR-04 e pelos nossos AC-03 e AC-04; AC-06 (sem segredos) pelo nosso AC-05; AC-07 (o anterior funciona) pelo nosso AC-06; AC-08 (compila e roda) pelo nosso AC-07; AC-09 (evidências) pelos 2 prints; AC-10 (explicar o fluxo) pela seção 10.

---

## 10. Implementation Plan

### Components to Create or Modify

- `notificacao/NotificacaoSarc.kt` (novo): cria o canal "Acionamentos SARC" e a função `enviar(contexto, mensagem)`, que devolve `true` se a notificação saiu e `false` se as notificações estão bloqueadas.
- `AndroidManifest.xml`: declara `POST_NOTIFICATIONS`.
- `MainActivity.kt`: cria o canal ao abrir o app, pede a permissão ao entrar no Painel (Android 13 ou mais novo) e, no "Enviar" válido, salva no banco e chama `enviar`.
- `NovoAcionamentoScreen.kt`: `onEnviar` passa a devolver se a notificação saiu, e o diálogo "Acionamento pronto" mostra a linha de sucesso ou o aviso amarelo.

### Expected Interaction Flow

```text
Login → Painel
  ↓ (Android 13+, sem permissão ainda)
Android pergunta: "Permitir notificações do SARC?" → resposta chega depois (assíncrono)
  ↓
Formulário → "Enviar" (válido)
  ↓
salvar(...) no Room (coroutine, como na Sprint 05) e, sem esperar a gravação terminar,
  ↓
notificações ligadas?
  ├─ sim → NotificationManager mostra "SARC — Acionamento registrado"
  │        diálogo: "✅ Notificação enviada ao aparelho."
  └─ não → nada é disparado, sem crash
           diálogo: "⚠️ Notificações desativadas: o acionamento foi salvo, mas sem alerta no aparelho."
  ↓ "OK"
Painel ← o acionamento aparece em "Meus acionamentos" nos dois casos
```

### Data or State Required

- O resultado do pedido de permissão (concedida ou negada), que chega por callback do `rememberLauncherForActivityResult`.
- O valor de `areNotificationsEnabled()` no momento do envio.
- No formulário, um estado `notificou` (`true`/`false`) que decide a linha mostrada no diálogo.

---

## 11. Validation Plan

A equipe deve:

1. Compilar o projeto.
2. Desinstalar o app do emulador `Pixel_8_API_37` e instalar de novo (assim o Android pergunta a permissão outra vez).
3. Entrar no Painel e conferir o pedido de permissão. Aceitar.
4. Enviar um acionamento válido, conferir a notificação e o "✅" no diálogo, e tocar na notificação (`integration-success.png`).
5. Reinstalar, entrar e negar a permissão. Enviar um acionamento válido e conferir o aviso amarelo e o registro em "Meus acionamentos" (`integration-alternative-state.png`).
6. Conceder a permissão, desligar as notificações do SARC nas Configurações e enviar de novo (mesmo resultado do passo 5).
7. Procurar segredos e a permissão `INTERNET` no projeto.
8. Repetir os testes das Sprints 02 a 05 e rodar 10 envios seguidos conferindo o logcat.

---

## 12. Validation Results

| Acceptance Criterion | Result | Notes |
| --- | --- | --- |
| AC-01 | PASS | Com o app recém-instalado, ao tocar em "Entrar" o Painel abriu e o Android perguntou "Allow SARC to send you notifications?". Aceitar e negar não travaram o app. |
| AC-02 | PASS | Com a permissão concedida, o envio de `Nível 1 - Apoio Pedagógico \| 7º A \| Sala 02 \| Solicito apoio.` mostrou a notificação "SARC — Acionamento registrado" na barra e o "✅ Notificação enviada ao aparelho." no diálogo (`integration-success.png`; a notificação na barra está em `integration-success-notification.png`). Tocar na notificação abriu o SARC (na tela de Login, porque o app é aberto do início). |
| AC-03 | PASS | Com o app reinstalado e a permissão negada, o mesmo envio mostrou o aviso amarelo "⚠️ Notificações desativadas..." e o acionamento apareceu em "Meus acionamentos" (`integration-alternative-state.png`). Depois da negação, voltar ao Painel pelo "OK" não perguntou de novo. |
| AC-04 | PASS | Com a permissão concedida e depois retirada (`adb shell pm revoke com.team05.sarc android.permission.POST_NOTIFICATIONS`, que deixa `areNotificationsEnabled()` em `false`, como o botão das Configurações), o envio mostrou o aviso amarelo, sem crash. O interruptor da tela de Configurações não foi tocado à mão. |
| AC-05 | PASS | Busca por `INTERNET`, `api_key`, `secret`, `token` e `bearer` em `app/src` sem nenhuma ocorrência; nenhum `build/`, `.gradle/`, `.idea/` nem `local.properties` no Git. |
| AC-06 | PASS | O Login, os modais "Sobre o SARC" e "Solicitação de Cadastro", Entrar, Sair e o voltar do Android funcionaram; o formulário vazio mostrou "Escolha o nível", "Escolha a turma" e "Escolha a sala"; o botão "Resolvido" trocou para "✓ Resolvido" e o histórico continuou salvo. |
| AC-07 | PASS | `./gradlew assembleDebug` terminou com `BUILD SUCCESSFUL`; os 10 envios seguidos (permissão concedida) e todos os testes acima geraram 0 `FATAL EXCEPTION` no logcat. |

### Environment Used for Validation

- **Device:** Android Studio Emulator, Pixel 8 (`Pixel_8_API_37`)
- **Android version / API:** Android API 37
- **Build result:** PASS (`./gradlew assembleDebug`: `BUILD SUCCESSFUL`)
- **Application execution:** PASS (APK instalado e validado no emulador Pixel 8, API 37)

### Implementation Notes and Pending Items

**O que foi feito nesta Sprint (e testado no emulador):**

- Branch `team-05/sprint-06`, criada a partir do `main` do curso, que já tem as Sprints 04 e 05 aceitas.
- Arquivo novo `notificacao/NotificacaoSarc.kt` (canal e envio da notificação); permissão `POST_NOTIFICATIONS` no manifesto; pedido de permissão e chamada do envio em `MainActivity.kt`; linha de sucesso ou aviso amarelo no diálogo de `NovoAcionamentoScreen.kt`. Nenhuma biblioteca nova.
- 3 prints de evidência (sucesso, estado alternativo e a notificação na barra).

**Ajuste feito durante os testes:** no primeiro teste, depois de negar, o Android perguntava a permissão de novo toda vez que o professor voltava ao Painel pelo "OK". Passamos a pedir uma única vez por abertura do app (`pediuPermissao`, `MainActivity.kt` L73), e o teste confirmou que parou de perguntar.

**O que ficou de fora de propósito (Out of Scope):**

- Avisar o Monitor Militar ou a Coordenação (exige servidor), sons diferentes por nível, botões dentro da notificação e tela de configurações.

**Observações e pendências:**

- O ícone da notificação é o ícone de alerta do próprio Android (sem arquivo novo); trocar pelo brasão fica para depois.
- A confirmação de que a equipe sabe explicar o fluxo (AC-10 do professor e o último item da seção 16) é feita pelo Matheus e pelo Paulo depois de estudar `SPRINT-06.md`.
- Falta abrir o Pull Request `[Sprint 06] Team 05 — External or Device Integration` (só depois do OK do Matheus).

---

## 13. Evidence

- `evidence/sprint-06/integration-success.png`
- `evidence/sprint-06/integration-alternative-state.png`

---

## 14. AI-Assisted Development

### AI Tool(s)

- **Tool:** Claude Code (Anthropic)

### How AI Was Used

O trabalho seguiu um plano em módulos, aprovado pelo Matheus antes de cada etapa: primeiro o rascunho da `SPEC-006`, depois o rascunho do código e só então a implementação. A IA (Claude Code) escreveu a SPEC e o código da notificação conforme o plano aprovado, compilou o app e executou os testes no emulador pelo `adb`, capturando as evidências.

### Human Review and Changes

- **O Matheus planejou a Sprint junto com a IA, passo a passo, e verificou cada etapa antes de seguir:** aprovou o plano em módulos, leu o rascunho completo da SPEC-006 e depois o rascunho do código, e só então mandou escrever.
- **Decisões do Matheus no rascunho:** pedir a permissão ao entrar no Painel (e não no "Enviar"); mostrar o resultado dentro do diálogo "Acionamento pronto", sem tela nova; fazer a notificação abrir o SARC ao ser tocada.
- **Ajuste durante os testes:** o pedido de permissão repetia a cada volta ao Painel e passou a acontecer uma vez por abertura do app.

### AI Validation

- [x] O conteúdo gerado pela IA foi revisado antes de ser usado.
- [ ] A equipe entende a implementação enviada.
- [x] A implementação foi compilada e executada.
- [x] Os critérios de aceite foram validados manualmente.
- [x] Nada fora do escopo da Sprint foi adicionado só porque a IA sugeriu.

---

## 15. Deliverables

- [x] Aplicativo Android atualizado
- [x] Esta SPEC completa
- [x] `SPRINT-06.md`
- [x] Evidências em `evidence/sprint-06/`

---

## 16. Specification Status

- [x] Contexto e objetivo claros.
- [x] Fronteira da integração documentada.
- [x] Requisitos funcionais completos.
- [x] Restrições documentadas.
- [x] Itens fora de escopo documentados.
- [x] Critérios de aceite mensuráveis.
- [x] Rastreabilidade completa.
- [x] Implementação atende a SPEC.
- [x] Resultados de validação documentados.
- [x] Evidências incluídas.
- [x] Uso de IA documentado por completo.
- [ ] Todos os membros sabem explicar a funcionalidade.

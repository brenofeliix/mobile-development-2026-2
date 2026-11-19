# Sprint 07 — Final Validation Report (SARC)

> **Team:** Team 05  
> **Branch:** `team-05/sprint-07`  
> **SPEC:** [`docs/specs/SPEC-007.md`](../../docs/specs/SPEC-007.md)  
> **Sprint Report:** [`SPRINT-07.md`](../../SPRINT-07.md)  
> **Date:** 18/11/2026

---

## 1. Environment & Build Verification

- **Device / Emulator:** Android Emulator `Pixel_8_API_37` (`emulator-5554`, `sdk_gphone64_x86_64`)
- **Android Version / API:** Android 17 (API 37)
- **Package ID:** `com.team05.sarc`
- **Gradle Build (`./gradlew assembleDebug`):** `BUILD SUCCESSFUL in 18s` (APK: `app/build/outputs/apk/debug/app-debug.apk`)
- **Installation Mode:** Instalação limpa (`adb -s emulator-5554 uninstall com.team05.sarc` seguido de `adb -s emulator-5554 install app-debug.apk`)
- **Repository Hygiene Check:**
  - `git status --ignored -s projects/team-05/` confirmou que `.gradle/`, `app/build/`, `build/` e `local.properties` estão devidamente ignorados pelo `.gitignore` e não rastreados pelo Git.
  - Varredura em `projects/team-05/app/app/src/` confirmou ausência de chaves, tokens, senhas e da permissão `INTERNET`.

---

## 2. Regression Testing Execution (9 Steps)

| Passo | Cenário Validado | Ações Executadas no `Pixel_8_API_37` | Resultado Real Observado | Status |
| :---: | :--- | :--- | :--- | :---: |
| **1** | Abertura limpa (`WelcomeScreen`) | Instalação limpa do APK e abertura de `com.team05.sarc/.MainActivity`. | Abriu na `WelcomeScreen` com brasão da Escola Cadidé, título `SARC`, subtítulo institucional, campos `E-mail Institucional` e `Senha`, botão `Entrar`, `Sobre o SARC e Prioridades` e `Não possui acesso? Solicitar Cadastro`. | **PASS** |
| **2** | Usabilidade da tela inicial | Preenchimento de `docente@cadide.mt.gov.br` e `sarc2026`; toque em `Exibir senha` e novamente em `Ocultar senha`. | Campos receberam foco e entrada normalmente; o ícone alternou entre exibir o texto da senha (`Ocultar senha`) e ocultá-lo novamente (`Exibir senha`). | **PASS** |
| **3** | Navegação ponta a ponta | `Entrar` (permissão concedida) → `Painel do Docente` → `Novo acionamento` → `Cancelar` → `Painel` → `Sair` → `WelcomeScreen` → `Entrar`. | Todas as transições pelo `NavHost` funcionaram sem perda de estado e sem repetir o diálogo de permissão ao entrar novamente no Painel. | **PASS** |
| **4** | Estado e interações | Modal *"Sobre o SARC"*: toque em `Nível 1` e depois em `Nível 4`. Modal *"Solicitação de Cadastro"*: avanço nas 3 etapas (`Próximo`), retorno com `Voltar` e fechamento com `Entendi`. No Painel: toque em `Resolvido`. | Apenas 1 cartão de prioridade expandiu por vez (Nível 1 recolheu ao abrir Nível 4); o guia navegou entre `Etapa 1 de 3`, `Etapa 2 de 3` e `Etapa 3 de 3` corretamente; o botão `Resolvido` mudou para `✓ Resolvido`. | **PASS** |
| **5** | Validação de formulário (`NovoAcionamentoScreen`) | 1) Toque em `Enviar` com formulário vazio. 2) Seleção de `Nível 2`, `8º A`, `04` + 115 caracteres em `Situação (opcional)` + `Enviar`. 3) Substituição por `"Aluno com indisciplina em sala."` (`31/100`) + `Enviar`. | 1) Exibiu `Escolha o nível`, `Escolha a turma` e `Escolha a sala` e bloqueou o envio. 2) Exibiu `115/100 - Máximo de 100 caracteres` e bloqueou o envio. 3) Abriu o diálogo `"Acionamento pronto"` com a mensagem formatada. | **PASS** |
| **6** | Persistência local (`sarc.db`) | Registro de dois acionamentos (1 marcado como `✓ Resolvido` e 1 pendente), encerramento completo (`adb shell am force-stop com.team05.sarc`), reabertura do app e entrada no Painel. | Ambos os registros (`Nível 1 - Apoio Pedagógico \| 7º A \| Sala 02 \| Solicito apoio.` e `Nível 2 - Atenção Disciplinar \| 8º A \| Sala 04 \| Aluno com indisciplina em sala.` com `✓ Resolvido`) permaneceram salvos em ordem decrescente (registrado em `final-home.png`). | **PASS** |
| **7** | Integração de notificação e fallback defensivo | **Cenário A (Permitida):** Permissão `Allow` no Painel + envio válido. **Cenário B (Negada):** `pm revoke POST_NOTIFICATIONS`, reabertura, escolha `Don’t allow` + envio de `Nível 3 \| 9º B \| Sala 08`. | **Cenário A:** Notificação `"SARC — Acionamento registrado"` exibida no canal `acionamentos_sarc` (confirmada no banner heads-up em `final-feature.png` e via `dumpsys notification`) + `"✅ Notificação enviada ao aparelho."`. **Cenário B:** Sem crash; exibiu `"⚠️ Notificações desativadas: o acionamento foi salvo, mas sem alerta no aparelho."`, salvou o registro no Room e não reabriu o pedido de permissão. | **PASS** |
| **8** | Botão Voltar do Android (`KEYCODE_BACK`) | `KEYCODE_BACK` com o modal *"Sobre o SARC"* aberto; depois `Novo acionamento` → `KEYCODE_BACK` → `Painel` → `KEYCODE_BACK` → `WelcomeScreen`. | Fechou o modal corretamente sem sair da tela inicial e navegou na pilha de telas de volta ao Painel e à tela de login conforme esperado. | **PASS** |
| **9** | Estabilidade (`logcat`) | Monitoramento contínuo via `adb -s emulator-5554 logcat -v time` durante toda a execução dos passos 1 a 8. | **8.984 linhas** capturadas; **`0` ocorrências de `FATAL EXCEPTION`** e **`0` ocorrências de `ANR in`**. Nenhuma exceção não tratada do pacote `com.team05.sarc`. | **PASS** |

---

## 3. Evidences Captured

- **`final-home.png`:** `Painel do Docente` após encerramento completo e reabertura do aplicativo (`force-stop`), comprovando a persistência no Room (`sarc.db`) de dois registros (um pendente com botão `Resolvido` e outro já marcado como `✓ Resolvido`).
- **`final-feature.png`:** `Novo acionamento` com chips selecionados (`Nível 2 - Atenção Disciplinar`, `8º A`, `Sala 04`), situação preenchida (`31/100`), diálogo `"Acionamento pronto"` com confirmação `"✅ Notificação enviada ao aparelho."` e notificação heads-up `"SARC — Acionamento registrado"` ativa na barra do sistema.

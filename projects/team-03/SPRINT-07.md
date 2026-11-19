# SPRINT 07 — Final Integration, Quality & Delivery — Team 03 (Wiflux)

> **Equipe:** Célia Hiromi (@hiromilly) e Geovanna Gaspar (@gegwspar)
> **Prazo final:** 23/11/2026
> **Especificação:** [`docs/specs/SPEC-007.md`](docs/specs/SPEC-007.md)

## 1. Objetivo da Sprint

Integrar o produto acumulado, remover problemas críticos, validar o aplicativo completo e preparar a entrega final. Não há funcionalidade nova: a SPEC-007 define requisitos de qualidade.

## 2. Revisão de todas as SPECs

O status das SPECs 001 a 006 vem do que cada Sprint já validou. O teste de regressão da seção 3 confirma que continuam valendo no app final.

| Specification | Main feature | Current status | Evidence |
| --- | --- | --- | --- |
| SPEC-001 | Primeira tela do produto | PASS | `evidence/sprint-01/first-screen.png` |
| SPEC-002 | Estado e interação: medidor reage ao toque | PASS | `evidence/sprint-02/before-interaction.png` e `after-interaction.png` |
| SPEC-003 | Navegação entre Verificação e Detalhes | PASS | `evidence/sprint-03/screen-a.png` e `screen-b.png` |
| SPEC-004 | Nome do cômodo com validação | PASS | `evidence/sprint-04/valid-input.png` e `invalid-input.png` |
| SPEC-005 | Histórico salvo em banco local (Room) | PASS | `evidence/sprint-05/data-created.png` e `data-restored.png` |
| SPEC-006 | Leitura real do sinal Wi-Fi | PASS | `evidence/sprint-06/integration-success.png` e `integration-alternative-state.png` |
| SPEC-007 | Final quality | PASS | `evidence/sprint-07/final-validation.md`, `final-home.png` e `final-feature.png` |

**Observação sobre a SPEC-002:** na Sprint 02 o resultado da medição era sorteado. A partir da Sprint 06 ele vem do sinal real do Wi-Fi; o mecanismo de estado e a interação são os mesmos.

**Observação sobre o nome:** as SPECs 001 e 002 usam o nome antigo "Wi-Fi Mapper". O produto passou a se chamar Wiflux na Sprint 03.

## 3. Teste de regressão

Roteiro executado pela Geovanna Gaspar em 18/11/2026, a partir de uma abertura limpa do app, no emulador Pixel 8 do Android Studio (Android 15, API 35), na branch `team-03/sprint-07`. O detalhe de cada passo está em [`evidence/sprint-07/final-validation.md`](evidence/sprint-07/final-validation.md).

| # | Passo | Resultado |
| --- | --- | --- |
| 1 | O app abre | PASS |
| 2 | A tela principal é utilizável | PASS |
| 3 | A navegação funciona | PASS |
| 4 | O estado e a interação funcionam | PASS |
| 5 | A validação do formulário funciona | PASS |
| 6 | Os dados persistem depois de reabrir o app | PASS |
| 7 | A integração funciona ou falha com mensagem clara | PASS |
| 8 | O botão voltar se comporta corretamente | PASS |
| 9 | Nenhum crash crítico no roteiro | PASS |

Nenhum problema foi encontrado.

## 4. Melhorias de uso e consistência

Revisão rápida, sem redesenhar o app:

- **Nome do app:** o app aparecia como "Wifi" no aparelho e no Android Studio, enquanto a tela dizia "Wiflux". O nome "Wifi" veio do projeto novo criado na Sprint 03. Corrigido em `strings.xml` (`app_name`) e `settings.gradle.kts` (`rootProject.name`).
- Durante a regressão não foi apontado nenhum problema de espaçamento, rótulo ou mensagem; por isso nenhum outro ajuste visual foi feito.

## 5. Limpeza do repositório

- Não existem `.idea/`, `.gradle/`, `build/`, `.kotlin/` nem `local.properties` entre os arquivos versionados de `projects/team-03/`.
- Não há API, chave, senha ou token no projeto.
- O Gradle Wrapper e os arquivos de configuração necessários foram mantidos.

## 6. README da equipe

Criado em [`README.md`](README.md): produto, problema, público, funcionalidades, tecnologias, como compilar e executar, configuração, limitações e integrantes.

## 7. Limitações conhecidas

- O app lê só a rede Wi-Fi conectada no momento; não mostra o nome da rede (SSID).
- No emulador o sinal costuma ser forte; "Médio" e "Fraco" dependem de um aparelho real.
- O histórico guarda cômodo, status e data/hora, mas não o dBm, e mostra só as 5 últimas.
- Não há testes automatizados além dos de exemplo; a validação é manual.
- As SPECs 001 e 002 usam o nome antigo do produto.

## 8. Evidências finais

| Arquivo | O que mostra |
| --- | --- |
| `evidence/sprint-07/final-home.png` | Tela inicial recém-aberta, com o histórico salvo |
| `evidence/sprint-07/final-feature.png` | Medição real: "Quarto", -50 dBm, "Sinal Forte" |
| `evidence/sprint-07/final-validation.md` | Aparelho, data e resultado dos 9 passos |

## 9. Rastreabilidade dos requisitos

Ver SPEC-007, seção 8 (requisito → código → evidência).

## 10. Uso de LLM

| Item | Resposta da equipe |
| --- | --- |
| LLM/tool used | Claude (Anthropic) |
| Task supported by the LLM | Revisão das SPECs anteriores, geração do roteiro de regressão a partir delas, rascunho do README, da SPEC-007 e deste relatório |
| Main suggestion received | Executar o roteiro de 9 passos partindo de uma abertura limpa e registrar cada resultado; deixar como "A executar" o que ainda não foi testado |
| What the team changed manually | Revisamos os textos propostos pela IA e corrigimos o que não batia com o projeto. Decidimos quais prints reaproveitar como evidência final e registramos a origem de cada um. Corrigimos o nome do app de "Wifi" para "Wiflux" no `strings.xml` e no `settings.gradle.kts`. A Geovanna executou o roteiro de regressão no emulador e relatou os resultados, e nós preenchemos o `final-validation.md` só com o que foi comprovado. |
| How the result was validated | A Geovanna Gaspar executou os 9 passos do roteiro no emulador Pixel 8 (Android 15) em 18/11/2026, na branch `team-03/sprint-07`, e tirou os prints `final-home.png` e `final-feature.png`. Nenhum problema foi encontrado. Conferimos também que não há `.idea/`, `.gradle/`, `build/`, `local.properties` nem segredos no que foi versionado. |

Nenhum print, resultado de teste, informação de aparelho ou critério foi inventado: a evidência vem de execução real.

## 11. Critérios de aceite da Sprint 07

- [x] **AC-01** — SPEC-007 existe.
- [x] **AC-02** — Todas as SPECs anteriores foram revisadas.
- [x] **AC-03** — O fluxo principal funciona desde a abertura do app.
- [x] **AC-04** — A navegação funciona.
- [x] **AC-05** — O estado e a interação funcionam.
- [x] **AC-06** — A validação do formulário funciona.
- [x] **AC-07** — A persistência funciona conforme a SPEC-005.
- [x] **AC-08** — A integração funciona ou falha com mensagem clara (SPEC-006).
- [x] **AC-09** — Nenhum crash crítico no fluxo documentado.
- [x] **AC-10** — A evidência final está completa.
- [x] **AC-11** — O README da equipe está completo.
- [x] **AC-12** — Nenhum segredo nem pasta gerada foi enviado.
- [x] **AC-13** — Os requisitos principais são rastreáveis até o código e a evidência.
- [x] **AC-14** — Cada integrante consegue explicar o projeto.

## 12. Informações de submissão Git

- **Branch:** `team-03/sprint-07`
- **PR:** `[Sprint 07] Team 03 — Final Integration, Quality & Delivery`
- **Base:** `brenofeliix/mobile-development-2026-2`, branch `main`

## 13. Definition of Done

- [x] SPEC-007 escrita.
- [x] Regressão executada (18/11/2026, emulador Pixel 8, Android 15).
- [x] Problemas críticos corrigidos ou documentados (nenhum encontrado).
- [x] README criado.
- [x] Evidência final real e completa.
- [x] Repositório sem arquivos indevidos.
- [x] Uso de IA documentado.
- [ ] PR final enviado até 23/11/2026.

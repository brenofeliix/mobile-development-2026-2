# SPEC-007 — Qualidade Final do Wiflux

**Sprint:** 07 — Final Integration, Quality & Delivery
**Equipe:** Team 03 — Wiflux (Célia Hiromi e Geovanna Gaspar)
**Pré-requisitos:** Sprints 01 a 06 entregues

> Esta SPEC **não** pede uma funcionalidade nova. Ela define os requisitos finais de qualidade do aplicativo completo e como eles são verificados.

## 1. Contexto

O Wiflux mede o sinal do Wi-Fi em cada cômodo da casa. Ao longo das Sprints o app ganhou: tela inicial (S01), estado e interação (S02), navegação (S03), formulário com validação (S04), histórico em banco local (S05) e leitura real do sinal do aparelho (S06). Falta garantir que tudo funciona junto, a partir de uma abertura limpa do app, e registrar as limitações conhecidas.

**Usuário:** pessoa que quer comparar o sinal do Wi-Fi entre os cômodos de casa ou do trabalho.

## 2. Objetivo

Verificar o aplicativo completo, corrigir problemas críticos ou registrá-los como limitação conhecida, e deixar a entrega reproduzível e explicável.

## 3. Cenário do Usuário

**Dado** que o usuário abre o Wiflux pela primeira vez,
**quando** digita o nome de um cômodo, toca em "Iniciar Verificação", abre "Ver Detalhes", volta e fecha o app,
**então** ele vê o sinal real do Wi-Fi, o detalhe da verificação, o nome do cômodo preservado e o histórico salvo ao reabrir o app.

## 4. Requisitos Funcionais

- **FR-01 — Abertura:** o app abre na tela inicial "Verificação de Sinal" sem travar.
- **FR-02 — Tela principal utilizável:** a tela mostra o medidor, o chip de status, o campo "Nome do cômodo", o botão "Iniciar Verificação" e o card "Últimas verificações".
- **FR-03 — Navegação:** "Ver Detalhes" abre a tela de detalhes; "Voltar" (botão da tela ou do sistema) retorna à tela inicial.
- **FR-04 — Estado e interação:** ao medir, o medidor, o chip e o dBm refletem o resultado da medição.
- **FR-05 — Validação do formulário:** nome vazio ou com menos de 3 letras mostra "Digite pelo menos 3 letras" e não mede nem salva.
- **FR-06 — Persistência:** as verificações válidas aparecem no histórico e continuam lá depois de fechar e reabrir o app.
- **FR-07 — Integração:** com Wi-Fi conectado, mostra o dBm real; sem Wi-Fi ou sem conexão, mostra uma mensagem clara e não salva nada.
- **FR-08 — Sem crash:** nenhum fechamento inesperado no fluxo testado.
- **FR-09 — Consistência visual e de nome:** o produto se chama **Wiflux** na tela e como nome do app no aparelho; os textos estão em português e padronizados.
- **FR-10 — Entrega limpa:** o repositório não contém `.idea/`, `.gradle/`, `build/`, `local.properties`, chaves, senhas ou tokens.

## 5. Restrições

- Kotlin, Jetpack Compose e Android Studio.
- Nenhuma funcionalidade nova nesta Sprint; só correções necessárias.
- Não redesenhar o app.
- Todo o trabalho fica em `projects/team-03/`.

## 6. Fora do Escopo

Novas telas ou funções, nome da rede (SSID), mapa dos cômodos, editar ou excluir registros, sincronização em nuvem, nova biblioteca.

## 7. Critérios de Aceite

- **AC-01** — SPEC-007 existe.
- **AC-02** — Todas as SPECs anteriores foram revisadas (tabela no `SPRINT-07.md`).
- **AC-03** — O fluxo principal funciona desde a abertura do app (FR-01, FR-02).
- **AC-04** — A navegação funciona (FR-03).
- **AC-05** — O estado e a interação funcionam (FR-04).
- **AC-06** — A validação do formulário funciona (FR-05).
- **AC-07** — A persistência funciona conforme a SPEC-005 (FR-06).
- **AC-08** — A integração funciona ou falha com mensagem clara, conforme a SPEC-006 (FR-07).
- **AC-09** — Nenhum crash crítico no fluxo documentado (FR-08).
- **AC-10** — A evidência final está completa (`final-home.png`, `final-feature.png`, `final-validation.md`).
- **AC-11** — O README da equipe está completo.
- **AC-12** — Nenhum segredo nem pasta gerada foi enviado (FR-10).
- **AC-13** — Os requisitos principais são rastreáveis até o código e a evidência (seção 8).
- **AC-14** — Cada integrante consegue explicar o projeto.

## 8. Rastreabilidade

| FR | AC | Onde está no código | Evidência |
| --- | --- | --- | --- |
| FR-01, FR-02 | AC-03 | `MainActivity.kt` (`NavHost`, rota `home`), `HomeScreen.kt` | `evidence/sprint-07/final-home.png` |
| FR-03 | AC-04 | `MainActivity.kt` (rota `details/{status}/{dbm}`), `DetailsScreen.kt` | `evidence/sprint-03/screen-b.png` |
| FR-04 | AC-05 | `HomeScreen.kt` (`SignalGauge`, chip de status) | `evidence/sprint-06/integration-success.png` |
| FR-05 | AC-06 | `HomeScreen.kt` (`roomName.trim().length < 3`) | `evidence/sprint-04/invalid-input.png` |
| FR-06 | AC-07 | `data/Verification.kt`, `VerificationDao.kt`, `AppDatabase.kt`, `HistoryViewModel.kt`, `HistoryList.kt` | `evidence/sprint-05/data-restored.png` |
| FR-07 | AC-08 | `data/WifiReader.kt`, `data/MeasureViewModel.kt` | `evidence/sprint-06/integration-success.png` e `integration-alternative-state.png` |
| FR-08 | AC-09 | Fluxo completo | `evidence/sprint-07/final-validation.md` |
| FR-09 | AC-03 | `strings.xml` (`app_name`), `HomeScreen.kt` | `evidence/sprint-07/final-home.png` |
| FR-10 | AC-12 | `.gitignore` do projeto | Lista de arquivos do PR |

## 9. Plano de Implementação

Não há funcionalidade nova. Correções desta Sprint:

- `strings.xml` e `settings.gradle.kts`: nome do app e do projeto passam de "Wifi" para "Wiflux" (FR-09).
- Documentação: `README.md`, `SPRINT-07.md`, esta SPEC e a evidência final.
- Qualquer problema encontrado na regressão é corrigido com a menor mudança possível ou registrado como limitação conhecida.

## 10. Plano de Validação

Partindo de uma abertura limpa do app (app fechado e reaberto), executar na ordem:

1. O app abre.
2. A tela principal está utilizável.
3. Digitar um nome válido e medir com o Wi-Fi conectado; o dBm real aparece.
4. Digitar um nome curto; aparece o erro e nada é medido.
5. "Ver Detalhes" mostra o mesmo dBm; "Voltar" retorna e o nome continua no campo.
6. Fechar o app e reabrir; o histórico continua.
7. Medir sem Wi-Fi; aparece a mensagem clara e nada é salvo.
8. O botão voltar do sistema se comporta corretamente.
9. Nenhum crash ocorre durante o roteiro.

O resultado de cada passo é registrado em `evidence/sprint-07/final-validation.md`.

## 11. Resultados da Validação

Roteiro executado pela Geovanna Gaspar em 18/11/2026 no app final (branch `team-03/sprint-07`). Detalhes de cada passo em `evidence/sprint-07/final-validation.md`.

| AC | Resultado | Observação |
| --- | --- | --- |
| AC-01 | PASS | SPEC-007 existe |
| AC-02 | PASS | SPECs 001 a 007 revisadas na tabela da seção 2 do `SPRINT-07.md` |
| AC-03 | PASS | Roteiro, passos 1 e 2 (`final-home.png`) |
| AC-04 | PASS | Roteiro, passos 5 e 8 |
| AC-05 | PASS | Roteiro, passo 3 (`final-feature.png`) |
| AC-06 | PASS | Roteiro, passo 4 |
| AC-07 | PASS | Roteiro, passo 6: o histórico manteve os registros de 07/11 e 18/11 |
| AC-08 | PASS | Roteiro, passos 3 e 7 |
| AC-09 | PASS | Roteiro, passo 9: nenhum crash |
| AC-10 | PASS | `final-home.png`, `final-feature.png` e `final-validation.md` |
| AC-11 | PASS | `README.md` com todos os itens pedidos |
| AC-12 | PASS | Nenhum `.idea/`, `.gradle/`, `build/`, `local.properties` ou segredo entre os arquivos versionados |
| AC-13 | PASS | Tabela da seção 8 (requisito → código → evidência) |
| AC-14 | PASS | As duas integrantes confirmaram que conseguem explicar o projeto |

**Ambiente:** emulador Pixel 8 do Android Studio, Android 15 (API 35).

## 12. Evidências

- `evidence/sprint-07/final-home.png`
- `evidence/sprint-07/final-feature.png`
- `evidence/sprint-07/final-validation.md`

## 13. Desenvolvimento com Apoio de IA

Ferramenta: Claude (Anthropic). Uso: revisão das SPECs anteriores, geração do roteiro de regressão a partir delas e rascunho dos documentos finais. Revisão humana: a equipe confere cada texto, executa os testes no emulador e preenche os resultados com o que realmente aconteceu. Nenhuma captura de tela, resultado de teste ou critério foi inventado. Detalhamento no `SPRINT-07.md`.

## 14. Deliverables

`app/`, `README.md`, `SPRINT-07.md`, `docs/specs/SPEC-001.md` a `SPEC-007.md`, `evidence/sprint-07/final-home.png`, `final-feature.png` e `final-validation.md`.

## 15. Status da Especificação

Validada — todos os critérios de aceite (AC-01 a AC-14) em PASS.

# SPEC-006 — Integração com o dispositivo: leitura real do sinal Wi-Fi

**Sprint:** 06 — External or Device Integration
**Equipe:** Team 03 — Wiflux
**Pré-requisitos:** Sprints 01 a 05 funcionando

## 1. Fronteira da integração

| Pergunta | Resposta |
| --- | --- |
| Qual capacidade é usada? | Wi-Fi do aparelho, via `WifiManager` do Android |
| Por que o produto precisa? | O objetivo do Wiflux é medir o sinal por cômodo. Até a Sprint 05 o status era sorteado; agora vem do sinal real |
| O que o app pede? | Se o Wi-Fi está ligado e a intensidade (RSSI, em dBm) da conexão atual |
| O que recebe? | Um número em dBm (ex.: -58), ou a informação de que não há leitura possível |
| O que pode falhar? | Wi-Fi desligado; Wi-Fi ligado sem rede Wi-Fi ativa (inclui estar usando dados móveis); erro inesperado na leitura |
| O app envia algo para fora? | Não. Nada sai do aparelho |

## 2. Permissão

`ACCESS_WIFI_STATE` e `ACCESS_NETWORK_STATE` são permissões normais (concedidas na instalação). Não há diálogo para o usuário, então não existe estado "permissão negada". O app não lê nome da rede (SSID) nem localização, que exigiriam permissão de localização.

## 3. Comportamento

1. O usuário digita o nome do cômodo e toca em "Iniciar Verificação" (validação da Sprint 04 continua valendo).
2. **Loading:** o botão fica desabilitado com o texto "Medindo sinal..." e o chip mostra "Medindo...".
3. O app faz 3 leituras com 0,5 s entre elas (em segundo plano) e calcula a média, pois o sinal oscila.
4. **Sucesso:** o medidor mostra o dBm real e o status é classificado (regra abaixo); a verificação é salva no histórico (Sprint 05).
5. **Falha:** mensagem em vermelho abaixo do botão; nada é salvo no histórico.

### Classificação

| dBm médio | Status |
| --- | --- |
| maior ou igual a -60 | Sinal Forte |
| de -61 a -75 | Sinal Médio |
| menor que -75 | Sinal Fraco |

## 4. Requisitos funcionais

- **FR-01** — Com nome válido, "Iniciar Verificação" dispara a leitura do Wi-Fi.
- **FR-02** — Durante a leitura, o botão fica desabilitado e a tela indica que está medindo.
- **FR-03** — Em caso de sucesso, a Home mostra o dBm real e o status correspondente.
- **FR-04** — "Ver Detalhes" mostra o mesmo dBm medido.
- **FR-05** — Wi-Fi desligado mostra "Wi-Fi desligado. Ative o Wi-Fi para medir." e não salva registro.
- **FR-06** — Wi-Fi ligado sem conexão mostra "Sem conexão Wi-Fi. Conecte-se a uma rede para medir." e não salva registro.
- **FR-07** — Erro inesperado mostra "Não foi possível ler o sinal do Wi-Fi." e não salva registro.

## 5. Critérios de aceite

- **AC-01** — SPEC-006 existe.
- **AC-02** — A leitura do Wi-Fi é relevante para o objetivo do produto (medir sinal por cômodo).
- **AC-03** — O app dispara a integração pelo botão "Iniciar Verificação".
- **AC-04** — O resultado real (dBm e status) aparece na tela (FR-03, FR-04).
- **AC-05** — Pelo menos um estado alternativo é tratado: Wi-Fi desligado e sem conexão (FR-05, FR-06).
- **AC-06** — Nenhum segredo (chave de API, token, senha) existe no repositório.
- **AC-07** — Sprints 01 a 05 continuam funcionando (navegação, validação do nome, histórico no Room).
- **AC-08** — O app compila e roda.
- **AC-09** — Evidências `integration-success.png` e `integration-alternative-state.png` existem.
- **AC-10** — A equipe sabe explicar o fluxo da integração.

## 6. Fora do escopo

Nome da rede (SSID), varredura de redes próximas, localização, mapa, gravar o dBm no banco, medição contínua em tempo real.

## 7. Rastreabilidade

| FR | AC | Onde está no código |
| --- | --- | --- |
| FR-01 | AC-03 | `HomeScreen`: `onClick` do botão valida o nome e chama `onMeasure` → `MeasureViewModel.measure()` |
| FR-02 | AC-04 | `MeasureState.Loading`; `HomeScreen`: `enabled = !medindo` e texto "Medindo sinal..." |
| FR-03 | AC-04 | `MeasureState.Success(dbm, status)`; `HomeScreen`: `SignalGauge(status, dbm)` |
| FR-04 | AC-04 | `MainActivity`: rota `details/{status}/{dbm}`; `DetailsScreen(dbm)` |
| FR-05 | AC-05 | `WifiReader.read()` devolve `WifiOff`; `MeasureViewModel.medir()` devolve `Error` |
| FR-06 | AC-05 | `WifiReader.read()` devolve `NotConnected`; `MeasureViewModel.medir()` devolve `Error` |
| FR-07 | AC-05 | `try/catch` em `MeasureViewModel.measure()` |

## 8. Plano de Implementação

| Arquivo | Responsabilidade |
| --- | --- |
| `data/WifiReader.kt` | Única classe que fala com o `WifiManager` e o `ConnectivityManager` |
| `data/MeasureViewModel.kt` | Estados Idle, Loading, Success e Error; 3 leituras, média e classificação |
| `MainActivity.kt` | Cria os ViewModels; salva no Room só quando a medição dá certo |
| `HomeScreen.kt` | Mostra loading, sucesso e erro |
| `DetailsScreen.kt` | Recebe o dBm medido |
| `AndroidManifest.xml` | Permissões `ACCESS_WIFI_STATE` e `ACCESS_NETWORK_STATE` |

Fluxo: botão → validação do nome → `Loading` → 3 leituras em segundo plano → média → `Success` (e salva no histórico) ou `Error` (nada é salvo).

## 9. Plano de Validação

1. Com Wi-Fi conectado, medir um cômodo: aparece o dBm real e o status (`integration-success.png`).
2. Sem conexão Wi-Fi, medir de novo: aparece a mensagem de erro e nada é salvo (`integration-alternative-state.png`).
3. Conferir as Sprints anteriores: nome inválido, "Ver Detalhes" e "Voltar", histórico depois de reiniciar o app.

## 10. Resultados da Validação

| AC | Resultado | Observação |
| --- | --- | --- |
| AC-01 | PASS | SPEC-006 existe |
| AC-02 | PASS | O produto mede o sinal por cômodo; agora com o sinal real |
| AC-03 | PASS | O botão "Iniciar Verificação" dispara a leitura |
| AC-04 | PASS | `integration-success.png`: -50 dBm e "Sinal Forte" |
| AC-05 | PASS | `integration-alternative-state.png`: "Sem conexão Wi-Fi..." e nada novo salvo |
| AC-06 | PASS | Sem API, chave, token ou senha no projeto |
| AC-07 | PASS | Nome inválido, "Ver Detalhes", "Voltar" e histórico funcionam |
| AC-08 | PASS | App compilado e executado pela Geovanna em 07/11/2026; depois só mudou `remember` para `rememberSaveable` no campo do cômodo |
| AC-09 | PASS | Os dois prints existem |
| AC-10 | PASS | Fluxo explicado nas seções 2 a 4 do `SPRINT-06.md` |

**Ambiente:** testes feitos pela Geovanna Gaspar em 07/11/2026, no emulador Android do Android Studio.

## 11. Evidências

- `evidence/sprint-06/integration-success.png`
- `evidence/sprint-06/integration-alternative-state.png`

## 12. Desenvolvimento com Apoio de IA

Ferramenta: Claude (Anthropic). Uso: escolha da integração, organização em `WifiReader` e `MeasureViewModel`, e estados de loading, sucesso e erro com coroutines. Revisão humana: a equipe testou nos dois cenários e consegue explicar o fluxo. Detalhamento completo no `SPRINT-06.md`.

## 13. Deliverables

`app/`, `SPRINT-06.md`, `docs/specs/SPEC-006.md`, `evidence/sprint-06/integration-success.png`, `evidence/sprint-06/integration-alternative-state.png`.

## 14. Status da Especificação

Validada — todos os critérios de aceite (AC-01 a AC-10) em PASS.

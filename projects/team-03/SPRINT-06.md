# SPRINT 06 — External or Device Integration — Team 03 (Wiflux)

## 1. Integração escolhida e relevância para o produto

Leitura do sinal Wi-Fi do aparelho (RSSI em dBm) com o `WifiManager` do Android. O Wiflux existe para medir a qualidade do Wi-Fi em cada cômodo; até a Sprint 05 o status era um sorteio, e agora vem do sinal real. Detalhes em `docs/specs/SPEC-006.md`.

---

## 2. Fluxo dos dados

Usuário toca em "Iniciar Verificação" → `HomeScreen` valida o nome → `onMeasure` → `MeasureViewModel.measure()` → `WifiReader.read()` (3 vezes) → média em dBm → `classificar()` → `MeasureState.Success(dbm, status)` → a Home mostra o medidor e o chip → `HistoryViewModel.save()` grava no Room (Sprint 05).

| Arquivo | Responsabilidade |
|---|---|
| `data/WifiReader.kt` | Única classe que fala com o `WifiManager`; devolve Success, WifiOff ou NotConnected |
| `data/MeasureViewModel.kt` | Estados da medição (Idle, Loading, Success, Error), 3 leituras, média e classificação |
| `MainActivity.kt` | Cria os dois ViewModels; liga `onMeasure` ao salvamento; rota de detalhes recebe o dBm |
| `HomeScreen.kt` | Mostra loading, sucesso e erro; o medidor usa o dBm real |
| `DetailsScreen.kt` | Recebe o dBm medido (parâmetro novo) |
| `AndroidManifest.xml` | Permissões `ACCESS_WIFI_STATE` e `ACCESS_NETWORK_STATE` |

---

## 3. Comportamento assíncrono

A medição roda dentro de `viewModelScope.launch`. Cada leitura usa `withContext(Dispatchers.IO)` e entre as leituras há `delay(500)`, então a interface não trava. O estado `Loading` desabilita o botão para evitar toques repetidos.

---

## 4. Tratamento de falhas

| Situação | O que o app faz |
|---|---|
| Wi-Fi desligado | `WifiReading.WifiOff` → mensagem em vermelho, nada salvo |
| Ligado, sem rede | `WifiReading.NotConnected` (RSSI inválido, -127) → mensagem em vermelho, nada salvo |
| Exceção inesperada | `try/catch` no ViewModel → mensagem genérica, nada salvo |

---

## 5. Privacidade e segurança

- O app lê só a intensidade do sinal. Não lê nome da rede (SSID), endereço MAC nem localização.
- Nada é enviado pela internet; não há API, chave ou token no projeto.
- Permissões usadas: `ACCESS_WIFI_STATE` (saber se o Wi-Fi está ligado e ler o sinal) e `ACCESS_NETWORK_STATE` (saber se a rede ativa é Wi-Fi). Ambas são permissões normais, sem diálogo para o usuário.

---

## 6. Validação

Os testes foram executados pela Geovanna Gaspar em 07/11/2026, no emulador Android do Android Studio. A única alteração feita no código depois desses testes foi trocar `remember` por `rememberSaveable` nas duas variáveis do campo "Nome do cômodo" na `HomeScreen`, para o nome continuar no campo ao voltar de "Ver Detalhes". É o mesmo recurso já validado na Sprint 05.

| Cenário | Como foi testado | Resultado | Evidência |
|---|---|---|---|
| Sucesso | Wi-Fi conectado, cômodo "cozinha" | O medidor mostra -50 dBm e "Sinal Forte"; o registro aparece no histórico | `evidence/sprint-06/integration-success.png` |
| Alternativo | Aparelho sem conexão Wi-Fi, cômodo "sala" | Aparece "Sem conexão Wi-Fi. Conecte-se a uma rede para medir." em vermelho; o medidor continua em "Aguardando" e nada novo é salvo | `evidence/sprint-06/integration-alternative-state.png` |
| Sprints anteriores | Nome inválido, "Ver Detalhes", histórico após reiniciar o app | Continuam funcionando | Prints das Sprints 04 e 05 |

Observação: no print do cenário alternativo, o histórico mostra a verificação "sala" feita antes de perder a conexão. O erro não criou um registro novo.

---

## 7. Uso de LLM

| Item | Resposta da equipe |
|---|---|
| LLM/ferramenta usada | Claude (Anthropic) |
| Tarefa apoiada pela LLM | Escolha da integração, leitura do Wi-Fi com `WifiManager`, estados de loading/sucesso/erro com coroutines |
| Principal sugestão recebida | Isolar o Android em `WifiReader`, usar um `MeasureViewModel` com estados e tirar a média de 3 leituras |
| O que a equipe alterou manualmente | Escreveu e organizou os arquivos novos (`WifiReader.kt`, `MeasureViewModel.kt`) e as mudanças em `MainActivity.kt`, `HomeScreen.kt` e `DetailsScreen.kt`; manteve `rememberSaveable` no campo do cômodo; corrigiu os textos dos documentos para bater com o código e com os prints |
| Como o resultado foi validado | A Geovanna executou o app no emulador do Android Studio nos dois cenários (sucesso e sem conexão) e conferiu as Sprints anteriores, registrado nos dois prints |

---

## 8. Critérios de aceite

Resultado detalhado na SPEC-006, seção 11.

- [x] **AC-01** — SPEC-006 existe.
- [x] **AC-02** — A leitura do Wi-Fi é relevante para o objetivo do produto.
- [x] **AC-03** — O botão "Iniciar Verificação" dispara a integração.
- [x] **AC-04** — O resultado real (dBm e status) aparece na tela.
- [x] **AC-05** — Estado alternativo tratado (Wi-Fi desligado e sem conexão).
- [x] **AC-06** — Nenhum segredo no repositório.
- [x] **AC-07** — Sprints 01 a 05 continuam funcionando.
- [x] **AC-08** — O app compila e roda (validado pela Geovanna em 07/11/2026).
- [x] **AC-09** — Os dois prints existem em `evidence/sprint-06/`.
- [x] **AC-10** — A equipe explica o fluxo da integração (seções 2 a 4 deste relatório).

## 9. Limitações conhecidas

- No emulador o sinal costuma ser forte; os status "Médio" e "Fraco" dependem de um aparelho real.
- O app lê só a rede Wi-Fi conectada no momento; não mostra o nome da rede.
- O valor em dBm aparece na tela, mas o histórico guarda apenas cômodo, status e data/hora.

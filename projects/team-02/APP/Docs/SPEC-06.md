# SPEC-006 — Integração com recursos do dispositivo

> **Equipe:** Team 02  
> **Integrantes:** Paulo Eduardo de Leon Fernandes Alves; Pedro Gustavo Macena da Silva  
> **Sprint:** 06  
> **Status:** **Não implementada no código atual.** O cronômetro da Sessão existe, mas ainda não há som, vibração nem controle de alertas.  
> **Última revisão:** 20/11/2026 — atualizada.

## 1. Contexto
Durante o treino, o usuário nem sempre olha para a tela. A Sessão guiada já controla séries e descanso; falta avisar, por som e vibração, quando o descanso começa e quando termina.

## 2. Objetivo
Integrar a Sessão de treino aos recursos de áudio e vibração do Android, com um controle para ligar ou desligar os alertas e sem quebrar o cronômetro quando algum recurso não estiver disponível.

## 3. Situação atual do cronômetro (implementado)
- Um único relógio (`LaunchedEffect` com `delay(1000)`) conta o tempo total da sessão e, durante o descanso, decrementa a contagem regressiva.
- A **fase de exercício não tem tempo**: o usuário toca em "Próximo" ao terminar a série (`concluirSerie()`), o que inicia o descanso com o tempo do item (`descansoSeg`).
- O **fim do descanso é automático** quando a contagem chega a zero, ou manual com "Pular descanso".
- Pausar/Retomar interrompe o relógio; o botão "Próximo" fica desabilitado enquanto pausado.
- Ao concluir a última série, a sessão mostra o resumo (tempo, séries, kcal estimadas) e "Salvar e sair" grava no histórico.

## 4. Cenário do usuário
**Dado** que o usuário está em uma Sessão com alertas ligados, **quando** tocar em "Próximo" ao fim de uma série, **então** ouve um apito curto e sente uma vibração (se disponível). **Quando** o descanso terminar, ouve um apito duplo e sente uma vibração.

## 5. Requisitos funcionais
- **RF-01:** detectar o fim da série (toque em "Próximo", em `concluirSerie()`).
- **RF-02:** emitir apito curto ao fim da série.
- **RF-03:** detectar o fim automático do descanso (`restante <= 0`).
- **RF-04:** emitir apito duplo ao fim do descanso.
- **RF-05:** vibrar em dispositivos compatíveis.
- **RF-06:** oferecer controle para ligar/desligar os alertas, salvo no `Repositorio`.
- **RF-07:** manter o cronômetro funcionando sem vibrador ou com falha no áudio.
- **RF-08:** não emitir alerta quando o usuário pular o descanso manualmente.

## 6. Restrições técnicas
- Áudio: `ToneGenerator` (`AudioManager.STREAM_NOTIFICATION`) ou `SoundPool`, sem arquivos externos.
- Vibração: `VibratorManager` (API 31+) ou `Vibrator` (versões anteriores), com `VibrationEffect`.
- Permissão `android.permission.VIBRATE` no `AndroidManifest.xml`.
- Liberar o `ToneGenerator` ao sair da tela (`DisposableEffect`).
- Sem serviços remotos.

## 7. Fora do escopo
Alertas com o app fechado ou em segundo plano, notificações agendadas, smartwatch e tempo cronometrado para a fase de exercício.

## 8. Critérios de aceitação
- **CA-01:** fim da série e fim do descanso são detectados.
- **CA-02:** fim da série produz apito curto.
- **CA-03:** fim do descanso produz apito duplo.
- **CA-04:** a vibração ocorre em dispositivo compatível com alertas ligados.
- **CA-05:** com alertas desligados, não há som nem vibração.
- **CA-06:** sem vibrador, o cronômetro e o áudio continuam funcionando.
- **CA-07:** falta de suporte ou de permissão é tratada sem crash.
- **CA-08:** as funcionalidades anteriores continuam funcionando (regressão).
- **CA-09:** a preferência de alertas continua após reabrir o app.
- **CA-10:** há evidências dos estados ligado e desligado, e a equipe explica as limitações.

## 9. Rastreabilidade
| Requisito | Componente | Situação | Evidência |
|---|---|---|---|
| RF-01, RF-03 | `SessaoScreen.kt` (`concluirSerie`, `LaunchedEffect`) | Pontos de detecção existem | `evidence/sprint-06/` |
| RF-02, RF-04, RF-05 | `Alertas.kt` (a criar em `data/` ou `util/`) | Não implementado | — |
| RF-06, RF-09 | `Repositorio` + `AppViewModel` (chave `alertas`) e controle na Sessão | Não implementado | — |
| RF-07, RF-08 | `Alertas.kt` com `try/catch` e checagem de `hasVibrator()` | Não implementado | — |

## 10. Plano de implementação
1. Criar a classe `Alertas(context)` com `apitoCurto()`, `apitoDuplo()` e `vibrar(ms)`, tudo protegido por `try/catch`.
2. Em `Repositorio`, adicionar `carregarAlertas()`/`salvarAlertas(Boolean)`; no `AppViewModel`, expor `alertasLigados` e `alternarAlertas()`.
3. Em `SessaoScreen`, criar `Alertas` com `remember` e liberar em `DisposableEffect`.
4. Chamar `apitoCurto()` + `vibrar()` no fim de `concluirSerie()`, quando o descanso começar.
5. Chamar `apitoDuplo()` + `vibrar()` no `LaunchedEffect` quando `restante` chegar a zero (não no "Pular descanso").
6. Adicionar um botão de alerta (ícone de sino) no cabeçalho da Sessão.
7. Conferir a permissão `VIBRATE` no manifesto.

## 11. Plano de validação
Testar fim de série e fim de descanso com alertas ligados; repetir com alertas desligados; testar com o aparelho no silencioso; testar em emulador (sem vibração); reabrir o app e conferir a preferência. Registrar modelo, versão do Android, volume e resultado observado.

## 12. Resultados da validação
| Cenário | Resultado | Observação |
|---|---|---|
| Todos | NOT RUN | Funcionalidade ainda não implementada. |

## 13. Riscos e observações
- Modo silencioso, volume e "Não perturbe" podem impedir o som.
- O emulador normalmente não vibra; a vibração deve ser testada em aparelho físico.
- Como a fase de exercício não é cronometrada, o "fim do exercício" depende do toque do usuário.

## 14. Uso de IA
Esta revisão foi feita com apoio de IA (Claude), comparando a SPEC com o código.

## 15. Entregáveis
Classe de alertas, controle liga/desliga, SPEC-006, `SPRINT-06.md` e evidências reais em `evidence/sprint-06/`.

# Sprint 06 — Integração com recursos do dispositivo

**Equipe:** Team 02  
**Integrantes:** Paulo Eduardo de Leon Fernandes Alves e Pedro Gustavo Macena da Silva  
**SPEC relacionada:** `docs/specs/SPEC-006.md`  
**Status:** **não implementada no código atual.** O cronômetro da Sessão existe; som, vibração e controle de alertas faltam.  
**Última revisão:** 20/11/2026

## Objetivo
Avisar, com som e vibração, o fim de cada série e o fim do descanso durante a Sessão de treino.

## Situação atual
- `SessaoScreen` tem relógio de sessão, contagem regressiva de descanso, pausar/retomar e pular descanso.
- A série termina quando o usuário toca em "Próximo"; o descanso termina sozinho ao chegar a zero.
- Não há `ToneGenerator`, `Vibrator`/`VibratorManager`, nem preferência de alertas.

## Comportamento a implementar
- Fim da série ("Próximo"): apito curto e vibração, se disponível.
- Fim automático do descanso: apito duplo e vibração.
- "Pular descanso": sem alerta.
- Botão para ligar/desligar alertas, salvo no `Repositorio`.
- Sem vibrador ou com falha de áudio, o cronômetro continua.

## Tarefas
- [X] Criar `Alertas.kt` com `apitoCurto()`, `apitoDuplo()` e `vibrar()`, protegidos por `try/catch`.
- [X] Adicionar chave `alertas` no `Repositorio` e estado no `AppViewModel`.
- [X] Chamar os alertas em `concluirSerie()` e no fim do descanso, no `LaunchedEffect`.
- [X] Adicionar botão de alertas no cabeçalho da Sessão.
- [ ] Conferir `android.permission.VIBRATE` no `AndroidManifest.xml`.
- [X] Liberar o `ToneGenerator` com `DisposableEffect`.

## Critérios de aceite
| CA | Descrição | Situação no código | Execução Android |
|---|---|---|---|
| CA-01 | Fim de série e de descanso detectados | Pontos existem; sem alerta ligado a eles | RUN |
| CA-02 | Apito curto ao fim da série | Não implementado | NOT RUN |
| CA-03 | Apito duplo ao fim do descanso | Não implementado | NOT RUN |
| CA-04 | Vibração em aparelho compatível | Não implementado | NOT RUN |
| CA-05 | Alertas desligados não emitem aviso | Não implementado | NOT RUN |
| CA-06 | Sem vibrador, cronômetro e áudio continuam | Não implementado | NOT RUN |
| CA-07 | Falta de suporte/permissão sem crash | Não implementado | NOT RUN |
| CA-08 | Funcionalidades anteriores continuam | Implementado | RUN |
| CA-09 | Preferência de alertas continua após reabrir | Não implementado | NOT RUN |
| CA-10 | Evidências ligado/desligado e explicação das limitações | Implementado | RUN |

## Evidências
Evidências na pasta images -> projects/team-02/APP/images.

## Resultado e pendências
Funcionalidade pendente. Testar a vibração em aparelho físico, já que o emulador normalmente não vibra.

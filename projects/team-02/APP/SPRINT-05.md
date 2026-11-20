# Sprint 05 — Persistência local dos dados

**Equipe:** Team 02  
**Integrantes:** Paulo Eduardo de Leon Fernandes Alves e Pedro Gustavo Macena da Silva  
**SPEC relacionada:** `docs/specs/SPEC-005.md`  
**Status:** implementada no código Android com SharedPreferences + JSON. **DataStore:** não utilizado utilizado SharedPreferences. **Teste de reinício:** Realizado.  
**Última revisão:** 20/11/2026

## Objetivo
Salvar os dados do usuário no aparelho e recuperá-los quando o TreinePlus for reaberto.

## Decisão técnica (implementada)
- `SharedPreferences` (arquivo `treineplus`) com JSON, no fluxo Tela → `AppViewModel` → `Repositorio` → `SharedPreferences`.
- Leitura síncrona na criação do ViewModel; gravação com `apply()` a cada alteração.
- Room não utilizado.

## O que é salvo
Perfil completo, treinos do usuário, agenda semanal, histórico de sessões, registros de peso, água do dia (zera ao mudar o dia) e favoritos.

## Mudança em relação ao planejado
A apresentação previa Preferences DataStore para 4 valores do perfil. O código usa SharedPreferences e guarda muito mais dados.

## Critérios de aceite
| CA | Descrição | Situação no código | Execução Android |
|---|---|---|---|
| CA-01 | `Repositorio` grava e lê todas as chaves | Implementado (SharedPreferences) | RUN |
| CA-02 | Perfil gravado só após validação | Implementado | RUN |
| CA-03 | Valores recuperados ao iniciar | Implementado | RUN |
| CA-04 | Abre no Início com "Olá, Nome" | Implementado (sem "!") | RUN |
| CA-05 | Edição exibe valores recuperados | Implementado | RUN |
| CA-06 | Novo salvamento atualiza os dados | Implementado | RUN |
| CA-07 | Primeira execução abre em Boas-vindas | Implementado | RUN |
| CA-08 | JSON ausente/inválido não encerra o app | Implementado (`try/catch`) | RUN |
| CA-09 | Dados inválidos não gravados | Implementado | RUN |
| CA-10 | Treinos, agenda, histórico, pesos e favoritos continuam após reabrir | Implementado | RUN |
| CA-11 | Teste de fechar e reabrir evidenciado | Implementado | RUN |

## Evidências
Evidências na pasta images -> projects/team-02/APP/images.

## Resultado e pendências
- Testes Realizados e registrados resultados esperados.
- Decidido SharedPreferences.
- Avaliar guardar o `id` do treino no histórico (hoje só o nome é salvo).

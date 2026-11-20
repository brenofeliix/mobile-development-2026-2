# Sprint 04 — Formulário de perfil e validação

**Equipe:** Team 02  
**Integrantes:** Paulo Eduardo de Leon Fernandes Alves e Pedro Gustavo Macena da Silva  
**SPEC relacionada:** `docs/specs/SPEC-004.md`  
**Status:** implementada código no Android Studio. **Execução Android:** Sucesso.  
**Última revisão:** 20/11/2026

## Objetivo
Cadastrar e editar o perfil do usuário, impedindo o salvamento de dados inválidos.

## O que foi implementado
- `SetupScreen` usada no cadastro ("Vamos te conhecer") e na edição ("Editar perfil").
- Campos: Nome, Altura (cm), Peso (kg), Idade; seletores: Sexo, Objetivo, Nível de atividade, "Onde você treina?".
- Regras: nome não vazio; altura inteira de 100 a 250; peso de 30 a 300 (aceita vírgula); idade de 12 a 100.
- Saudação "Olá, Nome" no Início com o primeiro nome.
- Registro do peso de hoje na aba Perfil, com a mesma faixa de peso.

## Divergências em relação ao planejado
- 8 entradas em vez de 4; objetivos Perder peso, Ganhar massa e Manter a forma (3, não 4).
- Erro exibido um por vez, acima do botão, e não junto ao campo.
- Validação dentro da tela, não em função separada.
- Sem mensagem de confirmação após salvar.

## Critérios de aceite
| CA | Descrição | Situação no código | Execução Android |
|---|---|---|---|
| CA-01 | Campos e seletores visíveis | Implementado | RUN |
| CA-02 | Nome vazio/só espaços rejeitado | Implementado | RUN |
| CA-03 | Peso fora de 30–300 ou não numérico rejeitado | Implementado | RUN |
| CA-04 | Altura fora de 100–250 ou não inteira rejeitada | Implementado | RUN |
| CA-05 | Idade fora de 12–100 rejeitada | Implementado | RUN |
| CA-06 | Mensagem de erro exibida | Implementado (uma por vez, acima do botão) | RUN |
| CA-07 | Perfil válido salvo e navegação correta | Implementado | RUN |
| CA-08 | Dados inválidos não salvos | Implementado | RUN |
| CA-09 | Correção sem reiniciar a tela | Implementado | RUN |
| CA-10 | Saudação usa o primeiro nome | Implementado | RUN |
| CA-11 | Edição vem preenchida | Implementado | RUN |
| CA-12 | Evidências de válido e inválido | Implementado | RUN |

## 14. Uso de IA
Foi utilizado a IA (Claude), para correção de erros e revisão.

## Evidências
Evidências na pasta images -> projects/team-02/APP/images.

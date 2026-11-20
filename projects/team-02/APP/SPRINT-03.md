# Sprint 03 — Navegação e múltiplas telas

**Equipe:** Team 02  
**Integrantes:** Paulo Eduardo de Leon Fernandes Alves e Pedro Gustavo Macena da Silva  
**SPEC relacionada:** `docs/specs/SPEC-003.md`  
**Status:** implementada no código Android (Navigation Compose).  
**Última revisão:** 20/11/2026

## Objetivo
Conectar todas as telas do TreinePlus com navegação por abas inferiores e rotas de detalhe, sessão e editor.

## O que foi implementado
- 11 rotas no objeto `Rotas` (`TreineplusApp.kt`): `welcome`, `setup`, `editar_perfil`, `home`, `treinos`, `agenda`, `nutricao`, `perfil`, `detalhe/{id}`, `sessao/{id}`, `editor/{id}`.
- Barra inferior com cinco abas (Início, Treinos, Agenda, Nutrição, Perfil), visível só nas abas, com estado preservado.
- Tela inicial decidida pelo perfil salvo: Boas-vindas ou Início.
- Agenda com os sete dias da semana atual, datas, treino do dia, dia de descanso e checklist semanal.
- Detalhe do treino com iniciar, agendar, copiar, editar e excluir.
- Sessão guiada com confirmação de saída (`BackHandler`).

## Mudança em relação ao planejado
O fluxo linear de sete destinos da apresentação (Início → Modalidade → Foco → Semana → Dia → Treino/Dieta) foi substituído por abas e rotas por `id`. Os dias de descanso não são mais fixos (sábado e domingo): qualquer dia sem treino agendado é descanso.

## Critérios de aceite
| CA | Descrição | Situação no código | Execução Android |
|---|---|---|---|
| CA-01 | Abre em Boas-vindas (sem perfil) ou Início (com perfil) | Implementado | RUN |
| CA-02 | As 11 rotas são acessíveis | Implementado | RUN |
| CA-03 | Barra inferior só nas abas, com destaque | Implementado | RUN |
| CA-04 | Agenda mostra os sete dias com datas | Implementado | RUN |
| CA-05 | Dia com treino mostra exercícios e ações | Implementado | RUN |
| CA-06 | Dia sem treino mostra descanso, sem iniciar treino | Implementado | RUN |
| CA-07 | Sessão acessível por Detalhe, Agenda e Início; Nutrição pela aba | Implementado | RUN |
| CA-08 | Voltar retorna à tela anterior; Sessão pede confirmação | Implementado | RUN |
| CA-09 | `id` inválido não encerra o app | Implementado | RUN |
| CA-10 | Fluxo executado e evidenciado | Implementado| RUN |

## Evidências
**Caminho para a evidencia**: projects/team-02/APP/images/Sprint03_image.png

![Resultado da Sprint-03](/images/Sprint03_image.png)


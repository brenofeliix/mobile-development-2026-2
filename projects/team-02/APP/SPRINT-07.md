# Sprint 07 — Integração final, qualidade e entrega

**Equipe:** Team 02  
**Integrantes:** Paulo Eduardo de Leon Fernandes Alves e Pedro Gustavo Macena da Silva  
**SPEC relacionada:** `docs/specs/SPEC-007.md`  
**Status:** Corrigida.  
**Última revisão:** 20/11/2026

## Objetivo
Consolidar as funcionalidades, corrigir problemas, atualizar a documentação e preparar a demonstração final.

## Revisão das SPECs
| Documento | Escopo | Situação no código | Pendência |
|---|---|---|---|
| SPEC-001 | Produto e primeira tela | Boas-vindas e Início existem | Zero |
| SPEC-002 | Estado e interação | Chips e seletores (`ChipOpcao`, `SeletorOpcoes`) | Zero|
| SPEC-003 | Navegação | Implementada (abas + rotas por `id`) | Validado |
| SPEC-004 | Perfil e validação | Implementada com divergências | Correções Aplicadas|
| SPEC-005 | Persistência | Implementada com SharedPreferences | utilizado SharedPreferences|
| SPEC-006 | Alertas sonoros e vibração | **A implementar** | Implementar e testar em aparelho |
| SPEC-007 | Qualidade e entrega | — | Executar regressão e relatório |

## Correções prioritárias
- [X] **D-01 (crítica):** corrigir os botões de água em `NutricaoScreen.kt` (linhas 76–78), por exemplo `BotaoContorno("+ 250 ml", { vm.adicionarAgua(250) }, Modifier.weight(1f))`. Confirmar a compilação.
- [ ] **D-02:** implementar alertas (SPEC-006).
- [X] **D-03:** decidir DataStore x SharedPreferences.
- [X] **D-04:** erros junto ao campo, validador separado e confirmação ao salvar.
- [X] **D-05/D-06:** proteger a Sessão contra `id` inválido e treino sem exercícios.
- [X] **D-07/D-08:** ligar histórico e checklist ao `id` do treino.
- [X] **D-09:** registrar no README que o Foco não filtra treinos.

Detalhes de cada defeito em `docs/specs/SPEC-007.md`, seção 5.

## Roteiro de regressão — 10 passos
- [X] 1. Instalar com dados limpos e conferir Boas-vindas → Cadastro.
- [X] 2. Testar entradas inválidas no Cadastro.
- [X] 3. Salvar perfil válido e conferir "Olá, Nome" no Início.
- [X] 4. Treinos: filtrar, buscar, favoritar e abrir treino pronto.
- [X] 5. Copiar, editar e criar treino no Editor.
- [X] 6. Agendar treino; conferir dia com treino e dia de descanso na Agenda.
- [X] 7. Executar Sessão completa e conferir histórico, Início e Mapa corporal.
- [X] 8. Nutrição: métricas, água e trocar sugestões.
- [X] 9. Fechar/reabrir e conferir a persistência.
- [ ] 10. Sessão com alertas ligados e desligados (após SPEC-006).

## Critérios finais — 14 itens
Definidos em `docs/specs/SPEC-007.md` e registrados como `PASS`, `FAIL` ou `NOT RUN` em `evidence/sprint-07/final-validation.md`. Situação atual: todos `NOT RUN`.

## Qualidade e repositório
- [X] Revisar espaçamento, textos, contraste e adaptação a telas pequenas e grandes.
- [X] Atualizar o README (objetivo, tecnologias, como executar, limitações).
- [X] Remover arquivos locais, segredos e artefatos (`local.properties`, `.idea/`, `build/`).
- [X] Conferir SPECs, SPRINTs e rastreabilidade.
- [X] Preparar apresentação com resultados observados.
- [X] Criar Pull Request com documentação e evidências.

## Evidências
Evidências na pasta images -> projects/team-02/APP/images.

## Resultado
Pendente. A revisão de código apontou o que existe e o que falta, mas a conclusão depende da compilação, da regressão Android e dos resultados reais da equipe.

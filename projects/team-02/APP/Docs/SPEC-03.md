# SPEC-003 — Navegação e múltiplas telas

> **Equipe:** Team 02  
> **Integrantes:** Paulo Eduardo de Leon Fernandes Alves; Pedro Gustavo Macena da Silva  
> **Sprint:** 03  
> **Status:** Implementada no código Android (Navigation Compose). Execução em emulador/dispositivo pendente de registro.  
> **Última revisão:** 20/11/2026 — atualizada

## 1. Contexto
O usuário precisa percorrer uma jornada completa no TreinePlus: cadastrar o perfil, escolher um treino, agendá-lo na semana, executá-lo de forma guiada e consultar o plano de alimentação. A proposta inicial previa um fluxo linear de sete destinos (Início → Modalidade → Foco → Semana → Dia → Treino/Dieta). A implementação evoluiu para uma navegação por **abas inferiores** com telas de detalhe, que permite acessar cada área diretamente.

## 2. Objetivo
Implementar a navegação entre todas as telas do TreinePlus com Navigation Compose, usando uma barra de navegação inferior para as áreas principais e rotas com argumento para detalhe, sessão de treino e editor, sempre permitindo voltar à etapa anterior.

## 3. Cenário do usuário
**Dado** que o usuário abriu o TreinePlus, **quando** concluir o cadastro, escolher um treino na aba Treinos e agendá-lo para um dia na Agenda, **então** poderá iniciar a sessão guiada a partir da Agenda ou do Início e consultar a aba Nutrição a qualquer momento.

## 4. Requisitos funcionais
- **RF-01:** iniciar em `welcome` quando não houver perfil salvo e em `home` quando houver.
- **RF-02:** disponibilizar 11 rotas: `welcome`, `setup`, `editar_perfil`, `home`, `treinos`, `agenda`, `nutricao`, `perfil`, `detalhe/{id}`, `sessao/{id}` e `editor/{id}`.
- **RF-03:** exibir barra inferior com cinco abas (Início, Treinos, Agenda, Nutrição, Perfil) somente nas rotas de aba.
- **RF-04:** ao trocar de aba, preservar o estado de cada aba (`saveState`/`restoreState`) e evitar cópias duplicadas da mesma tela (`launchSingleTop`).
- **RF-05:** transportar o identificador do treino (`id`) para Detalhe, Sessão e Editor; `editor/novo` cria um treino.
- **RF-06:** a partir de Detalhe, permitir iniciar o treino, agendar em um dia da semana, copiar treino pronto para "Meus treinos" e editar/excluir treino próprio.
- **RF-07:** a partir da Agenda, permitir escolher o treino do dia selecionado, iniciar o treino ou ver os detalhes; dia sem treino é apresentado como "Dia de descanso".
- **RF-08:** permitir voltar à tela anterior por botão ou gesto; na Sessão, pedir confirmação antes de sair, avisando que o progresso não será salvo.
- **RF-09:** após o cadastro inicial, remover Boas-vindas e Cadastro da pilha, para que voltar a partir do Início não retorne ao formulário.

## 5. Requisitos de conteúdo
- **Modalidade (tipo de treino):** Academia, Calistenia e Ao ar livre (`enum Local`), usada como filtro em Início e Treinos e como tipo no Editor.
- **Foco:** preferência do perfil "Onde você treina?" — Academia, Calistenia ou Híbrido (`enum Foco`). É salva no perfil, mas ainda não filtra os treinos.
- **Região do corpo:** não é escolhida no fluxo; aparece no Mapa corporal do Perfil, calculado a partir das séries realizadas.
- **Treinos:** 8 treinos prontos e 34 exercícios no catálogo (`Catalogo.kt`), além dos treinos criados pelo usuário.
- **Semana:** Segunda a Domingo com as datas da semana atual; cada dia pode receber um treino ou ficar como descanso. Não há dias de descanso fixos.
- **Dia:** lista de exercícios com séries e repetições, com ações Iniciar treino e Ver detalhes.
- **Treino (Sessão):** exercício atual, série atual, cronômetro total, contagem regressiva do descanso, pausar/retomar, pular descanso e lista de exercícios com progresso.
- **Dieta (Nutrição):** IMC, metabolismo basal, gasto diário, meta calórica, macros, hidratação e plano de cinco refeições com opção "Trocar sugestões".

## 6. Restrições técnicas
- Kotlin, Jetpack Compose e Navigation Compose (`NavHost`, `rememberNavController`, `composable`).
- Um único `AppViewModel` compartilhado entre as telas, obtido com `viewModel()` em `TreineplusApp`.
- Sem servidor ou API externa; dados de catálogo estáticos em `Catalogo.kt`.

## 7. Fora do escopo
Login, contas, sincronização remota, filtragem de treinos pelo Foco do perfil e geração automática de treinos.

## 8. Critérios de aceitação
- **CA-01:** na primeira execução o app abre em Boas-vindas; com perfil salvo, abre no Início.
- **CA-02:** as 11 rotas estão acessíveis pelo fluxo da interface.
- **CA-03:** a barra inferior aparece apenas nas cinco abas e destaca a aba atual.
- **CA-04:** a Agenda apresenta os sete dias da semana com as datas correspondentes.
- **CA-05:** selecionar um dia com treino exibe os exercícios desse treino e as ações Iniciar treino e Ver detalhes.
- **CA-06:** um dia sem treino exibe "Dia de descanso" e não oferece iniciar treino.
- **CA-07:** a Sessão pode ser aberta a partir de Detalhe, Agenda e Início; a Nutrição é acessível pela aba.
- **CA-08:** voltar retorna à tela anterior; sair da Sessão pede confirmação.
- **CA-09:** um `id` inexistente não encerra o app (Detalhe exibe "Treino não encontrado").
- **CA-10:** evidências reais do fluxo foram adicionadas após a execução.

## 9. Rastreabilidade
| Requisito | Componente implementado | Evidência |
|---|---|---|
| RF-01 a RF-05, RF-09 | `TreineplusApp.kt` (`Rotas`, `NavHost`, `irParaAba`, barra inferior); `MainActivity.kt` | `evidence/sprint-03/` |
| RF-06 | `DetalheScreen.kt`, `AppViewModel.duplicar`, `excluirTreino`, `definirAgenda` | capturas reais |
| RF-07 | `AgendaScreen.kt`, `AppViewModel.datasDaSemana` | capturas reais |
| RF-08 | `BotaoVoltar`, `BackHandler` e diálogo em `SessaoScreen.kt` | capturas reais |
| Conteúdo | `HomeScreen.kt`, `TreinosScreen.kt`, `NutricaoScreen.kt`, `Catalogo.kt` | capturas reais |

## 10. Plano de implementação (realizado)
Rotas centralizadas no objeto `Rotas`, com funções auxiliares `detalhe(id)`, `sessao(id)` e `editor(id)`. O argumento é lido por `NavBackStackEntry.id()`. As abas usam `popUpTo(HOME) { saveState = true }`, `launchSingleTop` e `restoreState`. A tela inicial é decidida uma vez com `remember`, conforme a existência de perfil.

## 11. Plano de validação
1. Primeira execução (dados do app limpos): conferir Boas-vindas → Cadastro → Início.
2. Navegar pelas cinco abas e conferir destaque e preservação do estado (por exemplo, o filtro em Treinos).
3. Abrir um treino pronto, agendar para hoje e conferir na Agenda e no Início.
4. Selecionar um dia sem treino e conferir "Dia de descanso".
5. Iniciar a sessão pela Agenda, tentar sair e conferir o diálogo de confirmação.
6. Copiar um treino pronto, editar, salvar e excluir.
7. Voltar em cada etapa e conferir a tela anterior.

## 12. Resultados da validação
| Passo | Resultado | Observação |
|---|---|---|
| 1  | RUN | Passou |
| 2  | RUN | Passou |
| 3  | RUN | Passou |
| 4  | RUN | Passou |
| 5  | RUN | Passou |
| 6  | RUN | Passou |
| 7  | RUN | Passou |

## 13. Riscos e observações
- **Mudança em relação à apresentação:** o fluxo linear de sete destinos foi substituído por abas inferiores e rotas por `id`. As rotas `modalidade`, `foco/{mod}`, `semana/{mod}`, `dia/{mod}/{indice}`, `treino/{mod}/{indice}` e `dieta/{indice}` não existem no código.
- O Foco do perfil é salvo, mas não filtra os treinos.
- Em `SessaoScreen`, um `id` inexistente faz a tela ficar vazia (`vm.treino(id) ?: return`), sem mensagem nem botão de voltar; o gesto de voltar do sistema continua funcionando. Sugestão: reaproveitar a mensagem "Treino não encontrado" usada no Detalhe.
- Um treino salvo sem exercícios faria a Sessão falhar em `itens[exIdx.coerceIn(0, size - 1)]`. O Editor impede salvar treino vazio, então o risco se limita a dados corrompidos.

## 14. Uso de IA
Esta revisão da SPEC foi feita com apoio de IA (Claude), comparando e melhorando o documento para a entrega.

## 15. Entregáveis
Navegação Android implementada, SPEC-003, `SPRINT-03.md` e evidências reais em `projects/team-02/APP/images`.

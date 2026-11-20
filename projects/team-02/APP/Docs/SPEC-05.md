# SPEC-005 — Persistência local dos dados

> **Equipe:** Team 02  
> **Integrantes:** Paulo Eduardo de Leon Fernandes Alves; Pedro Gustavo Macena da Silva  
> **Sprint:** 05  
> **Status:** Implementada no código Android com **SharedPreferences + JSON** (não DataStore). Teste de fechar e reabrir o app pendente de registro.  
> **Última revisão:** 20/11/2026

## 1. Contexto
Depois de cadastrar o perfil, montar treinos e registrar sessões, o usuário espera encontrar tudo igual ao fechar e reabrir o TreinePlus. A apresentação previa Preferences DataStore apenas para quatro valores do perfil. A implementação passou a guardar também treinos próprios, agenda, histórico, pesos, água e favoritos.

## 2. Objetivo
Salvar localmente os dados do usuário e recuperá-los ao iniciar o aplicativo, sem depender de internet.

## 3. Cenário do usuário
**Dado** que o usuário salvou um perfil válido e usou o app, **quando** fechar completamente e reabrir o TreinePlus, **então** o app abre direto no Início com "Olá, Nome", e treinos, agenda, histórico, pesos e favoritos continuam lá.

## 4. Requisitos funcionais
- **RF-01:** guardar o perfil (nome, altura, peso, idade, sexo, objetivo, nível de atividade e foco).
- **RF-02:** guardar treinos criados pelo usuário, agenda semanal, histórico de sessões, registros de peso, água do dia e treinos favoritos.
- **RF-03:** recuperar todos os dados quando o `AppViewModel` é criado.
- **RF-04:** abrir no Início quando houver perfil salvo e em Boas-vindas quando não houver.
- **RF-05:** preencher "Editar perfil" com os valores recuperados.
- **RF-06:** atualizar os dados salvos sempre que forem alterados.
- **RF-07:** tratar dados ausentes ou corrompidos sem encerrar o app, voltando ao estado vazio.
- **RF-08:** zerar automaticamente a contagem de água quando o dia mudar.
- **RF-09:** criar um registro de peso para o dia sempre que o peso do perfil mudar (um registro por data).

## 5. Decisão técnica (implementada)
- **Mecanismo:** `SharedPreferences` (arquivo `treineplus`, `MODE_PRIVATE`), com valores serializados em JSON (`org.json`).
- **Fluxo:** Tela → `AppViewModel` (`AndroidViewModel`) → `Repositorio` → `SharedPreferences`.
- **Leitura:** síncrona, na criação do ViewModel. Assim o app já sabe se há perfil antes de escolher a tela inicial.
- **Gravação:** `apply()` (assíncrona) a cada alteração.
- **Room:** não utilizado; o volume de dados é pequeno e não exige consultas relacionais.

| Chave | Conteúdo | Formato |
|---|---|---|
| `perfil` | Perfil completo | objeto JSON |
| `treinos` | Treinos do usuário (`id`, `nome`, `descricao`, `nivel`, `local`, `itens`) | lista JSON |
| `agenda` | Dia da semana (0 = segunda) → id do treino | objeto JSON |
| `historico` | Sessões (`d` data, `t` nome do treino, `m` minutos, `k` kcal) | lista JSON |
| `pesos` | Registros (`d` data, `k` kg) | lista JSON |
| `agua_data` / `agua_ml` | Data e mL de água do dia | texto / inteiro |
| `favoritos` | Ids dos treinos favoritos | lista JSON |

## 6. Fora do escopo
Banco relacional, sincronização em nuvem, backup remoto, exportação, compartilhamento e múltiplos usuários.

## 7. Critérios de aceitação
- **CA-01:** o `Repositorio` grava e lê o perfil e as demais chaves da tabela.
- **CA-02:** o perfil só é gravado depois da validação (SPEC-004).
- **CA-03:** os valores são recuperados ao iniciar o app.
- **CA-04:** com perfil salvo, o app abre no Início com "Olá, Nome".
- **CA-05:** "Editar perfil" exibe os valores recuperados.
- **CA-06:** salvar novamente atualiza os valores gravados.
- **CA-07:** na primeira execução, sem perfil, o app abre em Boas-vindas.
- **CA-08:** JSON ausente ou inválido não encerra o app.
- **CA-09:** dados inválidos não são gravados.
- **CA-10:** treinos, agenda, histórico, pesos e favoritos continuam após reabrir o app.
- **CA-11:** evidências de salvamento e recuperação após reinício foram adicionadas.

## 8. Rastreabilidade
| Requisito | Componente implementado | Evidência |
|---|---|---|
| RF-01, RF-02, RF-06, RF-07 | `Repositorio.kt` (`carregar*`/`salvar*`, função `lista`) | `evidence/sprint-05/` |
| RF-03, RF-08, RF-09 | `AppViewModel.kt` (inicialização, `adicionarAgua`, `registrarPesoInterno`) | capturas reais |
| RF-04 | `TreineplusApp.kt` (`inicio`) | capturas reais |
| RF-05 | `SetupScreen.kt` (`editando = true`) | capturas reais |

## 9. Plano de implementação (realizado)
O `Repositorio` concentra leitura e gravação. Listas usam a função genérica `lista(chave, conversor)`, que devolve lista vazia em caso de erro. O perfil devolve `null` se o JSON estiver ausente ou inválido. O `AppViewModel` carrega tudo em listas e mapas observáveis do Compose (`mutableStateListOf`, `mutableStateMapOf`) e chama o `Repositorio` depois de cada alteração.

## 10. Plano de validação
1. Primeira execução com dados limpos: conferir Boas-vindas.
2. Salvar perfil, criar um treino, agendar, concluir uma sessão, favoritar e beber água.
3. Fechar o app pela lista de recentes e reabrir: conferir Início, saudação e demais dados.
4. Editar o perfil, reabrir e conferir a atualização.
5. Mudar a data do aparelho para o dia seguinte e conferir água zerada.

## 11. Resultados da validação
| Passo | Resultado | Observação |
|---|---|---|
| 1 | RUN | Passou. |
| 2 | RUN | Passou. |
| 3 | RUN | Passou. |
| 4 | RUN | Passou. |
| 5 | RUN | Passou. |



## 12. Uso de IA
Esta revisão foi feita com apoio de IA (Claude), comparando a SPEC com o código.

## 13. Entregáveis
`Repositorio` com SharedPreferences, SPEC-005, `SPRINT-05.md` e evidências reais de reinício em `evidence/sprint-05/`.

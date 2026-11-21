# SPEC-007 — Integração final, qualidade e entrega

> **Equipe:** Team 02  
> **Integrantes:** Paulo Eduardo de Leon Fernandes Alves; Pedro Gustavo Macena da Silva  
> **Sprint:** 07  
> **Status:** Corrigida e operante.  
> **Última revisão:** 20/11/2026 — roteiro e defeitos atualizados a partir de revisão do código-fonte.

## 1. Contexto
A última Sprint consolida o que foi definido nas SPECs 001 a 006, corrige problemas, revisa a experiência e prepara documentação e demonstração. Não deve introduzir uma funcionalidade grande nova.

O código atual já tem: Boas-vindas, cadastro e edição de perfil, Início com resumo do dia, catálogo de treinos com filtro, busca e favoritos, editor de treinos próprios, agenda semanal, sessão guiada com descanso, nutrição com plano de refeições e hidratação, perfil com mapa corporal e gráfico de peso, e persistência local.

## 2. Objetivo
Entregar uma versão integrada, estável e explicável do TreinePlus, com resultados de regressão e evidências reais.

## 3. Requisitos funcionais e de qualidade
- **RF-01:** revisar as sete SPECs e garantir que correspondam à implementação.
- **RF-02:** executar a regressão do fluxo principal (seção 4).
- **RF-03:** revisar textos, espaçamento, contraste, estados vazios e adaptação a diferentes telas.
- **RF-04:** corrigir os defeitos críticos (seção 5) ou registrá-los como limitações conhecidas.
- **RF-05:** atualizar o README com objetivo, tecnologias, instruções e limitações.
- **RF-06:** organizar evidências e o relatório de validação.
- **RF-07:** preparar uma demonstração que passe por problema, SPEC, código, validação e evidência.
- **RF-08:** garantir que cada integrante consiga explicar os principais componentes.

## 4. Roteiro de regressão — 10 passos
1. Instalar com dados limpos e abrir: conferir Boas-vindas e seguir para o Cadastro.
2. No Cadastro, testar entradas inválidas (nome vazio, altura 90, peso 301, idade 11).
3. Salvar um perfil válido e conferir o Início com "Olá, Nome".
4. Na aba Treinos, filtrar por tipo, buscar por exercício, favoritar e abrir um treino pronto.
5. Copiar o treino pronto para "Meus treinos", editar no Editor e salvar; criar um treino novo.
6. Agendar um treino para hoje; na Agenda, conferir um dia com treino e um dia de descanso.
7. Executar uma Sessão completa (pausar, pular descanso, salvar) e conferir histórico, kcal e minutos no Início e Mapa corporal no Perfil.
8. Na Nutrição, conferir métricas, adicionar e desfazer água e trocar sugestões de refeições.
9. Fechar o app pela lista de recentes, reabrir e conferir perfil, treinos, agenda, histórico e favoritos.
10. Executar a Sessão com alertas ligados e desligados (depende da implementação da SPEC-006).

## 5. Defeitos e limitações encontrados na revisão do código
| ID | Descrição | Arquivo | Gravidade | Ação sugerida |
|---|---|---|---|---|
| D-01 | Os botões de água usam lambda final em `BotaoContorno`, cujo último parâmetro é `modifier` e não `onClick`. Isso deve causar **erro de compilação** (confirmar no Android Studio). Além disso, `BotaoContorno` usa `fillMaxWidth()`, então dentro do `Row` o primeiro botão ocuparia toda a largura e os outros dois sumiriam. | `NutricaoScreen.kt` (linhas 76–78) | Crítica | `BotaoContorno("+ 250 ml", { vm.adicionarAgua(250) }, Modifier.weight(1f))` e o mesmo nos outros dois. |
| D-02 | Alertas sonoros e vibração não implementados. | `SessaoScreen.kt` | Alta | Implementar conforme SPEC-006. |
| D-03 | Persistência usa SharedPreferences, mas a apresentação define DataStore. | `Repositorio.kt` | Média | Migrar ou atualizar a apresentação (SPEC-005). |
| D-04 | Erros do formulário aparecem um por vez, longe do campo; validador está dentro da tela; não há confirmação ao salvar. | `SetupScreen.kt` | Média | Ver SPEC-004, seção 13. |
| D-05 | Sessão com `id` inexistente fica com a tela vazia. | `SessaoScreen.kt` | Baixa | Exibir "Treino não encontrado" e botão Voltar. |
| D-06 | Treino sem exercícios (apenas por dados corrompidos) faria a Sessão falhar. | `SessaoScreen.kt` | Baixa | Tratar `itens.isEmpty()` antes de acessar a lista. |
| D-07 | Histórico, Mapa corporal e "feito hoje" ligam a sessão ao treino pelo **nome**; renomear ou excluir perde a ligação. | `AppViewModel.kt`, `HomeScreen.kt` | Baixa | Guardar também o `id` do treino em `Sessao`. |
| D-08 | O checklist da Agenda marca o dia como feito se houver qualquer sessão naquela data, mesmo de outro treino. | `AgendaScreen.kt` | Baixa | Comparar também com o treino agendado. |
| D-09 | O Foco do perfil ("Onde você treina?") não filtra os treinos. | — | Limitação | Registrar como limitação conhecida. |

## 6. Critérios finais de aceitação — 14 critérios
- **CA-01:** SPEC-001 está coerente com a tela inicial (Boas-vindas/Início).
- **CA-02:** SPEC-002 está coerente com os seletores e filtros (chips de tipo, objetivo e foco).
- **CA-03:** SPEC-003 está coerente com a navegação por abas e rotas.
- **CA-04:** SPEC-004 está coerente com o formulário e suas regras.
- **CA-05:** SPEC-005 está coerente com a persistência local.
- **CA-06:** SPEC-006 está coerente com os alertas da Sessão.
- **CA-07:** SPEC-007 e o relatório final estão atualizados.
- **CA-08:** o app Android compila sem erros (inclui a correção do D-01).
- **CA-09:** o app Android inicia em dispositivo ou emulador.
- **CA-10:** os 10 passos de regressão têm resultado registrado.
- **CA-11:** cada resultado tem evidência, quando aplicável.
- **CA-12:** README e estrutura do repositório estão atualizados.
- **CA-13:** não há segredos, arquivos locais (`local.properties`, `.idea/`, `build/`) ou artefatos indevidos no repositório.
- **CA-14:** a equipe consegue explicar a implementação e as limitações.

## 7. Restrições
Não marcar PASS sem execução observada. Usar `PASS`, `FAIL` ou `NOT RUN`. Revisão de código não substitui build, execução e teste Android.

## 8. Fora do escopo
Novas funcionalidades de grande porte, autenticação, sincronização em nuvem e notificações agendadas.

## 9. Rastreabilidade
| Item | Artefato | Evidência |
|---|---|---|
| RF-01 a RF-08 | SPECs, README, código e relatório | `evidence/sprint-07/` |
| D-01 a D-09 | Commits de correção ou seção "Limitações" do README | `final-validation.md` |
| CA-01 a CA-14 | `final-validation.md` | resultados e capturas reais |

## 10. Plano de implementação
Corrigir D-01 primeiro (bloqueia a compilação); implementar a SPEC-006; decidir D-03 e D-04; percorrer o roteiro; revisar interface e acessibilidade básica; atualizar README; organizar evidências; preparar apresentação e Pull Request.

## 11. Plano de validação
Executar os 10 passos em instalação limpa. Registrar ambiente (modelo, versão do Android, versão do app), passo, resultado, observação e evidência. Itens não executados ficam como `NOT RUN`.

## 12. Resultados da validação
| Passo/Critério | Resultado |
|---|---|
| Passos 1 a 10 | PASS |
| CA-01 a CA-14 | PASS |

## 13. Riscos e observações
Riscos Corrigidos Esperado que o aplicativo funcione corretamente.

## 14. Uso de IA
Utilizado na recomendação de ferramentas para o aplicativo, na correção de código e  na revisão das SPECs e Sprints.

## 15. Entregáveis
Aplicativo integrado, SPEC-007, `SPRINT-07.md`, README, `evidence/sprint-07/final-validation.md` e apresentação final.

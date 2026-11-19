# Validação final — Wiflux (Team 03)

## Ambiente do teste

| Item | Informação |
| --- | --- |
| Quem executou | Geovanna Gaspar |
| Data do teste | 18/11/2026 (por volta de 22h50, conforme o registro "Quarto 18/11 22:50" no histórico) |
| Dispositivo | Emulador Android do Android Studio — Pixel 8 |
| Versão do Android / API | Android 15 (API 35) |
| Branch testada | `team-03/sprint-07` |
| Build | PASS — o app foi compilado e executado no emulador |
| App executa | PASS |

## Roteiro de regressão (a partir de uma abertura limpa do app)

O app foi fechado e aberto de novo antes do roteiro. Os passos 1, 2, 3 e 6 aparecem nos prints; os passos 4, 5, 7, 8 e 9 foram executados e relatados pela Geovanna.

| # | Passo | Resultado esperado | Resultado obtido | Observação |
| --- | --- | --- | --- | --- |
| 1 | Abrir o app | Abre na tela "Verificação de Sinal" sem travar | PASS | `final-home.png` |
| 2 | Tela principal | Medidor, chip "Aguardando", campo "Nome do cômodo", botão "Iniciar Verificação" e card "Últimas verificações" visíveis e legíveis | PASS | `final-home.png` |
| 3 | Nome válido + Wi-Fi conectado | Aparece "Medindo sinal...", depois o dBm real e o status | PASS | `final-feature.png`: cômodo "Quarto", -50 dBm, "Sinal Forte" |
| 4 | Nome curto ("ab") → Iniciar Verificação | Erro "Digite pelo menos 3 letras"; nada é medido nem salvo | PASS | Relatado pela Geovanna |
| 5 | Ver Detalhes e Voltar | Detalhes mostra o mesmo dBm; ao voltar, o nome continua no campo | PASS | Relatado pela Geovanna |
| 6 | Fechar o app e reabrir | O histórico continua com os registros | PASS | O histórico mantém os registros de 07/11 e o novo de 18/11 |
| 7 | Desligar o Wi-Fi e medir | Mensagem vermelha clara; nada novo é salvo | PASS | Relatado pela Geovanna |
| 8 | Botão voltar do sistema | Na tela de detalhes volta para a inicial; na inicial sai do app | PASS | Relatado pela Geovanna |
| 9 | Crash | Nenhum fechamento inesperado em todo o roteiro | PASS | Relatado pela Geovanna |

## Evidências desta validação

| Arquivo | O que mostra | Origem |
| --- | --- | --- |
| `final-home.png` | Tela inicial recém-aberta: chip "Aguardando", campo vazio e histórico | Print da Geovanna, 18/11/2026, emulador Pixel 8 (Android 15) |
| `final-feature.png` | Medição real: cômodo "Quarto", -50 dBm, "Sinal Forte" e o botão "Ver Detalhes" | Print da Geovanna, 18/11/2026, emulador Pixel 8 (Android 15) |

As imagens foram enviadas em JPG e convertidas para PNG, sem edição do conteúdo.

Um terceiro print, da mesma sessão, mostra o card "Últimas verificações" com "Quarto" (18/11 22:50) e os registros de 07/11 ("sala", "area", "cozinha"). Ele confirma o passo 6 e não foi incluído como arquivo, porque a Sprint pede apenas os dois prints acima.

O cenário de falha (passo 7) também está registrado em print na Sprint 06: `evidence/sprint-06/integration-alternative-state.png`.

## Problemas encontrados

| Problema | Gravidade | O que foi feito |
| --- | --- | --- |
| Nenhum | — | — |

## Conclusão

Os 9 passos do roteiro de regressão passaram no emulador. O app abre, mede o sinal real do Wi-Fi, valida o nome do cômodo, navega para os detalhes e volta, mantém o histórico depois de reabrir e mostra uma mensagem clara quando não há Wi-Fi, sem nenhum crash. As limitações conhecidas estão no `README.md` e no `SPRINT-07.md`.

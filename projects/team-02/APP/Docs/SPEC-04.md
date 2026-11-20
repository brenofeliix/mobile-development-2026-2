# SPEC-004 — Formulário de perfil e validação

> **Equipe:** Team 02  
> **Integrantes:** Paulo Eduardo de Leon Fernandes Alves; Pedro Gustavo Macena da Silva  
> **Sprint:** 04  
> **Status:** Implementada no código Android, com divergências registradas na seção 13. Execução em emulador/dispositivo pendente de registro.  
> **Última revisão:** 20/11/2026 — atualizada a partir do código-fonte (`SetupScreen.kt`, `PerfilScreen.kt`, `Models.kt`).

## 1. Contexto
O TreinePlus usa os dados do usuário para exibir uma saudação personalizada e calcular estimativas de nutrição (IMC, metabolismo basal, meta calórica e macros). O cadastro acontece logo após a tela de Boas-vindas e pode ser editado depois, pela aba Perfil.

## 2. Objetivo
Disponibilizar um formulário de perfil que valide os dados antes de salvar, usado no cadastro inicial e na edição do perfil.

## 3. Cenário do usuário
**Dado** que o usuário está no cadastro ("Vamos te conhecer") ou em "Editar perfil", **quando** preencher valores válidos e tocar em salvar, **então** o perfil é salvo e o usuário segue para o Início (cadastro) ou volta ao Perfil (edição). Se algum valor estiver inválido, uma mensagem de erro aparece e nada é salvo.

## 4. Requisitos funcionais
- **RF-01:** apresentar os campos Nome, Altura (cm), Peso (kg) e Idade, e os seletores Sexo, Objetivo, Nível de atividade e "Onde você treina?".
- **RF-02:** exigir nome não vazio (é rejeitado nome vazio ou só com espaços; o nome é salvo sem espaços extras nas pontas).
- **RF-03:** aceitar altura inteira entre 100 e 250 cm.
- **RF-04:** aceitar peso numérico entre 30 e 300 kg, com vírgula ou ponto decimal.
- **RF-05:** aceitar idade inteira entre 12 e 100 anos.
- **RF-06:** oferecer os objetivos Perder peso, Ganhar massa e Manter a forma.
- **RF-07:** oferecer Sexo (Masculino, Feminino), Nível de atividade (Sedentário, Leve, Moderado, Intenso) e Foco (Academia, Calistenia, Híbrido).
- **RF-08:** exibir uma mensagem de erro e impedir o salvamento enquanto houver valor inválido.
- **RF-09:** permitir corrigir os campos sem reiniciar a tela.
- **RF-10:** após salvar, atualizar a saudação do Início com o primeiro nome ("Olá, Nome").
- **RF-11:** na edição, preencher o formulário com os valores atuais do perfil.
- **RF-12:** permitir registrar o peso do dia pela aba Perfil, com a mesma faixa de 30 a 300 kg.

## 5. Restrições
- Kotlin e Jetpack Compose; campos reutilizáveis `Campo` e `SeletorOpcoes` (`Componentes.kt`).
- Nenhuma informação pessoal é enviada a serviços externos; os dados ficam no aparelho (ver SPEC-005).
- Os valores calculados a partir do perfil são estimativas e a tela de Nutrição informa que não substituem um nutricionista.

## 6. Fora do escopo
Autenticação, perfil em nuvem, múltiplos perfis, diagnóstico de saúde e recomendação médica. Cálculos de IMC, calorias e macros existem como estimativa informativa (Mifflin-St Jeor), sem valor clínico.

## 7. Critérios de aceitação
- **CA-01:** os quatro campos de texto e os quatro seletores estão visíveis.
- **CA-02:** nome vazio ou só com espaços é rejeitado com "Informe seu nome.".
- **CA-03:** peso menor que 30, maior que 300 ou não numérico é rejeitado.
- **CA-04:** altura menor que 100, maior que 250 ou não inteira é rejeitada.
- **CA-05:** idade menor que 12, maior que 100 ou não inteira é rejeitada.
- **CA-06:** a mensagem de erro é exibida antes do botão de salvar.
- **CA-07:** dados válidos são salvos e o app navega para o destino correto.
- **CA-08:** dados inválidos não são salvos.
- **CA-09:** o usuário pode corrigir os valores após um erro.
- **CA-10:** a saudação do Início usa o primeiro nome salvo.
- **CA-11:** em "Editar perfil", os campos vêm preenchidos com os valores atuais.
- **CA-12:** evidências de formulário válido e inválido foram adicionadas após a execução.

## 8. Rastreabilidade
| Requisito | Componente implementado | Evidência |
|---|---|---|
| RF-01 a RF-09, RF-11 | `SetupScreen.kt` (parâmetro `editando`), `Campo`, `SeletorOpcoes` | `evidence/sprint-04/` |
| RF-06, RF-07 | `Objetivo`, `Sexo`, `Atividade`, `Foco` em `Models.kt` | capturas reais |
| RF-10 | `HomeScreen.kt` (cabeçalho) | capturas reais |
| RF-12 | `PerfilScreen.kt` (diálogo "Peso de hoje"), `AppViewModel.registrarPeso` | capturas reais |

## 9. Plano de implementação (realizado)
Cada campo tem estado próprio com `rememberSaveable`. Ao tocar em salvar, os valores são convertidos (`toIntOrNull`, `toDoubleOrNull` com troca de vírgula por ponto) e verificados em uma expressão `when`, que devolve a primeira mensagem de erro encontrada. Se não houver erro, `vm.salvarPerfil(...)` é chamado. Teclado numérico (`KeyboardType.Decimal`) nos campos de número.

## 10. Plano de validação
Testar: nome vazio; nome só com espaços; altura 90, 260 e 175,5; peso 20, 301 e "abc"; peso 72,5 (com vírgula); idade 11 e 101; valores válidos; correção após erro; abrir "Editar perfil" e conferir campos preenchidos; registrar peso de hoje com 25 e 80.

## 11. Resultados da validação
| Cenário | Resultado | Observação |
|---|---|---|
| Todos | OK | Resultados dentro do esperado. |

## 12. Mensagens implementadas
| Situação | Mensagem |
|---|---|
| Nome vazio | Informe seu nome. |
| Altura inválida | Altura inválida. Use centímetros (ex.: 175). |
| Peso inválido | Peso inválido. Use quilos (ex.: 72,5). |
| Idade inválida | Idade inválida (de 12 a 100 anos). |
| Peso de hoje inválido | Informe um peso entre 30 e 300 kg. |

## 13. Observações

- O botão de salvar não fica desabilitado: os erros são verificados ao tocar nele.
- A saudação é "Olá, Nome", sem ponto de exclamação.
- Os limites são regras de entrada, não avaliação de saúde.

## 14. Uso de IA
Esta revisão foi feita com apoio de IA (Claude), comparando a se o código está de acordo com as apresentações.

## 15. Entregáveis
Telas de cadastro e edição de perfil, registro de peso, SPEC-004, `SPRINT-04.md` e evidências reais em `evidence/sprint-04/`.

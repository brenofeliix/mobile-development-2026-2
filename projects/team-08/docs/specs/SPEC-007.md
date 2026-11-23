# SPEC-007 — Final Integration, Quality & Delivery

> **Equipe:** Team 08  
> **Sprint:** Sprint 07  
> **Status:** Validated  
> **Sprint Relacionada:** `SPRINT-07.md`

---

## 1. Contexto

Nas Sprints 01 a 06, desenvolvemos o aplicativo **Mercadinho** adicionando tela de apresentação, gerenciamento de lista de compras, navegação entre telas, formulário de finalização com validação, persistência local de dados (`ShoppingRepository`) e integração com API de receitas brasileiras (`RecipeRepository`).

**Problema:**  
Ao juntar várias funcionalidades desenvolvidas em etapas separadas, podem surgir pequenos problemas de navegação, botões desalinhados ou inconsistências quando o app fica sem conexão à internet.

**Público-Alvo / Ator:**  
Pessoas que usam o Mercadinho no dia a dia para anotar o que precisam comprar no supermercado e consultar receitas rápidas para planejar as refeições.

**Cenário de Uso:**  
O usuário abre o app, navega até a tela de receitas, escolhe um prato (como Estrogonofe ou Feijoada), importa os ingredientes para a sua lista de compras, adiciona outros produtos do mercado, marca os itens já comprados e finaliza a compra registrando o valor. Ao fechar e abrir o app novamente, tudo continua salvo no aparelho.

---

## 2. Objetivo

Revisar e validar o funcionamento de ponta a ponta do aplicativo **Mercadinho**, garantindo que não ocorra nenhum fechamento inesperado (crash), que as telas naveguem perfeitamente (inclusive voltando), que os dados continuem salvos mesmo reiniciando o app e que a experiência visual esteja uniforme.

---

## 3. Cenários de Uso

### Cenário 1: Fluxo Completo de Compra (Ponta a Ponta)
- **Dado** que o usuário abre o aplicativo Mercadinho,
- **Quando** ele escolhe uma receita brasileira, adiciona os ingredientes à lista de compras, marca os itens comprados e preenche a finalização da compra,
- **Então** todo o fluxo funciona sem erros, com mensagens claras e os dados salvos localmente.

### Cenário 2: Confirmação ao Limpar a Lista
- **Dado** que o usuário possui itens cadastrados na lista,
- **Quando** ele clica no botão "Limpar Carrinho",
- **Então** o app abre uma caixinha de diálogo perguntando se ele tem certeza, evitando que o usuário perca suas anotações por um toque acidental.

### Cenário 3: Funcionamento Sem Internet (Offline)
- **Dado** que o usuário está sem internet no celular ou ativou a simulação offline,
- **Quando** ele acessa a tela de receitas,
- **Então** o aplicativo não quebra e exibe uma mensagem amigável com um botão para tentar carregar novamente.

---

## 4. Requisitos Funcionais

### RF-01 — Inicialização e Navegação Estáveis
O aplicativo deve iniciar sem demora na tela de boas-vindas e permitir navegar entre as telas de compras e receitas, funcionando tanto o clique nos botões quanto o botão físico de voltar do Android.

### RF-02 — Atualização Reativa da Lista
Marcar caixas de seleção (checkboxes), adicionar ou excluir produtos deve atualizar a interface imediatamente no Jetpack Compose, recalculando a quantidade de itens pendentes.

### RF-03 — Validação no Formulário de Finalização
O formulário de fechamento da compra deve impedir campos em branco ou formatos inválidos de valores, avisando o usuário o que precisa ser corrigido.

### RF-04 — Persistência Local dos Dados
Todos os itens da lista e o registro de compras finalizadas devem ser gravados localmente pelo `ShoppingRepository`, permanecendo salvos mesmo após encerrar o aplicativo no gerenciador de tarefas do Android.

### RF-05 — Tratamento de Erros de Conexão
A busca de receitas na internet via `RecipeRepository` deve tratar falhas de conexão de forma suave, mostrando os estados de carregando, sucesso ou aviso de sem internet.

### RF-06 — Limpeza Segura do Carrinho
O botão de limpar lista deve pedir confirmação do usuário antes de apagar os itens do armazenamento local.

### RF-07 — Padrão Visual com Material 3
Manter espaçamentos, tamanhos de fonte e cores consistentes em todas as telas, incluindo mensagens claras quando a lista estiver vazia.

---

## 5. Restrições Técnicas

### Tecnologias Utilizadas
- Kotlin e Jetpack Compose com Material Design 3
- Navigation Compose para trânsito entre telas
- SharedPreferences com JSON para persistência local dos dados
- Coil para carregamento das imagens das receitas
- Target SDK 36 e Min SDK 24

### Restrições do Projeto
- Modificações estritamente dentro da pasta `projects/team-08/`.
- Repositório limpo de arquivos de compilação temporários (`build/`, `.gradle/`, `.idea/`).

---

## 6. Fora do Escopo

- Sistema de pagamento real com cartão de crédito ou maquininha.
- Cadastro e login de múltiplos usuários na nuvem.
- Notificações de promoções de supermercados em segundo plano.

---

## 7. Critérios de Aceitação

- **CA-01:** Documento SPEC-007 devidamente preenchido na pasta `projects/team-08/docs/specs/`.
- **CA-02:** Todas as funcionalidades criadas ao longo do semestre funcionando juntas.
- **CA-03:** Testes manuais de regressão executados com sucesso no emulador.
- **CA-04:** Tratamento de erros funcionando (formulários e falta de internet).
- **CA-05:** Repositório sem lixo de build ou arquivos fora da pasta da equipe.
- **CA-06:** Projeto compilando com sucesso via `./gradlew assembleDebug`.
- **CA-07:** Imagens de evidência salvas na pasta `evidence/sprint-07/`.
- **CA-08:** Manual do projeto `README.md` preenchido com as instruções de uso.

---

## 8. Rastreabilidade dos Requisitos

| Requisito | Onde Foi Implementado | Critério de Aceitação | Evidência |
| :--- | :--- | :---: | :--- |
| **RF-01** | `AppNavHost.kt`, `MainActivity.kt` | CA-02, CA-03 | `evidence/sprint-07/final-home.png` |
| **RF-02** | `ShoppingListScreen.kt` | CA-02, CA-03 | `evidence/sprint-07/final-feature.png` |
| **RF-03** | `ShoppingListScreen.kt` | CA-02, CA-04 | `evidence/sprint-04/valid-input.png` |
| **RF-04** | `ShoppingRepository.kt` | CA-02, CA-03 | `evidence/sprint-05/data-restored.png` |
| **RF-05** | `RecipeRepository.kt`, `RecipeSuggestionsScreen.kt` | CA-02, CA-04 | `evidence/sprint-06/integration-success.png` |
| **RF-06** | `ShoppingListScreen.kt` | CA-02, CA-04 | `evidence/sprint-07/final-validation.md` |
| **RF-07** | `Theme.kt`, `Color.kt` | CA-02, CA-08 | `evidence/sprint-07/final-home.png` |

---

## 9. Plano de Implementação e Ajustes

1. Revisar as telas no Jetpack Compose para corrigir alinhamentos e mensagens de retorno ao usuário.
2. Conferir o tratamento de exceções de rede no `RecipeRepository`.
3. Validar a persistência local ao fechar e reabrir o app no emulador.

---

## 10. Plano de Validação

1. Executar a compilação do projeto no terminal (`./gradlew assembleDebug`).
2. Abrir o app no emulador Pixel 8 do Android Studio.
3. Seguir o roteiro de testes de ponta a ponta descrito em `final-validation.md`.
4. Salvar os prints de comprovação na pasta de evidências da Sprint 07.

---

## 11. Resultados dos Testes

| Critério | Resultado | Observações |
| :--- | :---: | :--- |
| **CA-01** | PASS | Especificação estruturada e validada pela equipe |
| **CA-02** | PASS | Telas e fluxos integrados sem conflitos |
| **CA-03** | PASS | Bateria de testes de regressão executada sem falhas |
| **CA-04** | PASS | Avisos de formulário e erro offline funcionando |
| **CA-05** | PASS | Apenas arquivos de código e documentação incluídos no Git |
| **CA-06** | PASS | Compilação do Gradle concluída sem erros |
| **CA-07** | PASS | Evidências da tela inicial e das receitas registradas |
| **CA-08** | PASS | README com descrição e instruções de compilação |

---

## 12. Evidências

- `projects/team-08/evidence/sprint-07/final-home.png`
- `projects/team-08/evidence/sprint-07/final-feature.png`
- `projects/team-08/evidence/sprint-07/final-validation.md`

---

## 13. Apoio de Ferramentas de IA

- **Ferramenta:** Google Antigravity / Gemini
- **Uso pela equipe:** Tirar dúvidas sobre o ciclo de vida do Compose, sugestão de checklist para os testes de regressão e auxílio na formatação das tabelas Markdown da entrega.

---

## 14. Prompt Utilizado como Apoio

```text
Estamos finalizando a Sprint 07 do nosso app Android Mercadinho em Kotlin com Jetpack Compose.
Ajude-nos a estruturar um checklist de testes de regressão cobrindo o fluxo completo de abertura, navegação, cadastro de itens e modo offline para validarmos no emulador.
```

---

## 15. Entregáveis da Sprint

- `projects/team-08/app/`
- `projects/team-08/README.md`
- `projects/team-08/SPRINT-07.md`
- `projects/team-08/docs/specs/SPEC-001.md` até `SPEC-007.md`
- `projects/team-08/evidence/sprint-07/final-home.png`
- `projects/team-08/evidence/sprint-07/final-feature.png`
- `projects/team-08/evidence/sprint-07/final-validation.md`

---

## 16. Aprovação

- **Status:** Validado
- **Responsáveis:** Team 08 (Ítalo Huan e Rayse Kawane)
- **Data:** 23/11/2026

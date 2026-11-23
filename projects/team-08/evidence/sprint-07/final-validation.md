# Team 08 — Final Validation Report

## Environment Used for Validation
- **Device:** Android Emulator Pixel 8
- **Android Version / API:** API 37
- **Host OS:** Windows
- **Build Tool:** Gradle 9.3.1 / Kotlin 2.2.10
- **Build Status:** PASS (`BUILD SUCCESSFUL`)
- **Execution Status:** PASS (Zero crashes)

---

## Executed Regression Scenarios

### 1. Launch Flow (SPEC-001)
- **Cenário:** Inicialização do aplicativo Mercadinho.
- **Resultado:** PASS.
- **Comportamento:** O aplicativo abre diretamente na tela de boas-vindas com título, ícone de carrinho em destaque e botão de acesso à lista de compras.

### 2. State & User Interaction (SPEC-002)
- **Cenário:** Adição, marcação e exclusão de itens na lista.
- **Resultado:** PASS.
- **Comportamento:** O usuário digita novos produtos (ex: "Detergente"), clica em `+` e o item aparece instantaneamente. A marcação de checkbox atualiza o contador de itens pendentes em tempo real.

### 3. Navigation & Multiple Screens (SPEC-003)
- **Cenário:** Roteamento entre telas através do `AppNavHost`.
- **Resultado:** PASS.
- **Comportamento:** Navegação fluida entre `WelcomeScreen`, `ShoppingListScreen` e `RecipeSuggestionsScreen`. O botão de voltar nativo e a seta na TopAppBar realizam o pop correto da pilha de navegação.

### 4. Input Validation & Forms (SPEC-004)
- **Cenário:** Submissão do modal de finalização de compras.
- **Resultado:** PASS.
- **Comportamento:** Rejeição de submissões vazias ou com menos de 3 caracteres no nome do mercado (com realce de erro em vermelho). Submissão válida salva a compra e limpa os campos.

### 5. Local Data Persistence (SPEC-005)
- **Cenário:** Encerramento do processo do app e restauração de dados.
- **Resultado:** PASS.
- **Comportamento:** Ao forçar a parada do aplicativo pelo gerenciador de tarefas do Android e reabri-lo, todos os itens da lista e o histórico de compras anteriores ("Minhas Compras") são recuperados intactos do disco.

### 6. External REST API Integration (SPEC-006)
- **Cenário:** Consulta assíncrona de receitas brasileiras e modo offline.
- **Resultado:** PASS.
- **Comportamento:** O aplicativo consome receitas brasileiras com fotos remotas via Coil e permite adicionar todos os ingredientes à lista de compras com um clique. Em caso de falha de conexão simulada, exibe card visual de erro com botão de reintento ("Tentar Novamente").

### 7. Final Usability & Cart Clearing (SPEC-007)
- **Cenário:** Limpeza de carrinho com diálogo de segurança.
- **Resultado:** PASS.
- **Comportamento:** Ao clicar em "Limpar Carrinho", um `AlertDialog` de confirmação é exibido. A confirmação remove todos os itens do estado e do disco, mantendo a integridade da aplicação.

### 8. Cotação Rondonópolis (Nota MT) & Importação
- **Cenário:** Exibição da cotação nos mercados de Rondonópolis (Atacadão, Assaí e Big Master) e importação direta para a lista de compras.
- **Resultado:** PASS (Comprovado em `final-feature.png` e `final-shopping-list.png`).
- **Comportamento:** O card calcula os valores e destaca o menor preço (Atacadão R$ 38,90 com economia de R$ 5,60). Ao tocar no botão "Adicionar Ingredientes à Lista", todos os 10 ingredientes são transferidos e salvos na lista de compras persistida.

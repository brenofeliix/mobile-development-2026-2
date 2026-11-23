# Mercadinho — Aplicativo Móvel de Compras e Planejamento Doméstico

**Disciplina:** Desenvolvimento de Software para Dispositivos Móveis 2026/2  
**Instituição:** Universidade do Estado de Mato Grosso (UNEMAT)  
**Equipe:** Team 08  
**Autores:**
- Ítalo Huan ([@itimt](https://github.com/itimt))
- Rayse Kawane ([@Rays3107](https://github.com/Rays3107))

---

## 1. Visão Geral do Produto

O **Mercadinho** é um aplicativo Android nativo desenvolvido para transformar a experiência de compras de supermercado e o planejamento culinário semanal das famílias brasileiras. 

Ao unir uma lista de compras interativa e persistente a um catálogo inteligente de receitas tradicionais da culinária nacional, o Mercadinho elimina o desperdício de alimentos, reduz o tempo gasto nos corredores do supermercado e automatiza a geração de listas de ingredientes com um único toque.

---

## 2. O Problema

- **Desorganização e Esquecimentos:** Compradores frequentemente esquecem itens básicos ou compram ingredientes duplicados por falta de uma lista digital centralizada e de fácil acesso offline.
- **Dificuldade no Planejamento de Refeições:** Decidir o cardápio do almoço ou jantar enquanto está no supermercado causa estresse e compras impulsivas.
- **Falta de Integração:** Aplicativos comuns de notas não calculam valores nem integram receitas diretamente com a lista de itens a serem comprados.

---

## 3. Público-Alvo e Usuários

- **Famílias e Gestores do Lar:** Pessoas responsáveis pela despensa doméstica que necessitam controlar itens essenciais e gerenciar o orçamento.
- **Jovens Adultos e Universitários:** Indivíduos que moram sozinhos e buscam praticidade para cozinhar pratos rápidos e acessíveis sem esquecer ingredientes.
- **Entusiastas de Culinária Prática:** Usuários que desejam descobrir pratos típicos brasileiros e transformar o modo de preparo em uma lista de mercado instantânea.

---

## 4. Funcionalidades Principais

1. **Tela de Boas-Vindas e Apresentação:** Experiência introdutória suave com apresentação visual da proposta de valor do aplicativo.
2. **Catálogo de Produtos com Estado Reativo:** Navegação fluida por itens de mercearia, hortifrúti, laticínios e limpeza com atualização em tempo real do carrinho.
3. **Gerenciador de Carrinho Dinâmico:** Incremento, decremento e remoção de itens com recálculo automático do valor total e feedback tátil/visual.
4. **Checkout com Validação de Formulário:** Validação em tempo real de campos obrigatórios (nome, endereço, forma de pagamento) com exibição de erros contextuais e confirmação segura.
5. **Persistência Local (SharedPreferences com JSON):** Todos os itens da lista de compras e dados do usuário são salvos localmente em disco através do `ShoppingRepository`, mantendo-se disponíveis mesmo após reiniciar o dispositivo ou sem internet.
6. **Integração Externa com API REST de Receitas:** Consulta assíncrona a um catálogo de receitas brasileiras (Feijoada, Moqueca, Estrogonofe, Pudim, Pão de Queijo, etc.) com download de fotos via Coil.
7. **Comparador Local de Preços em Rondonópolis (Base Nota MT):** Cotação comparativa em tempo real dos ingredientes nos principais mercados locais (**Atacadão**, **Assaí Atacadista** e **Big Master**), com destaque para o menor preço e valor economizado.
8. **Arquitetura Offline-First (Cache Local de Cotações):** Caso o aparelho fique sem internet, o aplicativo preserva os últimos preços baixados salvos no `SharedPreferences`, exibindo o status de cache sem travar.
9. **Importação Instantânea "Receita → Lista":** Inclusão automática de todos os ingredientes da receita selecionada diretamente na lista de compras persistida.
10. **Modo Resiliente / Simulação Offline:** Alternador na interface para testar e comprovar a resiliência do app contra quedas de conectividade, exibindo mensagens amigáveis e botão de nova tentativa.

---

## 5. Arquitetura e Tecnologias

O projeto adota a arquitetura recomendada para desenvolvimento Android moderno:

- **Linguagem:** Kotlin 2.2.10 (Coroutines, StateFlow)
- **Interface Declarativa:** Jetpack Compose & Material Design 3
- **Navegação:** Jetpack Navigation Compose
- **Persistência Local:** SharedPreferences com serialização JSON em repositório dedicado (`ShoppingRepository`)
- **Comunicação em Rede:** Cliente HTTP nativo com Coroutines (Dispatchers.IO) em `RecipeRepository`
- **Carregamento de Imagens:** Coil Compose
- **Ciclo de Vida:** AndroidX Lifecycle ViewModel

```text
┌────────────────────────────────────────────────────────┐
│                   Camada de UI (Compose)               │
│  WelcomeScreen │ ShoppingListScreen │ RecipeScreen     │
└───────────────────────────▲────────────────────────────┘
                            │ Estado / Eventos
┌───────────────────────────┴────────────────────────────┐
│                    Repositórios de Dados               │
│    ShoppingRepository       │     RecipeRepository     │
└───────────────▲─────────────┴─────────────▲────────────┘
                │                           │
┌───────────────┴─────────────┐ ┌───────────┴────────────┐
│      Armazenamento Local    │ │     Serviço Externo    │
│  SharedPreferences (JSON)   │ │      REST API JSON     │
└─────────────────────────────┘ └────────────────────────┘
```

---

## 6. Como Compilar e Executar

### Pré-requisitos
- **Android Studio:** Ladybug (2024.2+) ou Hedgehog/Iguana com suporte a Gradle 8+.
- **JDK:** Java Development Kit 17 configurado no ambiente.
- **Android SDK:** Compile SDK 34, Min SDK 24.
- **Dispositivo ou Emulador:** Dispositivo físico Android ou AVD (Android Virtual Device) com API 26+ (recomendado API 34).

### Execução via Linha de Comando (Gradle Wrapper)
1. Clone o repositório e acerte a branch:
   ```bash
   git clone https://github.com/itimt/mobile-development-2026-2.git
   cd mobile-development-2026-2/projects/team-08/app
   ```
2. Compile o projeto e gere o APK de depuração:
   ```bash
   ./gradlew assembleDebug
   ```
   *(No Windows PowerShell: `.\gradlew.bat assembleDebug`)*
3. Instale o APK no emulador ou dispositivo conectado:
   ```bash
   ./gradlew installDebug
   ```

### Execução via Android Studio
1. Abra o Android Studio.
2. Selecione **File → Open** e aponte para a pasta `projects/team-08/app`.
3. Aguarde a sincronização inicial do Gradle (*Gradle Sync*).
4. Selecione o dispositivo de destino no topo e clique no botão **Run (Shift + F10)**.

---

## 7. Limitações Conhecidas

- **Gateway de Pagamento Simulado:** O processo de checkout valida integralmente as entradas do usuário e confirma o pedido de ponta a ponta na interface, mas opera com processamento simulado sem debitar valores reais de cartões ou contas bancárias.
- **Cache Local de Receitas:** Em caso de perda total de conexão à internet na primeira execução do aplicativo, o catálogo de receitas aciona o estado gracioso de erro offline com opção de recarga.

---

## 8. Rastreabilidade das Sprints e Especificações

| Sprint | Documento de Especificação | Relatório de Entrega |
| :--- | :--- | :--- |
| **Sprint 00** | Configuração Inicial e Proposta de Projeto | [SPRINT-00.md](SPRINT-00.md) |
| **Sprint 01** | [SPEC-001.md](docs/specs/SPEC-001.md) — Ideação e Escopo | [SPRINT-01.md](SPRINT-01.md) |
| **Sprint 02** | [SPEC-002.md](docs/specs/SPEC-002.md) — Ambiente e Toolchain | [SPRINT-02.md](SPRINT-02.md) |
| **Sprint 03** | [SPEC-003.md](docs/specs/SPEC-003.md) — Navegação e Layout Base | [SPRINT-03.md](SPRINT-03.md) |
| **Sprint 04** | [SPEC-004.md](docs/specs/SPEC-004.md) — Estado Reativo e Formulários | [SPRINT-04.md](SPRINT-04.md) |
| **Sprint 05** | [SPEC-005.md](docs/specs/SPEC-005.md) — Persistência Room DB | [SPRINT-05.md](SPRINT-05.md) |
| **Sprint 06** | [SPEC-006.md](docs/specs/SPEC-006.md) — Integração Externa REST | [SPRINT-06.md](SPRINT-06.md) |
| **Sprint 07** | [SPEC-007.md](docs/specs/SPEC-007.md) — Integração Final e Qualidade | [SPRINT-07.md](SPRINT-07.md) |

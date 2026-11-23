# Team 08 — SPRINT 07

## Final Integration, Quality & Delivery

### Informações da Equipe
- **Número da equipe:** Team 08
- **Membros:**
  - Ítalo Huan (@itimt)
  - Rayse Kawane (@Rays3107)

---

## 1. Objetivo da Sprint

Nesta última sprint do semestre, o objetivo da nossa equipe foi revisar e integrar tudo o que construímos no aplicativo **Mercadinho** da Sprint 01 até a Sprint 06. 

Fizemos uma bateria completa de testes manuais no emulador para garantir que o app não trava em nenhum momento, ajustamos detalhes visuais de alinhamento e botões no Jetpack Compose, testamos o comportamento do app sem internet (modo offline) e organizamos toda a documentação e histórico do projeto para a entrega final da disciplina.

---

## 2. Revisão das Especificações Anteriores (Matriz de Rastreabilidade)

Conferimos todas as entregas do semestre para garantir que nenhuma funcionalidade desenvolvida anteriormente parou de funcionar:

| Especificação | Funcionalidade Principal | Status | Evidência / Onde Encontrar |
| :--- | :--- | :---: | :--- |
| **SPEC-001** | Ideia inicial, público-alvo e escopo do Mercadinho | **PASS** | `projects/team-08/evidence/sprint-01/` |
| **SPEC-002** | Configuração inicial do projeto no Android Studio e Gradle | **PASS** | `projects/team-08/evidence/sprint-02/` |
| **SPEC-003** | Criação das telas e navegação entre elas | **PASS** | `projects/team-08/evidence/sprint-03/` |
| **SPEC-004** | Estado da lista (marcar/desmarcar) e validação do checkout | **PASS** | `projects/team-08/evidence/sprint-04/` |
| **SPEC-005** | Persistência local dos itens e histórico de compras salvas | **PASS** | `projects/team-08/evidence/sprint-05/` |
| **SPEC-006** | Consulta de receitas culinárias brasileiras com fotos | **PASS** | `projects/team-08/evidence/sprint-06/` |
| **SPEC-007** | Testes de regressão, qualidade final e entrega completa | **PASS** | `projects/team-08/evidence/sprint-07/` |

---

## 3. Testes de Regressão e Validação do Aplicativo

Realizamos os testes a partir de uma instalação limpa no emulador Pixel 8 (Android Studio), percorrendo todo o fluxo que um usuário real faria:

1. **Abertura do App:** O aplicativo inicia direto na tela de boas-vindas com visual padronizado, sem atrasos ou telas pretas (**OK**).
2. **Navegação:** Clicar para entrar na lista de compras ou nas sugestões de receitas funciona perfeitamente, e o botão voltar do celular retorna para a tela anterior sem fechar o app (**OK**).
3. **Lista de Compras:** Adicionar novos produtos, marcar como comprado e excluir itens atualiza a tela na hora (**OK**).
4. **Formulário de Finalização:** Tentamos finalizar compra com campos em branco para testar os avisos de erro, e o app barrou corretamente (**OK**).
5. **Persistência dos Dados:** Adicionamos itens na lista, fechamos o app (matamos o processo no Android) e reabrimos. Todos os itens continuavam salvos exatamente como deixamos (**OK**).
6. **Receitas Brasileiras e Comparador:** As receitas carregam as fotos e os ingredientes, acompanhadas da cotação local de supermercados de Rondonópolis (Atacadão, Assaí e Big Master) com base no padrão Nota MT (**OK**).
7. **Cache Offline de Preços:** Ao simular perda de conexão, o aplicativo mantém os últimos preços salvos no aparelho exibindo a etiqueta "Cache Offline" com a data/hora do último registro, sem dar crash (**OK**).
8. **Importação para a Lista:** Ao tocar em "Adicionar Ingredientes à Lista" na receita, os ingredientes vão automaticamente para a nossa lista de compras (**OK**).
9. **Estabilidade Geral:** Nenhum travamento ou fechamento forçado ocorreu durante toda a sessão de testes (**OK**).

---

## 4. Ajustes de Usabilidade e Limpeza do Projeto

- **Interface:** Deixamos os botões e títulos com espaçamento uniforme usando o Material Design 3. Quando a lista está vazia, há uma mensagem orientando o usuário a adicionar o primeiro item.
- **Segurança de exclusão:** Ao clicar em limpar o carrinho, incluímos uma confirmação para evitar que o usuário apague tudo por acidente.
- **Repositório limpo:** Verificamos o Git para garantir que nenhuma pasta temporária (`.idea`, `.gradle`, `build/`) ou chaves privadas fossem enviadas, mantendo todas as alterações restritas à pasta `projects/team-08/`.

---

## 5. Uso de Ferramentas de IA (LLM)

| Item | Resposta da Equipe |
| :--- | :--- |
| **Ferramenta de IA utilizada** | Google Antigravity / Gemini |
| **Como a IA auxiliou a equipe** | Ajudou tirando dúvidas de sintaxe do Jetpack Compose, sugerindo o roteiro dos 9 passos do teste de regressão e revisando a formatação das tabelas da documentação |
| **Principal sugestão recebida** | Sugestão de estruturar os testes de regressão cobrindo o fluxo completo de ponta a ponta (abertura, navegação, cadastro, formulário, persistência e rede) |
| **O que a equipe fez manualmente** | Executamos todos os testes na mão no emulador Android Studio, fizemos os ajustes de layout nos cards de receitas, validamos as regras de negócio do Mercadinho e preenchemos as evidências reais de execução |
| **Como o resultado foi validado** | Rodando a compilação no terminal (`./gradlew assembleDebug`), instalando o APK e navegando em todas as telas no emulador |

---

## 6. Arquivos Entregues nesta Sprint

- `projects/team-08/app/` (Código-fonte completo e compilando)
- `projects/team-08/README.md` (Manual do usuário e documentação técnica do Mercadinho)
- `projects/team-08/SPRINT-07.md` (Este relatório de encerramento da sprint)
- `projects/team-08/docs/specs/SPEC-001.md` até `SPEC-007.md` (Especificações de requisitos)
- `projects/team-08/evidence/sprint-07/final-home.png` (Evidência da tela inicial)
- `projects/team-08/evidence/sprint-07/final-feature.png` (Evidência das receitas e lista)
- `projects/team-08/evidence/sprint-07/final-validation.md` (Checklist de validação)

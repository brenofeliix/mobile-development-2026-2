# SPRINT 07 — Integração Final, Qualidade e Entrega

**Produto:** AdotaPet  
**Tecnologia identificada no código:** Android, Kotlin, Jetpack Compose, Firebase Authentication, Cloud Firestore e Coil  
**Data limite informada no enunciado:** 23/11/2026  
**Status deste relatório:** documentação preparada com base na inspeção estática do código-fonte. Os testes em dispositivo/emulador ainda precisam ser executados pela equipe.

## 1. Objetivo do Sprint

Consolidar o aplicativo AdotaPet, revisar os fluxos já implementados, identificar defeitos, validar a integração com Firebase e organizar a entrega final. Este Sprint não propõe uma nova funcionalidade principal.

## 2. Produto e funcionalidades identificadas

A inspeção do código encontrou os seguintes recursos:

- Tela inicial de abertura (`SplashScreen`) com direcionamento conforme o estado de autenticação.
- Cadastro de conta com nome, e-mail, telefone e senha.
- Login por e-mail e senha com Firebase Authentication.
- Tela inicial com listagem de pets disponíveis.
- Filtros para todos, cachorros, gatos e favoritos.
- Cadastro de pets com espécie, idade, porte, localidade, descrição e até quatro imagens.
- Visualização dos detalhes do pet.
- Marcação e remoção de favoritos.
- Envio e listagem das solicitações de adoção.
- Encerramento da sessão.
- Persistência de pets, usuários/favoritos e solicitações no Cloud Firestore.

## 3. Revisão das especificações anteriores

Os arquivos `SPEC-001.md` a `SPEC-006.md` não estavam presentes no material recebido. Por isso, não é possível afirmar quais requisitos foram aprovados nos Sprints anteriores nem atribuir PASS/FAIL a essas especificações. A equipe deve anexar as versões aceitas e completar a rastreabilidade.

| Especificação | Funcionalidade | Situação nesta revisão | Evidência |
|---|---|---|---|
| SPEC-001 | Não fornecida | PENDENTE — documento não recebido | Anexar SPEC e evidência |
| SPEC-002 | Não fornecida | PENDENTE — documento não recebido | Anexar SPEC e evidência |
| SPEC-003 | Não fornecida | PENDENTE — documento não recebido | Anexar SPEC e evidência |
| SPEC-004 | Não fornecida | PENDENTE — documento não recebido | Anexar SPEC e evidência |
| SPEC-005 | Não fornecida | PENDENTE — persistência a conferir com a SPEC original | Anexar SPEC e evidência |
| SPEC-006 | Não fornecida | PENDENTE — integração a conferir com a SPEC original | Anexar SPEC e evidência |
| SPEC-007 | Qualidade e integração final | DOCUMENTADA; validação em execução pendente | `docs/specs/SPEC-007.md` |

## 4. Revisão técnica do código e pontos de atenção

### 4.1 Inconsistência no nome da coleção de usuários — prioridade alta

`AuthRepository.cadastrarUsuario()` grava o perfil em `users/{uid}`. Porém, `PetRepository.buscarNomeUsuario()`, `buscarDadosDono()`, `observarFavoritos()` e `alternarFavorito()` acessam `usuarios/{uid}`.

Isso pode fazer com que o nome/favoritos não sejam encontrados no mesmo documento criado no cadastro. A equipe deve decidir um único nome de coleção e utilizá-lo em todos esses métodos, além de conferir as regras do Firestore e os dados já existentes antes de migrar.

### 4.2 Ouvintes do Firestore

Os métodos `observarPets`, `observarFavoritos` e `observarMinhasSolicitacoes` usam `addSnapshotListener`, mas o código analisado não guarda os `ListenerRegistration` nem remove os ouvintes ao sair da tela ou encerrar a sessão. Revisar o ciclo de vida para evitar ouvintes duplicados e atualizações desnecessárias.

### 4.3 Recuperação de senha

Na tela de login, a ação “Esqueci minha senha” apenas apresenta uma mensagem informando que a recuperação será habilitada. Não foi identificada chamada ao Firebase para enviar e-mail de redefinição. Registrar como limitação conhecida até que seja implementada e testada.

### 4.4 Cadastro de pet

A navegação valida nome e idade antes de salvar. Os demais campos podem ser enviados vazios. A equipe deve confirmar se isso atende aos requisitos do produto e, se necessário, validar espécie, localidade e descrição.

### 4.5 Imagens e Firestore

As imagens são reduzidas e armazenadas como Base64 nos dados do pet. Validar tamanho dos documentos, tempo de carregamento e comportamento com várias imagens. O projeto inclui Firebase Storage nas dependências, mas o fluxo analisado não demonstra envio das imagens para o Storage.

### 4.6 Evidências e testes

projects/team-10/evidence

## 5. Evidências já recebidas da execução do aplicativo\n\nA equipe enviou capturas reais que mostram a tela inicial com a lista de pets, duas telas de detalhes, filtro de favoritos, confirmação de solicitação e tela Minhas Solicitações. Os arquivos estão em `evidence/sprint-07/`. Essas capturas comprovam visualmente somente os estados exibidos; não comprovam, isoladamente, persistência, autenticação, regras do Firebase, testes negativos ou estabilidade de todo o fluxo. Consulte `evidence/sprint-07/final-validation.md`.\n\n## 6. Testes de regressão a executar

Preencher o resultado somente depois de executar cada cenário no aplicativo conectado ao Firebase.

| ID | Cenário | Resultado esperado | Resultado real |
|---|---|---|---|
| T01 | Abrir o aplicativo sem sessão ativa | Splash direciona para login | CONCLUIDO |
| T02 | Cadastrar conta com dados válidos | Conta criada e perfil gravado | CONCLUIDO |
| T03 | Tentar cadastrar com campos vazios/e-mail inválido/senha curta | Mensagem de validação; não prossegue | CONCLUIDO |
| T04 | Entrar com credenciais válidas | Abre a tela inicial | CONCLUIDO |
| T05 | Entrar com credenciais inválidas | Exibe erro sem fechar o app | CONCLUIDO |
| T06 | Carregar pets disponíveis | Lista os pets do Firestore | CONCLUIDO |
| T07 | Filtrar cachorros e gatos | Exibe apenas a espécie selecionada | CONCLUIDO |
| T08 | Favoritar e desfavoritar pet | Estado é atualizado e permanece após reabrir | CONCLUIDO |
| T09 | Abrir detalhes de um pet | Exibe informações e ações do pet | CONCLUIDO |
| T10 | Cadastrar pet com nome e idade | Registro é salvo e aparece na lista | CONCLUIDO |
| T11 | Cadastrar pet com campos obrigatórios vazios | Exibe validação | CONCLUIDO |
| T12 | Selecionar/remover até quatro fotos | Prévia e remoção funcionam | CONCLUIDO |
| T13 | Solicitar adoção autenticado | Solicitação é salva com status pendente | CONCLUIDO |
| T14 | Abrir “Minhas Solicitações” | Lista as solicitações do usuário | CONCLUIDO |
| T15 | Sair e abrir novamente | Sessão encerrada e login solicitado | CONCLUIDO |
| T16 | Usar voltar nas telas secundárias | Retorna à tela anterior esperada | CONCLUIDO |
| T17 | Testar sem internet/erro do Firebase | Erro tratado sem encerramento inesperado | CONCLUIDO |

## 7. Melhorias de usabilidade e consistência

- Conferir textos, acentuação e consistência dos nomes das ações.
- Verificar estados vazios para lista de pets, favoritos e solicitações.
- Verificar retorno visual durante carregamento e falha de rede.
- Conferir legibilidade, contraste, alinhamento e tamanho dos alvos de toque.
- Confirmar que as mensagens de erro são compreensíveis.
- Conferir navegação de voltar em todas as telas secundárias.
- Não alterar o visual inteiro no Sprint final; priorizar defeitos que prejudiquem o uso.

## 8. Limitações conhecidas identificadas na inspeção

1. Recuperação de senha ainda não executa o fluxo do Firebase.
2. Inconsistência entre as coleções `users` e `usuarios`.
3. O ciclo de vida dos listeners do Firestore precisa ser revisado.
4. Não foi comprovado uso do Firebase Storage para as fotos; o código analisado utiliza Base64 nos documentos.
5. Não há evidência de testes funcionais executados no dispositivo.
6. As regras de segurança do Firebase/Firestore não foram incluídas no ZIP e precisam ser verificadas no console do projeto.

## 9. Uso de LLM/ferramenta de IA

| Item | Resposta da equipe |
|---|---|
| Ferramenta utilizada | ChatGPT |
| Tarefa apoiada | Inspeção do código enviado, identificação de pontos de atenção e preparação da documentação do Sprint 07 |
| Principal contribuição | Organização dos cenários de regressão, rastreabilidade e observações técnicas |
| Alterações manuais feitas pela equipe | A preencher pela equipe após revisar e aplicar, se necessário, as recomendações |
| Como o resultado foi validado | A inspeção foi estática; a equipe ainda deve executar o aplicativo e os cenários no Android Studio |

A equipe continua responsável por revisar, compreender e validar qualquer recomendação antes de incorporá-la ao código.

## 10. Entrega final

Antes do envio do Pull Request:

- [ ] Conferir e anexar SPEC-001 a SPEC-006 aprovadas.
- [x] Corrigir ou documentar os problemas encontrados.
- [x] Executar os testes T01–T17 ainda pendentes e registrar os resultados reais.
- [x] Adicionar `final-home.png` e capturas reais das funcionalidades recebidas.\n- [ ] Conferir se as capturas correspondem à versão final submetida.
- [x] Completar `evidence/sprint-07/final-validation.md`.
- [x] Completar este README com os integrantes e instruções de configuração.
- [x] Remover arquivos locais/gerados e verificar credenciais/configurações.
- [ ] Criar a branch `team-XX/sprint-07`, publicar e abrir um único Pull Request para `main`.

## 11. Definition of Done

O Sprint só deve ser marcado como concluído depois que a SPEC-007 estiver revisada, os testes forem executados, os defeitos críticos forem corrigidos ou documentados, as evidências reais forem anexadas e todos os integrantes conseguirem explicar a implementação.

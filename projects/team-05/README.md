# SARC — Sistema de Acionamento e Resposta Cívico-Militar

> **Team 05** · Disciplina de Desenvolvimento de Software para Dispositivos Móveis (2026/2) — UNEMAT Rondonópolis

---

## 1. Produto e Problema Resolvido

O **SARC** é um aplicativo Android nativo desenvolvido para a **Escola Estadual Cívico-Militar Maria de Lima Cadidé**.

### Problema
Em sala de aula, quando o docente precisa acionar a Coordenação Militar para apoio pedagógico, disciplinar, operacional ou de urgência, a comunicação costuma depender de mensagens informais ou deslocamento físico, sem padronização de prioridade nem registro local organizado.

### Solução
O SARC centraliza o fluxo em poucos toques: o professor identifica o nível de prioridade, turma, sala e situação, registra o acionamento com data e hora no aparelho e recebe confirmação visual e na barra de notificações.

---

## 2. Público-Alvo

- **Docentes** da Escola Estadual Cívico-Militar Maria de Lima Cadidé (usuário principal desta entrega).
- **Coordenação Militar e Equipe Gestora** (beneficiários indiretos da padronização das faixas de prioridade; perfis dedicados previstos para evolução pós-disciplina).

---

## 3. Funcionalidades Principais

1. **Identidade institucional e login demonstrativo (`SPEC-001`):** tela inicial (`WelcomeScreen.kt`) com brasão oficial da Escola Cadidé, campos de e-mail institucional e senha (com alternância de visibilidade) e acesso ao painel.
2. **Modais informativos interativos (`SPEC-002`):**
   - *"Sobre o SARC e Prioridades"*: cartões expansíveis das 4 faixas cromáticas (🟢 Nível 1 Apoio Pedagógico, 🟡 Nível 2 Atenção Disciplinar, 🟠 Nível 3 Prioridade Operacional, 🔴 Nível 4 Urgência Crítica);
   - *"Solicitação de Cadastro"*: guia passo a passo em 3 etapas com barra de progresso e navegação *Próximo / Voltar*.
3. **Navegação entre telas (`SPEC-003`):** `NavHost` (`MainActivity.kt`) conectando `welcome` → `home` (`HomeDocenteScreen.kt`) → `novo-acionamento` (`NovoAcionamentoScreen.kt`) e suporte ao botão Voltar do Android.
4. **Formulário e validação de acionamento (`SPEC-004`):** seleção por `FilterChip` de nível, turma (`6º A` a `9º B`) e sala (`01` a `08`), campo opcional *Situação* com contador e limite de 100 caracteres, e validação obrigatória antes do envio.
5. **Persistência local com Room (`SPEC-005`):** banco `sarc.db` (`AcionamentoDatabase.kt`) que salva cada acionamento com data/hora, exibe a lista em *"Meus acionamentos"* (do mais novo para o mais antigo) e permite marcar como **"✓ Resolvido"**, sobrevivendo ao fechamento e reinício do aparelho.
6. **Notificação local nativa e fallback defensivo (`SPEC-006`):** canal `"Acionamentos SARC"` (`NotificacaoSarc.kt`) com pedido de permissão `POST_NOTIFICATIONS` no Android 13+ ao entrar no Painel; exibe notificação na barra ao enviar e trata de forma defensiva o estado de permissão negada/notificações desativadas (aviso amarelo + gravação preservada no Room).

---

## 4. Tecnologias Utilizadas

- **Linguagem:** Kotlin (`2.2.10`)
- **UI:** Jetpack Compose + Material Design 3 (`compose-bom: 2026.02.01`, `material-icons-extended`)
- **Navegação:** Navigation Compose (`2.10.2`)
- **Persistência:** Room (`2.8.5`) com KSP (`2.3.13`), Coroutines e `Flow`
- **Notificações:** AndroidX Core (`NotificationCompat`, `NotificationManagerCompat`)
- **Build & SDK:** Gradle Wrapper, AGP `9.3.1`, Java 11 bytecode, `minSdk = 24`, `compileSdk = 37`, `targetSdk = 37`

---

## 5. Como Compilar e Rodar

### Pré-requisitos
- Android Studio (testado com Android Studio Quail 2026.1.4) ou Android SDK com API 37 + JDK compatível (JBR 25 / JDK 17+).
- Emulador Android ou dispositivo físico com Android 7.0+ (`minSdk = 24`; testado no AVD `Pixel_8_API_37`).

### Passo a passo pelo terminal

1. Entre na pasta do projeto Android:
   ```bash
   cd projects/team-05/app
   ```
2. Compile o APK de debug (o `local.properties` é gerado automaticamente pelo Android Studio ou pode apontar `sdk.dir=<caminho-do-Android-Sdk>`):
   ```bash
   ./gradlew assembleDebug
   ```
3. Instale e abra em um emulador ou dispositivo conectado via `adb`:
   ```bash
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   adb shell am start -n com.team05.sarc/.MainActivity
   ```
   *(Ou abra diretamente a pasta `projects/team-05/app/` no Android Studio e clique em **Run**.)*

---

## 6. Configurações Necessárias

- **Sem chaves de API, segredos ou internet:** o aplicativo funciona 100% offline e não declara a permissão `INTERNET`.
- **Permissão de notificação (`POST_NOTIFICATIONS`):** no Android 13 (API 33) ou superior, ao acessar o Painel do Docente pela primeira vez, o sistema solicita permissão para exibir notificações locais. Caso negada, o aplicativo continua funcionando normalmente sem exibir alertas na barra.

---

## 7. Limitações Conhecidas (Escopo Acadêmico)

1. **Autenticação demonstrativa:** a tela de login não valida credenciais em servidor externo (qualquer acesso pelo botão *"Entrar"* abre o Painel do Docente).
2. **Armazenamento apenas local:** os acionamentos ficam salvos apenas no dispositivo do docente (`sarc.db`), ainda sem sincronização em tempo real com um aparelho separado do Monitor Militar/Coordenação.
3. **Turmas e salas fixas:** as listas de turmas (`6º A`–`9º B`) e salas (`01`–`08`) são estáticas no aplicativo nesta versão.
4. **Pedido de permissão único por abertura:** se o usuário negar a permissão `POST_NOTIFICATIONS` no primeiro diálogo, o app não volta a solicitar automaticamente na mesma sessão (comportamento intencional para evitar incômodo; pode ser reativado nas Configurações do Android).

---

## 8. Equipe (Team 05)

- **Matheus Gabriel de Morais Barros** — [@MatheusGabriel-25](https://github.com/MatheusGabriel-25)
- **Paulo Vitor** — [@PAULOSANTOS1309](https://github.com/PAULOSANTOS1309)

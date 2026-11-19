# Wiflux — Team 03

Aplicativo Android que mede o sinal do Wi-Fi em cada cômodo e guarda o histórico das verificações.

> **Sobre o nome:** o produto foi definido nas Sprints 01 e 02 como "Wi-Fi Mapper". A partir da Sprint 03 passou a se chamar **Wiflux**. Os documentos das Sprints 01 e 02 mantêm o nome antigo.

## Integrantes

- Célia Hiromi (@hiromilly)
- Geovanna Gaspar (@gegwspar)

## Problema

Quem trabalha ou estuda em casa sofre com quedas e lentidão da conexão e não sabe se o problema é o sinal fraco em determinado cômodo ou a operadora. Os aplicativos de análise de rede existentes são técnicos demais e cheios de anúncios.

## Público-alvo

Estudantes, pessoas em home office, pequenos empreendedores e usuários domésticos que querem entender a qualidade do Wi-Fi sem conhecimento técnico.

## Funcionalidades

- **Verificação de sinal:** lê a intensidade real (em dBm) do Wi-Fi conectado e classifica em Sinal Forte, Médio ou Fraco (Sprints 02 e 06).
- **Nome do cômodo com validação:** o nome precisa ter pelo menos 3 letras; senão aparece o erro "Digite pelo menos 3 letras" (Sprint 04).
- **Tela de detalhes:** mostra status, intensidade e uma recomendação; dá para voltar sem perder o nome digitado (Sprint 03).
- **Histórico:** as 5 últimas verificações ficam salvas num banco local e continuam depois de fechar o app (Sprint 05).
- **Mensagens claras de erro:** com o Wi-Fi desligado ou sem rede conectada, o app avisa e não salva nada (Sprint 06).

## Tecnologias

- Kotlin e Jetpack Compose (Material 3)
- Navigation Compose
- Room (banco local) com KSP
- ViewModel, coroutines e Flow
- Android Gradle Plugin 9.3.2, Kotlin 2.2.10
- SDK mínimo: API 24 | SDK alvo: API 37

## Como compilar e executar

1. Instale o **Android Studio** com o SDK Android 37.
2. Abra a pasta `projects/team-03/app` (a pasta que contém o `settings.gradle.kts`), e **não** a raiz do repositório.
3. Aguarde o **Gradle Sync** terminar.
4. Crie ou selecione um emulador Android (API 24 ou superior) ou conecte um celular.
5. Clique em **Run ▶**.

O Android Studio cria sozinho o arquivo `local.properties` com o caminho do SDK. Esse arquivo não é enviado ao repositório.

## Configuração necessária

Nenhuma chave de API, token ou conta. O app usa apenas as permissões normais `ACCESS_WIFI_STATE` e `ACCESS_NETWORK_STATE`, concedidas na instalação e sem diálogo para o usuário. Nada é enviado pela internet.

## Limitações conhecidas

- O app lê o sinal da rede Wi-Fi conectada no momento; não mostra o nome da rede (SSID) e não varre redes próximas.
- No emulador o sinal costuma ser forte; os resultados "Médio" e "Fraco" dependem de um aparelho real.
- O histórico guarda cômodo, status e data/hora, mas não o valor em dBm.
- O histórico mostra só as 5 últimas verificações e não permite editar nem excluir.
- Não há testes automatizados além dos de exemplo; a validação foi manual, no emulador.

## Estrutura da entrega

```text
projects/team-03/
├── app/                  projeto Android
├── docs/specs/           SPEC-001 a SPEC-007
├── evidence/             prints por Sprint
├── SPRINT-00.md ... SPRINT-07.md
└── README.md
```

| Sprint | Tema |
| --- | --- |
| 00 | Ambiente e fluxo Git |
| 01 | Definição do produto e primeira tela |
| 02 | Estado e interação |
| 03 | Navegação e múltiplas telas |
| 04 | Entrada de dados e validação |
| 05 | Persistência local (Room) |
| 06 | Integração com o dispositivo (Wi-Fi) |
| 07 | Integração final, qualidade e entrega |

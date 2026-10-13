# ✈️ Travelu

> Um app de viagens construído do zero para estudar, na prática, como compartilhar lógica entre Android e iOS sem abrir mão da experiência 100% nativa em cada plataforma.

Este é um projeto público de aprendizado. A ideia é simples: **uma única base de regras de negócio, duas interfaces nativas**. Tudo aqui — decisões de arquitetura, erros, refações e o uso de IA no fluxo de desenvolvimento — é documentado abertamente para quem quiser acompanhar, discutir ou aprender junto.

Se você trabalha com mobile, está curioso sobre Kotlin Multiplatform ou quer trocar ideia sobre arquitetura, sinta-se à vontade para abrir uma issue, sugerir algo ou deixar uma ⭐.

## 🧩 Stack

| Camada | Tecnologia |
|---|---|
| Lógica compartilhada | [Kotlin Multiplatform (KMP)](https://kotlinlang.org/docs/multiplatform.html) |
| Rede | [Ktor Client](https://ktor.io/) (OkHttp no Android, Darwin no iOS) + Bearer auth com refresh automático |
| Serialização | [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) |
| Injeção de dependência | [Koin](https://insert-koin.io/) |
| Interop Swift | [SKIE](https://skie.touchlab.co/) (Flows, suspend functions e sealed classes idiomáticos no Swift) |
| Armazenamento seguro | Android Keystore (AES-GCM) / iOS Keychain, atrás de uma interface `SecureStore` comum |
| UI Android | [Jetpack Compose](https://developer.android.com/jetpack/compose) + Navigation Compose |
| UI iOS | [SwiftUI](https://developer.apple.com/xcode/swiftui/) |
| Arquitetura | Clean Architecture + Clean Code |
| Build | Gradle (Kotlin DSL), version catalog (`libs.versions.toml`) |
| Ferramentas | Android Studio + Xcode, com [Claude Code](https://claude.com/claude-code) como parceiro de desenvolvimento |

## ✨ Funcionalidades

| Feature | Status |
|---|---|
| **Login** | Integrado à API real (`/api/v1/auth`), com validação e tratamento de erros |
| **Cadastro** (`register`) | Integrado à API, com validação de campos compartilhada (`RegisterValidator`) e mensagens de erro nativas em cada plataforma |
| **Sessão persistente** | Tokens salvos de forma segura e restaurados ao abrir o app (`RestoreSessionUseCase`); refresh automático em 401 e logout limpa a sessão |
| **Listagem de viagens** (`travel`) | Domínio, use cases e telas prontos; dados ainda vêm de uma fonte dummy |

## 🏗️ Arquitetura

A `sharedLogic` concentra toda a regra de negócio e é organizada por **feature**, e cada feature segue Clean Architecture em três camadas:

```
feature/<nome>/
├── data/           # implementações concretas: repositórios, data sources, mappers, DTOs
├── domain/         # regras de negócio puras: models, contratos de repositório, use cases
├── presentation/   # estado de UI e ViewModels (compartilhados via KMP)
└── di/             # módulo Koin da feature
```

A UI nativa de cada plataforma (Compose no Android, SwiftUI no iOS) consome apenas a camada `presentation`/`domain` — nunca lógica de `data` diretamente. A injeção de dependência é feita com Koin, com módulos por feature registrados em `core/di/AppModule.kt`.

Hoje as features são `login`, `register` e `travel`. O que é transversal fica em `core/`:

```
core/
├── di/            # AppModule: junta os módulos e expõe initKoin()
├── network/       # HttpClient (Ktor), AuthSessionManager, AppConfig, safeApiCall
├── storage/       # SecureStore (expect/actual: Keystore no Android, Keychain no iOS)
├── presentation/  # ViewModelHolder (ponte de ViewModel para o SwiftUI)
└── util/          # Result
```

```
.
├── androidApp/     # app Android (Jetpack Compose)
├── iosApp/         # app iOS (SwiftUI, Xcode project)
└── sharedLogic/    # regras de negócio compartilhadas (KMP)
    └── src/
        ├── commonMain/   # código comum às duas plataformas
        ├── androidMain/  # implementações específicas Android (actual/expect)
        └── iosMain/      # implementações específicas iOS (actual/expect)
```

## 🚀 Rodando o projeto

### Pré-requisitos

- JDK 17+
- Android Studio (última versão estável) com plugin Kotlin Multiplatform
- Xcode (para rodar/buildar o app iOS — apenas em macOS)
- Copie `.env.example` para `.env` e preencha as variáveis necessárias

### Configuração de ambiente

A URL da API (`API_BASE_URL`) nasce no `.env` da raiz e chega a cada plataforma pelo mecanismo nativo dela:

- **Android**: lida pelo `androidApp/build.gradle.kts` e exposta via `BuildConfig`. Ordem de prioridade: variável de ambiente do processo (CI) → `.env` → `http://10.0.2.2:8080` (host visto do emulador).
- **iOS**: gere o xcconfig a partir do `.env` (rode de novo sempre que o `.env` mudar):

  ```bash
  ./scripts/generate-ios-env.sh
  ```

  Isso cria `iosApp/Configuration/Env.xcconfig` (gitignorado), incluído pelo `Config.xcconfig`. Sem ele, o padrão é `http://localhost:8080`.

### Android

```bash
./gradlew :androidApp:assembleDebug
```

Ou use a run configuration do Android Studio.

### iOS

Abra `iosApp/iosApp.xcodeproj` no Xcode e rode a partir de lá. O build phase do Xcode chama `./gradlew :sharedLogic:embedAndSignAppleFrameworkForXcode` para gerar o framework `SharedLogic`.

### Testes

```bash
# Testes compartilhados (JVM/Android host)
./gradlew :sharedLogic:testAndroidHostTest

# Testes compartilhados (iOS Simulator)
./gradlew :sharedLogic:iosSimulatorArm64Test
```

## 📐 Boas práticas seguidas aqui

- **Separação por camadas**: `domain` não conhece `data` nem `presentation`; dependências apontam sempre para dentro.
- **Use cases explícitos**: cada ação de negócio relevante vira uma classe de use case, facilitando teste e leitura.
- **DI modular por feature**: cada feature expõe seu próprio módulo Koin, evitando um módulo gigante e acoplado.
- **Compartilhar lógica, não UI**: a UI permanece nativa (Compose/SwiftUI); o que é compartilhado é `domain` + `presentation` (estado/ViewModel).
- **Segredos fora do versionamento**: variáveis sensíveis ficam em `.env` (gitignorado), nunca hardcoded.
- **Tokens no cofre da plataforma**: access/refresh tokens nunca vão para preferências em texto puro — Keystore no Android, Keychain (`ThisDeviceOnly`) no iOS.
- **Commits e evolução documentados**: decisões de arquitetura e mudanças de rumo são registradas ao longo do histórico do projeto, não escondidas atrás de squashes silenciosos.

## 🗺️ Roadmap

- [x] Integração real de rede com Ktor Client
- [x] Login e cadastro integrados à API
- [x] Persistência e restauração da sessão (tokens em armazenamento seguro + refresh automático)
- [ ] Navegação pós-login para a home
- [ ] Listagem e detalhe de viagens consumindo API real (hoje usa fonte dummy)
- [ ] Ampliar testes de unidade (já existem para `RegisterValidator` e `AuthSessionManager`) para use cases e ViewModels
- [ ] CI (lint + testes) no GitHub Actions

## 🤝 Acompanhe e contribua

Este projeto está sendo construído em público como estudo contínuo. Sugestões, críticas e PRs são bem-vindos — abra uma issue ou comente o que achar.

## 📄 Licença

Distribuído sob a licença [MIT](./LICENSE).

---

Saiba mais sobre [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).

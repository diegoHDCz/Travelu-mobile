# ✈️ Travelu

> Um app de viagens construído do zero para estudar, na prática, como compartilhar lógica entre Android e iOS sem abrir mão da experiência 100% nativa em cada plataforma.

Este é um projeto público de aprendizado. A ideia é simples: **uma única base de regras de negócio, duas interfaces nativas**. Tudo aqui — decisões de arquitetura, erros, refações e o uso de IA no fluxo de desenvolvimento — é documentado abertamente para quem quiser acompanhar, discutir ou aprender junto.

Se você trabalha com mobile, está curioso sobre Kotlin Multiplatform ou quer trocar ideia sobre arquitetura, sinta-se à vontade para abrir uma issue, sugerir algo ou deixar uma ⭐.

## 🧩 Stack

| Camada | Tecnologia |
|---|---|
| Lógica compartilhada | [Kotlin Multiplatform (KMP)](https://kotlinlang.org/docs/multiplatform.html) |
| Rede | [Ktor Client](https://ktor.io/) |
| Injeção de dependência | [Koin](https://insert-koin.io/) |
| UI Android | [Jetpack Compose](https://developer.android.com/jetpack/compose) |
| UI iOS | [SwiftUI](https://developer.apple.com/xcode/swiftui/) |
| Arquitetura | Clean Architecture + Clean Code |
| Build | Gradle (Kotlin DSL), version catalog (`libs.versions.toml`) |
| Ferramentas | Android Studio + Xcode, com [Claude Code](https://claude.com/claude-code) como parceiro de desenvolvimento |

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

### Android

```bash
./gradlew :androidApp:assembleDebug
```

Ou use a run configuration do Android Studio.

### iOS

Abra `iosApp/iosApp.xcodeproj` no Xcode e rode a partir de lá.

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
- **Commits e evolução documentados**: decisões de arquitetura e mudanças de rumo são registradas ao longo do histórico do projeto, não escondidas atrás de squashes silenciosos.

## 🗺️ Roadmap

- [ ] Integração real de rede com Ktor Client (hoje a camada de dados usa uma fonte dummy)
- [ ] Autenticação completa (fluxo de login ponta a ponta)
- [ ] Listagem e detalhe de viagens consumindo API real
- [ ] Testes de unidade para use cases e ViewModels
- [ ] CI (lint + testes) no GitHub Actions

## 🤝 Acompanhe e contribua

Este projeto está sendo construído em público como estudo contínuo. Sugestões, críticas e PRs são bem-vindos — abra uma issue ou comente o que achar.

## 📄 Licença

Distribuído sob a licença [MIT](./LICENSE).

---

Saiba mais sobre [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).

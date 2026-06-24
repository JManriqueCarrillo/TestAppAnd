# TestAppAnd 🚀

Android application built using **Clean Architecture**, **SOLID** principles, and a **Multi-module** structure, designed for scalability and maintainability.

## 📱 Features
- **Product Catalog**: Real-time product listing fetched from the FakeStore API.
- **Local Persistence**: Management of favorite products using a local database (Room).
- **User Profile**: Display of user information and dynamic statistics (favorites count).
- **Navigation**: Implementation of `BottomNavigationBar` using Jetpack Compose Navigation.
- **Unified State Management**: Use of `BaseViewModel` and `UiState` for consistent handling of loading, success, and error states.

## 🏗️ Architecture
The project follows a layered modular architecture to separate responsibilities:

### Core Modules
- **`:app`**: Orchestrator module. Contains `MainActivity`, global Hilt configuration, visual theme, and the main navigation structure.
- **`:core`**: Shared core components. Contains domain entities, base ViewModel logic, failure management (`Failure`), and DI modules for network and dispatchers.
- **`:domain`**: Business rules. Defines repository interfaces and Use Cases that execute specific actions. It has no Android dependencies.
- **`:data`**: Data implementation. Contains Retrofit (API) and Room (Database) logic, repository implementations, and data mappers.

### Feature Modules (`:features:*`)
Each functionality resides in its own isolated module:
- **`:features:products`**: Catalog management and visualization.
- **`:features:favorites`**: List of products marked by the user.
- **`:features:profile`**: User information and statistics.

## 🛠️ Technology Stack
- **Language**: Kotlin + Coroutines + Flow.
- **UI**: Jetpack Compose (Material 3).
- **Dependency Injection**: Hilt.
- **Networking**: Retrofit + Kotlinx Serialization.
- **Database**: Room.
- **Functional Programming**: Arrow-kt (`Either` for error management).
- **Images**: Coil.

## 🧪 Testing
The project includes coverage at different levels:
- **Unit Tests**: Logic tests for `UseCases` and `ViewModels` using **MockK** and **Turbine**.
- **Integration Tests**: Validation of the data layer and repositories.
- **Instrumental Tests**: UI and navigation tests using **Compose Test Rule** and **Hilt Testing**.

## 🚀 Project Setup
1. Clone the repository.
2. Ensure you have the latest version of **Android Studio**.
3. Perform a **Gradle Sync**.
4. To run tests: `./gradlew test` (unit) or `./gradlew connectedCheck` (instrumental).

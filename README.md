# Disney Character App

A Kotlin Multiplatform project that demonstrates OpenTelemetry integration on both Android and iOS platforms, using the Disney API for data.

## Features

- Display a grid of Disney characters with images and names
- Distributed tracing and metrics using OpenTelemetry
- Cross-platform implementation (Android and iOS)
- Network monitoring with OpenTelemetry spans

## Technologies

- **Kotlin Multiplatform**: Shared code between Android and iOS
- **Compose Multiplatform**: UI framework for cross-platform development
- **Ktor**: HTTP client for API requests
- **Koin**: Dependency injection
- **OpenTelemetry**: Distributed tracing and metrics
- **Coil**: Image loading
- **Cocoapods**: iOS dependency management

## Project Structure

* `/composeApp`: Shared code for Compose Multiplatform applications
  - `commonMain`: Common code for all platforms
  - `androidMain`: Android-specific implementations
  - `iosMain`: iOS-specific implementations

* `/iosApp`: iOS application entry point with Cocoapods integration
  - Contains OpenTelemetry Swift SDK integration

## Setup

1. Clone the repository
2. Create a `local.properties` file with your SDK path
3. Create a `secrets.gradle.kts` file with the following content:
   ```kotlin
   extra["httpEndpoint"] = "YOUR_OPENTELEMETRY_ENDPOINT"
   extra["authorization"] = "YOUR_AUTHORIZATION_KEY"
   ```
4. Build the project using Gradle

## Learn More

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [OpenTelemetry](https://opentelemetry.io/)
- [Disney API](https://disneyapi.dev/)
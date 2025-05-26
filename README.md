# Disney Character App

A Kotlin Multiplatform project that demonstrates OpenTelemetry integration on both Android and iOS platforms, using the Disney API for data.

## Features

- Display a grid of Disney characters with images and names
- Distributed tracing and metrics using OpenTelemetry
- Cross-platform implementation (Android and iOS)
- Network monitoring with OpenTelemetry spans

## Screenshots

Here's a glimpse of the Disney Character App in action:

**Android**

![Android App Screenshot](placeholder_android_screenshot.png)
*(Coming Soon: Screenshot of the main character grid on Android)*

**iOS**

![iOS App Screenshot](placeholder_ios_screenshot.png)
*(Coming Soon: Screenshot of the main character grid on iOS)*

## Technologies

- **Kotlin Multiplatform**: Shared code between Android and iOS
- **Compose Multiplatform**: UI framework for cross-platform development
- **Ktor**: HTTP client for API requests
- **Koin**: Dependency injection
- **OpenTelemetry**: Distributed tracing and metrics
- **Coil**: Image loading
- **Cocoapods**: iOS dependency management

## Project Structure

- `/composeApp`: Shared code for Compose Multiplatform applications
  - `commonMain`: Common code for all platforms
  - `androidMain`: Android-specific implementations
  - `iosMain`: iOS-specific implementations

- `/iosApp`: iOS application entry point with Cocoapods integration
  - Contains OpenTelemetry Swift SDK integration

## OpenTelemetry Integration

This project leverages OpenTelemetry to provide insights into app performance and network interactions, and to demonstrate observability in a Kotlin Multiplatform Mobile (KMM) application.

**Key aspects of the OpenTelemetry integration include:**

-   **Purpose:** To monitor application performance, trace requests to the Disney API, and showcase how observability can be implemented in a KMM environment.
-   **Components Used:**
    -   **OTLP Exporters:** Telemetry data (traces and metrics) is exported to an OpenTelemetry collector via OTLP (OpenTelemetry Protocol). Configuration for the endpoint and authorization is managed in the `secrets.gradle.kts` file.
    -   **Ktor Client Auto-Instrumentation:** Network requests made using the Ktor HTTP client are automatically instrumented, creating spans for each request/response cycle. This helps in tracing the latency and success/failure of API calls.
    -   **Manual Span Creation:** Custom spans are created around specific code blocks or user interactions (e.g., screen navigation, button clicks) to provide more granular insights into application behavior.
-   **Telemetry Data Collected:**
    -   **Traces:** Detailed traces are collected for all Disney API requests, as well as for custom-defined spans that track specific application events or user flows.
    -   **Metrics:** (Future enhancement) Basic application performance metrics (e.g., startup time, screen rendering times) will be collected.

## Setup

1. Clone the repository.
2. Create a `local.properties` file (in the project root) with your SDK path.
3. Create a `secrets.gradle.kts` file (in the project root) with the following content:
   ```kotlin
   extra["httpEndpoint"] = "YOUR_OPENTELEMETRY_ENDPOINT"
   extra["authorization"] = "YOUR_AUTHORIZATION_KEY"
   ```
4. **Development Environment:**
    -   We recommend using Android Studio for Android development.
    -   For iOS development, Xcode is recommended.
5. Build the project using Gradle.

### How to Run

**Android:**

1.  Open the project in Android Studio.
2.  Select the `composeApp` run configuration from the dropdown menu in the toolbar.
3.  Choose an Android emulator or connect a physical Android device.
4.  Click the 'Run' button (the green play icon).

**iOS:**

1.  Navigate to the `iosApp` directory in your terminal or Finder.
2.  Open the `iosApp.xcworkspace` file. This should launch Xcode.
3.  In Xcode, select the `iosApp` scheme and choose an iOS Simulator or a connected physical iOS device from the device dropdown.
4.  Click the 'Play' (Run) button (the triangular icon) in the Xcode toolbar.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these general guidelines:

-   **Bug Reports & Feature Requests:** Please open an issue on GitHub to report bugs or suggest new features. Provide as much detail as possible, including steps to reproduce for bugs.
-   **Minor Changes & Bug Fixes:** For small changes or direct bug fixes, feel free to open a Pull Request directly. Please reference any relevant issue.
-   **New Features & Significant Changes:** For new features or changes that might significantly alter the codebase or architecture, please open an Issue first to discuss the proposed changes. This helps ensure that your contribution aligns with the project's goals.
-   **Code Style:** Ensure your code adheres to the existing style and conventions used throughout the project.
-   **Testing:** If applicable, ensure all existing tests pass and consider adding new tests for your changes.
-   **Pull Request Descriptions:** Provide a clear and detailed description of your changes in the Pull Request. Explain the problem you're solving and the solution you've implemented.

We appreciate your help in making this app better!

## Learn More

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [OpenTelemetry](https://opentelemetry.io/)
- [Disney API](https://disneyapi.dev/)


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE.md) file for details.

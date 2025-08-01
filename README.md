# Pokemon Catalog

A modern Android application built with Kotlin, showcasing a catalog of Pokemon using Jetpack
Compose and MVVM architecture. This app allows users to search for Pokemon, view detailed
information, and manage their profiles.

## Features

- **Pokemon Search:** Search
- **Pokemon Detail:** View detailed information about a selected pokemon.
- **Local Caching:** User data is cached locally using Room database.
- **Modern UI:** Utilizes ViewModel, repository pattern, and clean navigation structure.

## Project Structure

- `app` — Main application module
- `build-logic` — Gradle build logic for the project and implement convention plugins
- `core:common` — Common utilities and resources shared across features
- `core:data` — Data layer with API and local storage integration
- `core:navigation` — Navigation components for the app
- `feature:auth` — Authentication feature for user login and registration
- `feature:home` — Home screen with list (with search) & detail pokemon
- `feature:profile` — Profile screen with user information
- `feature:splash` — Splash screen for app initialization

## Tech Stack

- Kotlin with MVVM architecture
- Jetpack Compose (UI)
- Coroutines (Reactive programming)
- Gradle Convention Plugins
- Room (local database)
- Gson (JSON parsing)
- Retrofit (networking)
- Hilt (Dependency Injection)
- Pokemon API
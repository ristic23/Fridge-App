# Fridge App

This application is a digital fridge organizer that allows users to store items, update details, and remove them when no longer needed. The goal is to provide a simple and intuitive way to keep track of fridge contents, reduce waste, and make food management easier.

## Tech Stack

The app is built entirely in **Kotlin**, using **Jetpack Compose** for UI.
- **Architecture**: MVVM
- **Persistence**: Room Database
- **Dependency Injection**: Hilt
- **Asynchronous Operations**: Kotlin Coroutines
- **Navigation**: Jetpack Navigation 3 for Compose
- **Testing**: JUnit and Compose UI tests
- **CI/CD**: GitHub Actions for automated build and testing

## Future Enhancements

Planned improvements include:
- Adding an API integration for easier browsing and importing of new items.
- Implementing pagination for large lists.
- Push notifications to remind users when an item is close to expiring or has expired.
- Tracking quantity (by number or metric/imperial units).
- Adding item photos for easier recognition.
- Trashcan functionality for soft-deleted items.
- Duplicating items with new expiration dates.
- Distinguishing between fridge and freezer storage.
- Expanding current package opened state to dynamically adjust expiration dates.

## Project Structure

The project follows a **feature-based package structure** within a single app module. This organization is designed to keep features isolated and maintainable while supporting a smooth migration path to a **multi-module architecture** in the future.  

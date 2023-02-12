# News App

News Android is a fully functional Android app built entirely with Kotlin and Jetpack Compose. It
follows the [official architecture guidance](https://developer.android.com/topic/architecture) and
development best practices.

# Why this project ?

The objective of this repository is to illustrate the application of Clean Architecture principles
in Android development. This project serves as a demonstration of best practices in modern Android
development. As the industry evolves and new trends emerge, the aim is to continually refine and
improve the architectural model to ensure that it remains relevant and effective. Ultimately, the
goal is to provide a solid and scalable foundation for building robust, maintainable, and efficient
Android applications that can meet the demands of even the most complex business requirements.

## Screenshots

### 🌚 Dark Mode

Home | Details | Filter
:-: | --- | ---
<img src="C:\Users\islam\Desktop\News app screen shots\Screenshot_20230211_075445.png" width="250" style="display: inline-block;" />
|  <img src="C:\Users\islam\Desktop\News app screen shots\Screenshot_20230211_074441.png" width="250" style="display: inline-block;" />
|   <img src="C:\Users\islam\Desktop\News app screen shots\Screenshot_20230212_071755.png" width="250" style="display: inline-block;" />

### 🌞 Light Mode

Home | Details | Filter
--- | --- | --- |
<img src="C:\Users\islam\Desktop\News app screen shots\Screenshot_20230211_075343.png" width="250" style="display: inline-block;" />|   <img src="C:\Users\islam\Desktop\News app screen shots\Screenshot_20230212_051709.png" width="250" style="display: inline-block;" /> |  <img src="C:\Users\islam\Desktop\News app screen shots\Screenshot_20230212_071505.png" width="250" style="display: inline-block;" />

# UI

The app was designed using [Material 3 guidelines](https://m3.material.io/).

The Screens and UI elements are built entirely
using [Jetpack Compose](https://developer.android.com/jetpack/compose).

The app has two themes:

- Dynamic color - uses colors based on
  the [user's current color theme](https://material.io/blog/announcing-material-you) (if supported)
- Default theme - uses predefined colors when dynamic color is not supported

Each theme also supports dark mode.

## Essential Libraries

The following third-party libraries were used in this project:

- UI wrote
  in [Jetpack Compose Material3](https://developer.android.com/jetpack/androidx/releases/compose-material3)
  .
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
  and [Flow](https://developer.android.com/kotlin/flow) for async operations
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- [Retrofit](https://square.github.io/retrofit/) for networking
- Images are shown using [Coil](https://coil-kt.github.io/coil/).
- Build with [Gradle catalog ](https://docs.gradle.org/current/userguide/platforms.html)
- [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) for
  navigation between screens
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that stores
  exposes, and manages UI state
- [Room](https://developer.android.com/training/data-storage/room) data persistence library.
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) to load
  partial data on demand reduces the usage of network bandwidth and system resources.

## Future enhancements for the next releases

Our goal is to continuously improve and expand upon this project. Here are some of the planned
additions for future iterations:

- [ ] Implement Unit tests.
- [ ] Implement UI tests.
- [ ] Apply [ktlint](https://pinterest.github.io/ktlint/) for checking code style.
- [ ] Use [Git Hooks](https://git-scm.com/book/en/v2/Customizing-Git-Git-Hooks).
- [ ] Use [GitHub Actions](https://github.com/features/actions) as CI/CD.
- [ ] 
  Use [Core-splashscreen](https://developer.android.com/reference/kotlin/androidx/core/splashscreen/SplashScreen)
  to replicate the splash screen behavior from Android 12
- [ ] Add [Animation](https://developer.android.com/jetpack/compose/animation) to compose layouts.
- [ ] 
  Use [Benchmark](https://developer.android.com/topic/performance/benchmarking/benchmarking-overview)
  to inspect and monitor the performance.
- [ ] Use [JankStats](https://developer.android.com/topic/performance/jankstats) to track and
  analyze performance problems.
- [ ] Support Tablet Devices.

## Features

News app is designed to provide a seamless and user-friendly experience for reading articles
from [News API](https://newsapi.org/). Key features include:

- Displaying a comprehensive list of news articles
- Efficient loading of data through on-demand loading (Paging), reducing network usage and system
  resource consumption
- The ability for users to preview article details and read them in full by opening the article URL
- The option for users to filter articles sources (Providers) within the app
- Offline access to previously viewed articles, ensuring that users can access the information they
  need even without an internet connection
- An attractive and intuitive user interface, including a built-in **dark theme** for comfortable
  reading in low-light conditions

## Requirements

### News API key

News app uses the [News API](https://newsapi.org/) to fetch the articles. To use the API, you will
need to obtain a free developer API key. See
the [News API Documentation](https://newsapi.org/docs/get-started) for instructions.

Once you have the key, add this line to the `gradle.properties` file

```
api.key="your newsapi access key"
```

## Find this repository useful? :heart:

Support it by joining __[stargazers](https://github.com/subwilven/NewsApp/stargazers)__ for this
repository. :star:
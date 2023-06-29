# WeatherApp
This is a Weather App based on and extended from [philipplackner's version](https://github.com/philipplackner/WeatherApp).

## Features
- [Open Meteo](https://open-meteo.com/en): Harnessing the power of Open Meteo, the app provides hourly weather information for the entire day
- [Google Maps API](https://developers.google.com/maps): The application integrates Google Maps API enabling users to select any global location to retrieve the corresponding weather information.
- Real-time Location Tracking: With advanced GPS services, the app efficiently determines the user's current location and fetches the corresponding weather data.
- Recent Search Queries: The application includes a unique feature where all the recent search queries made by the user are temporarily stored for quick reference and efficient recall.

![weatherApp](images/ph1.jpg)

## Technical Stack and Architectural Patterns

The WeatherApp incorporates a diverse range of libraries, technologies, and design patterns, including:

- Kotlin: The application is entirely written in Kotlin, showcasing its concise syntax and strong language features.

- Jetpack Compose: Utilizing the modern UI toolkit, Jetpack Compose, the UI components are efficiently designed with less boilerplate code.

- MVI with Clean Architecture: Model-View-Intent (MVI) is used in combination with Clean Architecture principles to facilitate an unidirectional and predictable data flow, simplifying the understanding and modification of the app behavior.

- Hilt: For reliable and streamlined dependency injection, Hilt is employed.

- FusedLocationProvider: Leverages FusedLocationProvider for accurate and optimized location services.

- OneMap API & Retrofit2: OneMap API is used in conjunction with Retrofit2 for network requests, enabling a seamless flow of data from the network to the application.

- Leak Canary: To ensure memory efficiency and detect potential memory leaks, Leak Canary is incorporated.

This advanced version of WeatherApp stands as a testament to modern app development practices, offering users a refined weather tracking experience while providing developers an example of a well-structured, scalable, and maintainable application.

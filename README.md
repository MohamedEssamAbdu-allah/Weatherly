# Weatherly
Welcome to Weatherly App project repository! This Android application allows users to view hourly and weekly weather forecasts for their current location or any location chosen via GPS or map selection. It also supports saving locations for easy access to weather information without needing to search again.

## Features
- **Hourly and Weekly Weather**: View detailed weather forecasts for the upcoming hours and week.
- **Location Services**: Automatically fetch weather data based on current GPS coordinates.
- **Map Integration**: Select locations via Google Maps for weather information.
- **Saved Locations**: Save favorite locations for quick weather updates.
- **Settings Features**:
  - Choose Temperature Unit: Kelvin, Celsius, or Fahrenheit.
  - Select Language: Arabic or English.
  - Wind Speed Measurement: Miles per hour or Meters per second.
  - Location Selection Preference: GPS or Map.
- **Technologies Used**: Data Binding, Flow, Room Database, Retrofit for API calls, Google Maps SDK, Kotlin Coroutines for asynchronous operations, Glide for image loading, MVVM architecture, Singleton and Repository design patterns, JUnit for unit testing, manual dependency injection, and manual fake for testing.

## Usage
Once installed and configured, the Weather App provides a straightforward user interface:

first you need to give the app permission to initialize your location using gps (it can be modified later from the settings)

![1](https://github.com/user-attachments/assets/6fa47413-3aaa-4f3b-ab7d-e98c748f80e3)
<br /> 
<br /> 
<br /> 
then the splash screen load and navigate you into the app

![2PNG](https://github.com/user-attachments/assets/7d730f00-eaac-4ea6-9fcc-9fa80ead3380)

- **Home Screen**: Shows the current weather details for the user's current location as for the hourly weather and has the "7-Day Forecasts" option

<p align="left">
  <img src="https://github.com/user-attachments/assets/46a2464e-68f5-4621-a534-2eb8576cfa01" width="400" height="800" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/b20e6f37-7f07-4ddc-943d-e9cdbc3e6409" width="400" height="800" />
</p>

- **Search and Save Locations**: Use the search functionality or map integration to find and save locations.

![9](https://github.com/user-attachments/assets/1cf86f86-d6f4-4795-990f-27f0132a1886)

- **Saved Locations**: Access saved locations for quick weather updates.
  
![10](https://github.com/user-attachments/assets/69f03825-5e9e-47d2-bd6e-dc48d6f8ede5)

- **Settings**: Customize temperature unit, language, wind speed measurement, and location selection preference.
  
![5](https://github.com/user-attachments/assets/df0e394f-8ea8-4089-9af6-173d00f757cd)

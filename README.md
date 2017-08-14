# Zonky app

Application for periodic checking of new loans.

## Running the app via gradle (development mode)
* ```./gradlew run``` - every 5 minutes checks for new loans and print them to the console by default (or to the txt file)

## Running tests
* ```./gradlew test```

## Bundling the app
* ```./gradlew build```
* ```java -jar build/libs/zzz-1.0-SNAPSHOT.jar```
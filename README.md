
Webservice for the [Public Transport Enabler](https://github.com/schildbach/public-transport-enabler) library

# Install & Run

Build service 

    $ gradle clean build

Set environment variables (e.g. by adding a .env file)

    PROVIDER_NAME=...
    PROVIDER_AUTHORIZATION=...

Run application

    $ java -jar build/libs/public-transport-webservice.jar

# API

## Locations

GET /query-nearby-locations

## Departures

GET /query-departures

## Suggestions

GET /suggest-locations

# License
[MIT](LICENSE)

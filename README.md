# Parking Management System

A RESTful API for managing parking spaces, vehicle parking, and billing calculations built with Spring Boot.

## Features

- Park vehicles in available parking spaces
- Track parking status (available/occupied spaces)
- Generate bills with time-based charges
- Support for different vehicle types with different rates
- Additional charges for extended parking periods

## Technologies

- Java 17
- Spring Boot 3.3.0
- Maven
- MapStruct for object mapping
- Lombok
- OpenAPI Generator for API code generation

## Prerequisites

- JDK 17 or higher
- Maven 3.6+ (or use Maven Wrapper included in the project)

## Quick Start

```bash
.\mvnw.cmd clean install - builds the project and installs dependencies
.\mvnw.cmd spring-boot:run - runs the application on http://localhost:8080
```

Or using Maven:

```bash
mvn clean install - builds the project and installs dependencies
mvn spring-boot:run - runs the application on http://localhost:8080
```

Or using IDE - run the `main` method in `ParkingApplication.java`

## Configuration

Application settings can be configured in `src/main/resources/application.yml`:

```yaml
parking:
  total-spaces: 10
  rate:
    smallCar: 0.10
    mediumCar: 0.20
    largeCar: 0.40
  additional-charge:
    amount: 1.0
    periodMinutes: 5
```

- `total-spaces`: Total number of parking spaces available
- `rate`: Base rates per vehicle type (per time unit)
- `additional-charge`: Additional charge applied every `periodMinutes` minutes

## API Endpoints

### Get Parking Status

**GET** `/parking`

Returns the current parking status with available and occupied spaces.

**Response:**
```json
{
  "availableSpaces": 8,
  "occupiedSpaces": 2
}
```

### Park Vehicle

**POST** `/parking`

Parks a vehicle in the first available space.

**Request:**
```json
{
  "vehicleReg": "ABC123",
  "vehicleType": 0
}
```

**Response:**
```json
{
  "vehicleReg": "ABC123",
  "spaceNumber": 1,
  "timeIn": "2024-01-01T10:00:00"
}
```

**Vehicle Types:**
- `0` - Small car
- `1` - Medium car
- `2` - Large car

### Generate Bill

**POST** `/parking/bill`

Frees up the parking space and generates a bill with the total charge.

**Request:**
```json
{
  "vehicleReg": "ABC123"
}
```

**Response:**
```json
{
  "billId": "bill-123",
  "vehicleReg": "ABC123",
  "vehicleCharge": 2.50,
  "timeIn": "2024-01-01T10:00:00",
  "timeOut": "2024-01-01T12:30:00"
}
```


### Assumptions & Open Questions

- Parking time is calculated in full minutes (rounded up)
- A car cannot be parked twice
- Total number of parking spaces is fixed and configurable (default: 10)
- Concurrent requests handling is out of scope for this task


- Are parking spaces vehicle-type specific?
- What does “first available space” mean?
- How should parking time be calculated and rounded?
- How should the additional £1 charge be applied?
  Is it added for every full 5-minute interval, or for any started 5-minute interval?



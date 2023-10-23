# Booking Block Project

This project is a booking and block management system built using Java 11 and Spring Boot. It provides functionality for managing reservations (bookings) and blocking out specific dates (blocks) on a property.

## Table of Contents

- [Introduction](#introduction)
- [Property](#property)
- [Booking](#booking)
- [Block](#block)
- [Built With](#built-with)
- [Project structure](#project-structure)
- [Test](#test)
- [API Documentation](#api-documentation)
- [Postman Collection](#postman-collection)

## Introduction

In this application, a booking is defined as when a guest selects a start and end date and submits a reservation for a property. This allows guests to reserve the property for a specific time period. It's a crucial part of property management and helps property owners and managers keep track of reservations.

A block, on the other hand, is when the property owner or manager selects a range of days during which no guest can make a booking. There are various reasons for creating blocks, such as when the owner wants to use the property for themselves or when the property manager needs to schedule maintenance tasks, like repainting a few rooms. Blocks ensure that specific dates are unavailable for booking, helping to manage the property's availability effectively.

## Property

The property functionality of this system allows property owners to:
- Create a property by providing a name and description.
- View all properties

## Booking

The booking functionality of this system allows guests to:
- Create a booking for a property by providing a start and end date.
- View all bookings
- View a specific booking
- Update a booking
- Delete a booking


## Block

The block functionality of this system allows property owners to:
- Create a block for a property by providing a start and end date.
- View all blocks
- View a specific block
- Update a block
- Delete a block


## Built With

- Java 11: The project is developed using Java 11, a stable and widely used version of the Java programming language.
- Spring Boot: The project utilizes the Spring Boot framework, which simplifies the development of production-ready applications.

## Project structure
The project is structured as follows:
```sh
booking-block
├── booking-block.iml
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── bookingblock
│   │   │           ├── BookingBlockApplication.java
│   │   │           ├── controller
│   │   │           │   ├── BlockController.java
│   │   │           │   ├── BookingController.java
│   │   │           │   └── PropertyController.java
│   │   │           ├── model
│   │   │           │   ├── Block.java
│   │   │           │   ├── Booking.java
│   │   │           │   ├── Property.java
│   │   │           │   └── ResponseData.java
│   │   │           ├── repository
│   │   │           │   ├── BlockRepository.java
│   │   │           │   ├── BookingRepository.java
│   │   │           │   └── PropertyRepository.java
│   │   │           ├── service
│   │   │           │   ├── BlockService.java
│   │   │           │   ├── BookingService.java
│   │   │           │   └── PropertyService.java
│   │   │           └── SwaggerConfig.java
│   │   └── resources
│   │       └── application.yaml
│   └── test
│       └── java
│           └── com
│               └── bookingblock
│                   └── service
│                       ├── BlockServiceTest.java
│                       └── BookingServiceTest.java
```

## Test
The unit tests has been covered the book and block services using Mockito and JUnit. They can be found at:
```sh
src/test/java/com/booking/block/bookingblock/service/BlockServiceTest.java
src/test/java/com/booking/block/bookingblock/service/BookingServiceTest.java
```

## API Documentation
The API documentation has been generated using Swagger. It can be found at:
```sh
http://localhost:8080/swagger-ui/index.html
```

## Postman Collection

Also this project has been exported as a Postman collection. It can be found at the root of the project:
```sh
BookingBlock.postman_collection.json
```

# Booking Block Project

This project is a booking and block management system built using Java 11 and Spring Boot. It provides functionality for managing reservations (bookings) and blocking out specific dates (blocks) on a property.

## Table of Contents

- [Introduction](#introduction)
- [Booking](#booking)
- [Block](#block)
- [Built With](#built-with)

## Introduction

In this application, a booking is defined as when a guest selects a start and end date and submits a reservation for a property. This allows guests to reserve the property for a specific time period. It's a crucial part of property management and helps property owners and managers keep track of reservations.

A block, on the other hand, is when the property owner or manager selects a range of days during which no guest can make a booking. There are various reasons for creating blocks, such as when the owner wants to use the property for themselves or when the property manager needs to schedule maintenance tasks, like repainting a few rooms. Blocks ensure that specific dates are unavailable for booking, helping to manage the property's availability effectively.

## Booking

The booking functionality of this system allows guests to:
- Select a start and end date for their reservation.
- Submit reservation requests for the property.
- Get confirmation and details of their reservation once it's approved by the property owner or manager.

## Block

The block functionality of this system allows property owners and managers to:
- Select a range of days that should be unavailable for bookings.
- Provide reasons and descriptions for the block (e.g., "Property owner's personal use" or "Room repainting scheduled").
- Ensure that no guest can book the property during the blocked period.

## Built With

- Java 11: The project is developed using Java 11, a stable and widely used version of the Java programming language.
- Spring Boot: The project utilizes the Spring Boot framework, which simplifies the development of production-ready applications.

This README provides an overview of the project's core concepts and technologies used. For more detailed information on how to use and configure the system, please refer to the project's documentation and source code.

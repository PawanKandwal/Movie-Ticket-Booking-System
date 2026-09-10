# Movie Ticket Booking System

A Java-based, menu-driven Movie Ticket Booking System developed as part of the **TCS-504 System Design** assignment.

The system models a single-cinema movie booking workflow including movie/show listing, seat availability, booking, payment, ticket printing, and cancellation.

## Features

- List currently playing movies
- List shows for a selected movie
- Display seat layout with `AVAILABLE` / `BOOKED` status
- Book one or more seats
- Reject booking if any selected seat is already booked
- Seat-based pricing:
  - SILVER — ₹150
  - GOLD — ₹250
  - PLATINUM — ₹400
- Payment through:
  - UPI
  - Card
  - Cash
- Failed payment releases reserved seats
- Generate and print booking ticket
- Cancel confirmed bookings and release seats
- Validate invalid menu choices, movie/show selections, and seat numbers

## OOP Concepts Demonstrated

- Encapsulation
- Abstraction
- Inheritance
- Runtime Polymorphism
- Compile-Time Polymorphism
- Static Members
- `this` Keyword
- Composition
- Aggregation
- Association

## SOLID Principles

The project demonstrates the required SOLID design principles through:

- Single Responsibility Principle
- Open/Closed Principle
- Liskov Substitution Principle
- Interface Segregation Principle
- Dependency Inversion Principle consideration

A deliberate design decision is also documented regarding the use of direct console input/output.

## Project Structure

```text
src/
├── Movie.java
├── Seat.java
├── SeatType.java
├── Screen.java
├── Cinema.java
├── Show.java
├── ShowSeat.java
├── SeatStatus.java
├── Customer.java
├── Booking.java
├── BookingStatus.java
├── Payment.java
├── UpiPayment.java
├── CardPayment.java
├── CashPayment.java
├── PriceCalculator.java
├── TicketPrinter.java
├── BookingService.java
└── Main.java

docs/
└── Project diagrams and documentation

run.bat

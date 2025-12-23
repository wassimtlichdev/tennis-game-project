--Tennis Score Computer – Hexagonal Architecture

For this project, I chose to implement a Hexagonal Architecture.
Although this approach may seem overkill for a simple Tennis Kata, 
my goal was to demonstrate my ability to design a modern, maintainable, and testable Java application.

--Project Architecture

The project is structured around two main layers to ensure maintainability and testability:

1. Domain Core

Data Model: Uses sealed interfaces and records to represent the different score states.

Ports: Definition of input ports (use cases) and output ports.

Service: Pure state transition logic, independent from any infrastructure concerns.

2. Infrastructure Layer

Contains adapters that connect the domain to the outside world.

Console Adapter: Implements the output port to display scores in the terminal.

--Java Concepts Used

Sealed Interfaces: Ensure that the set of score states is closed and type-safe.

Pattern Matching for switch: Enables exhaustive and readable score rendering without nested if/else.

Records: Provide concise and immutable data models.

--Package Structure
src/main/java
├── domain/                  # Game logic
│   ├── model                # Score, PointScore, Deuce, etc.
│   ├── port/
│   │   ├── in               # PlayTennisGameUseCase
│   │   └── out              # ScoreOutputPort
│   └── application          # PlayTennisGameService (UseCase implementation)
├── infra/                   # Adapters
│   └── out                  # ConsoleScoreAdapter.java
└── TennisApp.java           # Application entry point

--Testing Strategy

JUnit 5, AssertJ, and Mockito are used for unit testing and behavior verification.

--Run Tests
mvn test
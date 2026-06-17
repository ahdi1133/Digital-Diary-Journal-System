# Digital Diary / Journal System - Presentation Outline

## Slide 1: Title

Digital Diary / Journal System

## Slide 2: Problem

People need a simple private desktop tool to record and organize daily thoughts without relying on paper diaries.

## Slide 3: Objectives

- Add, edit, delete, view, and search diary entries
- Keep data persistent in SQLite
- Apply MVC, Singleton, and Observer patterns
- Include UML and tests

## Slide 4: Technology Stack

- Java
- Java Swing
- SQLite JDBC
- MVC architecture
- Git/GitHub

## Slide 5: Main Features

- New diary entry with title, date, and content
- Edit selected entries
- Delete selected entries
- View all entries
- Search by keyword and date

## Slide 6: Architecture

The system separates UI, logic, and data access through MVC. The controller validates input and coordinates between the Swing view and SQLite repository.

## Slide 7: Design Patterns

- Singleton: `DatabaseConnection`
- MVC: model, view, controller, repository layers
- Observer: `EntryEventPublisher` refreshes the entry list

## Slide 8: Testing

Repository and controller tests verify CRUD, search, validation, and observer updates.

## Slide 9: Demo Flow

Create an entry, edit it, search it by keyword/date, delete it, and confirm persistence after restarting.

## Slide 10: Conclusion

The application meets the SCD project scope with a clean GUI, persistent storage, design patterns, UML diagrams, and tests.

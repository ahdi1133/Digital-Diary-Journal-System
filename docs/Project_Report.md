# Digital Diary / Journal System - Project Report

## Overview

The Digital Diary / Journal System is a Java Swing desktop application that lets users maintain personal diary entries digitally. Users can add, edit, delete, view, and search entries by keyword or date. The system uses SQLite through JDBC for persistent storage.

## Objectives

- Provide CRUD operations for diary entries.
- Support searching by title/content keyword and entry date.
- Use MVC to separate user interface, business logic, and data access.
- Apply Singleton and Observer design patterns.
- Provide UML diagrams and unit tests for Software Construction and Design evaluation.

## Architecture

The project follows MVC:

- Model: `DiaryEntry`
- View: `DiaryView`
- Controller: `DiaryController`
- Data Access: `DiaryRepository` and `SQLiteDiaryRepository`

The `DatabaseConnection` class is a Singleton that manages the SQLite JDBC connection. The `EntryEventPublisher` implements the Observer pattern so the view updates when entries are added, edited, deleted, or searched.

## Database

Table: `diary_entries`

| Column | Type | Purpose |
| --- | --- | --- |
| id | INTEGER PRIMARY KEY AUTOINCREMENT | Unique entry ID |
| title | TEXT NOT NULL | Entry title |
| entry_date | TEXT NOT NULL | Diary date in ISO format |
| content | TEXT NOT NULL | Entry body |
| created_at | TEXT | Creation timestamp |
| updated_at | TEXT | Last update timestamp |

## Testing

Tests are included for repository CRUD/search operations, controller validation, and observer notification. They can be run with `.\scripts\test.ps1`.

## Conclusion

The project satisfies the proposed idea by delivering a functional Java Swing diary application with database persistence, MVC structure, design patterns, UML documentation, and unit tests.

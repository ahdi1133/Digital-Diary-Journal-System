# Digital Diary / Journal System

Java Swing desktop application for writing, saving, editing, deleting, viewing, and searching personal diary entries.

## Features

- Add diary entries with title, date, and content
- Edit and delete existing entries
- View all saved entries
- Search by keyword or date
- SQLite database backend through JDBC
- MVC architecture
- Singleton database connection manager
- Observer pattern for refreshing the entry list
- Unit tests for repository and controller behavior


## Run

Double-click:

`run.bat`

Or run from PowerShell:

```powershell
.\scripts\build.ps1
.\scripts\run.ps1
```

The app stores its database at `data/diary.db`.

## IntelliJ IDEA

Open the project folder and let IntelliJ import the `pom.xml` as a Maven project. Then run:

`src/main/java/com/digitaldiary/Main.java`

If Maven import is unavailable, add `lib/sqlite-jdbc.jar` through File > Project Structure > Libraries.

## Test

```powershell
.\scripts\test.ps1
```

## Project Structure

- `src/main/java/com/digitaldiary` - application source
- `src/test/java/com/digitaldiary` - lightweight test runner and tests
- `docs/uml` - Use Case, Class, and Sequence diagrams
- `docs/Project_Report.md` - project report
- `docs/Presentation.md` - presentation outline

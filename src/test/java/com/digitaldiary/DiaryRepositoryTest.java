package com.digitaldiary;

import com.digitaldiary.database.DatabaseConnection;
import com.digitaldiary.model.DiaryEntry;
import com.digitaldiary.repository.SQLiteDiaryRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public final class DiaryRepositoryTest {
    private DiaryRepositoryTest() {
    }

    public static void crudAndSearch() {
        SQLiteDiaryRepository repository = createRepository("crud-search.db");

        DiaryEntry created = repository.create(new DiaryEntry("First Day", LocalDate.of(2026, 6, 7), "Project idea submitted."));
        Assertions.assertTrue(created.getId() > 0, "Created entry should have an ID");

        repository.update(new DiaryEntry(created.getId(), "Updated Day", LocalDate.of(2026, 6, 8), "Implemented CRUD."));
        List<DiaryEntry> keywordResults = repository.search("crud", null);
        Assertions.assertEquals(1, keywordResults.size());
        Assertions.assertEquals("Updated Day", keywordResults.get(0).getTitle());

        List<DiaryEntry> dateResults = repository.search("", LocalDate.of(2026, 6, 8));
        Assertions.assertEquals(1, dateResults.size());

        repository.delete(created.getId());
        Assertions.assertEquals(0, repository.findAll().size());
    }

    static SQLiteDiaryRepository createRepository(String fileName) {
        try {
            DatabaseConnection.resetForTests();
            Path path = Path.of("out", "test-data", fileName);
            Files.createDirectories(path.getParent());
            Files.deleteIfExists(path);
            return new SQLiteDiaryRepository(DatabaseConnection.getInstance(path));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}

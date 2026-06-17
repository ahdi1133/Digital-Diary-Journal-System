package com.digitaldiary;

import com.digitaldiary.controller.DiaryController;
import com.digitaldiary.model.DiaryEntry;
import com.digitaldiary.repository.SQLiteDiaryRepository;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public final class DiaryControllerTest {
    private DiaryControllerTest() {
    }

    public static void validationRejectsBlankTitle() {
        SQLiteDiaryRepository repository = DiaryRepositoryTest.createRepository("validation.db");
        DiaryController controller = new DiaryController(repository);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> controller.addEntry("", LocalDate.now(), "Content"));
    }

    public static void observerReceivesRefresh() {
        SQLiteDiaryRepository repository = DiaryRepositoryTest.createRepository("observer.db");
        DiaryController controller = new DiaryController(repository);
        AtomicInteger refreshCount = new AtomicInteger();
        controller.addEntryChangeListener(entries -> refreshCount.incrementAndGet());

        DiaryEntry entry = controller.addEntry("Observer", LocalDate.of(2026, 6, 7), "List should refresh.");

        Assertions.assertTrue(entry.getId() > 0, "Entry should be created");
        Assertions.assertEquals(1, refreshCount.get());
    }
}

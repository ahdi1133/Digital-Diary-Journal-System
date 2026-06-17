package com.digitaldiary.validation;

import java.time.LocalDate;

public final class EntryValidator {
    private EntryValidator() {
    }

    public static void validate(String title, LocalDate date, String content) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date is required.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content is required.");
        }
    }
}

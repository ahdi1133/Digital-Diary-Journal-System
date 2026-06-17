package com.digitaldiary.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DiaryEntry {
    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final int id;
    private String title;
    private LocalDate entryDate;
    private String content;

    public DiaryEntry(String title, LocalDate entryDate, String content) {
        this(0, title, entryDate, content);
    }

    public DiaryEntry(int id, String title, LocalDate entryDate, String content) {
        this.id = id;
        this.title = title;
        this.entryDate = entryDate;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return DISPLAY_DATE.format(entryDate) + " - " + title;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DiaryEntry that)) {
            return false;
        }
        return id == that.id
                && Objects.equals(title, that.title)
                && Objects.equals(entryDate, that.entryDate)
                && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, entryDate, content);
    }
}

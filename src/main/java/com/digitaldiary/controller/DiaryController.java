package com.digitaldiary.controller;

import com.digitaldiary.model.DiaryEntry;
import com.digitaldiary.observer.EntryChangeListener;
import com.digitaldiary.observer.EntryEventPublisher;
import com.digitaldiary.repository.DiaryRepository;
import com.digitaldiary.validation.EntryValidator;

import java.time.LocalDate;
import java.util.List;

public class DiaryController {
    private final DiaryRepository repository;
    private final EntryEventPublisher publisher = new EntryEventPublisher();

    public DiaryController(DiaryRepository repository) {
        this.repository = repository;
    }

    public void addEntryChangeListener(EntryChangeListener listener) {
        publisher.addListener(listener);
    }

    public List<DiaryEntry> loadEntries() {
        List<DiaryEntry> entries = repository.findAll();
        publisher.notifyEntriesChanged(entries);
        return entries;
    }

    public DiaryEntry addEntry(String title, LocalDate entryDate, String content) {
        EntryValidator.validate(title, entryDate, content);
        DiaryEntry entry = repository.create(new DiaryEntry(title.trim(), entryDate, content.trim()));
        loadEntries();
        return entry;
    }

    public void updateEntry(int id, String title, LocalDate entryDate, String content) {
        EntryValidator.validate(title, entryDate, content);
        repository.update(new DiaryEntry(id, title.trim(), entryDate, content.trim()));
        loadEntries();
    }

    public void deleteEntry(int id) {
        repository.delete(id);
        loadEntries();
    }

    public List<DiaryEntry> search(String keyword, LocalDate date) {
        List<DiaryEntry> entries = repository.search(keyword == null ? "" : keyword.trim(), date);
        publisher.notifyEntriesChanged(entries);
        return entries;
    }
}

package com.digitaldiary.repository;

import com.digitaldiary.model.DiaryEntry;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository {
    DiaryEntry create(DiaryEntry entry);

    void update(DiaryEntry entry);

    void delete(int id);

    List<DiaryEntry> findAll();

    List<DiaryEntry> search(String keyword, LocalDate date);
}

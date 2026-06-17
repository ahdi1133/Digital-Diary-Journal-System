package com.digitaldiary.observer;

import com.digitaldiary.model.DiaryEntry;

import java.util.List;

public interface EntryChangeListener {
    void onEntriesChanged(List<DiaryEntry> entries);
}

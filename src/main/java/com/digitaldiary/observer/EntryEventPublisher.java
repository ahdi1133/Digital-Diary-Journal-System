package com.digitaldiary.observer;

import com.digitaldiary.model.DiaryEntry;

import java.util.ArrayList;
import java.util.List;

public class EntryEventPublisher {
    private final List<EntryChangeListener> listeners = new ArrayList<>();

    public void addListener(EntryChangeListener listener) {
        listeners.add(listener);
    }

    public void notifyEntriesChanged(List<DiaryEntry> entries) {
        List<DiaryEntry> snapshot = List.copyOf(entries);
        for (EntryChangeListener listener : listeners) {
            listener.onEntriesChanged(snapshot);
        }
    }
}

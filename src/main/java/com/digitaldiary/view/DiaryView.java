package com.digitaldiary.view;

import com.digitaldiary.controller.DiaryController;
import com.digitaldiary.model.DiaryEntry;
import com.digitaldiary.observer.EntryChangeListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DiaryView implements EntryChangeListener {
    private final DiaryController controller;
    private final JFrame frame = new JFrame("Digital Diary / Journal System");
    private final DefaultListModel<DiaryEntry> entryListModel = new DefaultListModel<>();
    private final JList<DiaryEntry> entryList = new JList<>(entryListModel);
    private final JTextField titleField = new JTextField();
    private final JTextField dateField = new JTextField(LocalDate.now().toString());
    private final JTextArea contentArea = new JTextArea();
    private final JTextField searchField = new JTextField(18);
    private final JTextField searchDateField = new JTextField(10);
    private DiaryEntry selectedEntry;

    public DiaryView(DiaryController controller) {
        this.controller = controller;
        buildLayout();
        wireEvents();
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    @Override
    public void onEntriesChanged(List<DiaryEntry> entries) {
        entryListModel.clear();
        for (DiaryEntry entry : entries) {
            entryListModel.addElement(entry);
        }
    }

    public static void showStartupError(Exception exception) {
        JOptionPane.showMessageDialog(null, exception.getMessage(), "Startup Error", JOptionPane.ERROR_MESSAGE);
    }

    private void buildLayout() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(960, 620));
        frame.setLocationRelativeTo(null);

        entryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(entryList);
        listScroll.setBorder(BorderFactory.createTitledBorder("Entries"));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchButton = new JButton("Search");
        JButton clearSearchButton = new JButton("View All");
        searchPanel.add(new JLabel("Keyword"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Date"));
        searchPanel.add(searchDateField);
        searchPanel.add(searchButton);
        searchPanel.add(clearSearchButton);
        searchButton.addActionListener(event -> searchEntries());
        clearSearchButton.addActionListener(event -> {
            searchField.setText("");
            searchDateField.setText("");
            controller.loadEntries();
        });

        JPanel leftPanel = new JPanel(new BorderLayout(6, 6));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        leftPanel.add(listScroll, BorderLayout.CENTER);

        JPanel editorPanel = buildEditorPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, editorPanel);
        splitPane.setResizeWeight(0.36);
        frame.add(splitPane, BorderLayout.CENTER);
        frame.pack();
    }

    private JPanel buildEditorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 4, 4, 4);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Title"), constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        panel.add(titleField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        panel.add(new JLabel("Date"), constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        panel.add(dateField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(contentArea), constraints);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton newButton = new JButton("New");
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(newButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);

        newButton.addActionListener(event -> clearEditor());
        saveButton.addActionListener(event -> saveEntry());
        deleteButton.addActionListener(event -> deleteEntry());

        constraints.gridy = 3;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, constraints);
        return panel;
    }

    private void wireEvents() {
        entryList.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                selectedEntry = entryList.getSelectedValue();
                populateEditor(selectedEntry);
            }
        });
    }

    private void populateEditor(DiaryEntry entry) {
        if (entry == null) {
            return;
        }
        titleField.setText(entry.getTitle());
        dateField.setText(entry.getEntryDate().toString());
        contentArea.setText(entry.getContent());
    }

    private void saveEntry() {
        try {
            LocalDate date = LocalDate.parse(dateField.getText().trim());
            if (selectedEntry == null) {
                controller.addEntry(titleField.getText(), date, contentArea.getText());
            } else {
                controller.updateEntry(selectedEntry.getId(), titleField.getText(), date, contentArea.getText());
            }
            clearEditor();
        } catch (IllegalArgumentException | DateTimeParseException exception) {
            showError("Please enter a title, content, and date in yyyy-MM-dd format.");
        }
    }

    private void deleteEntry() {
        if (selectedEntry == null) {
            showError("Select an entry to delete.");
            return;
        }
        int answer = JOptionPane.showConfirmDialog(frame, "Delete selected entry?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            controller.deleteEntry(selectedEntry.getId());
            clearEditor();
        }
    }

    private void searchEntries() {
        try {
            LocalDate date = searchDateField.getText().isBlank() ? null : LocalDate.parse(searchDateField.getText().trim());
            controller.search(searchField.getText(), date);
        } catch (DateTimeParseException exception) {
            showError("Search date must use yyyy-MM-dd format.");
        }
    }

    private void clearEditor() {
        selectedEntry = null;
        entryList.clearSelection();
        titleField.setText("");
        dateField.setText(LocalDate.now().toString());
        contentArea.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Digital Diary", JOptionPane.ERROR_MESSAGE);
    }
}

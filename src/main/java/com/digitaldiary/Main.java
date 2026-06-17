package com.digitaldiary;

import com.digitaldiary.controller.DiaryController;
import com.digitaldiary.database.DatabaseConnection;
import com.digitaldiary.repository.SQLiteDiaryRepository;
import com.digitaldiary.view.DiaryView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                DatabaseConnection databaseConnection = DatabaseConnection.getInstance(Path.of("data", "diary.db"));
                SQLiteDiaryRepository repository = new SQLiteDiaryRepository(databaseConnection);
                DiaryController controller = new DiaryController(repository);
                DiaryView view = new DiaryView(controller);
                controller.addEntryChangeListener(view);
                view.showWindow();
                controller.loadEntries();
            } catch (Exception exception) {
                DiaryView.showStartupError(exception);
            }
        });
    }
}

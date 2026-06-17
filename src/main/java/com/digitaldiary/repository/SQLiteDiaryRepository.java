package com.digitaldiary.repository;

import com.digitaldiary.database.DatabaseConnection;
import com.digitaldiary.model.DiaryEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDiaryRepository implements DiaryRepository {
    private final DatabaseConnection databaseConnection;

    public SQLiteDiaryRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        initializeSchema();
    }

    @Override
    public DiaryEntry create(DiaryEntry entry) {
        String sql = "INSERT INTO diary_entries(title, entry_date, content) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entry.getTitle());
            statement.setString(2, entry.getEntryDate().toString());
            statement.setString(3, entry.getContent());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return new DiaryEntry(generatedKeys.getInt(1), entry.getTitle(), entry.getEntryDate(), entry.getContent());
            }
            throw new IllegalStateException("Entry was saved but no generated ID was returned.");
        } catch (SQLException exception) {
            throw new RepositoryException("Unable to create diary entry.", exception);
        }
    }

    @Override
    public void update(DiaryEntry entry) {
        String sql = "UPDATE diary_entries SET title = ?, entry_date = ?, content = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try {
            PreparedStatement statement = connection().prepareStatement(sql);
            statement.setString(1, entry.getTitle());
            statement.setString(2, entry.getEntryDate().toString());
            statement.setString(3, entry.getContent());
            statement.setInt(4, entry.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) {
                throw new RepositoryException("No diary entry exists with ID " + entry.getId());
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Unable to update diary entry.", exception);
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection().prepareStatement("DELETE FROM diary_entries WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RepositoryException("Unable to delete diary entry.", exception);
        }
    }

    @Override
    public List<DiaryEntry> findAll() {
        String sql = "SELECT id, title, entry_date, content FROM diary_entries ORDER BY entry_date DESC, id DESC";
        try {
            PreparedStatement statement = connection().prepareStatement(sql);
            return mapEntries(statement.executeQuery());
        } catch (SQLException exception) {
            throw new RepositoryException("Unable to load diary entries.", exception);
        }
    }

    @Override
    public List<DiaryEntry> search(String keyword, LocalDate date) {
        StringBuilder sql = new StringBuilder("SELECT id, title, entry_date, content FROM diary_entries WHERE 1 = 1");
        List<Object> parameters = new ArrayList<>();

        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND (LOWER(title) LIKE ? OR LOWER(content) LIKE ?)");
            String pattern = "%" + keyword.toLowerCase() + "%";
            parameters.add(pattern);
            parameters.add(pattern);
        }

        if (date != null) {
            sql.append(" AND entry_date = ?");
            parameters.add(date.toString());
        }

        sql.append(" ORDER BY entry_date DESC, id DESC");

        try {
            PreparedStatement statement = connection().prepareStatement(sql.toString());
            for (int index = 0; index < parameters.size(); index++) {
                Object parameter = parameters.get(index);
                statement.setString(index + 1, parameter.toString());
            }
            return mapEntries(statement.executeQuery());
        } catch (SQLException exception) {
            throw new RepositoryException("Unable to search diary entries.", exception);
        }
    }

    private void initializeSchema() {
        String sql = """
                CREATE TABLE IF NOT EXISTS diary_entries (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    entry_date TEXT NOT NULL,
                    content TEXT NOT NULL,
                    created_at TEXT DEFAULT CURRENT_TIMESTAMP,
                    updated_at TEXT DEFAULT CURRENT_TIMESTAMP
                )
                """;
        try {
            Statement statement = connection().createStatement();
            statement.execute(sql);
        } catch (SQLException exception) {
            throw new RepositoryException("Unable to initialize database schema.", exception);
        }
    }

    private Connection connection() throws SQLException {
        return databaseConnection.getConnection();
    }

    private List<DiaryEntry> mapEntries(ResultSet resultSet) throws SQLException {
        List<DiaryEntry> entries = new ArrayList<>();
        while (resultSet.next()) {
            entries.add(new DiaryEntry(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    LocalDate.parse(resultSet.getString("entry_date")),
                    resultSet.getString("content")
            ));
        }
        return entries;
    }
}

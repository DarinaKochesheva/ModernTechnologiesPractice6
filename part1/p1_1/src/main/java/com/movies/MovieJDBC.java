package com.movies;

import java.sql.*;

/**
 * CRUD операции с фильмами через JDBC.
 * Учебное задание: JDBC - работа с базой данных
 */
public class MovieJDBC {

    private static final String DB_URL = "jdbc:h2:mem:moviedb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        MovieJDBC movieDb = new MovieJDBC();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("✅ Подключение к базе данных установлено!\n");

            movieDb.dropAndCreateTable(conn);

            // ▼ ВЫЗОВ insertMovies ▼
            movieDb.insertMovies(conn);
            // ▲

            System.out.println("=== Все фильмы ===");
            movieDb.printAllMovies(conn);

            // ▼ ВЫЗОВ findByTitle ▼
            System.out.println("\n=== Поиск по названию 'Матрица' ===");
            movieDb.findByTitle(conn, "Матрица");
            // ▲

            System.out.println("\n=== Поиск по году (1999) ===");
            movieDb.findByYear(conn, 1999);

            System.out.println("\n=== Поиск по жанру (sci-fi) ===");
            movieDb.findByGenre(conn, "sci-fi");

            System.out.println("\n=== Обновление фильма ===");
            movieDb.updateMovie(conn, 1, "Матрица (Обновлено)", "sci-fi", 1999);
            movieDb.printAllMovies(conn);

            System.out.println("\n=== Удаление фильма ===");
            movieDb.deleteMovie(conn, 2);
            movieDb.printAllMovies(conn);

        } catch (SQLException e) {
            System.err.println("❌ Ошибка базы данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertMovies(Connection conn) throws SQLException {
        String sql = "INSERT INTO movies (title, genre, \"year\") VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Object[][] movies = {
                    {"Матрица", "sci-fi", 1999},
                    {"Начало", "sci-fi", 2010},
                    {"Крестный отец", "crime", 1972},
                    {"Темный рыцарь", "action", 2008}
            };

            for (Object[] movie : movies) {
                pstmt.setString(1, (String) movie[0]);
                pstmt.setString(2, (String) movie[1]);
                pstmt.setInt(3, (Integer) movie[2]);
                pstmt.executeUpdate();
                System.out.println("Добавлен: " + movie[0]);
            }
        }
    }

    public void findByTitle(Connection conn, String title) throws SQLException {
        String sql = "SELECT * FROM movies WHERE LOWER(title) LIKE LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("ID: %d, Название: %s, Жанр: %s, Год: %d%n",
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("genre"),
                            rs.getInt("year"));
                }
            }
        }
    }

    public void dropAndCreateTable(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS movies");
            stmt.execute("""
                CREATE TABLE movies (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    title VARCHAR(255) NOT NULL,
                    genre VARCHAR(100),
                    "year" INT
                )
                """);
            System.out.println("✅ Таблица movies создана\n");
        }
    }

    public void printAllMovies(Connection conn) throws SQLException {
        String sql = "SELECT * FROM movies";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("ID: %d, Название: %s, Жанр: %s, Год: %d%n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getInt("year"));
            }
        }
    }

    public void updateMovie(Connection conn, int id, String title, String genre, int year) throws SQLException {
        String sql = "UPDATE movies SET title = ?, genre = ?, \"year\" = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, genre);
            pstmt.setInt(3, year);
            pstmt.setInt(4, id);

            int rows = pstmt.executeUpdate();
            System.out.println("Обновлено записей: " + rows);
        }
    }

    public void deleteMovie(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM movies WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println("Удалено записей: " + rows);
        }
    }

    public void findByYear(Connection conn, int year) throws SQLException {
        String sql = "SELECT * FROM movies WHERE \"year\" = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("ID: %d, Название: %s, Жанр: %s, Год: %d%n",
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("genre"),
                            rs.getInt("year"));
                }
            }
        }
    }

    public void findByGenre(Connection conn, String genre) throws SQLException {
        String sql = "SELECT * FROM movies WHERE genre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, genre);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("ID: %d, Название: %s, Жанр: %s, Год: %d%n",
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("genre"),
                            rs.getInt("year"));
                }
            }
        }
    }
}

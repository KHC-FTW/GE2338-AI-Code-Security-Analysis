package cityu.khchan744.dao;

import cityu.khchan744.model.Article;
import cityu.khchan744.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    public Article getArticleById(int id) throws SQLException {
        String sql = "SELECT a.*, u.username FROM articles a " +
                "LEFT JOIN users u ON a.user_id = u.id WHERE a.id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToArticle(rs);
            }
        }
        return null;
    }

    public List<Article> getAllArticles(int limit, int offset) throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.*, u.username FROM articles a " +
                "LEFT JOIN users u ON a.user_id = u.id " +
                "ORDER BY a.created_at DESC LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(mapRowToArticle(rs));
            }
        }
        return articles;
    }

    public List<Article> getArticlesByUser(int userId, int limit, int offset) throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.*, u.username FROM articles a " +
                "LEFT JOIN users u ON a.user_id = u.id " +
                "WHERE a.user_id = ? ORDER BY a.created_at DESC LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(mapRowToArticle(rs));
            }
        }
        return articles;
    }

    public List<Article> searchArticles(String query, int limit, int offset) throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.*, u.username FROM articles a " +
                "LEFT JOIN users u ON a.user_id = u.id " +
                "WHERE LOWER(a.title) LIKE LOWER(?) OR LOWER(a.content) LIKE LOWER(?) " +
                "ORDER BY a.created_at DESC LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + query + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(mapRowToArticle(rs));
            }
        }
        return articles;
    }

    public int insertArticle(Article article) throws SQLException {
        String sql = "INSERT INTO articles (user_id, title, content) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, article.getUserId());
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContent());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }

    public boolean updateArticle(Article article) throws SQLException {
        String sql = "UPDATE articles SET title = ?, content = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setInt(3, article.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteArticle(int id) throws SQLException {
        String sql = "DELETE FROM articles WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public int countAllArticles() throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM articles";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    public int countSearchResults(String query) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM articles WHERE LOWER(title) LIKE LOWER(?) OR LOWER(content) LIKE LOWER(?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + query + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    private Article mapRowToArticle(ResultSet rs) throws SQLException {
        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setUserId(rs.getInt("user_id"));
        article.setTitle(rs.getString("title"));
        article.setContent(rs.getString("content"));
        article.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        article.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        article.setAuthorUsername(rs.getString("username"));
        return article;
    }
}


package cityu.khchan744.gemini3flash.dao;

import cityu.khchan744.gemini3flash.db.DBConnection;
import cityu.khchan744.gemini3flash.model.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {
    public boolean createArticle(Article article) {
        String sql = "INSERT INTO articles (author_id, title, content) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, article.getAuthorId());
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContent());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Article> getAllArticles() {
        String sql = "SELECT a.*, u.username FROM articles a JOIN users u ON a.author_id = u.id ORDER BY a.created_at DESC";
        List<Article> articles = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                articles.add(mapArticle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public Article getArticleById(int id) {
        String sql = "SELECT a.*, u.username FROM articles a JOIN users u ON a.author_id = u.id WHERE a.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapArticle(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Article> searchArticles(String query) {
        String sql = "SELECT a.*, u.username FROM articles a JOIN users u ON a.author_id = u.id WHERE a.search_vector @@ plainto_tsquery('english', ?) ORDER BY ts_rank(a.search_vector, plainto_tsquery('english', ?)) DESC";
        List<Article> articles = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, query);
            ps.setString(2, query);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) articles.add(mapArticle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    private Article mapArticle(ResultSet rs) throws SQLException {
        Article a = new Article();
        a.setId(rs.getInt("id"));
        a.setAuthorId(rs.getInt("author_id"));
        a.setAuthorName(rs.getString("username"));
        a.setTitle(rs.getString("title"));
        a.setContent(rs.getString("content"));
        a.setCreatedAt(rs.getTimestamp("created_at"));
        a.setUpdatedAt(rs.getTimestamp("updated_at"));
        return a;
    }
}


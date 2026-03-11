package cityu.khchan744.dao;

import cityu.khchan744.model.Comment;
import cityu.khchan744.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    public Comment getCommentById(int id) throws SQLException {
        String sql = "SELECT c.*, u.username FROM comments c " +
                "LEFT JOIN users u ON c.user_id = u.id WHERE c.id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToComment(rs);
            }
        }
        return null;
    }

    public List<Comment> getCommentsByArticle(int articleId, int limit, int offset) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.*, u.username FROM comments c " +
                "LEFT JOIN users u ON c.user_id = u.id " +
                "WHERE c.article_id = ? ORDER BY c.created_at DESC LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comments.add(mapRowToComment(rs));
            }
        }
        return comments;
    }

    public int insertComment(Comment comment) throws SQLException {
        String sql = "INSERT INTO comments (article_id, user_id, content) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, comment.getArticleId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getContent());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }

    public boolean deleteComment(int id) throws SQLException {
        String sql = "DELETE FROM comments WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public int countCommentsByArticle(int articleId) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM comments WHERE article_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    private Comment mapRowToComment(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setArticleId(rs.getInt("article_id"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setContent(rs.getString("content"));
        comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        comment.setAuthorUsername(rs.getString("username"));
        return comment;
    }
}


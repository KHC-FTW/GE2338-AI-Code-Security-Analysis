package cityu.khchan744.blog.dao;

import cityu.khchan744.blog.model.Comment;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    public List<Comment> findByPostId(int postId) {
        String sql = "SELECT * FROM comments WHERE post_id = ? AND approved = true ORDER BY created_at ASC";
        List<Comment> out = new ArrayList<>();
        try (Connection c = DataSourceProvider.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public int create(Comment cm) {
        String sql = "INSERT INTO comments (post_id, author_name, author_email, content, approved) VALUES (?,?,?,?,?) RETURNING id";
        try (Connection c = DataSourceProvider.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cm.getPostId());
            ps.setString(2, cm.getAuthorName());
            ps.setString(3, cm.getAuthorEmail());
            ps.setString(4, cm.getContent());
            ps.setBoolean(5, cm.isApproved());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    cm.setId(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    private Comment mapRow(ResultSet rs) throws SQLException {
        Comment c = new Comment();
        c.setId(rs.getInt("id"));
        c.setPostId(rs.getInt("post_id"));
        c.setAuthorName(rs.getString("author_name"));
        c.setAuthorEmail(rs.getString("author_email"));
        c.setContent(rs.getString("content"));
        c.setApproved(rs.getBoolean("approved"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) c.setCreatedAt(OffsetDateTime.ofInstant(ts.toInstant(), ZoneOffset.UTC));
        return c;
    }
}


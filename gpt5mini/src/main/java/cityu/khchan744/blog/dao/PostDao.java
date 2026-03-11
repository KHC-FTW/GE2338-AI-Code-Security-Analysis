package cityu.khchan744.blog.dao;

import cityu.khchan744.blog.model.Post;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostDao {

    public PostDao() {
    }

    public List<Post> listPublished(int limit, int offset) {
        String sql = "SELECT * FROM posts WHERE published = true ORDER BY created_at DESC LIMIT ? OFFSET ?";
        List<Post> out = new ArrayList<>();
        try (Connection c = DataSourceProvider.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public Optional<Post> findBySlug(String slug) {
        String sql = "SELECT * FROM posts WHERE slug = ?";
        try (Connection c = DataSourceProvider.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, slug);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public int create(Post p) {
        String sql = "INSERT INTO posts (author_id, title, slug, content, summary, published) VALUES (?,?,?,?,?,?) RETURNING id";
        try (Connection c = DataSourceProvider.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, p.getAuthorId());
            ps.setString(2, p.getTitle());
            ps.setString(3, p.getSlug());
            ps.setString(4, p.getContent());
            ps.setString(5, p.getSummary());
            ps.setBoolean(6, p.isPublished());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    p.setId(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public java.util.List<Post> search(String q, int limit) {
        String sql = "SELECT * FROM posts WHERE published = true AND (title ILIKE ? OR content ILIKE ?) ORDER BY created_at DESC LIMIT ?";
        java.util.List<Post> out = new java.util.ArrayList<>();
        try (Connection c = DataSourceProvider.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            String like = "%" + q + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setInt(3, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    private Post mapRow(ResultSet rs) throws SQLException {
        Post p = new Post();
        p.setId(rs.getInt("id"));
        p.setAuthorId(rs.getInt("author_id"));
        p.setTitle(rs.getString("title"));
        p.setSlug(rs.getString("slug"));
        p.setContent(rs.getString("content"));
        p.setSummary(rs.getString("summary"));
        p.setPublished(rs.getBoolean("published"));
        Timestamp ct = rs.getTimestamp("created_at");
        if (ct != null) p.setCreatedAt(OffsetDateTime.ofInstant(ct.toInstant(), ZoneOffset.UTC));
        Timestamp ut = rs.getTimestamp("updated_at");
        if (ut != null) p.setUpdatedAt(OffsetDateTime.ofInstant(ut.toInstant(), ZoneOffset.UTC));
        return p;
    }
}

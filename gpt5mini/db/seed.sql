-- Minimal seed data (replace password hash)

-- Example admin user: replace PASSWORD_HASH with a BCrypt hash for 'admin123'
INSERT INTO users (username, email, password_hash, display_name, role)
VALUES ('admin', 'admin@example.com', '$2a$12$ReplaceWithRealHash', 'Administrator', 'ADMIN')
ON CONFLICT DO NOTHING;

-- Sample post (author id 1 assumed)
INSERT INTO posts (author_id, title, slug, content, summary, published)
VALUES (1, 'Welcome to the Blog', 'welcome-to-the-blog', 'This is the first post.', 'Intro post', true)
ON CONFLICT DO NOTHING;

-- Sample tag and mapping
INSERT INTO tags (name) VALUES ('announcement') ON CONFLICT DO NOTHING;
INSERT INTO post_tags (post_id, tag_id)
SELECT p.id, t.id FROM posts p, tags t WHERE p.slug='welcome-to-the-blog' AND t.name='announcement'
ON CONFLICT DO NOTHING;


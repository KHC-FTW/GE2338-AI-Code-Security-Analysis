# Quick Start Guide

Get your blog app running in 5 minutes!

## Step 1: Set Up PostgreSQL Database

```sql
-- Open PostgreSQL command line as admin
psql -U postgres

-- Create database
CREATE DATABASE blog_db;

-- Exit
\q

-- Import schema (run from project directory)
psql -U postgres -d blog_db -f database_schema.sql
```

## Step 2: Configure Database Connection

Edit: `src/main/java/cityu/khchan744/util/DatabaseUtil.java`

Line 14-16:
```java
config.setJdbcUrl("jdbc:postgresql://localhost:5432/blog_db");
config.setUsername("postgres");
config.setPassword("YOUR_PASSWORD");  // ← Change this!
```

## Step 3: Build with Maven

```bash
cd claudehaiku4.5
mvn clean package
```

Output: `target/claudehaiku4.5-1.0-SNAPSHOT.war`

## Step 4: Deploy to Tomcat

### Using IntelliJ IDEA (Easiest):
1. Right-click project → Run with Tomcat
2. Application opens at `http://localhost:8080/claudehaiku4.5/`

### Or Manual:
1. Copy WAR to: `C:\tomcat\webapps\`
2. Restart Tomcat
3. Visit: `http://localhost:8080/claudehaiku4.5/`

## Step 5: Test the App

1. Click **Register** → Create account
2. Click **Login** → Enter credentials
3. Click **New Article** → Write a blog post
4. Click **Articles** → See all posts
5. Click **Read More** → Leave a comment
6. Use **Search** → Find articles

## Architecture Overview

```
Browser (JSP Pages)
    ↓
Servlets (HTTP Handlers)
    ↓
DAOs (Database Access)
    ↓
PostgreSQL Database
```

## File Structure

- `pom.xml` - Dependencies
- `src/main/java/` - Backend code (Models, DAOs, Servlets)
- `src/main/webapp/` - Frontend (JSP pages, HTML)
- `database_schema.sql` - Database tables
- `README.md` - Full documentation

## Database Tables

```
users
├── id (primary key)
├── username (unique)
├── email (unique)
├── password_hash
├── first_name, last_name, bio
└── created_at, updated_at

articles
├── id (primary key)
├── user_id (foreign key → users)
├── title
├── content
└── created_at, updated_at

comments
├── id (primary key)
├── article_id (foreign key → articles)
├── user_id (foreign key → users)
├── content
└── created_at
```

## URLs & Routes

| URL | Purpose |
|-----|---------|
| `/claudehaiku4.5/` | Home page |
| `/claudehaiku4.5/auth?action=login` | Login page |
| `/claudehaiku4.5/auth?action=register` | Register page |
| `/claudehaiku4.5/auth?action=logout` | Logout |
| `/claudehaiku4.5/profile` | User profile |
| `/claudehaiku4.5/article?action=list` | All articles |
| `/claudehaiku4.5/article?action=create` | Create article |
| `/claudehaiku4.5/article?action=view&id=1` | View article |
| `/claudehaiku4.5/article?action=edit&id=1` | Edit article |
| `/claudehaiku4.5/search?q=keyword` | Search articles |
| `/claudehaiku4.5/comment` | Add/delete comments |

## Key Classes

### Models
- `User.java` - User entity with profile data
- `Article.java` - Blog article entity
- `Comment.java` - Comment on article

### DAOs (Database)
- `UserDAO.java` - User CRUD operations
- `ArticleDAO.java` - Article CRUD + search
- `CommentDAO.java` - Comment operations

### Servlets (Controllers)
- `AuthServlet.java` - Login, register, logout
- `ProfileServlet.java` - Profile management
- `ArticleServlet.java` - Article operations
- `CommentServlet.java` - Comment operations
- `SearchServlet.java` - Search functionality

### Utilities
- `DatabaseUtil.java` - Database connection pooling (HikariCP)
- `PasswordUtil.java` - Password hashing (BCrypt)

## Troubleshooting

### "Database connection failed"
```
✓ Check PostgreSQL is running
✓ Verify credentials in DatabaseUtil.java
✓ Verify blog_db database exists
✓ Check schema was imported: psql -U postgres -d blog_db -c "SELECT COUNT(*) FROM users;"
```

### "Tomcat won't start"
```
✓ Check port 8080 is free: netstat -ano | findstr :8080
✓ Check JAVA_HOME environment variable is set
✓ Check Tomcat logs: catalina.out
```

### "Page not found (404)"
```
✓ Verify WAR is deployed: Check tomcat/webapps/
✓ Check URL is correct: /claudehaiku4.5/ not /
✓ Restart Tomcat
```

## Development Tips

- **Hot reload**: Most IDEs support hot reload - no restart needed for JSP changes
- **Database debugging**: Use `psql` to query tables directly
- **Logging**: Check `catalina.out` for Tomcat error messages
- **Testing**: Create test account and articles to verify functionality

## Next Steps

Once running:
1. Register a user account
2. Create a blog article
3. View articles and add comments
4. Edit your profile
5. Try the search feature

## Need Help?

See full documentation in `README.md`

**Enjoy your new blog! 📝**


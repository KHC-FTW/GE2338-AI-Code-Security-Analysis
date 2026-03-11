# Blog Application - Setup & Deployment Guide

A full-featured blog web application built with Java Server Pages (JSP), Servlets, and PostgreSQL.

## Features

✅ **User Management**
- User registration with password hashing using BCrypt
- Secure login/logout with session management
- View and edit user profiles

✅ **Blog Articles**
- Create, read, update, and delete (CRUD) blog articles
- Article pagination
- Author information display

✅ **Search Functionality**
- Full-text search on article titles and content
- Search results pagination

✅ **Comments**
- Leave comments on articles
- Delete your own comments
- Comment pagination

✅ **Navigation**
- Responsive navigation bar with search
- Menu for all major features
- User authentication status display

## Project Structure

```
src/
├── main/
│   ├── java/cityu/khchan744/
│   │   ├── model/          # Data models (User, Article, Comment)
│   │   ├── dao/            # Database access objects
│   │   ├── servlet/        # HTTP request handlers
│   │   └── util/           # Utilities (DB, Password, Session)
│   └── webapp/
│       ├── pages/          # JSP pages
│       ├── WEB-INF/
│       │   └── web.xml     # Web application configuration
│       └── index.jsp       # Home page
database_schema.sql         # PostgreSQL database schema
pom.xml                     # Maven configuration
```

## Prerequisites

- **Java**: JDK 21 or later
- **Maven**: 3.6 or later
- **PostgreSQL**: 12 or later
- **Tomcat**: 9.0 or later (or any servlet container)

## Installation & Setup

### 1. Database Setup

#### Create PostgreSQL Database

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE blog_db;

# Connect to the new database
\c blog_db

# Exit psql
\q
```

#### Run Database Schema

```bash
# Using psql
psql -U postgres -d blog_db -f database_schema.sql

# Or manually copy and paste the SQL from database_schema.sql in your PostgreSQL client
```

### 2. Update Database Credentials

Edit `src/main/java/cityu/khchan744/util/DatabaseUtil.java`:

```java
config.setJdbcUrl("jdbc:postgresql://localhost:5432/blog_db");
config.setUsername("postgres");
config.setPassword("YOUR_PASSWORD");  // Change this to your PostgreSQL password
```

### 3. Build the Project

```bash
# Navigate to project directory
cd claudehaiku4.5

# Build with Maven
mvn clean package

# This creates a WAR file: target/claudehaiku4.5-1.0-SNAPSHOT.war
```

### 4. Deploy to Tomcat

#### Option A: Using Tomcat Manager (GUI)

1. Navigate to `http://localhost:8080/manager/html`
2. Login with your Tomcat credentials
3. Select the WAR file: `target/claudehaiku4.5-1.0-SNAPSHOT.war`
4. Click "Deploy"

#### Option B: Manual Deployment

1. Copy the WAR file to Tomcat's webapps directory:
   ```bash
   cp target/claudehaiku4.5-1.0-SNAPSHOT.war /path/to/tomcat/webapps/
   ```

2. Restart Tomcat:
   ```bash
   # Linux/Mac
   ./catalina.sh stop
   ./catalina.sh start
   
   # Windows
   catalina.bat stop
   catalina.bat start
   ```

#### Option C: IDE Integration (IntelliJ IDEA)

1. Open the project in IntelliJ IDEA
2. Go to **Run > Edit Configurations**
3. Click **+** to add new configuration
4. Select **Tomcat Server > Local**
5. Configure Tomcat home directory
6. Under "Deployment", add artifact `claudehaiku4.5:war`
7. Click **Run**

### 5. Access the Application

Open your browser and navigate to:
```
http://localhost:8080/claudehaiku4.5/
```

## Usage

### First Time Setup

1. **Register a new account**
   - Click "Register" in the navigation
   - Fill in username, email, and password
   - Click "Register"

2. **Login**
   - Click "Login" in the navigation
   - Enter your credentials
   - Click "Login"

3. **Create an Article**
   - Click "New Article" in the navigation
   - Enter title and content
   - Click "Publish Article"

4. **View & Comment**
   - Click "Articles" to see all articles
   - Click "Read More" on any article
   - Leave a comment at the bottom

5. **Search**
   - Use the search bar in the navigation
   - Enter keywords to find articles

6. **Edit Profile**
   - Click "Profile" in the navigation
   - Update your first name, last name, and bio
   - Click "Update Profile"

## Key Technologies

- **Backend Framework**: Java Servlets
- **View Layer**: JavaServer Pages (JSP) with JSTL
- **Database**: PostgreSQL with HikariCP connection pooling
- **Security**: BCrypt password hashing
- **Build Tool**: Maven
- **Server**: Apache Tomcat

## Dependencies

- `javax.servlet-api:4.0.1` - Servlet API
- `javax.servlet.jsp-api:2.3.3` - JSP API
- `jstl:1.2` - JSP Standard Tag Library
- `postgresql:42.7.1` - PostgreSQL JDBC driver
- `HikariCP:5.0.1` - Connection pooling
- `jbcrypt:0.4` - Password hashing

## File Descriptions

### Model Classes
- **User.java** - Represents a user with username, email, and profile info
- **Article.java** - Represents a blog article with title and content
- **Comment.java** - Represents a comment on an article

### DAO Classes
- **UserDAO.java** - Handles user database operations
- **ArticleDAO.java** - Handles article database operations (including search)
- **CommentDAO.java** - Handles comment database operations

### Servlet Classes
- **AuthServlet.java** - Handles login, registration, and logout
- **ProfileServlet.java** - Handles user profile view and editing
- **ArticleServlet.java** - Handles article CRUD operations
- **CommentServlet.java** - Handles comment creation and deletion
- **SearchServlet.java** - Handles article search

### JSP Pages
- **header.jsp** - Navigation bar and layout header
- **footer.jsp** - Page footer
- **index.jsp** - Home page with latest articles
- **login.jsp** - User login form
- **register.jsp** - User registration form
- **profile.jsp** - User profile view and edit
- **articles.jsp** - List of all articles with pagination
- **create-article.jsp** - Create new article form
- **edit-article.jsp** - Edit existing article form
- **view-article.jsp** - View single article with comments
- **search-results.jsp** - Display search results
- **error.jsp** - Error page

## Common Issues & Solutions

### 1. Database Connection Fails
- Verify PostgreSQL is running
- Check database credentials in `DatabaseUtil.java`
- Verify database `blog_db` exists
- Check firewall settings

### 2. JAR/Dependency Not Found
```bash
# Run Maven dependency resolution
mvn clean install

# Or force update dependencies
mvn clean install -U
```

### 3. Tomcat Not Starting
- Check if port 8080 is already in use
- Check Tomcat logs: `catalina.out`
- Verify JAVA_HOME is set correctly

### 4. JSP Pages Not Rendering
- Clear browser cache
- Restart Tomcat
- Check web.xml configuration

## Database Backup & Restore

### Backup
```bash
pg_dump -U postgres blog_db > blog_backup.sql
```

### Restore
```bash
psql -U postgres blog_db < blog_backup.sql
```

## Performance Tips

1. **Database**: The application uses HikariCP for connection pooling (10 max connections)
2. **Pagination**: Articles are paginated with 10 per page
3. **Search**: Uses SQL LIKE queries - consider full-text search for large databases
4. **Caching**: JSP pages are compiled and cached by Tomcat

## Security Considerations

✅ **Implemented**
- Password hashing with BCrypt (cost factor 12)
- SQL injection prevention with PreparedStatements
- Session-based authentication
- CRUD permission checks (users can only edit/delete their own content)

⚠️ **Additional Recommendations**
- Use HTTPS in production
- Implement CSRF tokens for forms
- Add rate limiting for login/registration
- Sanitize user input for XSS prevention
- Use environment variables for sensitive credentials

## Troubleshooting

### View Application Logs
```bash
# Tomcat logs
tail -f /path/to/tomcat/logs/catalina.out

# PostgreSQL logs (Linux)
tail -f /var/log/postgresql/postgresql.log
```

### Check Database Connection
```bash
# Test connection from command line
psql -h localhost -U postgres -d blog_db -c "SELECT version();"
```

## Future Enhancements

- [ ] Add user avatar/profile pictures
- [ ] Implement article categories/tags
- [ ] Add email notifications
- [ ] Implement article likes/reactions
- [ ] Add user follow functionality
- [ ] Implement nested comments (replies)
- [ ] Add rich text editor for articles
- [ ] Implement content moderation
- [ ] Add user roles and admin panel
- [ ] API endpoints for mobile app

## License

This project is provided as-is for educational purposes.

## Support

For issues or questions:
1. Check the troubleshooting section
2. Review database_schema.sql for table structure
3. Check Tomcat and PostgreSQL logs
4. Verify all configuration values are correct

---

**Happy Blogging! 📝**


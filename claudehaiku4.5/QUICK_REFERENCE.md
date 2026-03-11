# Blog Application - Quick Reference Card

## 🚀 Getting Started (30 seconds)

```bash
# 1. Create database
psql -U postgres -c "CREATE DATABASE blog_db;"

# 2. Import schema
psql -U postgres -d blog_db -f database_schema.sql

# 3. Update password in DatabaseUtil.java line 14-16

# 4. Build
mvn clean package

# 5. Deploy to Tomcat
# Copy target/claudehaiku4.5-1.0-SNAPSHOT.war to tomcat/webapps/

# 6. Access
# http://localhost:8080/claudehaiku4.5/
```

---

## 📁 Key Files (Most Important)

| File | Purpose | Edit? |
|------|---------|-------|
| `src/main/java/.../util/DatabaseUtil.java` | DB credentials | ✏️ YES |
| `pom.xml` | Dependencies | Usually NO |
| `database_schema.sql` | Database setup | Run once |
| `src/main/webapp/pages/header.jsp` | Site styling | ✏️ YES |
| All Java files | Backend logic | Usually NO |

---

## 🗃️ Database Quick Ref

```sql
-- Connect to database
psql -U postgres -d blog_db

-- See all users
SELECT id, username, email FROM users;

-- See all articles
SELECT id, title, user_id FROM articles;

-- See comments on article 1
SELECT * FROM comments WHERE article_id = 1;

-- Delete test user (id=1)
DELETE FROM users WHERE id = 1;

-- Reset database
psql -U postgres -d blog_db < database_schema.sql
```

---

## 🔗 URL Patterns

```
Home:                 /
Login:               /auth?action=login
Register:            /auth?action=register
Logout:              /auth?action=logout
Profile:             /profile
All Articles:        /article?action=list
Create Article:      /article?action=create
View Article:        /article?action=view&id=1
Edit Article:        /article?action=edit&id=1
Search:              /search?q=keyword
Comments:            /comment (POST only)
```

---

## 🛠️ Maven Commands

```bash
mvn clean              # Delete old build
mvn package            # Build WAR file
mvn clean package      # Clean + Build
mvn clean package -DskipTests  # Build without tests
mvn -X package         # Verbose output
```

---

## 📊 Database Tables (Quick View)

### users
```
id, username, email, password_hash, first_name, 
last_name, bio, created_at, updated_at
```

### articles
```
id, user_id, title, content, created_at, updated_at
```

### comments
```
id, article_id, user_id, content, created_at
```

---

## 🔐 User Workflow

1. **Register** → Create account → Auto-logged in
2. **Login** → Username + password → Session created
3. **Logout** → Session destroyed → Redirected home
4. **Create** → Write article → Auto-published
5. **Read** → Browse articles → View/comment
6. **Edit** → Only own articles
7. **Delete** → Only own articles/comments

---

## 🎨 Styling Quick Ref

**CSS Location**: `src/main/webapp/pages/header.jsp` (lines 25-280)

**Main Colors**:
- Primary Blue: `#3498db`
- Dark Gray: `#2c3e50`
- Light Gray: `#f5f5f5`
- Success Green: `#28a745`
- Danger Red: `#dc3545`

---

## 🐛 Common Issues & Fixes

| Problem | Solution |
|---------|----------|
| Database won't connect | Check DatabaseUtil.java password |
| Tomcat port in use | Change port 8080 to 8081 |
| Imports won't resolve | Run `mvn clean install` |
| JSP won't update | Clear browser cache + restart Tomcat |
| Can't create user | Username/email already exists |
| Password too weak | Minimum 6 characters required |

---

## 📂 Project Structure Tree

```
src/main/
├── java/cityu/khchan744/
│   ├── model/        (User.java, Article.java, Comment.java)
│   ├── dao/          (UserDAO.java, ArticleDAO.java, CommentDAO.java)
│   ├── servlet/      (AuthServlet.java, ArticleServlet.java, ...)
│   └── util/         (DatabaseUtil.java, PasswordUtil.java)
│
└── webapp/
    ├── index.jsp
    ├── WEB-INF/web.xml
    └── pages/        (All JSP files)
```

---

## 🔑 Important Code Snippets

### Connect to DB in Servlet
```java
Connection conn = DatabaseUtil.getConnection();
```

### Hash Password
```java
String hash = PasswordUtil.hashPassword("password123");
```

### Check Password
```java
boolean valid = PasswordUtil.checkPassword("password123", hash);
```

### Get User Session
```java
Integer userId = (Integer) session.getAttribute("userId");
```

### Query Database
```java
PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
ps.setInt(1, userId);
ResultSet rs = ps.executeQuery();
```

---

## 📝 File Editing Guide

### Modify Database Credentials
**File**: `src/main/java/cityu/khchan744/util/DatabaseUtil.java`
**Lines**: 14-16
```java
config.setJdbcUrl("jdbc:postgresql://localhost:5432/blog_db");
config.setUsername("postgres");
config.setPassword("YOUR_PASSWORD");  // ← Change this
```

### Change Site Colors
**File**: `src/main/webapp/pages/header.jsp`
**Search for**: `color: #3498db;` and replace with your color

### Add New Page
1. Create `new-page.jsp` in `src/main/webapp/pages/`
2. Include header: `<jsp:include page="header.jsp" />`
3. Include footer: `<jsp:include page="footer.jsp" />`
4. Add link in `header.jsp` navigation

---

## ✨ Features Summary

| Feature | Status | Location |
|---------|--------|----------|
| Menu Bar | ✅ | header.jsp |
| Search | ✅ | SearchServlet.java |
| Login/Register | ✅ | AuthServlet.java |
| Profile | ✅ | ProfileServlet.java |
| Create Article | ✅ | ArticleServlet.java |
| View Article | ✅ | view-article.jsp |
| Comments | ✅ | CommentServlet.java |
| Pagination | ✅ | All DAOs |
| Responsive | ✅ | header.jsp CSS |

---

## 🔗 External Links

- **PostgreSQL Docs**: https://www.postgresql.org/docs/
- **Tomcat Docs**: https://tomcat.apache.org/
- **Java Servlet Docs**: https://javaee.github.io/servlet-spec/
- **JSP Docs**: https://javaee.github.io/jsp-spec/
- **Maven Guide**: https://maven.apache.org/guides/

---

## 📞 Debugging Steps

1. **Check Logs**: `catalina.out` in Tomcat/logs/
2. **Check Database**: `psql -U postgres -d blog_db`
3. **Check Maven**: `mvn clean install -X`
4. **Check Browser**: F12 for network requests
5. **Check Config**: Verify DatabaseUtil.java credentials

---

## 🎯 Testing Checklist

- [ ] Can register new user
- [ ] Can login with credentials
- [ ] Can logout
- [ ] Can view profile
- [ ] Can edit profile
- [ ] Can create article
- [ ] Can view articles
- [ ] Can edit own article
- [ ] Can delete own article
- [ ] Can add comment
- [ ] Can delete own comment
- [ ] Can search articles
- [ ] Navigation works
- [ ] Pagination works

---

## 🚀 Deployment Checklist

- [ ] Update DatabaseUtil.java with prod credentials
- [ ] Build: `mvn clean package`
- [ ] Test locally first
- [ ] Create backup of database
- [ ] Copy WAR to Tomcat
- [ ] Restart Tomcat
- [ ] Test in production
- [ ] Monitor logs for errors

---

## 💡 Pro Tips

1. **Use `psql`** to directly query and debug database
2. **Look at `catalina.out`** first when something breaks
3. **Session attribute names** are case-sensitive
4. **JSP gets compiled** on first request - takes longer
5. **Maven** caches dependencies - use `mvn clean` if stuck
6. **Passwords** are irreversible once hashed (good!)
7. **Articles/comments** cascade delete with user
8. **SQL LIKE** searches are case-insensitive in PostgreSQL

---

## 📋 File Checklist

Java Files (16):
- ✅ User.java
- ✅ Article.java
- ✅ Comment.java
- ✅ UserDAO.java
- ✅ ArticleDAO.java
- ✅ CommentDAO.java
- ✅ AuthServlet.java
- ✅ ProfileServlet.java
- ✅ ArticleServlet.java
- ✅ CommentServlet.java
- ✅ SearchServlet.java
- ✅ DatabaseUtil.java
- ✅ PasswordUtil.java

JSP Files (14):
- ✅ index.jsp
- ✅ header.jsp
- ✅ footer.jsp
- ✅ login.jsp
- ✅ register.jsp
- ✅ profile.jsp
- ✅ articles.jsp
- ✅ create-article.jsp
- ✅ edit-article.jsp
- ✅ view-article.jsp
- ✅ search-results.jsp
- ✅ error.jsp
- ✅ articles-list.jsp

Config Files (6):
- ✅ pom.xml
- ✅ web.xml
- ✅ database_schema.sql

Documentation (5):
- ✅ README.md
- ✅ QUICKSTART.md
- ✅ SITEMAP.md
- ✅ CONFIGURATION.md
- ✅ IMPLEMENTATION_SUMMARY.md

**Total: 41 Files Created** ✅

---

**Everything is ready to go! Happy coding! 🚀**


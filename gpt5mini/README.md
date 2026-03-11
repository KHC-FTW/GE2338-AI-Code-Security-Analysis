# BlogApp (JSP + Tomcat + PostgreSQL)

Quick start (Windows PowerShell):

1. Ensure Java 17+ and Maven are installed and available on PATH.
2. Create PostgreSQL database and run migrations:

```powershell
# create database (you'll be prompted for password if required)
psql -U postgres -c "CREATE DATABASE blogdb;"
# run schema
psql -U postgres -d blogdb -f db/schema.sql
# run seed (update password hash in db/seed.sql if needed)
psql -U postgres -d blogdb -f db/seed.sql
```

3. Build WAR:

```powershell
mvn clean package -DskipTests
```

4. Deploy WAR to Tomcat (copy target/blogapp.war to Tomcat's webapps folder) and start Tomcat.

5. Open http://localhost:8080/blogapp/

Configuration: edit `src/main/resources/application.properties` for DB URL, user and password.


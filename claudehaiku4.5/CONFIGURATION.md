# Configuration Examples

## PostgreSQL Connection Examples

### Windows with PostgreSQL Default Install
```java
// In src/main/java/cityu/khchan744/util/DatabaseUtil.java
config.setJdbcUrl("jdbc:postgresql://localhost:5432/blog_db");
config.setUsername("postgres");
config.setPassword("your_password");
```

### Linux/Mac
```java
config.setJdbcUrl("jdbc:postgresql://localhost:5432/blog_db");
config.setUsername("postgres");
config.setPassword("your_password");
```

### Remote Server
```java
config.setJdbcUrl("jdbc:postgresql://192.168.1.100:5432/blog_db");
config.setUsername("postgres");
config.setPassword("your_password");
```

### Docker PostgreSQL
```java
config.setJdbcUrl("jdbc:postgresql://postgres-container:5432/blog_db");
config.setUsername("postgres");
config.setPassword("your_password");
```

## Tomcat Server Configuration

### IntelliJ IDEA - Tomcat Configuration

```
Run → Edit Configurations → New → Tomcat Server (Local)

Name: Tomcat 9.0
Tomcat Home: C:\apache-tomcat-9.0.xx (on Windows)
                or /usr/local/tomcat (on Linux)

Deployment: 
  Artifact: claudehaiku4.5:war
  Application context: /claudehaiku4.5

Server:
  Port: 8080
  JMX port: 1099

VM options:
  -Xmx512m -Xms256m
```

### Maven Build Configuration

```bash
# Development build (with verbose output)
mvn clean package -X

# Production build (optimized)
mvn clean package -DskipTests -q

# Skip tests
mvn clean package -DskipTests

# Rebuild without downloading dependencies
mvn clean package -o
```

### Tomcat Startup Configuration

#### Windows - setenv.bat
```batch
@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-21
set CATALINA_HOME=C:\apache-tomcat-9.0.xx
set CATALINA_OPTS=-Xms256m -Xmx512m
```

#### Linux/Mac - setenv.sh
```bash
#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export CATALINA_HOME=/usr/local/tomcat
export CATALINA_OPTS="-Xms256m -Xmx512m"
```

## Environment Variables (Optional)

### Using Environment Variables Instead of Hardcoding

**DatabaseUtil.java with Environment Variables:**
```java
public class DatabaseUtil {
    private static HikariDataSource dataSource;

    static {
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        // Fallback to defaults if not set
        if (dbUrl == null) dbUrl = "jdbc:postgresql://localhost:5432/blog_db";
        if (dbUser == null) dbUser = "postgres";
        if (dbPassword == null) dbPassword = "password";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
```

### Set Environment Variables

**Windows Command Prompt:**
```cmd
set DB_URL=jdbc:postgresql://localhost:5432/blog_db
set DB_USER=postgres
set DB_PASSWORD=your_password

# Then start Tomcat
catalina.bat start
```

**Linux/Mac:**
```bash
export DB_URL=jdbc:postgresql://localhost:5432/blog_db
export DB_USER=postgres
export DB_PASSWORD=your_password

# Then start Tomcat
./catalina.sh start
```

## Database Configuration Examples

### PostgreSQL Connection Pool Tuning

```java
// For small applications (1-5 concurrent users)
config.setMaximumPoolSize(5);
config.setMinimumIdle(2);

// For medium applications (5-50 concurrent users)
config.setMaximumPoolSize(10);
config.setMinimumIdle(5);

// For large applications (50+ concurrent users)
config.setMaximumPoolSize(20);
config.setMinimumIdle(10);
```

### Connection Timeout Settings

```java
// Connection acquisition timeout (default: 30000ms)
config.setConnectionTimeout(30000);  // 30 seconds

// Idle connection timeout (default: 600000ms)
config.setIdleTimeout(600000);  // 10 minutes

// Maximum lifetime of connection (default: 1800000ms)
config.setMaxLifetime(1800000);  // 30 minutes

// Leak detection threshold
config.setLeakDetectionThreshold(60000);  // 1 minute
```

## Maven Build Profiles

### pom.xml with Profiles for Different Environments

```xml
<profiles>
    <!-- Development Profile -->
    <profile>
        <id>dev</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <db.url>jdbc:postgresql://localhost:5432/blog_db</db.url>
            <db.user>postgres</db.user>
            <db.password>dev_password</db.password>
        </properties>
    </profile>

    <!-- Production Profile -->
    <profile>
        <id>prod</id>
        <properties>
            <db.url>jdbc:postgresql://prod-server:5432/blog_db</db.url>
            <db.user>postgres_prod</db.user>
            <db.password>prod_password</db.password>
        </properties>
    </profile>
</profiles>

<!-- Use profile: mvn clean package -Pprod -->
```

## Logging Configuration

### Log4j2 Configuration (Optional)

**pom.xml addition:**
```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.20.0</version>
</dependency>
```

**log4j2.xml:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <File name="File" fileName="logs/blog.log">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </File>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="File"/>
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

## Application.properties File (Optional Config)

**Create: src/main/resources/application.properties**
```properties
# Database Configuration
db.url=jdbc:postgresql://localhost:5432/blog_db
db.user=postgres
db.password=your_password
db.pool.size=10
db.pool.min=5

# App Configuration
app.name=Blog Application
app.version=1.0.0
app.timezone=UTC

# Session Configuration
session.timeout=1800

# Pagination
pagination.articles-per-page=10
pagination.comments-per-page=20
```

**Load in Java:**
```java
Properties props = new Properties();
try (InputStream input = DatabaseUtil.class.getClassLoader()
        .getResourceAsStream("application.properties")) {
    props.load(input);
    String dbUrl = props.getProperty("db.url");
    String dbUser = props.getProperty("db.user");
    String dbPassword = props.getProperty("db.password");
    // Use properties...
}
```

## Docker Configuration (Optional)

### Dockerfile
```dockerfile
FROM tomcat:9.0-jdk21

# Copy application
COPY target/claudehaiku4.5-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/claudehaiku4.5.war

# Set environment variables
ENV DB_URL=jdbc:postgresql://postgres:5432/blog_db
ENV DB_USER=postgres
ENV DB_PASSWORD=password

EXPOSE 8080

CMD ["catalina.sh", "run"]
```

### docker-compose.yml
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: blog_db
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./database_schema.sql:/docker-entrypoint-initdb.d/schema.sql

  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      DB_URL: jdbc:postgresql://postgres:5432/blog_db
      DB_USER: postgres
      DB_PASSWORD: password
    depends_on:
      - postgres

volumes:
  postgres_data:
```

**Run with Docker:**
```bash
docker-compose up -d
# Access at http://localhost:8080/claudehaiku4.5/
```

## HTTPS/SSL Configuration (Production)

### Tomcat server.xml Configuration

```xml
<Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
           maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
           clientAuth="false" sslProtocol="TLS"
           keystoreFile="/path/to/keystore.jks"
           keystorePass="keystore_password" />
```

### Generate Self-Signed Certificate (Testing)

```bash
keytool -genkey -alias tomcat -keyalg RSA -keystore keystore.jks -validity 365

# Then move keystore.jks to Tomcat directory
mv keystore.jks /path/to/tomcat/conf/
```

## Performance Tuning

### HikariCP Advanced Configuration

```java
HikariConfig config = new HikariConfig();

// Basic
config.setJdbcUrl("jdbc:postgresql://localhost:5432/blog_db");
config.setUsername("postgres");
config.setPassword("password");

// Pool sizing
config.setMaximumPoolSize(20);
config.setMinimumIdle(5);
config.setConnectionTimeout(30000);
config.setIdleTimeout(600000);
config.setMaxLifetime(1800000);

// Performance
config.setAutoCommit(true);
config.setLeakDetectionThreshold(60000);
config.setValidationTimeout(5000);
config.setKeepaliveTime(300000);

// PostgreSQL specific
config.setConnectionInitSql("SET APPLICATION_NAME='BlogApp'");

HikariDataSource dataSource = new HikariDataSource(config);
```

---

Choose the configuration that best fits your deployment environment and modify accordingly!


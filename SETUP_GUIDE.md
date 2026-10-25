# Vehicle Rental Management System — Setup Guide

## Default Admin Login
- **Email:** admin@vrms.com
- **Password:** admin123

---

## Step 1: Prerequisites (Install Once)

Install these if not already installed:
1. **Java 17+** → https://adoptium.net
2. **Maven** → https://maven.apache.org/download.cgi
3. **MySQL** → https://dev.mysql.com/downloads/mysql/
4. **VS Code** → https://code.visualstudio.com

Verify installs by running in Command Prompt:
```
java -version
mvn -version
mysql --version
```

---

## Step 2: Set Up the MySQL Database

Open MySQL Workbench or Command Prompt and run:
```sql
CREATE DATABASE IF NOT EXISTS vrms;
```

The app will auto-create tables on first run.

---

## Step 3: Configure Database Password

Open `src/main/resources/application.properties` and change:
```
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

---

## Step 4: Run the Application

Open VS Code, open the `D:\mahira\vehicle-rental` folder, then open terminal (Ctrl+`) and run:
```
mvn spring-boot:run
```

Wait for: **Started VehicleRentalApplication in X seconds**

Then open: **http://localhost:8080**

---

## Step 5: Test on Your Phone

1. Run `ipconfig` in Command Prompt
2. Find **IPv4 Address** (e.g. 192.168.1.5)
3. Open `http://192.168.1.5:8080` on your phone (same WiFi)

---

## Pages Available

| Page | URL | Who |
|------|-----|-----|
| Home | / | Everyone |
| Register | /register | New customers |
| Login | /login | All users |
| Browse Vehicles | /vehicles | All (book requires login) |
| My Bookings | /my-rentals | Logged-in customers |
| Admin Dashboard | /admin | Admin only |

---

## Admin Credentials (auto-created on first run)
- Email: admin@vrms.com
- Password: admin123

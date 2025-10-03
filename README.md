# RestAssured API Framework (with TestNG, MockServer, JSON Data, POJO, POM)

This project demonstrates REST API automation testing using:
- ✅ **RestAssured** for HTTP requests
- ✅ **TestNG** as the test framework
- ✅ **MockServer** as a mock backend service
- ✅ **Page Object Model (POM)** structure
- ✅ **External JSON data** and **POJO classes**
- ✅ **Maven** for build and dependency management

---

## 📁 Project Structure

│
├── pom.xml
├── README.md
│
├── src/
│ ├── test/
│ │ ├── java/
│ │ │ ├── api/
│ │ │ │ ├── actions/
│ │ │ │ │ └── UserActions.java # Handles API operations (GET, POST, PUT, DELETE)
│ │ │ │ ├── pojo/
│ │ │ │ │ └── User.java # POJO for user payload
│ │ │ │ ├── tests/
│ │ │ │ │ └── UserAPITest.java # TestNG test cases for user APIs
│ │ │ │ ├── mock/
│ │ │ │ │ └── MockServerExample.java # Sets up mock server expectations
│ │ ├── resources/
│ │ │ └── testdata/
│ │ │ └── validUser.json # Sample JSON test data


---

## 📦 Key Components

| Component            | Purpose                                                                 |
|---------------------|-------------------------------------------------------------------------|
| `UserActions.java`  | Contains RestAssured logic (POST, PUT, GET, DELETE, headers, payloads)  |
| `User.java`         | POJO mapped to the JSON payload structure                               |
| `UserAPITest.java`  | Contains TestNG test methods and assertions                             |
| `MockServerExample` | Sets up expectations and responses using MockServer                     |
| `validUser.json`    | External file used for reading test data                                |

---

## ▶️ How to Run the Tests

### 🧪 Run Using Command Line:
```First Time setup
git clone https://github.com/nikagrawal2000/RESTASSUREDAPIWithMock.git
cd RESTASSUREDAPIWithMock

```bash
mvn clean test

mvn -Dtest=UserAPITest test

## MockServer Expectations

| Condition                   | Response Code | Message                     |
| --------------------------- | ------------- | --------------------------- |
| Valid user data             | `201`         | "User created successfully" |
| Empty JSON `{}`             | `400`         | "Invalid input"             |
| Invalid email (missing `@`) | `422`         | "Invalid email"             |

📚 Dependencies (in pom.xml)
RestAssured
TestNG
MockServer
Jackson (for JSON to POJO)
SLF4J (optional logging)

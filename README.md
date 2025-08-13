# MacOS - Installation guide
1. Open a terminal window.
2. Make sure you have Git installed. If not, you can install it using Homebrew:
   ```bash
   brew install git
   ```
3. Install java 17
   ```bash
   brew install openjdk@17
   ```
4. Add Java 17 to your PATH:
   ```bash
   echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
   source ~/.zshrc
   ```
5. Install Gradle:
   ```bash
   brew install gradle
   ```
6. Install postgresql
   ```bash
   brew install postgresql
   ```
7. Start the PostgreSQL service:
   ```bash
   brew services start postgresql
   ```
   Create a database named `ingemark`(run each line separately):
   ```bash
   psql postgres
   CREATE DATABASE productsdb;
   CREATE USER myuser WITH PASSWORD 'mypassword';
   GRANT ALL PRIVILEGES ON DATABASE productsdb TO myuser;
   \q
   ```
   If you want to use a different database name, user or password, make sure to update the `application.properties`
8. Clone the repository:
   ```bash
   git clone https://github.com/karikiram/Ingemark.git
   ```
9. Open the project in your preferred IDE (e.g., IntelliJ IDEA).
10. Navigate to the project directory and open the project.
   - If you are using IntelliJ IDEA, you can do this by selecting "Open" from the welcome screen and choosing the project directory.
   - If you are using another IDE, follow the instructions for opening a Gradle project.
11. In IntelliJ IDEA, gradle should automatically import all dependencies but in case it doesn't. Open the `build.gradle` file and click on "Load Gradle Changes" if prompted.
   - Alternatively, you can run the following command in the terminal:
   ```bash
   ./gradlew build
   ```
   This will download the necessary dependencies and build the project. The whole project works out of the box.
   Make sure to have the PostgreSQL server running.
12. Run main class:
   - In IntelliJ IDEA, you can click run(Ingemark application) on configuration after gradle build and indexing finished, but alternatively. Right-click on the `Main` class and select "Run 'Main.main()'".
   - Spring application includes an embedded Tomcat server, so you don't need to deploy it to an external server.
14. Access the application:
   - You should see the Ingemark application running.
   - Use swagger to test the endpoints: `http://localhost:8080/swagger-ui/index.html`

# Windows - Installation guide
1. Download and install [Git for Windows](https://git-scm.com/download/win).
2. Download and install [Amazon Corretto 17](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html) (choose the Windows x64 MSI installer).
3. Set `JAVA_HOME` and update `PATH`:
   - Open "System Properties" > "Advanced" > "Environment Variables".
   - Add a new `JAVA_HOME` variable pointing to your Corretto install path (e.g., `C:\Program Files\Amazon Corretto\jdk17.0.x_x`).
   - Edit the `Path` variable and add `%JAVA_HOME%\bin`.
4. Download and install [PostgreSQL](https://www.postgresql.org/download/windows/):
   - Run the installer and follow the prompts.
5. Initialize and start PostgreSQL
   - Open `psql` (e.g., from `C:\Program Files\PostgreSQL\15\bin\psql.exe`) and connect as user `postgres` (you can just click enter until it asks for the password).
   - Create a database and user(run each line separately):
   ```sql
   CREATE DATABASE productsdb;
   CREATE USER myuser WITH PASSWORD 'mypassword';
   GRANT ALL PRIVILEGES ON DATABASE productsdb TO myuser;
   ```
   If you want to use a different database name, user or password, make sure to update the `application.properties`
6. Clone the repository:
   ```powershell
   git clone https://github.com/karikiram/Ingemark.git
   ```
7. Open the project in your preferred IDE (e.g., IntelliJ IDEA).
8. Navigate to the project directory and open the project.
   - If you are using IntelliJ IDEA, you can do this by selecting "Open" from the welcome screen and choosing the project directory.
   - If you are using another IDE, follow the instructions for opening a Gradle project.
9. In IntelliJ IDEA, gradle should automatically import all dependencies but in case it doesn't. Open the `build.gradle` file and click on "Load Gradle Changes" if prompted.
   - Alternatively, you can run the following command in the terminal:
   ```bash
   .\gradlew build
   ```
   This will download the necessary dependencies and build the project. The whole project works out of the box.
   Make sure to have the PostgreSQL server running(you can check in services if the postgre is running).
10. Run main class:
   - In IntelliJ IDEA, you can click run(Ingemark application) on configuration after gradle build and indexing finished, but alternatively. Right-click on the `Main` class and select "Run 'Main.main()'".
   - Spring application includes an embedded Tomcat server, so you don't need to deploy it to an external server.
11. Access the application:
   - You should see the Ingemark application running.
   - Use swagger to test the endpoints: `http://localhost:8080/swagger-ui/index.html`

# Additional Notes
- Be sure to have proper configuration on application.properties file. Configuration in guide is if all settings are default.
- If you want to change the database name, user or password, make sure to update the `application.properties` file accordingly.
- If you encounter any issues, check the console output for error messages and ensure that all services (PostgreSQL, etc.) are running correctly.
- If you want to run the application on a different port, you can change the `server.port` property in the `application.properties` file.
- Gradle download is not included in guide, because it is included with IntelliJ IDEA.

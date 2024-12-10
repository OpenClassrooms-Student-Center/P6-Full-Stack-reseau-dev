# MDD Project

This readme will help you set up and test the *MDD* project, which is a platform where users can subscribe to themes and view articles within each theme. This guide will cover the setup for both the front-end (Angular) and back-end (Java), as well as the database configuration.

## Prerequisites

Before you begin, make sure you have installed the following tools:

1. [Angular CLI 18.1.1](https://angular.io/cli)
2. [Node.js](https://nodejs.org/)
3. [Java 22](https://www.oracle.com/java/technologies/javase-jdk22-downloads.html)
4. [MySQL Server and MySQL Workbench](https://dev.mysql.com/downloads/)
5. [Maven](https://maven.apache.org/)

Configure environment variables for Maven to use the `mvn` command.

## Database Configuration

1. Using MySQL Workbench, create a new schema named **MDD**. Set it as the default schema.
2. Run the SQL script that you can find at the root of the repository: [script.sql](https://github.com/romain-25/Developpez-une-application-full-stack-complete/blob/final/MDD_project.sql).

The script will create the following:
- A user with the credentials:
    - **Email**: `romain@mdd.fr`
    - **Password**: `Pass@1234`
- Four themes: Java, Python, Angular, and C#.
- Three articles per theme.
- The user is subscribed to the **Java** and **Angular** themes.

## Get the Source Code

Clone the GitHub repository:
```sh
git clone https://github.com/romain-25/Developpez-une-application-full-stack-complete.git
```
## Back-end Configuration and Execution

1. Open the `application.properties` file.
2. Insert your MySQL Server login and password for the following environment variables:
    ```properties
    spring.datasource.username=<your_mysql_username>
    spring.datasource.password=<your_mysql_password>
    jwt.secret.key=<your_jwt_secret_key>
    ```
3. Navigate to the back-end folder:
    ```sh
    cd back
    ```
4. Run the back-end project using Maven:
    ```sh
    mvn spring-boot:run
    ```

## Front-end Configuration and Execution

1. Navigate to the front-end folder:
    ```sh
    cd front
    ```
2. Install the Angular project dependencies:
    ```sh
    npm install
    ```
3. Run the front-end project:
    ```sh
    ng serve
    ```

The Angular application will be accessible at: `http://localhost:4200`.

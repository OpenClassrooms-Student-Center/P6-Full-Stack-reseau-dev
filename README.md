# P6-Full-Stack-reseau-dev

## Front 

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.3.

Don't forget to install your node_modules before starting (`npm install`).

### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

### Where to start

As you may have seen if you already started the app, a simple home page containing a logo, a title and a button is available. If you take a look at its code (in the `home.component.html`) you will see that an external UI library is already configured in the project.

This library is `@angular/material`, it's one of the most famous in the angular ecosystem. As you can see on their docs (https://material.angular.io/), it contains a lot of highly customizable components that will help you design your interfaces quickly.

Note: I recommend to use material however it's not mandatory, if you prefer you can get ride of it.

Good luck!


## ️ Settings

### Step 1 - Prerequistes :

Make sure the following softs are installed

- Angular CLI version 14.1.0.
- Java  17
- Maven
- MySQL >= 8

### Step 2 - Database

- Start MySql
- Create the BDD by importing the SQL script located in ./resources/sql/script.sql
- Add in your properties :
  - spring.datasource.username: (username)
  - spring.datasource.password: (password)
  - spring.datasource.url : (url of database)

By default they are two accounts:

- login: devUser1@example.com
- password: Password1*

- login: devUser2@example.com
- password: Password1*

### Step 3 - Spring Security

For use JWT and create token, add in your properties :
jwt.secret= (secret code)

## Run Locally

### Instructions

1.  Fork this repo
2.  Clone the repo onto your computer
3.  Open a terminal window in the cloned project
4.  Run the following commands:

**Back :**

1.Install dependencies :

```bash
mvn clean install
```

3.Start the development mode:

```bash
mvn spring-boot:run 
```

**Front:**

1.Install dependencies :

```bash
npm install
```

3.Start application:

```bash
ng serve --proxy-config proxy.config.json
```

## Version

V 1.0.0

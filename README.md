# P6-Full-Stack-reseau-dev


## Front

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 17.3.6.

Don't forget to install your node_modules before starting (`npm install`).

### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.


## Back

Make sure you have a local instance of mysql80 running on port 3306

Setup your database username and password in the application.yml file with your MySql username and password strings.

For testing purposes, the database is emptied every time you run the app you dont have to run any sql script to build all tables, all is done by hibernate

    hibernate:
      ddl-auto: create-drop

One testing user is created on app startup with credentials :
Email:

    testEmail@test.com

Password:

    123

Setup your database username and password in the following environment variables

    MDD_DB_USER
    MDD_DB_PASS

Go to backend folder

> cd back

Build project
> mvn package

Run project
> mvn spring-boot:run


Project runs at
```localhost:9000```

Swagger ui available at ```http://localhost:9000/api/swagger-ui/index.html```

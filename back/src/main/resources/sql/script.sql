CREATE DATABASE ORION_DATABASE;
USE ORION_DATABASE;

CREATE TABLE `USERS` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE `THEMES` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    created_at DATETIME
);

CREATE TABLE `SUBSCRIPTIONS` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    theme_id INT,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (theme_id) REFERENCES themes(id)
);

CREATE TABLE `ARTICLES` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    theme_id INT,
    title VARCHAR(255),
    content TEXT,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (theme_id) REFERENCES themes(id)
);

CREATE TABLE `COMMENTS` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    article_id INT,
    user_id INT,
    content TEXT,
    created_at DATETIME,
    FOREIGN KEY (article_id) REFERENCES articles(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO USERS (username, email, password, created_at, updated_at)
VALUES
('devUser1', 'devuser1@example.com', 'password.123', NOW(), NOW()),
('devUser2', 'devuser2@example.com', 'password.123', NOW(), NOW());

INSERT INTO THEMES (name, description, created_at)
VALUES
('Développement Frontend', 'Tout sur les technologies frontend.', NOW()),
('Développement Backend', 'Technologies côté serveur et bases de données.', NOW());

INSERT INTO SUBSCRIPTIONS (user_id, theme_id, created_at)
VALUES
(1, 1, NOW()),
(1, 2, NOW()),
(2, 2, NOW());

INSERT INTO ARTICLES (user_id, theme_id, title, content, created_at)
VALUES
(1, 1, 'Comprendre React', 'Contenu sur React...', NOW()),
(2, 2, 'Explorer Node.js', 'Contenu sur Node.js...', NOW());

INSERT INTO COMMENTS (article_id, user_id, content, created_at)
VALUES
(1, 2, 'Super article sur React !', NOW()),
(2, 1, 'Merci pour ces éclaircissements sur Node.js', NOW());

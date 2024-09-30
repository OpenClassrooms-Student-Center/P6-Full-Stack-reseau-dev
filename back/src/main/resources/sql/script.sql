CREATE DATABASE ORION_DATABASE;
USE ORION_DATABASE;
-- Création de la table User
CREATE TABLE User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Création de la table Themes
CREATE TABLE Themes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Création de la table Article
CREATE TABLE Article (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(5000) NOT NULL,
    author_id BIGINT NOT NULL,
    theme_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES User(id) ON DELETE CASCADE,
    FOREIGN KEY (theme_id) REFERENCES Themes(id) ON DELETE CASCADE
);

-- Création de la table Messages
CREATE TABLE Messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Article_id BIGINT,
    users_id BIGINT NOT NULL,
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (Article_id) REFERENCES Article(id) ON DELETE SET NULL,
    FOREIGN KEY (users_id) REFERENCES User(id) ON DELETE CASCADE
);

-- Insertion d'exemples dans la table User
INSERT INTO User (email, username, password, created_at, updated_at)
VALUES
('devUser1@example.com', 'devUser1', '$2a$10$bmSphCve7WxxjaXR0y.pu.NSc1PWDxC9PoCS6H/vZwXdRodkYKa9q', NOW(), NOW()),
('devUser2@example.com', 'devUser2', '$2a$10$bmSphCve7WxxjaXR0y.pu.NSc1PWDxC9PoCS6H/vZwXdRodkYKa9q', NOW(), NOW());

-- Insertion d'exemples dans la table Themes
INSERT INTO Themes (title, description, created_at, updated_at)
VALUES
('Développement Frontend', 'Apprenez les technologies de développement frontend.', NOW(), NOW()),
('Développement Backend', 'Comprendre les technologies côté serveur et les bases de données.', NOW(), NOW());

-- Insertion d'exemples dans la table Article
INSERT INTO Article (title, description, author_id, theme_id, created_at, updated_at)
VALUES
('Comprendre React', 'Un article approfondi sur React et ses fonctionnalités.', 1, 1, NOW(), NOW()),
('Introduction à Node.js', 'Découvrez comment Node.js fonctionne et ses cas d\'utilisation.', 2, 2, NOW(), NOW());

-- Insertion d'exemples dans la table Messages
INSERT INTO Messages (Article_id, users_id, message, created_at, updated_at)
VALUES
(1, 2, 'Super article sur React !', NOW(), NOW()),
(2, 1, 'Merci pour ces informations sur Node.js.', NOW(), NOW());

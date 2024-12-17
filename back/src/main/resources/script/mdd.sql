-- Création de la base de données
CREATE DATABASE IF NOT EXISTS mdd;
USE mdd;

-- Table des utilisateurs (users)
CREATE TABLE users (
                       id int AUTO_INCREMENT PRIMARY KEY,
                       username varchar(50) NOT NULL UNIQUE,
                       email varchar(100) NOT NULL UNIQUE,
                       password varchar(255) NOT NULL,
                       created_at timestamp default CURRENT_TIMESTAMP,
                       updated_at timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table des thèmes/sujets (subjects)
CREATE TABLE subjects (
                          id int AUTO_INCREMENT PRIMARY KEY,
                          name varchar(100) not null unique ,
                          description TEXT,
                          created_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Table des abonnements (subscriptions)
CREATE TABLE subscriptions (
                               id int AUTO_INCREMENT PRIMARY KEY,
                               user_id int NOT NULL,
                               subject_id int NOT NULL,
                               created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                               FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
                               UNIQUE (user_id, subject_id) -- Empêche les doublons d'abonnement
);

-- Table des articles/posts
CREATE TABLE posts (
                       id int AUTO_INCREMENT PRIMARY KEY,
                       user_id int NOT NULL,
                       subject_id int NOT NULL,
                       title varchar(255) NOT NULL,
                       content TEXT NOT NULL,
                       created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                       FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- Table des commentaires (comments)
CREATE TABLE comments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          post_id int NOT NULL,
                          user_id int NOT NULL,
                          content TEXT NOT NULL,
                          created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Ajout de quelques données de test
INSERT INTO users (username, email, password) VALUES
                                                  ('dev1', 'dev1@example.com', 'Password123!'),
                                                  ('dev2', 'dev2@example.com', 'Password456!');

INSERT INTO subjects (name, description) VALUES
                                             ('Java', 'Articles autour du langage Java'),
                                             ('Angular', 'Articles autour d\'Angular'),
('Spring Boot', 'Développement d\'API avec Spring Boot');

INSERT INTO subscriptions (user_id, subject_id) VALUES
                                                    (1, 1),
                                                    (1, 2),
                                                    (2, 2);

INSERT INTO posts (user_id, subject_id, title, content) VALUES
                                                            (1, 1, 'Découvrir Java', 'Un article pour les débutants en Java.'),
                                                            (1, 2, 'Introduction à Angular', 'Un guide simple pour Angular.');

INSERT INTO comments (post_id, user_id, content) VALUES
                                                     (1, 2, 'Merci pour cet article très utile !'),
                                                     (2, 1, 'Super guide, clair et concis.');

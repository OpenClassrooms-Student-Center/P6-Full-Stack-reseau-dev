
-- Create table for Users
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Create table for Themes
CREATE TABLE IF NOT EXISTS themes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    content TEXT NOT NULL
);

-- Create table for Articles
CREATE TABLE IF NOT EXISTS articles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    theme_id INT,
);

-- Create table for Subscriptions (many-to-many relationship between users and themes)
CREATE TABLE IF NOT EXISTS subscriptions (
    user_id INT,
    theme_id INT,
    PRIMARY KEY (user_id, theme_id),
);

-- Insert the user with id 1
INSERT INTO users (id, username, email, password) VALUES
(1, 'romain', 'romain@mdd.fr', '$2b$12$dJwZlfkrOJXaAWRXPNXMP.n2GcdZY9KgEJT.TIbu7RoYq/ciahG4q');

-- Insert themes
INSERT INTO themes (name, content) VALUES
('Java', 'Lorem ipsum dolor sit amet, Java theme content.'),
('Python', 'Lorem ipsum dolor sit amet, Python theme content.'),
('Angular', 'Lorem ipsum dolor sit amet, Angular theme content.'),
('C sharp', 'Lorem ipsum dolor sit amet, C sharp theme content.');

-- Insert articles for each theme
INSERT INTO articles (title, content, theme_id) VALUES
('Java Article 1', 'Lorem ipsum dolor sit amet, article content 1.', (SELECT id FROM themes WHERE name = 'Java')),
('Java Article 2', 'Lorem ipsum dolor sit amet, article content 2.', (SELECT id FROM themes WHERE name = 'Java')),
('Java Article 3', 'Lorem ipsum dolor sit amet, article content 3.', (SELECT id FROM themes WHERE name = 'Java')),
('Python Article 1', 'Lorem ipsum dolor sit amet, article content 1.', (SELECT id FROM themes WHERE name = 'Python')),
('Python Article 2', 'Lorem ipsum dolor sit amet, article content 2.', (SELECT id FROM themes WHERE name = 'Python')),
('Python Article 3', 'Lorem ipsum dolor sit amet, article content 3.', (SELECT id FROM themes WHERE name = 'Python')),
('Angular Article 1', 'Lorem ipsum dolor sit amet, article content 1.', (SELECT id FROM themes WHERE name = 'Angular')),
('Angular Article 2', 'Lorem ipsum dolor sit amet, article content 2.', (SELECT id FROM themes WHERE name = 'Angular')),
('Angular Article 3', 'Lorem ipsum dolor sit amet, article content 3.', (SELECT id FROM themes WHERE name = 'Angular')),
('C sharp Article 1', 'Lorem ipsum dolor sit amet, article content 1.', (SELECT id FROM themes WHERE name = 'C sharp')),
('C sharp Article 2', 'Lorem ipsum dolor sit amet, article content 2.', (SELECT id FROM themes WHERE name = 'C sharp')),
('C sharp Article 3', 'Lorem ipsum dolor sit amet, article content 3.', (SELECT id FROM themes WHERE name = 'C sharp'));

-- Subscribe the user with id 1 to the Java and Angular themes
INSERT INTO subscriptions (user_id, theme_id) VALUES
(1, (SELECT id FROM themes WHERE name = 'Java')),
(1, (SELECT id FROM themes WHERE name = 'Angular'));

INSERT INTO users (login, password)
VALUES (?, ?) RETURNING id;
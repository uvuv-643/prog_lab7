INSERT INTO lab7_users (login, password)
VALUES (?, ?) RETURNING id;
INSERT INTO users (username, password, first_name, last_name, email) VALUES
('user', '100', 's', 's', 's@gmail.com'),
('admin', '100', 'a', 'a', 'a@gmail.com');

INSERT INTO users_roles (user_id, role_id)  VALUES
(2, 1), (2, 2), (1, 2);
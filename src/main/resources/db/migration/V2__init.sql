DROP TABLE users CASCADE ;
CREATE TABLE users (
    id serial NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE roles (
    id serial NOT NULL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users_roles (
    user_id int NOT NULL,
    role_id int NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT FK_USER_ID_01 FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ROLE_ID_01 FOREIGN KEY (role_id)
    REFERENCES roles(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO roles (name) VALUES
('ROLE_USER'), ('ROLE_ADMIN');


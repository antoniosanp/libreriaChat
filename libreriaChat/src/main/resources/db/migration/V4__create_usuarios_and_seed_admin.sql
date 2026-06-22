-- V4: Tabla de usuarios y usuario administrador inicial

CREATE TABLE usuarios (
    id       BIGSERIAL    PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol      VARCHAR(50)  NOT NULL
);

-- password: Admin1234!  |  BCrypt $2b$10, 10 rounds (compatible con BCryptPasswordEncoder de Spring Security)
INSERT INTO usuarios (email, password, rol)
VALUES (
    'admin@example.com',
    '$2b$10$PlyHvp//1RhcB.sSLkGMdudhjNSG.oN5RbWt83ePpmNQ0OXKBVZgy',
    'ADMIN'
);

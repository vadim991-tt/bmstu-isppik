-- src/main/resources/db/changelog/db.changelog-1.1.sql

-- liquibase formatted sql

-- changeset PanchenkoVA:InsertInitialRoles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

-- changeset PanchenkoVA:InsertAdminUser
INSERT INTO users (username, password, enabled)
VALUES ('admin', 'admin', TRUE);

-- changeset PanchenkoVA:InsertAdminUserRole
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);


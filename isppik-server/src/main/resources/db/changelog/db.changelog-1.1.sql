-- src/main/resources/db/changelog/db.changelog-1.1.sql

-- liquibase formatted sql

-- changeset PanchenkoVA:InsertInitialRoles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

-- changeset PanchenkoVA:InsertAdminUser
INSERT INTO users (username, password_hash)
VALUES ('admin', '$2a$10$dW44aFzl4nJ7bPEpEbXYtOrwpshS1R7PSzvFKS9fnaf8T3LKijO2e');


-- changeset PanchenkoVA:InsertAdminUserRole
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);


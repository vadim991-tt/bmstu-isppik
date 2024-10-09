-- src/main/resources/db/changelog/db.changelog-1.1.sql

-- liquibase formatted sql

-- changeset YourName:InsertInitialRoles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

-- changeset YourName:InsertAdminUser
INSERT INTO users (id, username, password, enabled) 
VALUES (1, 'admin', '${ADMIN_PASSWORD}', TRUE);

-- changeset YourName:InsertAdminUserRole
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);

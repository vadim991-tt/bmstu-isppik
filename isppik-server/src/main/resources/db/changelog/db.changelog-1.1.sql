-- src/main/resources/db/changelog/db.changelog-1.1.sql

-- liquibase formatted sql

-- changeset PanchenkoVA:InsertInitialRoles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

-- changeset PanchenkoVA:InsertAdminUser
INSERT INTO users (id, username, password, enabled) 
VALUES (1, 'admin', 'admin', TRUE);

-- changeset PanchenkoVA:InsertAdminUserRole
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);

-- changeset PanchenkoVA:InsertOauthClient
INSERT INTO oauth_clients (client_id, client_secret, scopes, grant_types)
VALUES (
    'client-id',
    '$2a$10$5jWwZbqfoVCvn7cm7GrjI.fKLpmcI06i45QZJyCiLpBr97qAA2U5e',
    'read;write',
    'password;refresh_token'
);

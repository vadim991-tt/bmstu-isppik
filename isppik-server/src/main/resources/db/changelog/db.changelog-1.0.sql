-- src/main/resources/db/changelog/db.changelog-1.0.sql

-- liquibase formatted sql

-- changeset PanchenkoVA:CreateUsersTable
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,  -- BIGSERIAL для автоматической генерации ID
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- changeset PanchenkoVA:CreateRolesTable
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,  -- BIGSERIAL для автоматической генерации ID
    name VARCHAR(50) NOT NULL UNIQUE
);

-- changeset PanchenkoVA:CreateUsersRolesTable
CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- changeset PanchenkoVA:CreateNewsItemsTable
CREATE TABLE news_items (
    id BIGSERIAL PRIMARY KEY,  -- BIGSERIAL для автоматической генерации ID
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    link VARCHAR(255) NOT NULL,
    published_date TIMESTAMP NOT NULL,
    source_id BIGINT
);

-- changeset PanchenkoVA:CreateSourcesTable
CREATE TABLE sources (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    type VARCHAR(50)
);


-- changeset PanchenkoVA:CreateSubscriptionsTable
CREATE TABLE subscriptions (
    user_id BIGINT NOT NULL,
    source_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, source_id),
    CONSTRAINT fk_subscriptions_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_subscriptions_source FOREIGN KEY (source_id) REFERENCES sources(id)
);

-- changeset PanchenkoVA:CreateUserNewsInteractionsTable
CREATE TABLE user_news_interactions (
    user_id BIGINT NOT NULL,
    news_id BIGINT NOT NULL,
    action VARCHAR(20) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    PRIMARY KEY (user_id, news_id),
    CONSTRAINT fk_user_news_interactions_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_news_interactions_news FOREIGN KEY (news_id) REFERENCES news_items(id)
);

-- liquibase formatted sql

-- changeset Lucas:1787762072536-1
CREATE TABLE appointments (id BIGINT AUTO_INCREMENT NOT NULL, customer_id BIGINT NOT NULL, barber_id BIGINT NOT NULL, service_id BIGINT NOT NULL, appointment_time datetime NOT NULL, appointment_status TINYINT(3) NOT NULL, notes VARCHAR(500) NULL, CONSTRAINT PK_APPOINTMENTS PRIMARY KEY (id));

-- changeset Lucas:1787762072536-2
CREATE TABLE refresh_token (id BIGINT AUTO_INCREMENT NOT NULL, user_id BIGINT NOT NULL, token VARCHAR(255) NOT NULL, expiry_date timestamp NOT NULL, CONSTRAINT PK_REFRESH_TOKEN PRIMARY KEY (id), UNIQUE (token));

-- changeset Lucas:1787762072536-3
CREATE TABLE shop_services (id BIGINT AUTO_INCREMENT NOT NULL, service_name VARCHAR(100) NOT NULL, service_description VARCHAR(500) NOT NULL, duration_minutes INT NOT NULL, price DECIMAL(5, 2) NOT NULL, active BIT(1) NOT NULL, CONSTRAINT PK_SHOP_SERVICES PRIMARY KEY (id));

-- changeset Lucas:1787762072536-4
CREATE TABLE users (id BIGINT AUTO_INCREMENT NOT NULL, first_name VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, ddd VARCHAR(255) NOT NULL, phone_number VARCHAR(255) NOT NULL, user_role TINYINT(3) NOT NULL, active BIT(1) NOT NULL, CONSTRAINT PK_USERS PRIMARY KEY (id), UNIQUE (email));

-- changeset Lucas:1787762072536-5
CREATE INDEX barber_id ON appointments(barber_id);

-- changeset Lucas:1787762072536-6
CREATE INDEX customer_id ON appointments(customer_id);

-- changeset Lucas:1787762072536-7
CREATE INDEX service_id ON appointments(service_id);

-- changeset Lucas:1787762072536-8
CREATE INDEX user_id ON refresh_token(user_id);

-- changeset Lucas:1787762072536-9
ALTER TABLE appointments ADD CONSTRAINT appointments_ibfk_1 FOREIGN KEY (customer_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset Lucas:1787762072536-10
ALTER TABLE appointments ADD CONSTRAINT appointments_ibfk_2 FOREIGN KEY (barber_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset Lucas:1787762072536-11
ALTER TABLE appointments ADD CONSTRAINT appointments_ibfk_3 FOREIGN KEY (service_id) REFERENCES shop_services (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset Lucas:1787762072536-12
ALTER TABLE refresh_token ADD CONSTRAINT refresh_token_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;


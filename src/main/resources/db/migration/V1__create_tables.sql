CREATE TABLE IF NOT EXISTS picture_tool (
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS tool_tool (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    price BIGINT NOT NULL CHECK ( price > 0 ),
    picture_id BIGINT NOT NULL UNIQUE ,
    FOREIGN KEY (picture_id) REFERENCES picture_tool(id),
    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS user_tool (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER'
);

CREATE TABLE IF NOT EXISTS courier_tool (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE ,
    delivers INT,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_COURIER'
);

CREATE TABLE IF NOT EXISTS order_tool (
    id BIGSERIAL PRIMARY KEY,
    tool_id BIGINT NOT NULL,
    FOREIGN KEY (tool_id) REFERENCES tool_tool(id),
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_tool(id),
    full_price BIGINT,
    start_date DATE,
    end_date DATE,
    address VARCHAR(255) NOT NULL ,
    status VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS delivery_tool (
    id BIGSERIAL PRIMARY KEY,
    courier_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL UNIQUE,
    phone VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_courier VARCHAR(50) NOT NULL ,
    status VARCHAR(20),
    FOREIGN KEY (courier_id) REFERENCES courier_tool(id),
    FOREIGN KEY (order_id) REFERENCES order_tool(id) ON DELETE CASCADE
);

INSERT INTO user_tool (login, first_name, last_name, phone, email, password, role)
VALUES ('admin', 'Admin', 'Adminovich', '+7(800)555-35-35','admin@mail.ru',
        'admin', 'ROLE_ADMIN');
INSERT INTO user_tool (login, first_name, last_name, phone, email, password, role)
VALUES ('superadmin', 'SAdmin', 'SAdminovich', '+7(888)555-35-35',
        'superadmin@mail.ru', 'superadmin', 'ROLE_SUPERADMIN');
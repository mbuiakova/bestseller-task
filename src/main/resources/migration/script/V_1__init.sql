CREATE TABLE DRINK (
    id UUID,
    name varchar,
    price decimal(5, 2),
    PRIMARY KEY (id)
);

CREATE TABLE TOPPING (
    id uuid,
    name varchar,
    price decimal(5, 2),
    PRIMARY KEY (id)
);

CREATE TABLE ORDERS (
    id uuid,
    sum decimal(5, 2),
    PRIMARY KEY (id)
);

CREATE TABLE ORDER_LINES(
    id uuid,
    order_id uuid,
    drink_id uuid,
    topping_id uuid,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES ORDERS(id),
    FOREIGN KEY (drink_id) REFERENCES DRINK(id),
    FOREIGN KEY (topping_id) REFERENCES TOPPING(id)
);

CREATE TABLE ADMIN(
    id uuid,
    name varchar,
    PRIMARY KEY (id)
);

-- Filling with data.
INSERT INTO DRINK(id, name, price) VALUES
   (gen_random_uuid(), 'Black Coffee', 4),
   (gen_random_uuid(), 'Latte', 5),
   (gen_random_uuid(), 'Mocha', 6),
   (gen_random_uuid(), 'Tea', 3);


INSERT INTO TOPPING(id, name, price) VALUES
                                       (gen_random_uuid(), 'Milk', 2),
                                       (gen_random_uuid(), 'Hazelnut syrup', 3),
                                       (gen_random_uuid(), 'Chocolate sauce', 5),
                                       (gen_random_uuid(), 'Lemon', 2);
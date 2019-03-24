SET search_path = farmer;

--product table
CREATE TABLE product (
    product_key serial NOT NULL,
    name text,
    description text,
    ts timestamp with time zone
);



--farmer table
CREATE TABLE farmer (
    farmer_key serial NOT NULL,
    name text,
    location text,
    ts timestamp with time zone
);


--transportation table
CREATE TABLE transportation (
    transportation_key int NOT NULL,
    name text,
    location text,
    ts timestamp with time zone
);

--product_transportation table
CREATE TABLE product_transportation (
    product_transportation_key serial NOT NULL,
    product_key int,
    transportation_key int
);

--storage table
CREATE TABLE storage (
    storage_key serial NOT NULL,
    name text,
    max_capacity int,
    product_key int,
    product_amount int
);


ALTER TABLE farmer ADD CONSTRAINT farmer_pkey PRIMARY KEY (farmer_key);
ALTER TABLE transportation ADD CONSTRAINT transportation_pkey PRIMARY KEY (transportation_key);
ALTER TABLE product ADD CONSTRAINT product_pkey PRIMARY KEY (product_key);
ALTER TABLE product_transportation ADD CONSTRAINT product_transportation_pkey PRIMARY KEY (product_transportation_key);
ALTER TABLE storage ADD CONSTRAINT storage_pkey PRIMARY KEY (storage_key);

ALTER TABLE product_transportation
    ADD CONSTRAINT product_product_transportation_fkey FOREIGN KEY (product_key) REFERENCES product(product_key);

ALTER TABLE product_transportation
    ADD CONSTRAINT transportation_product_transportation_fkey FOREIGN KEY (transportation_key) REFERENCES transportation(transportation_key);

ALTER TABLE storage
    ADD CONSTRAINT storage_product_fkey FOREIGN KEY (product_key) REFERENCES product(product_key);


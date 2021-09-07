-- !Ups

CREATE TABLE "cartitem"
(
    "id"         INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "user_id"    INTEGER NOT NULL,
    "product_id" INTEGER NOT NULL,
    "quantity"   INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE "category"
(
    "id"                 INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"               VARCHAR NOT NULL,
    "parent_category_id" INTEGER,
    FOREIGN KEY (parent_category_id) REFERENCES "category" (id)
);

CREATE TABLE "delivery"
(
    "id"              INTEGER   NOT NULL PRIMARY KEY AUTOINCREMENT,
    "status"          INTEGER   NOT NULL,
    "shipping_method" INTEGER   NOT NULL,
    "delivery_date"   TIMESTAMP NOT NULL
);

CREATE TABLE "orderitem"
(
    "id"         INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "order_id"   INTEGER NOT NULL,
    "product_id" INTEGER NOT NULL,
    "quantity"   INTEGER NOT NULL,
    FOREIGN KEY (order_id) REFERENCES "order" (id),
    FOREIGN KEY (product_id) REFERENCES "product" (id)
);

CREATE TABLE "order"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "user_id"     INTEGER NOT NULL,
    "delivery_id" INTEGER,
    "payment_id"  INTEGER,
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (delivery_id) REFERENCES "delivery" (id),
    FOREIGN KEY (payment_id) REFERENCES "payment" (id)
);

CREATE TABLE "payment"
(
    "id"         INTEGER   NOT NULL PRIMARY KEY AUTOINCREMENT,
    "status"     INTEGER   NOT NULL,
    "updated_at" TIMESTAMP NOT NULL
);

CREATE TABLE "product"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "category_id" INTEGER NOT NULL,
    "name"        VARCHAR NOT NULL,
    "description" VARCHAR NOT NULL,
    "price"       INTEGER NOT NULL,
    FOREIGN KEY (category_id) REFERENCES "category" (id)
);

CREATE TABLE "return"
(
    "id"       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "order_id" INTEGER NOT NULL,
    "status"   INTEGER NOT NULL,
    FOREIGN KEY (order_id) REFERENCES "order" (id)
);

CREATE TABLE "user"
(
    "id"       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"     VARCHAR NOT NULL,
    "email"    VARCHAR NOT NULL,
    "password" VARCHAR NOT NULL,
    "city"     VARCHAR NOT NULL,
    "address"  VARCHAR NOT NULL
);

CREATE TABLE "wishlistitem"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "wishlist_id" INTEGER NOT NULL,
    "product_id"  INTEGER NOT NULL,
    FOREIGN KEY ("wishlist_id") REFERENCES "wishlist" (id),
    FOREIGN KEY ("product_id") REFERENCES "product" (id)
);

CREATE TABLE "wishlist"
(
    "id"      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "user_id" INTEGER NOT NULL,
    "name"    VARCHAR,
    FOREIGN KEY ("user_id") REFERENCES "user" (id)
);

-- !Downs

DROP TABLE "cartitem";
DROP TABLE "category";
DROP TABLE "delivery";
DROP TABLE "orderitem";
DROP TABLE "order";
DROP TABLE "payment";
DROP TABLE "product";
DROP TABLE "return";
DROP TABLE "user";
DROP TABLE "wishlistitem";
DROP TABLE "wishlist";

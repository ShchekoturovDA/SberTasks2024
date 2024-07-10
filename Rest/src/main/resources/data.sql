-- drop schema my_sch cascade; drop table products, clients, bins, products_bins cascade;

create schema if not exists my_sch;

create table if not exists my_sch.products
(
    id_product    serial primary key,
    name_product  varchar(255) not null,
    value_product integer      not null,
    quantity integer      not null
);

create table if not exists my_sch.bins
(
    id_bin        serial primary key,
    promocode varchar(255)
);

create table if not exists my_sch.clients
(
    id_client       serial primary key,
    name_client     varchar(255) not null,
    login_client varchar(255) not null,
    password_client varchar(255) not null,
    email    varchar(255),
    id_bin  integer      not null
        constraint client_bin_id_bin_fk
            references my_sch.bins
);

create table if not exists my_sch.products_bins
(
    products_binId         serial primary key,
    id_product integer not null
        constraint product_client_products_id_fk
            references my_sch.products,
    id_bin    integer not null
        constraint product_client_bin_id_fk
            references my_sch.bins,
    count      integer not null
);
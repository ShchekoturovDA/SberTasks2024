--drop schema my_sch cascade;
create schema if not exists my_sch;

create table if not exists my_sch.products
(
    productId    serial primary key,
    productName  varchar(255) not null,
    productValue integer      not null,
    quantity integer      not null
);

create table if not exists my_sch.bins
(
    binId        serial primary key,
    promocode varchar(255)
);

create table if not exists my_sch.clients
(
    clientId       serial primary key,
    clientName     varchar(255) not null,
    clientLogin varchar(255) not null,
    clientPassword varchar(255) not null,
    email    varchar(255),
    bin_id  integer      not null
        constraint client_bin_binId_fk
            references my_sch.bins
);

create table if not exists my_sch.products_bins
(
    products_binId         serial primary key,
    id_product integer not null
        constraint product_client_products_clientId_fk
            references my_sch.products,
    id_bin    integer not null
        constraint product_client_bin_binId_fk
            references my_sch.bins,
    count      integer not null
);

create schema my_sch;

create table my_sch.products
(
    productId    integer generated always as identity
        primary key,
    productName  varchar(255) not null,
    productValue integer      not null,
    quantity integer      not null
);

create table my_sch.bins
(
    binId        integer generated always as identity
        primary key,
    promocode varchar(255)
);

create table my_sch.clients
(
    clientId       integer generated always as identity primary key,
    clientName     varchar(255) not null,
    clientLogin varchar(255) not null,
    clientPassword varchar(255) not null,
    email    varchar(255),
    bin_id  integer      not null
        constraint client_bin_binId_fk
            references my_sch.bins
);

create table my_sch.products_bins
(
    products_binId         integer generated always as identity primary key,
    id_product integer not null
        constraint product_client_products_clientId_fk
            references my_sch.products,
    id_bin    integer not null
        constraint product_client_bin_binId_fk
            references my_sch.bins,
    count      integer not null
);
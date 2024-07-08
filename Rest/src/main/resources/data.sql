create table products
(
    id    integer,
    name  varchar(255) not null,
    value numeric      not null,
    quantity integer      not null,
    constraint products_id_pk primary key (id)
);

create table bins
(
    id        integer,
    promoCode varchar(255),
    constraint bins_id_pk primary key (id)
);

create table clients
(
    id       integer,
    name     varchar(255) not null,
    login varchar(255) not null,
    password varchar(255) not null,
    email    varchar(255),
    bin_id  integer,
    constraint clients_id_pk primary key (id),
    constraint client_bin_id_fk foreign key (bin_id) references bins (id)
);

create table products_bins
(
    id         integer,
    id_product integer not null,
    count      integer not null,
    id_bin    integer not null,
    constraint products_bin_id_pk primary key (id),
    constraint product_client_products_id_fk foreign key (id_product) references products (id),
    constraint product_client_bin_id_fk foreign key (id_bin) references bins (id),
);
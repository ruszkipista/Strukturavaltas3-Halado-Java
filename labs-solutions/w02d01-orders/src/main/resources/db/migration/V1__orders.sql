create table orders
(
    id                  bigint auto_increment,
    product_name         varchar(255),
    product_count           bigint,
    price_per_product     bigint,

    constraint pk_orders primary key (id)
)
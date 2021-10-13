create table review (
    id serial primary key,
    id_product int not null,
    reviewer varchar(100),
    text varchar(255)
);
CREATE TABLE IF NOT EXISTS bankusers (
    id identity,
    username varchar(15) unique not null,
    password varchar(61) not null
);

CREATE SEQUENCE IF NOT EXISTS users_seq;

CREATE TABLE IF NOT EXISTS bank_users (
    id int primary key,
    username varchar(15) unique not null,
    password varchar(60) not null
);

CREATE SEQUENCE IF NOT EXISTS accounts_seq;
CREATE SEQUENCE IF NOT EXISTS account_number_seq;

CREATE TABLE IF NOT EXISTS bank_accounts (
    id int default next value for accounts_seq primary key,
    account_number varchar2(10) default lpad('' || next value for account_number_seq, 10, '0') not null,
    user_id int not null,
    balance decimal(12, 2) not null default 0,
    pin varchar(60) not null,
    doc_id varchar(60) not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    constraint unique_account_number unique(account_number),
    constraint fk_user foreign key (user_id) references bank_users
);

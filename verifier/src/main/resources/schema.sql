CREATE TABLE IF NOT EXISTS bank_users (
    id identity,
    username varchar(15) unique not null,
    password varchar(61) not null
);

CREATE SEQUENCE IF NOT EXISTS accounts_seq;
CREATE SEQUENCE IF NOT EXISTS account_number_seq;

CREATE TABLE IF NOT EXISTS bank_accounts (
    id int default next value for accounts_seq primary key,
    account_number varchar2(10) default lpad('' || next value for account_number_seq, 10, '0') not null,
    user_id int not null,
    balance decimal(12, 2) not null default 0,
    constraint unique_account_number unique(account_number),
    constraint fk_user foreign key (user_id) references bank_users
);

drop table if exists tb_user;
create table tb_user
(
    id serial           constraint tb_user_pk primary key,
    nome                varchar(255) not null,
    documento           varchar(255) not null,
    data_criacao        TIMESTAMP(6) NOT NULL DEFAULT NOW(),
    data_atualizacao    TIMESTAMP(6) NOT NULL DEFAULT NOW()
);
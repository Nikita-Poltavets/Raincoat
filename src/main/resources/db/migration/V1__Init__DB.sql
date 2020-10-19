create sequence hibernate_sequence start 1 increment 1;

create table advert (
    id int8 not null,
    creation_date timestamp,
    description text,
    details varchar(1024),
    job boolean not null,
    title varchar(512),
    user_id varchar(255),
    primary key (id)
);

create table comment (
    id int8 not null,
    text varchar(512),
    advert_id int8,
    user_id varchar(255) not null,
    primary key (id)
);

create table usr (
    id varchar(255) not null,
    email varchar(255),
    gender varchar(255),
    last_visit timestamp,
    locale varchar(255),
    name varchar(255),
    userpic varchar(255),
    primary key (id)
);

alter table if exists advert
    add constraint advert_user_fk
        foreign key (user_id) references usr;

alter table if exists comment
    add constraint comment_advert_fk
        foreign key (advert_id) references advert;

alter table if exists comment
    add constraint comment_user_fk
        foreign key (user_id) references usr;
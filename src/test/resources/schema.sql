create table booking_seq
(
    next_val bigint null
);

create table guest
(
    id          bigint       not null
        primary key,
    email       varchar(255) null,
    name        varchar(255) not null,
    phonenumber varchar(255) null,
    constraint UKl30f0fvs78rfwtjbim6nqo2cp
        unique (email)
);

create table guest_seq
(
    next_val bigint null
);

create table room
(
    id          bigint       not null
        primary key,
    room_name   varchar(255) null,
    room_number varchar(255) not null,
    room_size   int          not null,
    check (`room_size` >= 1)
);

create table booking
(
    id               bigint not null
        primary key,
    date_from        date   not null,
    date_until       date   not null,
    number_of_guests int    not null,
    guest_id         bigint null,
    room_id          bigint null,
    constraint FKjn3lsroa8t8h7x5sld9b0ru2u
        foreign key (guest_id) references guest (id),
    constraint FKq83pan5xy2a6rn0qsl9bckqai
        foreign key (room_id) references room (id),
    check (`number_of_guests` >= 1)
);

create table room_seq
(
    next_val bigint null
);


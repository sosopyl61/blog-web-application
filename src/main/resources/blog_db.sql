create table if not exists public.users
(
    id          bigserial
        constraint users_pk
            primary key,
    username    varchar(255) not null,
    firstname   varchar(255) not null,
    second_name varchar(255) not null,
    email       varchar(255) not null,
    age         integer      not null,
    created     timestamp    not null,
    updated     timestamp    not null,
    sex         varchar(255) not null
);

alter table public.users
    owner to user32;

create table if not exists public.security
(
    id       bigserial
        constraint security_pk
            primary key,
    login    varchar(255)                                   not null,
    password varchar(255)                                   not null,
    role     varchar(255) default 'USER'::character varying not null,
    created  timestamp                                      not null,
    updated  timestamp                                      not null,
    user_id  bigint                                         not null
        constraint security_users_id_fk
            references public.users
            on update cascade on delete cascade
);

alter table public.security
    owner to user32;

create table if not exists public.posts
(
    id        bigserial
        constraint posts_pk
            primary key,
    title     varchar(255) not null,
    content   varchar(255) not null,
    created   timestamp    not null,
    updated   timestamp    not null,
    author_id bigint       not null
        constraint posts_users_id_fk
            references public.users
            on update cascade on delete cascade
);

alter table public.posts
    owner to user32;

create table if not exists public.comments
(
    id                bigserial
        constraint comments_pk
            primary key,
    comment_text      varchar(255) not null,
    created           timestamp    not null,
    updated           timestamp    not null,
    comment_author_id bigint       not null
        constraint comments_users_id_fk
            references public.users
            on update cascade on delete cascade,
    post_id           bigint       not null
        constraint comments_posts_id_fk
            references public.posts
            on update cascade on delete cascade
);

alter table public.comments
    owner to user32;

create table if not exists public.comments
(
    id                bigserial
        constraint comments_pk
            primary key,
    comment_text      varchar(255) not null,
    created           timestamp    not null,
    updated           timestamp    not null,
    comment_author_id bigint       not null
        constraint comments_users_id_fk
            references public.users
            on update cascade on delete cascade,
    post_id           bigint       not null
        constraint comments_posts_id_fk
            references public.posts
            on update cascade on delete cascade
);

alter table public.comments
    owner to user32;

create table if not exists public.likes
(
    id         bigserial
        constraint likes_pk
            primary key,
    user_id    bigint    not null
        constraint likes_users_id_fk
            references public.users
            on update cascade on delete cascade,
    post_id    bigint
        constraint likes_posts_id_fk
            references public.posts
            on update cascade on delete cascade,
    comment_id bigint
        constraint likes_comments_id_fk
            references public.comments
            on update cascade on delete cascade,
    created    timestamp not null
);

alter table public.likes
    owner to user32;

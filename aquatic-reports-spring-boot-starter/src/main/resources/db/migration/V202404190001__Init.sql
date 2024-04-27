-- test table

CREATE TABLE IF NOT EXISTS test
(
    id          uuid         not null,
    code        varchar(255) not null,
    hash        integer      not null,
    version     integer      not null,
    name        varchar(255),
    description varchar(2048),
    primary key (id)
);


-- step table

CREATE TABLE IF NOT EXISTS step
(
    id             uuid         not null,
    code           varchar(255) not null,
    hash           integer      not null,
    version        integer      not null,
    name           varchar(255),
    pre_condition  varchar(511),
    post_condition varchar(511),
    test_id        uuid         not null references test (id),
    primary key (id)
);

-- test result table

CREATE TABLE IF NOT EXISTS test_result
(
    id      uuid         not null,
    status  varchar(255) not null,
    test_id uuid         not null references test (id),
    primary key (id)
);

-- step result table

CREATE TABLE IF NOT EXISTS step_result
(
    id             uuid         not null,
    status         varchar(255) not null,
    step_id        uuid         not null references step (id),
    test_result_id uuid         not null references test_result (id),
    primary key (id)
);


-- test_attachment table

CREATE TABLE IF NOT EXISTS test_attachment
(
    id             uuid         not null,
    holder         varchar(255) not null,
    type           varchar(255) not null,
    created_at     timestamptz  not null default now(),
    step_result_id uuid references step_result,
    test_result_id uuid references test_result,
    content        text,
    primary key (id)
);

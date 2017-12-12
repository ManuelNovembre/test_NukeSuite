CREATE TABLE favorite
(
    id VARCHAR(100) PRIMARY KEY NOT NULL,
    title VARCHAR(100),
    description VARCHAR(1000),
    director VARCHAR(100),
    producer VARCHAR(100),
    release_date INT,
    rt_score INT
);

CREATE SEQUENCE user_id_seq start with 1 increment by 50;

CREATE TABLE IF NOT EXISTS Users (
                                    user_id BIGINT DEFAULT NEXTVAL('user_id_seq') NOT NULL PRIMARY KEY,
                                    username VARCHAR(255) NOT NULL UNIQUE,
                                    password VARCHAR(255) NOT NULL,
                                    role VARCHAR(255) NOT NULL,
                                    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                    update_at TIMESTAMP WITH TIME ZONE NOT NULL
);
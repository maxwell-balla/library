CREATE SEQUENCE book_id_seq start with 1 increment by 50;

CREATE TABLE IF NOT EXISTS Book (
                                    book_id BIGINT DEFAULT NEXTVAL('book_id_seq') NOT NULL PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL UNIQUE,
                                    description TEXT,
                                    is_available BOOLEAN NOT NULL DEFAULT TRUE,
                                    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                    update_at TIMESTAMP WITH TIME ZONE NOT NULL
);
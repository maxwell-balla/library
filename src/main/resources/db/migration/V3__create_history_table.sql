CREATE SEQUENCE history_id_seq start with 1 increment by 50;

CREATE TABLE IF NOT EXISTS History (
                                    history_id BIGINT DEFAULT NEXTVAL('history_id_seq') NOT NULL PRIMARY KEY,
                                    record_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                    update_at TIMESTAMP WITH TIME ZONE NOT NULL
);
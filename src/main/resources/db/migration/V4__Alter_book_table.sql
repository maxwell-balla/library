BEGIN;

ALTER TABLE Book ADD COLUMN publication_year INT;

COMMIT;
CREATE DATABASE test;

ALTER DATABASE test OWNER TO postgres;

\connect test

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE exemplo (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	externalId SERIAL NOT NULL,
	description varchar
);

CREATE INDEX exemplo_external_id_idx ON exemplo (externalId);

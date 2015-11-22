--create user pronto with PASSWORD 'specialsecret';
--create user pronto_usr with PASSWORD 'secret';
-- create database pronto OWNER postgres;

--GRANT ALL PRIVILEGES ON DATABASE pronto TO pronto;
-- privileges of pronto_usr should be less.
--GRANT ALL PRIVILEGES ON DATABASE pronto TO pronto_usr;
--GRANT CONNECT ON DATABASE pronto TO pronto_usr;
--GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA pronto TO pronto_usr

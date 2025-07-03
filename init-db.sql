-- Create database and role for the application
DO
$do$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'eldorg_user') THEN
      CREATE ROLE eldorg_user LOGIN PASSWORD 'eldorg_pass';
   END IF;
END
$do$;

-- Create database if not exists and set owner
DO
$do$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'eldorg_db') THEN
      CREATE DATABASE eldorg_db OWNER eldorg_user;
   END IF;
END
$do$;

-- You can add more initialization SQL here

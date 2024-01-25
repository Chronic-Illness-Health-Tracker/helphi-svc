CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE user_title AS ENUM ('', '', '');

CREATE TABLE IF NOT EXIST patient (
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    nhs_number NOT NULL VARCHAR(10),
    gp_id UUID FOREIGN KEY REFERENCES gp(id),
    address_id FOREIGN KEY REFERENCES address(id),
    title USER_TITLE,
    forename VARCHAR(35),
    middlenames VARCHAR(35),
    lastname VARCHAR(35),
    email VARCHAR(320),
    contact_number VARCHAR(15),
    alternate_contact_number VARCHAR(15),
    date_of_birth DATE,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXIST address (
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    address_line_one VARCHAR(35),
    address_line_two VARCHAR(35),
    postcode VARCHAR(8),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXIST clinitian (
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    organisation_id UUID FOREIGN KEY REFERENCES organisation(id),
    title USER_TITLE,
    forename VARCHAR(35),
    middlenames VARCHAR(35),
    lastname VARCHAR(35),
    email VARCHAR(320),
    contact_number VARCHAR(15),
    alternate_contact_number VARCHAR(15),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXIST health_condition (
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    organisation_id UUID FOREIGN KEY REFERENCES organisation(id),
    name VARCHAR(45),
    short_name VARCHAR(5),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXIST organisation (
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    name VARCHAR(45),
    country_code VARCHAR(6),
    address_id UUID FOREIGN KEY REFERENCES address(id),
    contact_number VARCHAR(15),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXIST gp_surgery (
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    address_id FOREIGN KEY REFERENCES address(id),
    country_code VARCHAR(6),
    contact_number VARCHAR(15),
    email VARCHAR(320),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXIST gp (
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    title USER_TITLE,
    forename VARCHAR(35),
    middlenames VARCHAR(35),
    lastname VARCHAR(35),
    contact_number VARCHAR(15),
    email VARCHAR(320),
    surgery_id FOREIGN KEY REFERENCES gp_surgery(id),
    PRIMARY KEY(id)
);

CREATE TABLE patient_condition_link (
    patient_id NOT NULL UUID,
    condition_id NOT NULL UUID,
    PRIMARY KEY (patient_id, condition_id)
);

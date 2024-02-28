CREATE SCHEMA extensions;

GRANT USAGE ON SCHEMA extensions TO public;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA extensions TO PUBLIC;

ALTER DEFAULT PRIVILEGES IN SCHEMA extensions
   GRANT EXECUTE ON FUNCTIONS TO PUBLIC;

ALTER DEFAULT PRIVILEGES IN SCHEMA extensions
   GRANT USAGE ON TYPES TO PUBLIC;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" schema extensions;

CREATE SCHEMA helphi;

CREATE TYPE user_title AS ENUM ('Mr', 'Mrs', 'Miss', 'Ms', 'Mx', 'Dr', 'Prof', 'Revd');

CREATE TABLE IF NOT EXISTS helphi.address (
    id UUID NOT NULL DEFAULT extensions.uuid_generate_v4(),
    address_line_one VARCHAR(35),
    address_line_two VARCHAR(35),
    postcode VARCHAR(8),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS helphi.organisation (
    id UUID NOT NULL DEFAULT extensions.uuid_generate_v4(),
    name VARCHAR(45),
    country_code VARCHAR(6),
    email VARCHAR(320),
    address_id UUID,
    contact_number VARCHAR(15),
    PRIMARY KEY(id),
    CONSTRAINT fk_address FOREIGN KEY(address_id) REFERENCES helphi.address(id)
);

CREATE TABLE IF NOT EXISTS helphi.health_condition (
    id UUID NOT NULL DEFAULT extensions.uuid_generate_v4(),
    organisation_id UUID NOT NULL,
    name VARCHAR(45),
    short_name VARCHAR(5),
    PRIMARY KEY(id),
    CONSTRAINT fk_organisation FOREIGN KEY(organisation_id) REFERENCES helphi.organisation(id)
);

CREATE TABLE IF NOT EXISTS helphi.clinician (
    id UUID NOT NULL DEFAULT extensions.uuid_generate_v4(),
    organisation_id UUID REFERENCES helphi.organisation(id),
    title USER_TITLE,
    forename VARCHAR(35),
    middlenames VARCHAR(35),
    lastname VARCHAR(35),
    email VARCHAR(320),
    contact_number VARCHAR(15),
    alternate_contact_number VARCHAR(15),
    PRIMARY KEY(id),
    CONSTRAINT fk_organisation FOREIGN KEY(organisation_id) REFERENCES helphi.organisation(id)
);

CREATE TABLE IF NOT EXISTS helphi.gp_surgery (
    id UUID NOT NULL DEFAULT extensions.uuid_generate_v4(),
    name VARCHAR(45),
    address_id UUID,
    country_code VARCHAR(6),
    contact_number VARCHAR(15),
    email VARCHAR(320),
    PRIMARY KEY(id),
    CONSTRAINT fk_address FOREIGN KEY(address_id) REFERENCES helphi.address(id)
);

CREATE TABLE IF NOT EXISTS helphi.gp (
    id UUID NOT NULL DEFAULT extensions.uuid_generate_v4(),
    title USER_TITLE,
    forename VARCHAR(35),
    middlenames VARCHAR(35),
    lastname VARCHAR(35),
    contact_number VARCHAR(15),
    email VARCHAR(320),
    surgery_id UUID,
    PRIMARY KEY(id),
    CONSTRAINT fk_surgery FOREIGN KEY(surgery_id) REFERENCES helphi.gp_surgery(id)
);

CREATE TABLE IF NOT EXISTS helphi.patient (
    id UUID NOT NULL DEFAULT extensions.uuid_generate_v4(),
    nhs_number VARCHAR(10) NOT NULL,
    gp_id UUID,
    address_id UUID REFERENCES helphi.address(id),
    title USER_TITLE,
    forename VARCHAR(35),
    middlenames VARCHAR(35),
    lastname VARCHAR(35),
    email VARCHAR(320),
    contact_number VARCHAR(15),
    alternate_contact_number VARCHAR(15),
    date_of_birth DATE,
    PRIMARY KEY(id),
    CONSTRAINT fk_gp FOREIGN KEY(gp_id) REFERENCES helphi.gp(id),
    CONSTRAINT fk_address FOREIGN KEY(address_id) REFERENCES helphi.address(id)
);


CREATE TABLE IF NOT EXISTS helphi.patient_condition_link (
    patient_id UUID NOT NULL,
    health_condition_id UUID NOT NULL,
    PRIMARY KEY (patient_id, health_condition_id),
    CONSTRAINT fk_patient FOREIGN KEY(patient_id) REFERENCES helphi.patient(id),
    CONSTRAINT fk_health_condition FOREIGN KEY(health_condition_id) REFERENCES helphi.health_condition(id)
);

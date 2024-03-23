CREATE TABLE IF NOT EXISTS patient (
    patient_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth DATE,
    social_security_number VARCHAR(11) -- Assuming SSN format ###-##-####
);

CREATE TABLE IF NOT EXISTS visit (
    visit_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID REFERENCES patient(patient_id),
    visit_datetime TIMESTAMP,
    home_visit BOOLEAN,
    visit_reason VARCHAR(20) CHECK (visit_reason IN ('FIRST_VISIT', 'RECURRING_VISIT', 'URGENT')),
    family_history TEXT
);

INSERT INTO patient (patient_id, first_name, last_name, date_of_birth, social_security_number)
VALUES
    (uuid_generate_v4(), 'John', 'Doe', '1990-05-15', '123-45-6789'),
    (uuid_generate_v4(), 'Jane', 'Smith', '1985-08-20', '987-65-4321'),
    (uuid_generate_v4(), 'Alice', 'Johnson', '1978-12-10', '456-78-9012');
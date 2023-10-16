CREATE TABLE User (
id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR (15) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR (255) ,
    otp INT (10),
    uuid VARCHAR(255)
);
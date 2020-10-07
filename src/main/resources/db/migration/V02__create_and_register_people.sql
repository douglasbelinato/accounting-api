CREATE TABLE IF NOT EXISTS person
(
    id                    BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name                  VARCHAR(50) NOT NULL,
    street                VARCHAR(60) NOT NULL,
    number                VARCHAR(8)  NOT NULL,
    neighborhood          VARCHAR(40) NOT NULL,
    zip_code              VARCHAR(10) NOT NULL,
    city                  VARCHAR(30) NOT NULL,
    state                 VARCHAR(2) NOT NULL,
    aditional_information VARCHAR(50),
    active                BOOLEAN     NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO person (name, street, number, neighborhood, zip_code, city, state, aditional_information, active)
VALUES ('Daniel Souza', 'Rua Florais', '84A', 'Jardim Luz', '08332-500', 'São Paulo', 'SP', 'Casa', true);

INSERT INTO person (name, street, number, neighborhood, zip_code, city, state, aditional_information, active)
VALUES ('Carla Moreira', 'Rua dos Oliveiras', '120', 'Jardim Primavera', '08332-200', 'São Paulo', 'SP', 'Casa', true);

INSERT INTO person (name, street, number, neighborhood, zip_code, city, state, aditional_information, active)
VALUES ('Beatriz Oliveira', 'Rua Maganês', '60', 'Vila Homero', '08102-202', 'Cascavel', 'PR', 'Apartamento', true);

INSERT INTO person (name, street, number, neighborhood, zip_code, city, state, aditional_information, active)
VALUES ('Leandro Oliveira', 'Rua Maganês', '60', 'Vila Homero', '08102-202', 'Cascavel', 'PR', 'Apartamento', false);

INSERT INTO person (name, street, number, neighborhood, zip_code, city, state, aditional_information, active)
VALUES ('Lucas Andrade Silva', 'Avenida Lopes Machado', '450', 'Vila Casa do Ator', '07530-450', 'Floripa', 'SC',
        'Casa', false);

INSERT INTO person (name, street, number, neighborhood, zip_code, city, state, aditional_information, active)
VALUES ('Andréa Silva', 'Rua Olímpia', '2', 'Jardim Vera Cruz', '09330-800', 'Cuiabá', 'MT', 'Apartamento', true);
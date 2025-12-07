CREATE TABLE owners (
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(64) NOT NULL,
	last_name VARCHAR(64) NOT NULL,
	middle_name VARCHAR(64) NOT NULL,
	phone_number VARCHAR(64) NOT NULL
)

CREATE TABLE cars (
	id SERIAL PRIMARY KEY,
	car_number VARCHAR(20) NOT NULL UNIQUE,
	brand VARCHAR(30) NOT NULL,
	id_owner INTEGER REFERENCES owners(id)
)

CREATE TABLE spots (
	id SERIAL PRIMARY KEY,
	spot_number INTEGER NOT NULL UNIQUE,
	is_available BOOLEAN NOT NULL
)

CREATE TABLE bookings (
	id SERIAL PRIMARY KEY,
	id_car INTEGER REFERENCES cars(id) NOT NULL,
	id_spot INTEGER REFERENCES spots(id) NOT NULL,
	start_date TIMESTAMP NOT NULL,
	end_date TIMESTAMP NOT NULL,
	is_paid BOOLEAN NOT NULL,
	is_active BOOLEAN NOT NULL
)

INSERT INTO owners (first_name, last_name, middle_name, phone_number) VALUES
('Иван', 'Иванов', 'Иванович', '+7(999)111-22-33'),
('Петр', 'Петров', 'Петрович', '+7(999)222-33-44'),
('Сергей', 'Сидоров', 'Сергеевич', '+7(999)333-44-55'),
('Александр', 'Смирнов', 'Александрович', '+7(999)444-55-66'),
('Дмитрий', 'Кузнецов', 'Дмитриевич', '+7(999)555-66-77'),
('Алексей', 'Попов', 'Алексеевич', '+7(999)666-77-88'),
('Андрей', 'Васильев', 'Андреевич', '+7(999)777-88-99'),
('Михаил', 'Павлов', 'Михайлович', '+7(999)888-99-00'),
('Евгений', 'Семенов', 'Евгеньевич', '+7(999)999-00-11'),
('Владимир', 'Голубев', 'Владимирович', '+7(999)000-11-22'),
('Ольга', 'Новикова', 'Сергеевна', '+7(999)111-00-22'),
('Мария', 'Морозова', 'Андреевна', '+7(999)222-11-33'),
('Анна', 'Волкова', 'Дмитриевна', '+7(999)333-22-44'),
('Екатерина', 'Алексеева', 'Игоревна', '+7(999)444-33-55'),
('Наталья', 'Лебедева', 'Викторовна', '+7(999)555-44-66');


INSERT INTO cars (car_number, brand, id_owner) VALUES
('А123АА777', 'Toyota Camry', 1),
('В456ВВ123', 'Honda Civic', 2),
('С789СС456', 'BMW X5', 3),
('Е012ЕЕ789', 'Mercedes-Benz E-Class', 4),
('Х345ХХ012', 'Audi A6', 5),
('К678КК345', 'Hyundai Solaris', 6),
('М901ММ678', 'Kia Rio', 7),
('Н234НН901', 'Lada Vesta', 8),
('О567ОО234', 'Ford Focus', 9),
('Р890РР567', 'Chevrolet Niva', 10),
('А001АА177', 'Lexus RX', 11),
('М777МС777', 'Volkswagen Tiguan', 12),
('С065АН777', 'Nissan Qashqai', 13),
('У321УУ777', 'Skoda Octavia', 14),
('Т654ТТ777', 'Mazda CX-5', 15);
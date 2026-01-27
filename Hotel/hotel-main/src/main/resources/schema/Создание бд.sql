CREATE TABLE IF NOT EXISTS client(
id BIGSERIAL primary key,
name varchar(30) NOT NULL,
surname varchar(30) NOT NULL,
dateOfBirth date NOT NULL,
gender varchar(10) NOT NULL
);
CREATE TABLE IF NOT EXISTS room(
roomNumber int primary key,
price money NOT NULL,
place int NOT NULL,
type varchar(20) NOT NULL,
status varchar(15),
stars varchar(10) NOT NULL
);
CREATE TABLE IF NOT EXISTS booking(
id BIGSERIAL primary key,
checkInDate date NOT NULL,
checkOutDate date NOT NULL,
totalPrice money NOT NULL,
roomNumber int NOT NULL,
id_client bigint NOT NULL,
CONSTRAINT fk_booking_roomNumber foreign key (roomNumber) references room(roomNumber),
CONSTRAINT fk_booking_id_client foreign key (id_client) references client(id),
CONSTRAINT date_valid CHECK (checkInDate < checkOutDate)
);
CREATE TABLE IF NOT EXISTS service(
id BIGSERIAL primary key,
typeService varchar(20) NOT NULL,
serviceName varchar(30) NOT NULL,
servicePrice money NOT NULL,
duration int NOT NULL,
date date NOT NULL,
id_client bigint NOT NULL,
CONSTRAINT  fk_service_id_client foreign key (id_client) references client(id)
);
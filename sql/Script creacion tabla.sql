CREATE DATABASE GESTORBIBLIOTECA;
USE GESTORBIBLIOTECA;

CREATE TABLE LIBRO (
isbn varchar(50) PRIMARY KEY,
titulo varchar(50) not null,
autor varchar(50) not null,
genero varchar(50),
anno date not null,
ejemplares_disponibles int not null
);

CREATE TABLE SOCIO(
dni varchar(50) primary key,
nombre varchar(50) not null,
direccion varchar(50) not null,
tlfn varchar(50),
alta date not null
);

create table PRESTAMO(
id int primary key auto_increment,
estado varchar(50) not null,
fecha_prestamo date not null,
devolucion_Estimada date not null,
fecha_Devolucion date,
libro_prestado varchar(50) not null,
socio_prestamo varchar(50) not null,
constraint fk_libro foreign key (libro_prestado) references Libro(isbn),
constraint fk_socio foreign key (socio_prestamo) references Socio(dni)
);

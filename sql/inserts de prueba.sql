
INSERT INTO libro (isbn, titulo, autor, anio, genero, ejemplares) VALUES
('9780140449136', 'La Odisea', 'Homero', 1996, 'Épico', 5),
('9788491050295', 'Don Quijote de la Mancha', 'Miguel de Cervantes', 2015, 'Novela', 4),
('9780142437230', 'Moby Dick', 'Herman Melville', 2002, 'Aventura', 3),
('9780307474278', 'El Gran Gatsby', 'F. Scott Fitzgerald', 2013, 'Novela', 6),
('9788497592201', '1984', 'George Orwell', 2003, 'Distopía', 7),
('9788499890947', 'El Principito', 'Antoine de Saint-Exupéry', 2015, 'Fábula', 10),
('9780307277671', 'Cien años de soledad', 'Gabriel García Márquez', 2006, 'Realismo mágico', 4),
('9788497593793', 'Crimen y castigo', 'Fiódor Dostoyevski', 2002, 'Novela', 5),
('9788498381498', 'El Hobbit', 'J.R.R. Tolkien', 2012, 'Fantasía', 8),
('9780547928227', 'El Señor de los Anillos', 'J.R.R. Tolkien', 2012, 'Fantasía', 6);


INSERT INTO socio (dni, nombre, direccion, telefono, fecha_alta) VALUES
('12345678A', 'Carlos Pérez', 'C/ Madrid 12', '600123123', '2024-01-15'),
('23456789B', 'Laura Gómez', 'Av. Salamanca 45', '611234234', '2024-02-10'),
('34567890C', 'María López', 'C/ Toledo 8', '622345345', '2024-03-05'),
('45678901D', 'Jorge Martín', 'C/ Zamora 22', '633456456', '2024-04-01'),
('56789012E', 'Ana Torres', 'C/ Valladolid 19', '644567567', '2024-04-20'),
('67890123F', 'Pedro Sánchez', 'C/ León 3', '655678678', '2024-05-02'),
('78901234G', 'Lucía Ramos', 'C/ Burgos 14', '666789789', '2024-05-10'),
('89012345H', 'David Hernández', 'C/ Cáceres 7', '677890890', '2024-05-18'),
('90123456J', 'Sara Blanco', 'C/ Ávila 11', '688901901', '2024-05-25'),
('01234567K', 'Miguel Ángel', 'C/ Segovia 5', '699012012', '2024-06-01');


INSERT INTO prestamo (isbn, dni_socio, fecha_prestamo, fecha_devolucion_estimada, fecha_devolucion_real) VALUES
('9780140449136', '12345678A', '2024-05-01', '2024-05-21', NULL),
('9788491050295', '23456789B', '2024-05-03', '2024-05-23', '2024-05-20'),
('9780142437230', '34567890C', '2024-05-05', '2024-05-25', NULL),
('9780307474278', '45678901D', '2024-05-07', '2024-05-27', '2024-05-26'),
('9788497592201', '56789012E', '2024-05-10', '2024-05-30', NULL),
('9788499890947', '67890123F', '2024-05-12', '2024-06-01', NULL),
('9780307277671', '78901234G', '2024-05-15', '2024-06-04', '2024-06-03'),
('9788497593793', '89012345H', '2024-05-18', '2024-06-07', NULL),
('9788498381498', '90123456J', '2024-05-20', '2024-06-09', NULL),
('9780547928227', '01234567K', '2024-05-22', '2024-06-11', NULL);

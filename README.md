[README.md](https://github.com/user-attachments/files/29175408/README.1.md)
# GestorBiblioteca

Sistema de gestión de biblioteca desarrollado en Java como proyecto final del módulo de Programación de 1º DAM.

---

## Descripción

GestorBiblioteca es una aplicación de escritorio que permite gestionar el catálogo de libros, socios y préstamos de la Biblioteca Municipal de Ciudad Rodrigo. Combina una interfaz gráfica Swing con persistencia en base de datos MySQL y copias de seguridad serializadas.

---

## Tecnologías utilizadas

- Java 17
- Swing (interfaz gráfica)
- JDBC (acceso a base de datos)
- MySQL 8
- Maven (gestión de dependencias)
- MySQL Connector J 8.0.33
- Log4j 2.26.0

---

## Requisitos previos

- Java 17 o superior instalado
- MySQL 8 instalado y en ejecución
- Maven instalado
- Eclipse IDE con WindowBuilder

---

## Instalación y configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/tuUsuario/GestorBiblioteca.git
```

### 2. Crear la base de datos

Abre MySQL Workbench y ejecuta el script:

```
src/main/resources/bd/biblioteca.sql
```

Esto creará la base de datos `GESTORBIBLIOTECA` con las tablas `LIBRO`, `SOCIO` y `PRESTAMO`.

### 3. Configurar la conexión

Edita el archivo `src/main/resources/config/db.properties` con tus credenciales:

```properties
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/GESTORBIBLIOTECA
db.usuario=root
db.password=tuContraseña
```

### 4. Compilar y ejecutar

Desde Eclipse:
- Clic derecho sobre el proyecto → Run As → Maven Build → Goals: `clean install`
- Ejecutar `VentanaPrincipal.java` como Java Application

---

## Estructura del proyecto

```
src/main/java/
├── modelo/          → Entidades: Libro, Socio, Prestamo
├── dao/             → Interfaces DAO e implementaciones JDBC
├── servicio/        → Lógica de negocio
├── controlador/     → Controladores MVC
├── vista/           → Interfaz gráfica Swing
├── util/            → ConexionDB, GestorFicheros, Validaciones
└── exception/       → Excepciones de dominio propias

src/main/resources/
├── bd/              → Script SQL de creación de tablas
└── config/          → Fichero de propiedades de conexión
```

---

## Funcionalidades

- **Libros** → Alta, baja, modificación y búsqueda por ISBN, título y autor
- **Socios** → CRUD completo con validación de DNI por expresión regular
- **Préstamos** → Registro de préstamos, control de disponibilidad, detección de vencimientos (20 días)
- **Copia de seguridad** → Serialización de datos a fichero `.bak` y restauración
- **Menú principal** → Pantalla de inicio con navegación a cada módulo

## Autor

**David**
1º DAM — Módulo de Programación
Curso 2025-2026

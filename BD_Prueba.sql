-- 1. Crear la base de datos
CREATE DATABASE IF NOT EXISTS cesde_db;
USE cesde_db;

-- 2. Crear la tabla de Docentes (Entidad Independiente)
CREATE TABLE docentes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    documento VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- 3. Crear la tabla de Cursos (Entidad Dependiente)
CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    duracion_semanas INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    fecha_inicio DATETIME NOT NULL,
    docente_id BIGINT,
    -- Relación: Un curso pertenece a un docente
    CONSTRAINT fk_curso_docente 
        FOREIGN KEY (docente_id) 
        REFERENCES docentes(id)
        ON DELETE SET NULL -- Si se borra un docente, el curso queda sin asignar
) ENGINE=InnoDB;

-- 4. Datos de prueba (Opcional)
INSERT INTO docentes (nombre, documento, correo) VALUES 
('Juan Perez', '12345678', 'juan.perez@cesde.edu.co');

INSERT INTO cursos (nombre, descripcion, duracion_semanas, precio, fecha_inicio, docente_id) VALUES 
('Desarrollo Backend', 'Curso avanzado de Spring Boot', 16, 1500000.00, '2024-02-01 18:30:00', 1);
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gestionpartes
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gestionpartes` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gestionpartes`;

-- -----------------------------------------------------
-- Table `gestionpartes`.`grupos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestionpartes`.`grupos`;
CREATE TABLE IF NOT EXISTS `gestionpartes`.`grupos` (
  `id_grupo` INT NOT NULL AUTO_INCREMENT,
  `nombre_grupo` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_grupo`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- -----------------------------------------------------
-- Table `gestionpartes`.`alumnos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestionpartes`.`alumnos`;
CREATE TABLE IF NOT EXISTS `gestionpartes`.`alumnos` (
  `id_alum` INT NOT NULL AUTO_INCREMENT,
  `id_grupo` INT NOT NULL,
  `puntos_acumulados` INT NOT NULL,
  `nombre_alum` VARCHAR(255) NULL DEFAULT NULL,
  `numero_expediente` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_alum`),
  INDEX `FKoif6noujgnb1q4hfucdj3by8q` (`id_grupo` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

ALTER TABLE `gestionpartes`.`alumnos`
  ADD CONSTRAINT `FKoif6noujgnb1q4hfucdj3by8q`
  FOREIGN KEY (`id_grupo`)
  REFERENCES `gestionpartes`.`grupos` (`id_grupo`);

-- -----------------------------------------------------
-- Table `gestionpartes`.`puntuacion_partes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestionpartes`.`puntuacion_partes`;
CREATE TABLE IF NOT EXISTS `gestionpartes`.`puntuacion_partes` (
  `id_punt_partes` INT NOT NULL AUTO_INCREMENT,
  `puntos` INT NOT NULL,
  `tipo_parte` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_punt_partes`),
  UNIQUE INDEX `UK_6m0adpsgoo0hnptrfpdvcx8vm` (`tipo_parte` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- -----------------------------------------------------
-- Table `gestionpartes`.`profesores`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestionpartes`.`profesores`;
CREATE TABLE IF NOT EXISTS `gestionpartes`.`profesores` (
  `id_profesor` INT NOT NULL AUTO_INCREMENT,
  `contrasena` VARCHAR(255) NULL DEFAULT NULL,
  `nombre` VARCHAR(255) NULL DEFAULT NULL,
  `numero_asignado` VARCHAR(255) NULL DEFAULT NULL,
  `tipo` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_profesor`),
  UNIQUE INDEX `UK_p6ltb4s5eu3ymeanq6rdw944v` (`numero_asignado` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- -----------------------------------------------------
-- Table `gestionpartes`.`partes_incidencia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestionpartes`.`partes_incidencia`;
CREATE TABLE IF NOT EXISTS `gestionpartes`.`partes_incidencia` (
  `id_alum` INT NULL DEFAULT NULL,
  `id_grupo` INT NULL DEFAULT NULL,
  `id_parte` INT NOT NULL AUTO_INCREMENT,
  `id_profesor` INT NULL DEFAULT NULL,
  `id_punt_partes` INT NULL DEFAULT NULL,
  `descripcion` VARCHAR(255) NULL DEFAULT NULL,
  `fecha` VARCHAR(255) NULL DEFAULT NULL,
  `hora` VARCHAR(255) NULL DEFAULT NULL,
  `sancion` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_parte`),
  INDEX `FKqrx661g5lij25bl2plx6cb2pl` (`id_alum` ASC),
  INDEX `FKckq2ajm1w9wbi3kunm8q3ldp3` (`id_grupo` ASC),
  INDEX `FKniytl2x2lvm632ic1904a1bhb` (`id_profesor` ASC),
  INDEX `FK75ruupml2w0bpugnsojl56g05` (`id_punt_partes` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

ALTER TABLE `gestionpartes`.`partes_incidencia`
  ADD CONSTRAINT `FK75ruupml2w0bpugnsojl56g05`
  FOREIGN KEY (`id_punt_partes`)
  REFERENCES `gestionpartes`.`puntuacion_partes` (`id_punt_partes`);

ALTER TABLE `gestionpartes`.`partes_incidencia`
  ADD CONSTRAINT `FKckq2ajm1w9wbi3kunm8q3ldp3`
  FOREIGN KEY (`id_grupo`)
  REFERENCES `gestionpartes`.`grupos` (`id_grupo`);

ALTER TABLE `gestionpartes`.`partes_incidencia`
  ADD CONSTRAINT `FKniytl2x2lvm632ic1904a1bhb`
  FOREIGN KEY (`id_profesor`)
  REFERENCES `gestionpartes`.`profesores` (`id_profesor`);

ALTER TABLE `gestionpartes`.`partes_incidencia`
  ADD CONSTRAINT `FKqrx661g5lij25bl2plx6cb2pl`
  FOREIGN KEY (`id_alum`)
  REFERENCES `gestionpartes`.`alumnos` (`id_alum`);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- Inserción de puntuaciones predeterminadas
INSERT INTO puntuacion_partes (id_punt_partes, tipo_parte, puntos) VALUES(1, 'verde', 1);
INSERT INTO puntuacion_partes (id_punt_partes, tipo_parte, puntos) VALUES(6, 'naranja', 6);
INSERT INTO puntuacion_partes (id_punt_partes, tipo_parte, puntos) VALUES(12, 'rojo', 12);

-- Inserción de profesores en la tabla 'profesores'
INSERT INTO profesores (nombre, tipo, numero_asignado, contrasena) VALUES('Juan Perez', 'jefe_de_estudios', 1001, 'ce5ca673d13b36118d54a7cf13aeb0ca012383bf771e713421b4d1fd841f539a');
INSERT INTO profesores (nombre, tipo, numero_asignado, contrasena) VALUES('Alberto Perez', 'profesor', 1002, 'ce5ca673d13b36118d54a7cf13aeb0ca012383bf771e713421b4d1fd841f539a');
INSERT INTO profesores (nombre, tipo, numero_asignado, contrasena) VALUES('Maria Lopez', 'profesor', 1003, '1b18033d8286c4efc126b8a131e85db079c731aca276c9204b6221ca00fedbb0');
INSERT INTO profesores (nombre, tipo, numero_asignado, contrasena) VALUES('Carlos Sanchez', 'profesor', 1004, '1b18033d8286c4efc126b8a131e85db079c731aca276c9204b6221ca00fedbb0');
INSERT INTO profesores (nombre, tipo, numero_asignado, contrasena) VALUES('Laura Gomez', 'profesor', 1005, '1b18033d8286c4efc126b8a131e85db079c731aca276c9204b6221ca00fedbb0');
INSERT INTO profesores (nombre, tipo, numero_asignado, contrasena) VALUES('Fernando Ruiz', 'profesor', 1006, '1b18033d8286c4efc126b8a131e85db079c731aca276c9204b6221ca00fedbb0');

-- Inserción de grupos en la tabla 'grupos'
INSERT INTO grupos (nombre_grupo) VALUES('1º ESO A');
INSERT INTO grupos (nombre_grupo) VALUES('1º ESO B');
INSERT INTO grupos (nombre_grupo) VALUES('2º ESO A');
INSERT INTO grupos (nombre_grupo) VALUES('2º ESO B');
INSERT INTO grupos (nombre_grupo) VALUES('3º ESO A');
INSERT INTO grupos (nombre_grupo) VALUES('3º ESO B');
INSERT INTO grupos (nombre_grupo) VALUES('4º ESO A');
INSERT INTO grupos (nombre_grupo) VALUES('4º ESO B');

-- Inserción de alumnos en la tabla 'alumnos'
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(1, 4, 'Alejandro García', '1001');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(1, 6, 'María Fernández', '1002');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(2, 4, 'Juan López', '1003');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(2, 12, 'Laura Martínez', '1004');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(3, 9, 'Pablo Sánchez', '1005');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(3, 1, 'Sara González', '1006');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(4, 4, 'David Rodríguez', '1007');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(4, 8, 'Lucía Pérez', '1008');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(5, 7, 'Daniel Gómez', '1009');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(5, 20, 'Elena Díaz', '1010');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(6, 33, 'Javier Ruiz', '1011');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(6, 4, 'Marta Morales', '1012');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(7, 4, 'Carlos Álvarez', '1013');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(7, 9, 'Ana Ortega', '1014');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(8, 16, 'Luis Navarro', '1015');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(8, 10, 'Carmen Ramírez', '1016');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(1, 4, 'Alberto Torres', '1017');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(2, 1, 'Paula Ibáñez', '1018');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(3, 8, 'Miguel Romero', '1019');
INSERT INTO alumnos (id_grupo, puntos_acumulados, nombre_alum, numero_expediente) VALUES(4, 3, 'Isabel Hernández', '1020');

-- Inserción de partes de incidencia en la tabla 'partes_incidencia'
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(1, 1, 1, 1, 'Incidente menor', '2024-01-01', '09:00', 'Advertencia');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(2, 1, 2, 6, 'Incidente moderado', '2024-01-02', '10:00', 'Suspensión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(3, 2, 3, 12, 'Incidente grave', '2024-01-03', '11:00', 'Expulsión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(4, 2, 4, 1, 'Incidente menor', '2024-01-04', '12:00', 'Advertencia');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(5, 3, 5, 6, 'Incidente moderado', '2024-01-05', '13:00', 'Suspensión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(6, 3, 6, 12, 'Incidente grave', '2024-01-06', '14:00', 'Expulsión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(7, 4, 1, 1, 'Incidente menor', '2024-01-07', '15:00', 'Advertencia');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(8, 4, 2, 6, 'Incidente moderado', '2024-01-08', '16:00', 'Suspensión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(9, 5, 3, 12, 'Incidente grave', '2024-01-09', '17:00', 'Expulsión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(10, 5, 4, 1, 'Incidente menor', '2024-01-10', '18:00', 'Advertencia');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(11, 6, 5, 6, 'Incidente moderado', '2024-01-11', '09:00', 'Suspensión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(12, 6, 6, 12, 'Incidente grave', '2024-01-12', '10:00', 'Expulsión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(13, 7, 1, 1, 'Incidente menor', '2024-01-13', '11:00', 'Advertencia');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(14, 7, 2, 6, 'Incidente moderado', '2024-01-14', '12:00', 'Suspensión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(15, 8, 3, 12, 'Incidente grave', '2024-01-15', '13:00', 'Expulsión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(16, 8, 4, 1, 'Incidente menor', '2024-01-16', '14:00', 'Advertencia');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(17, 1, 5, 6, 'Incidente moderado', '2024-01-17', '15:00', 'Suspensión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(18, 2, 6, 12, 'Incidente grave', '2024-01-18', '16:00', 'Expulsión');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(19, 3, 1, 1, 'Incidente menor', '2024-01-19', '17:00', 'Advertencia');
INSERT INTO partes_incidencia (id_alum, id_grupo, id_profesor, id_punt_partes, descripcion, fecha, hora, sancion) VALUES(20, 4, 2, 6, 'Incidente moderado', '2024-01-20', '18:00', 'Suspensión');
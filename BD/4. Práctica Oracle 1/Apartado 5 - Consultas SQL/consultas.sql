--1
CREATE VIEW vista1
AS SELECT Nombre, Calle, "Código postal"
FROM Empleados, Domicilios
WHERE Empleados.DNI = Domicilios.DNI
ORDER BY "Código postal", Nombre;

--2
CREATE VIEW R2
AS SELECT Nombre, Empleados.DNI, Calle, "Código postal"
FROM Empleados LEFT OUTER JOIN Domicilios
ON Empleados.DNI = Domicilios.DNI
ORDER BY "Código postal", Nombre;

CREATE VIEW vista2
AS SELECT Nombre, R2.DNI, Calle, "Código postal", Teléfono
FROM R2, Teléfonos
WHERE R2.DNI = Teléfonos.DNI
ORDER BY Nombre;

--3
CREATE VIEW vista3
AS SELECT Nombre, R2.DNI, Calle, "Código postal", Teléfono
FROM R2 LEFT OUTER JOIN Teléfonos
ON R2.DNI = Teléfonos.DNI
ORDER BY Nombre;

--4
CREATE VIEW vista4
AS SELECT Nombre, DNI, Calle, Población, Provincia, R2."Código postal"
FROM R2 LEFT OUTER JOIN "Códigos postales"
ON R2."Código postal" = "Códigos postales"."Código postal"
ORDER BY Nombre;

--5
CREATE VIEW vista5
AS SELECT Nombre, vista4.DNI, Calle, Población, Provincia, "Código postal", Teléfono
FROM vista4 LEFT OUTER JOIN Teléfonos
ON vista4.DNI = Teléfonos.DNI
ORDER BY Nombre;

--6
UPDATE Empleados
SET Sueldo = Sueldo * 1.1 
WHERE Sueldo <= 1727;

--7
UPDATE Empleados
SET Sueldo = Sueldo / 1.1
WHERE Sueldo <= 1900;

--8
UPDATE Empleados
SET Sueldo = Sueldo * 1.1 
WHERE Sueldo <= 1454;

UPDATE Empleados
SET Sueldo = Sueldo / 1.1
WHERE Sueldo <= 1600;

/* ¿Qué ocurre con los datos originales?
Lo que ocurre es que a Laura López no se le puede aumentar el 10% del sueldo
porque superaría los 1600€, pero sí que se le disminuye ya que no tenemos en
cuenta si ese sueldo había sido aumentado posteriormente o no. */

--9
CREATE VIEW vista9
AS SELECT COUNT(Nombre) AS Empleados, MIN(Sueldo) AS "Sueldo mínimo", MAX(Sueldo) AS "Sueldo máximo", AVG(Sueldo) AS "Sueldo medio"
FROM Empleados;

--10
CREATE VIEW R10A
AS SELECT DNI, "Código postal", Población
FROM "Códigos postales" NATURAL LEFT OUTER JOIN Domicilios;

CREATE VIEW R10B
AS SELECT Sueldo, DNI, Población
FROM Empleados NATURAL RIGHT OUTER JOIN R10A;

CREATE VIEW vista10
AS SELECT DISTINCT REGR_AVGX(Población, Sueldo) OVER (PARTITION BY Población) AS "Sueldo medio", REGR_COUNT(Población, DNI) OVER (PARTITION BY Población) AS "Número empleados", Población
FROM R10B
ORDER BY Población;

--11
CREATE VIEW R11
AS SELECT Nombre, DNI, REGR_COUNT(DNI, Teléfono) OVER (PARTITION BY DNI) AS "Cantidad de teléfonos", Teléfono
FROM vista2;

CREATE VIEW vista11
AS SELECT DISTINC Nombre, DNI, Teléfono
FROM R11
WHERE "Cantidad de teléfonos" > 1
ORDER BY Nombre;

--12
CREATE VIEW R12A
AS SELECT Población, NULL AS Barcelona, NULL AS Córdoba, NULL AS Madrid, NULL AS Zaragoza
FROM "Códigos postales";

CREATE VIEW R12B
AS SELECT Población, "Código postal" AS Barcelona, NULL AS Córdoba, NULL AS Madrid, NULL AS Zaragoza
FROM "Códigos postales"
WHERE Provincia = 'Barcelona';

CREATE VIEW R12C
AS SELECT Población, NULL AS Barcelona, "Código postal" AS Córdoba, NULL AS Madrid, NULL AS Zaragoza
FROM "Códigos postales"
WHERE Provincia = 'Córdoba';

CREATE VIEW R12D
AS SELECT Población, NULL AS Barcelona, NULL AS Córdoba, "Código postal" AS Madrid, NULL AS Zaragoza
FROM "Códigos postales"
WHERE Provincia = 'Madrid';

CREATE VIEW R12E
AS SELECT Población, NULL AS Barcelona, NULL AS Córdoba, NULL AS Madrid, "Código postal" AS Zaragoza
FROM "Códigos postales"
WHERE Provincia = 'Zaragoza';

CREATE VIEW R12F
AS SELECT * FROM R12A UNION SELECT * FROM R12B;

CREATE VIEW R12G
AS SELECT * FROM R12F UNION SELECT * FROM R12C;

CREATE VIEW R12H
AS SELECT * FROM R12G UNION SELECT * FROM R12D;

CREATE VIEW R12I
AS SELECT * FROM R12H UNION SELECT * FROM R12E;

CREATE VIEW vista12
AS SELECT * FROM R12I MINUS SELECT * FROM R12A;




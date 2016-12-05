--1
CREATE VIEW vista1
AS SELECT Nombre, Calle, "C�digo postal"
FROM Empleados, Domicilios
WHERE Empleados.DNI = Domicilios.DNI
ORDER BY "C�digo postal", Nombre;

--2
CREATE VIEW R2
AS SELECT Nombre, Empleados.DNI, Calle, "C�digo postal"
FROM Empleados LEFT OUTER JOIN Domicilios
ON Empleados.DNI = Domicilios.DNI
ORDER BY "C�digo postal", Nombre;

CREATE VIEW vista2
AS SELECT Nombre, R2.DNI, Calle, "C�digo postal", Tel�fono
FROM R2, Tel�fonos
WHERE R2.DNI = Tel�fonos.DNI
ORDER BY Nombre;

--3
CREATE VIEW vista3
AS SELECT Nombre, R2.DNI, Calle, "C�digo postal", Tel�fono
FROM R2 LEFT OUTER JOIN Tel�fonos
ON R2.DNI = Tel�fonos.DNI
ORDER BY Nombre;

--4
CREATE VIEW vista4
AS SELECT Nombre, DNI, Calle, Poblaci�n, Provincia, R2."C�digo postal"
FROM R2 LEFT OUTER JOIN "C�digos postales"
ON R2."C�digo postal" = "C�digos postales"."C�digo postal"
ORDER BY Nombre;

--5
CREATE VIEW vista5
AS SELECT Nombre, vista4.DNI, Calle, Poblaci�n, Provincia, "C�digo postal", Tel�fono
FROM vista4 LEFT OUTER JOIN Tel�fonos
ON vista4.DNI = Tel�fonos.DNI
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

/* �Qu� ocurre con los datos originales?
Lo que ocurre es que a Laura L�pez no se le puede aumentar el 10% del sueldo
porque superar�a los 1600�, pero s� que se le disminuye ya que no tenemos en
cuenta si ese sueldo hab�a sido aumentado posteriormente o no. */

--9
CREATE VIEW vista9
AS SELECT COUNT(Nombre) AS Empleados, MIN(Sueldo) AS "Sueldo m�nimo", MAX(Sueldo) AS "Sueldo m�ximo", AVG(Sueldo) AS "Sueldo medio"
FROM Empleados;

--10
CREATE VIEW R10A
AS SELECT DNI, "C�digo postal", Poblaci�n
FROM "C�digos postales" NATURAL LEFT OUTER JOIN Domicilios;

CREATE VIEW R10B
AS SELECT Sueldo, DNI, Poblaci�n
FROM Empleados NATURAL RIGHT OUTER JOIN R10A;

CREATE VIEW vista10
AS SELECT DISTINCT REGR_AVGX(Poblaci�n, Sueldo) OVER (PARTITION BY Poblaci�n) AS "Sueldo medio", REGR_COUNT(Poblaci�n, DNI) OVER (PARTITION BY Poblaci�n) AS "N�mero empleados", Poblaci�n
FROM R10B
ORDER BY Poblaci�n;

--11
CREATE VIEW R11
AS SELECT Nombre, DNI, REGR_COUNT(DNI, Tel�fono) OVER (PARTITION BY DNI) AS "Cantidad de tel�fonos", Tel�fono
FROM vista2;

CREATE VIEW vista11
AS SELECT DISTINC Nombre, DNI, Tel�fono
FROM R11
WHERE "Cantidad de tel�fonos" > 1
ORDER BY Nombre;

--12
CREATE VIEW R12A
AS SELECT Poblaci�n, NULL AS Barcelona, NULL AS C�rdoba, NULL AS Madrid, NULL AS Zaragoza
FROM "C�digos postales";

CREATE VIEW R12B
AS SELECT Poblaci�n, "C�digo postal" AS Barcelona, NULL AS C�rdoba, NULL AS Madrid, NULL AS Zaragoza
FROM "C�digos postales"
WHERE Provincia = 'Barcelona';

CREATE VIEW R12C
AS SELECT Poblaci�n, NULL AS Barcelona, "C�digo postal" AS C�rdoba, NULL AS Madrid, NULL AS Zaragoza
FROM "C�digos postales"
WHERE Provincia = 'C�rdoba';

CREATE VIEW R12D
AS SELECT Poblaci�n, NULL AS Barcelona, NULL AS C�rdoba, "C�digo postal" AS Madrid, NULL AS Zaragoza
FROM "C�digos postales"
WHERE Provincia = 'Madrid';

CREATE VIEW R12E
AS SELECT Poblaci�n, NULL AS Barcelona, NULL AS C�rdoba, NULL AS Madrid, "C�digo postal" AS Zaragoza
FROM "C�digos postales"
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




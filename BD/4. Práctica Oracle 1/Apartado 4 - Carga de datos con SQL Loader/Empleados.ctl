LOAD DATA
INFILE 'C:\hlocal\Empleados.txt'
APPEND
INTO TABLE Empleados
FIELDS TERMINATED BY ';'
(Nombre,DNI,Sueldo)
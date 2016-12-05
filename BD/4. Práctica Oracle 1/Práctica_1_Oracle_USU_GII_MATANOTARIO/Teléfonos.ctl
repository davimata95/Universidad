LOAD DATA
INFILE 'C:\hlocal\Teléfonos.txt'
APPEND
INTO TABLE Teléfonos
FIELDS TERMINATED BY ';'
(DNI,Teléfono)
LOAD DATA
INFILE 'C:\hlocal\Domicilios.txt'
APPEND
INTO TABLE Domicilios
FIELDS TERMINATED BY ';'
(DNI,Calle,"Código postal")
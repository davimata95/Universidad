LOAD DATA
INFILE 'C:\hlocal\Domicilios I.txt'
APPEND
INTO TABLE "Domicilios I"
FIELDS TERMINATED BY ';'
(DNI,Calle,"Código postal")
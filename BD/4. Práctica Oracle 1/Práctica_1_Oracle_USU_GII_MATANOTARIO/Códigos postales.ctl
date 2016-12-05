LOAD DATA
INFILE 'C:\hlocal\Códigos postales.txt'
APPEND
INTO TABLE "Códigos postales"
FIELDS TERMINATED BY ';'
("Código postal",Población,Provincia)
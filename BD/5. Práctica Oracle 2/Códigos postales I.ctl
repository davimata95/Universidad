LOAD DATA
INFILE 'C:\hlocal\C�digos postales I.txt'
APPEND
INTO TABLE "C�digos postales I"
FIELDS TERMINATED BY ';'
("C�digo postal",Poblaci�n,Provincia)
LOAD DATA
INFILE 'C:\hlocal\Tel�fonos.txt'
APPEND
INTO TABLE Tel�fonos
FIELDS TERMINATED BY ';'
(DNI,Tel�fono)
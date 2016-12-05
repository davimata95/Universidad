--1
INSERT INTO Empleados
VALUES 
('Rodrigo', '987654321', 1500); 
('David', '987654321', 3500); 

/*Informe de error:
Error SQL: ORA-00001: restricci�n �nica (USU_GII_MATANOTARIO.EMPLEADOS_PK) violada
00001. 00000 -  "unique constraint (%s.%s) violated"
*Cause:    An UPDATE or INSERT statement attempted to insert a duplicate key.
           For Trusted Oracle configured in DBMS MAC mode, you may see
           this message if a duplicate entry exists at a different level.
*Action:   Either remove the unique restriction or do not insert the key.*/

--2
INSERT INTO Empleados (dni, sueldo)
VALUES 
('015674867', 1600); 

/*Informe de error:
Error SQL: ORA-01400: no se puede realizar una inserci�n NULL en ("USU_GII_MATANOTARIO"."EMPLEADOS"."NOMBRE")
01400. 00000 -  "cannot insert NULL into (%s)"
*Cause:    
*Action:*/

--3
INSERT INTO Empleados
VALUES
('Pablo', '123456789', 6000);

/*Informe de error:
Error SQL: ORA-02290: restricci�n de control (USU_GII_MATANOTARIO.SUELDO_ADMISIBLE) violada
02290. 00000 -  "check constraint (%s.%s) violated"
*Cause:    The values being inserted do not satisfy the named check
           
*Action:   do not insert values that violate the constraint.*/

--4
INSERT INTO Tel�fonos
VALUES
('675974645', '913458675');

/*Informe de error:
Error SQL: ORA-02291: restricci�n de integridad (USU_GII_MATANOTARIO.SYS_C0037736) violada - clave principal no encontrada
02291. 00000 - "integrity constraint (%s.%s) violated - parent key not found"
*Cause:    A foreign key value has no matching primary key value.
*Action:   Delete the foreign key or add a matching primary key.*/


--Creamos el empleado y el c�digo postal
INSERT INTO Empleados
VALUES 
('Rodrigo', '987654321', 1500); 

INSERT INTO "C�digos postales"
VALUES 
('28038', 'Madrid', 'Madrid');

--Le asignamos el telefono y el domicilio 
INSERT INTO Tel�fonos
VALUES 
('987654321', '915486592');

INSERT INTO Domicilios
VALUES 
('987654321', 'Juan', '28038');


--5
DELETE from "C�digos postales"
WHERE "C�digo postal" = '28038' ;

/*Informe de error:
Error SQL: ORA-02292: restricci�n de integridad (USU_GII_MATANOTARIO.SYS_C0037742) violada - registro secundario encontrado
02292. 00000 - "integrity constraint (%s.%s) violated - child record found"
*Cause:    attempted to delete a parent key value that had a foreign
           dependency.
*Action:   delete dependencies first then parent or disable constraint.*/

--6
DELETE from EMPLEADOS
WHERE DNI = '987654321' ;

/* 1 filas eliminado
(Y se elimina su correspondiente tel�fono y domicilio)*/


--7 
--Hay que cambiar la tabla de tel�fonos, on delete set null

DROP TABLE Tel�fonos ;

CREATE TABLE Tel�fonos(DNI Char(9) CONSTRAINT TEL�FONOS_FK REFERENCES Empleados (DNI) ON DELETE SET NULL,
                       Tel�fono Char(9),
                       CONSTRAINT TEL�FONOS_PK PRIMARY KEY (DNI, Tel�fono));

--Creamos el empleado y su tel�fono
INSERT INTO Empleados
VALUES 
('Rodrigo', '987654321', 1500); 

INSERT INTO Tel�fonos
VALUES 
('987654321', '915486592');

DELETE from EMPLEADOS
WHERE DNI = '987654321';

/*Informe de error:
Error SQL: ORA-01407: no se puede actualizar ("USU_GII_MATANOTARIO"."TEL�FONOS"."DNI") a un valor NULL
01407. 00000 -  "cannot update (%s) to NULL"
*Cause:    
*Action:*/
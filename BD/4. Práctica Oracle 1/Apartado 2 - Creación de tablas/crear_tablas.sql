DROP TABLE Domicilios;
DROP TABLE "C�digos postales";
DROP TABLE Tel�fonos;
DROP TABLE Empleados;

CREATE TABLE Empleados(Nombre Char(50) NOT NULL, 
                       DNI Char(9) CONSTRAINT EMPLEADOS_PK PRIMARY KEY, 
                       Sueldo Number(6,2) NOT NULL,
                       CONSTRAINT SUELDO_ADMISIBLE CHECK (Sueldo BETWEEN 645.30 AND 5000));
                         
CREATE TABLE Tel�fonos(DNI Char(9) CONSTRAINT TEL�FONOS_FK REFERENCES Empleados (DNI) ON DELETE CASCADE,
                       Tel�fono Char(9),
                       CONSTRAINT TEL�FONOS_PK PRIMARY KEY (DNI, Tel�fono));
 
CREATE TABLE "C�digos postales"("C�digo postal" Char(5) CONSTRAINT C�DIGOS_POSTALES_PK PRIMARY KEY,
                                Poblaci�n Char(50) NOT NULL,
                                Provincia Char(50) NOT NULL);
								
CREATE TABLE Domicilios(DNI Char(9) REFERENCES Empleados (DNI) ON DELETE CASCADE,
                        Calle varchar(50), 
                        "C�digo postal" Char(5) REFERENCES "C�digos postales" ("C�digo postal"),
                        CONSTRAINT DOMICILIOS_PK PRIMARY KEY (DNI, Calle, "C�digo postal"));					

						
/*SELECT TABLE_NAME, CONSTRAINT_TYPE, CONSTRAINT_NAME FROM USER_CONSTRAINTS; */

/*TABLE_NAME                     CONSTRAINT_TYPE CONSTRAINT_NAME              
------------------------------ --------------- ------------------------------
C�digos postales               C               SYS_C0037607                   
C�digos postales               C               SYS_C0037608       
C�digos postales               P               C�DIGOS_POSTALES_PK             
DOMICILIOS                     P               DOMICILIOS_PK   
DOMICILIOS                     R               SYS_C0037612                   
DOMICILIOS                     R               SYS_C0037611                   
EMPLEADOS                      C               SYS_C0037601                   
EMPLEADOS                      C               SYS_C0037602                   
EMPLEADOS                      C               SUELDO_ADMISIBLE 
EMPLEADOS                      P               EMPLEADOS_PK                   
TEL�FONOS                      P               TEL�FONOS_PK     
TEL�FONOS                      R               SYS_C0037606  */                       
                                                                     

DROP TABLE Domicilios;
DROP TABLE "Códigos postales";
DROP TABLE Teléfonos;
DROP TABLE Empleados;

CREATE TABLE Empleados(Nombre Char(50) NOT NULL, 
                       DNI Char(9) CONSTRAINT EMPLEADOS_PK PRIMARY KEY, 
                       Sueldo Number(6,2) NOT NULL,
                       CONSTRAINT SUELDO_ADMISIBLE CHECK (Sueldo BETWEEN 645.30 AND 5000));
                         
CREATE TABLE Teléfonos(DNI Char(9) CONSTRAINT TELÉFONOS_FK REFERENCES Empleados (DNI) ON DELETE CASCADE,
                       Teléfono Char(9),
                       CONSTRAINT TELÉFONOS_PK PRIMARY KEY (DNI, Teléfono));
 
CREATE TABLE "Códigos postales"("Código postal" Char(5) CONSTRAINT CÓDIGOS_POSTALES_PK PRIMARY KEY,
                                Población Char(50) NOT NULL,
                                Provincia Char(50) NOT NULL);
								
CREATE TABLE Domicilios(DNI Char(9) REFERENCES Empleados (DNI) ON DELETE CASCADE,
                        Calle varchar(50), 
                        "Código postal" Char(5) REFERENCES "Códigos postales" ("Código postal"),
                        CONSTRAINT DOMICILIOS_PK PRIMARY KEY (DNI, Calle, "Código postal"));					

						
/*SELECT TABLE_NAME, CONSTRAINT_TYPE, CONSTRAINT_NAME FROM USER_CONSTRAINTS; */

/*TABLE_NAME                     CONSTRAINT_TYPE CONSTRAINT_NAME              
------------------------------ --------------- ------------------------------
Códigos postales               C               SYS_C0037607                   
Códigos postales               C               SYS_C0037608       
Códigos postales               P               CÓDIGOS_POSTALES_PK             
DOMICILIOS                     P               DOMICILIOS_PK   
DOMICILIOS                     R               SYS_C0037612                   
DOMICILIOS                     R               SYS_C0037611                   
EMPLEADOS                      C               SYS_C0037601                   
EMPLEADOS                      C               SYS_C0037602                   
EMPLEADOS                      C               SUELDO_ADMISIBLE 
EMPLEADOS                      P               EMPLEADOS_PK                   
TELÉFONOS                      P               TELÉFONOS_PK     
TELÉFONOS                      R               SYS_C0037606  */                       
                                                                     

--1
CREATE TABLE butacas(id number(8) primary key,
                      evento  varchar(30),
                      fila    varchar(10),
                      columna varchar(10));
                    
CREATE TABLE reservas(id number(8) primary key,
                      evento  varchar(30),
                      fila    varchar(10),
                      columna varchar(10));
                      
CREATE SEQUENCE Seq_Butacas INCREMENT BY 1 START WITH 1 NOMAXVALUE;

CREATE SEQUENCE Seq_Reservas INCREMENT BY 1 START WITH 1 NOMAXVALUE

--Salida de Script:
table BUTACAS creado.
table RESERVAS creado.
sequence SEQ_BUTACAS creado.
sequence SEQ_RESERVAS creado.

--2
INSERT INTO butacas VALUES (Seq_Butacas.NEXTVAL,'Circo','1','1');
INSERT INTO butacas VALUES (Seq_Butacas.NEXTVAL,'Circo','1','2');
INSERT INTO butacas VALUES (Seq_Butacas.NEXTVAL,'Circo','1','3');
COMMIT;

--Salida de Script:
1 filas insertadas.
1 filas insertadas.
1 filas insertadas.
confirmado.

--3
--SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
SET serveroutput ON size 1000000;
set define on;
SET echo OFF;
SET verify OFF;
def v_evento='Circo';
def v_fila='1';
def v_columna='1';
variable v_error char(20)
/
declare
  v_existe varchar(20) default null;
begin
  select count(*) into v_existe from butacas where evento='&v_evento' and fila='&v_fila' and columna='&v_columna';
  if v_existe<>'0' then 
    select count(*) into v_existe from reservas where evento='&v_evento' and fila='&v_fila' and columna='&v_columna';
    if v_existe='0' then
      dbms_output.put_line('INFO: Se intenta reservar.');
      :v_error:='false';
    else
      dbms_output.put_line('ERROR: La localidad ya est? reservada.');
      :v_error:='true';
    end if;
  else
    dbms_output.put_line('ERROR: No existe esa localidad.');
    :v_error:='true';
  end if;
end;
/
col SCRIPT_COL new_val SCRIPT
select decode(:v_error,'false','"C:\hlocal\preguntar.sql"','"C:\hlocal\no_preguntar.sql"') as SCRIPT_COL from dual;
print :v_error
--prompt 'Valor script: '&SCRIPT
@ &SCRIPT
prompt &v_confirmar
/
begin
  if '&v_confirmar'='s' and :v_error='false' then
    insert into reservas values (Seq_Reservas.NEXTVAL,'&v_evento','&v_fila','&v_columna');
    dbms_output.put_line('INFO: Localidad reservada.');
  else
    dbms_output.put_line('INFO: No se ha reservado la localidad.');
  end if;
end;
/
COMMIT;

--Salida de Script:
bloque anónimo terminado
INFO: Se intenta reservar.

SCRIPT_COL                 
----------------------------
"C:\hlocal\preguntar.sql"    

V_ERROR
-----
false

'?Confirmar la reserva?'
s
bloque anónimo terminado
INFO: Localidad reservada.

confirmado.

--4
Volvemos a ejecutar el Script.

--Salida de Script:
bloque anónimo terminado
ERROR: La localidad ya est? reservada.

SCRIPT_COL                 
----------------------------
"C:\hlocal                   
o_preguntar.sql"             


V_ERROR
------

n
bloque anónimo terminado
INFO: No se ha reservado la localidad.

confirmado.

--5
SET AUTOCOMMIT OFF;
COMMIT;

--SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
SET serveroutput ON size 1000000;
set define on;
SET echo OFF;
SET verify OFF;
def v_evento='Circo';
def v_fila='1';
def v_columna='4';
variable v_error char(20)
/
declare
  v_existe varchar(20) default null;
begin
  select count(*) into v_existe from butacas where evento='&v_evento' and fila='&v_fila' and columna='&v_columna';
  if v_existe<>'0' then 
    select count(*) into v_existe from reservas where evento='&v_evento' and fila='&v_fila' and columna='&v_columna';
    if v_existe='0' then
      dbms_output.put_line('INFO: Se intenta reservar.');
      :v_error:='false';
    else
      dbms_output.put_line('ERROR: La localidad ya est? reservada.');
      :v_error:='true';
    end if;
  else
    dbms_output.put_line('ERROR: No existe esa localidad.');
    :v_error:='true';
  end if;
end;
/
col SCRIPT_COL new_val SCRIPT
select decode(:v_error,'false','"C:\hlocal\preguntar.sql"','"C:\hlocal\no_preguntar.sql"') as SCRIPT_COL from dual;
print :v_error
--prompt 'Valor script: '&SCRIPT
@ &SCRIPT
prompt &v_confirmar
/
begin
  if '&v_confirmar'='s' and :v_error='false' then
    insert into reservas values (Seq_Reservas.NEXTVAL,'&v_evento','&v_fila','&v_columna');
    dbms_output.put_line('INFO: Localidad reservada.');
  else
    dbms_output.put_line('INFO: No se ha reservado la localidad.');
  end if;
end;
/

--Salida de Script:
bloque anónimo terminado
ERROR: No existe esa localidad.

SCRIPT_COL                 
----------------------------
"C:\hlocal                   
o_preguntar.sql"             


V_ERROR
------

n
bloque anónimo terminado
INFO: No se ha reservado la localidad.

--6
--SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
SET serveroutput ON size 1000000;
set define on;
SET echo OFF;
SET verify OFF;
def v_evento='Circo';
def v_fila='1';
def v_columna='2';
variable v_error char(20)
/
declare
  v_existe varchar(20) default null;
begin
  select count(*) into v_existe from butacas where evento='&v_evento' and fila='&v_fila' and columna='&v_columna';
  if v_existe<>'0' then 
    select count(*) into v_existe from reservas where evento='&v_evento' and fila='&v_fila' and columna='&v_columna';
    if v_existe='0' then
      dbms_output.put_line('INFO: Se intenta reservar.');
      :v_error:='false';
    else
      dbms_output.put_line('ERROR: La localidad ya est? reservada.');
      :v_error:='true';
    end if;
  else
    dbms_output.put_line('ERROR: No existe esa localidad.');
    :v_error:='true';
  end if;
end;
/
col SCRIPT_COL new_val SCRIPT
select decode(:v_error,'false','"C:\hlocal\preguntar.sql"','"C:\hlocal\no_preguntar.sql"') as SCRIPT_COL from dual;
print :v_error
--prompt 'Valor script: '&SCRIPT
@ &SCRIPT
prompt &v_confirmar
/
begin
  if '&v_confirmar'='s' and :v_error='false' then
    insert into reservas values (Seq_Reservas.NEXTVAL,'&v_evento','&v_fila','&v_columna');
    dbms_output.put_line('INFO: Localidad reservada.');
  else
    dbms_output.put_line('INFO: No se ha reservado la localidad.');
  end if;
end;
/

--Salida de Script:
bloque anónimo terminado
INFO: Se intenta reservar.

SCRIPT_COL                 
----------------------------
"C:\hlocal\preguntar.sql"    

V_ERROR
-----
false

'?Confirmar la reserva?'
s
bloque anónimo terminado
INFO: Localidad reservada.

--7
--Abrir una nueva instancia de SQL Developer
--SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
SET serveroutput ON size 1000000;
set define on;
SET echo OFF;
SET verify OFF;
def v_evento='Circo';
def v_fila='1';
def v_columna='2';
variable v_error char(20)
/
declare
  v_existe varchar(20) default null;
begin
  select count(*) into v_existe from butacas where evento='&v_evento' and fila='&v_fila' and columna='&v_columna';
  if v_existe<>'0' then 
    select count(*) into v_existe from reservas where evento='&v_evento' and fila='&v_fila' and columna='&v_columna';
    if v_existe='0' then
      dbms_output.put_line('INFO: Se intenta reservar.');
      :v_error:='false';
    else
      dbms_output.put_line('ERROR: La localidad ya est? reservada.');
      :v_error:='true';
    end if;
  else
    dbms_output.put_line('ERROR: No existe esa localidad.');
    :v_error:='true';
  end if;
end;
/
col SCRIPT_COL new_val SCRIPT
select decode(:v_error,'false','"C:\hlocal\preguntar.sql"','"C:\hlocal\no_preguntar.sql"') as SCRIPT_COL from dual;
print :v_error
--prompt 'Valor script: '&SCRIPT
@ &SCRIPT
prompt &v_confirmar
/
begin
  if '&v_confirmar'='s' and :v_error='false' then
    insert into reservas values (Seq_Reservas.NEXTVAL,'&v_evento','&v_fila','&v_columna');
    dbms_output.put_line('INFO: Localidad reservada.');
  else
    dbms_output.put_line('INFO: No se ha reservado la localidad.');
  end if;
end;
/
COMMIT;

--Salida de Script:
bloque anónimo terminado
INFO: Se intenta reservar.

SCRIPT_COL                 
----------------------------
"C:\hlocal\preguntar.sql"    

V_ERROR
-----
false

'?Confirmar la reserva?'
s
bloque anónimo terminado
INFO: Localidad reservada.

confirmado.

--8 (Confirmar la reserva DEL PUNTO 6)
COMMIT;
--Lo que sucede es que se realiza la misma reserva 2 veces.

--9
--Lo he arreglado añadiendo el siguiente código al final del Script:
/
declare
  v_existe_2 number;
begin
  select count(*) into v_existe_2 from reservas where evento='&v_evento' and fila='&v_fila' and columna='&v_columna';
   if v_existe_2 > 1 then
    ROLLBACK;
  end if;
end;
/
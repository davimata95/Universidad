David Mata Lorenzo
Rodrigo Notario Perez

/abolish

create table programadores(dni string primary key, nombre string, direcci�n string, tel�fono string);
insert into programadores values('1','Jacinto','Jazm�n 4','91-8888888');
insert into programadores values('2','Herminia','Rosa 4','91-7777777');
insert into programadores values('3','Calixto','Clavel 3','91-1231231');
insert into programadores values('4','Teodora','Petunia 3','91-6666666');

create table analistas(dni string primary key, nombre string, direcci�n string, tel�fono string);
insert into analistas values('4','Teodora','Petunia 3','91-6666666');
insert into analistas values('5','Evaristo','Luna 1','91-1111111');
insert into analistas values('6','Luciana','J�piter 2','91-8888888');
insert into analistas values('7','Nicodemo','Plut�n 3',NULL);

create table distribuci�n(c�digoPr string, dniEmp string, horas int, primary key (c�digoPr, dniEmp));
insert into distribuci�n values('P1','1',10);
insert into distribuci�n values('P1','2',40);
insert into distribuci�n values('P1','4',5);
insert into distribuci�n values('P2','4',10);
insert into distribuci�n values('P3','1',10);
insert into distribuci�n values('P3','3',40);
insert into distribuci�n values('P3','4',5);
insert into distribuci�n values('P3','5',30);
insert into distribuci�n values('P4','4',20);
insert into distribuci�n values('P4','5',10);

create table proyectos(c�digo string primary key, descripci�n string, dniDir string);
insert into proyectos values('P1','N�mina','4');
insert into proyectos values('P2','Contabilidad','4');
insert into proyectos values('P3','Producci�n','5');
insert into proyectos values('P4','Clientes','5');
insert into proyectos values('P5','Ventas','6');

-- Vista 1
create view vista1 as select dni from programadores union select dni from analistas 

-- Vista 2
create view vista2 as select dni from programadores intersect select dni from analistas 

-- Vista 3
create view R3 as select dniEmp from distribuci�n union select dniDir from proyectos
create view vista3 as select dni from vista1 except select dniEmp from R3

-- Vista 4
create view R4 as select c�digoPr from distribuci�n where distribuci�n.dniEmp = select dni from analistas
create view vista4 as select c�digo from proyectos except select c�digoPr from R4

--Vista5
create view R5 as select dniDir from proyectos where proyectos.dniDir = select dni from analistas
create view vista5 as select dniDir from R5 except select dni from programadores

--Vista6
create view R6 as select c�digoPr, dniEmp, horas, descripci�n from distribuci�n, proyectos where proyectos.c�digo = distribuci�n.c�digoPr
create view vista6 as select descripci�n, nombre, horas from R6, programadores where R6.dniEmp = programadores.dni 

--Vista7
create view R7A as select tel�fono as tlf from programadores
create view R7B as select tel�fono, dni from R7A, analistas where R7A.tlf = analistas.tel�fono
create view vista7 as select tel�fono from R7B, vista2 where vista2.dni <> R7B.dni

--Vista8
create view vista8 as select dni from programadores natural join analistas 

--Vista9
create view vista9 as select dniEmp, sum(horas) as horas from distribuci�n group by dniEmp

--Vista10
create view R10 as select dni, nombre from programadores union select dni, nombre from analistas 
create view vista10 as select dni, nombre, c�digoPr from distribuci�n right outer join R10 on distribuci�n.dniEmp = R10.dni

--Vista11
create view R11 as select dni, nombre, tel�fono from programadores union select dni, nombre, tel�fono from analistas
create view vista11 as select dni, nombre from R11 where tel�fono is null

--Vista12
create view R12A as select dniEmp, avg(horas) as horas from distribuci�n group by dniEmp
create view R12B as select avg(horas) as mediaHoras from distribuci�n
create view vista12 as select dniEmp from R12A, R12B where R12A.horas < R12B.mediaHoras

--Vista13
create view R13A as select dni from R10 where nombre = 'Evaristo'
create view R13B as select c�digoPr from R13A, distribuci�n where dni=dniEmp 
create view R13C as select dniEmp, c�digoPr from distribuci�n
create view vista13 as select * from R13C division select * from R13B

--Vista14
create view R14A as select dniEmp from distribuci�n
create view R14B as select * from R14A, R13B
create view R14C as select dniEmp from (select * from R14B minus select * from R13C)
create view vista14 as select * from (select * from R14A minus select * from R14C)

--vista15
create view R15A as select dniEmp from R13B inner join distribuci�n on R13B.c�digoPr = distribuci�n.c�digoPr
create view R15B as select dni, nombre from programadores union select dni, nombre from analistas;
create view R15C as select dni from R15B minus select dniEmp from R15A;
create view vista15 as select c�digoPr, dni, horas*1.2 as horas from distribuci�n, R15C where dniEmp = dni;

--vista16
create view R16A as select c�digo from R13A, proyectos where dniDir = dni;
create view R16B as select * from R16A right join select * from R13C on c�digoPr = c�digo;
create view R16C as select dniEmp from R16B minus select * from R13A;
create view vista16 as select nombre from R16C, R15B where dniEmp = dni;

select * from vista1;
select * from vista2;
select * from vista3;
select * from vista4;
select * from vista5;
select * from vista6;
select * from vista7;
select * from vista8;
select * from vista9;
select * from vista10;
select * from vista11;
select * from vista12;
select * from vista13;
select * from vista14;
select * from vista15;
select * from vista16;

/*drop database if exists DBProyectoDomotica;
create database DBProyectoDomotica;
use DBProyectoDomotica;*/
drop database if exists blirukoibzfmw7p6xec7;
create database blirukoibzfmw7p6xec7;
use blirukoibzfmw7p6xec7;
/*buscar la base de datos remota en clever cloud*/
create table Rol(
	codigoRol int not null auto_increment,
    rol varchar(25) not null,
	primary key PK_codigoRol(codigoRol)
);

create table Usuario( 
	usuario varchar(80) not null,
	password0 varchar(25) not null,
	rol_codigoRol int not null,
    primary key PK_usuario(usuario),
    constraint FK_Usuario_Rol foreign key (rol_codigoRol) references Rol(codigoRol)
);

create table Persona(
	codigoPersona int not null auto_increment,
    DPI varchar(15) not null,
    nombrePersona varchar(125) not null,
    fechaIngreso date not null,
    primary key PK_codigoPersona(codigoPersona)
);





/*ROL*******************************************************/
/*Listar Roles***************************************/
delimiter $$
	create procedure sp_ListarRoles()
		begin 
			select 
				blirukoibzfmw7p6xec7.Rol.codigoRol,
                blirukoibzfmw7p6xec7.Rol.rol
			from blirukoibzfmw7p6xec7.Rol;
        end $$
delimiter ;

call sp_ListarRoles();
/************************************************************************/

/*Agregar Roles***********************************************************/
delimiter $$
	create procedure sp_AgregarRoles( in roles varchar(25))
		begin
			insert into blirukoibzfmw7p6xec7.Rol(rol)
				values(roles);
		end $$
delimiter ;

call sp_AgregarRoles('Administrador');
call sp_AgregarRoles('Usuario');
/*************************************************************************/






/*USUARIO********************************************************************/
/*Listar Usuario************************************************************/
delimiter $$
	create procedure sp_ListarUsuarios()
		begin
			select 
				blirukoibzfmw7p6xec7.Usuario.usuario,
                blirukoibzfmw7p6xec7.Usuario.password0,
                blirukoibzfmw7p6xec7.Usuario.rol_codigoRol
			from blirukoibzfmw7p6xec7.Usuario;
        end $$
delimiter ;

call sp_ListarUsuarios();
/***************************************************************************/

/*Agregar Usuario**********************************************************/
delimiter $$
	create procedure sp_AgregarUsuario(in user0 varchar(80), pass varchar(25), rol0 int)
		begin
			insert into blirukoibzfmw7p6xec7.Usuario(usuario, password0, rol_codigoRol)
				values (user0, pass, rol0);
        end $$
delimiter ;

call sp_AgregarUsuario('Admin', 'Admin', 1);
call sp_AgregarUsuario('User', 'User', 2);
call sp_AgregarUsuario('Barker', 'Barker', 1);
call sp_AgregarUsuario('Javier', 'Javier', 2);
/****************************************************************************/



/*Buscar Usuario************************************************************/
delimiter $$
	create procedure sp_BuscarUsuario(in user0 varchar(80), pass varchar(25), rol0 int)
		begin
			select 
				blirukoibzfmw7p6xec7.Usuario.usuario,
                blirukoibzfmw7p6xec7.Usuario.password0,
                blirukoibzfmw7p6xec7.Usuario.rol_codigoRol
			from blirukoibzfmw7p6xec7.Usuario where binary
				blirukoibzfmw7p6xec7.Usuario.usuario = user0 and
                blirukoibzfmw7p6xec7.Usuario.password0 = pass and
                blirukoibzfmw7p6xec7.Usuario.rol_codigoRol = rol0;
        end $$
delimiter ;

call sp_BuscarUsuario('Admin','Admin',1);
/********************************************************************************/







/*PERSONA****************************************************************************/
/*Listar Persona*********************************************************************/
delimiter $$
	create procedure sp_ListarPersonas()
		begin
			select 
				blirukoibzfmw7p6xec7.Persona.codigoPersona,
                blirukoibzfmw7p6xec7.Persona.DPI,
                blirukoibzfmw7p6xec7.Persona.nombrePersona,
                blirukoibzfmw7p6xec7.Persona.fechaIngreso
			from blirukoibzfmw7p6xec7.Persona;
		end $$
delimiter ;

call sp_ListarPersonas();
/**************************************************************************************/

/*Agregar Persona**********************************************************************/
delimiter $$
	create procedure sp_AgregarPersona(in dpi varchar(25), nombre varchar(125), fecha date)
		begin
			insert into blirukoibzfmw7p6xec7.Persona (DPI, nombrePersona, fechaIngreso)
				values(dpi, nombre, fecha);
        end $$
delimiter ;

call sp_AgregarPersona("248515541", "Javier Barker", "2020/05/14");
call sp_AgregarPersona("562215154", "Jose Gongora", "2020/11/11");
call sp_AgregarPersona("112648148", "Leonel Lopez", "2020/10/20");
call sp_AgregarPersona("447125485", "Pablo Lopez", "2020/10/04");
call sp_AgregarPersona("445111177", "Sergio Mayen", "2020/06/17");
/*************************************************************************************/

/*Actualizar Persona******************************************************************/
delimiter $$
	create procedure sp_ActualizarPersona(in id int, dpi varchar(25), nombre varchar(125), fecha date)
		begin
			update blirukoibzfmw7p6xec7.Persona set DPI=dpi, nombrePersona=nombre, fechaIngreso=fecha
				where codigoPersona=id;
        end $$
delimiter ;

call sp_ActualizarPersona(1, "244444444", "Javier Badfddfdf", "2020/09/15");
/***************************************************************************************/



/*Eliminar Persona********************************************************************/
delimiter $$
	create procedure sp_EliminarPersona(in id int)
		begin
			delete from blirukoibzfmw7p6xec7.Persona
				where codigoPersona=id;
        end $$
delimiter ;

/*call sp_EliminarPersona(2);*/
/*************************************************************************************/

/*Buscar Persona**********************************************************************/
delimiter $$
	create procedure sp_BuscarPersona(in id int)
		begin
			select 
				blirukoibzfmw7p6xec7.Persona.codigoPersona,
                blirukoibzfmw7p6xec7.Persona.DPI,
                blirukoibzfmw7p6xec7.Persona.nombrePersona,
                blirukoibzfmw7p6xec7.Persona.fechaIngreso
			from blirukoibzfmw7p6xec7.Persona where blirukoibzfmw7p6xec7.Persona.codigoPersona=id;
        end $$
delimiter ;

call sp_BuscarPersona(2);
/***********************************************************************************/

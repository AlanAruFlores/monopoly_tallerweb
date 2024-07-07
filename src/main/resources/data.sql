/*INSERT DE UN USUARIO POR DEFECTO*/
INSERT INTO usuario (id,nombre,apellido,email,password,victorias,nombreUsuario,imagenPerfil,baneado) VALUES(1,"usuario","usuario","usuario@gmail.com","usuario1234",4,"UsuarioNombre1","/imagenes/fotosPerfil/perfil1.png",false);
INSERT INTO usuario (id,nombre,apellido,email,password,victorias,nombreUsuario,imagenPerfil,baneado) VALUES(2,"karl","karl","karl@gmail.com","karl1234",10,"karlos","/imagenes/fotosPerfil/perfil2.png",false);
INSERT INTO usuario (id,nombre,apellido,email,password,victorias,nombreUsuario,imagenPerfil,baneado) VALUES(3,"juan","juan","juan@gmail.com","juan1234",8,"juanceto07","/imagenes/fotosPerfil/perfil3.png",false);

/*INSERT PARTIDA (PRUEBA)*/
/*INSERT INTO partida(id,estadoPartida,fechaApertura,nombre,numeroJugadores,creador_id,turnoJugador_id) values (1,"ABIERTA",CURDATE(),"ASDpartida",3,1,1);*/

/*INSERCION DE LAS PROPIEDADES (no eliminar)*/
/*CASAS CELESTES Y SU TREN*/
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Calle Balcarce", "/imagenes/propiedades/celeste/balcarce.png",100,2,"#51bcf5",80);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Avenida Belgrano", "/imagenes/propiedades/celeste/belgrano.png",100,3,"#51bcf5",80);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Tren Roca", "/imagenes/propiedades/trenes/roca.png",500,4,"#ffffff",400);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Calle Juramento", "/imagenes/propiedades/celeste/juramento.png",100,5,"#51bcf5",80);

/*CASAS ROSAS Y SU SERVICIO*/
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Avenida 9 de Julio","/imagenes/propiedades/rosa/9julio.png",200,7,"#f1004e",150);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Calle Aribeño", "/imagenes/propiedades/rosa/arribeños.png",200,8,"#f1004e",150);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Servicio de Luz", "/imagenes/propiedades/utilidades/edenor.png",250,9,"#ffffff",200);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Calle Mendoza", "/imagenes/propiedades/rosa/mendoza.png",200,10,"#f1004e",150);

/*CASAS ROJAS Y SU TREN*/
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Avenida de Mayo", "/imagenes/propiedades/roja/mayo.png",400,12,"#d1001f",300);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Avenida Rivadavia", "/imagenes/propiedades/roja/rivadavia.png",400,13,"#d1001f",300);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Tren Sarmiento", "/imagenes/propiedades/trenes/sarmiento.png",500,14,"#ffffff",400);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Florencio Varela", "/imagenes/propiedades/roja/varela.png",400,15,"#d1001f",300);

/*CASAS VERDES Y SU SERVICIO*/
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Avenida Corrientes", "/imagenes/propiedades/verde/corrientes.png",600,17,"#16914f",500);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Avenida Florida", "/imagenes/propiedades/verde/florida.png",600,18,"#16914f",500);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Aysa", "/imagenes/propiedades/utilidades/aysa.png",350,19,"ffffff",300);
insert into propiedad(nombre,imagen,precio,nroCasilla,color,precioHipoteca) values("Avenida Santa Fé", "/imagenes/propiedades/verde/santafe.png",600,20,"#16914f",500);

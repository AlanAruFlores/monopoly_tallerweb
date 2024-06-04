/*INSERT DE UN USUARIO POR DEFECTO*/
INSERT INTO usuario (id,nombre,apellido,email,password,victorias,nombreUsuario) VALUES(1,"usuario","usuario","usuario@gmail.com","usuario1234",0,"UsuarioNombre1");
INSERT INTO usuario (id,nombre,apellido,email,password,victorias,nombreUsuario) VALUES(2,"karl","karl","karl@gmail.com","karl1234",0,"karlos");
INSERT INTO usuario (id,nombre,apellido,email,password,victorias,nombreUsuario) VALUES(3,"juan","juan","juan@gmail.com","juan1234",0,"juanceto07");

/*INSERT PARTIDA (PRUEBA)*/
INSERT INTO partida(id,estadoPartida,fechaApertura,nombre,numeroJugadores,creador_id,turnoJugador_id) values (1,"ABIERTA",CURDATE(),"ASDpartida",3,1,1);

/*INSERCION DE LAS PROPIEDADES (no eliminar)*/
/*CASAS CELESTES Y SU TREN*/
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Calle Balcarce", "/imagenes/propiedades/celeste/balcarce.png",100,2);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Avenida Belgrano", "/imagenes/propiedades/celeste/belgrano.png",100,3);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Tren Roca", "/imagenes/propiedades/trenes/roca.png",500,4);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Calle Juramento", "/imagenes/propiedades/celeste/juramento.png",100,5);

/*CASAS ROSAS Y SU SERVICIO*/
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Avenida 9 de Julio","/imagenes/propiedades/rosa/9julio.png",200,7);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Calle Aribeño", "/imagenes/propiedades/rosa/arribeños.png",200,8);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Servicio de Luz", "/imagenes/propiedades/utilidades/edenor.png",250,9);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Calle Mendoza", "/imagenes/propiedades/rosa/mendoza.png",200,10);

/*CASAS ROJAS Y SU TREN*/
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Avenida de Mayo", "/imagenes/propiedades/roja/mayo.png",400,12);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Avenida Rivadavia", "/imagenes/propiedades/roja/rivadavia.png",400,13);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Tren Sarmiento", "/imagenes/propiedades/trenes/sarmiento.png",500,14);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Florencio Varela", "/imagenes/propiedades/roja/varela.png",400,15);

/*CASAS VERDES Y SU SERVICIO*/
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Avenida Corrientes", "/imagenes/propiedades/verde/corrientes.png",600,17);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Avenida Florida", "/imagenes/propiedades/verde/florida.png",600,18);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Aysa", "/imagenes/propiedades/utilidades/aysa.png",350,19);
insert into propiedad(nombre,imagen,precio,nroCasilla) values("Avenida Santa Fé", "/imagenes/propiedades/verde/santafe.png",600,20);

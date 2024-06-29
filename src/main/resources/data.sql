/*INSERT DE UN USUARIO POR DEFECTO*/
/*INSERT DE UN JUGADOR POR DEFECTO*/
INSERT INTO jugador(usuario_id,posicionCasilla,saldo) VALUES (1,1,1000);

/*INSERCION DE LAS PROPIEDADES (no eliminar)*/
/*CASAS CELESTES Y SU TREN*/
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Calle Balcarce", "/imagenes/propiedades/celeste/balcarce.png",100,true,2);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Avenida Belgrano", "/imagenes/propiedades/celeste/belgrano.png",100,true,3);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Tren Roca", "/imagenes/propiedades/trenes/roca.png",500,true,4);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Calle Juramento", "/imagenes/propiedades/celeste/juramento.png",100,true,5);

/*CASAS ROSAS Y SU SERVICIO*/
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Avenida 9 de Julio","/imagenes/propiedades/rosa/9julio.png",200,true,7);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Calle Aribeño", "/imagenes/propiedades/rosa/arribeños.png",200,true,8);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Servicio de Luz", "/imagenes/propiedades/utilidades/edenor.png",250,true,9);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Calle Mendoza", "/imagenes/propiedades/rosa/mendoza.png",200,true,10);

/*CASAS ROJAS Y SU TREN*/
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Avenida de Mayo", "/imagenes/propiedades/roja/mayo.png",400,true,12);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Avenida Rivadavia", "/imagenes/propiedades/roja/rivadavia.png",400,true,13);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Tren Sarmiento", "/imagenes/propiedades/trenes/sarmiento.png",500,true,14);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Florencio Varela", "/imagenes/propiedades/roja/varela.png",400,true,15);

/*CASAS VERDES Y SU SERVICIO*/
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Avenida Corrientes", "/imagenes/propiedades/verde/corrientes.png",600,true,17);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Avenida Florida", "/imagenes/propiedades/verde/florida.png",600,true,18);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Aysa", "/imagenes/propiedades/utilidades/aysa.png",350,true,19);
insert into propiedad(nombre,imagen,precio,disponibilidad,nroCasilla) values("Avenida Santa Fé", "/imagenes/propiedades/verde/santafe.png",600,true,20);

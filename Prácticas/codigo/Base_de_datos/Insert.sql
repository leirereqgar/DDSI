INSERT INTO Edicion (Año) VALUES (2015);

INSERT INTO Edicion (Año) VALUES (2016);

INSERT INTO Edicion (Año) VALUES (2020);

INSERT INTO Edicion (Año) VALUES (2019);

/******************************************************************************************************************************/

INSERT INTO Jugador(idJugador,nombreJ, apellidoJ, telefonoJ, emailJ)
VALUES ('1','Juan','Perez','999000999','juan@gmail.com');

INSERT INTO Jugador(idJugador,nombreJ, apellidoJ, telefonoJ, emailJ)
VALUES ('2','Paco','Fiestas','999111999','paco@gmail.com');

INSERT INTO Jugador(idJugador,nombreJ, apellidoJ, telefonoJ, emailJ)
VALUES ('3','Mari','Gomez','999222999','mari@gmail.com');

INSERT INTO Jugador(idJugador,nombreJ, apellidoJ, telefonoJ, emailJ)
VALUES ('4','Sara','Sarao','999333999','sara@gmail.com');


/******************************************************************************************************************************/

INSERT INTO Entrenador(idEntrenador, nombreE,apellidoE,telefonoE,emailE)
VALUES (1, 'Alberto', 'Lopez', '342567687', 'albi@gmail.com');

INSERT INTO Entrenador(idEntrenador, nombreE,apellidoE,telefonoE,emailE)
VALUES (2, 'Miguel', 'Morena', '754756386', 'morc@gmail.com');

INSERT INTO Entrenador(idEntrenador, nombreE,apellidoE,telefonoE,emailE)
VALUES (3, 'Pedro', 'Hidalgo', '456789853', 'pdrh@gmail.com');

INSERT INTO Entrenador(idEntrenador, nombreE,apellidoE,telefonoE,emailE)
VALUES (4, 'Curro', 'Toledano', '124567890', 'Curreoat@gmail.com');

/******************************************************************************************************************************/

INSERT INTO Partido(idPartido,fecha,resultado)
VALUES(1,TO_TIMESTAMP('2016-08-06 08:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),'1-5');

INSERT INTO Partido(idPartido,fecha,resultado)
VALUES(2,TO_TIMESTAMP('2015-08-07 08:15:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),'2-2');

INSERT INTO Partido(idPartido,fecha)
VALUES(3,TO_TIMESTAMP('2020-08-08 08:16:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'));

INSERT INTO Partido(idPartido,fecha,resultado)
VALUES(4,TO_TIMESTAMP('2019-08-09 08:17:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),'4-3');

/******************************************************************************************************************************/

INSERT INTO Arbitro(idArbitro, nombreA,apellidoA,telefonoA,emailA)
VALUES (1, 'Luis', 'de Haro', '593591125', 'ajskd@gmail.com');

INSERT INTO Arbitro(idArbitro, nombreA,apellidoA,telefonoA,emailA)
VALUES (2, 'Raul', 'Gonzalez', '123567659', 'qwasdew@gmail.com');

INSERT INTO Arbitro(idArbitro, nombreA,apellidoA,telefonoA,emailA)
VALUES (3, 'Enrique', 'Camacho', '456789012', 'camch@gmail.com');

INSERT INTO Arbitro(idArbitro, nombreA,apellidoA,telefonoA,emailA)
VALUES (4, 'Critina', 'Reina', '981234567', 'cris@gmail.com');

/******************************************************************************************************************************/

INSERT INTO Personal (idPersonal, nombreP, apellidosP, emailP, telefonoP)
VALUES (1, 'Javier', 'Martinez Perez', 'javier@gmail.com', 12345678);

INSERT INTO Personal (idPersonal, nombreP, apellidosP, emailP, telefonoP)
VALUES (2, 'Lorena', 'Sanchez Ortega', 'lorena@gmail.com', 12995112);

INSERT INTO Personal (idPersonal, nombreP, apellidosP, emailP, telefonoP)
VALUES (3, 'Laura', 'Catena Carrasco', 'laura@gmail.com', 12345030);

INSERT INTO Personal (idPersonal, nombreP, apellidosP, emailP, telefonoP)
VALUES (4, 'Felipe', 'Fernandez Fernandez', 'felipe@gmail.com', 12300678);

/******************************************************************************************************************************/

INSERT INTO Pista (idPista, nombre, capacidad) VALUES (1, 'Pista Uno', 6);

INSERT INTO Pista (idPista, nombre, capacidad) VALUES (2, 'Pista Dos', 16);

INSERT INTO Pista (idPista, nombre, capacidad) VALUES (3, 'Pista Tres', 8);

INSERT INTO Pista (idPista, nombre, capacidad) VALUES (4, 'Pista Cuatro', 7);

/******************************************************************************************************************************/

INSERT INTO Entidad(idEntidad, nombreEn, persona_de_contacto, telefonoEn, emailEn)
VALUES (1, 'Gestoría García', 'Isabel', '987546523', 'asbdsk@ddnbcls.com');

INSERT INTO Entidad(idEntidad, nombreEn, persona_de_contacto, telefonoEn, emailEn)
VALUES (2, 'Traducciones Tradurea', 'Alba', '568785234', 'qqqqqqq@ttttttt.com');

INSERT INTO Entidad(idEntidad, nombreEn, persona_de_contacto, telefonoEn, emailEn)
VALUES (3, 'Los Rigos', 'Pepel', '111223344', 'ooooooo@ddddd.com');

INSERT INTO Entidad(idEntidad, nombreEn, persona_de_contacto, telefonoEn, emailEn)
VALUES (4, 'El Señorío', 'Pablo', '777889944', 'vdr@kjdls.com');

/******************************************************************************************************************************/

INSERT INTO Material(idMaterial,nombre) VALUES(1,'palas');

INSERT INTO Material(idMaterial,nombre) VALUES(2,'pelotas');

INSERT INTO Material(idMaterial,nombre) VALUES(3,'botellas de agua');

INSERT INTO Material(idMaterial,nombre) VALUES(4,'Toallas');

/******************************************************************************************************************************/

INSERT INTO Pedido(numero_pedido) VALUES(1);

INSERT INTO Pedido(numero_pedido) VALUES(2);

INSERT INTO Pedido(numero_pedido) VALUES(3);

INSERT INTO Pedido(numero_pedido) VALUES(4);

/******************************************************************************************************************************/

INSERT INTO SeJuegaEN (idPartido,idPista) VALUES (1,1);

INSERT INTO SeJuegaEN (idPartido,idPista) VALUES (2,2);

INSERT INTO SeJuegaEN (idPartido,idPista) VALUES (3,3);

INSERT INTO SeJuegaEN (idPartido,idPista) VALUES (4,4);

/******************************************************************************************************************************/

INSERT INTO Arbitrado (idPartido,idArbitro) VALUES (1,1);

INSERT INTO Arbitrado (idPartido,idArbitro) VALUES (2,2);

INSERT INTO Arbitrado (idPartido,idArbitro) VALUES (3,3);

INSERT INTO Arbitrado (idPartido,idArbitro) VALUES (4,4);

/******************************************************************************************************************************/

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (1,2);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (1,3);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (1,4);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (2,1);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (2,3);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (2,4);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (3,1);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (3,2);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (3,4);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (4,1);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (4,2);

INSERT INTO Pareja(idJugador1, idJugador2) VALUES (4,3);

/******************************************************************************************************************************/

INSERT INTO Inscrita(idJugador1, idJugador2, Año)
VALUES ('1','2','2020');

INSERT INTO Inscrita(idJugador1, idJugador2, Año)
VALUES ('3','4','2020');

INSERT INTO Inscrita(idJugador1, idJugador2, Año, Pos_ranking)
VALUES ('1','3','2015','6');

INSERT INTO Inscrita(idJugador1, idJugador2, Año, Pos_ranking)
VALUES ('2','4','2019','3');

/******************************************************************************************************************************/

INSERT INTO Participan(IdJugador1,idJugador2,Año,idPartido) VALUES(1,2,2020,2);

INSERT INTO Participan(IdJugador1,idJugador2,Año,idPartido) VALUES(3,4,2020,1);

INSERT INTO Participan(IdJugador1,idJugador2,Año,idPartido) VALUES(1,3,2015,3);

INSERT INTO Participan(IdJugador1,idJugador2,Año,idPartido) VALUES(2,4,2019,4);

/******************************************************************************************************************************/

INSERT INTO Jugado (idPartido,Año) VALUES (1,2016);

INSERT INTO Jugado (idPartido,Año) VALUES (2,2015);

INSERT INTO Jugado (idPartido,Año) VALUES (3,2020);

INSERT INTO Jugado (idPartido,Año) VALUES (4,2019);

/******************************************************************************************************************************/

INSERT INTO Entrena(idJugador1, idJugador2, idEntrenador, Año)
VALUES ('1','2','1','2020');

INSERT INTO Entrena(idJugador1, idJugador2, idEntrenador, Año)
VALUES ('3','4','1','2020');

INSERT INTO Entrena(idJugador1, idJugador2, idEntrenador, Año)
VALUES ('1','3','3','2015');

INSERT INTO Entrena(idJugador1, idJugador2, idEntrenador, Año)
VALUES ('2','4','2','2019');

/******************************************************************************************************************************/

INSERT INTO Trabaja  (idPersonal, Año, sueldo)  values (4, 2015, 950.5);

INSERT INTO Trabaja  (idPersonal, Año, sueldo)  values (2, 2016, 900.5);

INSERT INTO Trabaja  (idPersonal, Año, sueldo)  values (1, 2019, 750.5);

INSERT INTO Trabaja  (idPersonal, Año, sueldo)  values (3, 2020, 860.5);

/******************************************************************************************************************************/

INSERT INTO Asigna(idPersonal,Año,idPista,FechaInicio,FechaFin)
VALUES(3, 2020, 2,
	TO_TIMESTAMP('2020-07-02 09:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2020-07-02 16:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')
);

INSERT INTO Asigna(idPersonal,Año,idPista,FechaInicio,FechaFin)
VALUES(1, 2019, 2,
	TO_TIMESTAMP('2019-08-06 09:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2019-08-06 16:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')
	);

INSERT INTO Asigna(idPersonal,Año,idPista,FechaInicio,FechaFin)
VALUES(2, 2016, 2,
	TO_TIMESTAMP('2016-09-15 09:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2016-09-15 16:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')
);

INSERT INTO Asigna(idPersonal,Año,idPista,FechaInicio,FechaFin)
VALUES(2, 2016, 2,
	TO_TIMESTAMP('2016-07-02 09:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2016-07-02 16:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')
);

/******************************************************************************************************************************/

INSERT INTO Colabora (idEntidad, Año, dinero_aportado)
VALUES (1,2020,600);

INSERT INTO Colabora (idEntidad, Año, dinero_aportado)
VALUES (3,2015,1000);

INSERT INTO Colabora (idEntidad, Año, dinero_aportado)
VALUES (2,2020,50);

INSERT INTO Colabora (idEntidad, Año, dinero_aportado)
VALUES (4,2016,150);

/******************************************************************************************************************************/

INSERT INTO Patrocina (idEntidad, Año, dinero_aportado)
VALUES (2,2016,800);

INSERT INTO Patrocina (idEntidad, Año, dinero_aportado)
VALUES (2,2015,2000);

INSERT INTO Patrocina (idEntidad, Año, dinero_aportado)
VALUES (3,2020,600);

INSERT INTO Patrocina (idEntidad, Año, dinero_aportado)
VALUES (4,2015,250);

/******************************************************************************************************************************/

INSERT INTO Proporciona(idmaterial,identidad,Año,cantidad_suministrada)
VALUES(1,2,2016,20);

INSERT INTO Proporciona(idmaterial,identidad,Año,cantidad_suministrada)
VALUES(2,3,2020,50);

INSERT INTO Proporciona(idmaterial,identidad,Año,cantidad_suministrada)
VALUES(3,2,2015,80);

INSERT INTO Proporciona(idmaterial,identidad,Año,cantidad_suministrada)
VALUES(4,3,2020,50);

/******************************************************************************************************************************/

INSERT INTO Recoge(numero_pedido,idpersonal,Año,idpista,fechainicio,fechafin,fecha)
VALUES(1,2,2016,2,
	TO_TIMESTAMP('2016-07-02 09:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2016-07-02 16:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2016-07-02 12:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')
);

INSERT INTO Recoge(numero_pedido,idpersonal,Año,idpista,fechainicio,fechafin,fecha)
VALUES(2,2,2016,2,
	TO_TIMESTAMP('2016-07-02 09:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2016-07-02 16:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2016-07-02 09:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')
);

INSERT INTO Recoge(numero_pedido,idpersonal,Año,idpista,fechainicio,fechafin,fecha)
VALUES(3,1,2019,2,
	TO_TIMESTAMP('2019-08-06 09:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2019-08-06 16:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2019-08-06 10:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')
);

INSERT INTO Recoge(numero_pedido,idpersonal,Año,idpista,fechainicio,fechafin,fecha)
VALUES(4,1,2019,2,
	TO_TIMESTAMP('2019-08-06 09:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2019-08-06 16:00:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),
	TO_TIMESTAMP('2019-08-06 10:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')
);

/******************************************************************************************************************************/

INSERT INTO Compuesto(IdMaterial,numero_pedido,cantidad) VALUES(1,1,20);

INSERT INTO Compuesto(IdMaterial,numero_pedido,cantidad) VALUES(2,2,30);

INSERT INTO Compuesto(IdMaterial,numero_pedido,cantidad) VALUES(3,3,50);

INSERT INTO Compuesto(IdMaterial,numero_pedido,cantidad) VALUES(4,4,50);

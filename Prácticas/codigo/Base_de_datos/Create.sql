CREATE TABLE Edicion(
	Año INT,
	PRIMARY KEY (Año)
);

CREATE TABLE Jugador(
	idJugador INT,
	nombreJ VARCHAR(30),
	apellidoJ VARCHAR(60),
	telefonoJ VARCHAR(12) CONSTRAINT tfnoJ CHECK(regexp_like(telefonoJ,'^[/+]?[0-9]')),
	emailJ VARCHAR(50) CONSTRAINT mail_checkJ CHECK (emailJ LIKE '%_@__%.__%'),
	PRIMARY KEY (idJugador)
);

CREATE TABLE Entrenador(
	idEntrenador INT,
	nombreE VARCHAR(30),
	apellidoE VARCHAR(60),
	telefonoE VARCHAR(12) CONSTRAINT tfnoE CHECK(regexp_like(telefonoE,'^[/+]?[0-9]')),
	emailE VARCHAR(50) CONSTRAINT mail_checkE CHECK (emailE LIKE '%_@__%.__%'),
	PRIMARY KEY (idEntrenador)
);

CREATE TABLE Partido(
	idPartido INT,
	fecha DATE,
	resultado VARCHAR(30),
	PRIMARY KEY (idPartido)
);

CREATE TABLE Arbitro(
	idArbitro INT,
	nombreA VARCHAR(30),
	apellidoA VARCHAR(60),
	telefonoA VARCHAR(12) CONSTRAINT tfnoA CHECK(regexp_like(telefonoA,'^[/+]?[0-9]')),
	emailA VARCHAR(50) CONSTRAINT mail_checkA CHECK (emailA LIKE '%_@__%.__%'),
	PRIMARY KEY (idArbitro)
);

CREATE TABLE Personal(
	idPersonal INT,
	nombreP VARCHAR(30),
	apellidosP VARCHAR(60),
	telefonoP VARCHAR(12) CONSTRAINT tfnoP CHECK(regexp_like(telefonoP,'^[/+]?[0-9]')),
	emailP VARCHAR(50) CONSTRAINT mail_checkP CHECK (emailP LIKE '%_@__%.__%'),
	PRIMARY KEY (idPersonal)
);

CREATE TABLE Pista(
	idPista INT,
	nombre VARCHAR(30),
	capacidad INT,
	PRIMARY KEY (idPista)
);

CREATE TABLE Entidad(
	idEntidad INT,
	nombreEn VARCHAR(30),
	persona_de_contacto VARCHAR(80),
	telefonoEn VARCHAR(12) CONSTRAINT tfnoEn CHECK(regexp_like(telefonoEn,'^[/+]?[0-9]')),
	emailEn VARCHAR(50) CONSTRAINT mail_checkEn CHECK (emailEn LIKE '%_@__%.__%'),
	PRIMARY KEY (idEntidad)
);

CREATE TABLE Material(
	idMaterial INT,
	nombre VARCHAR(30),
	PRIMARY KEY (idMaterial)
);

CREATE TABLE Pedido(
	numero_pedido INT,
	PRIMARY KEY (numero_pedido)
);

CREATE TABLE SeJuegaEN(
	idPartido INT CONSTRAINT idPartido_no_nulo NOT NULL,
	idPista INT CONSTRAINT idPista_no_nula NOT NULL,
	CONSTRAINT idPartido_clave_primaria PRIMARY KEY(idPartido),
	CONSTRAINT idPartido_clave_externa FOREIGN KEY(idPartido) REFERENCES Partido(idPartido),
	CONSTRAINT idPista_clave_externa FOREIGN KEY(idPista) REFERENCES Pista(idPista)
);

CREATE TABLE Arbitrado(
	idPartido INT CONSTRAINT idPartidoA_no_nulo NOT NULL,
	idArbitro INT CONSTRAINT idArbitro_no_nula NOT NULL,
	CONSTRAINT idPartidoA_clave_primaria PRIMARY KEY(idPartido),
	CONSTRAINT idPartidoA_clave_externa FOREIGN KEY(idPartido) REFERENCES Partido(idPartido),
	CONSTRAINT idArbitro_clave_externa FOREIGN KEY(idArbitro) REFERENCES Arbitro(idArbitro)
);

CREATE TABLE Pareja(
	idJugador1 INT,
	idJugador2 INT,
	FOREIGN KEY(idJugador1) REFERENCES Jugador(idJugador),
	FOREIGN KEY(idJugador2) REFERENCES Jugador(idJugador),
	PRIMARY KEY (idJugador1, idJugador2)
);

CREATE TABLE Inscrita(
	idJugador1 INT,
	idJugador2 INT,
	Año INT,
	Pos_ranking INT,
	FOREIGN KEY(idJugador1, idJugador2) REFERENCES Pareja(idJugador1, idJugador2),
	FOREIGN KEY(Año) REFERENCES Edicion(Año),
	PRIMARY KEY (idJugador1, idJugador2, Año)
);

CREATE TABLE Participan(
	idJugador1 INT CONSTRAINT jugadorP1_no_nulo NOT NULL,
	idJugador2 INT CONSTRAINT jugadorP2_no_nulo NOT NULL,
	Año INT CONSTRAINT Año_no_nuloP NOT NULL,
	idPartido INT CONSTRAINT Partido_no_nuloP NOT NULL,
	CONSTRAINT idClaveExterna_Inscrita FOREIGN KEY(idJugador1,idJugador2,Año)
		REFERENCES Inscrita(idJugador1,idJugador2,Año),
	CONSTRAINT idPartidoP_clave_externa FOREIGN KEY(idPartido)
		REFERENCES Partido(idPartido),
	CONSTRAINT clave_primaria_compuestaP PRIMARY KEY (idJugador1, idJugador2, Año,idPartido)

);

CREATE TABLE Jugado(
	idPartido INT CONSTRAINT idPartidoJ_no_nulo NOT NULL,
	Año INT CONSTRAINT idAño_no_nulo NOT NULL,
	CONSTRAINT idPartidoJ_clave_primaria PRIMARY KEY(idPartido),
	CONSTRAINT idPartidoJ_clave_externa FOREIGN KEY(idPartido) REFERENCES Partido(idPartido),
	CONSTRAINT idAño_clave_externa FOREIGN KEY(Año) REFERENCES Edicion(Año)
);

CREATE TABLE Entrena(
	idJugador1 INT,
	idJugador2 INT,
	idEntrenador INT,
	Año INT,
	FOREIGN KEY(idJugador1,idJugador2, Año) REFERENCES Inscrita(idJugador1,idJugador2, Año),
	FOREIGN KEY(idEntrenador) REFERENCES Entrenador(idEntrenador),
	PRIMARY KEY (idJugador1, idJugador2, Año, idEntrenador)
);

CREATE TABLE Trabaja (
	idPersonal INT,
	Año INT,
	sueldo INT,
	FOREIGN KEY (idPersonal) REFERENCES Personal (idPersonal),
	FOREIGN KEY (Año) REFERENCES Edicion (Año),
	PRIMARY KEY (idPersonal, Año)
);

CREATE TABLE Asigna (
	idPersonal INT,
	Año INT,
	idPista INT,
	FechaInicio TIMESTAMP,
	FechaFin TIMESTAMP,
	FOREIGN KEY (idPersonal, Año) REFERENCES Trabaja (idPersonal, Año),
	CHECK (FechaFin > FechaInicio),
	CHECK (FechaFin > FechaInicio),
	CHECK ((CAST (FechaFin as DATE) - CAST (FechaInicio as DATE))*24< 8),
	FOREIGN KEY (idPista) REFERENCES Pista (idPista),
	PRIMARY KEY (idPersonal, Año, idPista, FechaInicio, FechaFin)
);

CREATE TABLE Colabora (
	idEntidad INT,
	Año INT,
	dinero_aportado INT,
	FOREIGN KEY(idEntidad) REFERENCES Entidad(idEntidad),
	FOREIGN KEY (Año) REFERENCES Edicion(Año),
	PRIMARY KEY (idEntidad, Año)
);

CREATE TABLE Patrocina (
	idEntidad INT,
	Año INT,
	dinero_aportado INT,
	FOREIGN KEY(idEntidad) REFERENCES Entidad(idEntidad),
	FOREIGN KEY (Año) REFERENCES Edicion(Año),
	PRIMARY KEY (idEntidad, Año)
);

CREATE TABLE Proporciona(
	idmaterial INT PRIMARY KEY,
	identidad INT,
	Año INT,
	cantidad_suministrada INT,
	FOREIGN KEY(identidad,Año) REFERENCES Patrocina(identidad,Año),
	FOREIGN KEY(idmaterial) REFERENCES Material(idmaterial)
);

CREATE TABLE Recoge(
	numero_pedido INT,
	idPersonal INT,
	Año INT,
	idPista INT,
	fechainicio TIMESTAMP,
	fechafin TIMESTAMP,
	fecha TIMESTAMP,
	CONSTRAINT CHK_Fecha CHECK (fechainicio < fechafin),
	CONSTRAINT CHK_Fecha_Recogida1 CHECK(fecha >= fechainicio AND fecha <= fechafin),
	FOREIGN KEY(numero_pedido)REFERENCES Pedido(numero_pedido),
	FOREIGN KEY(fechainicio,fechafin,idPersonal,Año, idPista)
	REFERENCES Asigna(fechainicio, fechafin, idPersonal, Año, idPista),
	PRIMARY KEY(numero_pedido)
);

CREATE TABLE Compuesto(
	idMaterial INT,
	numero_pedido INT,
	cantidad int,
	FOREIGN KEY(idMaterial) REFERENCES Proporciona(idMaterial),
	FOREIGN KEY(numero_pedido) REFERENCES Pedido(numero_pedido),
	PRIMARY KEY(idMaterial,numero_pedido)
);
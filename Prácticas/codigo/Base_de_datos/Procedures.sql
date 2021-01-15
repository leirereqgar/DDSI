SET serveroutput ON;
SET feedback ON;

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE mostrarEntrenador(anio IN NUMBER, idEntr IN NUMBER, salida OUT CLOB)
IS
  CURSOR cEntrena IS SELECT * FROM Entrena WHERE (anio = Año) AND (idEntr = idEntrenador);
  coach Entrena%ROWTYPE;
  coach_row CLOB;
BEGIN
    salida := 'Entrenador ' || idEntr || ' en la edicion ' || anio || ' entrena:' || CHR(10);
    OPEN cEntrena;
        FETCH cEntrena INTO coach;
        WHILE (cEntrena%FOUND) LOOP
            coach_row := '   Pareja: ' || coach.idJugador1 || ' ' || coach.idJugador2;
            salida := salida || coach_row || CHR(10);

            FETCH cEntrena INTO coach;
        END LOOP;
    CLOSE cEntrena;
END;
/

/*DECLARE
salida CLOB;
BEGIN
salida := ' ';
mostrarEntrenador(2015, 3, salida);
dbms_output.put_line( salida );
END;*/
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE mostrarRecaudacion(edicion IN NUMBER, recaudacion OUT NUMBER) IS
    RecaudacionColab INT;
    RecaudacionPatr INT;
BEGIN
    SELECT SUM(Dinero_aportado)
    INTO RecaudacionColab
    FROM Colabora
    WHERE Año=edicion;

    SELECT SUM(Dinero_aportado)
    INTO RecaudacionPatr
    FROM Patrocina
    WHERE Año=edicion;

    recaudacion := RecaudacionPatr + RecaudacionColab;
END;
/

/*DECLARE
recaudacion NUMBER;
BEGIN
mostrarRecaudacion(2015,recaudacion);
dbms_output.put_line( recaudacion );
END;*/
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE mostrarPersonal (anio IN NUMBER, salida OUT CLOB)
IS
  CURSOR cPersonal IS
  (SELECT * FROM Personal
    WHERE NOT EXISTS(
        SELECT IDPERSONAL FROM Trabaja WHERE (anio=Trabaja.Año AND Trabaja.idPersonal = Personal.idPersonal)));
  persona Personal%ROWTYPE;
BEGIN
    OPEN cPersonal;
        FETCH cPersonal INTO persona;
        WHILE (cPersonal%FOUND) LOOP
            salida := salida ||'ID: ' || persona.idPersonal || ' Nombre y apellidos: ' || persona.nombreP || ' ' || persona.apellidosP || ' Email: ' || persona.emailp || CHR(10);
            FETCH cPersonal INTO persona;
        END LOOP;
    CLOSE cPersonal;
END;
/

/*DECLARE
salida CLOB;
BEGIN
salida := ' ';
mostrarPersonal(2015, salida);
dbms_output.put_line( salida );
END;*/
/

/******************************************************************************************************************************/
/*----------------------------------------------------------------------------------------------------------------------------*/
/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE insertarEdicion(anio NUMBER) AS
BEGIN
    INSERT INTO Edicion(Año)
    VALUES (anio);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE insertarJugador(idJ NUMBER, nombre VARCHAR2, apellido VARCHAR2, telefono VARCHAR2, email VARCHAR2) AS
BEGIN
    INSERT INTO Jugador(idJugador,nombreJ, apellidoJ, telefonoJ, emailJ)
    VALUES (idJ,nombre,apellido,telefono,email);
END;
/

--EXEC insertarJugador('5','Nico','Robin','686868686','nico@robin.com');

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE insertarEntrenador(idE NUMBER, nombre VARCHAR2, apellido VARCHAR2, telefono VARCHAR2, email VARCHAR2) AS
BEGIN
    INSERT INTO Entrenador(idEntrenador,nombreE, apellidoE, telefonoE, emailE)
    VALUES (idE,nombre,apellido,telefono,email);
END;
/

--EXEC insertarEntrenador('5','Bri','Ya','636868686','brilli@gmail.com');

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE registrarPareja(idJ1 NUMBER, idJ2 NUMBER) AS
BEGIN
    INSERT INTO Pareja(idJugador1, idJugador2)
    VALUES (idJ1,idJ2);
END;
/

--EXEC registrarPareja(1,5);

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE registrarInscrita(idJ1 NUMBER, idJ2 NUMBER, anio NUMBER, ranking NUMBER) AS
BEGIN
    INSERT INTO Inscrita(idJugador1, idJugador2, Año, Pos_ranking)
    VALUES (idJ1,idJ2,anio,ranking);
END;
/

--EXEC registrarInscrita(1,5,2021,6);

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE registrarEntrena(idJ1 NUMBER, idJ2 NUMBER, idE NUMBER, anio NUMBER) AS
BEGIN
    INSERT INTO Entrena(idJugador1, idJugador2, idEntrenador, Año)
    VALUES (idJ1,idJ2,idE,anio);
END;
/

--EXEC registrarEntrena(1,5,5,2021,6);

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE insertarEntidad(idE NUMBER, nombre VARCHAR2, contacto VARCHAR2, telefono NUMBER, email VARCHAR2) AS
BEGIN
    INSERT INTO Entidad(idEntidad, nombreEn, persona_de_contacto, telefonoEn, emailEn)
    VALUES (idE,nombre,contacto,telefono,email);
END;
/

--EXEC insertarEntidad(5,'AGRSL','Angel',123123123,'agr@gmail.com');

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE registrarColaborador(idE NUMBER, anio NUMBER, dinero NUMBER) AS
BEGIN
    INSERT INTO Colabora (idEntidad, Año, dinero_aportado)
    VALUES (idE,anio,dinero);
END;
/

--EXEC registrarColaborador(5,2021,777);

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE registrarPatrocinador(idE NUMBER, anio NUMBER, dinero NUMBER) AS
BEGIN
    INSERT INTO Patrocina (idEntidad, Año, dinero_aportado)
    VALUES (idE,anio,dinero);
END;
/

--EXEC registrarPatrocinador(5,2021,777);

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE insertarArbitro(idA NUMBER, nombre VARCHAR2, apellido VARCHAR2, telefono VARCHAR2, email VARCHAR2) AS
BEGIN
    INSERT INTO Arbitro(idArbitro,nombreA, apellidoA, telefonoA, emailA)
    VALUES (idA,nombre,apellido,telefono,email);
END;
/

--EXEC insertarArbitro('5','Neutro','Neutral','611111116','neu@tral.com');

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE InsertarPartido(idPartido NUMBER, fecha TIMESTAMP, resultado VARCHAR2, idEdicion NUMBER, idPista NUMBER) IS
BEGIN
    INSERT INTO Partido(idPartido,fecha,resultado) VALUES (idPartido,fecha,resultado);
    INSERT INTO SeJuegaEN(idPartido,idPista) VALUES (idPartido,idPista);
    INSERT INTO Jugado(idPartido,Año) VALUES (idPartido,idEdicion);
END;

/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE InsertarPartidoEnPista(idPartido INT, idPista INT) IS
BEGIN
    INSERT INTO SeJuegaEN(idPartido,idPista) VALUES (idPartido,idPista);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE InsertarPista(idPista INT, nombre VARCHAR2,capacidad INT) IS
BEGIN
    INSERT INTO Pista(idPista, nombre,capacidad) VALUES (idPista, nombre,capacidad);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE InsertarMaterial(idMaterial INT, nombre VARCHAR2)
IS
BEGIN
    INSERT INTO Material(idMaterial,nombre) VALUES(idMaterial,nombre);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE InsertarPedido(numeroPedido INT)
IS
BEGIN
    INSERT INTO Pedido(numero_Pedido) VALUES(numeroPedido);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE registrarCompuesto(material INT, numeroPedido INT, cantidad1 INT)
IS
BEGIN
    INSERT INTO Compuesto(idMaterial, numero_Pedido, cantidad) VALUES(material, numeroPedido, cantidad1);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE InsertarAsignacionRecogida(numeroPedido INT, idPersonal INT, Año INT,
idPista INT, fechainicio TIMESTAMP, fechafin TIMESTAMP, fecha TIMESTAMP)
IS
BEGIN
    INSERT INTO Recoge(numero_pedido,idpersonal,año,idpista,fechainicio,fechafin,fecha)
    VALUES(numeroPedido,idpersonal,año,idpista,fechainicio,fechafin,fecha);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE modificaDineroPatr(idE NUMBER, anio NUMBER, dinero NUMBER) AS
BEGIN
    UPDATE Patrocina
    SET dinero_aportado = dinero
    WHERE idEntidad = idE AND Año = anio;
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE modificaDineroColab(idE NUMBER, anio NUMBER, dinero NUMBER) AS
BEGIN
    UPDATE Colabora
    SET dinero_aportado = dinero
    WHERE idEntidad = idE AND Año = anio;
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE insertarPersonal(idPersonal1 INT,
                       nombreP1 VARCHAR2,
                       apellidosP1 VARCHAR2,
                       telefonoP1 INT,
                       emailP1 VARCHAR2) AS
BEGIN
    INSERT INTO Personal(idPersonal, nombreP, apellidosP, telefonoP, emailP)
    VALUES (idPersonal1, nombreP1, apellidosP1, telefonoP1, emailP1);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE modificarSalario(idPersonal1 INT, anio NUMBER, nuevoSueldo NUMBER) AS
BEGIN
    UPDATE Trabaja
    SET sueldo = nuevoSueldo
    WHERE idPersonal = idPersonal1 AND Año = anio;
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE registrarTrabajador(idPersonal1 INT, edicion INT, sueldo1 NUMBER) AS
BEGIN
    INSERT INTO Trabaja(idPersonal, Año, sueldo)
    VALUES (idPersonal1, edicion, sueldo1);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE InsertarMaterial(idMaterial INT, nombre VARCHAR2)
IS
BEGIN
    INSERT INTO Material(idMaterial,nombre) VALUES(idMaterial,nombre);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE InsertarPedido(numeroPedido NUMBER)
IS
BEGIN
INSERT INTO Pedido(numero_Pedido) VALUES(numeroPedido);
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE PROCEDURE InsertarAsigna(personal INT, edicion INT, pista INT, inicio TIMESTAMP, fin TIMESTAMP)
IS
BEGIN
INSERT INTO Asigna(idPersonal, año, idPista, FechaInicio, FechaFin)
VALUES(personal, edicion, pista, inicio, fin);
END;
/

/******************************************************************************************************************************/
/******************************************************************************************************************************/
/******************************************************************************************************************************/
/******************************************************************************************************************************/

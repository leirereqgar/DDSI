SET serveroutput ON;
SET feedback ON;

/******************************************************************************************************************************/
CREATE OR REPLACE TRIGGER jugador_unico
    BEFORE
    INSERT ON Inscrita
    FOR EACH ROW
DECLARE
    CURSOR cJugador IS SELECT * FROM Inscrita WHERE (:new.Año = Año);
    jugadores Inscrita%ROWTYPE;
BEGIN
    OPEN cJugador;
    FETCH cJugador INTO jugadores;
    WHILE (cJugador%FOUND) LOOP
        IF (:new.idJugador1 = jugadores.idJugador1 OR :new.IdJugador1 = jugadores.idJugador2)
            THEN raise_application_error(-20600, :new.idJugador1 || 'JUGADOR 1: No se puede pertenecer a más de una pareja en la misma edicion');
        END IF;

        IF (:new.idJugador2 = jugadores.idJugador1 OR :new.IdJugador2 = jugadores.idJugador2)
            THEN raise_application_error(-20600, :new.idJugador2 || 'JUGADOR 2: No se puede pertenecer a más de una pareja en la misma edicion');
        END IF;

        FETCH cJugador INTO jugadores;
    END LOOP;

    CLOSE cJugador;
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE TRIGGER dinero_tras_inicio_colab
    BEFORE
    UPDATE OF Dinero_aportado ON Colabora
    FOR EACH ROW
DECLARE
    CURSOR cPrimerPartido IS
    SELECT fecha FROM Partido WHERE fecha IN (SELECT MIN(fecha) FROM Partido WHERE (EXTRACT(YEAR FROM fecha) = :old.Año) GROUP BY to_char(fecha,'YYYY')) GROUP BY fecha;

    earliest DATE;
BEGIN
    OPEN cPrimerPartido;
        FETCH cPrimerPartido INTO earliest;

        IF (earliest < CURRENT_DATE)
        THEN raise_application_error(-20600, :new.Dinero_aportado || 'No se puede modificar el dinero aportado si ya ha comenzado el torneo');
        END IF;

    CLOSE cPrimerPartido;

END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE TRIGGER dinero_tras_inicio_patr
    BEFORE
    UPDATE OF Dinero_aportado ON Patrocina
    FOR EACH ROW
DECLARE
    CURSOR cPrimerPartido IS
    SELECT fecha FROM Partido WHERE fecha IN (SELECT MIN(fecha) FROM Partido WHERE (EXTRACT(YEAR FROM fecha) = :old.Año) GROUP BY to_char(fecha,'YYYY')) GROUP BY fecha;

    earliest DATE;
BEGIN
    OPEN cPrimerPartido;
        FETCH cPrimerPartido INTO earliest;

        IF (earliest < CURRENT_DATE)
        THEN raise_application_error(-20600, :new.Dinero_aportado || 'No se puede modificar el dinero aportado si ya ha comenzado el torneo');
        END IF;

    CLOSE cPrimerPartido;

END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE VIEW PistaPartidoFecha AS (SELECT distinct
            SeJuegaEn.idPista,SeJuegaEn.idPartido,Partido.fecha from SeJuegaEn,SeJuegaEn cjuega,Partido,Partido par where(
            ((Partido.fecha - par.fecha)<1 AND Partido.idPartido<>par.idPartido)
            AND
            (SeJuegaEn.idPista=cjuega.idPista AND SeJuegaEn.idPartido<>cjuega.idPartido)
            AND
            (Partido.idPartido=SeJuegaEn.idPartido)
            ));


CREATE OR REPLACE TRIGGER partido_unico
    BEFORE
    INSERT ON SeJuegaEn
    FOR EACH ROW
DECLARE
     CURSOR cPartidoFecha IS SELECT * FROM PistaPartidoFecha;
     CURSOR cPartido IS SELECT * FROM Partido;
     datos PistaPartidoFecha%ROWTYPE;
     datosPartido Partido%ROWTYPE;
     numPartidos number;
BEGIN
  OPEN cPartidoFecha;
  OPEN cPartido;
     select (SELECT COUNT(*) FROM PistaPartidoFecha) into numPartidos from dual;
     FETCH cPartidoFecha INTO datos;
     FETCH cPartido INTO datosPartido;
     WHILE(cPartidoFecha%FOUND)LOOP
        WHILE(cPartido%FOUND)LOOP
            IF(numPartidos >= 2 AND :new.idPista = datos.idPista AND :new.idPartido <> datos.idPartido AND
            (:new.idPartido = datosPartido.idPartido AND (datosPartido.fecha-datos.fecha) < 1))
            THEN
                raise_application_error(-20600, :new.idPartido || 'No se jugar más de dos partidos en la misma pista en un mismo dia');
            END IF;
        FETCH cPartido INTO datosPartido;
        END LOOP;
    FETCH cPartidoFecha INTO datos;
    END LOOP;
  CLOSE cPartido;
  CLOSE cPartidoFecha;
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE TRIGGER recogida
    BEFORE INSERT ON Recoge
    FOR EACH ROW
DECLARE
    CURSOR cRecoge IS SELECT * FROM Recoge;
    diferencia NUMERIC;
    asignacion Recoge%ROWTYPE;
BEGIN
    Open cRecoge;
    FETCH cRecoge INTO asignacion;
    WHILE (cRecoge%FOUND) LOOP
        diferencia := EXTRACT(HOUR FROM :new.fecha - asignacion.fecha);
        IF(diferencia < 1 AND asignacion.idPersonal = :new.idPersonal AND asignacion.año = :new.año) THEN
            raise_application_error(-20600, :new.fecha || 'Un trabajador no puede serle asignado un pedido con menos de 1 hora de diferencia');
        END IF;
        FETCH cRecoge INTO asignacion;
    END LOOP;
    CLOSE cRecoge;
END;
/

/******************************************************************************************************************************/
CREATE OR REPLACE TRIGGER modificacionSalario
    BEFORE
    UPDATE OF sueldo ON Trabaja
    FOR EACH ROW
DECLARE
    CURSOR cAsigna IS SELECT FechaInicio FROM Asigna WHERE (idPersonal = :new.idPersonal);
    fechas_asigna TIMESTAMP;

BEGIN
    OPEN cAsigna;
        FETCH cAsigna INTO fechas_asigna;
        WHILE (cAsigna%FOUND) LOOP
            IF (CAST(sysdate AS TIMESTAMP) > fechas_asigna AND (EXTRACT(YEAR FROM fechas_asigna) >= :new.Año)) THEN
                    RAISE_APPLICATION_ERROR(-20600, :new.sueldo || '***EXCEPTION**** No se puede modificar el sueldo ya que ya ha empezado su trabajo');
            END IF;
            dbms_output.put_line( fechas_asigna );
            FETCH cAsigna INTO fechas_asigna;
        END LOOP;
    CLOSE cAsigna;
END;
/

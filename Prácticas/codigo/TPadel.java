import java.sql.*;
import java.util.Scanner;
import java.util.regex.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.*;

//Compilar con javac Tutorial.java
//Ejecutar con java -cp ojdbc8.jar:. Tutorial (en el mismo directorio donde está ojdbc8.jar)
class TPadel {
	public static void main(String ar[]) {
		System.out.println("___________________\n" +
		                   "| Torneo de padel |\n" +
		                   "___________________\n");

		System.out.println("Introduzca su usuario (que coincide con su password): ");
		String entrada;
		Scanner entradaEscaner = new Scanner (System.in);
		entrada = entradaEscaner.nextLine();

		try(Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es", entrada, entrada)){
			if(conn != null){
				System.out.println("\u001B[32mConectado a la base de datos\u001B[0m");

				conn.setAutoCommit(false);

				String query;
				Statement stmt = null;
				ResultSet rset = null;
				CallableStatement cs = null;

				try {
					int menu = -1;

					/***************************
					INTERFAZ
					****************************/

					while (menu != 7) {
						System.out.println("\n\u001B[34m\t\t--- MENU ---\u001B[0m");
						System.out.println("\u001B[36m1.Ediciones\u001B[0m");
						System.out.println("\u001B[36m2.Jugadores/Entrenadores\u001B[0m");
						System.out.println("\u001B[36m3.Pistas/Partidos\u001B[0m");
						System.out.println("\u001B[36m4.Patrocinadores/Colaboradores\u001B[0m");
						System.out.println("\u001B[36m5.Personal/Horarios\u001B[0m");
						System.out.println("\u001B[36m6.Materiales/Pedidos\u001B[0m");
						System.out.println("\u001B[36m7.Salir del programa\u001B[0m");

						entradaEscaner = new Scanner (System.in);
						menu = entradaEscaner.nextInt();

						switch(menu) {
							case 1:
								int edicion = -1;
								while(edicion != 5 && edicion != 6) {
									System.out.println("\n\u001B[36m\t\t" + "--- Operaciones sobre Ediciones ---" + "\u001B[0m");
									System.out.println("\u001B[33m" + "1.Insertar nueva edición" + "\u001B[0m");
									System.out.println("\u001B[33m" + "2.Mostrar el total recaudado" + "\u001B[0m");
									System.out.println("\u001B[33m" + "3.Consultar las parejas de un entrenador" + "\u001B[0m");
									System.out.println("\u001B[33m" + "4.Mostrar el personal que no trabaja en una edición" + "\u001B[0m");
									System.out.println("\u001B[33m" + "5.Guardar cambios" + "\u001B[0m");
									System.out.println("\u001B[33m" + "6.Cancelar" + "\u001B[0m");

									entradaEscaner = new Scanner (System.in);
									edicion = entradaEscaner.nextInt();
									int anio;

									switch(edicion) {
										case 1:

											System.out.println("\nIntroduzca el año de la nueva edición ");
											entradaEscaner = new Scanner (System.in);
											anio = entradaEscaner.nextInt();

											try {
												query = "CALL insertarEdicion(" + anio + ")";
												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 2:
											System.out.println("\nIntroduzca la edicion a consultar ");
											entradaEscaner = new Scanner (System.in);
											anio = entradaEscaner.nextInt();

											try {
											cs = conn.prepareCall("{CALL mostrarRecaudacion(?,?)}");
											cs.setInt(1, anio);

											cs.registerOutParameter(2, java.sql.Types.INTEGER);
											cs.executeUpdate();

											//read the OUT parameter now
											int result = cs.getInt(2);

											System.out.println("Dinero recaudado::"+result);

											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}

										break;

										case 3:
											int idEntrenador;

											System.out.println("\nIntroduzca la edicion a consultar ");
											entradaEscaner = new Scanner (System.in);
											anio = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el id del entrenador ");
											entradaEscaner = new Scanner (System.in);
											idEntrenador = entradaEscaner.nextInt();

											try {
												cs = conn.prepareCall("{CALL mostrarEntrenador(?,?,?)}");
												cs.setInt(1, anio);
												cs.setInt(2, idEntrenador);

												cs.registerOutParameter(3, java.sql.Types.CLOB);
												cs.executeUpdate();

												//read the OUT parameter now
												String result = cs.getString(3);

												System.out.println("\n" + result);

											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 4:
											System.out.println("\nIntroduzca la edicion a consultar ");
											entradaEscaner = new Scanner (System.in);
											anio = entradaEscaner.nextInt();

											try {
												cs = conn.prepareCall("{CALL mostrarPersonal(?,?)}");
												cs.setInt(1, anio);

												cs.registerOutParameter(2, java.sql.Types.CLOB);
												cs.executeUpdate();

												//read the OUT parameter now
												String result = cs.getString(2);

												System.out.println("\nNo trabajan en la edicion " + anio + ":\n" + result);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 5:
											conn.commit();
										break;

										case 6:
											conn.rollback();
										break;

										default: System.out.println("\u001B[31m" + "Por favor, seleccione una opcion valida: " + "\u001B[0m");
									}
								}
							break;

							case 2:
								int jugentr = -1;
								while(jugentr != 7 && jugentr != 8) {
									System.out.println("\n\u001B[36m\t\t" + "--- Operaciones sobre jugadores y entrenadores ---" + "\u001B[0m");
									System.out.println("\u001B[33m" + "1.Insertar nuevo jugador" + "\u001B[0m");
									System.out.println("\u001B[33m" + "2.Insertar entrenador" + "\u001B[0m");
									System.out.println("\u001B[33m" + "3.Registrar pareja" + "\u001B[0m");
									System.out.println("\u001B[33m" + "4.Inscribir pareja en edición" + "\u001B[0m");
									System.out.println("\u001B[33m" + "5.Añadir entrenador a pareja" + "\u001B[0m");
									System.out.println("\u001B[33m" + "6.Añadir posicionamiento en el ranking" + "\u001B[0m");
									System.out.println("\u001B[33m" + "7.Guardar cambios" + "\u001B[0m");
									System.out.println("\u001B[33m" + "8.Cancelar" + "\u001B[0m");

									entradaEscaner = new Scanner (System.in);
									jugentr = entradaEscaner.nextInt();

									int idJugador, idEntrenador, miembro1, miembro2, anio;

									switch(jugentr) {
										case 1:
											String nombrej, apellidoj, telefonoj, emailj;

											System.out.println("\nIntroduzca el id del jugador ");
											entradaEscaner = new Scanner (System.in);
											idJugador = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el nombre ");
											entradaEscaner = new Scanner (System.in);
											nombrej = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca los apellidos ");
											entradaEscaner = new Scanner (System.in);
											apellidoj = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca el telefono ");
											entradaEscaner = new Scanner (System.in);
											telefonoj = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca el e-mail (debe contener la @) ");
											entradaEscaner = new Scanner (System.in);
											emailj = entradaEscaner.nextLine();

											try {
												query = "CALL insertarJugador("+ idJugador + ",'" + nombrej
												+"','" + apellidoj + "'," + telefonoj + ",'" + emailj + "')";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 2:
											String nombree, apellidoe, telefonoe, emaile;

											System.out.println("\nIntroduzca el id del entrenador ");
											entradaEscaner = new Scanner (System.in);
											idEntrenador = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el nombre ");
											entradaEscaner = new Scanner (System.in);
											nombree = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca los apellidos ");
											entradaEscaner = new Scanner (System.in);
											apellidoe = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca el telefono ");
											entradaEscaner = new Scanner (System.in);
											telefonoe = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca el e-mail (debe contener la @) ");
											entradaEscaner = new Scanner (System.in);
											emaile = entradaEscaner.nextLine();

											try {
												query = "CALL insertarEntrenador("+ idEntrenador + ",'" + nombree
												+ "','" + apellidoe + "'," + telefonoe + ",'" + emaile + "')";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 3:
											System.out.println("\nIntroduzca el id del primer jugador ");
											entradaEscaner = new Scanner (System.in);
											miembro1 = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el id del segundo jugador ");
											entradaEscaner = new Scanner (System.in);
											miembro2 = entradaEscaner.nextInt();

											try {
												query = "CALL registrarPareja("+ miembro1 + "," + miembro2 + ")";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 4:
											int ranking;
											char rank_opcional;

											System.out.println("\nIntroduzca año donde quiere inscribir la pareja ");
											entradaEscaner = new Scanner (System.in);
											anio = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el id del primer miembro de la pareja ");
											entradaEscaner = new Scanner (System.in);
											miembro1 = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el id del segundo miembro de la pareja ");
											entradaEscaner = new Scanner (System.in);
											miembro2 = entradaEscaner.nextInt();

											do{
												rank_opcional = 'N';

												System.out.println("\n¿Desea registrar la posicion en el ranking? Y/N");
												entradaEscaner = new Scanner (System.in);
												rank_opcional = entradaEscaner.next().charAt(0);

												rank_opcional = Character.toUpperCase(rank_opcional);

												if(rank_opcional == 'Y'){
														System.out.println("\nPosicion de la pareja en el ranking ");
														entradaEscaner = new Scanner (System.in);
														ranking = entradaEscaner.nextInt();

														try {
															query = "CALL registrarInscrita("+ miembro1 + "," + miembro2 + "," + anio + "," + ranking + ")";

															stmt = conn.createStatement();
															stmt.executeUpdate(query);
														} catch (SQLException e) {
															System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
														} catch (Exception e) {
															e.printStackTrace();
														}

												} else if (rank_opcional == 'N'){
														try {
															query = "CALL registrarInscrita("+ miembro1 + "," + miembro2 + "," + anio + ", NULL )";

															stmt = conn.createStatement();
															stmt.executeUpdate(query);
														} catch (SQLException e) {
															System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
														} catch (Exception e) {
															e.printStackTrace();
														}
												} else{
													System.out.println("\nPor favor, introduzca una opcion valida: Y para si, N para no");
												}
											}while(rank_opcional != 'Y' && rank_opcional != 'N');

										break;

										case 5:
											System.out.println("\nIntroduzca año en el que está inscrita la pareja ");
											entradaEscaner = new Scanner (System.in);
											anio = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el id del primer miembro de la pareja ");
											entradaEscaner = new Scanner (System.in);
											miembro1 = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el id del segundo miembro de la pareja ");
											entradaEscaner = new Scanner (System.in);
											miembro2 = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el id del entrenador ");
											entradaEscaner = new Scanner (System.in);
											idEntrenador = entradaEscaner.nextInt();

											try {
												query = "CALL registrarEntrena("+ miembro1 + "," + miembro2 + "," + idEntrenador + "," + anio + ")";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}

										break;

										case 6:
										System.out.println("\nIntroduzca año en el que está inscrita la pareja ");
										entradaEscaner = new Scanner (System.in);
										anio = entradaEscaner.nextInt();

										System.out.println("\nIntroduzca el id del primer miembro de la pareja ");
										entradaEscaner = new Scanner (System.in);
										miembro1 = entradaEscaner.nextInt();

										System.out.println("\nIntroduzca el id del segundo miembro de la pareja ");
										entradaEscaner = new Scanner (System.in);
										miembro2 = entradaEscaner.nextInt();

										System.out.println("\nIntroduzca posicion de la pareja en el ranking ");
										entradaEscaner = new Scanner (System.in);
										ranking = entradaEscaner.nextInt();

										try {
											query = "UPDATE Inscrita SET pos_ranking = " + ranking + " WHERE idJugador1=" + miembro1 + "AND idJugador2=" + miembro2 + " AND Año=" + anio;

											stmt = conn.createStatement();
											stmt.executeUpdate(query);
										} catch (SQLException e) {
											System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
										} catch (Exception e) {
											e.printStackTrace();
										}
										break;

										case 7:
											conn.commit();
										break;

										case 8:
											conn.rollback();
										break;

										default: System.out.println("\u001B[31m" + "Por favor, seleccione una opcion valida: " + "\u001B[0m");
									}
								}
							break;

							case 3:
								int pistpart = -1;
								while(pistpart != 4 && pistpart != 5) {
									System.out.println("\n\u001B[36m\t\t" + "--- Operaciones sobre pistas y partidos ---" + "\u001B[0m");
									System.out.println("\u001B[33m" + "1.Insertar pista" + "\u001B[0m");
									System.out.println("\u001B[33m" + "2.Insertar partido" + "\u001B[0m");
									System.out.println("\u001B[33m" + "3.Registrar resultado de partido" + "\u001B[0m");
									System.out.println("\u001B[33m" + "4.Guardar cambios" + "\u001B[0m");
									System.out.println("\u001B[33m" + "5.Cancelar" + "\u001B[0m");

									entradaEscaner = new Scanner (System.in);
									pistpart = entradaEscaner.nextInt();

									int idPista, idPartido, anio;

									switch(pistpart) {
										case 1:
											String nombre;
											int capacidad;
											String resultado;
											char resultado_opcional;

											System.out.println("\nIntroduzca el id de la pista ");
											entradaEscaner = new Scanner (System.in);
											idPista = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el nombre de la pista ");
											entradaEscaner = new Scanner (System.in);
											nombre = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca la capacidad de la pista ");
											entradaEscaner = new Scanner (System.in);
											capacidad = entradaEscaner.nextInt();

											try {
												query = "CALL insertarPista("+ idPista + ",'" + nombre + "'," + capacidad + ")";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 2:
											System.out.println("\nIntroduzca el id del partido ");
											entradaEscaner = new Scanner (System.in);
											idPartido = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca la edicion ");
											entradaEscaner = new Scanner (System.in);
											anio = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el id de la pista ");
											entradaEscaner = new Scanner (System.in);
											idPista = entradaEscaner.nextInt();

											resultado = "NULL";

											do{
												System.out.println("\n¿Desea insertar el resultado del partido? Y/N");
												entradaEscaner = new Scanner (System.in);
												resultado_opcional = entradaEscaner.next().charAt(0);

												resultado_opcional = Character.toUpperCase(resultado_opcional);

												if(resultado_opcional == 'Y'){
														System.out.println("\nIntroduzca el resultado ");
														entradaEscaner = new Scanner (System.in);
														resultado = entradaEscaner.nextLine();

												} else if (resultado_opcional == 'N'){
													resultado = "NULL";
												} else{
													System.out.println("\nPor favor, introduzca una opcion valida: Y para si, N para no");
												}
											}while(resultado_opcional != 'Y' && resultado_opcional != 'N');


											//COMPROBACIÓN DE LA FECHA
											boolean fecha_correcta = false;
			  								boolean formato_correcto = false;

			  								while(!fecha_correcta){
												System.out.println("Fecha del partido (YYYY-MM-DD): ");
												entradaEscaner = new Scanner (System.in);
												String fecha = entradaEscaner.nextLine();

												//COMPROBACIÓN DEL FORMATO
												DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
												try{
													formatter.parse(fecha, LocalDate::from);
													formato_correcto = true;
												}
												catch(DateTimeParseException e){
													System.out.println("\u001B[31m" + "Formato incorrecto " + "\u001B[0m");
												}

												if(formato_correcto){
													//COMPROBACIÓN DE LA FECHA
													//mes
													String res = fecha.substring(5,7);
													int mes = Integer.parseInt(res);

													//día
													res = fecha.substring(8,10);
													int dia = Integer.parseInt(res);
//CALL insertarPartido(10,TO_TIMESTAMP('2018-08-06 08:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'),1,2019,11);
													if(mes == 2){
														if(1 <= dia && dia <= 28){
															try {
																query = "CALL insertarPartido("+ idPartido + ",TO_TIMESTAMP('" + fecha + " 08:14:00.742000000','YYYY-MM-DD HH24:MI:SS.FF')," + resultado + "," + anio + "," + idPista + ")";

																stmt = conn.createStatement();
																stmt.executeUpdate(query);
															} catch (SQLException e) {
																System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
															} catch (Exception e) {
																e.printStackTrace();
															}

															fecha_correcta = true;
														}
													}
													else{
														if(mes%2 == 1){
															if(1 <= dia && dia <= 31){
																try {
																	query = "CALL insertarPartido("+ idPartido + ",TO_TIMESTAMP('" + fecha + " 08:14:00.742000000','YYYY-MM-DD HH24:MI:SS.FF')," + resultado + "," + anio + "," + idPista + ")";

																	stmt = conn.createStatement();
																	stmt.executeUpdate(query);
																} catch (SQLException e) {
																	System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
																} catch (Exception e) {
																	e.printStackTrace();
																}

																fecha_correcta = true;
															}
														}
														else{
															if(1 <= dia && dia <= 30){
																try {
																	query = "CALL insertarPartido("+ idPartido + ",TO_TIMESTAMP('" + fecha + " 08:14:00.742000000','YYYY-MM-DD HH24:MI:SS.FF')," + resultado + "," + anio + "," + idPista + ")";

																	stmt = conn.createStatement();
																	stmt.executeUpdate(query);
																} catch (SQLException e) {
																	System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
																} catch (Exception e) {
																	e.printStackTrace();
																}

																fecha_correcta = true;
															}
														}
													}
												}
											}

										break;

										case 3:
										System.out.println("\nIntroduzca el id del partido ");
										entradaEscaner = new Scanner (System.in);
										idPartido = entradaEscaner.nextInt();

										System.out.println("\nIntroduzca el resultado ");
										entradaEscaner = new Scanner (System.in);
										resultado = entradaEscaner.nextLine();

										try {
											query = "UPDATE Partido SET Resultado='" + resultado + "' WHERE idPartido=" + idPartido;

											stmt = conn.createStatement();
											stmt.executeUpdate(query);
										} catch (SQLException e) {
											System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
										} catch (Exception e) {
											e.printStackTrace();
										}

										break;

										case 4:
											conn.commit();
										break;

										case 5:
											conn.rollback();
										break;

										default: System.out.println("\u001B[31m" + "Por favor, seleccione una opcion valida: " + "\u001B[0m");
									}
								}
							break;

							case 4:
								int patrcol = -1;
								while(patrcol != 5 && patrcol != 6) {
									System.out.println("\n\u001B[36m\t\t" + "--- Operaciones sobre colaboradores y patrocinadores ---" + "\u001B[0m");
									System.out.println("\u001B[33m" + "1.Insertar entidad" + "\u001B[0m");
									System.out.println("\u001B[33m" + "2.Registrar colaborador" + "\u001B[0m"); //sejuegaen jugado arbitrado y participan
									System.out.println("\u001B[33m" + "3.Registrar patrocinador" + "\u001B[0m");
									System.out.println("\u001B[33m" + "4.Modificar dinero aportado" + "\u001B[0m");
									System.out.println("\u001B[33m" + "5.Guardar cambios" + "\u001B[0m");
									System.out.println("\u001B[33m" + "6.Cancelar" + "\u001B[0m");

									entradaEscaner = new Scanner (System.in);
									patrcol = entradaEscaner.nextInt();

									int idEntidad, anio, aportacion;

									switch(patrcol) {
										case 1:
											String nombreen, contacto, telefonoen, emailen;

											System.out.println("\nIntroduzca el id de la entidad ");
											entradaEscaner = new Scanner (System.in);
											idEntidad = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el nombre de la entidad ");
											entradaEscaner = new Scanner (System.in);
											nombreen = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca una persona de contacto ");
											entradaEscaner = new Scanner (System.in);
											contacto = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca el telefono ");
											entradaEscaner = new Scanner (System.in);
											telefonoen = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca el e-mail (debe contener la @) ");
											entradaEscaner = new Scanner (System.in);
											emailen = entradaEscaner.nextLine();

											try {
												query = "CALL insertarEntidad("+ idEntidad + ",'" + nombreen
												+ "','" + contacto + "'," + telefonoen + ",'" + emailen + "')";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 2:
											System.out.println("\nIntroduzca el id de la entidad ");
											entradaEscaner = new Scanner (System.in);
											idEntidad = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca la edicion donde colabora ");
											entradaEscaner = new Scanner (System.in);
											anio = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca la cantidad con la que colabora ");
											entradaEscaner = new Scanner (System.in);
											aportacion = entradaEscaner.nextInt();

											try {
												query = "CALL registrarColaborador("+ idEntidad + "," + anio + "," + aportacion + ")";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 3:
											System.out.println("\nIntroduzca el id de la entidad ");
											entradaEscaner = new Scanner (System.in);
											idEntidad = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca la edicion donde patrocina ");
											entradaEscaner = new Scanner (System.in);
											anio = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca la cantidad con la que patrocina ");
											entradaEscaner = new Scanner (System.in);
											aportacion = entradaEscaner.nextInt();

											try {
												query = "CALL registrarPatrocinador("+ idEntidad + "," + anio + "," + aportacion + ")";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 4:
											int quesoy = -1;
											Savepoint save1 = conn.setSavepoint();
											while (quesoy != 3 && quesoy != 4) {
												System.out.println("\n\u001B[36m\t\t" + "--- ¿Colaborador o patrocinador? ---" + "\u001B[0m");
												System.out.println("\u001B[33m" + "1.Colaborador" + "\u001B[0m");
												System.out.println("\u001B[33m" + "2.Patrocinador" + "\u001B[0m"); //sejuegaen jugado arbitrado y participan
												System.out.println("\u001B[33m" + "3.Guardar" + "\u001B[0m");
												System.out.println("\u001B[33m" + "4.Cancelar" + "\u001B[0m");

												entradaEscaner = new Scanner (System.in);
												quesoy = entradaEscaner.nextInt();

												switch(quesoy) {
													case 1:
														System.out.println("\nIntroduzca el id de la entidad ");
														entradaEscaner = new Scanner (System.in);
														idEntidad = entradaEscaner.nextInt();

														System.out.println("\nIntroduzca la edicion donde se cambiara la aportacion ");
														entradaEscaner = new Scanner (System.in);
														anio = entradaEscaner.nextInt();

														System.out.println("\nIntroduzca la nueva cantidad con la que colabora ");
														entradaEscaner = new Scanner (System.in);
														aportacion = entradaEscaner.nextInt();

														try {
															query = "CALL modificaDineroColab("+ idEntidad + "," + anio + "," + aportacion + ")";

															stmt = conn.createStatement();
															stmt.executeUpdate(query);
														} catch (SQLException e) {
															System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
														} catch (Exception e) {
															e.printStackTrace();
														}
													break;

													case 2:
														System.out.println("\nIntroduzca el id de la entidad ");
														entradaEscaner = new Scanner (System.in);
														idEntidad = entradaEscaner.nextInt();

														System.out.println("\nIntroduzca la edicion donde se cambiara la aportacion ");
														entradaEscaner = new Scanner (System.in);
														anio = entradaEscaner.nextInt();

														System.out.println("\nIntroduzca la cantidad con la que patrocina ");
														entradaEscaner = new Scanner (System.in);
														aportacion = entradaEscaner.nextInt();

														try {
															query = "CALL modificaDineroPatr("+ idEntidad + "," + anio + "," + aportacion + ")";

															stmt = conn.createStatement();
															stmt.executeUpdate(query);
														} catch (SQLException e) {
															System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
														} catch (Exception e) {
															e.printStackTrace();
														}
													break;

													case 3:
														conn.commit();
													break;

													case 4:
														conn.rollback(save1);
													break;

													default: System.out.println("\u001B[31m" + "Por favor, seleccione una opcion valida: " + "\u001B[0m");
												}
											}
										break;

										case 5:
											conn.commit();
										break;

										case 6:
											conn.rollback();
										break;

										default: System.out.println("\u001B[31m" + "Por favor, seleccione una opcion valida: " + "\u001B[0m");
									}

								}
							break;

							case 5:
								int perhor = -1;
								while(perhor != 4 && perhor != 5) {
									System.out.println("\n\u001B[36m\t\t" + "--- Operaciones sobre personal y horarios ---" + "\u001B[0m");
									System.out.println("\u001B[33m" + "1.Insertar personal" + "\u001B[0m");
									System.out.println("\u001B[33m" + "2.Registrar trabajador" + "\u001B[0m");
									System.out.println("\u001B[33m" + "3.Modificar salario" + "\u001B[0m");
									System.out.println("\u001B[33m" + "4.Guardar cambios" + "\u001B[0m");
									System.out.println("\u001B[33m" + "5.Cancelar" + "\u001B[0m");

									entradaEscaner = new Scanner (System.in);
									perhor = entradaEscaner.nextInt();

									int idPersonal, anio, sueldo;

									switch(perhor) {
										case 1:
											String nombrep, apellidop, telefonop, emailp;

											System.out.println("\nIntroduzca el id del personal ");
											entradaEscaner = new Scanner (System.in);
											idPersonal = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el nombre ");
											entradaEscaner = new Scanner (System.in);
											nombrep = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca los apellidos ");
											entradaEscaner = new Scanner (System.in);
											apellidop = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca el telefono ");
											entradaEscaner = new Scanner (System.in);
											telefonop = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca el e-mail (debe contener la @) ");
											entradaEscaner = new Scanner (System.in);
											emailp = entradaEscaner.nextLine();

											try {
												query = "CALL insertarPersonal("+ idPersonal + ",'" + nombrep
												+ "','" + apellidop + "'," + telefonop + ",'" + emailp + "')";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 2:
											System.out.println("\nIntroduzca el id del personal ");
											entradaEscaner = new Scanner (System.in);
											idPersonal = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca la edicion donde trabajara ");
											entradaEscaner = new Scanner (System.in);
											anio = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el sueldo ");
											entradaEscaner = new Scanner (System.in);
											sueldo = entradaEscaner.nextInt();

											try {
												query = "CALL registrarTrabajador("+ idPersonal + "," + anio + "," + sueldo + ")";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 3:
											System.out.println("\nIntroduzca el id del personal ");
											entradaEscaner = new Scanner (System.in);
											idPersonal = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el sueldo ");
											entradaEscaner = new Scanner (System.in);
											sueldo = entradaEscaner.nextInt();

											try {
												query = "CALL modificarSalario("+ idPersonal + "," + sueldo + ")";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 4:
											conn.commit();
										break;

										case 5:
											conn.rollback();
										break;

										default: System.out.println("\u001B[31m" + "Por favor, seleccione una opcion valida: " + "\u001B[0m");
									}
								}

							break;

							case 6:
								int matped = -1;
								while(matped != 4 && matped != 5) {
									System.out.println("\n\u001B[36m\t\t" + "--- Operaciones sobre materiales y pedidos ---" + "\u001B[0m");
									System.out.println("\u001B[33m" + "1.Insertar material" + "\u001B[0m"); // proporciona
									System.out.println("\u001B[33m" + "2.Insertar pedido" + "\u001B[0m"); // bucle para insertar varios materiales en compuesto
									System.out.println("\u001B[33m" + "3.Asignar pedido a trabajador" + "\u001B[0m");
									System.out.println("\u001B[33m" + "4.Guardar cambios" + "\u001B[0m");
									System.out.println("\u001B[33m" + "5.Cancelar" + "\u001B[0m");

									entradaEscaner = new Scanner (System.in);
									matped = entradaEscaner.nextInt();

									int idMaterial, idPedido;

									switch(matped) {
										case 1:
											System.out.println("\nIntroduzca el id del material ");
											entradaEscaner = new Scanner (System.in);
											idMaterial = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el nombre del material ");
											entradaEscaner = new Scanner (System.in);
											String nombrem = entradaEscaner.nextLine();

											try {
												query = "CALL insertarMaterial("+ idMaterial + ",'" + nombrem + "')";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 2:
												System.out.println("\nIntroduzca el numero de pedido ");
												entradaEscaner = new Scanner (System.in);
												idPedido = entradaEscaner.nextInt();

												try {
													query = "CALL insertarPedido("+ idPedido + ")";

													stmt = conn.createStatement();
													stmt.executeUpdate(query);
												} catch (SQLException e) {
													System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
												} catch (Exception e) {
													e.printStackTrace();
												}
										break;

										case 3:
											System.out.println("\nIntroduzca el numero del pedido ");
											entradaEscaner = new Scanner (System.in);
											idPedido = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca el id del trabajador ");
											entradaEscaner = new Scanner (System.in);
											int idTrabajador = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca la edicion ");
											entradaEscaner = new Scanner (System.in);
											int anio = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca la pista ");
											entradaEscaner = new Scanner (System.in);
											int idPista = entradaEscaner.nextInt();

											System.out.println("\nIntroduzca la fecha de inicio ");
											entradaEscaner = new Scanner (System.in);
											String fechainicio = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca la fecha de fin ");
											entradaEscaner = new Scanner (System.in);
											String fechafin = entradaEscaner.nextLine();

											System.out.println("\nIntroduzca la fecha de recogida ");
											entradaEscaner = new Scanner (System.in);
											String fecharecogida = entradaEscaner.nextLine();

											try {
												query = "CALL insertarAsignacionRecogida("+ idPedido + "," + idTrabajador + "," +
												                                          anio + "," + idPista + ",TO_TIMESTAMP('" + fechainicio + "06:14:00.742000000','YYYY-MM-DD HH24:MI:SS.FF'),TO_TIMESTAMP('" +
																										fechafin + "11:14:00.742000000','YYYY-MM-DD HH24:MI:SS.FF'),TO_TIMESTAMP('" + fecharecogida + "09:14:00.742000000','YYYY-MM-DD HH24:MI:SS.FF'))";

												stmt = conn.createStatement();
												stmt.executeUpdate(query);
											} catch (SQLException e) {
												System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
											} catch (Exception e) {
												e.printStackTrace();
											}
										break;

										case 4:
											conn.commit();
										break;

										case 5:
											conn.rollback();
										break;

										default: System.out.println("\u001B[31m" + "Por favor, seleccione una opcion valida: " + "\u001B[0m");
									}
								}
							break;

							case 7:
								System.out.println("\u001B[31m" + "Cerrando la conexion..." + "\u001B[0m");
							break;

							default: System.out.println("\u001B[31m" + "Por favor, seleccione una opcion valida: " + "\u001B[0m");
						}
					}
				}
				finally {
					conn.close();
				}
			}
			else{
				System.out.println("\u001B[31m" + "Fail" + "\u001B[0m");
			}
		}
		catch(SQLException e){
			System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
		}

	}

}

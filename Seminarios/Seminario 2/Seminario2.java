import java.sql.*;
import java.util.Scanner;
import java.util.regex.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.*;


//Compilar con javac Seminario2.java
//Ejecutar con java -cp ojdbc8.jar:. Seminario2 (en el mismo directorio donde está ojdbc8.jar)

class Seminario2 {
	public static void main(String ar[]) {
		System.out.println("Introduzca su usuario (que coincide con su password): ");
		String entrada;
		Scanner entradaEscaner = new Scanner (System.in);
		entrada = entradaEscaner.nextLine();

		try(Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es", entrada, entrada)){
			if(conn != null){
				System.out.println("\u001B[32mConectado a la base de datos\u001B[0m");

				conn.setAutoCommit(false);

				String query;
				Statement stmt=null;

				try {
					int menu = -1;

					//Interfaz
					while( menu != 4 ){
						System.out.println("\n\u001B[34m\t\t--- MENU ---\u001B[0m");
						System.out.println("\u001B[36m1.Borrado y creacion de las tablas e insercion de tuplas\u001B[0m");
						System.out.println("\u001B[36m2.Dar de alta nuevo pedido\u001B[0m");
						System.out.println("\u001B[36m3.Borrar un pedido\u001B[0m");
						System.out.println("\u001B[36m4.Salir del programa y finalizar conexion\u001B[0m");

						entradaEscaner = new Scanner (System.in);
						menu = entradaEscaner.nextInt();

						switch(menu){
							case 1:
								//Dropear las tablas si existen
								try{
									query = "DELETE Detalle_pedido";
									stmt  = conn.createStatement();
									stmt.executeUpdate(query);

									query = "DROP TABLE Detalle_pedido";
									stmt  = conn.createStatement();
									stmt.executeUpdate(query);
									System.out.println("Tabla Detalle_pedido dropeada");

								} catch(Exception e){
									System.out.println("La tabla Detalle_pedido no existia");
								}

								try{
									query = "DELETE Pedido";
									stmt  = conn.createStatement();
									stmt.executeUpdate(query);

									query = "DROP TABLE Pedido";
									stmt  = conn.createStatement();
									stmt.executeUpdate(query);
									System.out.println("Tabla Pedido dropeada");

								} catch(Exception e){
									System.out.println("La tabla Pedido no existia");
								}

								try{
									query = "DELETE Stock";
									stmt  = conn.createStatement();
									stmt.executeUpdate(query);

									query = "DROP TABLE Stock";
									stmt  = conn.createStatement();
									stmt.executeUpdate(query);
									System.out.println("Tabla Stock dropeada");

								} catch(Exception e){
									System.out.println("La tabla Stock no existia");
								}

								//Crear tablas
								query = "CREATE TABLE Stock(Cproducto INT NOT NULL, Cantidad INT, PRIMARY KEY (Cproducto))";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query = "CREATE TABLE Pedido (Cpedido INT NOT NULL, Ccliente INT, Fecha_pedido DATE, PRIMARY KEY (Cpedido))";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query = "CREATE TABLE Detalle_pedido (Cpedido INT NOT NULL, Cproducto INT NOT NULL, Cantidad int,FOREIGN KEY(Cpedido) REFERENCES Pedido(Cpedido),FOREIGN KEY(Cproducto) REFERENCES Stock(Cproducto),PRIMARY KEY(Cpedido,Cproducto))";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								//Insertar tuplas
								//String[] insert_query = new String[10];

								query="INSERT INTO Stock(Cproducto, Cantidad) VALUES (1,34)";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query="INSERT INTO Stock(Cproducto, Cantidad) VALUES (2,66)";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query="INSERT INTO Stock(Cproducto, Cantidad) VALUES (3,12)";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query="INSERT INTO Stock(Cproducto, Cantidad) VALUES (4,46)";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query="INSERT INTO Stock(Cproducto, Cantidad) VALUES (5,2)";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query="INSERT INTO Stock(Cproducto, Cantidad) VALUES (6,34)";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query="INSERT INTO Stock(Cproducto, Cantidad) VALUES (7,21)";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query="INSERT INTO Stock(Cproducto, Cantidad) VALUES (8,17)";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query="INSERT INTO Stock(Cproducto, Cantidad) VALUES (9,50)";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								query="INSERT INTO Stock(Cproducto, Cantidad) VALUES (10,71)";
								stmt  = conn.createStatement();
								stmt.executeUpdate(query);

								//Intento de insertar con un bucle, no es straightforward
								/*for (int i=0; i<10; i++) {
									stmt  = conn.createStatement();
									stmt.executeUpdate(insert_query[i]);
									System.out.println("Insertando tupla " + i);
								}*/

								conn.commit();
								System.out.println("Datos insertados en la tabla Stock");

							break;

							case 2:
								//Creamos el savepoint al que volver si al final no se realiza el pedido
								Savepoint save = conn.setSavepoint();

								System.out.println("\n\u001B[36m--- Introduzca los datos del pedido ---\u001B[0m");
								System.out.println("Código de pedido: ");
								entradaEscaner = new Scanner (System.in);
								int cpedido = entradaEscaner.nextInt();

								System.out.println("Código de cliente: ");
								entradaEscaner = new Scanner (System.in);
								int ccliente = entradaEscaner.nextInt();

								//COMPROBACIÓN DE LA FECHA	
								boolean fecha_correcta = false;
  								boolean formato_correcto = false;
  								
  								while(!fecha_correcta){
									System.out.println("Fecha pedido (YYYY-MM-DD): ");
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
									  	
									  	if(mes == 2){
									  		if(1 <= dia && dia <= 28){
												query = "INSERT INTO PEDIDO VALUES("+cpedido+","+ccliente + ",TO_DATE('"+fecha+"','YYYY-MM-DD'))";
//"INSERT INTO Pedido VALUES(2,2,TO_DATE('2020-02-21','YYYY-MM-DD'))";
								  				
									  			stmt  = conn.createStatement();
												stmt.executeUpdate(query);
									  			System.out.println("\u001B[32mAñadiendo pedido \u001B[0m");
									  			fecha_correcta = true;
									  		}
									  	} 
									  	else{
									  		if(mes%2 == 1){
									  			if(1 <= dia && dia <= 31){
													query = "INSERT INTO PEDIDO VALUES("+cpedido+","+ccliente + ",TO_DATE('"+fecha+"','YYYY-MM-DD'))";
									  				
									  				stmt  = conn.createStatement();
													stmt.executeUpdate(query);
									  				System.out.println("\u001B[32mAñadiendo pedido \u001B[0m");
									  				fecha_correcta = true;
									  			}
									  		}
									  		else{
									  			if(1 <= dia && dia <= 30){
													query = "INSERT INTO PEDIDO VALUES("+cpedido+","+ccliente + ",TO_DATE('"+fecha+"','YYYY-MM-DD'))";
					  		
									  				stmt  = conn.createStatement();
													stmt.executeUpdate(query);
									  				System.out.println("\u001B[32mAñadiendo pedido \u001B[0m");
									  				fecha_correcta = true;
									  			}
									  		}
									  	}
								  	}
								  }	
								//TODO insercion

								int nuevo_pedido = -1;

								while(nuevo_pedido != 3 && nuevo_pedido != 4){
									System.out.println("\n\u001B[36m\t\t" + "--- NUEVO PEDIDO ---" + "\u001B[0m");
									System.out.println("\u001B[33m" + "1.Añadir detalle de producto" + "\u001B[0m");
									System.out.println("\u001B[33m" + "2.Eliminar todos los detalles de producto" + "\u001B[0m");
									System.out.println("\u001B[33m" + "3.Cancelar pedido" + "\u001B[0m");
									System.out.println("\u001B[33m" + "4.Finalizar pedido" + "\u001B[0m");

									entradaEscaner = new Scanner (System.in);
									nuevo_pedido = entradaEscaner.nextInt();

									switch(nuevo_pedido){
										case 1:
											System.out.println("Introduzca código de producto: ");
											entradaEscaner = new Scanner (System.in);
											int cproducto = entradaEscaner.nextInt();

											System.out.println("Introduzca cantidad: ");
											entradaEscaner = new Scanner (System.in);
											int cantidad = entradaEscaner.nextInt();

											stmt  = conn.createStatement();
											ResultSet rset = stmt.executeQuery ("SELECT Cantidad FROM Stock WHERE Cproducto='" + cproducto + "'");

											while(rset.next()){
												int cantidad_actual = rset.getInt(1);

												if(cantidad_actual >= cantidad){
													cantidad_actual -= cantidad;

													System.out.println("Cantidad disponible, procesando...");

													query="UPDATE Stock SET Cantidad=" + cantidad_actual + " WHERE Cproducto='" + cproducto + "'";
													stmt  = conn.createStatement();
													stmt.executeUpdate(query);

													System.out.println("Registrando pedido...");

																	//Esto lo tiene que hacer al entrar en el caso, pero lo necesito ahora mismo BORRAR LUEGO
																	//query="INSERT INTO Pedido(Cpedido) VALUES (" + cpedido + ")";
																	//stmt  = conn.createStatement();
																	//stmt.executeUpdate(query);

																	//conn.commit();


													query="INSERT INTO Detalle_pedido(Cpedido, Cproducto, Cantidad) VALUES ((SELECT CPedido FROM Pedido WHERE Cpedido='" + cpedido + "'), (SELECT Cproducto FROM Stock WHERE Cproducto='" + cproducto + "')," + cantidad + ")";
													stmt  = conn.createStatement();
													stmt.executeUpdate(query);

													//conn.commit();

												} else{
													System.out.println("Cantidad no disponible, faltan " + (cantidad-cantidad_actual) + " unidades");
												}
											}

										break;

										case 2:
											//TODO buscar en detalle_pedido una tupla con el cpedido
											// introducido y borrarla

										break;

										case 3:
											// Se vuelve al savepoint que hemos creado al principio y salimos al menú principal
											System.out.println("\u001B[31m" + "No se han hecho efectivos los cambios" + "\u001B[0m");
											conn.rollback(save);

										break;

										case 4:
											System.out.println("\u001B[32m" + "El pedido se ha guardado correctamente" + "\u001B[0m");
											conn.commit();

										break;

										default: System.out.println("\u001B[31m" + "Por favor, seleccione una opcion valida: " + "\u001B[0m");
									}
								}

							break;

							case 3:
								System.out.println("\nIntroduzca el código de pedido: ");
								entradaEscaner = new Scanner (System.in);
								cpedido = entradaEscaner.nextInt();
								//TODO borrar la tupla de pedido y sus correspondientes detalles
							break;

							case 4:
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

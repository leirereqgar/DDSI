import java.sql.*;
import java.util.Scanner;


//Compilar con javac Seminario2.java
//Ejecutar con java -cp ojdbc8.jar:. Seminario2 (en el mismo directorio donde está ojdbc8.jar)


class Seminario2 {
	public static void main(String ar[]) {
		System.out.println("Introduzca su usuario (que coincide con su password): ");
		String entrada																																		= "x5985367";
		Scanner entradaEscaner = new Scanner (System.in);
		//entrada = entradaEscaner.nextLine();

		try(Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es", entrada, entrada)){
			if(conn != null){
				System.out.println("Conectado a la base de datos");

				conn.setAutoCommit(false);

				String query;
				Statement stmt=null;

				try {
					/*//Dropear las tablas si existen
					query = "IF EXISTS (SELECT * FROM Detalle_pedido) DROP TABLE Detalle_pedido";
					stmt  = conn.createStatement();
					stmt.executeUpdate(query);

					query = "IF EXISTS (SELECT * FROM Pedido) DROP TABLE Pedido";
					stmt  = conn.createStatement();
					stmt.executeUpdate(query);

					query = "IF EXISTS (SELECT * FROM Stock) DROP TABLE Stock";
					stmt  = conn.createStatement();
					stmt.executeUpdate(query);*/



					int menu = -1;

					//Interfaz
					while( menu != 4 ){
						System.out.println("--- MENU ---");
						System.out.println("1.Borrado y creacion de las tablas e insercion de tuplas");
						System.out.println("2.Dar de alta nuevo pedido");
						System.out.println("3.Borrar un pedido");
						System.out.println("4.Salir del programa y finalizar conexion");

						entradaEscaner = new Scanner (System.in);
						menu = entradaEscaner.nextInt();

						switch(menu){
							case 1:
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
									//TODO

							break;

							case 2:
								System.out.println("--- Introduzca los datos del pedido ---");
								System.out.println("Código de pedido: ");
								entradaEscaner = new Scanner (System.in);
								int cpedido = entradaEscaner.nextInt();

								System.out.println("Código de cliente: ");
								entradaEscaner = new Scanner (System.in);
								int ccliente = entradaEscaner.nextInt();

								System.out.println("Fecha pedido (YYYY-MM-DD): ");
								entradaEscaner = new Scanner (System.in);
								String fecha = entradaEscaner.nextLine();

								//TODO insercion

								int nuevo_pedido = -1;

								while(nuevo_pedido != 4){
									System.out.println("--- NUEVO PEDIDO ---");
									System.out.println("1.Añadir detalle de producto");
									System.out.println("2.Eliminar todos los detalles de producto");
									System.out.println("3.Cancelar pedido");
									System.out.println("4.Finalizar pedido");

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

											//TODO consultar si hay stock.cantidad de stock.cproducto
											// y si la hay, restar la cantidad introducida de stock.cantidad
											// e insertar en detalle_pedido la cpedido, cproducto y cantidad

										break;

										case 2:
											//TODO buscar en detalle_pedido una tupla con el cpedido
											// introducido y borrarla

										break;

										case 3:
											//TODO Eliminar pedido y todos sus detalles (rollback)

										break;

										case 4:
											//TODO hacer los cambios permanentes (commit)

										break;

										default: System.out.println("Por favor, seleccione una opcion valida: ");
									}
								}

							break;

							case 3:
								System.out.println("Introduzca el código de pedido: ");
								entradaEscaner = new Scanner (System.in);
								int cpedido = entradaEscaner.nextInt();
								//TODO borrar la tupla de pedido y sus correspondientes detalles
							break;

							case 4:
								System.out.println("Cerrando la conexion...");
							break;

							default: System.out.println("Por favor, seleccione una opcion valida: ");
						}

					}
				}
				finally {
					conn.close();
				}


					/*//Dropear las tablas
					query = "DROP TABLE Detalle_pedido";
					stmt  = conn.createStatement();
					stmt.executeUpdate(query);

					query = "DROP TABLE Pedido";
					stmt  = conn.createStatement();
					stmt.executeUpdate(query);

					query = "DROP TABLE Stock";
					stmt  = conn.createStatement();
					stmt.executeUpdate(query);*/

			}
			else{
				System.out.println("Fail");
			}
		}
		catch(SQLException e){
			System.err.format("SQL State; %s\n%s",e.getSQLState(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
		}

	}

}

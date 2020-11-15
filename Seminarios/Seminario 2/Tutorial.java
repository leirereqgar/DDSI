import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Compilar con javac Tutorial.java
//Ejecutar con java -cp ojdbc8.jar:. Tutorial (en el mismo directorio donde está ojdbc8.jar)
class Tutorial
{
	public static void main(String ar[])
	{

		System.out.println("Database tutorial");
		
		try(Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es", "xDNI", "xDNI")){
		
			if(conn != null){
				System.out.println("Connected database");
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

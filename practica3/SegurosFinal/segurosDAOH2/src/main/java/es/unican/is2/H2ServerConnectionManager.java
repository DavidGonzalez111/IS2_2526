package es.unican.is2;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Clase que gestiona el acceso a la base de datos H2 en memoria.
 * Permite abrir conexiones y crear y rellenar la propia base de datos
 * al inicio de la aplicacion
 */
public class H2ServerConnectionManager {

	// Conexion con la base de datos
	protected static Connection connection;

	// Atributos de acceso a la Base de Datos
	protected static String dbName = "test";
	protected static String user = "sa";
	protected static String pass = "";

	/**
	 * Obtiene una conexion con la base de datos
	 * @return La conexion
	 * @throws DataAccessException si no se puede establecer al conexion
	 */
	public static Connection getConnection() throws DataAccessException {

		if (connection == null) { 
			try {
				Class.forName("org.h2.Driver"); //comprueba que el driver esta instalado
				connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa","");		
				cargaDatos();
			} catch (SQLException | ClassNotFoundException e) {
				throw new DataAccessException();
			}
		}
		return connection;
	}

	/**
	 * Crea la base de datos e introduce los datos iniciales
	 * @throws DataAccessException Si hay un fallo en la conexion
	 */
	public static void cargaDatos() throws DataAccessException {
		try (Connection con = getConnection();
			Statement stm = con.createStatement()) {

			// Creación de tablas
			String sql = "CREATE TABLE Clientes (dni CHAR(9) NOT NULL, nombre VARCHAR(100) NOT NULL, "
					+ "minusvalia BOOLEAN NOT NULL, PRIMARY KEY(dni))";
			stm.execute(sql);
			
			// Creacion de la tabla Vehiculos
			sql  = "CREATE TABLE Seguros (id BIGINT NOT NULL AUTO_INCREMENT, matricula CHAR(7) NOT NULL, fechaInicio DATE NOT NULL, "
					+ "cobertura VARCHAR(100) NOT NULL, potencia INT NOT NULL, conductorAdicional VARCHAR(100), cliente_FK CHAR(9) NOT NULL, "
					+ "PRIMARY KEY(id), FOREIGN KEY(cliente_FK) REFERENCES Clientes(dni))";

			stm.execute(sql); 		
			
			// Inicializa contribuyentes
			sql = "INSERT INTO Clientes (dni, nombre, minusvalia) VALUES ('11111111A', 'Juan', false)"; 
			stm.executeUpdate(sql);
			sql = "INSERT INTO Clientes (dni, nombre, minusvalia) VALUES ('22222222A', 'Ana', false)"; 
			stm.executeUpdate(sql);
			sql = "INSERT INTO Clientes (dni, nombre, minusvalia) VALUES ('33333333A', 'Luis', true)"; 
			stm.executeUpdate(sql);
			sql = "INSERT INTO Clientes (dni, nombre, minusvalia) VALUES ('44444444A', 'Pepe', false)"; 
			stm.executeUpdate(sql);
			
			// Inicializa vehiculos
			sql = "INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, cliente_FK) "
					+ "VALUES ('1111AAA', '2002-01-15', 'TERCEROS', 15, '11111111A')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, conductorAdicional, cliente_FK) "
					+ "VALUES ('1111BBB', '2016-05-20', 'TODO_RIESGO', 20, 'Pepe', '11111111A')";
			stm.executeUpdate(sql);			
			sql = "INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, cliente_FK) "
					+ "VALUES ('1111CCC', '2022-05-21', 'TERCEROS', 100, '11111111A')";
			stm.executeUpdate(sql);
			
			sql = "INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, cliente_FK) "
					+ "VALUES ('2222AAA', '2010-06-01', 'TERCEROS_LUNAS', 25, '22222222A')";
			stm.executeUpdate(sql);
			
			sql = "INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, cliente_FK) "
					+ "VALUES ('4444AAA', '2026-01-02', 'TERCEROS', 40, '44444444A')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Seguros (matricula, fechaInicio, cobertura, potencia, cliente_FK) "
					+ "VALUES ('4444BBB', '2026-01-02', 'TERCEROS_LUNAS', 300, '44444444A')";
			stm.executeUpdate(sql);
			
			
			// Cierra el statement
			stm.close();
			
		} catch (SQLException e) {
			System.out.println(e);
			throw new DataAccessException();
			
		} 		
	}

	/**
	 * Metodo estatico para ejecutar operaciones SQL y manejar los errores.
	 * 
	 * IMPORTANTE: este metodo solo puede ser llamado para operaciones de INSERT, UPDATE
	 * y DELETE. No debe usarse para realizar SELECTs ni llamadas a PROCEDIMIENTO, las ejecuciones
	 * de ese tipo de operaciones tienen que implementarse en sus respectivos metodos.
	 * @param stringStatement Instruccion a ejecutar
	 * @return true si se ha ejecutado la operacion
	 *         false si no se ha ejecutado la operacion
	 * @throws DataAccessException si hay un error en la conexion
	 */
	public static void executeSqlStatement(String stringStatement) throws DataAccessException {
		try (Connection con = getConnection();
			Statement stm = con.createStatement()) {

			stm.execute(stringStatement);

		} catch (SQLException e) {
			throw new DataAccessException();
		}
	}
}


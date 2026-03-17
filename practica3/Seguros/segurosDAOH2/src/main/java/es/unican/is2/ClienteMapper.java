package es.unican.is2;


import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * Clase de utilidad que mapea filas de la base de datos a 
 * instancias de Cliente
 */
public class ClienteMapper {

	/**
	 * Recibe una fila de la BBDD correspondinete a un Cliente 
	 * y devuelve un objeto Cliente con los datos correspondientes
	 * a sus atributos primitivos (no asociaciones multiples)
	 * @param results Fila resultado de una consulta en base de datos
	 * @return Cliente El cliente en su estado actual en BBDD
	 */
	public static Cliente toCliente(ResultSet results) throws DataAccessException {

		Cliente cont =null;
		try {
			String dni = results.getString("dni");
			String nombre = results.getString("nombre");
			boolean minusvalia = results.getBoolean("minusvalia");
			cont = new Cliente();
			cont.setDni(dni);
			cont.setNombre(nombre);	
			cont.setMinusvalia(minusvalia);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}
		return cont;
	}
}

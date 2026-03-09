package es.unican.is2.persistencia;


import es.unican.is2.comun.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Clase de utilidad que mapea filas de la base de datos a 
 * instancias de Seguro
 */
public class SeguroMapper {

	/**
	 * Recibe una fila de la BBDD correspondiente a un seguro 
	 * y devuelve un objeto Seguro con los datos correspondientes
	 * @param results Fila resultado de una consulta en base de datos
	 * @return El seguro en su estado actual en BBDD
	 */
	public static Seguro toSeguro(ResultSet results) throws DataAccessException {

		Seguro seg = null;
		try {
			long id = results.getLong("id");
			String matricula = results.getString("matricula");
			LocalDate fecha = results.getDate("fechaInicio").toLocalDate();
			Cobertura cobertura = Cobertura.valueOf(results.getString("cobertura"));
			int potencia = Integer.valueOf(results.getString("potencia"));
			String conductorAdicional = results.getString("conductorAdicional");
			seg = new Seguro();
			seg.setMatricula(matricula);
			seg.setCobertura(cobertura);	
			seg.setPotencia(potencia);
			seg.setConductorAdicional(conductorAdicional);
			seg.setFechaInicio(fecha);
			seg.setId(id);
			return seg;
		} catch (SQLException e) {
			throw new DataAccessException();
		}
	}

}

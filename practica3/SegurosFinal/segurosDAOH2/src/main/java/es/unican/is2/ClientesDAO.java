package es.unican.is2;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class ClientesDAO implements IClientesDAO {

	@Override
	public Cliente creaCliente(Cliente c) throws DataAccessException {
		String insertStatement = String.format(
				"insert into Clientes(dni, nombre, minusvalia) values ('%s', '%s', '%b')",
				c.getDni(),
				c.getNombre(),
				c.getMinusvalia());
		H2ServerConnectionManager.executeSqlStatement(insertStatement);
		return c;
	}

	@Override
	public Cliente cliente(String dni) throws DataAccessException {
		Cliente result = null; 
		String sql = "SELECT dni, nombre, minusvalia FROM Clientes WHERE dni = ?";
		try (Connection con = H2ServerConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {   // aquí se cierra automáticamente

			ps.setString(1, dni);
			try (ResultSet rs = ps.executeQuery()) {              // ResultSet también se cierra solo
				if (rs.next()) {
					result = procesaCliente(con, rs);
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException();
		}
		return result;
	}

	@Override
	public Cliente actualizaCliente(Cliente nuevo) throws DataAccessException {
		Cliente cliente = null;
		Cliente old = cliente(nuevo.getDni());
		String statementText;

		Connection con = H2ServerConnectionManager.getConnection();

		statementText = String.format(
				"update Clientes set nombre = '%s', minusvalia = '%b' where dni = '%s'", 
				nuevo.getNombre(), nuevo.getMinusvalia(), nuevo.getDni());
			H2ServerConnectionManager.executeSqlStatement(statementText);
			for(Seguro s: old.getSeguros()) {
				if (!nuevo.getSeguros().contains(s)) {
					statementText = String.format(
						"update Seguros set cliente_FK = null where id = '%d'",
						s.getId());
					H2ServerConnectionManager.executeSqlStatement(statementText);
				}
			}
			cliente = cliente(nuevo.getDni());
		
		return cliente;
	}

	@Override
	public Cliente eliminaCliente(String dni) throws DataAccessException {
		Cliente cliente = cliente(dni);
		Connection con = H2ServerConnectionManager.getConnection();
		String statementText = "delete from Clientes where dni = " + dni;
		H2ServerConnectionManager.executeSqlStatement(statementText);
		return cliente;
	}

	@Override
	public List<Cliente> clientes() throws DataAccessException {
		List<Cliente> clientes = new LinkedList<>();
		String sql = "SELECT dni, nombre, minusvalia FROM Clientes"; // listado explícito de columnas

		try (Connection con = H2ServerConnectionManager.getConnection();
			Statement statement = con.createStatement();
			ResultSet results = statement.executeQuery(sql)) {

			while (results.next()) {
				clientes.add(procesaCliente(con, results));
			}

		} catch (SQLException e) {
			throw new DataAccessException();
		}

		return clientes;
	}

	private Cliente procesaCliente(Connection con, ResultSet results) throws SQLException, DataAccessException {
		Cliente result = ClienteMapper.toCliente(results);
		// Cargamos los seguros del cliente
		String sql = "SELECT matricula, fechaInicio, cobertura, potencia, cliente_FK FROM Seguros WHERE cliente_FK = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, result.getDni());  // valor seguro
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					result.getSeguros().add(SeguroMapper.toSeguro(rs));
				}
			}
		}
		return result;
	}
	
}


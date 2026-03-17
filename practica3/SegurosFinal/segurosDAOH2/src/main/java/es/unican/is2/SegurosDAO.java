package es.unican.is2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class SegurosDAO implements ISegurosDAO {

	@Override
	public Seguro creaSeguro(Seguro s) throws DataAccessException {
		String insertStatement = String.format(
				"insert into Seguros(matricula, fechaInicio, cobertura, potencia, conductorAdicional) values ('%s', '%s', '%s', %d, '%s')",
				s.getMatricula(),
				s.getFechaInicio().toString(),
				s.getCobertura().toString(),
				s.getPotencia(),
				s.getConductorAdicional());
		H2ServerConnectionManager.executeSqlStatement(insertStatement);
		return s;
	}

	@Override
	public Seguro eliminaSeguro(long id) throws DataAccessException {
		Seguro seguro = seguro(id);
		Connection con = H2ServerConnectionManager.getConnection();
		String statementText = "delete from Seguros where id = " + id;
		H2ServerConnectionManager.executeSqlStatement(statementText);
		return seguro;
	}

	@Override
	public Seguro actualizaSeguro(Seguro nuevo) throws DataAccessException {
		Seguro seguro = null;
		Seguro old = seguro(nuevo.getId());
		String statementText;
		Connection con = H2ServerConnectionManager.getConnection();

		statementText = String.format(
				"update Seguros set matricula = '%s', fechaInicio = '%s', cobertura = '%s', potencia = '%d', conductorAdicional = '%s' where id = '%d'", 
				nuevo.getMatricula(), nuevo.getFechaInicio().toString(), nuevo.getCobertura().toString(), nuevo.getPotencia(), nuevo.getConductorAdicional(), nuevo.getId());
			H2ServerConnectionManager.executeSqlStatement(statementText);
			seguro = seguro(nuevo.getId());
		return seguro;
	}

	@Override
	public Seguro seguro(long id) throws DataAccessException {
		Seguro result = null; 
		String sql = "SELECT matricula, fechaInicio, cobertura, potencia, cliente_FK FROM Seguros WHERE id = ?";
		try (Connection con = H2ServerConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, id);  // parámetro seguro
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) { 
					result = SeguroMapper.toSeguro(rs);
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException();
		}
		return result;
	}

	@Override
	public List<Seguro> seguros() throws DataAccessException {
		List<Seguro> seguros = new LinkedList<>();
		String sql = "SELECT id, matricula, fechaInicio, cobertura, potencia, conductorAdicional, cliente_FK FROM Seguros"; // columnas explícitas

		try (Connection con = H2ServerConnectionManager.getConnection();
			Statement statement = con.createStatement();
			ResultSet results = statement.executeQuery(sql)) {

			while (results.next()) {
				seguros.add(SeguroMapper.toSeguro(results));
			}

		} catch (SQLException e) {
			throw new DataAccessException();
		}

		return seguros;
	}

	@Override
	public Seguro seguroPorMatricula(String matricula) throws DataAccessException {
		Seguro result = null; 
		String sql = "SELECT matricula, fechaInicio, cobertura, potencia, cliente_FK FROM Seguros WHERE matricula = ?";
		try (Connection con = H2ServerConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, matricula);  // se pasa el parámetro de forma segura
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) { 
					result = SeguroMapper.toSeguro(rs);
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException();
		}
		return result;
	}
	
	

	

}

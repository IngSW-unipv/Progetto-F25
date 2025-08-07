//PRIMARY KEY = nomeSconto

package it.unipv.poisw.f25.gympal.persistence.beans.Sconto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;

public class ScontoDAO implements IScontoDAO{
	
	private final IConnectionFactory connectionFactory;

	public ScontoDAO(IConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	@Override
	// Recupera tutti gli sconti dal database
	public List<Sconto> selectAll(){
		List<Sconto> sconti = new ArrayList<>();
		String query = "SELECT NOME_SCONTO, DATA_SCONTO, AMOUNT_SCONTO FROM DATE_SCONTI";
		
		// Blocco try-with-resources, chiude in automatico le risorse
		try(Connection conn = connectionFactory.createConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs  = stmt.executeQuery(query)){
			
			while(rs.next()) {
				sconti.add(mapResultSetToSconto(rs));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return sconti;
	}
	
	@Override
	// Recupera tutti gli sconti in una data dal database
	public List<Sconto> selectAllScontiByDate(Sconto sconto){
		List<Sconto> sconti = new ArrayList<>();
		String query = "SELECT NOME_SCONTO, DATA_SCONTO, AMOUNT_SCONTO FROM DATE_SCONTI WHERE DATA_SCONTO = ?";
		
		// Blocco try-with-resources, chiude in automatico le risorse
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			ps.setDate(1, Date.valueOf(sconto.getDataSconto()));
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					sconti.add(mapResultSetToSconto(rs));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return sconti;
	}
	
	@Override
	// Recupera uno sconto dal database
	public Sconto selectSconto(Sconto sconto) {
		Sconto result = null;
		String query = "SELECT NOME_SCONTO, DATA_SCONTO, AMOUNT_SCONTO FROM DATE_SCONTI WHERE NOME_SCONTO = ?";
		
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			ps.setString(1, sconto.getNomeSconto());
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					result = mapResultSetToSconto(rs);
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	// Inserisce un nuovo sconto nel database 
	public boolean insertSconto(Sconto sconto) {
		
		String query = "INSERT INTO DATE_SCONTI (NOME_SCONTO, DATA_SCONTO, AMOUNT_SCONTO) VALUES (?, ?, ?)";
		
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			if (connectionFactory.isReadOnlyMode()) {
	            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile inserire nuovi dati.");
	            return false;
	        }
			
			ps.setString(1, sconto.getNomeSconto());
			ps.setDate(2, Date.valueOf(sconto.getDataSconto()));
			ps.setBigDecimal(3, sconto.getAmountSconto());
			
			return ps.executeUpdate() > 0;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	// Aggiorna i dati di uno sconto esistente nel database
	public boolean updateSconto(Sconto sconto) {
		
		String query = "UPDATE DATE_SCONTI SET DATA_SCONTO = ?, AMOUNT_SCONTO = ? WHERE NOME_SCONTO = ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			if (connectionFactory.isReadOnlyMode()) {
	            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile aggiornare i dati.");
	            return false;
	        }
			
			ps.setDate(1, Date.valueOf(sconto.getDataSconto()));
			ps.setBigDecimal(2, sconto.getAmountSconto());	
			ps.setString(3, sconto.getNomeSconto());
			
			return ps.executeUpdate() > 0;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	// Cancella uno sconto dal database 
	public boolean deleteSconto(Sconto sconto) {
		
		String query = "DELETE FROM DATE_SCONTI WHERE NOME_SCONTO = ?";
		
		// Blocco try-with-resources
		try(Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			if (connectionFactory.isReadOnlyMode()) {
	            System.err.println("AVVISO: Il sistema è in modalità di sola lettura. Impossibile eliminare i dati.");
	            return false;
	        }
			
			ps.setString(1, sconto.getNomeSconto());
			return ps.executeUpdate() > 0;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	private Sconto mapResultSetToSconto(ResultSet rs) throws SQLException{
		String nomeSconto = rs.getString("NOME_SCONTO");
		LocalDate dataSconto = rs.getDate("DATA_SCONTO").toLocalDate();
		BigDecimal amountSconto= rs.getBigDecimal("AMOUNT_SCONTO");
		
		return new Sconto(nomeSconto, dataSconto, amountSconto);
		
	}

}

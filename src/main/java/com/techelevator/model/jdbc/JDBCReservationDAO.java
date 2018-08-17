package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import com.techelevator.model.domain.Tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import com.techelevator.model.dao.ReservationDAO;
import com.techelevator.model.domain.Reservation;
import com.techelevator.model.domain.ShoppingCart;


@Component
public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservation> searchToolsByName(String userName) {

		List<Reservation> searchToolsByName = new ArrayList<>();

		LocalDate date = LocalDate.now();

		String sqlSearchToolsByName = "SELECT t.tool_id, tt.tool_name, au.user_name, r.from_date, r.to_date "
				+ "FROM app_user au " + "JOIN reservation r ON r.app_user_id = au.app_user_id "
				+ "JOIN tool_reservation tr ON r.reservation_id = tr.reservation_id "
				+ "JOIN tool t ON tr.tool_id = t.tool_id " + "JOIN tool_type tt ON t.tool_type_id = tt.tool_type_id "
				+ "WHERE au.user_name = ? "
				+ "AND (to_date(?, 'YYYY/MM/DD')) <= r.to_date AND (to_date(?, 'YYYY/MM/DD')) >= r.from_date";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchToolsByName, userName.toUpperCase(), date.toString(),
				date.toString());

		while (results.next()) {
			Reservation theReservation = mapRowToReservation(results);
			searchToolsByName.add(theReservation);
		}
		return searchToolsByName;
	}

	@Override
	public List<Reservation> searchToolsByDriversLicense(String driversLicense) {

		List<Reservation> searchToolsByDriversLicense = new ArrayList<>();

		LocalDate date = LocalDate.now();

		String sqlSearchToolsByDriversLicense = "SELECT t.tool_id, tt.tool_name, au.user_name, r.from_date, r.to_date "
				+ "FROM app_user au " + "JOIN reservation r ON r.app_user_id = au.app_user_id "
				+ "JOIN tool_reservation tr ON r.reservation_id = tr.reservation_id "
				+ "JOIN tool t ON tr.tool_id = t.tool_id " + "JOIN tool_type tt ON t.tool_type_id = tt.tool_type_id "
				+ "WHERE au.drivers_license = ? "
				+ "AND (to_date(?, 'YYYY/MM/DD')) <= r.to_date AND (to_date(?, 'YYYY/MM/DD')) >= r.from_date";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchToolsByDriversLicense, driversLicense, date.toString(),
				date.toString());

		while (results.next()) {
			Reservation theReservation = mapRowToReservation(results);
			searchToolsByDriversLicense.add(theReservation);
		}
		return searchToolsByDriversLicense;
	}

	@Override
	public List<Reservation> searchToolsByToolNumber(int toolId) {

		List<Reservation> searchToolsByToolNumber = new ArrayList<>();

		LocalDate date = LocalDate.now();

		String sqlSearchToolsByToolNumber = "SELECT t.tool_id, tt.tool_name, au.user_name, r.from_date, r.to_date "
				+ "FROM app_user au " + "JOIN reservation r ON r.app_user_id = au.app_user_id "
				+ "JOIN tool_reservation tr ON r.reservation_id = tr.reservation_id "
				+ "JOIN tool t ON tr.tool_id = t.tool_id " + "JOIN tool_type tt ON t.tool_type_id = tt.tool_type_id "
				+ "WHERE t.tool_id = ? "
				+ "AND (to_date(?, 'YYYY/MM/DD')) <= r.to_date AND (to_date(?, 'YYYY/MM/DD')) >= r.from_date";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchToolsByToolNumber, toolId, date.toString(),
				date.toString());

		while (results.next()) {
			Reservation theReservation = mapRowToReservation(results);
			searchToolsByToolNumber.add(theReservation);
		}
		return searchToolsByToolNumber;
	}
	
	@Override
	public List<Reservation> getAllCheckedOutTools(){
		
		List<Reservation> searchForCheckedOutTools = new ArrayList<>();
		
		LocalDate date = LocalDate.now();
	
		String sqlSearchCheckedOutTools = "SELECT t.tool_id, tt.tool_name, au.user_name, r.from_date, r.to_date "
				+ "FROM app_user au " + "JOIN reservation r ON r.app_user_id = au.app_user_id "
				+ "JOIN tool_reservation tr ON r.reservation_id = tr.reservation_id "
				+ "JOIN tool t ON tr.tool_id = t.tool_id " + "JOIN tool_type tt ON t.tool_type_id = tt.tool_type_id "
				+ "AND (to_date(?, 'YYYY/MM/DD')) <= r.to_date AND (to_date(?, 'YYYY/MM/DD')) >= r.from_date "
				+ "ORDER BY t.tool_id";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchCheckedOutTools, date.toString(),
				date.toString());
		
		while (results.next()) {
			Reservation theReservation = mapRowToReservation(results);
			searchForCheckedOutTools.add(theReservation);
		}
		return searchForCheckedOutTools;
	}

	public List<Reservation> searchReservationsByReservationNumber(int reservationId){
		
		List<Reservation> getReservationById = new ArrayList<>();
		
		String sqlSearchReservationById = "SELECT au.user_name, t.tool_id, tt.tool_name, r.to_date, r.from_date "
										+"FROM reservation r "
										+"JOIN tool_reservation tr ON r.reservation_id = tr.reservation_id "
										+"JOIN app_user au ON r.app_user_id = au.app_user_id "
										+"JOIN tool t ON t.tool_id = tr.tool_id "
										+" JOIN tool_type tt ON t.tool_type_id = tt.tool_type_id "
										+"WHERE r.reservation_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchReservationById, reservationId);
		
		while (results.next()) {
			Reservation theReservation = mapRowToReservation(results);
			getReservationById.add(theReservation);
		}
		
		return getReservationById;
	}
	
	@Override
	public int saveNewReservation(ShoppingCart cart, int memberId) {
		LocalDate date = LocalDate.now();
		
		List<Tool> items = cart.getItems();
		if(items.size() == 0) {
		}
		String sqlSaveNewReservation = "INSERT INTO reservation (app_user_id, from_date, to_date) VALUES (?,?,?)";
		
		jdbcTemplate.update(sqlSaveNewReservation, memberId, date, date.plusDays(7));
		
		String sqlGetReservationId = "SELECT reservation_id FROM reservation where reservation_id=(SELECT MAX(reservation_id) FROM reservation)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservationId);

		int reservationId = 0;
	
		while(results.next()) {
			
			reservationId = Integer.parseInt(results.getString("reservation_id"));
		
		}
				
		String sqlInsertTool = "INSERT INTO tool_reservation (tool_id, reservation_id) VALUES (?,?)";
		
		for (Tool e : items) {
			
			jdbcTemplate.update(sqlInsertTool, e.getToolId(), reservationId);	
		}
		
		return reservationId;
	}

	public Reservation mapRowToReservation(SqlRowSet results) {

		Reservation newReservation = new Reservation();

		String memberName = results.getString("user_name").substring(0, 1) + results.getString("user_name").toLowerCase().substring(1).toLowerCase();
		String toolName = results.getString("tool_name").substring(0, 1) + results.getString("tool_name").toLowerCase().substring(1).toLowerCase();
		String checkoutDate = results.getString("from_date").substring(5) + "-" + results.getString("from_date").substring(0,4);
		String returnDate = results.getString("to_date").substring(5) + "-" + results.getString("to_date").substring(0,4);
		
		newReservation.setToolId(results.getInt("tool_id"));
		newReservation.setMemberName(memberName);
		newReservation.setToolName(toolName);
		newReservation.setCheckoutDate(checkoutDate);
		newReservation.setReturnDate(returnDate);

		return newReservation;
	}

}
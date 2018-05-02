package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.bean.EventType;
import it.polito.tdp.poweroutages.bean.PowerOutage;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutageDAO {

	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}

	public List<PowerOutage> getPowerOutagesForNerc(Nerc n) {
		String sql = "SELECT * FROM poweroutages,eventtype WHERE nerc_id=? AND eventtype.id=poweroutages.event_type_id";
		List<PowerOutage> poList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				EventType eventType = new EventType(res.getInt("event_type_id"), res.getString("value"));
				
				LocalDateTime ldtinizio = res.getTimestamp("date_event_began").toLocalDateTime();
				LocalDateTime ldtfine = res.getTimestamp("date_event_finished").toLocalDateTime();
				PowerOutage po = new PowerOutage(res.getInt("id"), n, ldtinizio, ldtfine, res.getInt("customers_affected"));
				po.setEventType(eventType);
				
				poList.add(po);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return poList;
	}

}

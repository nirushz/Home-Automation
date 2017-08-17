package com.ilrd.javascript_to_tomcat;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class DevicesCrud extends Crud<Integer,RegisterDeviceData>{

	public DevicesCrud(String DBName, String tableName) {
		super(DBName, tableName);
	}

	@Override
	Integer create(RegisterDeviceData data) throws IOException {
		
		String insert = "INSERT INTO " + getTableName() + " (userID, companyName, deviceName, deviceID)"
				+ " VALUES ('" + data.getUserID() + "', '" + data.getCompanyName() + 
							"', '" + data.getDeviceName() +"', '" + data.getDeviceID() + "')";
		
		int rs = -1;
		try {
			PreparedStatement pst = getCon().prepareStatement(insert);
			rs = pst.executeUpdate(insert);
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getErrorCode();	//In case of SQL ERROR - return the error code.
		}
		
		return rs;
	}

	@Override
	JSONArray read(Integer deviceID) throws IOException {
		JSONArray jsonArray = new JSONArray();
		PreparedStatement pst;
		/*
		try {
			String useDB = "USE "+ getDBName();
			pst = getCon().prepareStatement(useDB);
			int rs2 = pst.executeUpdate(useDB);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		*/
		String read = "SELECT * FROM "+ getTableName();
		try {
			pst = getCon().prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){		       
				 JSONObject jsonObj = new JSONObject();
				 jsonObj.put("userID",rs.getString("userID"));
				 jsonObj.put("companyName",rs.getString("companyName"));
				 jsonObj.put("deviceID",rs.getString("deviceID"));
				 jsonObj.put("deviceName",rs.getString("deviceName"));
				 jsonArray.put(jsonObj);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jsonArray;
	}

	@Override
	void update(Integer deviceID, RegisterDeviceData data) throws IOException {
		
	}

	@Override
	void delete(Integer deviceID) throws IOException {
		
		String delete = "DELETE FROM " + getTableName() + " WHERE deviceID='" + deviceID +"'";
		
		int rs = -1;
		try {
			PreparedStatement pst = getCon().prepareStatement(delete);
			rs = pst.executeUpdate(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
		

}

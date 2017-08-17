package com.ilrd.javascript_to_tomcat;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataCrud extends Crud<Integer,RegisterDeviceData>{

	public DataCrud(String DBName, String tableName) {
		super(DBName, tableName);
	}

	@Override
	Integer create(RegisterDeviceData data) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	JSONArray read(Integer deviceID) throws IOException {
		JSONArray jsonArray = new JSONArray();
		PreparedStatement pst;
		String read = "SELECT * FROM "+ getTableName() + " where deviceID=" + deviceID;
		
		try {
			pst = getCon().prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){		       
				 JSONObject jsonObj = new JSONObject();
				 jsonObj.put("deviceID",rs.getString("deviceID"));
				 jsonObj.put("deviceData",rs.getString("deviceData"));
				 jsonArray.put(jsonObj);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}

	
	
	
	@Override
	void update(Integer key, RegisterDeviceData data) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	void delete(Integer key) throws IOException {
		// TODO Auto-generated method stub
		
	}

	
}

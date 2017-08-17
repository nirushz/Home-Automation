package com.ilrd.javascript_to_tomcat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.json.JSONArray;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public abstract class Crud<K,V> {
	
	private String DBName;
	private String tableName;
	private java.sql.Connection con = null;
	private PreparedStatement pst = null;
	private Properties props = new Properties();
	private FileInputStream fis = null;
	
	public Crud(String DBName, String tableName) {
		this.DBName = DBName;
		this.tableName = tableName;
		ConnectDB();
	}
	
	
	private void ConnectDB(){
		MysqlDataSource ds = null;
		try {
			fis = new FileInputStream("/home/tmnt/svn/Proj/src/java/workspace/SyslogToDB/src/main/java/com/ilrd/SyslogToDB/db.properties");
			props.load(fis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		ds = new MysqlConnectionPoolDataSource();
		ds.setURL(props.getProperty("mysql.url"));	
		ds.setUser(props.getProperty("mysql.username")); 
		ds.setPassword(props.getProperty("mysql.password"));
		
		try {		
			con = ds.getConnection();
			String db = "CREATE DATABASE " + DBName;
			pst = con.prepareStatement(db);
			int rs1 = pst.executeUpdate(db);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		
		try {
			String useDB = "USE " + DBName;
			pst = con.prepareStatement(useDB);
			int rs2 = pst.executeUpdate(useDB);
			String table = "CREATE TABLE " + tableName + "(userID varchar(15), companyName varchar(25), deviceName varchar(100), deviceID int NOT NULL, PRIMARY KEY (deviceID))"; 
			pst = con.prepareStatement(table);
			int rs3 = pst.executeUpdate(table);
			
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	} 
	
	
	abstract K create(V data) throws IOException;
	
	abstract JSONArray read(K key) throws IOException;
	
	abstract void update(K key, V data) throws IOException;
	
	abstract void delete(K key) throws IOException;
	
	
	/********  Getters *************/
	
	public String getDBName() {
		return DBName;
	}


	public String getTableName() {
		return tableName;
	}


	public java.sql.Connection getCon() {
		return con;
	}


	public PreparedStatement getPst() {
		return pst;
	}

	
}

package com.ilrd.javascript_to_tomcat;

class RegisterDeviceData {
	
	private String userID, companyName, deviceName, deviceID;
	
	RegisterDeviceData(String userID, String companyName, String deviceName, String deviceID) {
		this.userID = userID;
		this.companyName = companyName;
		this.deviceName = deviceName;
		this.deviceID = deviceID;
	}
	
	public String getUserID() {
		return userID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getDeviceID() {
		return deviceID;
	}


}

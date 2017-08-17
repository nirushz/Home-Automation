package com.ilrd.javascript_to_tomcat;

public class CrudKey {
	
	private String companyName; 	//DB
	private String deviseName;		//devise table
	
	public CrudKey(String companyName, String devise) {
		this.companyName = companyName;
		this.deviseName = devise;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getDeviseName() {
		return deviseName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((deviseName == null) ? 0 : deviseName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CrudKey other = (CrudKey) obj;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (deviseName == null) {
			if (other.deviseName != null)
				return false;
		} else if (!deviseName.equals(other.deviseName))
			return false;
		return true;
	}
}

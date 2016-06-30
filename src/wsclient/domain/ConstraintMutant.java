package wsclient.domain;

import java.util.ArrayList;

public class ConstraintMutant {
	private int id;
	private String paraName;
	private ArrayList<Constraint> constraintList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getParaName() {
		return paraName;
	}
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	public ArrayList<Constraint> getConstraintList() {
		return constraintList;
	}
	public void setConstraintMutantList(ArrayList<Constraint> constraintList) {
		this.constraintList = constraintList;
	}
	@Override
	public String toString() {
		return "ConstraintMutant [id=" + id + ", paraName=" + paraName
				+ ", constraintList=" + constraintList + "]";
	}
	
}

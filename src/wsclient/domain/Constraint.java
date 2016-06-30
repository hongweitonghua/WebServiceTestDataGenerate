package wsclient.domain;

public class Constraint {
	//the constraint's name
	private String constraintName;
	//the constraint's value
	private String constraintValue;
	
	public Constraint(String constraintName, String constraintValue) {
		super();
		this.constraintName = constraintName;
		this.constraintValue = constraintValue;
	}
	public Constraint() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getConstraintName() {
		return constraintName;
	}
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	public String getConstraintValue() {
		return constraintValue;
	}
	public void setConstraintValue(String constraintValue) {
		this.constraintValue = constraintValue;
	}
	@Override
	public String toString() {
		return "Constraint [constraintName=" + constraintName
				+ ", constraintValue=" + constraintValue + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		Constraint c = (Constraint)obj;
		return this.constraintName.equals(c.getConstraintName());
	}
	
	
	
}

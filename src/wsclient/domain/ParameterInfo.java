package wsclient.domain;

import java.util.ArrayList;
import java.util.HashSet;

public class ParameterInfo {
	private String name;//参数名
	private String kind;//参数类型
	private int id;//参数标识 
	private String value;//参数值
	private String serviceid;//服务id
	private String operationname;//操作名
	private String inputtype=null;
	private String type;
	private ArrayList<Constraint> constraintList;
	private ArrayList testData;
	
	
	public ArrayList getTestData() {
		return testData;
	}
	public void setTestData(ArrayList testData) {
		this.testData = testData;
	}
	public ArrayList<Constraint> getConstraintList() {
		return constraintList;
	}
	public void setConstraintList(ArrayList<Constraint> constraintList) {
		this.constraintList = constraintList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInputtype() {
		return inputtype;
	}
	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}
	public String getOperationname() {
		return operationname;
	}
	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}
	public String getServiceid() {
		return serviceid;
	}
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String name2) {
		this.kind = name2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

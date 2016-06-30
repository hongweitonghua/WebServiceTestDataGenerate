package wsclient.util;

import java.util.ArrayList;
import java.util.Date;

import wsclient.domain.Constraint;
import wsclient.domain.ConstraintMutant;
import wsclient.domain.ParameterInfo;

public class CreateConstraintMutant {
	//约束变异体集合
	private static ArrayList<ConstraintMutant> constraintMutantList = new ArrayList<ConstraintMutant>();
	//生成约束变异时间,ms为单位
	private String mutationTime;
	
	private final Date startCreate;
	private Date endCreate;
	
	public CreateConstraintMutant(){
		startCreate = new Date();
		
	}
	
	/**
	 * 生成约束变异体集合
	 * @return 约束变异体集合
	 */
	public ArrayList<ConstraintMutant> getConstraintMutantList(ParameterInfo paraInfo){
		
		createTestCRPMutant(paraInfo);
		return constraintMutantList;
	}
	/**
	 * 创建CRP（约束替换）的约束变异体集合
	 * @param alist 原有的数据约束集合
	 */
	public void createTestCRPMutant(ParameterInfo paraInfo){
		System.out.println("11111111");
		String[] constraintArr = {"minInclusive","minExclusive","maxInclusive","maxExclusive"};
		ArrayList<Constraint> originalConstraintList = paraInfo.getConstraintList();
		if(!originalConstraintList.isEmpty()){
			for (Constraint constraint : originalConstraintList) {
				System.out.println(constraint); 
				for (String s : constraintArr) {
					if(!s.equals(constraint.getConstraintName())){
						ConstraintMutant cm = new ConstraintMutant();
						if(constraintMutantList==null)
							cm.setId(1);
						else
							cm.setId(constraintMutantList.size()+1);				//设置ConstraintMutant序号
						cm.setParaName(paraInfo.getName()); 						//设置ConstraintMutant参数名
						ArrayList<Constraint> cList = new ArrayList<Constraint>();	//设置ConstraintMutant约束列表
						Constraint c = new Constraint();
						c.setConstraintName(s);
						c.setConstraintValue(constraint.getConstraintValue());
						cList.add(c);
						cm.setConstraintMutantList(cList);
						constraintMutantList.add(cm);
						System.out.print("");
					}
				}//for
			}//for
		}
		System.out.println("我要输出！");
		for (ConstraintMutant cm : constraintMutantList) {
			System.out.println(cm.getId()+"..."+cm.getParaName()+"...");
			for (Constraint c : cm.getConstraintList()) {
				System.out.println(c.getConstraintName()+"..."+c.getConstraintValue());
			}
		}	
	}
	
	
	/**
	 * 计算约束变异体生成时间
	 * @return 约束变异体生成时间
	 */
	public String getMutationTime(){
		endCreate = new Date();
		
		//计算时间差，ms为单位
		return null;		
	}
	
}

package wsclient.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import wsclient.domain.Constraint;
import wsclient.domain.ConstraintMutant;
import wsclient.domain.OperationInfo;
import wsclient.domain.ParameterInfo;

public class CreateConstraintMutant {
	//约束变异体集合
	private static ArrayList<ConstraintMutant> constraintMutantList = new ArrayList<ConstraintMutant>();
	//生成约束变异时间,ms为单位
	private String mutationTime;
	
	private  Date startCreate;
	private  Date endCreate;
	private final int Button = Integer.MIN_VALUE;
	private final int Top = Integer.MAX_VALUE;
	public CreateConstraintMutant(){
		startCreate = new Date();
		
	}
	
	/**
	 * 生成约束变异体集合
	 * @return 约束变异体集合
	 */
	public ArrayList<ConstraintMutant> getConstraintMutantList(ParameterInfo paraInfo,OperationInfo operationInfo){
		//createTestCRPMutant(paraInfo);
		//createTestVRPMutant(paraInfo);
		/*System.out.println("--------------------------------------------------------");
		createTestCECMutant(operationInfo);*/
		printConstraintMutantList();	
		endCreate = new Date();
		return constraintMutantList;
	}
	/**
	 * 创建CRP（约束替换）的约束变异体集合
	 * @param alist 原有的数据约束集合
	 */
	public void createTestCRPMutant(ParameterInfo paraInfo){
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
	}
	/**
	 * 创建VRP（约束值替换）的约束变异体集合
	 * @param alist 原有的数据约束集合
	 */
	public void createTestVRPMutant(ParameterInfo paraInfo){
		ArrayList<Constraint> originalConstraintList = paraInfo.getConstraintList();
		
		if(!originalConstraintList.isEmpty()){
			int min = Button;
			int max = Top;
			String paraName = paraInfo.getName();
			//遍历每一个约束,获取到最大值和最小值
			for (Constraint constraint : originalConstraintList) {
				String constraintName = constraint.getConstraintName();
				if("minInclusive".equals(constraintName) || "minExclusive".equals(constraintName)){
					min = Integer.parseInt(constraint.getConstraintValue());
				}
				if("maxInclusive".equals(constraintName) || "maxExclusive".equals(constraintName)){
					max = Integer.parseInt(constraint.getConstraintValue());
				}
			}
			System.out.println("min="+min+"...max="+max);
			//得到三个测试数据点test1，test2，test3
			int test1 = TestDataGenerateSupport.getRandomNum(Button,min);
			int test2 = TestDataGenerateSupport.getRandomNum(min,max);
			int test3 = TestDataGenerateSupport.getRandomNum(max,Top);
			for (Constraint constraint : originalConstraintList) {
				String constraintName = constraint.getConstraintName();
				createMutantObject(paraName, constraintName,Button, test1);
				createMutantObject(paraName, constraintName,test1, min);
				createMutantObject(paraName, constraintName,min, test2);
				createMutantObject(paraName, constraintName,test2, max);
				createMutantObject(paraName, constraintName,max, test3);
				createMutantObject(paraName, constraintName,test3, Top);				
			}
		}
	}
	
	/**
	 * 创建LOR（逻辑运算符替换）的约束变异体集合
	 */
	public void createTestLORMutant(ParameterInfo paraInfo){
		ArrayList<Constraint> originalConstraintList = paraInfo.getConstraintList();
		
		if(!originalConstraintList.isEmpty()){
			
		}
	}
	/**
	 * 创建CEC（约束互换）的约束变异体集合
	 * 思想：获得所有变量名列表，打乱变量名顺序，再依次将operationInfo里面的paraName改为变量名列表中的值，依此达到约束互换的目的
	 */
	public ArrayList<ConstraintMutant> createTestCECMutant(OperationInfo operationInfo){
		@SuppressWarnings("unchecked")
		ArrayList<ParameterInfo> parameterList = (ArrayList) operationInfo.getInparameters();
		/** 所有变量名列表 */
		ArrayList<String> paraNameList= new ArrayList<String>();
		ArrayList<String> paraNameList2= new ArrayList<String>();
		System.out.println("原来的变量名列表：");
		for (ParameterInfo paraInfo : parameterList) {
			System.out.print(paraInfo.getName()+"...");
			paraNameList.add(paraInfo.getName());			
		}
		paraNameList2.addAll(paraNameList);
		//随机打乱变量名顺序
		/*while(!isNotSame(paraNameList,paraNameList2)){
			Collections.shuffle(paraNameList);
		}*/
		Collections.shuffle(paraNameList);
		System.out.println("随机打乱变量名顺序:");
		for (String string : paraNameList) {
			System.out.print(string+",,,");
		}
		//按照打乱的顺序，依次赋值给operationInfo中的paramterInfo的变量名
		for (int i = 0; i < paraNameList.size(); i++) {
			parameterList.get(i).setName(paraNameList.get(i));			
		}	
		System.out.println("互换后的的变量名列表：");
		for (ParameterInfo paraInfo : parameterList) {
			System.out.print(paraInfo.getName()+"...");
			paraNameList.add(paraInfo.getName());			
		}
		
		//添加CEC约束变异体加入到constraintMutantList
		for (ParameterInfo paraInfo : parameterList) {
			ArrayList<Constraint> constraintList = paraInfo.getConstraintList();
			String paraName = paraInfo.getName();	
			ArrayList<Constraint> cList = new ArrayList<Constraint>();	//设置ConstraintMutant约束列表	
			ConstraintMutant cm = new ConstraintMutant();
			if(constraintMutantList==null)
				cm.setId(1);
			else
				cm.setId(constraintMutantList.size()+1);				//设置ConstraintMutant序号
			cm.setParaName(paraInfo.getName()); 						//设置ConstraintMutant参数名
			if(!constraintList.isEmpty()){
				for (Constraint constraint : constraintList) {
					Constraint c = new Constraint();
					c.setConstraintName(constraint.getConstraintName());
					c.setConstraintValue(constraint.getConstraintValue());
					cList.add(c);
				}	
			}
			cm.setConstraintMutantList(cList);
			constraintMutantList.add(cm);
		}
		return constraintMutantList;
	}
	
	/**
	 * 根据条件生成constraintMutant对象，并加入到constraintMutantList中
	 * @param paraName 参数名
	 * @param constraintName List<Constraint>的Constraint的constraintName
	 * @param min constraintValue的最小值
	 * @param max constraintValue的最大值
	 */
	private void createMutantObject(String paraName,String constraintName, int min,int max) {
		ConstraintMutant cm = new ConstraintMutant();
		if(constraintMutantList==null)
			cm.setId(1);
		else
			cm.setId(constraintMutantList.size()+1);
		cm.setParaName(paraName); 						//设置ConstraintMutant参数名
		ArrayList<Constraint> cList = new ArrayList<Constraint>();	//设置ConstraintMutant约束列表
		Constraint c = new Constraint();
		c.setConstraintName(constraintName);			//设置约束体名
		c.setConstraintValue(TestDataGenerateSupport.getRandomNum(min, max)+"");//设置约束体值
		cList.add(c);
		cm.setConstraintMutantList(cList);
		constraintMutantList.add(cm);
	}
	
	/**
	 * 输出constraintMutantList
	 */
	private void printConstraintMutantList() {
		System.out.println("*********************************************printConstraintMutantList:");
		for (ConstraintMutant cm : constraintMutantList) {
			System.out.println(cm.getId()+"..."+cm.getParaName()+"...");
			for (Constraint c : cm.getConstraintList()) {
				System.out.println(c.getConstraintName()+"..."+c.getConstraintValue());
			}
		}
	}
	/**
	 * 比较两个list值是否相等，list中元素顺序不一致，也属于不一致！
	 * @param <T>
	 * @param list1 - 
	 * @param list2
	 * @return true/false
	 */
	public static <T extends Comparable<T>> boolean isNotSame(ArrayList<T> list1, ArrayList<T> list2) {
		  if(list1.size() != list2.size())
		    return true;
		  int flag = 1;
		  for(int i=0;i<list1.size();i++){
		    if(list1.get(i).equals(list2.get(i)))
		    	flag=0;
		  }
		  return flag==1?true:false;
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

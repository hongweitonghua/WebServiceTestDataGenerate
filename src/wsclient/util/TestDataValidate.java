package wsclient.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import wsclient.domain.Constraint;
import wsclient.domain.ConstraintMutant;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

public class TestDataValidate {
	//记录每条测试数据杀死变异体的情况，String为测试数据，Integer为杀死变异体的个数
	private static HashMap<String,ArrayList<Integer>> mutantKillConditionMap = new HashMap<String, ArrayList<Integer>>();
	//最终测试数据集合
	private static ArrayList<String> finalTestDataList = new ArrayList<String>();
	private static ArrayList<String> finalTestCaseList = new ArrayList<String>();
	//当前已经选出的测试数据所杀死的变异体编号集合
	private static HashSet<Integer> killedMutantNumSet = new HashSet<Integer>();
	
	/**
	 * 判断是否能够杀死约束变异体,得到判断后的mutantKillConditionMap、finalTestDataList、killedMutantNumSet集合
	 * @param constraintMutantList 约束变异体集合
	 * @param testDataList 测试数据集合
	 * @return
	 */
	public void isKilledMutant(ArrayList<ConstraintMutant> constraintMutantList,ArrayList<String> testDataList){
		String[] paraArr = {"edge_A","edge_B","edge_C"};
		int flag = 0;
		for (String testDataStr : testDataList) {	//遍历测试数据集合
			String perTestData[] = testDataStr.split(",");
			//存储测试数据 <变量名称,int值>,方便对应的变量名称 获取测试数据值，
			//例如测数据库(0,0,80)，存储形式：{< edge_A, 0 >, <edge_B,0> , <edge_C,80>}
			HashMap<String,Integer> perTestDataMap= new HashMap<String, Integer>();	
			//存储每一组测试数据对应的可杀死变异体的编号,mutantKillConditionMap中的ArrayList<Integer>
			ArrayList<Integer> mutantNumList = new ArrayList<Integer>();
			int i =0;
			//得到每一条测试数据int数组
			for (String string : perTestData) {
				perTestDataMap.put(paraArr[i], Integer.parseInt(string));
				i++;
			}
			//遍历约束变异体集合
			for (ConstraintMutant cm : constraintMutantList) {
				flag = 0;
				String paraName = cm.getParaName();
				ArrayList<Constraint> constraintList = cm.getConstraintList();
				//遍历约束变异体的每一个约束
				for (Constraint constraint : constraintList) {

					int constraintValue =  Integer.parseInt(constraint.getConstraintValue());
					String constraintName = constraint.getConstraintName();
					if("minInclusive".equals(constraintName)){
						if(!(perTestDataMap.get(paraName)>=constraintValue)){//不满足约束变异体条件，说明能杀死变异体
							flag++;
						}
					}
					if("minExclusive".equals(constraintName)){
						if(!(perTestDataMap.get(paraName)>constraintValue)){//不满足约束变异体条件，说明能杀死变异体
							flag++;
						}
					}
					if("maxInclusive".equals(constraintName)){
						if(!(perTestDataMap.get(paraName)<=constraintValue)){//不满足约束变异体条件，说明能杀死变异体
							flag++;
						}
					}
					if("maxExclusive".equals(constraintName)){
						if(!(perTestDataMap.get(paraName)<constraintValue)){//不满足约束变异体条件，说明能杀死变异体
							flag++;
						}
					}
				}//for //遍历约束变异体的每一个约束
				if(flag!=0){	//只要有一个约束条件不满足，即可以杀死变异体
					//1.此测试数据加入到finalTestDataList
					//2.将<此测试数据,此变异体编号列表>,遍历完整个约束体集合后，加入到mutantKillConditionMap
					//3.将变异体编号加入到killedMutantNumSet
					if(!finalTestDataList.contains(testDataStr))
						finalTestDataList.add(testDataStr);
					mutantNumList.add(cm.getId());
					killedMutantNumSet.add(cm.getId());
				}
			}//for //遍历约束变异体集合
			//2.将<此测试数据,此变异体编号列表>,遍历完整个约束体集合后，加入到mutantKillConditionMap
			mutantKillConditionMap.put(testDataStr, mutantNumList);
		}//for //遍历测试数据集合
		print();
		selectTestCase();	
	}
	/**
	 * 筛选测试数据
	 * 主要思想：
	 * 1.先将mutantKillConditionMap按照能够杀死变异体的个数 进行排序 得到排序后的sortMutantKillConditionMap
	 * 2.从第二个开始判断是否有异于前面已经出现的变异体的编号
			要是有，则将此测试数据加入到finalTestCase列表
			要是没有，则删掉此测试数据
	 * 3.得到最终的finalTestCaseList
	 */
	public void selectTestCase(){
		LinkedHashMap<String, ArrayList<Integer>> sortMutantKillConditionMap = getMapByValueSort(mutantKillConditionMap);
		System.out.println("排序后：");
		for (java.util.Map.Entry<String, ArrayList<Integer>> en : sortMutantKillConditionMap.entrySet()) {
			System.out.println("测试用例：("+en.getKey()+")");
			System.out.print(en.getValue());
			System.out.println();
		}
		//用于存储 可以加入最终测试数据集合 的对应约束变异体
		ArrayList<ArrayList<Integer>> mutantList = new ArrayList<ArrayList<Integer>>();
		for (java.util.Map.Entry<String, ArrayList<Integer>> en: sortMutantKillConditionMap.entrySet()) {
			if(!isContains(mutantList,en.getValue())){//若不包含
				mutantList.add(en.getValue());
				finalTestCaseList.add(en.getKey());
			}
		}
		//打印finalTestCaseList
		System.out.println("最终测试数据用例：");
		for (String  s: finalTestCaseList) {
			System.out.println("("+s+")");
		}
		System.out.println("最终测试数据用例共有："+finalTestCaseList.size());
	}
	/**
	 * 从第二个开始判断是否有异于前面已经出现的变异体的编号
	 * @return 是否包含,返回true,false
	 */ 
	private boolean isContains(ArrayList<ArrayList<Integer>> mutantList,ArrayList<Integer> list){
		for (ArrayList<Integer> perList : mutantList) {
			if(perList.containsAll(list)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Map按照value从大到小排序，返回排序后的map;此方法中的value是指测试数据多能杀死的约束变异体的集合list
	 * @param map 要排序的map
	 * @return 返回排序后的map，linkedHashMap
	 */
	private LinkedHashMap getMapByValueSort(HashMap<String,ArrayList<Integer>> map) {
		
		ArrayList<Map.Entry<String,ArrayList<Integer>>> list = new ArrayList<Map.Entry<String,ArrayList<Integer>>>();
		list.addAll(map.entrySet());
		//jdk 7sort有可能报错，  
        //加上这句话:System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");  
        //表示，使用以前版本的sort来排序 
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");  
		Collections.sort(list, new Comparator<Map.Entry<String,ArrayList<Integer>>>() {
			@Override
			public int compare(
					java.util.Map.Entry<String, ArrayList<Integer>> o1,
					java.util.Map.Entry<String, ArrayList<Integer>> o2) {
				int num = o2.getValue().size() - o1.getValue().size();
				return num == 0 ? 1 : num;
			}
			
		});
		LinkedHashMap<String, ArrayList<Integer>> newMap = new LinkedHashMap();  
		for (int i=0;i<list.size();i++) {
			newMap.put(list.get(i).getKey(), list.get(i).getValue());
		}
		return newMap;
	}
	
	public void print(){
		System.out.println("我是mutantKillConditionMap：");
		for (java.util.Map.Entry<String, ArrayList<Integer>> en : mutantKillConditionMap.entrySet()) {
			System.out.println("测试数据为:("+en.getKey()+")");
			ArrayList<Integer> killMutantList = en.getValue();
			System.out.println("可杀死的变异体编号为：");
			for (Integer integer : killMutantList) {
				System.out.print(integer+" ");
			}
			System.out.println();
		}
		System.out.println("我是finalTestDataList：");
		System.out.println("最终测试数据共有："+finalTestDataList.size());
		for (String string : finalTestDataList) {
			System.out.print("("+string+"),");
		}
		System.out.println();
		System.out.println("我是killedMutantNumSet：");
		for (Integer i : killedMutantNumSet) {
			System.out.print(i+",");
		}
		System.out.println();
	}
	
}

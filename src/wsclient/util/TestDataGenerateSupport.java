package wsclient.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import wsclient.domain.Constraint;

public class TestDataGenerateSupport {
	public static int Button = Integer.MIN_VALUE, Top = Integer.MAX_VALUE;
	/**
	 * 生成初始测试用例集合List
	 * 
	 * @param testDataList 含有所有参数的测试数据List
	 * @return 所有参数测试数据笛卡尔积后得到的测试用例集合List
	 */
	public static ArrayList generateOriginalTestCase(ArrayList<ArrayList> testDataList) {
		ArrayList groupList = new ArrayList();
		ArrayList resultList = new ArrayList();
		resultList.add("");
		Iterator itr = testDataList.iterator();
		while (itr.hasNext()) {
			groupList = (ArrayList) itr.next();
			resultList = CircleTraverse(groupList, resultList);
		}
		return resultList;
	}

	/**
	 * 生成每个参数后的测试用例结果集，与generateOriginalTestCase()配合使用
	 * 
	 * @param list1
	 *            当前参数测试数据集List
	 * @param list2
	 *            上一个参数测试数据遍历后得到的结果集List
	 * @return 遍历当前参数测试数据集List1后得到的测试用例结果集
	 */
	private static ArrayList CircleTraverse(ArrayList list1, ArrayList list2) {
		ArrayList resultList = new ArrayList();
		for (Object obj2 : list2)
			for (Object obj1 : list1) {
				if(obj1=="")
					obj1 = "\"\"";		//如果是空，则显示空引号
				if (obj2 != "") {
					resultList.add("" + obj2 + "," + obj1);
				} else {
					resultList.add("" + obj1);
				}
			}
		return resultList;
	}

	/**
	 * 生成每个参数的测试数据
	 * 
	 * @param alist
	 *            每个参数的约束列表
	 */
	public ArrayList generateTestDataSet(ArrayList<Constraint> alist) {
		
		int flag = 0;// flag标记为1，代表为范围约束；flag为2，代表为长度约束
		for (Constraint constraint : alist) {
			String name = constraint.getConstraintName();
			String value = constraint.getConstraintValue();
			if ("minInclusive".equals(name) || "minExclusive".equals(name)) {
				flag = 1;
				Button = Integer.parseInt(value);
			}
			if ("maxInclusive".equals(name) || "maxExclusive".equals(name)) {
				flag = 1;
				Top = Integer.parseInt(value);
			}
			if ("length".equals(name)) {
				flag = 2;
				Button = Top = Integer.parseInt(value);
			}
			if ("minLength".equals(name)) {
				flag = 2;
				Button = Integer.parseInt(value);
			}
			if ("maxLength".equals(name)) {
				flag = 2;
				Top = Integer.parseInt(value);
			}
		}
		if (flag == 1) {// 范围约束
			return generateTestDataByRange(Button, Top);
		} else if (flag == 2) {// 长度约束
			return generateTestDataByLength(Button, Top);
		}
		return null;
	}

	/**
	 * 范围约束，生成测试数据
	 * 
	 * @param min 范围的最小值
	 * @param max 范围的最大值
	 */
	public ArrayList generateTestDataByRange(int min, int max) {
		ArrayList list = new ArrayList();
		HashSet<Integer> set = new HashSet<>();
		if (max < min)
			return null;
		int integerMin = Integer.MIN_VALUE;
		int integerMax = Integer.MAX_VALUE;
		set.add(min - 1);
		set.add(min);
		set.add(min + 1);
		set.add(max - 1);
		set.add(max);
		set.add(max + 1);
		set.add(0);
		set.add(-1);
		set.add(getRandomNum(integerMin, min));
		set.add(getRandomNum(min, max));
		set.add(getRandomNum(min, integerMax));
		list.addAll(set);
		return list;
	}

	/**
	 * 长度约束，生成测试数据
	 * 
	 * @param min
	 *            长度范围的最小值
	 * @param max
	 *            长度范围的最大值
	 */
	private ArrayList generateTestDataByLength(int minLen, int maxLen) {
		ArrayList list = new ArrayList();
		HashSet<String> set = new HashSet<>();
		set.add("");
		set.add(null);
		if (maxLen < minLen)
			return null;
		if (minLen == maxLen) {
			set.add(generateLenString(minLen));
			list.addAll(set);
			return list;
		}
		set.add(generateLenString(minLen - 1));
		set.add(generateLenString(minLen));
		set.add(generateLenString(minLen + 1));
		set.add(generateLenString(maxLen - 1));
		set.add(generateLenString(maxLen));
		set.add(generateLenString(maxLen + 1));
		set.add(generateLenString(minLen, maxLen));
		list.addAll(set);
		return list;
	}

	/**
	 * 获得一个给定范围的随机整数
	 * 
	 * @param minNum
	 *            给定的范围最小值
	 * @param maxNum
	 *            给定的范围最大值
	 * @return 在此范围内的随机整数
	 */
	public static int getRandomNum(int minNum, int maxNum) {
		Random random = new Random();
		return (Math.abs(random.nextInt()) % (maxNum - minNum + 1)) + minNum;
	}

	/**
	 * 生成指定长度的随机字符串
	 * 
	 * @param length
	 *            指定长度
	 * @return 随机字符串
	 */
	public static String generateLenString(int length) {
		char[] cResult = new char[length];
		int i = 0;
		while (i < length) {
			i = i % length;
			int f = (int) (Math.random() * 3 % 3);
			if (f == 0)
				cResult[i] = (char) ('A' + Math.random() * 26);
			else if (f == 1)
				cResult[i] = (char) ('a' + Math.random() * 26);
			else
				cResult[i] = (char) ('0' + Math.random() * 10);
			i++;
		}
		return new String(cResult);
	}

	/**
	 * 生成指定长度范围的字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String generateLenString(int minLength, int maxLength) {
		if (minLength > maxLength)
			return "";
		int length = getRandomNum(minLength, maxLength); // 生成随机长度
		char[] cResult = new char[length]; // 存放结果
		int i = 0; // i记录当前生成字符串的长度
		while (i < length) {
			i = i % length;
			int f = (int) (Math.random() * 3 % 3);
			if (f == 0)
				cResult[i] = (char) ('A' + Math.random() * 26);
			else if (f == 1)
				cResult[i] = (char) ('a' + Math.random() * 26);
			else
				cResult[i] = (char) ('0' + Math.random() * 10);
			i++;
		}
		return new String(cResult);
	}
}

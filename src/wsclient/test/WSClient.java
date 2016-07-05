package wsclient.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wsclient.domain.OperationInfo;
import wsclient.domain.ParameterInfo;
import wsclient.domain.ServiceInfo;
import wsclient.util.ComponentBuilder;
import wsclient.util.TestDataValidate;

public class WSClient {

	public static void testWSDL4J() {
		int i = 0, j = 0;
		try {
			ComponentBuilder builder = new ComponentBuilder();
			ServiceInfo serviceInfo = new ServiceInfo();
			String relativePath = System.getProperty("user.dir");
			String wsdllocation=relativePath+"\\file\\testExample.wsdl";
			//String wsdllocation="G:\\bpel_newstart\\bpel_Project\\wsdl4jTest_1\\wsdl4j\\file\\testExample.wsdl";
			//String wsdllocation="G:\\helloworld.wsdl";
			//String wsdllocation="D:/eclips/testSoftware/test9/Test/HelloBPEL123.wsdl";
			//String wsdllocation="G:\\bpel_newstart\\bpel_Project\\wsdl4jTest_1\\wsdl4j\\file\\HelloBPEL123.wsdl";
			serviceInfo.setWsdllocation(wsdllocation);
			serviceInfo = builder.buildserviceinformation(serviceInfo);
			System.out.println("");
			Iterator iter = serviceInfo.getOperations();
			System.out.println("现在可以查看远端Web服务对象的有关情况了(对应本地Web服务类,ServiceInfo)");
			System.out.println(serviceInfo.getName() + "提供的操作有:");
			while (iter.hasNext()) {
				i++;
				OperationInfo oper = (OperationInfo) iter.next();
				System.out.println("");
				System.out.println("操作:" + i + " " + oper.getTargetMethodName());
				List inps = oper.getInparameters();
				List outps = oper.getOutparameters();
				if (inps.size() == 0) {
					System.out.println("此操作所需的输入参数为:");
					System.out.println("执行此操作不需要输入任何参数!");
				} else {
					System.out.println("此操作所需的输入参数为:");
					for (Iterator iterator1 = inps.iterator(); iterator1.hasNext();) {
						ParameterInfo element = (ParameterInfo) iterator1.next();
						
						System.out.println("参数名为:" + element.getName());
						System.out.println("参数类型为:" + element.getKind());
					}
				}
				if (outps.size() == 0) {
					System.out.println("执行此操作不返回任何参数!");
				} else {
					System.out.println("此操作的输出参数为:");
					for (Iterator iterator2 = outps.iterator(); iterator2.hasNext();) {
						ParameterInfo element = (ParameterInfo) iterator2.next();
						System.out.println("参数名:" + element.getName());
						System.out.println("类型为:" + element.getKind());
					}
				}
				System.out.println("");
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("111111111");
		WSClient.testWSDL4J();
	
	}

}

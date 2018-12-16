package com.eyoubika.model.application;

import java.util.ArrayList;
import java.util.List;

import com.eyoubika.model.domain.*;
import com.eyoubika.model.resource.template.JavaFileTpl;
import com.eyoubika.model.application.BaseCoderAL;
import com.eyoubika.util.CommonUtil;
public class ServiceCoderAL extends BaseCoderAL{
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		assignModule
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 下午4:34:47
	 *--------------------------------------------------------------------------------------*/
	public void assignModule(ModuleDomain md, String fileType){
		if (fileType ==null){
			fileType = "java";
		}
		if (fileType.equals("impl.java")){
			md.setFileDir(md.getModuleDir() +  "service/impl/");	
			md.setClassName(md.getModuleName() +"ServiceImpl");
			fileType = "java";
		} else {
			md.setFileDir(md.getModuleDir() +  "service/");	
			md.setClassName("I" + md.getModuleName() +"Service");
		}
		//md.setPackageName(md.getPackageName() + "." + "web.VO");
		md.setFileType(fileType);
	}

	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getAllDomains
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 下午9:09:30
	 *--------------------------------------------------------------------------------------*/
	public List<String> getAllDomains(ModuleDomain md){
		List<String> list = new ArrayList<String>();
		int actionSize = md.getActionList().size();
		System.out.println("actionSize " + actionSize);
		for (int i = 0; i < actionSize; i++){
			String inputParam = md.getActionList().get(i).get("input");
			String outputParam = md.getActionList().get(i).get("output");
			if (inputParam!=null){
				String[] inputParams = inputParam.split(",");
				for (int j = 0; j < inputParams.length; j++){
					if (inputParams[j].contains("Domain") && !list.contains(inputParams[j])){
						list.add(inputParams[j].trim());
					}
				}
			}
			if (outputParam!=null){
				String[] outputParams = outputParam.split(",");
				System.out.println("outputParams.length " + outputParams.length);
				for (int j = 0; j < outputParams.length; j++){
					if (outputParams[j].contains("Domain") && !list.contains(outputParams[j])){
						list.add(outputParams[j].trim());
					}
				}	
			}
			
		}
		return list;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		importFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:15
	 *--------------------------------------------------------------------------------------*/
	public String importImplFile(ModuleDomain md, String toFile, String toDir){
		String importString = "";
		String fileCustom = extractCustomString(toFile, toDir, "import");
		importString += JavaFileTpl.serviceImplImportPre ;
		importString +=  JavaFileTpl.importLine.replaceAll(JavaFileTpl.tplRegex, md.getPackageName()+".service.I" + md.getModuleName() + "Service");
		importString +=  JavaFileTpl.importLine.replaceAll(JavaFileTpl.tplRegex, md.getPackageName()+".web.VO." + md.getModuleName() + "VO");
		importString +=  JavaFileTpl.importLine.replaceAll(JavaFileTpl.tplRegex, md.getPackageName()+".application." + md.getModuleName() + "AL");
		List<String> domainList = getAllDomains(md);
		int actionSize = domainList.size();
		for (int i = 0; i < actionSize; i++){
			importString +=  JavaFileTpl.importLine.replaceAll(JavaFileTpl.tplRegex, md.getPackageName()+".domain." + domainList.get(i));		
		}
		importString += fileCustom;
		return importString;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		importFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:15
	 *--------------------------------------------------------------------------------------*/
	public String importServiceFile(ModuleDomain md, String toFile, String toDir){
		String importString = "";
		String fileCustom = extractCustomString(toFile, toDir, "import");
		importString += JavaFileTpl.serviceImplImportPre ;
		importString +=  JavaFileTpl.importLine.replaceAll(JavaFileTpl.tplRegex, md.getPackageName()+".web.VO." + md.getModuleName() + "VO");
		importString += fileCustom;
		return importString;
	}

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildServiceJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年7月14日 上午10:42:07
	 *--------------------------------------------------------------------------------------*/
	public void buildServiceJavaFile(ModuleDomain md){
		String fileContent = "";
		String methodContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		String packageName = md.getPackageName() + ".service";
		String toFile = "I" + md.getModuleName() + "Service." + md.getFileType();
		String toDir = md.getFileDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "java");
		//>5. 构造JavaDomain File的内容。
		

		fileContent += JavaFileTpl.packageLine.replaceAll(JavaFileTpl.packageName, packageName);
		fileContent += importImplFile(md, toFile, toDir);
		fileContent += JavaFileTpl.serviceClassHeader.replace(JavaFileTpl.moduleName, md.getModuleName());
		

		/*int listSize = md.getVoList().size();
		for (int i = 0; i < listSize; i++){
			String attrType = md.getVoList().get(i).get("attrType");
			String attrName = md.getVoList().get(i).get("attrName");
			System.out.println("3");
			methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
			methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
			
		}*/
	
		fileContent += methodContent ;
		String voType = md.getModuleName() + "VO";
		String voObj = lowerFirst(voType);
		
		int actionSize = md.getActionList().size();
		String domainName = "";
		String retString = "";
		for (int i = 0; i < actionSize; i++){
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			
			if (md.getActionList().get(i).get("input") == null ){			
			} else {
				domainName = md.getActionList().get(i).get("input");
				//String[] inputParmas = md.getActionList().get(i).get("input").split(",");			
			}
	
			fileContent += JavaFileTpl.serviceFunctionLine.replace(JavaFileTpl.attrType, voType).replace(JavaFileTpl.attrName, voObj).replace(JavaFileTpl.actionName, md.getActionList().get(i).get("name"));				
		}

		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.classFooter;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	构造Domain Java File
	 * Method:		buildDomainJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:17
	 *--------------------------------------------------------------------------------------*/
	public void buildServiceImplJavaFile(ModuleDomain md){
		String fileContent = "";
		String methodContent = "";
		//>1. 构造Java File Model
		assignModule(md,  "impl.java");
		//>2. 如果目录不存在建立目录
		buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		String packageName = md.getPackageName() + ".service.impl";
		String toFile = md.getModuleName() + "ServiceImpl." + md.getFileType();
		String toDir = md.getFileDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "java");
		//>5. 构造JavaDomain File的内容。
		

		fileContent += JavaFileTpl.packageLine.replaceAll(JavaFileTpl.packageName, packageName);
		fileContent += importImplFile(md, toFile, toDir);
		fileContent += JavaFileTpl.serviceImplClassHeader.replace(JavaFileTpl.moduleName, md.getModuleName());
		

		/*int listSize = md.getVoList().size();
		for (int i = 0; i < listSize; i++){
			String attrType = md.getVoList().get(i).get("attrType");
			String attrName = md.getVoList().get(i).get("attrName");
			System.out.println("3");
			methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
			methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
			
		}*/
		String attrType = md.getModuleName()+"AL";
		String attrName =  lowerFirst(attrType);
				
		fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.attrNote, "");
		List<String> domainList = getAllDomains(md);
		int domainSize = domainList.size();
		
		for (int i = 0; i < domainSize; i++){
			String domainType = domainList.get(i);
			String domainName = lowerFirst(domainType);
			fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, domainType).replace(JavaFileTpl.attrName, domainName).replace(JavaFileTpl.attrNote, "");
			methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, domainType).replace(JavaFileTpl.attrName, domainName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(domainName));
			methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, domainType).replace(JavaFileTpl.attrName, domainName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(domainName));
		}		
		
		methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
		methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
		fileContent += methodContent ;
		String voType = md.getModuleName() + "VO";
		String voObj = lowerFirst(voType);
		String resString = "";
		int actionSize = md.getActionList().size();
		String domainName = "";
		String retString = "";
		String equalString = "";
		String retValue = "";
		for (int i = 0; i < actionSize; i++){
			System.out.println("8");
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			
			if (md.getActionList().get(i).get("input") == null ){	
				domainName = "";
			} else {
				domainName = lowerFirst(md.getActionList().get(i).get("input"));
				//String[] inputParmas = md.getActionList().get(i).get("input").split(",");			
			}
			if (md.getActionList().get(i).get("output") == null){
				retString = "";
				equalString = "";
				retValue = "";
			} else {
				retString = md.getActionList().get(i).get("output");
				equalString = "=";
				retValue = "resDomain";
			}
			fileContent += JavaFileTpl.serviceImplFunctionLine.replace(JavaFileTpl.attrType, voType).replace(JavaFileTpl.attrName, voObj).replace(JavaFileTpl.moduleName, lowerFirst(md.getModuleName())).replace(JavaFileTpl.actionName, md.getActionList().get(i).get("name")).replace(JavaFileTpl.domainName, domainName);				
			fileContent = fileContent.replace(JavaFileTpl.outputParam, retString).replace(JavaFileTpl.equal, equalString).replace(JavaFileTpl.outputValue, retValue);
		}
		System.out.println("9");
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.classFooter;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
}

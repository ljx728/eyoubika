package com.eyoubika.model.application;

import java.util.ArrayList;
import java.util.List;

import com.eyoubika.model.domain.*;
import com.eyoubika.model.resource.template.JavaFileTpl;
import com.eyoubika.model.application.BaseCoderAL;
import com.eyoubika.util.CommonUtil;
public class ApplicationCoderAL extends BaseCoderAL{
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		assignModule
	 * Author:		1.0 created by lijiaxuan at 2015年7月14日 上午11:11:30
	 *--------------------------------------------------------------------------------------*/
	public void assignModule(ModuleDomain md, String fileType){
		if (fileType ==null){
			fileType = "java";
		}
		//md.setPackageName(md.getPackageName() + "." + "web.VO");
		md.setFileDir(md.getModuleDir() +  "application/");		
		md.setClassName(md.getModuleName() +"AL");
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
	public String importJavaFile(ModuleDomain md, String toFile, String toDir){
		String importString = "";
		String fileCustom = extractCustomString(toFile, toDir, "import");
		importString += JavaFileTpl.alImportPre ;
		List<String> domainList = getAllDomains(md);
		int actionSize = domainList.size();
		for (int i = 0; i < actionSize; i++){
			importString +=  JavaFileTpl.importLine.replaceAll(JavaFileTpl.tplRegex, md.getPackageName()+".domain." + domainList.get(i));	
			importString +=  JavaFileTpl.importLine.replaceAll(JavaFileTpl.tplRegex, md.getPackageName()+".dao." + domainList.get(i).replace("Domain", "Dao"));		
		}
		importString += fileCustom;
		return importString;
	}

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildServiceJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年7月14日 上午10:42:07
	 *--------------------------------------------------------------------------------------*/
	public void buildApplicationJavaFile(ModuleDomain md){
		String fileContent = "";
		String methodContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		String packageName = md.getPackageName() + ".application";
		String toFile =  md.getModuleName() + "AL." + md.getFileType();
		String toDir = md.getFileDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "java");
		//>5. 构造JavaDomain File的内容。
		

		fileContent += JavaFileTpl.packageLine.replaceAll(JavaFileTpl.packageName, packageName);
		fileContent += importJavaFile(md, toFile, toDir);
		fileContent += JavaFileTpl.alClassHeader.replace(JavaFileTpl.moduleName, md.getModuleName());
		List<String> domainList = getAllDomains(md);
		int domainSize = domainList.size();
		
		for (int i = 0; i < domainSize; i++){
			String attrType = domainList.get(i).replace("Domain", "Dao");
			String attrName = lowerFirst(attrType);
			fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.attrNote, "");
			methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
			methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
		}	
		fileContent += methodContent ;
		fileContent += buildFunctionContent(md);	
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.classFooter;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
	
	public String buildFunctionContent(ModuleDomain md){
		String res = "";
		
		
		int actionSize = md.getActionList().size();
		for (int i = 0; i < actionSize; i++){
			String domainType = "";
			String domainName ="";
			String retType= "";
			String retString = "";
			String actionName = md.getActionList().get(i).get("name");
			if (md.getActionList().get(i).get("input") == null ){	
				
			} else {
				domainType = md.getActionList().get(i).get("input");
				domainName = lowerFirst(domainType);
				//String[] inputParmas = md.getActionList().get(i).get("input").split(",");			
			}
			if (md.getActionList().get(i).get("output") == null){
				retType= "void"; 
				retString = "\n";
			} else {
				retType =  md.getActionList().get(i).get("output");
				retString += retType + " " + lowerFirst(retType) + " = new " + retType + ";\n";
				retString = "return " + lowerFirst(retType) + ";\n" ;
			}
			res += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			res +=  JavaFileTpl.alFunctionLine.replace(JavaFileTpl.outputParam, retType).replace(JavaFileTpl.actionName, actionName).replace(JavaFileTpl.inputParam, domainType).replace(JavaFileTpl.inputValue, domainName);
			res = res.replace(JavaFileTpl.functionContent, retString);	
		}
		return res;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildLocalBeansXmlFile
	 * Author:		1.0 created by lijiaxuan at 2015年11月24日 上午10:50:36
	 *--------------------------------------------------------------------------------------*/
	public void buildLocalBeansXmlFile(ModuleDomain md){
		String fileContent = "";
		//String toStringContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		//buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		//String packageName = md.getPackageName() + ".action";
		String toFileStr = md.getModuleDir() + md.getNamespace() + "Context.xml" + "\n";
		String toFile =  "res.xml";
		String toDir = CommonUtil.MODEL_RES_DIR;
		fileContent += "<!--3.> to: " + toFileStr + "-->\n";
		
		
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "xml");
		//>5. 构造JavaDomain File的内容。
		//fileContent += JavaFileTpl.beansXmlHeader;
		String attrVOName = md.getModuleName() + "VO";
		fileContent += JavaFileTpl.beanXmlTagHeader.replace(JavaFileTpl.attrName,lowerFirst(attrVOName)).replace(JavaFileTpl.packageName, md.getPackageName() + ".web.VO." + attrVOName);	
		fileContent +=  JavaFileTpl.beanXmlTagEnd;
		
		List<String> domainList = getAllDomains(md);
		int domainSize = domainList.size();
		for (int i = 0; i < domainSize; i++){		
			fileContent += JavaFileTpl.beanXmlTagHeader.replace(JavaFileTpl.attrName,lowerFirst(domainList.get(i))).replace(JavaFileTpl.packageName, md.getPackageName() + ".domain." + domainList.get(i));	
			fileContent +=  JavaFileTpl.beanXmlTagEnd;
		}
		String attrALName = md.getModuleName() +"AL";
		fileContent += JavaFileTpl.beanXmlTagHeader.replace(JavaFileTpl.attrName,lowerFirst(attrALName)).replace(JavaFileTpl.packageName, md.getPackageName() + ".application." + attrALName);	
		/*for (int i = 0; i < domainSize; i++){		
			fileContent += JavaFileTpl.beanPropertyLine.replace(JavaFileTpl.attrName,lowerFirst(domainList.get(i)));	
		}*/
		fileContent += JavaFileTpl.beanXmlTagEnd;
		
		String attrServiceName = md.getModuleName() +"Service";
		fileContent += JavaFileTpl.beanXmlTagHeader.replace(JavaFileTpl.attrName,lowerFirst(attrServiceName)).replace(JavaFileTpl.packageName, md.getPackageName() + ".service.impl."+md.getModuleName()+"ServiceImpl");	
		for (int i = 0; i < domainSize; i++){		
			fileContent += JavaFileTpl.beanPropertyLine.replace(JavaFileTpl.attrName,lowerFirst(domainList.get(i)));	
		}
		fileContent += JavaFileTpl.beanPropertyLine.replace(JavaFileTpl.attrName, lowerFirst(attrALName));
		fileContent += JavaFileTpl.beanXmlTagEnd;
		
		
		String attrActionName = md.getModuleName() +"Action";
		fileContent += JavaFileTpl.beanXmlTagHeader.replace(JavaFileTpl.attrName,lowerFirst(attrActionName)).replace(JavaFileTpl.packageName, md.getPackageName() + ".web.action." + attrActionName);		
		fileContent += JavaFileTpl.beanPropertyLine.replace(JavaFileTpl.attrName, lowerFirst(attrVOName));
		fileContent += JavaFileTpl.beanPropertyLine.replace(JavaFileTpl.attrName, lowerFirst(attrServiceName));
		fileContent += JavaFileTpl.beanXmlTagEnd;
		
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.beansXmlTagEnd;
		//>6. 写入JavaDomain File。
		CommonUtil.appendFile(fileContent, toFile, toDir);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	构造Domain Java File
	 * Method:		buildDomainJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:17
	 *--------------------------------------------------------------------------------------*/
	public void buildGlobalBeansXmlFile(ModuleDomain md){
		String fileContent = "";
		//String toStringContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		//buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		//String packageName = md.getPackageName() + ".action";
		
		String toFileStr = CommonUtil.PROJECTSRC_DIR + "applicationContext.xml";
		String toFile =  "res.xml";
		String toDir = CommonUtil.MODEL_RES_DIR;
		fileContent += "<!--4.> to: " + toFileStr + "-->\n";
		
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "xml");
		//>5. 构造JavaDomain File的内容。
		//fileContent += JavaFileTpl.beansXmlHeader;
		fileContent += JavaFileTpl.beansXmlAdding.replace(JavaFileTpl.namespace, md.getNamespace());
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.beansXmlTagEnd;
		//>6. 写入JavaDomain File。
		CommonUtil.appendFile(fileContent, toFile, toDir);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildStrutsXmlFile
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 下午8:36:47
	 *--------------------------------------------------------------------------------------*/
	public void buildBeansXmlFile(ModuleDomain md){
		 buildLocalBeansXmlFile( md);
		buildGlobalBeansXmlFile( md);
	}
	
	/*public void buildLocalBeansXmlFile(ModuleDomain md){
		String fileContent = "";
		//String toStringContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		//buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		//String packageName = md.getPackageName() + ".action";
		String toFile = md.getNamespace() + "Context.xml";
		String toDir = md.getModuleDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "xml");
		//>5. 构造JavaDomain File的内容。
		fileContent += JavaFileTpl.beansXmlHeader;
		String attrVOName = md.getModuleName() + "VO";
		fileContent += JavaFileTpl.beanXmlTagHeader.replace(JavaFileTpl.attrName,lowerFirst(attrVOName)).replace(JavaFileTpl.packageName, md.getPackageName() + ".web.VO." + attrVOName);	
		fileContent +=  JavaFileTpl.beanXmlTagEnd;
		
		List<String> domainList = getAllDomains(md);
		int domainSize = domainList.size();
		for (int i = 0; i < domainSize; i++){		
			fileContent += JavaFileTpl.beanXmlTagHeader.replace(JavaFileTpl.attrName,lowerFirst(domainList.get(i))).replace(JavaFileTpl.packageName, md.getPackageName() + ".domain." + domainList.get(i));	
			fileContent +=  JavaFileTpl.beanXmlTagEnd;
		}
		String attrALName = md.getModuleName() +"AL";
		fileContent += JavaFileTpl.beanXmlTagHeader.replace(JavaFileTpl.attrName,lowerFirst(attrALName)).replace(JavaFileTpl.packageName, md.getPackageName() + ".application." + attrALName);	
		for (int i = 0; i < domainSize; i++){		
			fileContent += JavaFileTpl.beanPropertyLine.replace(JavaFileTpl.attrName,lowerFirst(domainList.get(i)));	
		}
		fileContent += JavaFileTpl.beanXmlTagEnd;
		
		String attrServiceName = md.getModuleName() +"Service";
		fileContent += JavaFileTpl.beanXmlTagHeader.replace(JavaFileTpl.attrName,lowerFirst(attrServiceName)).replace(JavaFileTpl.packageName, md.getPackageName() + ".service.impl."+md.getModuleName()+"ServiceImpl");	
		for (int i = 0; i < domainSize; i++){		
			fileContent += JavaFileTpl.beanPropertyLine.replace(JavaFileTpl.attrName,lowerFirst(domainList.get(i)));	
		}
		fileContent += JavaFileTpl.beanPropertyLine.replace(JavaFileTpl.attrName, lowerFirst(attrALName));
		fileContent += JavaFileTpl.beanXmlTagEnd;
		
		
		String attrActionName = md.getModuleName() +"Action";
		fileContent += JavaFileTpl.beanXmlTagHeader.replace(JavaFileTpl.attrName,lowerFirst(attrActionName)).replace(JavaFileTpl.packageName, md.getPackageName() + ".web.action." + attrActionName);		
		fileContent += JavaFileTpl.beanPropertyLine.replace(JavaFileTpl.attrName, lowerFirst(attrVOName));
		fileContent += JavaFileTpl.beanPropertyLine.replace(JavaFileTpl.attrName, lowerFirst(attrServiceName));
		fileContent += JavaFileTpl.beanXmlTagEnd;
		
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.beansXmlTagEnd;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
	
	--------------------------------------------------------------------------------------*
	 * Description:	构造Domain Java File
	 * Method:		buildDomainJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:17
	 *--------------------------------------------------------------------------------------
	public void buildGlobalBeansXmlFile(ModuleDomain md){
		String fileContent = "";
		//String toStringContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		//buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		//String packageName = md.getPackageName() + ".action";
		String toFile = "applicationContext.xml";
		String toDir = CommonUtil.PROJECTSRC_DIR;
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "xml");
		//>5. 构造JavaDomain File的内容。
		fileContent += JavaFileTpl.beansXmlHeader;
		fileContent += JavaFileTpl.beansXmlAdding.replace(JavaFileTpl.namespace, md.getNamespace());
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.beansXmlTagEnd;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}*/
	
}

package com.eyoubika.model.application;

import com.eyoubika.model.domain.*;
import com.eyoubika.model.resource.template.JavaFileTpl;
import com.eyoubika.model.application.BaseCoderAL;
import com.eyoubika.util.CommonUtil;
public class ActionCoderAL extends BaseCoderAL{
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		assignModule
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 下午4:34:47
	 *--------------------------------------------------------------------------------------*/
	public void assignModule(ModuleDomain md, String fileType){
		if (fileType ==null){
			fileType = "java";
		}
		//md.setPackageName(md.getPackageName() + "." + "web.VO");
		md.setFileDir(md.getModuleDir() +  "web/action/");		
		md.setClassName(md.getModuleName() +"Action");
		md.setFileType(fileType);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		importFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:15
	 *--------------------------------------------------------------------------------------*/
	public String importFile(ModuleDomain md, String toFile, String toDir){
		String importString = "";
		String fileCustom = extractCustomString(toFile, toDir, "import");
		importString += JavaFileTpl.actionImportPre ;
		importString +=  JavaFileTpl.importLine.replaceAll(JavaFileTpl.tplRegex, md.getPackageName()+".service.I" + md.getModuleName() + "Service");
		importString +=  JavaFileTpl.importLine.replaceAll(JavaFileTpl.tplRegex, md.getPackageName()+".web.VO." + md.getModuleName() + "VO");
		importString += fileCustom;
		return importString;
	}


	/*--------------------------------------------------------------------------------------*
	 * Description:	构造Domain Java File
	 * Method:		buildDomainJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:17
	 *--------------------------------------------------------------------------------------*/
	public void buildActionJavaFile(ModuleDomain md){
		String fileContent = "";
		//String toStringContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		String packageName = md.getPackageName() + ".web.action";
		String toFile = md.getModuleName() + "Action." + md.getFileType();
		String toDir = md.getFileDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "java");
		//>5. 构造JavaDomain File的内容。
		fileContent += JavaFileTpl.packageLine.replaceAll(JavaFileTpl.packageName, packageName);
		fileContent += importFile(md, toFile, toDir);
		fileContent += JavaFileTpl.javaClassNote.replace(JavaFileTpl.now, CommonUtil.getNow());
		fileContent += JavaFileTpl.actionClassHeader.replaceAll(JavaFileTpl.className, md.getClassName());
		
		String voType = md.getModuleName() + "VO";
		String voObj = lowerFirst(voType);
		String serviceType = "I" + md.getModuleName() + upperFirst("service");
		String serviceObj = lowerFirst(md.getModuleName()) + upperFirst("service");
		fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, serviceType).replace(JavaFileTpl.attrName, serviceObj);
		fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, voType).replace(JavaFileTpl.attrName, voObj);
		fileContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, serviceType).replace(JavaFileTpl.attrName, serviceObj).replace(JavaFileTpl.firstCapitalAttrName, md.getModuleName()+ upperFirst("service") );
		fileContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, serviceType).replace(JavaFileTpl.attrName, serviceObj).replace(JavaFileTpl.firstCapitalAttrName, md.getModuleName()+ upperFirst("service")) ;
		fileContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, voType).replace(JavaFileTpl.attrName, voObj).replace(JavaFileTpl.firstCapitalAttrName, md.getModuleName()+ upperFirst("VO") );
		fileContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, voType).replace(JavaFileTpl.attrName, voObj).replace(JavaFileTpl.firstCapitalAttrName, md.getModuleName()+ upperFirst("VO")) ;
		int actionSize = md.getActionList().size();
		for (int i = 0; i < actionSize; i++){
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			fileContent += JavaFileTpl.actionFunctionLine.replace(JavaFileTpl.attrName, voObj).replace(JavaFileTpl.callName, serviceObj).replace(JavaFileTpl.actionName, md.getActionList().get(i).get("name"));		
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
	public void buildLocalStrutsXmlFile(ModuleDomain md){
		String fileContent = "";
		//String toStringContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		//buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		//String packageName = md.getPackageName() + ".action";
		String toFileStr =  md.getModuleDir() + md.getNamespace() + "Struts.xml" + "\n";
		String toFile =  "res.xml";
		String toDir = CommonUtil.MODEL_RES_DIR;
		CommonUtil.toFile("", toFile, toDir);
		fileContent += "<!--1.> to: " + toFileStr + "-->\n";
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "xml");
		//>5. 构造JavaDomain File的内容。
		//fileContent += JavaFileTpl.strutsXmlHeader;
		fileContent += JavaFileTpl.strutsXmlPre.replace(JavaFileTpl.packageName, md.getPackageName()).replace(JavaFileTpl.namespace, md.getNamespace());
		int actionSize = md.getActionList().size();
		for (int i = 0; i < actionSize; i++){
			
			fileContent += JavaFileTpl.strutsXmlLine.replace(JavaFileTpl.moduleName, lowerFirst(md.getModuleName())).replace(JavaFileTpl.actionName, md.getActionList().get(i).get("name"));		
		}
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.strutsXmlFooter;
		//>6. 写入JavaDomain File。
		CommonUtil.appendFile(fileContent, toFile, toDir);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	构造Domain Java File
	 * Method:		buildDomainJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:17
	 *--------------------------------------------------------------------------------------*/
	public void buildGlobalStrutsXmlFile(ModuleDomain md){
		String fileContent = "";
		//String toStringContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		//buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		//String packageName = md.getPackageName() + ".action";
		
		String toFileStr =  CommonUtil.PROJECTSRC_DIR + "struts.xml" + "\n";
		String toFile =  "res.xml";
		String toDir = CommonUtil.MODEL_RES_DIR;
		fileContent += "<!--2.> to: " + toFileStr + "-->\n";
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "xml");
		//>5. 构造JavaDomain File的内容。
		//fileContent += JavaFileTpl.strutsXmlHeader;
		fileContent += JavaFileTpl.strutsXmlTagBeg;
		fileContent += JavaFileTpl.strutsXmlAdding.replace(JavaFileTpl.namespace, md.getNamespace());
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.strutsXmlTagEnd;
		//>6. 写入JavaDomain File。
		CommonUtil.appendFile(fileContent, toFile, toDir);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildStrutsXmlFile
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 下午8:36:47
	 *--------------------------------------------------------------------------------------*/
	public void buildStrutsXmlFile(ModuleDomain md){
		 buildLocalStrutsXmlFile( md);
		buildGlobalStrutsXmlFile( md);
	}
	
	/*public void buildGlobalStrutsXmlFile(ModuleDomain md){
		String fileContent = "";
		//String toStringContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		//buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		//String packageName = md.getPackageName() + ".action";
		String toFile = "struts.xml";
		String toDir = CommonUtil.PROJECTSRC_DIR;
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "xml");
		//>5. 构造JavaDomain File的内容。
		fileContent += JavaFileTpl.strutsXmlHeader;
		fileContent += JavaFileTpl.strutsXmlTagBeg;
		fileContent += JavaFileTpl.strutsXmlAdding.replace(JavaFileTpl.namespace, md.getNamespace());
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.strutsXmlTagEnd;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
	
	public void buildLocalStrutsXmlFile(ModuleDomain md){
		String fileContent = "";
		//String toStringContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		//buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		//String packageName = md.getPackageName() + ".action";
		String toFile = md.getNamespace() + "Struts.xml";
		String toDir = md.getModuleDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "xml");
		//>5. 构造JavaDomain File的内容。
		fileContent += JavaFileTpl.strutsXmlHeader;
		fileContent += JavaFileTpl.strutsXmlPre.replace(JavaFileTpl.packageName, md.getPackageName()).replace(JavaFileTpl.namespace, md.getNamespace());
		int actionSize = md.getActionList().size();
		for (int i = 0; i < actionSize; i++){
			
			fileContent += JavaFileTpl.strutsXmlLine.replace(JavaFileTpl.moduleName, lowerFirst(md.getModuleName())).replace(JavaFileTpl.actionName, md.getActionList().get(i).get("name"));		
		}
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.strutsXmlFooter;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}*/
	
	
}

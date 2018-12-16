package com.eyoubika.model.application;

import java.util.Iterator;
import java.util.Map;

import com.eyoubika.model.domain.*;
import com.eyoubika.model.resource.template.JavaFileTpl;
import com.eyoubika.model.application.BaseCoderAL;
import com.eyoubika.util.CommonUtil;
public class POJOCoderAL extends BaseCoderAL{
	/*--------------------------------------------------------------------------------------*
	 * Description:	构造Java File Model
	 * Method:		assignJFModel
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:12
	 *--------------------------------------------------------------------------------------*/
	public void assignJFModel(JavaFileModelDomain jfm, String layerType, String fileType){
		if (fileType ==null){
			fileType = "java";
		}
		//jfm.setPackageName(jfm.getPackageName() + "." + layerType);
		jfm.setFileDir(jfm.getModuleDir() +  layerType + "/");
		if (layerType.equals("application")){
			jfm.setClassName(jfm.getModuleName() + "AL");
		} else {
			jfm.setClassName(jfm.getModuleName() + upperFirst(layerType));
		}
		
		jfm.setLayerType(layerType);
		jfm.setFileType(fileType);
	}
	
	
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
		md.setFileDir(md.getModuleDir() +  "web/VO/");		
		md.setClassName(md.getModuleName() +"VO");
		md.setFileType(fileType);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		importFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:15
	 *--------------------------------------------------------------------------------------*/
	public String importFile(JavaFileModelDomain jfm, String toFile, String toDir){
		String importString = "";
		String importPre = "";
		int listSize = jfm.getAttributeList().size();
		String importTml = "";
		
		if (jfm.getLayerType() == "dao"){
			importPre = JavaFileTpl.daoImportPre.replace(JavaFileTpl.domainPath, jfm.getPackageName() + ".domain." + jfm.getModuleName() + "Domain");
		} else {
			
		}
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "import");
		importString += importPre ;
		importTml = JavaFileTpl.importLine;
		for (int i = 0; i < listSize; i++){
			String attrType = jfm.getAttributeList().get(i).get("attrType");
			if (attrType.contains("Map")){
				if (!importString.contains("java.util.Map")){
					importString += importTml.replaceAll(JavaFileTpl.tplRegex, "java.util.Map");
				}
			}
			if (attrType.contains("List")){
				if (!importString.contains("java.util.List")){
					importString += importTml.replaceAll(JavaFileTpl.tplRegex, "java.util.List");
				}
			}
		}
		importString += fileCustom;
		return importString;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		importFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:15
	 *--------------------------------------------------------------------------------------*/
	public String importFile(ModuleDomain md, String toFile, String toDir){
		String importString = "";
		String importPre = "";
		int listSize = md.getVoList().size();
		String importTml = "";
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "import");
		importString += importPre ;
		importTml = JavaFileTpl.importLine;
		/*for (int i = 0; i < listSize; i++){
			String attrType = md.getVoList().get(i).get("attrType");
			if (attrType.contains("Map")){
				if (!importString.contains("java.util.Map")){
					importString += importTml.replaceAll(JavaFileTpl.tplRegex, "java.util.Map");
				}
			}
			if (attrType.contains("List")){
				if (!importString.contains("java.util.List")){
					importString += importTml.replaceAll(JavaFileTpl.tplRegex, "java.util.List");
				}
			}
		}*/
		importString += fileCustom;
		return importString;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	构造Domain Java File
	 * Method:		buildDomainJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:17
	 *--------------------------------------------------------------------------------------*/
	public void buildDomainJavaFile(JavaFileModelDomain jfm){
		String fileContent = "";
		String toStringContent = "";
		String initContent = "";
		String methodContent = "";
		//>1. 构造Java File Model
		assignJFModel(jfm, "domain", null);
		//>2. 如果目录不存在建立目录
		buildDirectory(jfm);	
		//>3. 从JFM中读取文件配置
		String packageName = jfm.getPackageName() + ".domain";
		String toFile = jfm.getModuleName() + "Domain." + jfm.getFileType();
		String toDir = jfm.getFileDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "java");
		//>5. 构造JavaDomain File的内容。
		fileContent += JavaFileTpl.packageLine.replaceAll(JavaFileTpl.packageName, packageName);
		fileContent += importFile(jfm, toFile, toDir);
		fileContent += JavaFileTpl.baisicClassHeader.replaceAll(JavaFileTpl.tplRegex, jfm.getClassName());
		toStringContent += JavaFileTpl.toStringHeader;
		initContent += JavaFileTpl.initHeader;
		int listSize = jfm.getAttributeList().size();
		for (int i = 0; i < listSize; i++){
			String attrType = jfm.getAttributeList().get(i).get("attrType");
			String attrName = jfm.getAttributeList().get(i).get("attrName");
			String attrNote = jfm.getAttributeList().get(i).get("attrNote");
			fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.attrNote, attrNote);
			methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
			methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
			toStringContent += JavaFileTpl.toStringLine.replace(JavaFileTpl.attrNote, attrNote).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
			initContent += JavaFileTpl.initLine.replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
		}
		toStringContent += JavaFileTpl.toStringFooter;		
		initContent += JavaFileTpl.functionFooter;
		fileContent += methodContent + toStringContent + initContent;

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
	public void buildVOJavaFile(ModuleDomain md){
		String fileContent = "";
		String toStringContent = "";
		String initContent = "";
		String methodContent = "";
		//>1. 构造Java File Model
		assignModule(md,  null);
		//>2. 如果目录不存在建立目录
		buildDirectory(md);	
		//>3. 从JFM中读取文件配置
		String packageName = md.getPackageName() + ".web.VO";
		String toFile = md.getModuleName() + "VO." + md.getFileType();
		String toDir = md.getFileDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "java");
		//>5. 构造JavaDomain File的内容。
		fileContent += JavaFileTpl.packageLine.replaceAll(JavaFileTpl.packageName, packageName);
		fileContent += importFile(md, toFile, toDir);
		fileContent += JavaFileTpl.baisicClassHeader.replaceAll(JavaFileTpl.tplRegex, md.getClassName());
		toStringContent += JavaFileTpl.toStringHeader;
		initContent += JavaFileTpl.initHeader;
		fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "userId").replace(JavaFileTpl.attrNote, "用户标识");
		fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "token").replace(JavaFileTpl.attrNote, "令牌");
		fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "msg").replace(JavaFileTpl.attrNote, "消息");
		fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "ret").replace(JavaFileTpl.attrNote, "返回码");
		int listSize = md.getVoList().size();
		for (int i = 0; i < listSize; i++){
			//int valSize = md.getVoList().get(i).size();
			Iterator iter = md.getVoList().get(i).entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				//Object key = entry.getKey();
				System.out.println("entry.getValue() " + entry.getValue().toString());
				if (entry.getValue() instanceof String){
					continue;
				}
				Map<String,String> val =  (Map<String,String>) entry.getValue();
				//Iterator valIter = val.entrySet().iterator();
				String attrType = val.get("attrType");
				String attrName = val.get("attrName");
				String attrNote = val.get("attrNote");
				fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.attrNote, attrNote);
				methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
				methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, attrType).replace(JavaFileTpl.attrName, attrName).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
				toStringContent += JavaFileTpl.toStringLine.replace(JavaFileTpl.attrNote, attrNote).replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
				initContent += JavaFileTpl.initLine.replace(JavaFileTpl.firstCapitalAttrName, upperFirst(attrName));
			}		
		}
		methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "ret").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("ret"));
		methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "ret").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("ret"));
		methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "token").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("token"));
		methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "token").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("token"));
		methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "msg").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("msg"));
		methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "msg").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("msg"));
		methodContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "userId").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("userId"));
		methodContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, "String").replace(JavaFileTpl.attrName, "userId").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("userId"));
		toStringContent += JavaFileTpl.toStringLine.replace(JavaFileTpl.attrNote, "用户标识").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("userId"));
		toStringContent += JavaFileTpl.toStringLine.replace(JavaFileTpl.attrNote, "令牌").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("token"));
		toStringContent += JavaFileTpl.toStringLine.replace(JavaFileTpl.attrNote, "返回码").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("ret"));
		toStringContent += JavaFileTpl.toStringLine.replace(JavaFileTpl.attrNote, "消息").replace(JavaFileTpl.firstCapitalAttrName, upperFirst("msg"));
		toStringContent += JavaFileTpl.toStringFooter;
		initContent += JavaFileTpl.initLine.replace(JavaFileTpl.firstCapitalAttrName, upperFirst("userId"));
		initContent += JavaFileTpl.initLine.replace(JavaFileTpl.firstCapitalAttrName, upperFirst("token"));
		initContent += JavaFileTpl.initLine.replace(JavaFileTpl.firstCapitalAttrName, upperFirst("ret"));
		initContent += JavaFileTpl.initLine.replace(JavaFileTpl.firstCapitalAttrName, upperFirst("msg"));
		initContent += JavaFileTpl.functionFooter;
		fileContent += methodContent + toStringContent + initContent;
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.classFooter;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
}

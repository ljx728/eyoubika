package com.eyoubika.model.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.model.domain.*;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.model.application.*;
public class AutoCoderAL extends BaseCoderAL{
	DaoCoderAL daoCoderAL;
	POJOCoderAL pOJOCoderAL;
	SQLCoderAL sQLCoderAL;
	ActionCoderAL actionCoderAL;
	ServiceCoderAL serviceCoderAL;
	ApplicationCoderAL applicationCoderAL;
	
	public ActionCoderAL getActionCoderAL() {
		return actionCoderAL;
	}

	public void setActionCoderAL(ActionCoderAL actionCoderAL) {
		this.actionCoderAL = actionCoderAL;
	}

	public ServiceCoderAL getServiceCoderAL() {
		return serviceCoderAL;
	}

	public void setServiceCoderAL(ServiceCoderAL serviceCoderAL) {
		this.serviceCoderAL = serviceCoderAL;
	}

	public ApplicationCoderAL getApplicationCoderAL() {
		return applicationCoderAL;
	}

	public void setApplicationCoderAL(ApplicationCoderAL applicationCoderAL) {
		this.applicationCoderAL = applicationCoderAL;
	}

	public DaoCoderAL getDaoCoderAL() {
		return daoCoderAL;
	}

	public void setDaoCoderAL(DaoCoderAL daoCoderAL) {
		this.daoCoderAL = daoCoderAL;
	}

	public POJOCoderAL getpOJOCoderAL() {
		return pOJOCoderAL;
	}

	public void setpOJOCoderAL(POJOCoderAL pOJOCoderAL) {
		this.pOJOCoderAL = pOJOCoderAL;
	}

	public SQLCoderAL getsQLCoderAL() {
		return sQLCoderAL;
	}

	public void setsQLCoderAL(SQLCoderAL sQLCoderAL) {
		this.sQLCoderAL = sQLCoderAL;
	}

	public  void buildDDSJavaFile(String moduleName, String modelName){
		//DaoCoderAL daoCoderAL = new DaoCoderAL();
		//POJOCoderAL pOJOCoderAL = new POJOCoderAL();
		//SQLCoderAL sQLCoderAL = new SQLCoderAL();
		if (modelName!=null){
			JavaFileModelDomain javaFileModel = getModel(modelName);	
			pOJOCoderAL.buildDomainJavaFile(javaFileModel);
			pOJOCoderAL.buildDomainJavaFile(javaFileModel);
			daoCoderAL.buildDaoJavaFile(javaFileModel);
			daoCoderAL.buildDaoXmlFile(javaFileModel);
			sQLCoderAL.buildSqlFile(javaFileModel);
		}
		if (moduleName!=null){

			ModuleDomain moduleDomain = getModule(moduleName);
			pOJOCoderAL.buildVOJavaFile(moduleDomain);
			actionCoderAL.buildActionJavaFile(moduleDomain);
			actionCoderAL.buildStrutsXmlFile(moduleDomain);
			serviceCoderAL.buildServiceImplJavaFile(moduleDomain);
			serviceCoderAL.buildServiceJavaFile(moduleDomain);
			applicationCoderAL.buildApplicationJavaFile(moduleDomain);
			applicationCoderAL.buildBeansXmlFile(moduleDomain);
		}
		
		
		
	}
	
	public void autoCode(String moduleName, String modelName){
		buildDDSJavaFile(moduleName, modelName);
	}
		
		
		
	
		
		
		
		
		
		
		
	
	
		/*--------------------------------------------------------------------------------------*
		 * Description:	TODO
		 * Method:		buildApplicationJavaFile
		 * Author:		1.0 created by lijiaxuan at 2015年7月13日 上午10:12:51
		 *--------------------------------------------------------------------------------------*/
		/*public static void buildApplicationJavaFile(JavaFileModelDomain jfm){
			String fileContent = "";
			//>1. 构造Java File Model
			assignJFModel(jfm, "application", null);
			//>2. 如果目录不存在建立目录
			buildDirectory(jfm);
			//>3. 从JFM中读取文件配置
			String packageName = jfm.getPackageName() + ".application";
			String toFile = jfm.getModuleName() + "AL." + jfm.getFileType();
			String toDir = jfm.getFileDir();
			//>4. 获取JavaAL File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
			String fileCustom = extractCustomString(toFile, toDir, "java");
			//>5. 构造JavaAL File的内容。
			//包名package
			fileContent += JavaFileTpl.packageLine.replaceAll(JavaFileTpl.tplRegex, packageName);
			//导入import
			fileContent += importFile(jfm, toFile, toDir);
			//类注释
			fileContent += JavaFileTpl.javaClassNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			//类头
			fileContent += JavaFileTpl.daoClassHeader.replaceAll(JavaFileTpl.className, jfm.getClassName()).replaceAll(JavaFileTpl.tableName, jfm.getTableName());
			//成员变量
			String daoType = jfm.getModuleName() + upperFirst("dao");
			String daoObj = lowerFirst(daoType);
			fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, domainType).replace(JavaFileTpl.attrName, domainObj);
			fileContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, domainType).replace(JavaFileTpl.attrName, domainObj).replace(JavaFileTpl.firstCapitalAttrName, domainType);
			fileContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, domainType).replace(JavaFileTpl.attrName, domainObj).replace(JavaFileTpl.firstCapitalAttrName, domainType);
			//成员函数
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			if (jfm.isAuto()){
				//add
				fileContent += JavaFileTpl.daoAutoAddLine.replace(JavaFileTpl.moduleName, jfm.getModuleName()).replace(JavaFileTpl.attrName, domainObj).replace(JavaFileTpl.domainType, domainType);		
			} else {
				fileContent += JavaFileTpl.daoAddLine.replace(JavaFileTpl.moduleName, jfm.getModuleName()).replace(JavaFileTpl.attrName, domainObj).replace(JavaFileTpl.domainType, domainType);	
			}		
			//delete 
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			fileContent += JavaFileTpl.daoDeleteLine.replace(JavaFileTpl.moduleName, jfm.getModuleName()).replace(JavaFileTpl.primaryKey, jfm.getPrimaryKey()).replace(JavaFileTpl.attrType,  getPrimaryKeyType(jfm));
			//delete all
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			fileContent += JavaFileTpl.daoDeleteAllLine;
			//modify
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			fileContent += JavaFileTpl.daoModifyLine.replace(JavaFileTpl.firstCapitalAttrName, jfm.getModuleName()).replace(JavaFileTpl.attrName, domainObj).replace(JavaFileTpl.attrType, domainType);
			//find
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			fileContent += JavaFileTpl.daoFindLine.replace(JavaFileTpl.firstCapitalAttrName, jfm.getModuleName()).replace(JavaFileTpl.attrName, jfm.getPrimaryKey()).replace(JavaFileTpl.attrType, getPrimaryKeyType(jfm)).replace(JavaFileTpl.domainType, domainType);
			//find by domain
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			fileContent += JavaFileTpl.daoFindByDomainLine.replace(JavaFileTpl.firstCapitalAttrName, jfm.getModuleName()).replace(JavaFileTpl.attrName, domainObj).replace(JavaFileTpl.domainType, domainType);
			//query list
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			fileContent += JavaFileTpl.daoQueryListLine.replace(JavaFileTpl.moduleName, jfm.getModuleName()).replace(JavaFileTpl.attrName, domainObj).replace(JavaFileTpl.domainType, domainType);
			//isExist
			fileContent += JavaFileTpl.javaFunctionNote.replace(JavaFileTpl.now, CommonUtil.getNow());
			fileContent += JavaFileTpl.daoIsExistByDomainLine.replace(JavaFileTpl.attrName, domainObj).replace(JavaFileTpl.domainType, domainType);
			if (!jfm.getPrimaryKey().contains(",")){
				fileContent += 
			}
			//加入用户自定义内容。
			fileContent += fileCustom;
			fileContent += JavaFileTpl.classFooter;
			//>6. 写入JavaDomain File。
			CommonUtil.toFile(fileContent, toFile, toDir);
		}*/
		
		
	
}
package com.eyoubika.model.application;

import com.eyoubika.model.domain.JavaFileModelDomain;
import com.eyoubika.model.resource.template.*;
import com.eyoubika.model.application.BaseCoderAL;
import com.eyoubika.util.CommonUtil;
public class DaoCoderAL extends BaseCoderAL{
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
	 * Method:		buildDaoJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午6:25:47
	 *--------------------------------------------------------------------------------------*/
	public void buildDaoJavaFile(JavaFileModelDomain jfm){
		String fileContent = "";
		//>1. 构造Java File Model
		assignJFModel(jfm, "dao", null);
		//>2. 如果目录不存在建立目录
		buildDirectory(jfm);
		//>3. 从JFM中读取文件配置
		String packageName = jfm.getPackageName() + ".dao";
		String toFile = jfm.getModuleName() + "Dao." + jfm.getFileType();
		String toDir = jfm.getFileDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "java");
		//>5. 构造JavaDao File的内容。
		fileContent += JavaFileTpl.packageLine.replaceAll(JavaFileTpl.tplRegex, packageName);
		fileContent += importFile(jfm, toFile, toDir);
		fileContent += JavaFileTpl.javaClassNote.replace(JavaFileTpl.now, CommonUtil.getNow());
		fileContent += JavaFileTpl.daoClassHeader.replaceAll(JavaFileTpl.className, jfm.getClassName()).replaceAll(JavaFileTpl.tableName, jfm.getTableName());

		String domainType = jfm.getModuleName() + upperFirst("domain");
		String domainObj = lowerFirst(domainType);
		fileContent += JavaFileTpl.attrLine.replace(JavaFileTpl.attrType, domainType).replace(JavaFileTpl.attrName, domainObj);
		fileContent += JavaFileTpl.getMethodLine.replace(JavaFileTpl.attrType, domainType).replace(JavaFileTpl.attrName, domainObj).replace(JavaFileTpl.firstCapitalAttrName, domainType);
		fileContent += JavaFileTpl.setMethodLine.replace(JavaFileTpl.attrType, domainType).replace(JavaFileTpl.attrName, domainObj).replace(JavaFileTpl.firstCapitalAttrName, domainType);
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
		/*if (!jfm.getPrimaryKey().contains(",")){
			fileContent += 
		}*/
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += JavaFileTpl.classFooter;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildDaoFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午8:19:30
	 *--------------------------------------------------------------------------------------*/
	public void buildDaoXmlFile(JavaFileModelDomain jfm){
		String fileContent = "";
		assignJFModel(jfm, "dao", "xml");
		buildDirectory(jfm);
		String toFile = jfm.getModuleName() + "Dao." + jfm.getFileType();
		String toDir = jfm.getFileDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "xml");
		//xml Header
		fileContent += XmlFileTpl.daoHeader;	
		fileContent += XmlFileTpl.daoFileNote.replace(XmlFileTpl.now, CommonUtil.getNow());
		fileContent += XmlFileTpl.daoSqlMapBeg.replace(XmlFileTpl.tableName, jfm.getTableName());
		String domainType = jfm.getModuleName() + upperFirst("domain");
		String domainObj = lowerFirst(domainType);
		String domainPath = jfm.getPackageName() + ".domain." +jfm.getModuleName() + "Domain";
		//TypeAlias
		fileContent += XmlFileTpl.daoTypeAlias.replace(XmlFileTpl.domainType, domainType).replace(XmlFileTpl.domainPath, domainPath);
		//ResultMap
		fileContent += XmlFileTpl.daoResultMapBeg.replace(XmlFileTpl.firstCapitalAttrName, jfm.getModuleName()).replace(XmlFileTpl.domainPath, domainPath);
		int listSize = jfm.getAttributeList().size();
		String tableAbbr = upperAllAbbr(jfm.getTableName());
		String columNames = "";
		String columValues = "";
		String dynamicWhereString = XmlFileTpl.daoDynamicWhereTag;
		String dynamicSetString = XmlFileTpl.daoDynamicSetTag;
		for (int i = 0; i < listSize; i++){
			String attrType = jfm.getAttributeList().get(i).get("attrType");
			String attrName = jfm.getAttributeList().get(i).get("attrName");
			String attrNote = jfm.getAttributeList().get(i).get("attrNote");	
			String dbType = jfm.getAttributeList().get(i).get("dbType");	
			fileContent += XmlFileTpl.daoResultMapLine.replace(XmlFileTpl.tableAbbr, tableAbbr).replace(XmlFileTpl.firstCapitalAttrName, upperFirst(attrName)).replace(XmlFileTpl.attrName, attrName).replace(XmlFileTpl.attrType, toIBatisType(dbType));
			dynamicWhereString += XmlFileTpl.daoDynamicWhereLine.replace(XmlFileTpl.tableAbbr, tableAbbr).replace(XmlFileTpl.attrName, attrName).replace(XmlFileTpl.firstCapitalAttrName, upperFirst(attrName));
			if (jfm.isAuto()){
				if (attrName.equals(jfm.getPrimaryKey())){
					continue;
				}
			}
			if ( columNames.equals("") || columValues.equals("")) {
				columNames += upperAllAbbr(jfm.getTableName()) + "_" +upperFirst(attrName);
				columValues += "#" +attrName+ "#";
			} else {
				columNames +=  " ," + upperAllAbbr(jfm.getTableName()) + "_" +upperFirst(attrName);
				columValues += " ," + "#" +attrName+ "#";
			}				
			dynamicSetString += XmlFileTpl.daoDynamicSetLine.replace(XmlFileTpl.tableAbbr, tableAbbr).replace(XmlFileTpl.attrName, attrName).replace(XmlFileTpl.firstCapitalAttrName, upperFirst(attrName));	//如果包含自增，则不应当set		
		}
		
		fileContent += XmlFileTpl.daoResultMapEnd;
		//add
		if (jfm.isAuto()){
			String daoAutoAddLine = XmlFileTpl.daoAutoAddLine.replace(XmlFileTpl.moduleName, jfm.getModuleName()).replace(XmlFileTpl.domainPath, domainPath).replace(XmlFileTpl.tableName, jfm.getTableName()).replace(XmlFileTpl.primaryKey, jfm.getPrimaryKey());
			fileContent += daoAutoAddLine.replace(XmlFileTpl.columNames, columNames).replace(XmlFileTpl.columValues, columValues);
		} else {
			String daoAddLine = XmlFileTpl.daoAddLine.replace(XmlFileTpl.moduleName, jfm.getModuleName()).replace(XmlFileTpl.domainPath, domainPath).replace(XmlFileTpl.tableName, jfm.getTableName()).replace(XmlFileTpl.primaryKey, jfm.getPrimaryKey());
			fileContent += daoAddLine.replace(XmlFileTpl.columNames, columNames).replace(XmlFileTpl.columValues, columValues);
		}
		//find by PK
		fileContent += XmlFileTpl.daoFindLine.replace(XmlFileTpl.moduleName, jfm.getModuleName()).replace(XmlFileTpl.attrType, getPrimaryKeyType(jfm)).replace(XmlFileTpl.tableName, jfm.getTableName()).replace(XmlFileTpl.tableAbbr, tableAbbr).replace(XmlFileTpl.firstCapitalAttrName, upperFirst(jfm.getPrimaryKey())).replace(XmlFileTpl.primaryKey, jfm.getPrimaryKey());
		//find by domain
		fileContent += XmlFileTpl.daoSelectByDomainBeg.replace(XmlFileTpl.moduleName, jfm.getModuleName()).replace(XmlFileTpl.domainPath, domainPath).replace(XmlFileTpl.tableName, jfm.getTableName());
		fileContent += dynamicWhereString;
		fileContent += XmlFileTpl.daoDynamicTagEnd;
		fileContent += XmlFileTpl.daoSelectTagEnd;
		
		//query by domain
		fileContent += XmlFileTpl.daoSelectListBeg.replace(XmlFileTpl.moduleName, jfm.getModuleName()).replace(XmlFileTpl.domainPath, domainPath).replace(XmlFileTpl.tableName, jfm.getTableName());
		fileContent += dynamicWhereString;
		fileContent += XmlFileTpl.daoDynamicTagEnd;
		fileContent += XmlFileTpl.daoSelectTagEnd;
		
		//delete by PK
		String daoDeleteLine = XmlFileTpl.daoDeleteLine.replace(XmlFileTpl.moduleName, jfm.getModuleName()).replace(XmlFileTpl.attrType, getPrimaryKeyType(jfm)).replace(XmlFileTpl.tableName, jfm.getTableName()).replace(XmlFileTpl.primaryKey, jfm.getPrimaryKey());
		fileContent += daoDeleteLine.replace(XmlFileTpl.tableAbbr, tableAbbr).replace(XmlFileTpl.firstCapitalAttrName, upperFirst(jfm.getPrimaryKey()));
		//delete all
		fileContent += XmlFileTpl.daoDeleteAllLine.replace(XmlFileTpl.tableName, jfm.getTableName());
		//update by PK
		fileContent += XmlFileTpl.daoUpdateBeg.replace(XmlFileTpl.moduleName, jfm.getModuleName()).replace(XmlFileTpl.domainPath, domainPath).replace(XmlFileTpl.tableName, jfm.getTableName());
		fileContent += dynamicSetString;
		fileContent += XmlFileTpl.daoDynamicTagEnd;
		fileContent += XmlFileTpl.daoUpdateWhereEnd.replace(XmlFileTpl.tableAbbr, tableAbbr).replace(XmlFileTpl.firstCapitalAttrName, upperFirst(jfm.getPrimaryKey())).replace(XmlFileTpl.primaryKey, jfm.getPrimaryKey());
		//update by Domain
		/*fileContent += XmlFileTpl.daoUpdateBeg.replace(XmlFileTpl.moduleName, jfm.getModuleName()).replace(XmlFileTpl.domainPath, domainPath).replace(XmlFileTpl.tableName, jfm.getTableName());
		fileContent += dynamicSetString;
		fileContent += XmlFileTpl.daoDynamicTagEnd;
		fileContent += dynamicWhereString;
		fileContent += XmlFileTpl.daoDynamicTagEnd;
		fileContent += XmlFileTpl.daoUpdateTagEnd;*/
		
		//isExist
		fileContent += XmlFileTpl.daoIsExistBeg.replace(XmlFileTpl.tableName, jfm.getTableName()).replace(XmlFileTpl.domainPath, domainPath);
		fileContent += dynamicWhereString;
		fileContent += XmlFileTpl.daoDynamicTagEnd;
		fileContent += XmlFileTpl.daoLimitLine;
		fileContent += XmlFileTpl.daoSelectTagEnd;
		
		
		//end
		//加入用户自定义内容。
		fileContent += fileCustom;	
		fileContent += XmlFileTpl.daoSqlMapEnd;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
	
}

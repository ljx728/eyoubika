package com.eyoubika.model.application;

import com.eyoubika.model.domain.JavaFileModelDomain;
import com.eyoubika.model.resource.template.SqlFileTpl;
import com.eyoubika.model.application.BaseCoderAL;
import com.eyoubika.util.CommonUtil;
public class SQLCoderAL extends BaseCoderAL{
	/*--------------------------------------------------------------------------------------*
	 * Description:	构造Java File Model
	 * Method:		assignJFModel
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:12
	 *--------------------------------------------------------------------------------------*/
	public void assignJFModel(JavaFileModelDomain jfm, String layerType, String fileType){
		/*if (fileType ==null){
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
		jfm.setFileType(fileType);*/
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildSqlFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午8:19:30
	 *--------------------------------------------------------------------------------------*/
	public void buildSqlFile(JavaFileModelDomain jfm){
		String fileContent = "";
	
		//>1. 构造Java File Model
		//assignJFModel(jfm, "sql", null);
		//>2. 如果目录不存在建立目录
		//buildDirectory(jfm);
		
		String toFile = jfm.getTableName() + ".sql";
		String toDir = CommonUtil.PROJECTSRC_DIR + "com/eyoubika/model/resource/sql/";
		String tableAbbr = upperAllAbbr(jfm.getTableName());
		fileContent += SqlFileTpl.sqlDbLine;		
		fileContent += SqlFileTpl.sqlTbLine.replace(SqlFileTpl.tableName, jfm.getTableName());
		int listSize = jfm.getAttributeList().size();
		String nullString = "";
		
		for (int i = 0; i < listSize; i++){
			String attrType = jfm.getAttributeList().get(i).get("attrType");
			String attrName = jfm.getAttributeList().get(i).get("attrName");
			String attrNote = jfm.getAttributeList().get(i).get("attrNote");
			String attrNull = jfm.getAttributeList().get(i).get("attrNull");
			String dbType = jfm.getAttributeList().get(i).get("dbType");
			String columnLine =  SqlFileTpl.sqlTbColumnLine.replace(SqlFileTpl.tableAbbr, tableAbbr).replace(SqlFileTpl.firstCapitalAttrName, upperFirst(attrName)).replace(SqlFileTpl.attrType, dbType);
			if (attrNull.equals("true")) {
				columnLine = columnLine.replace(SqlFileTpl.nullType,  "default NULL");
			} else if (attrNull.equals("false") )
			columnLine = columnLine.replace(SqlFileTpl.nullType, "NOT NULL");
			String autoStirng = "";
			if (i == 0){
				if (jfm.isAuto()){
					fileContent += columnLine.replace(SqlFileTpl.autoFlag, "auto_increment");
				} else {
					fileContent += columnLine.replace(SqlFileTpl.autoFlag, "");
				}
			} else {
				fileContent += columnLine.replace(SqlFileTpl.autoFlag, "");
			}
			
		}
		fileContent += SqlFileTpl.sqlTbPKLine.replace(SqlFileTpl.tableAbbr, tableAbbr).replace(SqlFileTpl.primaryKey, upperFirst(jfm.getPrimaryKey()));
		fileContent += SqlFileTpl.sqlTbEndLine.replace(SqlFileTpl.tableName, jfm.getTableName());
		
		CommonUtil.toFile(fileContent, toFile, toDir);
	}

}

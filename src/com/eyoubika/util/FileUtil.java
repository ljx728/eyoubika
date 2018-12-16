package com.eyoubika.util;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.domain.*;
import com.eyoubika.model.domain.*;
import com.eyoubika.model.template.*;//FileTemplate;

public class FileUtil{
	//本项目的根目录
	
	//public static final String MODEL_DIR = APP_PATH + "eyoubika/model/";
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:07:51
	 *--------------------------------------------------------------------------------------*/
	public static void buildJavaFile(String moduleName){
		//String moduleName = "News";
		JavaFileModelDomain javaFileModel = getModel(moduleName);
		buildDomainJavaFile(javaFileModel);
		buildDaoJavaFile(javaFileModel);
		buildDaoXmlFile(javaFileModel);
		buildSqlFile(javaFileModel);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getModuleDirectory
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:07:56
	 *--------------------------------------------------------------------------------------*/
	public static String getModuleDirectory(String dirString, String type){
		String[] dirs = dirString.split("\\.");
		String dirPath = CommonUtil.APPSRC_PATH;			
		for (int i = 0; i < dirs.length; i++){
			dirPath += dirs[i] + "/";
		}
		if (type != null){
			dirPath  +=  type + "/";
		}		
		return dirPath;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildDirectory
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:07:59
	 *--------------------------------------------------------------------------------------*/
	public static String buildDirectory(JavaFileModelDomain javaFileModel){
		String dirPath = javaFileModel.getModuleDir();
		if (javaFileModel.getLayerType() != null){
			dirPath  +=  javaFileModel.getLayerType() + "/";
		}		
		File f = new File(dirPath);
        // 创建文件夹
		System.out.println("dirPath " + dirPath);
        if (!f.exists()) {
        	System.out.println("dirPath " + dirPath);
            f.mkdirs();
        }
        return dirPath;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getModel
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:08
	 *--------------------------------------------------------------------------------------*/
	public static JavaFileModelDomain getModel(String  moduleName){
		JavaFileModelDomain javaFileModel = new JavaFileModelDomain();
		javaFileModel.setModuleName(moduleName);
		String fromDir = CommonUtil.MODEL_CONFIG_DIR;
		String fromFile = moduleName + CommonUtil.MODEL_FILE;	
		String toDir = "";
		String toFile = "";
		//> 1. 读取文件内容
		String fromContent = CommonUtil.fromFile(fromFile, fromDir);
		//> 2. 解析内容到模型
		String[] lines = fromContent.split("\n");
		List<Map<String, String>> attributeList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < lines.length; i++){
			lines[i] = lines[i].trim().replaceAll("\\s+", " ");
			if (i == 0 ){		
				String[] firstLine = lines[0].split(" ");
				javaFileModel.setClassName(firstLine[0]);
				javaFileModel.setPackageName(firstLine[1]);
				if (firstLine.length > 2){
					javaFileModel.setTableName(firstLine[2]);
					javaFileModel.setPrimaryKey(firstLine[3]);
					if (firstLine.length > 4) {
						javaFileModel.setAuto(true);
					} else {
						javaFileModel.setAuto(false);
					}
				}
				javaFileModel.setModuleDir(getModuleDirectory(firstLine[1],null));
			} else {
				String attrString = lines[i];//.trim().replaceAll("\\s+", " ");
				System.out.println("stirng; " +attrString);
				String[] attr = attrString.split(" "); // 0, 1, 2
				Map<String, String> attrMap = new HashMap<String, String>();			
				attrMap.put("attrType", attr[0]);
				attrMap.put("attrName", attr[1]);
				attrMap.put("dbType", attr[2]);
				
				attrMap.put("attrNull", attr[3]);		
				attrMap.put("attrNote", attr[4]);
				attributeList.add(attrMap);		
			}			
		}
		javaFileModel.setAttributeList(attributeList);
		System.out.println("javaFileModel  " +javaFileModel.toString());
		return javaFileModel;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	构造Java File Model
	 * Method:		assignJFModel
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:12
	 *--------------------------------------------------------------------------------------*/
	public static void assignJFModel(JavaFileModelDomain jfm, String layerType, String fileType){
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
	public static String importFile(JavaFileModelDomain jfm, String toFile, String toDir){
		String importString = "";
		String importPre = "";
		int listSize = jfm.getAttributeList().size();
		String importTml = "";
		
		if (jfm.getLayerType() == "dao"){
			importPre = FileTemplate.daoImportPre.replace(FileTemplate.domainPath, jfm.getPackageName() + ".domain." + jfm.getModuleName() + "Domain");
		} else {
			
		}
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "import");
		importString += importPre ;
		importTml = FileTemplate.importLine;
		for (int i = 0; i < listSize; i++){
			String attrType = jfm.getAttributeList().get(i).get("attrType");
			if (attrType.contains("Map")){
				if (!importString.contains("java.util.Map")){
					importString += importTml.replaceAll(FileTemplate.tplRegex, "java.util.Map");
				}
			}
			if (attrType.contains("List")){
				if (!importString.contains("java.util.List")){
					importString += importTml.replaceAll(FileTemplate.tplRegex, "java.util.List");
				}
			}
		}
		importString += fileCustom;
		return importString;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	构造Domain Java File
	 * Method:		buildDomainJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:17
	 *--------------------------------------------------------------------------------------*/
	public static void buildDomainJavaFile(JavaFileModelDomain jfm){
		String fileContent = "";
		String toStringContent = "";
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
		fileContent += FileTemplate.packageLine.replaceAll(FileTemplate.packageName, packageName);
		fileContent += importFile(jfm, toFile, toDir);
		fileContent += FileTemplate.baisicClassHeader.replaceAll(FileTemplate.tplRegex, jfm.getClassName());
		toStringContent += FileTemplate.toStringHeader;
		int listSize = jfm.getAttributeList().size();
		for (int i = 0; i < listSize; i++){
			String attrType = jfm.getAttributeList().get(i).get("attrType");
			String attrName = jfm.getAttributeList().get(i).get("attrName");
			String attrNote = jfm.getAttributeList().get(i).get("attrNote");
			fileContent += FileTemplate.attrLine.replace(FileTemplate.attrType, attrType).replace(FileTemplate.attrName, attrName).replace(FileTemplate.attrNote, attrNote);
			methodContent += FileTemplate.getMethodLine.replace(FileTemplate.attrType, attrType).replace(FileTemplate.attrName, attrName).replace(FileTemplate.firstCapitalAttrName, upperFirst(attrName));
			methodContent += FileTemplate.setMethodLine.replace(FileTemplate.attrType, attrType).replace(FileTemplate.attrName, attrName).replace(FileTemplate.firstCapitalAttrName, upperFirst(attrName));
			toStringContent += FileTemplate.toStringLine.replace(FileTemplate.attrNote, attrNote).replace(FileTemplate.firstCapitalAttrName, upperFirst(attrName));
		}
		toStringContent += FileTemplate.toStringFooter;
		fileContent += methodContent + toStringContent;
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += FileTemplate.classFooter;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildDaoJavaFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午6:25:47
	 *--------------------------------------------------------------------------------------*/
	public static void buildDaoJavaFile(JavaFileModelDomain jfm){
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
		fileContent += FileTemplate.packageLine.replaceAll(FileTemplate.tplRegex, packageName);
		fileContent += importFile(jfm, toFile, toDir);
		fileContent += FileTemplate.javaClassNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoClassHeader.replaceAll(FileTemplate.className, jfm.getClassName()).replaceAll(FileTemplate.tableName, jfm.getTableName());

		String domainType = jfm.getModuleName() + upperFirst("domain");
		String domainObj = lowerFirst(domainType);
		fileContent += FileTemplate.attrLine.replace(FileTemplate.attrType, domainType).replace(FileTemplate.attrName, domainObj);
		fileContent += FileTemplate.getMethodLine.replace(FileTemplate.attrType, domainType).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.firstCapitalAttrName, domainType);
		fileContent += FileTemplate.setMethodLine.replace(FileTemplate.attrType, domainType).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.firstCapitalAttrName, domainType);
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		if (jfm.isAuto()){
			//add
			fileContent += FileTemplate.daoAutoAddLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.domainType, domainType);		
		} else {
			fileContent += FileTemplate.daoAddLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.domainType, domainType);	
		}		
		//delete 
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoDeleteLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.primaryKey, jfm.getPrimaryKey()).replace(FileTemplate.attrType,  getPrimaryKeyType(jfm));
		//delete all
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoDeleteAllLine;
		//modify
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoModifyLine.replace(FileTemplate.firstCapitalAttrName, jfm.getModuleName()).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.attrType, domainType);
		//find
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoFindLine.replace(FileTemplate.firstCapitalAttrName, jfm.getModuleName()).replace(FileTemplate.attrName, jfm.getPrimaryKey()).replace(FileTemplate.attrType, getPrimaryKeyType(jfm)).replace(FileTemplate.domainType, domainType);
		//find by domain
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoFindByDomainLine.replace(FileTemplate.firstCapitalAttrName, jfm.getModuleName()).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.domainType, domainType);
		//query list
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoQueryListLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.domainType, domainType);
		//isExist
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoIsExistByDomainLine.replace(FileTemplate.attrName, domainObj).replace(FileTemplate.domainType, domainType);
		/*if (!jfm.getPrimaryKey().contains(",")){
			fileContent += 
		}*/
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += FileTemplate.classFooter;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildDaoXmlFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午8:19:30
	 *--------------------------------------------------------------------------------------*/
	public static void buildDaoXmlFile(JavaFileModelDomain jfm){
		String fileContent = "";
		assignJFModel(jfm, "dao", "xml");
		buildDirectory(jfm);
		String toFile = jfm.getModuleName() + "Dao." + jfm.getFileType();
		String toDir = jfm.getFileDir();
		//>4. 获取JavaDomain File 中的用户自定义内容。第一次建立文件时自定义内容肯定为空。
		String fileCustom = extractCustomString(toFile, toDir, "xml");
		//xml Header
		fileContent += FileTemplate.daoXmlHeader;	
		fileContent += FileTemplate.daoXmlFileNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoXmlSqlMapBeg.replace(FileTemplate.tableName, jfm.getTableName());
		String domainType = jfm.getModuleName() + upperFirst("domain");
		String domainObj = lowerFirst(domainType);
		String domainPath = jfm.getPackageName() + ".domain." +jfm.getModuleName() + "Domain";
		//TypeAlias
		fileContent += FileTemplate.daoXmlTypeAlias.replace(FileTemplate.domainType, domainType).replace(FileTemplate.domainPath, domainPath);
		//ResultMap
		fileContent += FileTemplate.daoXmlResultMapBeg.replace(FileTemplate.firstCapitalAttrName, jfm.getModuleName()).replace(FileTemplate.domainPath, domainPath);
		int listSize = jfm.getAttributeList().size();
		String tableAbbr = upperAllAbbr(jfm.getTableName());
		String columNames = "";
		String columValues = "";
		String dynamicWhereString = FileTemplate.daoXmlDynamicWhereTag;
		String dynamicSetString = FileTemplate.daoXmlDynamicSetTag;
		for (int i = 0; i < listSize; i++){
			String attrType = jfm.getAttributeList().get(i).get("attrType");
			String attrName = jfm.getAttributeList().get(i).get("attrName");
			String attrNote = jfm.getAttributeList().get(i).get("attrNote");	
			String dbType = jfm.getAttributeList().get(i).get("dbType");	
			fileContent += FileTemplate.daoXmlResultMapLine.replace(FileTemplate.tableAbbr, tableAbbr).replace(FileTemplate.firstCapitalAttrName, upperFirst(attrName)).replace(FileTemplate.attrName, attrName).replace(FileTemplate.attrType, toIBatisType(dbType));
			dynamicWhereString += FileTemplate.daoXmlDynamicWhereLine.replace(FileTemplate.tableAbbr, tableAbbr).replace(FileTemplate.attrName, attrName).replace(FileTemplate.firstCapitalAttrName, upperFirst(attrName));
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
			dynamicSetString += FileTemplate.daoXmlDynamicSetLine.replace(FileTemplate.tableAbbr, tableAbbr).replace(FileTemplate.attrName, attrName).replace(FileTemplate.firstCapitalAttrName, upperFirst(attrName));	//如果包含自增，则不应当set		
		}
		
		fileContent += FileTemplate.daoXmlResultMapEnd;
		//add
		if (jfm.isAuto()){
			String daoXmlAutoAddLine = FileTemplate.daoXmlAutoAddLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.domainPath, domainPath).replace(FileTemplate.tableName, jfm.getTableName()).replace(FileTemplate.primaryKey, jfm.getPrimaryKey());
			fileContent += daoXmlAutoAddLine.replace(FileTemplate.columNames, columNames).replace(FileTemplate.columValues, columValues);
		} else {
			String daoXmlAddLine = FileTemplate.daoXmlAddLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.domainPath, domainPath).replace(FileTemplate.tableName, jfm.getTableName()).replace(FileTemplate.primaryKey, jfm.getPrimaryKey());
			fileContent += daoXmlAddLine.replace(FileTemplate.columNames, columNames).replace(FileTemplate.columValues, columValues);
		}
		//find by PK
		fileContent += FileTemplate.daoXmlFindLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.attrType, getPrimaryKeyType(jfm)).replace(FileTemplate.tableName, jfm.getTableName()).replace(FileTemplate.tableAbbr, tableAbbr).replace(FileTemplate.firstCapitalAttrName, upperFirst(jfm.getPrimaryKey())).replace(FileTemplate.primaryKey, jfm.getPrimaryKey());
		//find by domain
		fileContent += FileTemplate.daoXmlSelectByDomainBeg.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.domainPath, domainPath).replace(FileTemplate.tableName, jfm.getTableName());
		fileContent += dynamicWhereString;
		fileContent += FileTemplate.daoXmlDynamicTagEnd;
		fileContent += FileTemplate.daoXmlSelectTagEnd;
		
		//query by domain
		fileContent += FileTemplate.daoXmlSelectListBeg.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.domainPath, domainPath).replace(FileTemplate.tableName, jfm.getTableName());
		fileContent += dynamicWhereString;
		fileContent += FileTemplate.daoXmlDynamicTagEnd;
		fileContent += FileTemplate.daoXmlSelectTagEnd;
		
		//delete by PK
		String daoXmlDeleteLine = FileTemplate.daoXmlDeleteLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.attrType, getPrimaryKeyType(jfm)).replace(FileTemplate.tableName, jfm.getTableName()).replace(FileTemplate.primaryKey, jfm.getPrimaryKey());
		fileContent += daoXmlDeleteLine.replace(FileTemplate.tableAbbr, tableAbbr).replace(FileTemplate.firstCapitalAttrName, upperFirst(jfm.getPrimaryKey()));
		//delete all
		fileContent += FileTemplate.daoXmlDeleteAllLine.replace(FileTemplate.tableName, jfm.getTableName());
		//update by PK
		fileContent += FileTemplate.daoXmlUpdateBeg.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.domainPath, domainPath).replace(FileTemplate.tableName, jfm.getTableName());
		fileContent += dynamicSetString;
		fileContent += FileTemplate.daoXmlDynamicTagEnd;
		fileContent += FileTemplate.daoXmlUpdateWhereEnd.replace(FileTemplate.tableAbbr, tableAbbr).replace(FileTemplate.firstCapitalAttrName, upperFirst(jfm.getPrimaryKey())).replace(FileTemplate.primaryKey, jfm.getPrimaryKey());
		//update by Domain
		/*fileContent += FileTemplate.daoXmlUpdateBeg.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.domainPath, domainPath).replace(FileTemplate.tableName, jfm.getTableName());
		fileContent += dynamicSetString;
		fileContent += FileTemplate.daoXmlDynamicTagEnd;
		fileContent += dynamicWhereString;
		fileContent += FileTemplate.daoXmlDynamicTagEnd;
		fileContent += FileTemplate.daoXmlUpdateTagEnd;*/
		
		//isExist
		fileContent += FileTemplate.daoXmlIsExistBeg.replace(FileTemplate.tableName, jfm.getTableName()).replace(FileTemplate.domainPath, domainPath);
		fileContent += dynamicWhereString;
		fileContent += FileTemplate.daoXmlDynamicTagEnd;
		fileContent += FileTemplate.daoXmlLimitLine;
		fileContent += FileTemplate.daoXmlSelectTagEnd;
		
		
		//end
		//加入用户自定义内容。
		fileContent += fileCustom;	
		fileContent += FileTemplate.daoXmlSqlMapEnd;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		extractCustomString
	 * Author:		1.0 created by lijiaxuan at 2015年7月11日 上午8:31:36
	 *--------------------------------------------------------------------------------------*/
	public static String extractCustomString(String fileName, String fileDir, String type){
		System.out.println("fileName, fileDir: " + fileDir +  fileName); 
		if (!CommonUtil.isFileExists(fileName, fileDir)){
			return "";
			
		} else {
			System.out.println("true " ); 
		}
		String content = CommonUtil.fromFile(fileName, fileDir);
		String[] blocks = null;
		String res = "";
		String tag = "";
		
		if (type.equals("java")){
			tag = FileTemplate.dbSlashCustomFileTag;
		} else if (type.equals("xml")){
			tag = FileTemplate.bracketCustomFileTag;
		} else if (type.equals("import")){
			tag = FileTemplate.dbSlashCustomImportTag;
		}
		 blocks = content.split(tag);		
		if (blocks!= null && blocks.length >=2){
			if (type.equals("import")){
				res = tag + blocks[1]+ tag + "\n";
			}else{
				res = "\t" + tag + blocks[1]+ tag + "\n";
			}
		}
		
		return res;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		buildSqlFile
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午8:19:30
	 *--------------------------------------------------------------------------------------*/
	public static void buildSqlFile(JavaFileModelDomain jfm){
		String fileContent = "";
		String toFile = jfm.getTableName() + ".sql";
		String toDir = CommonUtil.APPSRC_PATH + "com/eyoubika/model/sql/";
		String tableAbbr = upperAllAbbr(jfm.getTableName());
		fileContent += FileTemplate.sqlDbLine;		
		fileContent += FileTemplate.sqlTbLine.replace(FileTemplate.tableName, jfm.getTableName());
		int listSize = jfm.getAttributeList().size();
		String nullString = "";
		
		for (int i = 0; i < listSize; i++){
			String attrType = jfm.getAttributeList().get(i).get("attrType");
			String attrName = jfm.getAttributeList().get(i).get("attrName");
			String attrNote = jfm.getAttributeList().get(i).get("attrNote");
			String attrNull = jfm.getAttributeList().get(i).get("attrNull");
			String dbType = jfm.getAttributeList().get(i).get("dbType");
			String columnLine =  FileTemplate.sqlTbColumnLine.replace(FileTemplate.tableAbbr, tableAbbr).replace(FileTemplate.firstCapitalAttrName, upperFirst(attrName)).replace(FileTemplate.attrType, dbType);
			if (attrNull.equals("true")) {
				columnLine = columnLine.replace(FileTemplate.nullType,  "default NULL");
			} else if (attrNull.equals("false") )
			columnLine = columnLine.replace(FileTemplate.nullType, "NOT NULL");
			String autoStirng = "";
			if (i == 0){
				if (jfm.isAuto()){
					fileContent += columnLine.replace(FileTemplate.autoFlag, "auto_increment");
				} else {
					fileContent += columnLine.replace(FileTemplate.autoFlag, "");
				}
			} else {
				fileContent += columnLine.replace(FileTemplate.autoFlag, "");
			}
			
		}
		fileContent += FileTemplate.sqlTbPKLine.replace(FileTemplate.tableAbbr, tableAbbr).replace(FileTemplate.primaryKey, upperFirst(jfm.getPrimaryKey()));
		fileContent += FileTemplate.sqlTbEndLine.replace(FileTemplate.tableName, jfm.getTableName());
		
		CommonUtil.toFile(fileContent, toFile, toDir);
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
		fileContent += FileTemplate.packageLine.replaceAll(FileTemplate.tplRegex, packageName);
		//导入import
		fileContent += importFile(jfm, toFile, toDir);
		//类注释
		fileContent += FileTemplate.javaClassNote.replace(FileTemplate.now, CommonUtil.getNow());
		//类头
		fileContent += FileTemplate.daoClassHeader.replaceAll(FileTemplate.className, jfm.getClassName()).replaceAll(FileTemplate.tableName, jfm.getTableName());
		//成员变量
		String daoType = jfm.getModuleName() + upperFirst("dao");
		String daoObj = lowerFirst(daoType);
		fileContent += FileTemplate.attrLine.replace(FileTemplate.attrType, domainType).replace(FileTemplate.attrName, domainObj);
		fileContent += FileTemplate.getMethodLine.replace(FileTemplate.attrType, domainType).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.firstCapitalAttrName, domainType);
		fileContent += FileTemplate.setMethodLine.replace(FileTemplate.attrType, domainType).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.firstCapitalAttrName, domainType);
		//成员函数
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		if (jfm.isAuto()){
			//add
			fileContent += FileTemplate.daoAutoAddLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.domainType, domainType);		
		} else {
			fileContent += FileTemplate.daoAddLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.domainType, domainType);	
		}		
		//delete 
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoDeleteLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.primaryKey, jfm.getPrimaryKey()).replace(FileTemplate.attrType,  getPrimaryKeyType(jfm));
		//delete all
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoDeleteAllLine;
		//modify
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoModifyLine.replace(FileTemplate.firstCapitalAttrName, jfm.getModuleName()).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.attrType, domainType);
		//find
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoFindLine.replace(FileTemplate.firstCapitalAttrName, jfm.getModuleName()).replace(FileTemplate.attrName, jfm.getPrimaryKey()).replace(FileTemplate.attrType, getPrimaryKeyType(jfm)).replace(FileTemplate.domainType, domainType);
		//find by domain
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoFindByDomainLine.replace(FileTemplate.firstCapitalAttrName, jfm.getModuleName()).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.domainType, domainType);
		//query list
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoQueryListLine.replace(FileTemplate.moduleName, jfm.getModuleName()).replace(FileTemplate.attrName, domainObj).replace(FileTemplate.domainType, domainType);
		//isExist
		fileContent += FileTemplate.javaFunctionNote.replace(FileTemplate.now, CommonUtil.getNow());
		fileContent += FileTemplate.daoIsExistByDomainLine.replace(FileTemplate.attrName, domainObj).replace(FileTemplate.domainType, domainType);
		if (!jfm.getPrimaryKey().contains(",")){
			fileContent += 
		}
		//加入用户自定义内容。
		fileContent += fileCustom;
		fileContent += FileTemplate.classFooter;
		//>6. 写入JavaDomain File。
		CommonUtil.toFile(fileContent, toFile, toDir);
	}*/
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		toIBatisType
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 上午10:11:45
	 *--------------------------------------------------------------------------------------*/
	public static String toIBatisType(String dbType){	
		if (dbType.contains("varchar")) return "VARCHAR";
		if (dbType.contains("char")) return "CHAR";
		if (dbType.equals("MediumBlob")) return "BLOB";
		if (dbType.equals("int")) return "INT";
		if (dbType.equals("float")) return "FLOAT";
		return dbType;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getPrimaryKeyType
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 上午10:11:42
	 *--------------------------------------------------------------------------------------*/
	public static String getPrimaryKeyType(JavaFileModelDomain jfm){
		int listSize = jfm.getAttributeList().size();
		String name =jfm.getPrimaryKey();
		String res = null;
		for (int i = 0; i < listSize; i++){
			if (jfm.getAttributeList().get(i).get("attrName").equals(name)){
				return  jfm.getAttributeList().get(i).get("attrType");
			} 			
		}
		return res;		
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		upperFirst
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:21
	 *--------------------------------------------------------------------------------------*/
	public static String upperFirst(String name) {
		  //name = name.substring(0, 1).toUpperCase() + name.substring(1);
		  //return  name;
		char[] cs=name.toCharArray();
		if (cs[0]>='a' && cs[0]<='z'){
			cs[0]-=32;
		}	
		return String.valueOf(cs);	        
	}
	public static String lowerFirst(String name) {
		  //name = name.substring(0, 1).toUpperCase() + name.substring(1);
		  //return  name;
		char[] cs=name.toCharArray();
		if (cs[0]>='A' && cs[0]<='Z'){
			cs[0]+=32;
		}	
		return String.valueOf(cs);	        
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		upperAllAbbr
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午3:14:27
	 *--------------------------------------------------------------------------------------*/
	public static String upperAllAbbr(String name){
		String res = "";
		char[] cs=name.toCharArray();
		int size = cs.length;
		for (int i = 0 ; i < size; i++ ){
			if (cs[i]>='A' && cs[i]<='Z'){
				res += cs[i];
			}	
		}
		return res;
	}
}
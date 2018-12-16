package com.eyoubika.model.resource.template;

import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.YbkException;


public class BaseTpl{
	public static String tplRegex = "<<[0-9a-zA-Z]+>>";
	public static String sp = "\t";
	public static String packageName = "<<packageName>>";
	public static String namespace = "<<namespace>>";
	public static String className = "<<className>>";
	public static String attrType = "<<attrType>>";
	public static String attrName = "<<attrName>>";
	public static String attrNote = "<<attrNote>>";
	public static String firstCapitalAttrName = "<<firstCapitalAttrName>>";
	public static String sqlExcption = "000020";
	public static String domainPath = "<<domainPath>>";
	public static String domainType = "<<domainType>>";
	public static String domainName = "<<domainName>>";
	public static String tableAbbr = "<<tableAbbr>>";
	public static String tableName = "<<tableName>>";
	public static String columNames = "<<columNames>>";
	public static String columValues = "<<columValues>>";
	public static String primaryKey = "<<primaryKey>>";
	public static String moduleName = "<<moduleName>>";
	public static String autoFlag = "<<autoFlag>>";
	public static String nullType = "<<nullType>>";
	public static String functionName = "<<functionName>>";
	public static String dirPath = "<<dirPath>>";
	public static String callName = "<<callName>>";
	public static String actionName = "<<actionName>>";
	public static String outputParam = "<<outputParam>>";
	public static String outputValue = "<<outputValue>>";
	public static String equal = "<<equal>>";
	public static String inputParam = "<<inputParam>>";
	public static String inputValue = "<<inputValue>>";
	public static String functionContent = "<<functionContent>>";


	public static String now = "<<now>>";
	
	public static String dbName = "eyoubika";
	public static String dbSlashCustomFileTag = "//>. <CustomFileTag>";
	//public static String javaCustomTagEnd = "/*</CustomTag>*/";
	public static String bracketCustomFileTag = "<!--<CustomFileTag>-->";
	public static String dbSlashCustomImportTag = "//>. <CustomImportTag>";
	//public static String xmlCustomTagEnd = "<!--</CustomTag>-->";
	
	public static String authorName = "lijiaxuan";
	public static String authorEmail = "jiaxuan.li@eyoubika.com";
	public static String authorCorp = "CaiDan";
	public static String year = "2015";
	
}
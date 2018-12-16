package com.eyoubika.model.resource.template;

import com.eyoubika.model.resource.template.BaseTpl;

public class SqlFileTpl extends BaseTpl{
	

	//------------------------------------------ SQL -------------------------------------------------
		public static String sqlDbLine = "USE `"+ dbName + "`;\n";
		public static String sqlTbLine = "/*Table structure for table `<<tableName>>` */\n" +
										"DROP TABLE IF EXISTS `<<tableName>>`;\n" +
										"CREATE TABLE `<<tableName>>` (\n";
		public static String sqlTbColumnLine = sp + " `<<tableAbbr>>_<<firstCapitalAttrName>>` <<attrType>> <<nullType>> <<autoFlag>>,\n";
		public static String sqlTbPKLine = sp + "PRIMARY KEY  (`<<tableAbbr>>_<<primaryKey>>`)\n";
		public static String sqlTbEndLine = ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n/*Data for the table `<<tableName>>` */\n\n";

	//----------------------------------------- JAVA annotation ----------------------------------------- 
	public static String daoFileNote = "<!--==========================================================================================-\n" +
									"  - Description:__Description__\n" +
									"  - Sql:\t\t__Sql__\n" +
									"  - Author:\t\t" + authorName +"\n" +
									"  - Copyright:\t"+ authorCorp +" (c) " + year + " " + authorEmail +"\n" +
									"  - History:\t1.0 created by " + authorName +" at <<now>>\n" +
									"  -==========================================================================================-->\n";
	
}

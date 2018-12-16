package com.eyoubika.model.resource.template;

import com.eyoubika.model.resource.template.BaseTpl;

public class XmlFileTpl extends BaseTpl{
	
	public static String daoHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
									"<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\"\n" + 
									"\"http://ibatis.apache.org/dtd/sql-map-2.dtd\">\n";
	//------------------------------------------ DAO XML -------------------------------------------------
	public static String daoSqlMapBeg = "<sqlMap namespace=\"<<tableName>>\">\n";
	public static String daoSqlMapEnd = "</sqlMap>\n";
	public static String daoResultMapEnd = sp + "</resultMap>\n";
	public static String daoTypeAlias = sp + "<typeAlias alias=\"<<domainType>>\" type=\"<<domainPath>>\"/>\n";
	public static String daoResultMapBeg = sp + "<resultMap id=\"<<firstCapitalAttrName>>Result\" class=\"<<domainPath>>\">\n";						
	public static String daoResultMapLine = sp + sp + "<result column=\"<<tableAbbr>>_<<firstCapitalAttrName>>\" property=\"<<attrName>>\" jdbcType=\"<<attrType>>\" />\n";
	
	public static String daoAutoAddLine = sp + "<insert id=\"insert<<moduleName>>\" parameterClass=\"<<domainPath>>\">\n" + 
									sp + sp + "insert into <<tableName>>(<<columNames>>) \n" +
									sp + sp + "values (<<columValues>>)\n"  +
									sp + sp + "<selectKey resultClass=\"int\" keyProperty=\"<<primaryKey>>\">\n" +
									sp + sp + sp + "SELECT LAST_INSERT_ID() as <<primaryKey>>\n" +
									sp + sp + "</selectKey>\n" +
									sp + "</insert>\n";
	public static String daoAddLine = sp + "<insert id=\"insert<<moduleName>>\" parameterClass=\"<<domainPath>>\">\n" + 
									sp + sp + "insert into <<tableName>>(<<columNames>>) \n" +
									sp + sp + "values (<<columValues>>)\n"  +
									sp + "</insert>\n";
	public static String daoFindLine = sp + "<select id=\"select<<moduleName>>\" parameterClass=\"<<attrType>>\" resultMap=\"<<moduleName>>Result\">\n" +
									sp + sp + "select * from <<tableName>> where <<tableAbbr>>_<<firstCapitalAttrName>>= #<<primaryKey>>#\n" +
									sp + "</select>\n";
	public static String daoSelectByDomainBeg = sp + "<select id=\"select<<moduleName>>ByDomain\" parameterClass=\"<<domainPath>>\" resultMap=\"<<moduleName>>Result\">\n" +
									sp + sp + "select * from <<tableName>>\n";
	public static String daoDynamicWhereTag = sp + sp + "<dynamic prepend=\"where\">\n";
	public static String daoDynamicSetTag = sp + sp + "<dynamic prepend=\"set\">\n";
	public static String daoDynamicTagEnd = sp + sp + "</dynamic>\n";
	public static String daoDynamicWhereLine = sp + sp + sp + "<isNotNull prepend=\"and\" property=\"<<attrName>>\">\n" +
									sp + sp + sp + sp + "<<tableAbbr>>_<<firstCapitalAttrName>> = #<<attrName>>#\n" +
									sp + sp + sp + "</isNotNull>\n";
									
	public static String daoSelectTagEnd = sp + "</select>\n";
	public static String daoSelectListBeg = sp + "<select id=\"select<<moduleName>>List\" parameterClass=\"<<domainPath>>\" resultMap=\"<<moduleName>>Result\">\n" +
			sp + sp + "select * from <<tableName>>\n";								
	public static String daoDeleteLine = sp + "<delete id = \"delete<<moduleName>>\"  parameterClass=\"<<attrType>>\">\n" +
									sp + sp + "delete from <<tableName>> where <<tableAbbr>>_<<firstCapitalAttrName>> = #<<primaryKey>>#\n" +
									sp + "</delete>\n";
	public static String daoDeleteAllLine = sp + "<delete id = \"deleteAll\">\n" +
									sp + sp + "delete from <<tableName>>\n" +
									sp + "</delete>\n";
	public static String daoUpdateBeg = sp + "<update id = \"update<<moduleName>>\" parameterClass=\"<<domainPath>>\">\n" +
									sp + sp + "update <<tableName>>\n";								
	public static String daoDynamicSetLine = sp + sp + sp + "<isNotNull prepend=\",\" property=\"<<attrName>>\">\n" +
									sp + sp + sp + sp + "<<tableAbbr>>_<<firstCapitalAttrName>> = #<<attrName>>#\n" +
									sp + sp + sp + "</isNotNull>\n" ;						
	public static String daoUpdateWhereEnd = sp + "where <<tableAbbr>>_<<firstCapitalAttrName>> = #<<primaryKey>>#\n" +
									sp + "</update>\n";
	public static String daoUpdateTagEnd = sp + "</update>\n";  
	public static String daoIsExistBeg = sp + "<select id=\"isExistByDomain\" parameterClass=\"<<domainPath>>\" resultClass=\"String\">\n" +
									sp + sp + "select 1 from <<tableName>>\n";
	public static String daoLimitLine = sp + sp + " limit 1\n";
	
/*	public static String daoIsExist = sp + "<select id=\"isExist\" parameterClass=\"<<attrType>>\" resultClass=\"String\">\n" +
			sp + sp + "select 1 from <<tableName>> where <<tableAbbr>>_<<firstCapitalAttrName>> = #<<primaryKey>># limit 1\n"+			
			sp + "</select>\n"; */


	//----------------------------------------- JAVA annotation ----------------------------------------- 
	public static String daoFileNote = "<!--==========================================================================================-\n" +
									"  - Description:__Description__\n" +
									"  - Sql:\t\t__Sql__\n" +
									"  - Author:\t\t" + authorName +"\n" +
									"  - Copyright:\t"+ authorCorp +" (c) " + year + " " + authorEmail +"\n" +
									"  - History:\t1.0 created by " + authorName +" at <<now>>\n" +
									"  -==========================================================================================-->\n";
	
}
package com.eyoubika.model.resource.template;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseService;
import com.eyoubika.common.YbkException;
import com.eyoubika.model.resource.template.BaseTpl;
import com.eyoubika.user.application.UserAL;
import com.eyoubika.user.domain.UserBasicDomain;
import com.eyoubika.user.domain.UserDetailDomain;
import com.eyoubika.user.domain.UserInfoDomain;
import com.eyoubika.user.service.IUserService;
import com.eyoubika.user.web.VO.UserBasicVO;
import com.eyoubika.util.ConverterUtil;

public class JavaFileTpl extends BaseTpl{

	

		//------------------------------------------ BEAN XML-------------------------------------------------
		
		public static String beansXmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
										"<beans\n" +
										sp + "xmlns=\"http://www.springframework.org/schema/beans\"\n" +
										sp + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
										sp + "xmlns:p=\"http://www.springframework.org/schema/p\"\n" +
										sp + "xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd\">\n" ; 
		public static String beansXmlTagBeg = "<beans>\n";
		public static String beansXmlTagEnd= "</beans>\n";
		public static String beanXmlTagHeader = sp + "<bean id=\"<<attrName>>\" class=\"<<packageName>>\">\n";
		public static String beanPropertyLine = sp + sp + "<property name=\"<<attrName>>\" ref=\"<<attrName>>\" />\n";
		public static String beanXmlTagBeg = sp + "<bean>\n";
		public static String beanXmlTagEnd= sp + "</bean>\n";
		
		public static String beansXmlAdding =  sp + " <import resource=\"classpath:com/eyoubika/<<namespace>>/<<namespace>>Context.xml\" />\n";
	//------------------------------------------ STRUTS XML-------------------------------------------------
	
	public static String strutsXmlHeader = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" + 
									"<!DOCTYPE struts PUBLIC\n" +
									sp + "\"-//Apache Software Foundation//DTD Struts Configuration 2.1.7//EN\"\n" +
									sp + "\"http://struts.apache.org/dtds/struts-2.1.7.dtd\">\n" ; 
	public static String strutsXmlPre = "<struts>\n" + 
									sp + "<package name=\"<<packageName>>\" extends=\"json-default\" namespace=\"/<<namespace>>\">\n" +
									sp + sp + "<interceptors>\n" +
									sp + sp + sp + "<interceptor name=\"errorInterceptor\" class=\"com.eyoubika.common.ErrorInterceptor\"></interceptor>\n" +
									sp + sp + sp + "<interceptor name=\"authInterceptor\" class=\"com.eyoubika.common.AuthInterceptor\"></interceptor>\n" +
									sp + sp + sp + "<interceptor-stack name=\"myErrorInterceptor\">\n" +
									sp + sp + sp + sp + "<interceptor-ref name=\"defaultStack\" />\n" +
									sp + sp + sp + sp + "<interceptor-ref name=\"authInterceptor\" />\n" +
									sp + sp + sp + sp + "<interceptor-ref name=\"errorInterceptor\" />\n" +
									sp + sp + sp + "</interceptor-stack>\n" +
									sp + sp + "</interceptors>\n" +
									sp + sp + "<default-interceptor-ref name=\"myErrorInterceptor\" />\n" +
									sp + sp + "<global-results>\n" +
									sp + sp + sp + "<result name=\"YbkException\" type=\"json\">\n" +
									sp + sp + sp + sp + "<param name=\"root\">jsonData</param>\n" +
									sp + sp + sp + "</result>\n" +
									sp + sp + sp + "<result type=\"json\">\n" +
									sp + sp + "<param name=\"root\">jsonData</param>\n" +
									sp + sp + sp + "</result>\n" +
									sp + sp + "</global-results>\n"; 
	public static String strutsXmlLine = sp + sp + "<action name=\"<<actionName>>\" class=\"<<moduleName>>Action\" method=\"<<actionName>>\">\n" + 
									sp + sp + sp + "<result type=\"json\">\n" +
									sp + sp + sp + sp + "<param name=\"root\">jsonData</param>\n" +
									sp + sp + sp + "</result>\n" +
									sp + sp + "</action>\n";
	public static String strutsXmlFooter = sp + "</package>\n" +
									"</struts>\n";
	public static String strutsXmlTagBeg = "<struts>\n";
	public static String strutsXmlTagEnd= "</struts>\n";
	public static String strutsXmlAdding =  sp + "<include file=\"com/eyoubika/<<namespace>>/<<namespace>>Struts.xml\"/>\n";
	
	

	//------------------------------------------ DOMAIN JAVA-------------------------------------------------
	public static String packageLine = "package <<packageName>>;\n";
	public static String importLine = "import <<importName>>;\n";
	public static String baisicClassHeader = "public class <<className>>{\n";
	public static String toStringHeader = sp + "public String toString() {\n"+ sp + sp + "String string = \"\";\n";
	public static String attrLine = sp + "private <<attrType>> <<attrName>>;"+ sp + "//<<attrNote>>\n" ;
	public static String toStringLine = sp + sp + "string += \"<<attrNote>>:\\t\"+ this.get<<firstCapitalAttrName>>()+\"\\n\";\n";
	public static String getMethodLine = sp + "public <<attrType>> get<<firstCapitalAttrName>>(){\n"+sp+sp+"return <<attrName>>;\n"+sp+"}\n";
	public static String setMethodLine = sp + "public void set<<firstCapitalAttrName>>(<<attrType>> <<attrName>>){\n"+sp+sp+"this.<<attrName>> = <<attrName>>;\n"+sp+"}\n";
	public static String toStringFooter = sp + sp + "return string;\n"+ sp + "}\n";
	public static String classFooter = "}\n";
	public static String initHeader = sp + "public void init(){\n";
	public static String initLine = sp + sp + "this.set<<firstCapitalAttrName>>(null);\n";
	public static String functionFooter =  sp + "}\n";
	//------------------------------------------ ACTION JAVA-------------------------------------------------
	
	public static String actionImportPre = "import javax.servlet.http.HttpServletRequest;\n" + 
									"import org.apache.struts2.ServletActionContext;\n" +
									"import com.eyoubika.common.BaseAction;\n" +
									"import com.eyoubika.common.YbkException;\n" +
									"import com.eyoubika.util.ConverterUtil;\n"; 
								

	public static String actionClassHeader =  "public class <<className>> extends BaseAction {\n"; 
	public static String actionFunctionLine = sp +"public String <<actionName>>(){\n" +
			sp + sp + "HttpServletRequest request = ServletActionContext.getRequest();\n" +
			sp + sp + "//> 1.check params\n" +
			sp + sp + "if (null==request.getParameter(\"userId\")){;\n" +
			sp + sp + sp + "throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);\n" +
			sp + sp + "}\n" +
			sp + sp + "//> 2. build vo\n" +
			sp + sp + "<<attrName>>.init();\n" +
			sp + sp + "ConverterUtil.RequestToVO(request, <<attrName>>);\n" +
			sp + sp + "//> 3. do\n" +
			sp + sp + "this.jsonData = <<callName>>.<<actionName>>(<<attrName>>);\n" +
			sp + sp + "return SUCCESS;\n" +
			sp + "}\n" ;

	/*public static String actionFunctionLine = sp +"public String <<actionName>>(){\n" +
			sp + sp + "<<attrType>> <<attrName>> = new <<attrType>>();\n" +
			sp + sp + "HttpServletRequest request = ServletActionContext.getRequest();\n" +
			sp + sp + "//> 1.check params\n" +
			sp + sp + "if (null==request.getParameter(\"userId\")){;\n" +
			sp + sp + sp + "throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);\n" +
			sp + sp + "}\n" +
			sp + sp + "//> 2. build vo\n" +
			sp + sp + "ConverterUtil.RequestToVO(request, <<attrName>>);\n" +
			sp + sp + "//> 3. do\n" +
			sp + sp + "this.jsonData = <<callName>>.<<actionName>>(<<attrName>>);\n" +
			sp + sp + "//> 4. return json\n" +
			sp + sp + "<<attrName>> = null;\n" +
			sp + sp + "return SUCCESS;\n" +
			sp + "}\n" ;*/

	//------------------------------------------ SERVICE JAVA-------------------------------------------------
	public static String serviceImportPre = "import java.util.Map;\n" + 
									"import com.eyoubika.util.ConverterUtil;\n" + 
									"import com.eyoubika.common.BaseService;\n"; 
	public static String serviceClassHeader =  "public interface I<<moduleName>>Service{\n"; 
	public static String serviceFunctionLine = sp +"public Map<String, Object> <<actionName>>(<<attrType>> <<attrName>>);\n";
	public static String serviceImplImportPre = "import java.util.Map;\n" + 
									"import com.eyoubika.util.ConverterUtil;\n" + 
									"import com.eyoubika.common.BaseService;\n"; 
	public static String serviceImplClassHeader =  "public class <<moduleName>>ServiceImpl extends BaseService implements I<<moduleName>>Service{\n"; 
	/*public static String serviceImplFunctionLine = sp +"public Map<String, Object> <<actionName>>(<<attrType>> <<attrName>>){\n" +
									sp + sp + "//ConverterUtil.VOToDomain(<<attrName>>, <<domainName>>);\n" +
									sp + sp + "<<outputParam>> <<outputValue>> <<equal>> <<moduleName>>AL.<<actionName>>(<<domainName>>);\n" +
									sp + sp + "return this.buildRetData( <<attrName>>.getUserId() , <<attrName>>.getToken());\n" +
									sp + "}\n" ;*/
	public static String serviceImplFunctionLine = sp +"public Map<String, Object> <<actionName>>(<<attrType>> <<attrName>>){\n" +
			sp + sp + "//<<domainName>>.init();\n//ConverterUtil.VOToDomain(<<attrName>>, <<domainName>>);\n" +
			sp + sp + "<<outputParam>> <<outputValue>> <<equal>> <<moduleName>>AL.<<actionName>>(<<domainName>>);\n" +
			sp + sp + "return this.buildRetData( <<attrName>>.getUserId() , <<attrName>>.getToken());\n" +
			sp + "}\n" ;
		

	//------------------------------------------ AL JAVA-------------------------------------------------
		public static String alImportPre = "import com.eyoubika.common.CommonVariables;\n" + 
										"import com.eyoubika.util.CommonUtil;\n" + 
										"import com.eyoubika.common.YbkException;\n"; 
		public static String alClassHeader =  "public class <<moduleName>>AL{\n"; 
		
		public static String alFunctionLine = sp +"public <<outputParam>> <<actionName>>(<<inputParam>> <<inputValue>>){\n" +										
										sp + sp + "<<functionContent>>" +
										sp + "}\n" ;
	//------------------------------------------ DAO JAVA-------------------------------------------------
	public static String daoClassHeader =  "public class <<className>> extends BaseDao {\n"+sp +"String nameSpace = \"<<tableName>>\";\n"; 
	public static String daoImportPre = "import java.sql.SQLException;\nimport java.util.List;\nimport com.eyoubika.common.BaseDao;\nimport com.eyoubika.common.YbkException;\nimport <<domainPath>>;\n";
	public static String daoAutoAddLine = sp +"public int add<<moduleName>>(<<domainType>> <<attrName>>){\n" +
									sp + sp + "int insertId = 0;\n" +
									sp + sp + "Object ob = new Object();\n" +
									sp + sp + "try {\n" +
									sp + sp + sp + "ob =  sqlMapClient.insert(nameSpace +\".insert<<moduleName>>\", <<attrName>>);\n" +
									sp + sp + "} catch (SQLException e) {\n" +
									sp + sp + sp + "ob = null;\n" +
									sp + sp + sp + "e.printStackTrace();\n" +
									sp + sp + sp + "throw new YbkException(YbkException.CODE"+ sqlExcption +", YbkException.DESC"+ sqlExcption +");\n" +
									sp + sp + "}\n" +
									sp + sp + "insertId = Integer.parseInt(ob.toString());\n" +
									sp + sp + "ob = null;\n" +
									sp + sp + "return insertId;\n" +
									sp + "}\n" ;
	public static String daoAddLine = sp +"public void add<<moduleName>>(<<domainType>> <<attrName>>){\n" +
									sp + sp + "try {\n" +
									sp + sp + sp + "sqlMapClient.insert(nameSpace +\".insert<<moduleName>>\", <<attrName>>);\n" +
									sp + sp + "} catch (SQLException e) {\n" +
									sp + sp + sp + "e.printStackTrace();\n" +
									sp + sp + sp + "throw new YbkException(YbkException.CODE"+ sqlExcption +", YbkException.DESC"+ sqlExcption +");\n" +
									sp + sp + "}\n" +
									sp + "}\n" ;
	public static String daoDeleteLine = sp +"public int delete<<moduleName>>(<<attrType>> <<primaryKey>>){\n" +
									sp + sp + "try {\n" +
									sp + sp + sp + "return (int)sqlMapClient.delete(nameSpace +\".delete<<moduleName>>\", <<primaryKey>>);\n" +
									sp + sp + "} catch (SQLException e) {\n" +
									sp + sp + sp + "e.printStackTrace();\n" +
									sp + sp + sp + "throw new YbkException(YbkException.CODE"+ sqlExcption +", YbkException.DESC"+ sqlExcption +");\n" +
									sp + sp + "}\n" +
									sp + "}\n" ;
	public static String daoDeleteAllLine = sp +"public int deleteAll(){\n" +
									sp + sp + "try {\n" +
									sp + sp + sp + "return (int)sqlMapClient.delete(nameSpace +\".deleteAll\");\n" +
									sp + sp + "} catch (SQLException e) {\n" +
									sp + sp + sp + "e.printStackTrace();\n" +
									sp + sp + sp + "throw new YbkException(YbkException.CODE"+ sqlExcption +", YbkException.DESC"+ sqlExcption +");\n" +
									sp + sp + "}\n" +
									sp + "}\n" ;

	public static String daoModifyLine = sp +"public void modify<<firstCapitalAttrName>>(<<attrType>> <<attrName>>){\n" +
									sp + sp + "try {\n" +
									sp + sp + sp + "sqlMapClient.update(nameSpace +\".update<<firstCapitalAttrName>>\", <<attrName>>);\n" +
									sp + sp + "} catch (SQLException e) {\n" +
									sp + sp + sp + "e.printStackTrace();\n" +
									sp + sp + sp + "throw new YbkException(YbkException.CODE"+ sqlExcption +", YbkException.DESC"+ sqlExcption +");\n" +
									sp + sp + "}\n" +
									sp + "}\n" ;
	/*public static String daoModifyByDomainLine = sp +"public void modify<<firstCapitalAttrName>>ByDomain(<<attrType>> <<attrName>>){\n" +
									sp + sp + "try {\n" +
									sp + sp + sp + "sqlMapClient.update(\"update<<firstCapitalAttrName>>ByDomain\", <<attrName>>);\n" +
									sp + sp + "} catch (SQLException e) {\n" +
									sp + sp + sp + "e.printStackTrace();\n" +
									sp + sp + sp + "throw new YbkException(YbkException.CODE"+ sqlExcption +", YbkException.DESC"+ sqlExcption +");\n" +
									sp + sp + "}\n" +
									sp + "}\n" ;*/
	public static String daoFindLine = sp +"public <<domainType>> find<<firstCapitalAttrName>>(<<attrType>> <<attrName>>){\n" +
									sp + sp + "try {\n" +
									sp + sp + sp + "return (<<domainType>>) sqlMapClient.queryForObject(nameSpace +\".select<<firstCapitalAttrName>>\", <<attrName>>);\n" +
									sp + sp + "} catch (SQLException e) {\n" +
									sp + sp + sp + "e.printStackTrace();\n" +
									sp + sp + sp + "throw new YbkException(YbkException.CODE"+ sqlExcption +", YbkException.DESC"+ sqlExcption +");\n" +
									sp + sp + "}\n" +
									sp + "}\n" ;
	public static String daoFindByDomainLine = sp +"public <<domainType>> find<<firstCapitalAttrName>>ByDomain(<<domainType>> <<attrName>>){\n" +
									sp + sp + "try {\n" +
									sp + sp + sp + "return (<<domainType>>) sqlMapClient.queryForObject(nameSpace +\".select<<firstCapitalAttrName>>ByDomain\", <<attrName>>);\n" +
									sp + sp + "} catch (SQLException e) {\n" +
									sp + sp + sp + "e.printStackTrace();\n" +
									sp + sp + sp + "throw new YbkException(YbkException.CODE"+ sqlExcption +", YbkException.DESC"+ sqlExcption +");\n" +
									sp + sp + "}\n" +
									sp + "}\n" ;
	public static String daoQueryListLine = sp +"public List<<<domainType>>> query<<moduleName>>(<<domainType>> <<attrName>>){\n" +
									sp + sp + "try {\n" +
									sp + sp + sp + "return (List<<<domainType>>>) sqlMapClient.queryForList(nameSpace +\".select<<moduleName>>List\", <<attrName>>);\n" +
									sp + sp + "} catch (SQLException e) {\n" +
									sp + sp + sp + "e.printStackTrace();\n" +
									sp + sp + sp + "throw new YbkException(YbkException.CODE"+ sqlExcption +", YbkException.DESC"+ sqlExcption +");\n" +
									sp + sp + "}\n" +
									sp + "}\n" ;
	public static String daoIsExistByDomainLine = sp +"public boolean isExistByDomain(<<domainType>> <<attrName>>){\n" +
									sp + sp + "boolean ret = true;\n" +
									sp + sp + "Object ob = new Object();\n" +
									sp + sp + "try {\n" +
									sp + sp + sp + "ob = sqlMapClient.queryForObject(nameSpace +\".isExistByDomain\", <<attrName>>);\n" +
									sp + sp + sp + "ret = (ob==null) ? false : true;\n" +
									sp + sp + "} catch (SQLException e) {\n" +
									sp + sp + sp + "e.printStackTrace();\n" +
									sp + sp + sp + "throw new YbkException(YbkException.CODE"+ sqlExcption +", YbkException.DESC"+ sqlExcption +");\n" +
									sp + sp + "}\n" +
									sp + sp + "return ret;\n" +
									sp + "}\n" ;
	
	public static String daoXmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
									"<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\"\n" + 
									"\"http://ibatis.apache.org/dtd/sql-map-2.dtd\">\n";
	//------------------------------------------ DAO XML -------------------------------------------------
	public static String daoXmlSqlMapBeg = "<sqlMap namespace=\"<<tableName>>\">\n";
	public static String daoXmlSqlMapEnd = "</sqlMap>\n";
	public static String daoXmlResultMapEnd = sp + "</resultMap>\n";
	public static String daoXmlTypeAlias = sp + "<typeAlias alias=\"<<domainType>>\" type=\"<<domainPath>>\"/>\n";
	public static String daoXmlResultMapBeg = sp + "<resultMap id=\"<<firstCapitalAttrName>>Result\" class=\"<<domainPath>>\">\n";						
	public static String daoXmlResultMapLine = sp + sp + "<result column=\"<<tableAbbr>>_<<firstCapitalAttrName>>\" property=\"<<attrName>>\" jdbcType=\"<<attrType>>\" />\n";
	
	public static String daoXmlAutoAddLine = sp + "<insert id=\"insert<<moduleName>>\" parameterClass=\"<<domainPath>>\">\n" + 
									sp + sp + "insert into <<tableName>>(<<columNames>>) \n" +
									sp + sp + "values (<<columValues>>)\n"  +
									sp + sp + "<selectKey resultClass=\"int\" keyProperty=\"<<primaryKey>>\">\n" +
									sp + sp + sp + "SELECT LAST_INSERT_ID() as <<primaryKey>>\n" +
									sp + sp + "</selectKey>\n" +
									sp + "</insert>\n";
	public static String daoXmlAddLine = sp + "<insert id=\"insert<<moduleName>>\" parameterClass=\"<<domainPath>>\">\n" + 
									sp + sp + "insert into <<tableName>>(<<columNames>>) \n" +
									sp + sp + "values (<<columValues>>)\n"  +
									sp + "</insert>\n";
	public static String daoXmlFindLine = sp + "<select id=\"select<<moduleName>>\" parameterClass=\"<<attrType>>\" resultMap=\"<<moduleName>>Result\">\n" +
									sp + sp + "select * from <<tableName>> where <<tableAbbr>>_<<firstCapitalAttrName>>= #<<primaryKey>>#\n" +
									sp + "</select>\n";
	public static String daoXmlSelectByDomainBeg = sp + "<select id=\"select<<moduleName>>ByDomain\" parameterClass=\"<<domainPath>>\" resultMap=\"<<moduleName>>Result\">\n" +
									sp + sp + "select * from <<tableName>>\n";
	public static String daoXmlDynamicWhereTag = sp + sp + "<dynamic prepend=\"where\">\n";
	public static String daoXmlDynamicSetTag = sp + sp + "<dynamic prepend=\"set\">\n";
	public static String daoXmlDynamicTagEnd = sp + sp + "</dynamic>\n";
	public static String daoXmlDynamicWhereLine = sp + sp + sp + "<isNotNull prepend=\"and\" property=\"<<attrName>>\">\n" +
									sp + sp + sp + sp + "<<tableAbbr>>_<<firstCapitalAttrName>> = #<<attrName>>#\n" +
									sp + sp + sp + "</isNotNull>\n";
									
	public static String daoXmlSelectTagEnd = sp + "</select>\n";
	public static String daoXmlSelectListBeg = sp + "<select id=\"select<<moduleName>>List\" parameterClass=\"<<domainPath>>\" resultMap=\"<<moduleName>>Result\">\n" +
			sp + sp + "select * from <<tableName>>\n";								
	public static String daoXmlDeleteLine = sp + "<delete id = \"delete<<moduleName>>\"  parameterClass=\"<<attrType>>\">\n" +
									sp + sp + "delete from <<tableName>> where <<tableAbbr>>_<<firstCapitalAttrName>> = #<<primaryKey>>#\n" +
									sp + "</delete>\n";
	public static String daoXmlDeleteAllLine = sp + "<delete id = \"deleteAll\">\n" +
									sp + sp + "delete from <<tableName>>\n" +
									sp + "</delete>\n";
	public static String daoXmlUpdateBeg = sp + "<update id = \"update<<moduleName>>\" parameterClass=\"<<domainPath>>\">\n" +
									sp + sp + "update <<tableName>>\n";								
	public static String daoXmlDynamicSetLine = sp + sp + sp + "<isNotNull prepend=\",\" property=\"<<attrName>>\">\n" +
									sp + sp + sp + sp + "<<tableAbbr>>_<<firstCapitalAttrName>> = #<<attrName>>#\n" +
									sp + sp + sp + "</isNotNull>\n" ;						
	public static String daoXmlUpdateWhereEnd = sp + "where <<tableAbbr>>_<<firstCapitalAttrName>> = #<<primaryKey>>#\n" +
									sp + "</update>\n";
	public static String daoXmlUpdateTagEnd = sp + "</update>\n";  
	public static String daoXmlIsExistBeg = sp + "<select id=\"isExistByDomain\" parameterClass=\"<<domainPath>>\" resultClass=\"String\">\n" +
									sp + sp + "select 1 from <<tableName>>\n";
	public static String daoXmlLimitLine = sp + sp + " limit 1\n";
	
/*	public static String daoXmlIsExist = sp + "<select id=\"isExist\" parameterClass=\"<<attrType>>\" resultClass=\"String\">\n" +
			sp + sp + "select 1 from <<tableName>> where <<tableAbbr>>_<<firstCapitalAttrName>> = #<<primaryKey>># limit 1\n"+			
			sp + "</select>\n"; */

	//----------------------------------------- JAVA annotation ----------------------------------------- 
	public static String javaFunctionNote =  sp + "/*--------------------------------------------------------------------------------------*\n" +
									sp +" * Description:\t__Description__\n" +
									sp +" * Method:\t\t__Method__\n" +
									sp +" * Author:\t\t1.0 created by " + authorName +" at <<now>>\n" +
									sp +" *--------------------------------------------------------------------------------------*/\n";
	public static String javaClassNote = "/*==========================================================================================*\n" +
									" * Description:\t__Description__\n" +
									" * Class:\t\t__Class__\n" +
									" * Author:\t\t" + authorName +"\n" +
									" * Copyright:\t"+ authorCorp +" (c) " + year + " " + authorEmail +"\n" +
									" * History:\t\t1.0 created by " + authorName +" at <<now>>\n" +
									" *==========================================================================================*/\n";
	public static String daoXmlFileNote = "<!--==========================================================================================-\n" +
									"  - Description:__Description__\n" +
									"  - Sql:\t\t__Sql__\n" +
									"  - Author:\t\t" + authorName +"\n" +
									"  - Copyright:\t"+ authorCorp +" (c) " + year + " " + authorEmail +"\n" +
									"  - History:\t1.0 created by " + authorName +" at <<now>>\n" +
									"  -==========================================================================================-->\n";
	

	
}
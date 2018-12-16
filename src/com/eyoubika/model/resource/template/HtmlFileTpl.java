package com.eyoubika.model.resource.template;

import com.eyoubika.model.resource.template.BaseTpl;

public class HtmlFileTpl extends BaseTpl{
	
	public static String interfaceHeader = "<html>\n"+
								sp + "<head>\n" + 
								sp + sp + "<title><<htmlTitle>></title>\n" +
								sp + sp + "<link rel=\"stylesheet\" href=\"../css/typo.css\"/>\n" +
								sp + sp + "<link rel=\"stylesheet\" href=\"../css/my.css\"/>\n" +
								sp + sp + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
								sp + sp + "<style>\n" +
								sp + sp + sp + "code{ color:#1abc9c;}\n" +
								sp + sp + sp + "html{ background:#eee; }\n" +
								sp + sp + sp + "body{ width:90%; max-width: 960px; background:#fff; margin:3em auto 0; padding-top:2em;border:1px solid #ddd;border-width:0 1px;}\n" +
								sp + sp + sp + "pre{white-space:pre-wrap;}\n" +
								sp + sp + sp + "i.serif{ text-transform:lowercase; color:#1abc9c; }\n" +
								sp + sp + sp + ":-moz-any(h1, h2, h3, h4, h5, h5) i.serif{ text-transform: capitalize; }\n" +
								sp + sp + sp + "i.serif:hover{ color:inherit; }\n" +
								sp + sp + sp + "#wrapper{ padding:5% 10%; position:relative;}\n" +
								sp + sp + sp + "#tagline{ color:#999; font-size:1em; margin:-2em 0 2em; padding-bottom:2em; border-bottom:3px double #eee; }\n" +
								sp + sp + sp + "#fork{ position:fixed; top:0; right:0; _position:absolute; }\n" +
								sp + sp + sp + "#table{ margin-bottom:2em; color:#888; }\n" +
								sp + sp + sp + "#github{ position:absolute;top:1em;}\n" +
								sp + sp + sp + "#github iframe{ display:inline;margin-right:1em; }\n" +
								sp + sp + sp + "@media only screen and (max-width: 640px) {\n" +
								sp + sp + sp + "table{ word-break:break-all;word-wrap:break-word;font-size:12px; }\n" +
								sp + sp + sp + ".typo table th, .typo table td, .typo-table th, .typo-table td .typo table caption {\n" +
								sp + sp + sp + "padding: 0.5em;\n" +
								sp + sp + sp + "}\n" +
								sp + sp + sp + "#fork{ display:none; }\n" +
								sp + sp + sp + "}\n" +
								sp + sp + "</style>\n" +
								sp + "</head>\n" ;
	public static String InterfaceBodyBeg = sp + "<body>\n" +
								sp + sp +"<div id=\"wrapper\" class=\"typo typo-selection\">\n" +
								sp + sp + sp + "<h2><a name=\"<<interfaceModule>>\"><<interfaceLevel>> <<interfaceIndex>></a></h2>\n" +
								sp + sp + sp + "<ol id=\"table\">";
	public static String InterfaceIndexLine = sp + sp + sp + sp + " <li><a href=\"<<interfaceModule>>Service.html#<<interfaceName>>\"><<interfaceCode>> <<interfaceCName>> <i class=\"serif\"><<interfaceName>></i></a></li>\n";
	public static String InterfaceIndexEnd = sp + sp + sp + "</ol>\n";					
	public static String InterfaceContextBeg = sp + sp + sp + "<h2><a name=\"<<interfaceName>>\"><<interfaceCode>> <<interfaceCName>></a><span class=\"title_return\"><a href=\"<<interfaceModule>>Service.html#<<interfaceModule>>\">:</a></span><span class=\"title_return\"><a href=\"\">&nbsp;</a></span><span class=\"title_return\"><a href=\"../\">-</a></span></h2>\n" +	
								sp + sp + sp + "<h3>接口地址</h3>\n" +
								sp + sp + sp + "<p><<interfaceUrl>></p>" +
								sp + sp + sp + "<h3>接口说明</h3>\n" +
								sp + sp + sp + "<<interfaceText>>\n" +
								sp + sp + sp + "<h3>输入参数</h3>\n" +
								sp + sp + sp + "<table>\n" +
								sp + sp + sp + "<thead>\n" +
								sp + sp + sp + "<tr>\n" +
								sp + sp + sp + "<th>No</th>\n" +
								sp + sp + sp + "<th>字段名</th>\n" +
								sp + sp + sp + "<th>变量名</th>\n" +
								sp + sp + sp + "<th>类型</th>\n" +
								sp + sp + sp + "<th>说明</th>\n" +
								sp + sp + sp + "<th>可空</th>\n" +
								sp + sp + sp + "</tr>\n" +
								sp + sp + sp + "</thead>\n" +
								sp + sp + sp + "<tbody>\n" ;
	
			



	
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
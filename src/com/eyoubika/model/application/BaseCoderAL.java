package com.eyoubika.model.application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eyoubika.model.domain.*;
import com.eyoubika.model.resource.template.JavaFileTpl;
import com.eyoubika.util.CommonUtil;

public class BaseCoderAL{
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		toIBatisType
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 上午10:11:45
	 *--------------------------------------------------------------------------------------*/
	public String toIBatisType(String dbType){	
		if (dbType.contains("varchar")) return "VARCHAR";
		if (dbType.contains("char")) return "CHAR";
		if (dbType.equals("MediumBlob")) return "BLOB";
		if (dbType.equals("int")) return "INT";
		if (dbType.equals("float")) return "FLOAT";
		if (dbType.contains("decimal")) return "DECIMAL";
		if (dbType.contains("numeric")) return "FLOAT";
		return dbType;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getPrimaryKeyType
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 上午10:11:42
	 *--------------------------------------------------------------------------------------*/
	public String getPrimaryKeyType(JavaFileModelDomain jfm){
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
	public String upperFirst(String name) {
		return CommonUtil.upperFirst(name);
	}
		
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		lowerFirst
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 上午10:54:07
	 *--------------------------------------------------------------------------------------*/
	public String lowerFirst(String name) {
		return CommonUtil.lowerFirst(name) ;      
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		upperAllAbbr
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午3:14:27
	 *--------------------------------------------------------------------------------------*/
	public String upperAllAbbr(String name){
		return CommonUtil.buildAbbr(name);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getModuleDirectory
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:07:56
	 *--------------------------------------------------------------------------------------*/
	public String getModuleDirectory(String dirString, String type){
		String[] dirs = dirString.split("\\.");
		String dirPath = CommonUtil.PROJECTSRC_DIR;			
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
	public String buildDirectory(JavaFileModelDomain javaFileModel){
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
	 * Method:		buildDirectory
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:07:59
	 *--------------------------------------------------------------------------------------*/
	public String buildDirectory(ModuleDomain javaFileModel){
		String dirPath = javaFileModel.getFileDir();
				
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
	 * Method:		extractCustomString
	 * Author:		1.0 created by lijiaxuan at 2015年7月11日 上午8:31:36
	 *--------------------------------------------------------------------------------------*/
	public String extractCustomString(String fileName, String fileDir, String type){
		System.out.println("fileName, fileDir: " + fileDir +  fileName); 
		String res = "";
		if (!CommonUtil.isFileExists(fileName, fileDir)){
			String tag = "";
			if (type.equals("java")){
				tag = JavaFileTpl.dbSlashCustomFileTag;
				res = "\t" + tag + "\n\t" + tag + "\n";
			} else if (type.equals("xml")){
				tag = JavaFileTpl.bracketCustomFileTag;
				res = "\t" + tag + "\n\t" + tag + "\n";
			} else if (type.equals("import")){
				tag = JavaFileTpl.dbSlashCustomImportTag;
				res =  tag + "\n" + tag + "\n";
			}
			 return res;
		} else {
			System.out.println("true " ); 
		}
		String content = CommonUtil.fromFile(fileName, fileDir);
		String[] blocks = null;
		
		String tag = "";
		
		if (type.equals("java")){
			tag = JavaFileTpl.dbSlashCustomFileTag;
		} else if (type.equals("xml")){
			tag = JavaFileTpl.bracketCustomFileTag;
		} else if (type.equals("import")){
			tag = JavaFileTpl.dbSlashCustomImportTag;
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
	 * Method:		getModel
	 * Author:		1.0 created by lijiaxuan at 2015年6月7日 下午2:08:08
	 *--------------------------------------------------------------------------------------*/
	public JavaFileModelDomain getModel(String  moduleName){
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
	 * Description:	TODO
	 * Method:		getModule
	 * Author:		1.0 created by lijiaxuan at 2015年7月13日 下午7:02:33
	 *--------------------------------------------------------------------------------------*/
	public ModuleDomain getModule(String  moduleName){
		ModuleDomain moduleDomain = new ModuleDomain();
		String fromDir = CommonUtil.MODEL_CONFIG_DIR;
		String fromFile = moduleName + "Module.xml";	
		String toDir = "";
		String toFile = "";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
	    try  
	        {  
	            DocumentBuilder db = dbf.newDocumentBuilder();  
	            Document doc = db.parse(fromDir + fromFile);  
	  
	            NodeList moduleList = doc.getElementsByTagName("module");  
	            System.out.println("共有" + moduleList.getLength() + "个module节点");  
	            for (int i = 0; i < moduleList.getLength(); i++)  
	            {  
	                Node module = moduleList.item(i);  
	                Element elem = (Element) module;  
	                System.out.println("id:" + elem.getAttribute("id"));  
	                List<Map<String,String>> actionList = new ArrayList<Map<String,String>>();
	                List<Map<String,Object>> voList = new ArrayList<Map<String,Object>>();
	                for (Node node = module.getFirstChild(); node != null; node = node.getNextSibling())  
	                {  
	                    if (node.getNodeType() == Node.ELEMENT_NODE)  
	                    {  
	                        String name = node.getNodeName();  
	                        String value = node.getFirstChild().getNodeValue();  
	                        if (name.equals("name")) {
	                        	moduleDomain.setModuleName(value);
	                        }else if (name.equals("package")) {
	                        	moduleDomain.setPackageName(value);
	                        	String[] pix = value.split("\\.");
	                        	//System.out.printiln("")
	                        	if (pix!=null && pix.length >=1){
	                        		moduleDomain.setNamespace(pix[pix.length-1]);
	                        	}
	                        	
	                        }else if (name.equals("action")) {
	                        	 HashMap<String, String> actionParams = new  HashMap<String, String>();
	                        	  Element nodeEl = (Element) node;      	                
	                        	  actionParams.put("name", nodeEl.getAttribute("name"));
	                        	 for (Node actionNode = node.getFirstChild(); actionNode != null; actionNode = actionNode.getNextSibling()){
	                        		if (actionNode.getNodeType() == Node.ELEMENT_NODE){
	                        			actionParams.put(actionNode.getNodeName(), actionNode.getFirstChild().getNodeValue());
	                        		}
	                        	 }
	                        	 actionList.add(actionParams);
	                        }else if (name.equals("vo")) {
	                        	 HashMap<String, Object> voParams = new  HashMap<String, Object>();
	                        	 
	                        	  Element nodeEl = (Element) node;      	                
	                        	  voParams.put("voName", nodeEl.getAttribute("voName"));
	                        	 for (Node voNode = node.getFirstChild(); voNode != null; voNode = voNode.getNextSibling()){
	                        		if (voNode.getNodeType() == Node.ELEMENT_NODE){
	                        			if (voNode.getNodeName().equals("val")){
	                        				System.out.println(voNode.getNodeName());
	                        				HashMap<String, String> valParams = new  HashMap<String, String>();
	                        				Element valEL = (Element) voNode;   
	                        				 for (Node valNode = voNode.getFirstChild(); valNode != null; valNode = valNode.getNextSibling()){
	                        					 if (valNode.getNodeType() == Node.ELEMENT_NODE){
	                        						 valParams.put(valNode.getNodeName(), valNode.getFirstChild().getNodeValue());
	                        					 }
	                        				 }
	                        				 voParams.put(valEL.getAttribute("valName"), valParams);
		                        		}
	                        		}
	                        			
	                        	 }
	                        	 voList.add(voParams);
	                        }
	                    }  
	                }
	                moduleDomain.setActionList(actionList);
	                moduleDomain.setVoList(voList);
	                moduleDomain.setModuleDir(getModuleDirectory(moduleDomain.getPackageName(),null));
	     
	                System.out.println(moduleDomain.toString());  
	            }  
	        }  
	        catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        }  
	    return moduleDomain;
		}
	
	//public static void main(String[] arg){
	//	ModuleDomain mo= getModelFromXml("OpenAccount");
	//	System.out.println(mo.toString());
	//}
}

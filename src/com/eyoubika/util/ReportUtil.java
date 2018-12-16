package com.eyoubika.util;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.eyoubika.common.YbkException;


public class ReportUtil{
	public static void createExcel(OutputStream os, List<Map<String, Object>> list, String sheetName) {
        //创建工作薄
		try {
			WritableWorkbook workbook =  Workbook.createWorkbook(os);	;	

	        //创建新的一页
			if (sheetName == null){
				sheetName = "First Sheet";
			}
	        WritableSheet sheet = workbook.createSheet(sheetName,0);
	        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
	        int listSize = list.size();
	        if (listSize < 1){
	        	return ;
	        }
	        int index = 0; 
	        for (String key : list.get(0).keySet()) {
	        	Label title = new Label(index,0, key); 
	        	sheet.addCell(title);
	        	System.out.println("title " + key);
	        	index++;
	        	//map.get(key)	        	  
	        }
	        
	        for (int i = 0 ; i < listSize; i++){
	        	//System.out.println("iv " + i);
	        	index = 0; 
	        	for (String key : list.get(i).keySet()) {        		     		
	 	        	String cellStr = "";
	 	        	if (list.get(i).get(key)!=null){
	 	        		cellStr = list.get(i).get(key).toString(); 	        		
	 	        	}  else {
	 	        		cellStr ="";
	 	        	}
	        		Label cell = new Label(index,i+1, cellStr); 
	 	        	sheet.addCell(cell);
	 	        	index++;	        	  
	 	       }
	        }      
	        //把创建的内容写入到输出流中，并关闭输出流
	        workbook.write();
	        workbook.close();
	        os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public static void createExcel(OutputStream os, ArrayList<ArrayList<String>> list, String sheetName) {
        //创建工作薄
		try {
			WritableWorkbook workbook =  Workbook.createWorkbook(os);	;	

	        //创建新的一页
			if (sheetName == null){
				sheetName = "First Sheet";
			}
	        WritableSheet sheet = workbook.createSheet(sheetName,0);
	        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
	        int rowNumber = list.size();
	      
	        if (rowNumber < 1){
	        	return ;
	        }
	        int columnNumber = list.get(0).size();
	        for (int i = 0; i < columnNumber; i++){
	        	Label title = new Label(i,0, list.get(0).get(i)); 
	        	sheet.addCell(title);
	        	//System.out.println("title " + key);
	        }
	        
	        for (int i = 1 ; i < rowNumber; i++){
	        	//System.out.println("iv " + i);
	        	for (int j = 0; j < columnNumber; j++) {
	        		String cellStr = "";
	 	        	if (list.get(i).get(j)!=null){
	 	        		cellStr = list.get(i).get(j).toString(); 	        		
	 	        	}  else {
	 	        		cellStr ="";
	 	        	}
	 	        	Label cell = new Label(j,i, cellStr); 
	 	        	sheet.addCell(cell);	 	 
	        	}
	        }      
	        //把创建的内容写入到输出流中，并关闭输出流
	        workbook.write();
	        workbook.close();
	        os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
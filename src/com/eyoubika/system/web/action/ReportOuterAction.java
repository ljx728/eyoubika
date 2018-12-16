package com.eyoubika.system.web.action;

import com.eyoubika.common.BaseAction;
import com.eyoubika.util.CommonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts2.ServletActionContext;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
	

public class ReportOuterAction extends BaseAction {
	 private String fileName;  
	 
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void outputExcel(){
		try {
			System.out.println("in output Excel...");
			OutputStream f = new FileOutputStream(CommonUtil.RESOURCE_DIR + "MyOut.dat") ;
			buildExcel(f);
			
			//FileOutputStream fo = new FileOutputStream(CommonUtil.RESOURCE_DIR + "MyOut.dat");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public InputStream getDownloadFile() throws FileNotFoundException{
		System.out.println("in down...");
		        String srcFile=CommonUtil.RESOURCE_DIR+   "a.xls";
		        this.fileName = "abc.xls";
		        File file=new File(srcFile);//得到一个file对象
		        return new FileInputStream(file);//返回一个文件输入流
		     }
	
	public void buildExcel(OutputStream os) {
        //创建工作薄
		try {
			WritableWorkbook workbook =  Workbook.createWorkbook(os);	;	

	        //创建新的一页
	        WritableSheet sheet = workbook.createSheet("First Sheet",0);
	        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
	        Label xuexiao = new Label(0,0,"学校");
	        
			sheet.addCell(xuexiao);
			
	        Label zhuanye = new Label(1,0,"专业");
	        sheet.addCell(zhuanye);
	        Label jingzhengli = new Label(2,0,"专业竞争力");
	        sheet.addCell(jingzhengli);
	        
	        Label qinghua = new Label(0,1,"清华大学");
	        sheet.addCell(qinghua);
	        Label jisuanji = new Label(1,1,"计算机专业");
	        sheet.addCell(jisuanji);
	        Label gao = new Label(2,1,"高");
	        sheet.addCell(gao);
	        
	        Label beida = new Label(0,2,"北京大学");
	        sheet.addCell(beida);
	        Label falv = new Label(1,2,"法律专业");
	        sheet.addCell(falv);
	        Label zhong = new Label(2,2,"中");
	        sheet.addCell(zhong);
	        
	        Label ligong = new Label(0,3,"北京理工大学");
	        sheet.addCell(ligong);
	        Label hangkong = new Label(1,3,"航空专业");
	        sheet.addCell(hangkong);
	        Label di = new Label(2,3,"低");
	        sheet.addCell(di);
	        
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
	
	  public String execute() throws Exception {  
          
	        return SUCCESS;  
	    }  
}
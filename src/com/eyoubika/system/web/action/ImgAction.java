package com.eyoubika.system.web.action;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ImageUtil;
public class ImgAction extends BaseAction implements Serializable {
	public int MAX_IMG_SIZE = 1024;
	private File[] file;
	private String[] fileFileName;
	private String[] fileContentType;
	
	public String getImage() {
		String imgName =  ServletActionContext.getRequest().getParameter("img");//.getAttribute("img").toString();
        HttpServletResponse response = null;
        ServletOutputStream out = null;
        try {
        	
        	imgName = CommonUtil.SPIDER_IMG_DIR +imgName;
            response = ServletActionContext.getResponse();
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            out.write(BufferStreamForByte(imgName, MAX_IMG_SIZE));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                  //  ((FileInputStream) response).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
	
	   public static byte[] BufferStreamForByte(String spec, int size) {
	        byte[] content = null;
	        try {
	            BufferedInputStream bis = null;    
	            ByteArrayOutputStream out = null;
	            try {
	                FileInputStream input=new FileInputStream(spec);
	                bis = new BufferedInputStream(input, size);
	                byte[] bytes = new byte[1024];	//MAX_size
	                int len;
	                out = new ByteArrayOutputStream();
	                while ((len = bis.read(bytes)) > 0) {
	                    out.write(bytes, 0, len);
	                }
	                
	                bis.close();
	                // bos.flush();
	                // bos.close();
	                content = out.toByteArray();
	            } finally {
	                if (bis != null)
	                    bis.close();
	                if (out != null)
	                    out.close();
	            }
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return content;

	    }
	   
	  /* public String uploadImg(){
		   imageFileName = CommonUtil.getNow() + getExtention(fileName);
		   String uploadFileName = ServletActionContext.getServletContext().getRealPath("UploadImages" ) + "/" + imageFileName;
		   File imageFile = new File(uploadFileName);
		   copy(myFile, imageFile);
		   return SUCCESS;
	   }*/

	   public String[] getFileFileName() {
	   return fileFileName;
	   }

	   public File[] getFile() {
	   return file;
	   }

	   public void setFile(File[] file) {
	   this.file = file;
	   }

	   public void setFileFileName(String[] fileFileName) {
	   this.fileFileName = fileFileName;
	   }

	   public String[] getFileContentType() {
	   return fileContentType;
	   }

	   public void setFileContentType(String[] fileContentType) {
	   this.fileContentType = fileContentType;
	   }

	   public String uploadImage() throws Exception {
		   System.out.println("in upload.....");
		   HttpServletRequest request = ServletActionContext.getRequest();
		   for (int i = 0; i < this.file.length; i++) {
			   System.out.println(i);
			   InputStream is = new FileInputStream(this.file[i]);
			   // String str = ServletActionContext.getServletContext().getRealPath("/upload");
			   String str = CommonUtil.TRANS_IMG_DIR;
			   System.out.println("str: " + str);
			   File f = new File(str, this.fileFileName[i]);
			   OutputStream os = new FileOutputStream(f);

			   byte[] b = new byte[1000000];
			   while (is.read(b) != -1) {
				   System.out.println(b.length);
				   os.write(b, 0, b.length);
			   }
			   is.close();
			   os.close();
		   }
		   return SUCCESS;
	   }
	   
	   public String getThumbnail(){
		   String str = CommonUtil.TRANS_IMG_DIR + "test.png";
		   int w = 100;
		   int h = 100;
		   ImageUtil.thumbnailImage(str, w, h);
		   return SUCCESS;
	   }
}
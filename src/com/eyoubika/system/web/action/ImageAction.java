package com.eyoubika.system.web.action;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eyoubika.common.BaseAction;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ImageUtil;
import com.eyoubika.system.service.IImageService;
public class ImageAction extends BaseAction {
	private IImageService imageService;
	public int MAX_IMG_SIZE = 1024;

	public IImageService getImageService() {
		return imageService;
	}

	public void setImageService(IImageService imageService) {
		this.imageService = imageService;
	}

	private String allowSuffix = "jpg,png,gif,jpeg";//允许文件格式
	   /* String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        int length = getAllowSuffix().indexOf(suffix);
        if(length == -1){
            throw new Exception("请上传允许格式的文件");
        }
        if(file.getSize() > getAllowSize()){
            throw new Exception("您上传的文件大小已经超出范围");
        }*/

	public String getImage() {
		String imgName =  ServletActionContext.getRequest().getParameter("img");//.getAttribute("img").toString();
        HttpServletResponse response = null;
        ServletOutputStream out = null;
        try {
        	
        	imgName = CommonUtil.IMG_DIR +imgName;
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
	    public String uploadImage(){
	    	imageService.uploadImage( ServletActionContext.getRequest());
	    	/*System.out.println("in headpic....");
	    	//磁盘文件条目
	    	DiskFileItemFactory factory = new DiskFileItemFactory();
	    	//上传文件的路径
	    	String savePath = CommonUtil.UPLOAD_IMG_DIR;
	    	System.out.println("savePath " + savePath);
	    	//设置缓存
	    	factory.setRepository(new File(savePath));
	    	//设置缓存大小，如果超过阀值，直接放到临时文件。。。
	    	 factory.setSizeThreshold(4096); 
	    	 
	    	ServletFileUpload upload = new ServletFileUpload(factory);
	    	 upload.setSizeMax(1000000 * 20);  
	    	HttpServletRequest request = ServletActionContext.getRequest();
	    	try {
	    		
	    		List<FileItem> list= (List<FileItem>) upload.parseRequest(request);
	    		System.out.println(list.size());
	    		for (FileItem item : list ){
	    			//获取表单的属性名字
	    			String name = item.getFieldName();
	    			System.out.println("item.getFieldName() " + item.getFieldName());
	    			//如果获取的是普通文本
	    			if (item.isFormField()){
	    				//获取字符串
	    				String value = item.getString();
	    				System.out.println("value " + value);
	    				request.setAttribute(name, value);
	    			} else{ //传入的非简单字符串处理，比如二进制数据。
	    				//获取路径名
	    				String value = item.getName();
	    				
	    				System.out.println("item.getName() " + item.getName());
	    				//索引到最后一个反斜杠
	    				int start = value.lastIndexOf("\\");
	    				//截取上传文件的字符串名字
	    				String fileName = value.substring(start + 1);
	    				System.out.println("fileName " + fileName);
	    				request.setAttribute(name, fileName);
	    				item.write(new File(savePath, fileName));
	    			}
	    		}
	    	}catch (Exception e){
	    		
	    	}*/
	    	return SUCCESS;
	    }
}
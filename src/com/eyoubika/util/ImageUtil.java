package com.eyoubika.util;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.ares.slf4j.test.Slf4jUtil;
/*==========================================================================================*
 * Description:	使用JDK原生态类生成图片缩略图和裁剪图片
 * Class:		ImageUtil 
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-17 20:10:32
 *==========================================================================================*/
public class ImageUtil{
	 /*static {
	        Slf4jUtil.buildSlf4jUtil().loadSlf4j();
	    }*/
	    private Logger log = LoggerFactory.getLogger(getClass());
	    
	    private static String DEFAULT_PREVFIX = "thumb_";
	    private static String DEFAULT_CUT_PREVFIX = "cut_";
	    private static Boolean DEFAULT_FORCE = false;
	    
	    /**
	     * <p>Title: cutImage</p>
	     * <p>Description:  根据原图与裁切size截取局部图片</p>
	     * @param srcImg    源图片
	     * @param output    图片输出流
	     * @param rect        需要截取部分的坐标和大小
	     */
	    public static void cutImage(File srcImg, OutputStream output, java.awt.Rectangle rect){
	        if(srcImg.exists()){
	            java.io.FileInputStream fis = null;
	            ImageInputStream iis = null;
	            try {
	                fis = new FileInputStream(srcImg);
	                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
	                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
	                String suffix = null;
	                // 获取图片后缀
	                if(srcImg.getName().indexOf(".") > -1) {
	                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
	                }// 类型和图片后缀全部小写，然后判断后缀是否合法
	                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") < 0){
	                   // log.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
	                    return ;
	                }
	                // 将FileInputStream 转换为ImageInputStream
	                iis = ImageIO.createImageInputStream(fis);
	                // 根据图片类型获取该种类型的ImageReader
	                ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
	                reader.setInput(iis,true);
	                ImageReadParam param = reader.getDefaultReadParam();
	                param.setSourceRegion(rect);
	                BufferedImage bi = reader.read(0, param);
	                ImageIO.write(bi, suffix, output);
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    if(fis != null) fis.close();
	                    if(iis != null) iis.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }else {
	            //log.warn("the src image is not exist.");
	        }
	    }
	    
	    public static void cutImage(File srcImg, OutputStream output, int x, int y, int width, int height){
	        cutImage(srcImg, output, new java.awt.Rectangle(x, y, width, height));
	    }
	    
	    public static void cutImage(File srcImg, String destImgPath, java.awt.Rectangle rect){
	        File destImg = new File(destImgPath);
	        if(destImg.exists()){
	            String p = destImg.getPath();
	            try {
	                if(!destImg.isDirectory()) p = destImg.getParent();
	                if(!p.endsWith(File.separator)) p = p + File.separator;
	                cutImage(srcImg, new java.io.FileOutputStream(p + DEFAULT_CUT_PREVFIX + "_" + new java.util.Date().getTime() + "_" + srcImg.getName()), rect);
	            } catch (FileNotFoundException e) {
	               // log.warn("the dest image is not exist.");
	            }
	        }//else log.warn("the dest image folder is not exist.");
	    }
	    
	    public static void cutImage(File srcImg, String destImg, int x, int y, int width, int height){
	        cutImage(srcImg, destImg, new java.awt.Rectangle(x, y, width, height));
	    }
	    
	    public static void cutImage(String srcImg, String destImg, int x, int y, int width, int height){
	        cutImage(new File(srcImg), destImg, new java.awt.Rectangle(x, y, width, height));
	    }
	    /**
	     * <p>Title: thumbnailImage</p>
	     * <p>Description: 根据图片路径生成缩略图 </p>
	     * @param imagePath    原图片路径
	     * @param w            缩略图宽
	     * @param h            缩略图高
	     * @param prevfix    生成缩略图的前缀
	     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
	     */
	    public static  void thumbnailImage(File imgFile, int w, int h, String prevfix, boolean force){
	        if(imgFile.exists()){
	            try {
	                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
	                String types = Arrays.toString(ImageIO.getReaderFormatNames());
	                String suffix = null;
	                // 获取图片后缀
	                if(imgFile.getName().indexOf(".") > -1) {
	                    suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
	                }// 类型和图片后缀全部小写，然后判断后缀是否合法
	                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0){
	                    //log.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
	                    return ;
	                }
	                //log.debug("target image's size, width:{}, height:{}.",w,h);
	                Image img = ImageIO.read(imgFile);
	                if(!force){
	                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
	                    int width = img.getWidth(null);
	                    int height = img.getHeight(null);
	                    if((width*1.0)/w < (height*1.0)/h){
	                        if(width > w){
	                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
	                           // log.debug("change image's height, width:{}, height:{}.",w,h);
	                        }
	                    } else {
	                        if(height > h){
	                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
	                           // log.debug("change image's width, width:{}, height:{}.",w,h);
	                        }
	                    }
	                }
	                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	                Graphics g = bi.getGraphics();
	                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
	                g.dispose();
	                String p = imgFile.getPath();
	                // 将图片保存在原目录并加上前缀
	                ImageIO.write(bi, suffix, new File(p.substring(0,p.lastIndexOf(File.separator)) + File.separator + prevfix +imgFile.getName()));
	            } catch (IOException e) {
	              // log.error("generate thumbnail image failed.",e);
	            }
	        }else{
	           // log.warn("the image is not exist.");
	        }
	    }
	    
	    public static void thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force){
	        File imgFile = new File(imagePath);
	        thumbnailImage(imgFile, w, h, prevfix, force);
	    }
	    
	    public static void thumbnailImage(String imagePath, int w, int h, boolean force){
	        thumbnailImage(imagePath, w, h, DEFAULT_PREVFIX, force);
	    }
	    
	    public static void thumbnailImage(String imagePath, int w, int h){
	        thumbnailImage(imagePath, w, h, DEFAULT_FORCE);
	    }
	    
	    public static void main(String[] args) {
	        new ImageUtil().thumbnailImage("imgs/Tulips.jpg", 100, 150);
	    }
}
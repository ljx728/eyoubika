package com.eyoubika.util;
  
import java.io.File;   
  

import org.apache.tools.ant.Project;   
import org.apache.tools.ant.taskdefs.Zip;   
import org.apache.tools.ant.types.FileSet;   

import com.eyoubika.common.YbkException;
  
public class ZipUtil {   
  
  
       
    public static void compress(String dstFile, String srcPathName) {
    	File zipFile = new File(dstFile) ;   
        File srcdir = new File(srcPathName);   
        if (!srcdir.exists())   
            throw new RuntimeException(srcPathName + "不存在！");   
           
        Project prj = new Project();   
        Zip zip = new Zip();   
        zip.setProject(prj);   
        zip.setDestFile(zipFile);   
        FileSet fileSet = new FileSet();   
        fileSet.setProject(prj);   
        fileSet.setDir(srcdir);   
        //fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹 eg:zip.setIncludes("*.java");   
        //fileSet.setExcludes(...); 排除哪些文件或文件夹   
        zip.addFileset(fileSet);   
           
        zip.execute();   
    }   
    public static void compress(String dstFile, String srcPathName, String date) {
    	File zipFile = new File(dstFile) ;   
        File srcdir = new File(srcPathName);   
        if (!srcdir.exists()) {
        	throw new YbkException(YbkException.CODE970011, YbkException.DESC970011 + ": " + srcPathName );
        } 
        Project prj = new Project();   
        Zip zip = new Zip();   
        zip.setProject(prj);   
        zip.setDestFile(zipFile);   
        FileSet fileSet = new FileSet();   
        fileSet.setProject(prj);   
        fileSet.setDir(srcdir);   
        fileSet.setIncludes( date +"*.*"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");   
        //fileSet.setExcludes(...); 排除哪些文件或文件夹   
        zip.addFileset(fileSet);   
           
        zip.execute();   
    }   
}  
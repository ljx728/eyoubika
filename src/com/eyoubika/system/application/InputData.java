package com.eyoubika.system.application;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eyoubika.system.dao.*;
import com.eyoubika.system.domain.*;
import com.eyoubika.util.CommonUtil;
public class InputData{
	
	private static String fileDir = CommonUtil.PROJECTSRC_DIR +"com/eyoubika/system/application/";
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		inputCity
	 * Author:		1.0 created by lijiaxuan at 2015年7月14日 下午10:51:15
	 *--------------------------------------------------------------------------------------*/
	public static void inputCity(){
		CityInfoDomain cityInfoDomain = new CityInfoDomain();
		CityInfoDao cityInfoDao = new CityInfoDao();
		String fromContent = CommonUtil.fromFile("city.txt", fileDir);
		String[] lines = fromContent.split("\n");
		for (int i = 0; i < lines.length; i++){
			lines[i] = lines[i].trim().replaceAll("\\s+", " ");
			String[] pix = lines[i].split(",");
			String cityId = pix[0].split(":")[1].replace("\"", "");
			String cityName = pix[1].split(":")[1].replace("\"", "");
			String proId = pix[2].split(":")[1].replace("\"", "").replace("}","");
			cityInfoDomain.setCityId(cityId.trim());
			cityInfoDomain.setCityName(cityName.trim());
			cityInfoDomain.setProId(proId.trim());
			cityInfoDao.addCityInfo(cityInfoDomain);
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		inputProvince
	 * Author:		1.0 created by lijiaxuan at 2015年7月14日 下午10:51:11
	 *--------------------------------------------------------------------------------------*/
	public static void inputProvince(){
		ProvinceInfoDomain provinceInfoDomain = new ProvinceInfoDomain();
		ProvinceInfoDao provinceInfoDao = new ProvinceInfoDao();
		String fromContent = CommonUtil.fromFile("province.txt", fileDir);
		String[] lines = fromContent.split("\n");
		for (int i = 0; i < lines.length; i++){
			lines[i] = lines[i].trim().replaceAll("\\s+", " ");
			String[] pix = lines[i].split(",");
			String provinceId = pix[0].split(":")[1].replace("\"", "");
			String provinceName = pix[1].split(":")[1].replace("\"", "").replace("}","");			
			provinceInfoDomain.setProId(provinceId.trim());
			provinceInfoDomain.setProName(provinceName.trim());		
			provinceInfoDao.addProvinceInfo(provinceInfoDomain);
		}
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		inputBank
	 * Author:		1.0 created by lijiaxuan at 2015年7月15日 下午7:42:17
	 *--------------------------------------------------------------------------------------*/
	public static void inputBank(){
		BankInfoDomain bankInfoDomain = new BankInfoDomain();
		BankInfoDao bankInfoDao = new BankInfoDao();
		String fromContent = CommonUtil.fromFile("bank.txt", fileDir);
		String[] lines = fromContent.split("\n");
		for (int i = 0; i < lines.length; i++){
			lines[i] = lines[i].trim().replaceAll("\\s+", " ");
			String[] pix = lines[i].split(",");
			String bankId = pix[0].split(":")[1].replace("\"", "");
			String bankName = pix[1].split(":")[1].replace("\"", "").replace("}","");			
			bankInfoDomain.setBankId(bankId.trim());
			bankInfoDomain.setBankName(bankName.trim());		
			bankInfoDao.addBankInfo(bankInfoDomain);
		}
	}
	
	public static void main(String[] params){
		inputCity();
		inputProvince();
		inputBank();
	}
}
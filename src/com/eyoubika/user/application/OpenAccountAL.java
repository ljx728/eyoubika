package com.eyoubika.user.application;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.PageInfo;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.util.ReportUtil;
import com.eyoubika.util.ZipUtil;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.OpenAccountPersDomain;
import com.eyoubika.user.dao.OpenAccountPersDao;
import com.eyoubika.user.domain.OpenAccountCorpDomain;
import com.eyoubika.user.dao.OpenAccountCorpDao;
import com.eyoubika.user.domain.OpenAccountDetailDomain;
import com.eyoubika.user.domain.OpenAccountBriefDomain;
import com.eyoubika.user.domain.UserBasicDomain;
import com.eyoubika.user.dao.OpenAccountDetailDao;
import com.eyoubika.user.web.VO.QueryOpenAccountVO;
import com.eyoubika.spider.domain.NewsBasicDomain;
import com.eyoubika.system.dao.ProvinceInfoDao;
import com.eyoubika.system.dao.CityInfoDao;
import com.eyoubika.system.dao.BankInfoDao;
import com.eyoubika.sbc.dao.ExchangeBasicDao;
import com.eyoubika.system.domain.ProvinceInfoDomain;
import com.eyoubika.system.domain.CityInfoDomain;
import com.eyoubika.system.domain.BankInfoDomain;
import com.eyoubika.sbc.domain.ExchangeBasicDomain;
import com.eyoubika.system.application.ImageAL;
public class OpenAccountAL{
	private OpenAccountPersDao openAccountPersDao;	//
	private OpenAccountCorpDao openAccountCorpDao;	//
	private OpenAccountDetailDao openAccountDetailDao;	//
	
	
	public OpenAccountPersDao getOpenAccountPersDao(){
		return openAccountPersDao;
	}
	public void setOpenAccountPersDao(OpenAccountPersDao openAccountPersDao){
		this.openAccountPersDao = openAccountPersDao;
	}
	public OpenAccountCorpDao getOpenAccountCorpDao(){
		return openAccountCorpDao;
	}
	public void setOpenAccountCorpDao(OpenAccountCorpDao openAccountCorpDao){
		this.openAccountCorpDao = openAccountCorpDao;
	}
	public OpenAccountDetailDao getOpenAccountDetailDao(){
		return openAccountDetailDao;
	}
	public void setOpenAccountDetailDao(OpenAccountDetailDao openAccountDetailDao){
		this.openAccountDetailDao = openAccountDetailDao;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public void addOpenAccount( ){
		
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public void getOpenAccount( ){
		
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public void modifyOpenAccount( ){
		
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public PageInfo getOpenAccountList(OpenAccountBriefDomain openAccountBriefDomain, PageInfo pageInfo){
		System.out.println("0  newsAL getNewsList");		
		return openAccountPersDao.queryOpenAccountInPage(openAccountBriefDomain, pageInfo);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public String addPersAccount(OpenAccountPersDomain openAccountPersDomain){
		String openNo = CommonUtil.buildTransNo(CommonVariables.TRANS_OPEN_ACCOUNT);
		openAccountPersDomain.setOpenNo(openNo);
		openAccountPersDomain.setApplyDate(CommonUtil.getNowDate());
		openAccountPersDomain.setApplyTime(CommonUtil.getNowTime());
		openAccountPersDomain.setStatus(CommonVariables.OPEN_ACCOUNT_UNCHEKED);
		openAccountPersDao.addOpenAccountPers(openAccountPersDomain);
		return openNo;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public String addCorpAccount(OpenAccountCorpDomain openAccountCorpDomain){
		String openNo = CommonUtil.buildTransNo(CommonVariables.TRANS_OPEN_ACCOUNT);
		openAccountCorpDomain.setOpenNo(openNo);
		openAccountCorpDomain.setApplyDate(CommonUtil.getNowDate());
		openAccountCorpDomain.setApplyTime(CommonUtil.getNowTime());
		openAccountCorpDomain.setStatus(CommonVariables.OPEN_ACCOUNT_UNCHEKED);
		openAccountCorpDao.addOpenAccountCorp(openAccountCorpDomain);
		return openNo;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountPersDomain getPersAccountByDomain(OpenAccountPersDomain openAccountPersDomain){
		return openAccountPersDao.findOpenAccountPersByDomain(openAccountPersDomain);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountPersDomain getPersAccount(String openNo){
		return openAccountPersDao.findOpenAccountPers(openNo);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getCorpAccount
	 * Author:		1.0 created by lijiaxuan at 2015年7月17日 下午6:18:20
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountCorpDomain getCorpAccount(String openNo){
		return openAccountCorpDao.findOpenAccountCorp(openNo);
	}
	
	public String getOpenAccountIamges(String openNo){
		List<String> fileNames = CommonUtil.getFileNames(CommonUtil.TRANS_IMG_DIR);
		int listSize = fileNames.size();
		for (int i = 0 ; i < listSize; i++){
			//if (fileNames[i])
		}
		return null;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public OpenAccountCorpDomain getCorpAccountByDomain(OpenAccountCorpDomain openAccountCorpDomain){
		return openAccountCorpDao.findOpenAccountCorpByDomain(openAccountCorpDomain);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public void modifyCorpAccount(OpenAccountCorpDomain openAccountCorpDomain){
		openAccountCorpDao.modifyOpenAccountCorp(openAccountCorpDomain);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public void modifyPersAccount(OpenAccountPersDomain openAccountPersDomain){
		openAccountPersDao.modifyOpenAccountPers(openAccountPersDomain);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public void modifyAccountDetail(OpenAccountDetailDomain openAccountDetailDomain){
		
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public void uploadAccountDetail(OpenAccountDetailDomain openAccountDetailDomain){
		
	}
	
	//>. <CustomFileTag>
	public void uploadAccountImage(HttpServletRequest request){
		ImageAL imageAL = new ImageAL();
		String batchNo = imageAL.uploadImage(request);
		imageAL = null;
	}
	
	public void downloadAccountReport(String applyTime){
		ImageAL imageAL = new ImageAL();
		//String batchNo = imageAL.uploadImage(request);
		//System.out.println("batchNoaaaaaaaaa: "+batchNo);
		imageAL = null;
	}
	
	public String createReport(OpenAccountPersDomain openAccountPersDomain){
		String logt = "openAccountPersDomain: " + openAccountPersDomain.toString() + "\n";
		CommonUtil.toLog(logt);
		String date = openAccountPersDomain.getApplyDate();
		openAccountPersDomain.init();
		openAccountPersDomain.setApplyDate(date);
		//openAccountPersDomain.setUserId(CommonVariables.USERID_INITIAL);	//默认是userId = 0；
		
		CityInfoDao cityInfoDao = new CityInfoDao();
		ProvinceInfoDao provinceInfoDao = new ProvinceInfoDao();
		BankInfoDao bankInfoDao = new BankInfoDao();
		ExchangeBasicDao exchangeBasicDao = new ExchangeBasicDao();
		String name = "OPAC" + openAccountPersDomain.getApplyDate() + ".xls";
		List<OpenAccountPersDomain> reportDomains = new ArrayList<OpenAccountPersDomain>();
		List<OpenAccountPersDomain> allDomains = new ArrayList<OpenAccountPersDomain>();
		//openAccountPersDomain.setApplyTime(null);
		reportDomains = openAccountPersDao.queryOpenAccountPers(openAccountPersDomain);
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		list.add(buildHeader());
		if (reportDomains.size() <= 0){
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		for (int i = 0; i < reportDomains.size(); i++){
			OpenAccountPersDomain domain = new OpenAccountPersDomain();
			if (reportDomains.get(i).getBankId() != null && reportDomains.get(i).getExId() != null ){		
				if (reportDomains.get(i).getBankId().contains(",") && reportDomains.get(i).getExId().contains(",") ){
					domain = null;
					String[] bankIds = reportDomains.get(i).getBankId().split(",");
					String[] exIds = reportDomains.get(i).getExId().split(",");
					
					for (int j = 0; j < bankIds.length; j++){
						OpenAccountPersDomain domainIn = new OpenAccountPersDomain();
						ConverterUtil.DomainToDomain(reportDomains.get(i), domainIn);
						domainIn.setBankId(bankIds[j]);
						domainIn.setExId(exIds[j]);
						allDomains.add( domainIn);
					}
				} else {
					ConverterUtil.DomainToDomain(reportDomains.get(i), domain);
					allDomains.add( domain);
				}
			}
		}
		
		for (int i = 0; i < allDomains.size(); i++){
			OpenAccountPersDomain domain = allDomains.get(i);
			//CityInfoDomain cityInfoDomain = cityInfoDao.findCityInfo(domain.getCity());
			
			ProvinceInfoDomain provinceInfoDomain = provinceInfoDao.findProvinceInfo(domain.getProvince());
			if (provinceInfoDomain!=null){
				domain.setProvince(provinceInfoDomain.getProName());
			}
			
			CityInfoDomain cityInfoDomain = cityInfoDao.findCityInfo(domain.getCity());
			if (cityInfoDomain!=null){
				domain.setCity(cityInfoDomain.getCityName());
			}
			
			BankInfoDomain bankInfoDomain = bankInfoDao.findBankInfo(domain.getBankId());
			if (bankInfoDomain!=null){
				domain.setBankId(bankInfoDomain.getBankName());
			}
		
			ExchangeBasicDomain ExchangeBasicDomain = exchangeBasicDao.findExchangeBasic(domain.getExId());
			if (ExchangeBasicDomain!=null){
				domain.setExId(ExchangeBasicDomain.getExName());
			}	
			//解释渠道，默认苹果
			if (domain.getChannel()==null || domain.getChannel().equals(CommonVariables.OPEN_CHANNEL_APPIOS)) {
				domain.setChannel("苹果");
			} else if (domain.getChannel().equals(CommonVariables.OPEN_CHANNEL_APPAND)) {
				domain.setChannel("安卓");
			} else if (domain.getChannel().equals(CommonVariables.OPEN_CHANNEL_WEB)) {
				domain.setChannel("网站");
			} else if (domain.getChannel().equals(CommonVariables.OPEN_CHANNEL_WECHART)) {
				domain.setChannel("微信");
			} 
			list.add(buildBody(domain));						
		}	
		OutputStream f = null;
		try {
			//f = new FileOutputStream("/Users/lijiaxuan/ljx728/" + name);
			f = new FileOutputStream(CommonUtil.TRANS_REPORT_DIR + name);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReportUtil.createExcel(f, list, null);
		System.out.println("name " +  name);
		return name;
	}
	
	public ArrayList<String> buildHeader( ){
		ArrayList<String> strArray = new ArrayList<String>();
		strArray.add("开户编号");
		strArray.add("姓名");
		strArray.add("手机号");
		strArray.add("邮箱");
		strArray.add("身份证");
		strArray.add("交易所");
		strArray.add("开户行");
		strArray.add("开户账号");
		strArray.add("开户行地址");
		strArray.add("身份");
		strArray.add("城市");
		strArray.add("街道");
		strArray.add("渠道");
		return strArray;
	}
	public ArrayList<String> buildBody( OpenAccountPersDomain openAccountPersDomain){
		ArrayList<String> strArray = new ArrayList<String>();

			strArray.add(openAccountPersDomain.getOpenNo());
			strArray.add(openAccountPersDomain.getClientName());
			strArray.add(openAccountPersDomain.getPnumber());
			strArray.add(openAccountPersDomain.getEmail());
			strArray.add(openAccountPersDomain.getIdentifyNumber());		
			strArray.add(openAccountPersDomain.getExId());
			strArray.add(openAccountPersDomain.getBankId());
			strArray.add(openAccountPersDomain.getBankAccount());
			strArray.add(openAccountPersDomain.getBankPlace());
			strArray.add(openAccountPersDomain.getProvince());
			strArray.add(openAccountPersDomain.getCity());
			strArray.add(openAccountPersDomain.getStreet());
			strArray.add(openAccountPersDomain.getChannel());
			
		return strArray;
	}
	
	public String createImageZip(OpenAccountPersDomain openAccountPersDomain){
		String name = "OPAC" + openAccountPersDomain.getApplyDate() + ".zip";
		String dstName = CommonUtil.TRANS_REPORT_DIR + name;
		ZipUtil.compress(dstName,  CommonUtil.TRANS_IMG_DIR, openAccountPersDomain.getApplyDate());
		return name;
	}
	
	public InputStream  getAccountReport(String fileName){
		String srcFile=CommonUtil.TRANS_REPORT_DIR +  fileName;
		File file=new File(srcFile);//得到一个file对象
		InputStream report = null;
		try {
			report = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return report;//返回一个文件输入流
	}
	
	public InputStream  getAccountZip(String fileName){
		String srcFile=CommonUtil.TRANS_REPORT_DIR +  fileName;
		File file=new File(srcFile);//得到一个file对象
		InputStream report = null;
		try {
			report = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return report;//返回一个文件输入流
	}
	//>. <CustomFileTag>
}

package com.eyoubika.user.application;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.PageInfo;
import com.eyoubika.sbc.dao.SbcPriceDao;
import com.eyoubika.sbc.domain.SbcPriceDomain;
import com.eyoubika.spider.dao.QuotesRedisDao;
import com.eyoubika.spider.dao.SbcMapRedisDao;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.common.YbkException;
import com.eyoubika.user.domain.FavoriteExsDomain;
import com.eyoubika.user.domain.FavoriteSbcsDomain;
import com.eyoubika.user.dao.FavoriteExsDao;
import com.eyoubika.user.dao.FavoriteSbcsDao;
import com.eyoubika.info.domain.QuotationsDomain;

public class FavorListAL{
	private FavoriteSbcsDao favoriteSbcsDao;	//
	private FavoriteExsDao favoriteExsDao;
	//private SbcPriceDao sbcPriceDao;
	private QuotesRedisDao quotesRedisDao;
	private SbcMapRedisDao sbcMapRedisDao;
	
	
	
	public FavoriteExsDao getFavoriteExsDao() {
		return favoriteExsDao;
	}


	public void setFavoriteExsDao(FavoriteExsDao favoriteExsDao) {
		this.favoriteExsDao = favoriteExsDao;
	}


	public SbcMapRedisDao getSbcMapRedisDao() {
		return sbcMapRedisDao;
	}


	public void setSbcMapRedisDao(SbcMapRedisDao sbcMapRedisDao) {
		this.sbcMapRedisDao = sbcMapRedisDao;
	}


	public FavoriteSbcsDao getFavoriteSbcsDao() {
		return favoriteSbcsDao;
	}


	public void setFavoriteSbcsDao(FavoriteSbcsDao favoriteSbcsDao) {
		this.favoriteSbcsDao = favoriteSbcsDao;
	}


	public QuotesRedisDao getQuotesRedisDao() {
		return quotesRedisDao;
	}


	public void setQuotesRedisDao(QuotesRedisDao quotesRedisDao) {
		this.quotesRedisDao = quotesRedisDao;
	}


	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-15 21:09:48
	 *--------------------------------------------------------------------------------------*/
	public void addFavoriteSbc(FavoriteSbcsDomain favoriteSbcsDomain){
		favoriteSbcsDomain.setDate(CommonUtil.getNowDate());
		favoriteSbcsDomain.setTime(CommonUtil.getNowTime());
		favoriteSbcsDao.addFavoriteSbcs(favoriteSbcsDomain);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		addFavoriteSbcs
	 * Author:		1.0 created by lijiaxuan at 2015年10月11日 下午10:56:00
	 *--------------------------------------------------------------------------------------*/
	public void addFavoriteSbcs( String userIdStr, String sbcList, FavoriteSbcsDomain favoriteSbcsDomain){
		Integer userId  = Integer.parseInt(userIdStr);
		favoriteSbcsDomain.init();
		if (sbcList.equals("0")){
			favoriteSbcsDao.deleteFavoriteSbcs(userId);
		} else {
			String[] sbcArray = sbcList.split(",");
			if (sbcArray.length > CommonVariables.FAVORITE_LISTITEM_MAX) {
				throw new YbkException(YbkException.CODE900002, YbkException.DESC900002);
			}  
			//1. 删除
			favoriteSbcsDao.deleteFavoriteSbcs(userId);
			//2. 添加			
			favoriteSbcsDomain.setUserId(userId);
			favoriteSbcsDomain.setDate(CommonUtil.getNowDate());
			for (int i = 0 ; i < sbcArray.length; i++){
				
				favoriteSbcsDomain.setTime(""+i);
				favoriteSbcsDomain.setSbcId(Integer.parseInt(sbcArray[i]));
			
				favoriteSbcsDao.addFavoriteSbcs(favoriteSbcsDomain);
			}	
		}
		
	}
	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		deleteFavoriteSbc
	 * Author:		1.0 created by lijiaxuan at 2015年7月29日 上午10:09:34
	 *--------------------------------------------------------------------------------------*/
	public void deleteFavoriteSbc(FavoriteSbcsDomain favoriteSbcsDomain){		
		favoriteSbcsDao.deleteFavoriteSbcsByDomain(favoriteSbcsDomain);
	}
	
	
	
	/*public List<SbcPriceDomain> syncFavoriteSbcs(String clientSbcIds, String clientTimestamp, int userId){//FavoriteSbcsDomain favoriteSbcsDomain
		List<FavoriteSbcsDomain> serverDomains = favoriteSbcsDao.queryFavoriteSbcs(favoriteSbcsDomain)
		String cresSbcIds = "";
		String sresSbcIds = "";
		String type = "";
		
		//0. 服务器端没有数据，则需要插入
		if (serverDomains == null){
		}	

		String[] cs = clientSbcIds.split(",");
		int csSize = cs.length;
		int serverDomainsSize = serverDomains.size();
		for (int i = 0 ; i < serverDomainsSize; i++){
			//时间戳较新的需要保留
			if (serverDomains.get(i).getTimestamp().compareTo(clientTimestamp) > 0) {
				cresSbcIds += serverDomains.get(i).getSbcId();
			}
		}
		
		for (int i = 0; i < csSize; i++){
			
		}
		
		//服务器时间较新：覆盖客户端
		if (favoriteSbcsDomain.getTimestamp().compareTo(serverDomain.getTimestamp()) < 0){
			
			//客户端时间较新：覆盖服务器端
		} else {
			serverSbcIds = favoriteSbcsDomain.getSbcId();
			
		}
		
		
		if (favoriteSbcsDomain.getSbcId().equals("")) {
			//1. 客户端空，服务器空
			if (serverDomain.getSbcId().equals("") ){
				//clientSbcIds = "";
			} else {
				//2. 客户端空，服务器非空, 服务器时间较新：覆盖客户端
				if (favoriteSbcsDomain.getTimestamp().compareTo(serverDomain.getTimestamp()) < 0){
					clientSbcIds = serverDomain.getSbcId();
					type = CommonVariables.SYNC_SIDE_CLIENT;
					//3. 客户端空，服务器非空, 客户端时间较新：覆盖服务器端
				} else if (favoriteSbcsDomain.getTimestamp().compareTo(serverDomain.getTimestamp()) > 0){
					serverSbcIds = "";
					type = CommonVariables.SYNC_SIDE_SERVER;
				}
			}
		} else {
			if (serverDomain.getSbcId().equals("") ){
				//clientSbcIds = "";
				//2. 客户端非空，服务器空, 服务器时间较新：覆盖客户端
				if (favoriteSbcsDomain.getTimestamp().compareTo(serverDomain.getTimestamp()) < 0){
					clientSbcIds = "";
					type = CommonVariables.SYNC_SIDE_CLIENT;
					//3. 客户端非空，服务器空, 客户端时间较新：覆盖服务器端
				} else if (favoriteSbcsDomain.getTimestamp().compareTo(serverDomain.getTimestamp()) > 0){
					serverSbcIds = favoriteSbcsDomain.getSbcId();
				}
			} else {
				//2. 客户端非空，服务器非空, 服务器时间较新
				if (favoriteSbcsDomain.getTimestamp().compareTo(serverDomain.getTimestamp()) < 0){
					String[] cs = favoriteSbcsDomain.getSbcId().split(",");
					String[] ss = serverDomain.getSbcId();
					clientSbcIds = "";
					type = CommonVariables.SYNC_SIDE_CLIENT;
					//3. 客户端非空，服务器非空, 客户端时间较新
				} else if (favoriteSbcsDomain.getTimestamp().compareTo(serverDomain.getTimestamp()) > 0){
					serverSbcIds = favoriteSbcsDomain.getSbcId();
				}
			}
		}				
		//favoriteSbcsDao.addFavoriteSbcs(favoriteSbcsDomain);
		
	}
	*/
	//>. <CustomFileTag>
	public List<String>  getFavoriteSbcListByUserId(Integer userId){
		List<FavoriteSbcsDomain> sbcList= favoriteSbcsDao.queryFavoriteSbcsByUserId(userId);
		
		List<Integer> sbcIdList = new ArrayList<Integer>();
		int size = sbcList.size();
		//String sbcIdList = "";
		for (int i = 0; i < size; i++){  
			sbcIdList.add(sbcList.get(i).getSbcId());
		}
		//map.put("userId", userId);
		//map.put("sbcIdList", sbcIdList);
		return  quotesRedisDao.querySbcPriceBySbcList(sbcIdList, sbcMapRedisDao);
		//return sbcPriceDao.querySbcPriceBySbcList(map);	
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		deleteFavoriteSbcs
	 * Author:		1.0 created by lijiaxuan at 2015年7月29日 上午10:09:21
	 *--------------------------------------------------------------------------------------*/
	public void deleteFavoriteSbcs(Integer userId, String sbcIds){
		String[] sbcArray = sbcIds.split(",");
		Map<String,Object> map = new HashMap<String, Object>();
		List<Integer> sbcIdList = new ArrayList<Integer>();
		int size = sbcArray.length;
		//String sbcIdList = "";
		for (int i = 0; i < size; i++){  
			if (!sbcArray[i].equals("")){
				sbcIdList.add(Integer.parseInt(sbcArray[i]));
			}		
		}

		map.put("userId", userId);
		map.put("sbcIdList", sbcIdList);
		favoriteSbcsDao.deleteFavoriteSbcsListBySbcIdLIst( map);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		addFavoriteEx
	 * Author:		1.0 created by lijiaxuan at 2015年9月5日 上午11:45:03
	 *--------------------------------------------------------------------------------------*/
	public void addFavoriteEx(FavoriteExsDomain favoriteExsDomain){
		favoriteExsDomain.setDate(CommonUtil.getNowDate());
		favoriteExsDomain.setTime(CommonUtil.getNowTime());
		favoriteExsDao.addFavoriteExs(favoriteExsDomain);
	}
	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		addFavoriteExs
	 * Author:		1.0 created by lijiaxuan at 2015年10月11日 下午11:03:40
	 *--------------------------------------------------------------------------------------*/
	public void addFavoriteExs( String userIdStr, String exList, FavoriteExsDomain favoriteExsDomain){
		Integer userId  = Integer.parseInt(userIdStr);
		favoriteExsDomain.init();
		if (exList.equals("0")){
			favoriteExsDao.deleteFavoriteExs(userId);
		} else {
			String[] exArray = exList.split(",");
			
			if (exArray.length > CommonVariables.FAVORITE_LISTITEM_MAX) {
				throw new YbkException(YbkException.CODE900002, YbkException.DESC900002);
			}  
			//1. 删除
			favoriteExsDao.deleteFavoriteExs(userId);
			//2. 添加
			favoriteExsDomain.setUserId(userId);	
			favoriteExsDomain.setDate(CommonUtil.getNowDate());
			for (int i = 0 ; i < exArray.length; i++){				
				favoriteExsDomain.setTime(""+i);
				favoriteExsDomain.setExId(exArray[i]);
				favoriteExsDao.addFavoriteExs(favoriteExsDomain);
			}	
		}
		
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		deleteFavoriteEx
	 * Author:		1.0 created by lijiaxuan at 2015年9月5日 上午11:43:44
	 *--------------------------------------------------------------------------------------*/
	public void deleteFavoriteEx(FavoriteExsDomain favoriteExsDomain){		
		favoriteExsDao.deleteFavoriteExsByDomain(favoriteExsDomain);
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getFavoriteExListByUserId
	 * Author:		1.0 created by lijiaxuan at 2015年9月5日 上午11:56:29
	 *--------------------------------------------------------------------------------------*/
	public List<String>  getFavoriteExListByUserId(Integer userId){
		List<FavoriteExsDomain> exList= favoriteExsDao.queryFavoriteExsByUserId(userId);
		
		List<String> exIdList = new ArrayList<String>();
		int size = exList.size();
		//String sbcIdList = "";
		for (int i = 0; i < size; i++){  
			exIdList.add(exList.get(i).getExId());
		}
		return exIdList;
	}

	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		deleteFavoriteSbcs
	 * Author:		1.0 created by lijiaxuan at 2015年7月29日 上午10:09:21
	 *--------------------------------------------------------------------------------------*/
	public void deleteFavoriteExs(Integer userId, String exIds){
		String[] exIdArray = exIds.split(",");
		Map<String,Object> map = new HashMap<String, Object>();
		List<String> exIdList = new ArrayList<String>();
		int size = exIdArray.length;
		//String sbcIdList = "";
		for (int i = 0; i < size; i++){  
			if (!exIdArray[i].equals("")){
				exIdList.add(exIdArray[i]);
			}		
		}
		
		map.put("userId", userId);
		map.put("exIdList", exIdList);
		favoriteExsDao.deleteFavoriteExsByExIds( map);
	}
	//>. <CustomFileTag>
	
}

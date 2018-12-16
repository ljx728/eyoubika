package com.eyoubika.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eyoubika.common.BaseService;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.YbkException;
import com.eyoubika.sbc.domain.SbcPriceDomain;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.user.web.VO.FavoriteListVO;
import com.eyoubika.user.domain.FavoriteExsDomain;
import com.eyoubika.user.domain.FavoriteSbcsDomain;
import com.eyoubika.user.service.IFavorListService;
import com.eyoubika.user.application.FavorListAL;
import com.eyoubika.info.domain.QuotationsDomain;
/*==========================================================================================*
 * Description:	定义了用户服务的接口实现
 * Class:		FavorListServiceImpl
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-23 13:54:53
 *==========================================================================================*/
public class FavorListServiceImpl extends BaseService implements IFavorListService {
	private FavorListAL favorListAL;
	private FavoriteSbcsDomain favoriteSbcsDomain;
	private FavoriteExsDomain favoriteExsDomain;

	
	public FavoriteExsDomain getFavoriteExsDomain() {
		return favoriteExsDomain;
	}

	public void setFavoriteExsDomain(FavoriteExsDomain favoriteExsDomain) {
		this.favoriteExsDomain = favoriteExsDomain;
	}

	public FavorListAL getFavorListAL() {
		return favorListAL;
	}

	public void setFavorListAL(FavorListAL favorListAL) {
		this.favorListAL = favorListAL;
	}

	public FavoriteSbcsDomain getFavoriteSbcsDomain() {
		return favoriteSbcsDomain;
	}

	public void setFavoriteSbcsDomain(FavoriteSbcsDomain favoriteSbcsDomain) {
		this.favoriteSbcsDomain = favoriteSbcsDomain;
	}

	
	public Map<String, Object> addFavoriteSbc(FavoriteListVO favoriteListVO){
		favoriteSbcsDomain.init();
		ConverterUtil.VOToDomain(favoriteListVO, favoriteSbcsDomain);
		favorListAL.addFavoriteSbc(favoriteSbcsDomain);
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken(),  favoriteListVO.getSbcId());
	}
	
	public Map<String, Object> addFavoriteSbcs(FavoriteListVO favoriteListVO){
		favoriteSbcsDomain.init();
		favorListAL.addFavoriteSbcs(favoriteListVO.getUserId(), favoriteListVO.getSbcId(), favoriteSbcsDomain);
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken());
	}	
	
	public Map<String, Object> deleteFavoriteSbc(FavoriteListVO favoriteListVO){
		favoriteSbcsDomain.init();
		ConverterUtil.VOToDomain(favoriteListVO, favoriteSbcsDomain);
		favorListAL.deleteFavoriteSbc(favoriteSbcsDomain);
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken(),  favoriteListVO.getSbcId());
	}
	
	public Map<String, Object> deleteFavoriteSbcs(FavoriteListVO favoriteListVO){

		favorListAL.deleteFavoriteSbcs(Integer.parseInt(favoriteListVO.getUserId()), favoriteListVO.getSbcId());
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken(), favoriteListVO.getSbcId());
	}
	
	public  Map<String, Object>  getFavoriteSbcList(FavoriteListVO favoriteListVO){
		Integer userId = Integer.parseInt(favoriteListVO.getUserId());
		//List<QuotationsDomain> sbcDomainList = favorListAL.getFavoriteSbcListByUserId(userId);
		List<String> sbcDomainList = favorListAL.getFavoriteSbcListByUserId(userId);
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken(), sbcDomainList);
	}

	public Map<String, Object> addFavoriteEx(FavoriteListVO favoriteListVO){
		favoriteExsDomain.init();
		ConverterUtil.VOToDomain(favoriteListVO, favoriteExsDomain);
		favorListAL.addFavoriteEx(favoriteExsDomain);
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken(), favoriteListVO.getExId());
	}
	
	public Map<String, Object> addFavoriteExs(FavoriteListVO favoriteListVO){
		favoriteExsDomain.init();
		favorListAL.addFavoriteExs(favoriteListVO.getUserId(), favoriteListVO.getExId(), favoriteExsDomain);
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken());
	}	 
	public Map<String, Object> deleteFavoriteEx(FavoriteListVO favoriteListVO){
		favoriteExsDomain.init();
		ConverterUtil.VOToDomain(favoriteListVO, favoriteExsDomain);
		favorListAL.deleteFavoriteEx(favoriteExsDomain);
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken());
	}
	
	public Map<String, Object> deleteFavoriteExs(FavoriteListVO favoriteListVO){

		favorListAL.deleteFavoriteExs(Integer.parseInt(favoriteListVO.getUserId()), favoriteListVO.getExId());
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken(), favoriteListVO.getExId());
	}
	
	public  Map<String, Object>  getFavoriteExList(FavoriteListVO favoriteListVO){
		Integer userId = Integer.parseInt(favoriteListVO.getUserId());
		//List<QuotationsDomain> sbcDomainList = favorListAL.getFavoriteSbcListByUserId(userId);
		List<String> exIdList = favorListAL.getFavoriteExListByUserId(userId);
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken(), exIdList);
	}
	
	/*
	public Map<String, Object> syncFavoriteSbcs(FavoriteListVO favoriteListVO){
		//favoriteSbcsDomain.init();
		ConverterUtil.VOToDomain(favoriteListVO, favoriteSbcsDomain);
		List<FavoriteSbcsDomain>  domainList = new ArrayList<FavoriteSbcsDomain>();
		String[] sbcIds = favoriteListVO.getSbcIds().split(",");
		int sbcIdsSize = sbcIds.length;
		for (int i = 0; i < sbcIdsSize; i++) {
			FavoriteSbcsDomain favoriteSbcsDomain = new FavoriteSbcsDomain();
			favoriteSbcsDomain.setUserId(Integer.parseInt(favoriteListVO.getUserId()));
			favoriteSbcsDomain.setSbcId(Integer.parseInt(sbcIds[i]));
			favoriteSbcsDomain.setTimestamp(CommonUtil.getTimestamp());
			domainList.add(favoriteSbcsDomain);
		}
		
		favorListAL.syncFavoriteSbcs(favoriteSbcsDomain);
		return this.buildRetData(favoriteListVO.getUserId(), favoriteListVO.getToken());
	}*/
}
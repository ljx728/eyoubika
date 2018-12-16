package com.eyoubika.user.service;
import java.util.Map;

import com.eyoubika.user.web.VO.FavoriteListVO;

/*==========================================================================================*
 * Description:	定义用户服务接口
 * Class:		IUserService
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-23 13:52:23
 *==========================================================================================*/
public interface IFavorListService {
	
	//public Map<String, Object> syncFavoriteSbcs(FavoriteListVO favoriteListVO);
	public Map<String, Object> addFavoriteSbc(FavoriteListVO favoriteListVO);
	public Map<String, Object> addFavoriteSbcs(FavoriteListVO favoriteListVO);
	public Map<String, Object> deleteFavoriteSbc(FavoriteListVO favoriteListVO);
	public Map<String, Object> deleteFavoriteSbcs(FavoriteListVO favoriteListVO);
	public Map<String, Object> getFavoriteSbcList(FavoriteListVO favoriteListVO);
	public Map<String, Object> addFavoriteEx(FavoriteListVO favoriteListVO);
	public Map<String, Object> addFavoriteExs(FavoriteListVO favoriteListVO);
	public Map<String, Object> deleteFavoriteEx(FavoriteListVO favoriteListVO);
	public Map<String, Object> deleteFavoriteExs(FavoriteListVO favoriteListVO);
	public Map<String, Object> getFavoriteExList(FavoriteListVO favoriteListVO);
	
	
	
}
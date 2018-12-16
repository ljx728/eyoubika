package com.eyoubika.user.web.action;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;
import com.eyoubika.util.ConverterUtil;
import com.eyoubika.user.web.VO.FavoriteListVO;
import com.eyoubika.user.service.IFavorListService;

/*==========================================================================================*
 * Description:	定义了用户控制器
 * Class:		UserAction
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-21 14:17:33
 *==========================================================================================*/
public class FavorListAction extends BaseAction {

    private IFavorListService favorListService;	
    private FavoriteListVO favoriteListVO;
    
	public FavoriteListVO getFavoriteListVO() {
		return favoriteListVO;
	}
	public void setFavoriteListVO(FavoriteListVO favoriteListVO) {
		this.favoriteListVO = favoriteListVO;
	}
	public IFavorListService getFavorListService() {
		return favorListService;
	}
	public void setFavorListService(IFavorListService favorListService) {
		this.favorListService = favorListService;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		addFavoriteSbc
	 * Author:		1.0 created by lijiaxuan at 2015年7月29日 上午11:35:31
	 *--------------------------------------------------------------------------------------*/
	public String addFavoriteSbc(){	
		FavoriteListVO favoriteListVO = new FavoriteListVO();
		HttpServletRequest request = ServletActionContext.getRequest();		
		//> 1.check params userId
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, favoriteListVO);
		//> 3. do register
		this.jsonData = favorListService.addFavoriteSbc(favoriteListVO);
		//> 4. return json
		favoriteListVO = null;
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		addFavoriteSbcs
	 * Author:		1.0 created by lijiaxuan at 2015年10月11日 下午11:09:06
	 *--------------------------------------------------------------------------------------*/
	public String addFavoriteSbcs(){	
		FavoriteListVO favoriteListVO = new FavoriteListVO();
		HttpServletRequest request = ServletActionContext.getRequest();		
		//> 1.check params userId
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, favoriteListVO);
		//> 3. do register
		this.jsonData = favorListService.addFavoriteSbcs(favoriteListVO);
		//> 4. return json
		favoriteListVO = null;
		return SUCCESS;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		deleteFavoriteSbc
	 * Author:		1.0 created by lijiaxuan at 2015年7月29日 上午11:35:34
	 *--------------------------------------------------------------------------------------*/
	public String deleteFavoriteSbc(){	
		FavoriteListVO favoriteListVO = new FavoriteListVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params userId
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, favoriteListVO);
		//> 3. do register
		this.jsonData = favorListService.deleteFavoriteSbc(favoriteListVO);
		//> 4. return json
		favoriteListVO = null;
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		deleteFavoriteSbcs
	 * Author:		1.0 created by lijiaxuan at 2015年7月29日 上午11:35:58
	 *--------------------------------------------------------------------------------------*/
	public String deleteFavoriteSbcs(){	
		FavoriteListVO favoriteListVO = new FavoriteListVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params userId
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, favoriteListVO);
		//> 3. do register
		this.jsonData = favorListService.deleteFavoriteSbcs(favoriteListVO);
		//> 4. return json
		favoriteListVO = null;
		return SUCCESS;
	}
	
	public String getFavoriteSbcList(){	
		FavoriteListVO favoriteListVO = new FavoriteListVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params userId
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, favoriteListVO);
		//> 3. do register
		this.jsonData = favorListService.getFavoriteSbcList(favoriteListVO);
		//> 4. return json
		favoriteListVO = null;
		return SUCCESS;
	}

	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		addFavoriteExs
	 * Author:		1.0 created by lijiaxuan at 2015年10月11日 下午11:09:23
	 *--------------------------------------------------------------------------------------*/
	public String addFavoriteExs(){	
		FavoriteListVO favoriteListVO = new FavoriteListVO();
		HttpServletRequest request = ServletActionContext.getRequest();		
		//> 1.check params userId
		if (null == request.getParameter("userId") && null == request.getParameter("sbcId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, favoriteListVO);
		//> 3. do register
		this.jsonData = favorListService.addFavoriteExs(favoriteListVO);
		//> 4. return json
		favoriteListVO = null;
		return SUCCESS;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		addFavoriteEx
	 * Author:		1.0 created by lijiaxuan at 2015年9月5日 上午11:28:10
	 *--------------------------------------------------------------------------------------*/
	public String addFavoriteEx(){	
		FavoriteListVO favoriteListVO = new FavoriteListVO();
		HttpServletRequest request = ServletActionContext.getRequest();		
		//> 1.check params userId
		if (null == request.getParameter("userId") && null == request.getParameter("exId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, favoriteListVO);
		//> 3. do register
		this.jsonData = favorListService.addFavoriteEx(favoriteListVO);
		//> 4. return json
		favoriteListVO = null;
		return SUCCESS;
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		deleteFavoriteEx
	 * Author:		1.0 created by lijiaxuan at 2015年9月5日 上午11:27:59
	 *--------------------------------------------------------------------------------------*/
	public String deleteFavoriteEx(){	
		FavoriteListVO favoriteListVO = new FavoriteListVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params userId
		if (null == request.getParameter("userId") && null == request.getParameter("exId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, favoriteListVO);
		//> 3. do register
		this.jsonData = favorListService.deleteFavoriteEx(favoriteListVO);
		//> 4. return json
		favoriteListVO = null;
		return SUCCESS;
	}
	
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		deleteFavoriteExs
	 * Author:		1.0 created by lijiaxuan at 2015年9月5日 上午11:28:49
	 *--------------------------------------------------------------------------------------*/
	public String deleteFavoriteExs(){	
		FavoriteListVO favoriteListVO = new FavoriteListVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params userId
		if (null == request.getParameter("userId") && null == request.getParameter("exId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, favoriteListVO);
		//> 3. do register
		this.jsonData = favorListService.deleteFavoriteExs(favoriteListVO);
		//> 4. return json
		favoriteListVO = null;
		return SUCCESS;
	}
	
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getFavoriteExList
	 * Author:		1.0 created by lijiaxuan at 2015年9月5日 上午11:29:08
	 *--------------------------------------------------------------------------------------*/
	public String getFavoriteExList(){	
		FavoriteListVO favoriteListVO = new FavoriteListVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//> 1.check params userId
		if (null == request.getParameter("userId") && null == request.getParameter("exId")) {
			throw new YbkException(YbkException.CODE000003, YbkException.DESC000003);
		}		
		//> 2. build vo	
		ConverterUtil.RequestToVO(request, favoriteListVO);
		//> 3. do register
		this.jsonData = favorListService.getFavoriteExList(favoriteListVO);
		//> 4. return json
		favoriteListVO = null;
		return SUCCESS;
	}
}
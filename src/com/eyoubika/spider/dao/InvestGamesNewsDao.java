package com.eyoubika.spider.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.spider.domain.InvestGamesNewsDomain;
//>. <CustomImportTag>
//>. <CustomImportTag>
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-09-05 11:26:13
 *==========================================================================================*/
public class InvestGamesNewsDao extends BaseDao {
	String nameSpace = "ta_InvestGamesNews";
	private InvestGamesNewsDomain investGamesNewsDomain;	//<<attrNote>>
	public InvestGamesNewsDomain getInvestGamesNewsDomain(){
		return investGamesNewsDomain;
	}
	public void setInvestGamesNewsDomain(InvestGamesNewsDomain investGamesNewsDomain){
		this.investGamesNewsDomain = investGamesNewsDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-09-05 11:26:13
	 *--------------------------------------------------------------------------------------*/
	public int addInvestGamesNews(InvestGamesNewsDomain investGamesNewsDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertInvestGamesNews", investGamesNewsDomain);
		} catch (SQLException e) {
			ob = null;
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		insertId = Integer.parseInt(ob.toString());
		ob = null;
		return insertId;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-09-05 11:26:13
	 *--------------------------------------------------------------------------------------*/
	public int deleteInvestGamesNews(Integer newsId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteInvestGamesNews", newsId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-09-05 11:26:13
	 *--------------------------------------------------------------------------------------*/
	public int deleteAll(){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteAll");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-09-05 11:26:13
	 *--------------------------------------------------------------------------------------*/
	public void modifyInvestGamesNews(InvestGamesNewsDomain investGamesNewsDomain){
		try {
			sqlMapClient.update(nameSpace +".updateInvestGamesNews", investGamesNewsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-09-05 11:26:13
	 *--------------------------------------------------------------------------------------*/
	public InvestGamesNewsDomain findInvestGamesNews(Integer newsId){
		try {
			return (InvestGamesNewsDomain) sqlMapClient.queryForObject(nameSpace +".selectInvestGamesNews", newsId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-09-05 11:26:13
	 *--------------------------------------------------------------------------------------*/
	public InvestGamesNewsDomain findInvestGamesNewsByDomain(InvestGamesNewsDomain investGamesNewsDomain){
		try {
			return (InvestGamesNewsDomain) sqlMapClient.queryForObject(nameSpace +".selectInvestGamesNewsByDomain", investGamesNewsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-09-05 11:26:13
	 *--------------------------------------------------------------------------------------*/
	public List<InvestGamesNewsDomain> queryInvestGamesNews(InvestGamesNewsDomain investGamesNewsDomain){
		try {
			return (List<InvestGamesNewsDomain>) sqlMapClient.queryForList(nameSpace +".selectInvestGamesNewsList", investGamesNewsDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-09-05 11:26:13
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(InvestGamesNewsDomain investGamesNewsDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", investGamesNewsDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
	//>. <CustomFileTag>
	//>. <CustomFileTag>
}

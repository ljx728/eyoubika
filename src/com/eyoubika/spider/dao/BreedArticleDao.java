package com.eyoubika.spider.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.spider.domain.BreedArticleDomain;
/*==========================================================================================*
 * Description:	__Description__
 * Class:		__Class__
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-07-31 17:21:42
 *==========================================================================================*/
public class BreedArticleDao extends BaseDao {
	String nameSpace = "ta_FetchBreedArticle";
	private BreedArticleDomain breedArticleDomain;	//<<attrNote>>
	public BreedArticleDomain getBreedArticleDomain(){
		return breedArticleDomain;
	}
	public void setBreedArticleDomain(BreedArticleDomain breedArticleDomain){
		this.breedArticleDomain = breedArticleDomain;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:21:42
	 *--------------------------------------------------------------------------------------*/
	public void addBreedArticle(BreedArticleDomain breedArticleDomain){
		try {
			sqlMapClient.insert(nameSpace +".insertBreedArticle", breedArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:21:42
	 *--------------------------------------------------------------------------------------*/
	public int deleteBreedArticle(Integer articleId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteBreedArticle", articleId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:21:42
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
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:21:42
	 *--------------------------------------------------------------------------------------*/
	public void modifyBreedArticle(BreedArticleDomain breedArticleDomain){
		try {
			sqlMapClient.update(nameSpace +".updateBreedArticle", breedArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:21:42
	 *--------------------------------------------------------------------------------------*/
	public BreedArticleDomain findBreedArticle(Integer articleId){
		try {
			return (BreedArticleDomain) sqlMapClient.queryForObject(nameSpace +".selectBreedArticle", articleId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:21:42
	 *--------------------------------------------------------------------------------------*/
	public BreedArticleDomain findBreedArticleByDomain(BreedArticleDomain breedArticleDomain){
		try {
			return (BreedArticleDomain) sqlMapClient.queryForObject(nameSpace +".selectBreedArticleByDomain", breedArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:21:42
	 *--------------------------------------------------------------------------------------*/
	public List<BreedArticleDomain> queryBreedArticle(BreedArticleDomain breedArticleDomain){
		try {
			return (List<BreedArticleDomain>) sqlMapClient.queryForList(nameSpace +".selectBreedArticleList", breedArticleDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-07-31 17:21:42
	 *--------------------------------------------------------------------------------------*/
	public boolean isExistByDomain(BreedArticleDomain breedArticleDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", breedArticleDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
}

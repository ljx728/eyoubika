package com.eyoubika.info.application;
import com.eyoubika.common.PageInfo;
import com.eyoubika.info.domain.InvestArticleDomain;
import com.eyoubika.info.dao.InvestArticleDao;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.util.HtmlUtil;
//>. <CustomImportTag>
//>. <CustomImportTag>
public class InvestArticleAL{
	private InvestArticleDao investArticleDao;	//
	public InvestArticleDao getInvestArticleDao(){
		return investArticleDao;
	}
	public void setInvestArticleDao(InvestArticleDao investArticleDao){
		this.investArticleDao = investArticleDao;
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public void addInvestArticle(InvestArticleDomain investArticleDomain){
		String content = investArticleDomain.getContent();
		String htmlContent = HtmlUtil.buildInvestHtml(content, investArticleDomain.getTitle());
		investArticleDomain.setGenDate(CommonUtil.getNowDate());
		investArticleDomain.setGenTime(CommonUtil.getNowTime());	
		int articleId = investArticleDao.addInvestArticle(investArticleDomain);
		String fileName = articleId + ".html";
		String fileDir = CommonUtil.INVESTMENT_HTML_DIR;
		CommonUtil.toFile(htmlContent, fileName, fileDir);
		fileDir = CommonUtil.INVESTMENT_SEO_DIR;
		
		htmlContent = HtmlUtil.buildInvestSeoHtml(content, investArticleDomain.getTitle());
		CommonUtil.toFile(htmlContent, fileName, fileDir);	
				
		investArticleDomain.init();
		investArticleDomain.setArticleId(articleId);
		investArticleDomain.setUrl("web/info/investment/" + articleId + ".html");
		investArticleDao.modifyInvestArticle(investArticleDomain);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public InvestArticleDomain getInvestArticle(Integer articleId ){
		return investArticleDao.findInvestArticle(articleId);
	}
	
	public InvestArticleDomain editInvestArticle(Integer articleId ){
		return investArticleDao.findInvestArticle(articleId);
	}
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public void deleteInvestArticle(InvestArticleDomain investArticleDomain){
		investArticleDao.deleteInvestArticle(investArticleDomain.getArticleId());
		int articleId = investArticleDomain.getArticleId();
		//删除html网页
		String fileName = CommonUtil.INVESTMENT_HTML_DIR + articleId + ".html";
		String fileSeoName = CommonUtil.INVESTMENT_SEO_DIR + articleId + ".html";
		CommonUtil.deleteFile(fileName);
		CommonUtil.deleteFile(fileSeoName);
	}
	/*--------------------------------------------------------------------------------------*
	 * Description:	__Description__
	 * Method:		__Method__
	 * Author:		1.0 created by lijiaxuan at 2015-11-26 15:16:13
	 *--------------------------------------------------------------------------------------*/
	public void modifyInvestArticle(InvestArticleDomain investArticleDomain){
		String content = investArticleDomain.getContent();
		String htmlContent = HtmlUtil.buildInvestHtml(content, investArticleDomain.getTitle());
		investArticleDomain.setModDate(CommonUtil.getNowDate());
		investArticleDomain.setModTime(CommonUtil.getNowTime());	
		int articleId = investArticleDomain.getArticleId();
		//重新生成html网页
		String fileName = articleId + ".html";
		String fileDir = CommonUtil.INVESTMENT_HTML_DIR;
		CommonUtil.toFile(htmlContent, fileName, fileDir);
		fileDir = CommonUtil.INVESTMENT_SEO_DIR;
		htmlContent = HtmlUtil.buildInvestSeoHtml(content, investArticleDomain.getTitle());
		CommonUtil.toFile(htmlContent, fileName, fileDir);	
		investArticleDao.modifyInvestArticle(investArticleDomain);
	}
	//>. <CustomFileTag>
	
	/*--------------------------------------------------------------------------------------*
	 * Description:	TODO
	 * Method:		getInvestArticleList
	 * Author:		1.0 created by lijiaxuan at 2015年11月26日 下午5:18:00
	 *--------------------------------------------------------------------------------------*/
	public PageInfo getInvestArticleList(InvestArticleDomain investArticleDomain,
			PageInfo pageInfo) {
		PageInfo res = null;
		res = investArticleDao.queryInvestArticleListInPage(
				investArticleDomain, pageInfo);
		return res;
	}
	//>. <CustomFileTag>
}

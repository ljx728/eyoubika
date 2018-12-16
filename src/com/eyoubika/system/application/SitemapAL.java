package com.eyoubika.system.application;
import java.util.ArrayList;
import java.util.List;

import com.eyoubika.common.CommonVariables;
import com.eyoubika.util.CommonUtil;
import com.eyoubika.common.YbkException;
import com.eyoubika.info.dao.InvestArticleDao;
import com.eyoubika.info.domain.ArticleSimpleDomain;
import com.eyoubika.info.domain.InvestArticleDomain;
import com.eyoubika.system.domain.SystemInfoDomain;
import com.eyoubika.system.dao.SystemInfoDao;
public class SitemapAL{
	InvestArticleDao investArticleDao;
	
	public InvestArticleDao getInvestArticleDao() {
		return investArticleDao;
	}

	public void setInvestArticleDao(InvestArticleDao investArticleDao) {
		this.investArticleDao = investArticleDao;
	}

	public void buildSitemap(){
		//对seo页面进行生成站点地图
		buildSeoInvestSt();

	}
	
	public void buildSeoInvestSt(){
		//对seo页面进行生成站点地图
		String fileDir = "/var/lib/tomcat7/webapps/eyoubika/web/seo/investment";
		String fileUrl = "http://www.eyoubika.com/web/seo/investment/";
		String fileName = "sitemap.html";
		String seoDir = "/var/lib/tomcat7/webapps/eyoubika/web/";
		ArrayList<String> files = new ArrayList<String>();
		ArrayList<Integer> articleIds = new ArrayList<Integer>();
		CommonUtil.getFiles(fileDir, files);
		for (int i = 0 ; i < files.size(); i++) 
		{
			String file  =  files.get(i);
			int pos1 =  file.lastIndexOf('/');
			int pos2 =  file.lastIndexOf('.');			
			String idStr = file.substring(pos1+1, pos2);
			if (CommonUtil.isStringDigit(idStr)){
				articleIds.add(Integer.parseInt(idStr));
			}
			
		}
		
		List<ArticleSimpleDomain> articleList= investArticleDao.queryInvestArticleSimple(articleIds);
		StringBuffer str = new StringBuffer();
		str.append("<!doctype html>");
		str.append("<html>");
		str.append("    <head>");
		str.append("        <title>易邮币卡</title>");
		str.append("        <meta charset=\"utf-8\">");
		str.append("        <meta content=\"杭州彩蛋信息科技有限公司免费提供多家邮币卡交易所开户\" name=\"description\">");
		str.append("        <meta content=\"易邮币卡,邮币卡,彩蛋,交易所\" name=\"keywords\">");
		str.append("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		str.append("        <link rel=\"apple-touch-icon\" sizes=\"120x120\" href=\"http://www.eyoubika.com/web/images/iphone.png\">");
		str.append("        <link rel=\"apple-touch-icon\" sizes=\"152x152\" href=\"http://www.eyoubika.com/web/images/ipad.png\">");
		str.append("        <link rel=\"stylesheet\" href=\"http://www.eyoubika.com/web/styles/vendor.css\">");
		str.append("        <link rel=\"stylesheet\" href=\"http://www.eyoubika.com/web/styles/main.css\">");
		str.append("        <script src=\"http://www.eyoubika.com/web/scripts/vendor.js\"></script>");
		str.append("        <script src=\"http://www.eyoubika.com/scripts/util.js\"></script>");
		str.append("        <script src=\"http://www.eyoubika.com/scripts/config.js\"></script>");
		str.append("        <script src=\"http://www.eyoubika.com/web/scripts/effect.js\"></script> ");
		str.append("    </head>");
		str.append("    <body>");
		str.append("        <div class=\"pure-g wapper head\">");
		str.append("            <div class=\"pure-u-1-4 logo\">");
		str.append("                <a href=\"http://www.eyoubika.com\" title=\"易邮币卡\" rel=\"nofollow\"><img src=\"http://www.eyoubika.com/web/images/logo.png\" alt=\"易邮币卡\" height=\"35px\"></a>");
		str.append("            </div>");
		str.append("            <div class=\"pure-u-3-4\">");
		str.append("                <div class=\"menu\"><a href=\"http://www.eyoubika.com/web/kh/index.html\" title=\"开户\" rel=\"nofollow\">开户</a></div>");
		str.append("                <div class=\"menu\"><a href=\"http://www.eyoubika.com/web/download.html\">下载</a></div>");
		str.append("                <div class=\"menu active\"><a href=\"http://www.eyoubika.com\">资讯</a></div>");
		str.append("            </div>");
		str.append("        </div>");
		str.append("        <!-- menu bar -->");
		str.append("        <div class=\"pure-g uh-submenu\">");
		str.append("        <div class=\"wapper\">");
		str.append("            <ul class=\"pure-u-1\" >");
		str.append("                <li><a class=\"zhzx\" href=\"http://www.eyoubika.com\">综合资讯</a></li>");
		str.append("                <li><a class=\"dpdp\" href=\"http://www.eyoubika.com\">大盘点评</a></li>");
		str.append("                <li><a class=\"tzfx\" href=\"http://www.eyoubika.com\">投资分析</a></li>");
		str.append("                <li><a clss=\"xpsg\" href=\"http://www.eyoubika.com/web/shengou.html\">新品申购</a></li>");
		str.append("                <li><a class=\"xptg\" href=\"http://www.eyoubika.com/web/tuoguan.html\">新品托管</a></li>");
		str.append("                <li id=\"exchangerMenu\">文交所</li>");
		str.append("            </ul>");
		str.append("            </div>");
		str.append("        </div>");
		str.append("        <div class=\"pure-g uh-submenuMobile\">");
		str.append("        <div class=\"wapper\">");
		str.append("            <ul class=\"pure-u-1\" >");
		str.append("                <li><a class=\"zhzx\" href=\"http://www.eyoubika.com\">资讯</a></li>");
		str.append("                <li><a class=\"dpdp\" href=\"http://www.eyoubika.com\">点评</a></li>");
		str.append("                <li><a class=\"tzfx\" href=\"http://www.eyoubika.com\">分析</a></li>");
		str.append("                <li><a clss=\"xpsg\" href=\"http://www.eyoubika.com/web/shengou.html\">申购</a></li>");
		str.append("                <li><a class=\"xptg\" href=\"http://www.eyoubika.com/web/tuoguan.html\">托管</a></li>              ");
		str.append("                <li id=\"exchangerMobileMenu\">文交所</li>");
		str.append("            </ul>");
		str.append("            </div>");
		str.append("        </div>");
		str.append("        <div class=\"pure-g uh-submenuBlock\"></div>");
		str.append("        <div class=\"uh-exchangermenu\">");
		str.append("            <div class=\"wapper\">");
		str.append("                <div><a class=\"exchangerName\" exId=\"001000\" href=\"http://www.eyoubika.com/web/exchanger.html\">南京</a></div>");
		str.append("                <div><a class=\"exchangerName\" exId=\"002000\" href=\"http://www.eyoubika.com/web/exchanger.html\">南方</a></div>");
		str.append("                <div><a class=\"exchangerName\" exId=\"004000\" href=\"http://www.eyoubika.com/web/exchanger.html\">福丽特</a></div>");
		str.append("                <div><a class=\"exchangerName\" exId=\"003000\" href=\"http://www.eyoubika.com/web/exchanger.html\">中南</a></div>");
		str.append("                <div><a class=\"exchangerName\" exId=\"005000\" href=\"http://www.eyoubika.com/web/exchanger.html\">金马甲</a></div>");
		str.append("                <div><a class=\"exchangerName\" exId=\"010000\" href=\"http://www.eyoubika.com/web/exchanger.html\">艺交所</a></div>");
		str.append("                <div><a class=\"exchangerName\" exId=\"007000\" href=\"http://www.eyoubika.com/web/exchanger.html\">上海</a></div>");
		str.append("                <div class=\"exchangerName\" id=\"close\">收起</div>");
		str.append("            </div>");
		str.append("        </div>");
		str.append("        <!-- menu bar end -->");
		str.append("        <!-- banner slider need js -->");
		str.append("        <div class=\"uh-content\">");
		str.append("            <div class=\"pure-g wapper\">");
		str.append("                <div class=\"pure-u-1 pure-u-md-16-24 \">");
		str.append("                    <div class=\"uh-article\">");
		str.append("                        <div class=\"title\">关于广东省银行大洋票伍元纸币等邮币停牌的通知</div>");
		str.append("                        <div class=\"info\">来源：金马甲 时间：2015年7月5日</div>");
		str.append("                        <div class=\"content\">");
		
		for (int i = 0 ; i < articleList.size(); i++){
			str.append(" <p><a href=\""+ fileUrl + articleList.get(i).getArticleId()+ ".html" + "\">"+articleList.get(i).getTitle()+"</a></p>");
		}
		
		str.append("</div>");
		str.append("                            <!--div class=\"share\">");
		str.append("                                <div class=\"share-button\"><img src=\"../images/wechat.svg\"><span>分享到微信</span></div>");
		str.append("                                <div class=\"share-button\"><img src=\"../images/weibo.svg\"><span>分享到微博</span></div>");
		str.append("                            </div-->");
		str.append("                        </div>");
		str.append("                    </div>");
		str.append("                    <!-- left over -->");
		str.append("                    <div class=\"pure-u-8-24 hide\">");
		str.append("                        <div class=\"uh-banner\">");
		str.append("                            <div class=\"pic\"><img src=\"http://www.eyoubika.com/web/images/down.jpg\"></div>");
		str.append("                            <div class=\"title\">易邮币卡 手机客户端</div>");
		str.append("                            <div class=\"desc\">查询、资讯、交易一个App就够了</div>");
		str.append("                        </div>");
		str.append("                    </div>");
		str.append("                </div>");
		str.append("            </div>");
		str.append("            <div class=\"contact-qq hide\" onclick=\"window.open('http://wpa.qq.com/msgrd?v=3&uin=82142130&site=qq&menu=yes', '_blank')\">");
		str.append("                <a> 联系我们</a>");
		str.append("            </div>");
		str.append("            <div class=\"footer\">");
		str.append("                <div class=\"wapper pure-g\">");
		str.append("                    <div class=\"pure-u-1-4 col hide\">");
		str.append("                        <ul>");
		str.append("                            <li class=\"title\">关于</li>");
		str.append("<li>易邮币卡</li>");
		str.append("                            <li>彩蛋公司</li>");
		str.append("                            <li>用户协议</li>");
		str.append("                        </ul>");
		str.append("                    </div>");
		str.append("                    <div class=\"pure-u-1-4 col hide\">");
		str.append("                        <ul>");
		str.append("                            <li class=\"title\"> 下载 </li>");
		str.append("                            <li><a href=\"https://itunes.apple.com/app/yi-you-bi-ka/id1057006343?mt=8\" target=\"_blank\">iOS 客户端</a></li>");
		str.append("                            <li>Android 客户端</li>");
		str.append("                        </ul></div>");
		str.append("                        <div class=\"pure-u-1-4 col hide\">");
		str.append("                            <ul>");
		str.append("                                <li class=\"title\">  社交 </li>");
		str.append("                                <li>微信公众号</li>");
		str.append("                                <li><a href=\"http://weibo.com/eyoubika?wvr=5\" target=\"_blank\">官方微博</a></li>");
		str.append("                            </ul></div>");
		str.append("                            <div class=\"pure-u-1-4 col hide\">");
		str.append("                                <ul>");
		str.append("                                    <li class=\"title\">  联系 </li>");
		str.append("                                    <li>电话 0571-85226989</li>");
		str.append("                                    <li>邮箱 help@eyoubika.com</li>");
		str.append("                                    <li>地址 杭州市经开区金沙湖1号</li>");
		str.append("                                </ul>");
		str.append("                            </div>");
		str.append("                        </div>");
		str.append("                        <div class=\"pure-u-1 call show\" onclick=\"location.href='tel:0571-85226989';\">");
		str.append("                            <div class=\"pure-u-1\"><img src=\"http://www.eyoubika.com/web/images/phone-call.svg\" width=\"30px\"></div>");
		str.append("                            <div class=\"pure-u-1\"><a href=\"tel:0571-85226989\">拨打电话 0571-85226989</a></div>");
		str.append("                        </div>");
		str.append("                        <div class=\"pure-u-1 copyright\">2015©彩蛋 备案号 浙ICP备 15022997</div>");
		str.append("                    </div>");
		str.append("                </div>");
		str.append("            </body>");
		str.append("        </html>");
		CommonUtil.toFile(str.toString(), fileName, seoDir);
	}
}
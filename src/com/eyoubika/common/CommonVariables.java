package com.eyoubika.common;
import java.util.Map;

/*==========================================================================================*
 * Description:	定义了基本的通用变量
 * Class:		CommonVariables
 * Author:		lijiaxuan
 * Copyright:	CaiDan (c) 2015 jiaxuan.li@eyoubika.com
 * History:		1.0 created by lijiaxuan at 2015-5-16 22:19:43
 *==========================================================================================*/
public final class CommonVariables {
	//************************* 用户类型 *************************//
	public static final String USER_STATUS_GUEST = "00";
	public static final String USER_STATUS_NORMAL = "01";
	public static final String USER_STATUS_LIMIT = "03";
	public static final String USER_STATUS_FORBID = "04";
	public static final String USER_STATUS_SUPER = "08";
	public static final String USER_STATUS_ADMIN = "09";
	//************************* 登录类型 *************************//
	public static final String LOGIN_TYPE_INITIAL = "initial";		//首次登陆
	public static final String LOGIN_TYPE_FORCE = "force";			//强制登陆	
	public static final String LOGIN_TYPE_AUTO = "auto";			//自动登陆
	public static final String LOGIN_TYPE_ADMIN= "admin";			//管理员登陆
	public static final String LOGIN_TYPE_OTHER = "other";			//其他登陆
	
	//************************* 抓取级别 *************************//
	public static final String FETCH_CONTENT_URL = "01";			//抓取URL
	public static final String FETCH_CONTENT_ORIGIN = "02";			//抓取内容
	public static final String FETCH_CONTENT_PROCESS = "03";		//程序修改内容
	public static final String FETCH_CONTENT_HANDLED = "04";		//人工修改内容
	
	//************************* 品种类型 *************************//
	public static final String SBC_INITIAL = "000000";				//未分类
	public static final String SBC_STAMP = "001000";				//邮票
	public static final String SBC_COIN = "002000";					//钱币
	public static final String SBC_CARD = "003000";					//电话卡
	public static final String SBC_INDEX = "009000";				//指数

	//************************* 文章类型 *************************//
	//消息类
	public static final String MESS_BREED_INFO = "001001";			//藏品信息	
	//资讯类
	public static final String NOTE_NOTICE = "002001";				//通知公告,综合资讯
	public static final String NOTE_EXCHANGIN_DATA = "002002";		//交易数据
	public static final String NOTE_SUBSCRIBE = "002003";			//申购公告
	public static final String NOTE_DEPOSIT = "002004";				//托管公告
	public static final String NOTE_WAREHOUSE= "002005";			//入库公告
	public static final String NOTE_MARKET_REVIEW = "002006";		//大盘点评
	public static final String NOTE_INVEST_REFER = "002007";		//投资分析
	//新闻类
	public static final String INFO_INDUSTRY_NEWS = "003001";		//行业新闻	
	public static final String INFO_MARKET_QUOTES = "003002";		//市场报价
	public static final String INFO_CICC_RESEARCH = "003003";		//中金研究
	public static final String INFO_EXPERT_COLUMN = "003004";		//名家专栏	
	public static final String INFO_MARKETING_CAP = "003005";		//营销活动
		
	//************************* 交易所 *************************//
	public static final String EXCHANGE_NANJING = "001000";			//南京交易所  http://www.zgqbyp.com/ 1
	public static final String EXCHANGE_NANFANG = "002000";			//南方交易所 http://www.nfqbyp.com/ 1 
	public static final String EXCHANGE_ZHONGNAN = "003000";		//中南文交所 http://www.znypjy.com/ 1
	public static final String EXCHANGE_FULITE = "004000";			//福丽特交易所 http://dzp.wjybk.com/  1 
	public static final String EXCHANGE_JINMAJIA = "005000";		//金马甲交易所 http://qbyp.jinmajia.com/market/s/48/  1
	public static final String EXCHANGE_JIANGSU = "006000";			//江苏文交所 http://ybk.jscaee.com.cn/
	public static final String EXCHANGE_SHANGHAI = "007000";		//上海交易所 http://www.ybika.com/ 
	public static final String EXCHANGE_HUAXIA = "008000";			//华夏文交所 http://www.xhcae.com/  1
	public static final String EXCHANGE_HUAZHONG = "009000";		//华中文交所 http://www.hbcpre.com/ 
	public static final String EXCHANGE_YIJIAO = "010000";			//艺交所 http://www.cacecybk.com.cn/
	public static final String EXCHANGE_TIANJIN = "011000";		//天津文交所 
	public static final String EXCHANGE_XINAN = "012000";			//西南文交所
	

	//************************* 性别 *************************//
	public static final String SEX_MALE= "01";	 					//男性	
	public static final String SEX_FEMALE= "02";					//女性
	public static final String SEX_MIDDLE= "03";					//中性
	
	//************************* 开户类型 *************************//
	public static final String OPEN_PERS_ACCOUNT= "01";				//开户渠道：ios		
	public static final String OPEN_CORP_ACCOUNT= "02";				//开户渠道：android
	public static final String OPEN_CHANNEL_APPIOS= "01";				//开户渠道：ios		
	public static final String OPEN_CHANNEL_APPAND= "02";				//开户渠道：android
	public static final String OPEN_CHANNEL_WEB= "04";				//开户渠道：web	
	public static final String OPEN_CHANNEL_WECHART= "03";				//开户渠道：微信渠道	
	//************************* 业务编号 *************************//
	public static final String TRANS_OPEN_ACCOUNT = "OPAC";			//开户业务
	
	//************************* 业务状态 *************************//
	public static final String OPEN_ACCOUNT_CHEKED = "01";			//已经验证
	public static final String OPEN_ACCOUNT_UNCHEKED = "02";		//未验证
	public static final String OPEN_ACCOUNT_NOTALLOWED = "03";		//不允许
	
	//************************* 排序标识 *************************//
	public static final String RANK_ASC = "01";						//升序
	public static final String RANK_DESC = "02";					//降序
	
	public static final String RANK_TURNOVER_RATE = "01";			//当日换手率排行榜
	public static final String RANK_VOLATILITY = "02";				//当日波动排行
	public static final String RANK_RISE_FIVEMIN = "03";			//5分钟快速涨幅排行
	public static final String RANK_RISE_THREEDAY = "04";			//3日涨幅排行
	
	//************************* 日志类型 *************************//
	public static final String LOG_TYPE_SYSTEM = "01";				//系统日志
	public static final String LOG_TYPE_SPIDER = "03";  			//爬虫日志	
	
	//************************* 黑白名单 *************************//
	public static final String BADWORD_NICKNAME = "01";				//黑名单类型-昵称
	public static final String BADWORD_FORUM = "02";				//黑名单类型-论坛
	
	//************************* 内存数据库标识 *************************//
	public static final String REDIS_KEY_SBCEXIDMAP = "sbcExIdMap";	//<品种ID:交易所ID>映射表
	public static final String REDIS_KEY_QUOTS = "qu:";				//行情
	public static final String REDIS_KEY_SBCCODEMAP = "sc:";		//<品种编码:品种ID>映射表
	public static final String REDIS_KEY_TIMESHARE = "tm:";			//分时图
	public static final String REDIS_KEY_QUOTSMORE = "qm:";			//品种其他信息
	public static final String REDIS_KEY_BILLBOARD = "bb:";			//品种其他信息

	public static final int RDB_SYSTEM = 0;							//系统相关数据库
	public static final int RDB_QUOTATIONS = 1;						//实时行情相关数据库
	public static final int RDB_TRANSACTION = 2;					//业务相关数据库
	
	//************************* 时间跨度 *************************//
	public static final String TIME_INTERVAL_SEC = "01";			//second
	public static final String TIME_INTERVAL_HMI = "02";			//half-minute
	public static final String TIME_INTERVAL_MIN = "03";			//minute
	public static final String TIME_INTERVAL_HHO = "04";			//half-hour
	public static final String TIME_INTERVAL_HOU = "05";			//hour
	public static final String TIME_INTERVAL_DAY = "06";			//day
	public static final String TIME_INTERVAL_WEK = "07";			//weeek
	public static final String TIME_INTERVAL_MON = "08";			//month
	public static final String TIME_INTERVAL_QUA = "09";			//quarter
	public static final String TIME_INTERVAL_YEA = "10";			//year
	
	//************************* 交易时间 *************************//
	public static final String BATCH_BEG_AM = "01:00:00";		//夜间的批量时间
	public static final String BATCH_EXE_AM = "09:15:00";		//早上的批量时间
	
	public static final String TRADEHOUR_BEG_AM = "09:30:00";		//早上的交易开始时间
	public static final String TRADESHARE_BEG_AM = "09:30:30";		//早上的交易开始时间
	public static final String TRADEHOUR_END_AM = "11:30:30";		//早上的交易结束时间
	public static final String TRADEHOUR_BEG_PM = "13:00:00";		//下午的交易开始时间
	public static final String TRADESHARE_BEG_PM = "13:00:30";		//下午的交易开始时间
	public static final String TRADEHOUR_END_PM = "15:00:30";		//下午的交易结束时间
	
	//************************* 通用级别 *************************//
	public static final String COMMON_LEVEL_01= "01";				//通用级别：01级		
	public static final String COMMON_LEVEL_02= "02";				//通用级别：02级	
	public static final String COMMON_LEVEL_03= "03";				//通用级别：03级	
	public static final String COMMON_LEVEL_04= "04";				//通用级别：04级	
	public static final String COMMON_LEVEL_05= "05";				//通用级别：04级	
	public static final String COMMON_LEVEL_06= "06";				//通用级别：04级	
	public static final String COMMON_LEVEL_07= "07";				//通用级别：04级	
	public static final String COMMON_LEVEL_08= "08";				//通用级别：04级	
	//************************* 通用级别 *************************//
	//type
	//01: 普通日子，周一-周四
	//02：非交易日，周日
	//03：部分交易日，周五
	//04：部分交易日，周六
	//05: 月的最后一天，普通日子，周一-周四
	//06：月的最后一天，非交易日，周日
	//07：月的最后一天，部分交易日，周五
	//08：月的最后一天，部分交易日，周六
	
	//status
	//01: 普通
	//02：周一
	//03：月首
	//04：周一月首
	
	//************************* 任务ID *************************//
	public static final String TASK_INPUT_TCHART= "01";				//通用级别：01级		
	public static final String TASK_INPUT_CLOSEPRICE= "02";				//通用级别：02级	
	public static final String TASK_CLEAN_REDIS= "03";				//通用级别：03级	
	public static final String TASK_INPUT_QUOTATIONS= "04";				//通用级别：04级	
	
	//************************* 通用标识 *************************//
	public static final String SESSION_OPERATOR = "currentUser";
	public static final String SESSION_AUTHCODE = "authCode";
	public static final String SESSION_USERID = "userId";
	public static final String SESSION_PNUMBER = "pnumber";
	public static final String SESSION_TOKEN = "token";
	public static final String ASPLIT = "\\|";
	public static final String PLACEHOLDER = "-";
	
	//************************* 布尔值 *************************//
	public static final String TRUE_SHORT_FLAG = "1";
	public static final String TRUE_WROD_FLAG = "OK";
	public static final String FALSE_SHORT_FLAG = "0";
	public static final String TRUE_MIDDLE_FLAG = "01";
	public static final String FALSE_MIDDLE_FLAG = "00";
	//************************* 其他设置 *************************//
	public static final int FAVORITE_LISTITEM_MAX = 100;
	public static final int USERID_INITIAL = 0;  				//默认初始userId
	public static final String AUTHCODE_EXPIRETIME = "30";		//验证码过期时间：30min	
	public static final String SESSION_EXPIRETIME = "60";		//session过期时间：60min
	public static final int HTML_SIBLING_MAX = 20;				//HTML解析：最大兄弟节点个数
	public static final int HTML_PARENT_MAX = 8;				//HTML解析：最大父亲节点个数
	public static final int ARTICLE_BRIEF_MAX = 200;  			//文章摘要最大字数
	public static final int LIST_ITEMS_MAX = 50;  				//返回列表中的条目最大数
	public static final int RANKLIST_ITEMS_MAX = 10;  			//排行榜的条目最大数
	
}
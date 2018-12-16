USE `eyoubika`;
/*Table structure for table `ta_DepositArticle` */
DROP TABLE IF EXISTS `ta_DepositArticle`;
CREATE TABLE `ta_DepositArticle` (
	 `DA_ArticleId` int(10) NOT NULL auto_increment,
	 `DA_Title` varchar(512) default NULL ,
	 `DA_SubTitle` varchar(512) default NULL ,
	 `DA_IssueTime` varchar(32) default NULL ,
	 `DA_WebTime` varchar(32) default NULL ,
	 `DA_FetchTime` varchar(32) default NULL ,
	 `DA_ExId` varchar(32) default NULL ,
	 `DA_Article` mediumtext default NULL ,
	 `DA_Type` char(6) default NULL ,
	 `DA_Status` char(2) default NULL ,
	 `DA_HaveImg` char(1) default NULL ,
	 `DA_ArticleUrl` varchar(512) default NULL ,
	PRIMARY KEY  (`DA_ArticleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ta_DepositArticle` */


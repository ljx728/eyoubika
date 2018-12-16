USE `eyoubika`;
/*Table structure for table `ta_SubscribeArticle` */
DROP TABLE IF EXISTS `ta_SubscribeArticle`;
CREATE TABLE `ta_SubscribeArticle` (
	 `SA_ArticleId` int(10) NOT NULL auto_increment,
	 `SA_Title` varchar(512) default NULL ,
	 `SA_SubTitle` varchar(512) default NULL ,
	 `SA_IssueTime` varchar(32) default NULL ,
	 `SA_WebTime` varchar(32) default NULL ,
	 `SA_FetchTime` varchar(32) default NULL ,
	 `SA_ExId` varchar(32) default NULL ,
	 `SA_Article` MEDIUMTEXT default NULL ,
	 `SA_Type` char(6) default NULL ,
	 `SA_Status` char(2) default NULL ,
	 `SA_HaveImg` char(1) default NULL ,
	 `SA_ArticleUrl` varchar(512) default NULL ,
	PRIMARY KEY  (`SA_ArticleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ta_SubscribeArticle` */


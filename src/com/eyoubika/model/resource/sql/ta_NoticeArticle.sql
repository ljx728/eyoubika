USE `eyoubika`;
/*Table structure for table `ta_NoticeArticle` */
DROP TABLE IF EXISTS `ta_NoticeArticle`;
CREATE TABLE `ta_NoticeArticle` (
	 `NA_ArticleId` int(10) NOT NULL auto_increment,
	 `NA_Title` varchar(512) default NULL ,
	 `NA_SubTitle` varchar(512) default NULL ,
	 `NA_IssueTime` varchar(32) default NULL ,
	 `NA_WebTime` varchar(32) default NULL ,
	 `NA_FetchTime` varchar(32) default NULL ,
	 `NA_ExId` varchar(32) default NULL ,
	 `NA_Article` mediumtext default NULL ,
	 `NA_Type` char(6) default NULL ,
	 `NA_Status` char(2) default NULL ,
	 `NA_HaveImg` char(1) default NULL ,
	 `NA_ArticleUrl` varchar(512) default NULL ,
	PRIMARY KEY  (`NA_ArticleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ta_NoticeArticle` */


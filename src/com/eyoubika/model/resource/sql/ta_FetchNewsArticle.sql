USE `eyoubika`;
/*Table structure for table `ta_FetchNewsArticle` */
DROP TABLE IF EXISTS `ta_FetchNewsArticle`;
CREATE TABLE `ta_FetchNewsArticle` (
	 `FNA_NewsId` int(10) NOT NULL auto_increment,
	 `FNA_Title` varchar(128) default NULL ,
	 `FNA_SubTitle` varchar(128) default NULL ,
	 `FNA_IssueTime` varchar(32) default NULL ,
	 `FNA_IssueUnit` varchar(128) default NULL ,
	 `FNA_WebTime` varchar(32) default NULL ,
	 `FNA_FetchTime` varchar(32) default NULL ,
	 `FNA_From` varchar(32) default NULL ,
	 `FNA_Article` text default NULL ,
	 `FNA_Img` varchar(128) default NULL ,
	 `FNA_ImgPos` varchar(1024) default NULL ,
	 `FNA_Table` text default NULL ,
	 `FNA_TablePos` varchar(1024) default NULL ,
	 `FNA_Type` char(6) default NULL ,
	 `FNA_Status` char(2) default NULL ,
	 `FNA_HaveImg` char(1) default NULL ,
	 `FNA_HaveTable` char(1) default NULL ,
	 `FNA_ArticleUrl` varchar(256) default NULL ,
	PRIMARY KEY  (`FNA_NewsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ta_FetchNewsArticle` */


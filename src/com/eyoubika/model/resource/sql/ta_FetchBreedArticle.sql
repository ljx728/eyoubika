USE `eyoubika`;
/*Table structure for table `ta_FetchBreedArticle` */
DROP TABLE IF EXISTS `ta_FetchBreedArticle`;
CREATE TABLE `ta_FetchBreedArticle` (
	 `FBA_ArticleId` int(10) NOT NULL ,
	 `FBA_SbcId` int(10) NOT NULL ,
	 `FBA_Title` varchar(128) default NULL ,
	 `FBA_SubTitle` varchar(128) default NULL ,
	 `FBA_IssueTime` varchar(32) default NULL ,
	 `FBA_IssueUnit` varchar(128) default NULL ,
	 `FBA_WebTime` varchar(32) default NULL ,
	 `FBA_FetchTime` varchar(32) default NULL ,
	 `FBA_From` varchar(32) default NULL ,
	 `FBA_Article` text default NULL ,
	 `FBA_Img` varchar(128) default NULL ,
	 `FBA_ImgPos` varchar(1024) default NULL ,
	 `FBA_Table` text default NULL ,
	 `FBA_TablePos` varchar(1024) default NULL ,
	 `FBA_Type` char(6) default NULL ,
	 `FBA_Status` char(2) default NULL ,
	 `FBA_HaveImg` char(1) default NULL ,
	 `FBA_HaveTable` char(1) default NULL ,
	 `FBA_ArticleUrl` varchar(256) default NULL ,
	PRIMARY KEY  (`FBA_ArticleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ta_FetchBreedArticle` */


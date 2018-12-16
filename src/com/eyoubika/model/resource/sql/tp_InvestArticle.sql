USE `eyoubika`;
/*Table structure for table `tp_InvestArticle` */
DROP TABLE IF EXISTS `tp_InvestArticle`;
CREATE TABLE `tp_InvestArticle` (
	 `IA_ArticleId` int NOT NULL auto_increment,
	 `IA_Title` varchar(128) NOT NULL ,
	 `IA_Url` varchar(512) default NULL ,
	 `IA_Source` varchar(512) default NULL ,
	 `IA_Brief` varchar(1024) default NULL ,
	 `IA_Content` text default NULL ,
	 `IA_Author` varchar(128) default NULL ,
	 `IA_Tag` varchar(128) default NULL ,
	 `IA_ExId` varchar(128) default NULL ,
	 `IA_Type` char(6) default NULL ,
	 `IA_Status` char(2) default NULL ,
	 `IA_GenDate` char(8) default NULL ,
	 `IA_GenTime` char(8) default NULL ,
	 `IA_ModDate` char(8) default NULL ,
	 `IA_ModTime` char(8) default NULL ,
	PRIMARY KEY  (`IA_ArticleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tp_InvestArticle` */


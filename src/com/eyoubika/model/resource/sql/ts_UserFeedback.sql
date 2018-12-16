USE `eyoubika`;
/*Table structure for table `ts_UserFeedback` */
DROP TABLE IF EXISTS `ts_UserFeedback`;
CREATE TABLE `ts_UserFeedback` (
	 `UF_UfId` int NOT NULL auto_increment,
	 `UF_FeederId` int default NULL ,
	 `UF_NickName` varchar(128) default NULL ,
	 `UF_Contact` varchar(128) default NULL ,
	 `UF_Content` text default NULL ,
	 `UF_Status` varchar(2) NOT NULL ,
	 `UF_Date` char(8) NOT NULL ,
	 `UF_Time` char(8) NOT NULL ,
	PRIMARY KEY  (`UF_UfId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ts_UserFeedback` */


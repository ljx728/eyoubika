USE `eyoubika`;
/*Table structure for table `ta_InvestGamesNews` */
DROP TABLE IF EXISTS `ta_InvestGamesNews`;
CREATE TABLE `ta_InvestGamesNews` (
	 `IGN_NewsId` int NOT NULL  auto_increment,
	 `IGN_Title` varchar(128) default NULL ,
	 `IGN_Url` varchar(512) NOT NULL ,
	 `IGN_Brief` varchar(1024) default NULL ,
	 `IGN_Type` char(2) default NULL ,
	 `IGN_Date` char(8) NOT NULL ,
	 `IGN_Time` char(8) NOT NULL ,
	PRIMARY KEY  (`IGN_NewsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ta_InvestGamesNews` */


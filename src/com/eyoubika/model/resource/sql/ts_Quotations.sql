USE `eyoubika`;
/*Table structure for table `ts_Quotations` */
DROP TABLE IF EXISTS `ts_Quotations`;
CREATE TABLE `ts_Quotations` (
	 `Q_SbcId` int(10) NOT NULL ,
	 `Q_SbcCode` varchar(32) NOT NULL ,
	 `Q_SbcName` varchar(128) default NULL ,
	 `Q_ExId` char(6) NOT NULL ,
	 `Q_YesterClPrice` varchar(16) default NULL ,
	 `Q_OpeningPrice` varchar(16) default NULL ,
	 `Q_CurrentPrice` varchar(16) default NULL ,
	 `Q_HighestPrice` varchar(16) default NULL ,
	 `Q_LowestPrice` varchar(16) default NULL ,
	 `Q_AveragePrice` varchar(16) default NULL ,
	 `Q_YesterAvPrice` varchar(16) default NULL ,
	 `Q_RiseValue` varchar(16) default NULL ,
	 `Q_DealVolume` varchar(16) default NULL ,
	 `Q_Poq` varchar(16) default NULL ,
	 `Q_TotalValue` varchar(16) default NULL ,
	 `Q_RecordTime` varchar(16) default NULL ,
	PRIMARY KEY  (`Q_SbcId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ts_Quotations` */


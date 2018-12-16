USE `eyoubika`;
/*Table structure for table `tp_SbcPriceInfo` */
DROP TABLE IF EXISTS `tp_SbcPriceInfo`;
CREATE TABLE `tp_SbcPriceInfo` (
	 `SPI_SbcId` int(10) NOT NULL ,
	 `SPI_SbcCode` varchar(16) default NULL ,
	 `SPI_SbcName` varchar(128) NOT NULL ,
	 `SPI_ExId` char(6) default NULL ,
	 `SPI_OpeningPrice` decimal(18,2) default NULL ,
	 `SPI_ClosingPrice` decimal(18,2) default NULL ,
	 `SPI_HighestPrice` decimal(18,2) default NULL ,
	 `SPI_LowestPrice` decimal(18,2) default NULL ,
	 `SPI_AveragePrice` decimal(18,2) default NULL ,
	 `SPI_CurrentPrice` decimal(18,2) default NULL ,
	 `SPI_DealVolume` int default NULL ,
	 `SPI_Poq` int default NULL ,
	 `SPI_RiseValue` decimal(18,2) default NULL ,
	 `SPI_TotalValue` int default NULL ,
	 `SPI_RecordDate` varchar(16) default NULL ,
	PRIMARY KEY  (`SPI_SbcId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tp_SbcPriceInfo` */


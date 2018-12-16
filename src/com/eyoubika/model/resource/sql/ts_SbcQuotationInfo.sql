USE `eyoubika`;
/*Table structure for table `ts_SbcQuotationInfo` */
DROP TABLE IF EXISTS `ts_SbcQuotationInfo`;
CREATE TABLE `ts_SbcQuotationInfo` (
	 `SQI_SbcId` int NOT NULL ,
	 `SQI_OpenPrice` varchar(16) NOT NULL ,
	 `SQI_ClosePrice` varchar(16) NOT NULL ,
	 `SQI_HighestPrice` varchar(16) NOT NULL ,
	 `SQI_LowestPrice` varchar(16) NOT NULL ,
	 `SQI_RiseValue` varchar(16) <<nullType>> ,
	 `SQI_DealVolume` varchar(16) <<nullType>> ,
	 `SQI_DealValue` varchar(16) <<nullType>> ,
	 `SQI_RiseScope` varchar(16) <<nullType>> ,
	 `SQI_Type` char(1) <<nullType>> ,
	 `SQI_Date` char(8) NOT NULL ,
	PRIMARY KEY  (`SQI_SbcId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ts_SbcQuotationInfo` */


USE `eyoubika`;
/*Table structure for table `tp_ExchangeBasicInfo` */
DROP TABLE IF EXISTS `tp_ExchangeBasicInfo`;
CREATE TABLE `tp_ExchangeBasicInfo` (
	 `EBI_ExId` varchar(6) NOT NULL ,
	 `EBI_ExName` varchar(64) NOT NULL ,
	 `EBI_Website` varchar(64) NOT NULL ,
	PRIMARY KEY  (`EBI_ExId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tp_ExchangeBasicInfo` */


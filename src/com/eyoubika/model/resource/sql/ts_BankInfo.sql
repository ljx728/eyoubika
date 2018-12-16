USE `eyoubika`;
/*Table structure for table `ts_BankInfo` */
DROP TABLE IF EXISTS `ts_BankInfo`;
CREATE TABLE `ts_BankInfo` (
	 `BI_BankId` varchar(6) NOT NULL ,
	 `BI_BankName` varchar(64) NOT NULL ,
	PRIMARY KEY  (`BI_BankId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ts_BankInfo` */


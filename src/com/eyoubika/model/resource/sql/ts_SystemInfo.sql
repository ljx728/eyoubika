USE `eyoubika`;
/*Table structure for table `ts_SystemInfo` */
DROP TABLE IF EXISTS `ts_SystemInfo`;
CREATE TABLE `ts_SystemInfo` (
	 `SI_SysId` int NOT NULL auto_increment,
	 `SI_Name` varchar(128) NOT NULL ,
	 `SI_Value` varchar(128) NOT NULL ,
	 `SI_Type` varchar(6) default NULL ,
	 `SI_Date` char(8) NOT NULL ,
	PRIMARY KEY  (`SI_SysId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ts_SystemInfo` */


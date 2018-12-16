USE `eyoubika`;
/*Table structure for table `ts_DbManager` */
DROP TABLE IF EXISTS `ts_DbManager`;
CREATE TABLE `ts_DbManager` (
	 `DM_CId` varchar(6) NOT NULL ,
	 `DM_Host` varchar(32) NOT NULL ,
	 `DM_Port` varchar(8) NOT NULL ,
	 `DM_Database` varchar(32) NOT NULL ,
	 `DM_Table` varchar(32) NOT NULL ,
	 `DM_Type` varchar(6) NOT NULL ,
	 `DM_User` varchar(32) NOT NULL ,
	 `DM_Password` varchar(32) NOT NULL ,
	PRIMARY KEY  (`DM_CId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ts_DbManager` */


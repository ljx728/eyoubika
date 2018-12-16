USE `eyoubika`;
/*Table structure for table `ta_TestApp` */
DROP TABLE IF EXISTS `ta_TestApp`;
CREATE TABLE `ta_TestApp` (
	 `TA_TestId` int(10) NOT NULL auto_increment,
	 `TA_ModulePath` varchar(32) default NULL ,
	 `TA_ClassName` varchar(32) default NULL ,
	 `TA_PackageName` varchar(32) NOT NULL ,
	 `TA_TableName` varchar(32) default NULL ,
	PRIMARY KEY  (`TA_TestId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ta_TestApp` */


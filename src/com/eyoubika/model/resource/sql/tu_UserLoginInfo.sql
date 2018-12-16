USE `eyoubika`;
/*Table structure for table `tu_UserLoginInfo` */
DROP TABLE IF EXISTS `tu_UserLoginInfo`;
CREATE TABLE `tu_UserLoginInfo` (
	 `ULI_UserId` int(10) NOT NULL ,
	 `ULI_UUID` varchar(128) default NULL ,
	 `ULI_TermType` char(6) default NULL ,
	 `ULI_FirstLogiTime` varchar(32) default NULL ,
	 `ULI_LastLogiTime` varchar(32) default NULL ,
	 `ULI_LogiTime` varchar(32) default NULL ,
	 `ULI_LogiIp` varchar(32) default NULL ,
	 `ULI_Version` varchar(32) default NULL ,
	PRIMARY KEY  (`ULI_UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tu_UserLoginInfo` */


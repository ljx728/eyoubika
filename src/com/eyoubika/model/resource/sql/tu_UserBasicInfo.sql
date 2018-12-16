USE `eyoubika`;
/*Table structure for table `tu_UserBasicInfo` */
DROP TABLE IF EXISTS `tu_UserBasicInfo`;
CREATE TABLE `tu_UserBasicInfo` (
	 `UBI_UserId` int(10) NOT NULL auto_increment,
	 `UBI_NickName` varchar(64) default NULL ,
	 `UBI_Password` varchar(32) NOT NULL ,
	 `UBI_Token` varchar(32) default NULL ,
	 `UBI_Salt` varchar(32) default NULL ,
	 `UBI_Pnumber` varchar(32) default NULL ,
	 `UBI_Status` char(6) NOT NULL ,
	 `UBI_Email` varchar(64) default NULL ,
	 `UBI_RegiTime` varchar(32) default NULL ,
	 `UBI_TradePassword` varchar(32) default NULL ,
	PRIMARY KEY  (`UBI_UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tu_UserBasicInfo` */


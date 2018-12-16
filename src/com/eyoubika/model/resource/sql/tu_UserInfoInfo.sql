USE `eyoubika`;
/*Table structure for table `tu_UserInfoInfo` */
DROP TABLE IF EXISTS `tu_UserInfoInfo`;
CREATE TABLE `tu_UserInfoInfo` (
	 `UII_UserId` int(10) NOT NULL ,
	 `UII_NickName` varchar(64) default NULL ,
	 `UII_Status` char(6) NOT NULL ,
	 `UII_Pnumber` varchar(32) default NULL ,
	 `UII_UserName` varchar(32) default NULL ,
	 `UII_IdentifyNo` varchar(32) default NULL ,
	 `UII_Email` varchar(64) default NULL ,
	 `UII_RegiTime` varchar(32) default NULL ,
	 `UII_Sex` char(2) default NULL ,
	 `UII_Telephone` varchar(32) default NULL ,
	 `UII_City` char(6) default NULL ,
	 `UII_Address` varchar(128) default NULL ,
	 `UII_Postcode` char(6) default NULL ,
	 `UII_Icon` MediumBlob default NULL ,
	 `UII_VipLevel` char(6) default NULL ,
	 `UII_SocialLevel` char(6) default NULL ,
	 `UII_Species` int default NULL ,
	 `UII_Duration` float default NULL ,
	PRIMARY KEY  (`UII_UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tu_UserInfoInfo` */


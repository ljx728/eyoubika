USE `eyoubika`;
/*Table structure for table `tu_UserDetailInfo` */
DROP TABLE IF EXISTS `tu_UserDetailInfo`;
CREATE TABLE `tu_UserDetailInfo` (
	 `UDI_UserId` int(10) NOT NULL ,
	 `UDI_UserName` varchar(32) default NULL ,
	 `UDI_IdentifyNo` varchar(32) default NULL ,
	 `UDI_Sex` char(2) default NULL ,
	 `UDI_Telephone` varchar(32) default NULL ,
	 `UDI_City` char(6) default NULL ,
	 `UDI_Address` varchar(256) default NULL ,
	 `UDI_Postcode` char(6) default NULL ,
	 `UDI_Icon` MediumBlob default NULL ,
	 `UDI_VipLevel` char(6) default NULL ,
	 `UDI_SocialLevel` char(6) default NULL ,
	 `UDI_Species` int default NULL ,
	 `UDI_Duration` float default NULL ,
	PRIMARY KEY  (`UDI_UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tu_UserDetailInfo` */


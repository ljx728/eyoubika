USE `eyoubika`;
/*Table structure for table `ts_InterfaceAttrInfo` */
DROP TABLE IF EXISTS `ts_InterfaceAttrInfo`;
CREATE TABLE `ts_InterfaceAttrInfo` (
	 `IAI_AttrId` Integer NOT NULL auto_increment,
	 `IAI_Type` varchar(32) NOT NULL ,
	 `IAI_IsNull` char(1) NOT NULL ,
	 `IAI_Name` varchar(64) NOT NULL ,
	 `IAI_NickName` varchar(128) NOT NULL ,
	 `IAI_Description` varchar(1028) default NULL ,
	 `IAI_Refer` Integer default NULL ,
	 `IAI_Date` char(8) default NULL ,
	 `IAI_Time` char(8) default NULL ,
	PRIMARY KEY  (`IAI_AttrId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ts_InterfaceAttrInfo` */


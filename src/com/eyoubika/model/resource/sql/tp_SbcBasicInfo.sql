USE `eyoubika`;
/*Table structure for table `tp_SbcBasicInfo` */
DROP TABLE IF EXISTS `tp_SbcBasicInfo`;
CREATE TABLE `tp_SbcBasicInfo` (
	 `SBI_SbcId` int(10) NOT NULL auto_increment,
	 `SBI_SbcCode` varchar(16) default NULL ,
	 `SBI_SbcName` varchar(128) NOT NULL ,
	 `SBI_SbcType` char(6) default NULL ,
	 `SBI_IssueTime` varchar(32) default NULL ,
	 `SBI_IssueOrg` varchar(64) default NULL ,
	 `SBI_IssueVolume` varchar(64) default NULL ,
	 `SBI_IssuePrice` varchar(64) default NULL ,
	 `SBI_FaceValue` varchar(32) default NULL ,
	 `SBI_Texture` varchar(32) default NULL ,
	 `SBI_Volume` varchar(32) default NULL ,
	 `SBI_ExId` char(6) default NULL ,
	 `SBI_Status` char(2) default NULL ,
	 `SBI_InputDate` char(8) default NULL ,
	PRIMARY KEY  (`SBI_SbcId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tp_SbcBasicInfo` */


USE `eyoubika`;
/*Table structure for table `ts_InterfaceBasicInfo` */
DROP TABLE IF EXISTS `ts_InterfaceBasicInfo`;
CREATE TABLE `ts_InterfaceBasicInfo` (
	 `IBI_Code` varchar(6) NOT NULL ,
	 `IBI_Name` varchar(128) NOT NULL ,
	 `IBI_NickName` varchar(128) NOT NULL ,
	 `IBI_Url` varchar(256) default NULL ,
	 `IBI_Input` varchar(256) default NULL ,
	 `IBI_Output` varchar(256) default NULL ,
	 `IBI_InputEx` varchar(2048) default NULL ,
	 `IBI_OutputEx` varchar(2048) default NULL ,
	 `IBI_Text` text default NULL ,
	 `IBI_Channel` char(2) default NULL ,
	 `IBI_Note` varchar(512) default NULL ,
	 `IBI_Date` char(8) default NULL ,
	 `IBI_Time` char(8) default NULL ,
	PRIMARY KEY  (`IBI_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ts_InterfaceBasicInfo` */


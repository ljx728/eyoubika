USE `eyoubika`;
/*Table structure for table `tu_OpenAccountCorp` */
DROP TABLE IF EXISTS `tu_OpenAccountCorp`;
CREATE TABLE `tu_OpenAccountCorp` (
	 `OAC_OpenNo` char(16) NOT NULL ,
	 `OAC_UserId` int(10) default NULL ,
	 `OAC_ClientName` varchar(128) default NULL ,
	 `OAC_IdentifyType` char(2) default NULL ,
	 `OAC_IdentifyNumber` varchar(32) default NULL ,
	 `OAC_UserName` varchar(32) default NULL ,
	 `OAC_Pnumber` varchar(32) default NULL ,
	 `OAC_Email` varchar(32) default NULL ,
	 `OAC_Province` varchar(6) default NULL ,
	 `OAC_City` varchar(32) default NULL ,
	 `OAC_Street` varchar(128) default NULL ,
	 `OAC_BankId` varchar(128) default NULL ,
	 `OAC_BankPlace` varchar(128) default NULL ,
	 `OAC_BankAccount` varchar(256) default NULL ,
	 `OAC_Status` char(2) NOT NULL ,
	 `OAC_ExId` varchar(128) default NULL ,
	 `OAC_EmerName` varchar(64) default NULL ,
	 `OAC_EmerPnumber` varchar(32) default NULL ,
	 `OAC_ApplyDate` char(8) NOT NULL ,
	 `OAC_ApplyTime` varchar(16) NOT NULL ,
	 `OAC_OperDate` char(8) default NULL ,
	 `OAC_OperTime` varchar(16) default NULL ,
	 `OAC_OperName` varchar(64) default NULL ,
	 `OAC_Channel` char(2) default NULL ,
	PRIMARY KEY  (`OAC_OpenNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tu_OpenAccountCorp` */


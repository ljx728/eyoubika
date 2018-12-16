USE `eyoubika`;
/*Table structure for table `tu_OpenAccountPers` */
DROP TABLE IF EXISTS `tu_OpenAccountPers`;
CREATE TABLE `tu_OpenAccountPers` (
	 `OAP_OpenNo` char(16) NOT NULL ,
	 `OAP_UserId` int(10) default NULL ,
	 `OAP_ClientName` varchar(64) default NULL ,
	 `OAP_IdentifyType` char(2) default NULL ,
	 `OAP_IdentifyNumber` varchar(32) default NULL ,
	 `OAP_Pnumber` varchar(32) NOT NULL ,
	 `OAP_Email` varchar(32) default NULL ,
	 `OAP_Province` varchar(6) default NULL ,
	 `OAP_City` varchar(32) default NULL ,
	 `OAP_Street` varchar(128) default NULL ,
	 `OAP_Status` char(2) NOT NULL ,
	 `OAP_ExId` varchar(128) default NULL ,
	 `OAP_BankId` varchar(128) default NULL ,
	 `OAP_BankPlace` varchar(128) default NULL ,
	 `OAP_BankAccount` varchar(256) default NULL ,
	 `OAP_EmerName` varchar(64) default NULL ,
	 `OAP_EmerPnumber` varchar(32) default NULL ,
	 `OAP_ApplyDate` char(8) NOT NULL ,
	 `OAP_ApplyTime` varchar(16) NOT NULL ,
	 `OAP_OperDate` char(8) default NULL ,
	 `OAP_OperTime` varchar(16) default NULL ,
	 `OAP_OperName` varchar(64) default NULL ,
	 `OAP_Channel` char(2) default NULL ,
	PRIMARY KEY  (`OAP_OpenNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tu_OpenAccountPers` */


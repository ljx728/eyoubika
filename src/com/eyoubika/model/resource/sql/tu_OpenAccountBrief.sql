USE `eyoubika`;
/*Table structure for table `tu_OpenAccountBrief` */
DROP TABLE IF EXISTS `tu_OpenAccountBrief`;
CREATE TABLE `tu_OpenAccountBrief` (
	 `OAB_OpenNo` char(1) NOT NULL ,
	 `OAB_UserId` int(10) default NULL ,
	 `OAB_ClientName` varchar(128) default NULL ,
	 `OAB_Pnumber` varchar(32) default NULL ,
	 `OAB_ApplyDate` char(8) NOT NULL ,
	 `OAB_OpenType` varchar(2) NOT NULL ,
	 `OAB_Status` varchar(2) NOT NULL ,
	PRIMARY KEY  (`OAB_OpenNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tu_OpenAccountBrief` */


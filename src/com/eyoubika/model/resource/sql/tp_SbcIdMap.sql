USE `eyoubika`;
/*Table structure for table `tp_SbcIdMap` */
DROP TABLE IF EXISTS `tp_SbcIdMap`;
CREATE TABLE `tp_SbcIdMap` (
	 `SIM_MapId` int NOT NULL auto_increment,
	 `SIM_SbcId` int NOT NULL ,
	 `SIM_EphId` varchar(16) NOT NULL ,
	 `SIM_EphCode` varchar(16) NOT NULL ,
	 `SIM_Status` char(2) NOT NULL ,
	PRIMARY KEY  (`SIM_MapId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tp_SbcIdMap` */


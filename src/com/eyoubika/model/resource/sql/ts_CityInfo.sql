USE `eyoubika`;
/*Table structure for table `ts_CityInfo` */
DROP TABLE IF EXISTS `ts_CityInfo`;
CREATE TABLE `ts_CityInfo` (
	 `CI_CityId` varchar(6) NOT NULL ,
	 `CI_CityName` varchar(32) NOT NULL ,
	 `CI_ProId` varchar(6) NOT NULL ,
	PRIMARY KEY  (`CI_CityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ts_CityInfo` */



USE `eyoubika`;
/*Table structure for table `tu_FavoriteSbcList` */
DROP TABLE IF EXISTS `tu_FavoriteSbcList`;
CREATE TABLE `tu_FavoriteSbcList` (
	 `FSL_UserId` int NOT NULL ,
	 `FSL_SbcId` int NOT NULL ,
	 `FSL_Date` char(8) NOT NULL ,
	 `FSL_Time` varchar(12) NOT NULL ,
PRIMARY KEY  (`FSL_UserId`, `FSL_SbcId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tu_FavoriteSbcList` */

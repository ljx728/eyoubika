USE `eyoubika`;
/*Table structure for table `tu_FavoriteExList` */
DROP TABLE IF EXISTS `tu_FavoriteExList`;
CREATE TABLE `tu_FavoriteExList` (
	 `FEL_UserId` int NOT NULL ,
	 `FEL_ExId` char(6) NOT NULL ,
	 `FEL_Date` char(8) NOT NULL ,
	 `FEL_Time` varchar(12) NOT NULL ,
	PRIMARY KEY  (`FEL_UserId`, `FEL_ExId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tu_FavoriteExList` */


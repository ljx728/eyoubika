USE `eyoubika`;
/*Table structure for table `tu_OpenAccountDetail` */
DROP TABLE IF EXISTS `tu_OpenAccountDetail`;
CREATE TABLE `tu_OpenAccountDetail` (
	 `OAD_OpenNo` char(16) NOT NULL ,
	 `OAD_BatchNo` int default NULL ,
	 `OAD_IdentifyFrontPic` MediumBlob default NULL ,
	 `OAD_IdentifyBackPic` MediumBlob default NULL ,
	 `OAD_BankCardFrontPic` MediumBlob default NULL ,
	 `OAD_BankCardBackPic` MediumBlob default NULL ,
	 `OAD_UploadDate` char(8) default NULL ,
	 `OAD_Status` char(2) default NULL ,
	PRIMARY KEY  (`OAD_OpenNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tu_OpenAccountDetail` */


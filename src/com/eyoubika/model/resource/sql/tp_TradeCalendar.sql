USE `eyoubika`;
/*Table structure for table `tp_TradeCalendar` */
DROP TABLE IF EXISTS `tp_TradeCalendar`;
CREATE TABLE `tp_TradeCalendar` (
	 `TC_Date` char(8) NOT NULL ,
	 `TC_Week` char(2) default NULL ,
	 `TC_Month` char(2) default NULL ,
	 `TC_Type` char(2) default NULL ,
	 `TC_Status` char(2) default NULL ,
	PRIMARY KEY  (`TC_Date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `tp_TradeCalendar` */


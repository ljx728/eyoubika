USE `eyoubika`;
/*Table structure for table `ts_BadWordInfo` */
DROP TABLE IF EXISTS `ts_BadWordInfo`;
CREATE TABLE `ts_BadWordInfo` (
	 `BWI_WordId` int NOT NULL auto_increment,
	 `BWI_Word` varchar(128) NOT NULL ,
	 `BWI_Type` char(2) NOT NULL ,
	 `BWI_Date` char(8) NOT NULL ,
	PRIMARY KEY  (`BWI_WordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Data for the table `ts_BadWordInfo` */
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('admin', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('manager', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('root', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('eyoubika', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('习近平', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('胡锦涛', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('江泽民', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('邓小平', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('毛泽东', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('孙中山', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('蒋介石', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('傻逼', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('奥巴马', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('妈逼', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('傻×', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('贱人', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('骚货', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('烂货', '01', '20150803');
insert into ts_BadWordInfo(BWI_Word, BWI_Type, BWI_Date) values ('操你妈', '01', '20150803');



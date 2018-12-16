explain
select max(SA_WebTime) from eyoubika.ta_SubscribeArticle where SA_ExId= '007000'

USE `eyoubika`;
update tp_SbcBasicInfo set SBI_Volume = '384432' where SBI_ExId = '007000' and SBI_SbcName='93主席型张';
update tp_SbcBasicInfo set SBI_Volume = '264984' where SBI_ExId = '007000' and SBI_SbcName='岁岁平安套票';
update tp_SbcBasicInfo set SBI_Volume = '2303' where SBI_ExId = '007000' and SBI_SbcName='歼10银币';
update tp_SbcBasicInfo set SBI_Volume = '384432' where SBI_ExId = '007000' and SBI_SbcName='93主席型张';
update tp_SbcBasicInfo set SBI_Volume = '384432' where SBI_ExId = '007000' and SBI_SbcName='93主席型张';


USE `eyoubika`; 
delete from tp_Quotation_001000 where SQH_Type='07';
delete from tp_Quotation_001000 where SQH_Type='08';
delete from tp_Quotation_002000 where SQH_Type='07';
delete from tp_Quotation_002000 where SQH_Type='08';
delete from tp_Quotation_003000 where SQH_Type='07';
delete from tp_Quotation_003000 where SQH_Type='08';
delete from tp_Quotation_004000 where SQH_Type='07';
delete from tp_Quotation_004000 where SQH_Type='08';
delete from tp_Quotation_005000 where SQH_Type='07';
delete from tp_Quotation_005000 where SQH_Type='08';
delete from tp_Quotation_006000 where SQH_Type='07';
delete from tp_Quotation_006000 where SQH_Type='08';
delete from tp_Quotation_007000 where SQH_Type='07';
delete from tp_Quotation_007000 where SQH_Type='08';
delete from tp_Quotation_008000 where SQH_Type='07';
delete from tp_Quotation_008000 where SQH_Type='08';
delete from tp_Quotation_009000 where SQH_Type='07';
delete from tp_Quotation_009000 where SQH_Type='08';
delete from tp_Quotation_010000 where SQH_Type='07';
delete from tp_Quotation_010000 where SQH_Type='08';


SELECT OAP_OpenNo, OAP_ClientName, OAP_IdentifyNumber, OAP_Pnumber, OAP_Email, OAP_Province, OAP_City, OAP_Street, OAP_Status, OAP_ExId, OAP_BankId, OAP_ApplyDate, OAP_ApplyTime, 
case OAP_Channel 
when '01' then '苹果'
when '02' then '安卓'
when '04' then '网站'
when '03' then '微信'
end as channel
FROM eyoubika.tu_OpenAccountPers;
use eyoubika;
alter table tp_InvestArticle add IA_Source varchar(512) default null;


insert into  eyoubika.ts_SystemInfo(SI_Name, SI_Value, SI_Type, SI_Date) values('androidVersion', '0.0.2', null, '20160119');

#!/bin/bash
#--------------------------------------------
# 每日凌晨1点执行批量程序：
# 功能： 1. 初始化内存数据库，行情数据库。
#       2. 抓取文章。
# 特色：全自动打包，不需要输入任何参数
#--------------------------------------------
day=`date +%Y%m%d`
echo "Hello today is ${day}!"

webRoot="http://123.57.215.20:8081/"
#webRoot="http://localhost:8080/eyoubika/"
logDir="/var/lib/tomcat7/webapps/eyoubika/resource/system/log/"
#logDir="/Users/lijiaxuan/ljx728/log/"

fetchSubscribeArticleUrl="${webRoot}batch/fetchSubscribeArticle.action"     #抓取新品申购文章
fetchDepositArticleUrl="${webRoot}batch/fetchDepositArticle.action"         #抓取新品托管文章
fetchNoticeArticleUrl="${webRoot}batch/fetchNoticeArticle.action"           #抓取通知类文章（行业新闻等等）

batchLogFile="${logDir}articleBatch_${day}.log"


# 1>. 抓取新品申购文章
echo "1>. 抓取新品申购文章 ${fetchSubscribeArticleUrl}"
curl ${fetchSubscribeArticleUrl} >> ${batchLogFile} &
sleep 10s
# 2>. 抓取新品托管文章
echo "2>. 抓取新品托管文章 ${fetchDepositArticleUrl}"
curl ${fetchDepositArticleUrl} >> ${batchLogFile} &
sleep 10s
# 3>. 抓取通知类文章
echo "3>. 抓取通知类文章 ${fetchNoticeArticleUrl}"
curl ${fetchNoticeArticleUrl} >> ${batchLogFile} &
sleep 10s
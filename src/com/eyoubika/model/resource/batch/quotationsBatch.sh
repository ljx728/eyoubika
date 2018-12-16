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

calSbcKQuotationUrl="${webRoot}batch/calSbcKQuotation.action" #录入K线图
cleanRedisSbcUrl="${webRoot}batch/cleanRedisSbc.action"         #清除内存基本数据库
cleanRedisTChartUrl="${webRoot}batch/cleanRedisTChart.action"   #清除内存基本数据库
initMysqlSbcBasicUrl="${webRoot}batch/initSbcs.action"          #初始化品种mysql数据库
initRedisSbcBasicUrl="${webRoot}batch/initSbcRedis.action"      #初始化品种redis数据库
initSbcHistoryUrl="${webRoot}batch/initSbcHistory.action"       #初始化品种日周月数据
initThreeDayPriceUrl="${webRoot}batch/initThreeDayPrice.action" #初始化三日前品种收盘价
fetchSbcVolumeUrl="${webRoot}batch/fetchSbcVolume.action"        #抓取品种库存
initSbcVolumeUrl="${webRoot}batch/initSbcVolume.action"        #初始化品种库存

fetchQuotationsUrl="${webRoot}batch/fetchQuotations.action"      #抓取行情

inputMarketValueUrl="${webRoot}batch/inputMarketValue.action"    #生成交易所的市值


inputTurnoverRateUrl="${webRoot}batch/inputTurnoverRate.action"  #生成品种当日换手率
inputVolatilityUrl="${webRoot}batch/inputVolatility.action"      #生成品种当日波动
inputFiveMinRiseUrl="${webRoot}batch/inputFiveMinRise.action"    #生成品种五分钟涨幅
inputThreeDayRiseUrl="${webRoot}batch/inputThreeDayRise.action"  #生成品种三日涨幅
batchLogFile="${logDir}quotationsBatch_${day}.log"


# 1>. 录入K线图到数据库
echo "1>. 录入K线图到数据库 ${calSbcKQuotationUrl}"
curl ${calSbcKQuotationUrl} >> ${batchLogFile} &
sleep 20s
# 2>. 清除内存基础数据库
echo "2>. 清除内存基础数据库 ${cleanRedisSbcUrl}"
curl ${cleanRedisSbcUrl} >> ${batchLogFile} &
sleep 5s
# 3>. 抓取新的品种到数据库
echo "3>. 抓取新的品种到数据库"
curl ${initMysqlSbcBasicUrl} >> ${batchLogFile} &
sleep 25s
# 4>. 初始化品种redis数据库
echo "4>. 初始化品种redis数据库"
curl ${initRedisSbcBasicUrl} >> ${batchLogFile} &
sleep 5s
# 5>. 初始化品种日周月数据
echo "5>. 初始化品种日周月数据 ${initSbcHistoryUrl}"
curl ${initSbcHistoryUrl} >> ${batchLogFile} &
sleep 15s
# 6>. 初始化三日前品种收盘价
echo "6>. 初始化三日前品种收盘价 ${initThreeDayPriceUrl}"
curl ${initThreeDayPriceUrl} >> ${batchLogFile} &
sleep 5s
# 7>. 抓取品种库存
echo "7>. 抓取品种库存 ${fetchSbcVolumeUrl} "
curl ${fetchSbcVolumeUrl} >> ${batchLogFile} &
sleep 25s
# 8>. 初始化品种库存
echo "8>. 初始化品种库存 ${initSbcVolumeUrl}"
curl ${initSbcVolumeUrl} >> ${batchLogFile} &
sleep 5s
# 9>. 清除内存分时图数据库
echo "9>. 清除内存分时图数据库"
curl ${cleanRedisTChartUrl} >> ${batchLogFile} &
sleep 5s

# 10>. 抓取行情
echo "10>. 抓取行情"
curl ${fetchQuotationsUrl} >> ${batchLogFile} &
sleep 5s
# 11>. 生成交易所的市值
echo "11>. 生成交易所的市值"
curl ${inputMarketValueUrl} >> ${batchLogFile} &
sleep 5s
# 12>. 生成品种当日换手率
echo "12>. 生成品种当日换手率"
curl ${inputTurnoverRateUrl} >> ${batchLogFile} &
sleep 2s
# 13>. 生成品种当日波动
echo "13>. 生成品种当日波动"
curl ${inputVolatilityUrl} >> ${batchLogFile} &
sleep 2s
# 14>. 生成品种五分钟涨幅
echo "11>. 生成品种五分钟涨幅"
curl ${inputFiveMinRiseUrl} >> ${batchLogFile} &
sleep 2s
# 15>. 生成品种三日涨幅
echo "12>. 生成品种三日涨幅"
curl ${inputThreeDayRiseUrl} >> ${batchLogFile} &
sleep 2s

















#--------------------------------------------
# http://localhost:8080/eyoubika/batch/inputSbcQuotation.action
# http://localhost:8080/eyoubika/batch/initSbcs.action
# http://localhost:8080/eyoubika/batch/initSbcRedis.action
# http://localhost:8080/eyoubika/batch/cleanRedis.action
#--------------------------------------------

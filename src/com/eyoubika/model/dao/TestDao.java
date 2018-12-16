package com.eyoubika.model.dao;
import java.sql.SQLException;
import java.util.List;
import com.eyoubika.common.BaseDao;
import com.eyoubika.common.YbkException;
import com.eyoubika.model.domain.TestDomain;
public class TestDao extends BaseDao {
	String nameSpace = "ta_TestApp";
	TestDomain testDomain;	//<<attrNote>>
	public TestDomain getTestDomain(){
		return testDomain;
	}
	public void setTestDomain(TestDomain testDomain){
		this.testDomain = testDomain;
	}
	public int addTest(TestDomain testDomain){
		int insertId = 0;
		Object ob = new Object();
		try {
			ob =  sqlMapClient.insert(nameSpace +".insertTest", testDomain);
		} catch (SQLException e) {
			ob = null;
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		insertId = Integer.parseInt(ob.toString());
		ob = null;
		return insertId;
	}
	public int deleteTest(int testId){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteTest", testId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public int deleteAll(){
		try {
			return (int)sqlMapClient.delete(nameSpace +".deleteAll");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public void modifyTest(TestDomain testDomain){
		try {
			sqlMapClient.update(nameSpace +".updateTest", testDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public TestDomain findTest(int testId){
		try {
			return (TestDomain) sqlMapClient.queryForObject(nameSpace +".selectTest", testId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public TestDomain findTestByDomain(TestDomain testDomain){
		try {
			return (TestDomain) sqlMapClient.queryForObject(nameSpace +".selectTestByDomain", testDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public List<TestDomain> queryTest(TestDomain testDomain){
		try {
			return (List<TestDomain>) sqlMapClient.queryForList(nameSpace +".selectTestList", testDomain);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
	}
	public boolean isExistByDomain(TestDomain testDomain){
		boolean ret = true;
		Object ob = new Object();
		try {
			ob = sqlMapClient.queryForObject(nameSpace +".isExistByDomain", testDomain);
			ret = (ob==null) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new YbkException(YbkException.CODE000020, YbkException.DESC000020);
		}
		return ret;
	}
}

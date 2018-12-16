package com.eyoubika.model.service.impl;
import java.util.Map;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.BaseService;
import com.eyoubika.common.CommonVariables;
import com.eyoubika.common.YbkException;
import com.eyoubika.model.application.AutoCoderAL;
import com.eyoubika.model.service.IAutoCoderService;
import com.eyoubika.model.web.VO.AutoCoderVO;
public class AutoCoderServiceImpl extends BaseService implements IAutoCoderService{
	AutoCoderAL autoCoderAL;
	
	public AutoCoderAL getAutoCoderAL() {
		return autoCoderAL;
	}

	public void setAutoCoderAL(AutoCoderAL autoCoderAL) {
		this.autoCoderAL = autoCoderAL;
	}

	public Map<String, Object> autoCode(AutoCoderVO autoCoderVO){
		autoCoderAL.autoCode(autoCoderVO.getModule(), autoCoderVO.getModel());
		return this.buildRetData(CommonVariables.TRUE_SHORT_FLAG, CommonVariables.TRUE_WROD_FLAG);
	}
}
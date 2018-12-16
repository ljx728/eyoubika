package com.eyoubika.model.web.action;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.eyoubika.common.BaseAction;
import com.eyoubika.common.YbkException;
import com.eyoubika.model.service.IAutoCoderService;
import com.eyoubika.model.web.VO.AutoCoderVO;
import com.eyoubika.util.ConverterUtil;
public class AutoCoderAction extends BaseAction{
	IAutoCoderService  autoCoderService;
	
	public IAutoCoderService getAutoCoderService() {
		return autoCoderService;
	}

	public void setAutoCoderService(IAutoCoderService autoCoderService) {
		this.autoCoderService = autoCoderService;
	}

	public String autoCode(){
		AutoCoderVO autoCoderVO = new AutoCoderVO();
		HttpServletRequest request = ServletActionContext.getRequest();
		ConverterUtil.RequestToVO(request, autoCoderVO);
		//> 3. do register
		this.jsonData = autoCoderService.autoCode(autoCoderVO);
		//> 4. return json
		autoCoderVO = null;
		return SUCCESS;
	}
}
package com.eyoubika.model.service;

import java.util.Map;

import com.eyoubika.model.web.VO.AutoCoderVO;

public interface IAutoCoderService{
	public Map<String, Object> autoCode(AutoCoderVO autoCoderVO);	
}
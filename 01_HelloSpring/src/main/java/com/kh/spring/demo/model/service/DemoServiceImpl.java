package com.kh.spring.demo.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.demo.model.dao.DemoDAO;
import com.kh.spring.demo.model.vo.Dev;

@Service
public class DemoServiceImpl implements DemoService {
	@Autowired
	private DemoDAO demoDAO;

	@Override
	public int insertDev(Dev dev) {
		return demoDAO.insertDev(dev);
	}

	@Override
	public List<Map<String, String>> selectDevList() {
		return demoDAO.selectDevList();
	}


}

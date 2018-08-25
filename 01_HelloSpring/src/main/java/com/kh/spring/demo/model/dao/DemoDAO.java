package com.kh.spring.demo.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.spring.demo.model.vo.Dev;

public interface DemoDAO {

	int insertDev(Dev dev);

	List<Map<String, String>> selectDevList();

}

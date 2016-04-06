package com.wex.poc.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface Worker {

	XSSFWorkbook work(String fileName);
}

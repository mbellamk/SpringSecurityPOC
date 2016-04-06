package com.wex.poc.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.wex.poc.form.VPayable;
import com.wex.poc.model.RandomTable;

public interface LandingPageService {

	public List<VPayable> getVPayables(String username);

	public List<RandomTable> testHibernateSearch(String searchText);

	public void indexValues();

	public List<RandomTable> testHibernate(String searchText);

	public XSSFWorkbook getExcel(String fileName);

	public XSSFWorkbook getUploadedExcel(MultipartFile file);

	/*
	 * public Map<String, Object> getRandomRecords(Integer firstResult, Integer
	 * maxResults);
	 */
}

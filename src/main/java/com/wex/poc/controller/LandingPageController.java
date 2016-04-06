package com.wex.poc.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wex.poc.form.VPayable;
import com.wex.poc.model.RandomTable;
import com.wex.poc.service.LandingPageService;
import com.wex.poc.service.Worker;

@Controller
public class LandingPageController {

	@Autowired
	private LandingPageService landingPageService;

	@Autowired
	private Worker worker;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		List<String> permissions = new ArrayList<String>();
		for (GrantedAuthority a : SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities()) {
			permissions.add(a.getAuthority());
		}
		model.put("permissions", permissions);
		System.out.println("Final Permissions:" + permissions.toString());
		return "home";
	}

	@ResponseBody
	@RequestMapping(value = "/vpayables", method = RequestMethod.GET)
	public List<VPayable> getVPayables() {
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		return landingPageService.getVPayables(username);
	}

	@ResponseBody
	@RequestMapping(value = "/permissions", method = RequestMethod.GET)
	public List<String> getPermissions() {
		List<String> authorityList = new ArrayList<String>();
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority ga : authorities) {
			authorityList.add(ga.getAuthority());
		}
		System.out.println("Second:" + authorityList.toString());
		return authorityList;
	}

	@RequestMapping(value = "/getRandom", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> list(
			@RequestParam(required = false) Integer firstResult,
			@RequestParam(required = false) Integer maxResults) {
		// return landingPageService.getRandomRecords(firstResult, maxResults);
		return null;
	}

	@RequestMapping(value = "/testHibernateSearch/{searchText}", method = RequestMethod.GET)
	@ResponseBody
	public List<RandomTable> testHibernateSearch(@PathVariable String searchText) {
		return landingPageService.testHibernateSearch(searchText);
	}

	@RequestMapping(value = "/testHibernate/{searchText}", method = RequestMethod.GET)
	@ResponseBody
	public List<RandomTable> testHibernate(@PathVariable String searchText) {
		return landingPageService.testHibernate(searchText);
	}

	@RequestMapping(value = "/indexValues", method = RequestMethod.GET)
	@ResponseBody
	public String indexValues() {
		landingPageService.indexValues();
		return "done";
	}

	@RequestMapping(value = "/getUploadFile", method = RequestMethod.GET)
	public String getUploadFile() {

		return "uploadFile";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(
			@ModelAttribute("uploadFileData") MultipartFile uploadFileData,
			@ModelAttribute("name") String name, HttpServletResponse response) {
		if (!uploadFileData.isEmpty()) {
			try {
				byte[] bytes = uploadFileData.getBytes();

				XSSFWorkbook workbook = landingPageService
						.getUploadedExcel(uploadFileData);
				ServletOutputStream out;

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "wexFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);

				ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
				workbook.write(outByteStream);
				byte[] outArray = outByteStream.toByteArray();
				String fileOut = name + ".xlsx";

				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setContentLength(outArray.length);
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + fileOut + "\"");

				OutputStream outStream = response.getOutputStream();
				outStream.write(outArray);

				outStream.flush();
				outStream.close();
				/*
				 * FileOutputStream fos = new FileOutputStream(serverFile);
				 * workbook.write(fos); out = response.getOutputStream();
				 * workbook.write(out); out.flush(); out.close();
				 */

				/*
				 * BufferedOutputStream stream = new BufferedOutputStream( fos);
				 * 
				 * stream.write(bytes); stream.close();
				 */
				System.out.println("Server File Location="
						+ serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				e.printStackTrace();
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}

	@RequestMapping(value = "/uploadTest", method = RequestMethod.POST)
	@ResponseBody
	public String uploadTest() {
		return "uploadFile";
	}

	@RequestMapping(value = "/getExcel", method = RequestMethod.GET)
	public HttpServletResponse getExcel(Model model,
			HttpServletRequest request, HttpServletResponse response) {
		// landingPageService.indexValues();
		exportExceltoResponse(
				landingPageService
						.getExcel("C:\\Users\\MRaghavendra\\Documents\\vpayables.xlsx"),
				response, "vpayables_modified.xlsx");
		setContentType("vpayables_modified", response);
		return response;
	}

	@RequestMapping(value = "/getAsyncExcel", method = RequestMethod.GET)
	@ResponseBody
	public String getAsyncExcel(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// landingPageService.indexValues();

		worker.work("C:\\Users\\MRaghavendra\\Documents\\vpayables.xlsx");

		return "Success";
	}

	public void setContentType(String excelName, HttpServletResponse response) {
		if (excelName != null && excelName.trim().contains(" ")) {
			excelName = excelName.trim().replaceAll(" ", "_");
		}

		response.setContentType("application/octet-stream");
		// response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ excelName + ".xlsx");
	}

	public void exportExceltoResponse(XSSFWorkbook workbook,
			HttpServletResponse response, String reportName) {
		ServletOutputStream out;
		try {
			File file = new File("/mybook.xlsx");
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

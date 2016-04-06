package com.wex.poc.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.wex.poc.service.Worker;
import com.wex.poc.xml.ObjectFactory;
import com.wex.poc.xml.VpayableXML;

@Component
public class AsyncOpenWorkBook implements Worker {

	private VPayableDemo vpayableDemo = new VPayableDemo();

	@Async
	public XSSFWorkbook work(String fileName) {

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Map<Integer, StringBuilder> error = new HashMap<Integer, StringBuilder>();
		File file = new File(fileName);
		XSSFWorkbook workbook = null;
		XSSFSheet sheet;
		Iterator<Row> rowIerator;
		// FileInputStream fIP;
		try {
			FileInputStream fIP = new FileInputStream(file);
			// Get the workbook instance for XLSX file
			workbook = new XSSFWorkbook(fIP);
			sheet = workbook.getSheetAt(0);
			rowIerator = sheet.iterator();
			int count = 0;
			while (rowIerator.hasNext()) {
				Row nextRow;
				Iterator<Cell> cellIterator;
				System.out.println();
				if (count < 1) {
					rowIerator.next();
					count++;
				} else {
					nextRow = rowIerator.next();
					cellIterator = nextRow.cellIterator();
					ObjectFactory obj = new ObjectFactory();
					VpayableXML vpayable = obj.createVpayableXML();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();

						System.out.println(cell.getColumnIndex());

						switch (cell.getColumnIndex()) {
						case 1:
							vpayable.setAmount(cell.getNumericCellValue());
							break;
						case 2:
							vpayable.setType(cell.getStringCellValue());
							break;
						case 3:
							vpayable.setDescription(cell.getStringCellValue());
							break;

						}

					}

					vpayableDemo.validateAgainstXSD(vpayable,
							nextRow.getRowNum(), error);

				}
			}

			if (error != null && !error.isEmpty()) {
				for (Integer i : error.keySet()) {
					Row row = sheet.getRow(i);
					XSSFCellStyle style = workbook.createCellStyle();
					style.setBorderTop((short) 6); // double lines border
					style.setBorderBottom((short) 1); // single line border
					XSSFFont font = workbook.createFont();
					font.setFontHeightInPoints((short) 15);
					font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
					XSSFColor color = new XSSFColor(Color.RED);
					font.setColor(color);
					style.setFont(font);
					for (int j = 0; j < row.getLastCellNum(); j++) {// For each
																	// cell in
																	// the row
						row.getCell(j).setCellStyle(style);// Set the style
					}
				}
			}
			System.out.println(error);
			if (file.isFile() && file.exists()) {
				System.out
						.println("createworkbook.xlsx file open successfully.");
			} else {
				System.out.println("Error to open openworkbook.xlsx file.");
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return workbook;

	}
}
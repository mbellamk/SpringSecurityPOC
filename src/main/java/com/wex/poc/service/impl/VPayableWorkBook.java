package com.wex.poc.service.impl;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.wex.poc.helper.VPayableHeaders;
import com.wex.poc.xml.ObjectFactory;
import com.wex.poc.xml.Vpayable;

@Component("vPayableWorkBook")
public class VPayableWorkBook {

	@Autowired
	private VPayableValidator vPayableValidator;

	public XSSFWorkbook vpayableWorkBookMultipart(MultipartFile file) {

		Map<Integer, StringBuilder> error = new HashMap<Integer, StringBuilder>();
		XSSFWorkbook workbook = null;
		XSSFSheet sheet;
		Iterator<Row> rowIerator;
		try {
			workbook = new XSSFWorkbook(file.getInputStream());
			sheet = workbook.getSheetAt(1);
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
					Vpayable vpayable = obj.createVpayable();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();

						System.out.println(cell.getColumnIndex());

						switch (cell.getColumnIndex()) {
						case VPayableHeaders.VPAYABLE_ORGANIZATION_ID:
							vpayable.setOrganizationId(cell
									.getNumericCellValue());
							break;
						case VPayableHeaders.VPAYABLE_PAYABLE_ID:
							vpayable.setPayableId(cell.getNumericCellValue());
							break;
						case VPayableHeaders.VPAYABLE_AMOUNT:
							vpayable.setAmount(cell.getNumericCellValue());
							break;
						case VPayableHeaders.VPAYABLE_DELIVERY_METHOD:
							vpayable.setDeliveryMethod(cell
									.getStringCellValue());
							break;
						case VPayableHeaders.VPAYABLE_NUMBER_OF_AUTHORIZATIONS_ALLOWED:
							vpayable.setNumberOfAuthorizationsAllowed(((Double) cell
									.getNumericCellValue()).intValue());
							break;
						case VPayableHeaders.VPAYABLE_AMOUNT_OF_AUTHORIZATIONS_ALLOWED:
							vpayable.setAmountOfAuthorizationsAllowed(((Double) cell
									.getNumericCellValue()).intValue());
							break;
						}

					}

					vPayableValidator.validateAgainstXSD(vpayable,
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
						Cell cell = row.getCell(j); // the row
						cell.setCellStyle(style);// Set the style

						Drawing drawing = cell.getSheet()
								.createDrawingPatriarch();
						CreationHelper factory = cell.getSheet().getWorkbook()
								.getCreationHelper();
						ClientAnchor anchor = factory.createClientAnchor();
						anchor.setCol1(cell.getColumnIndex());
						anchor.setCol2(cell.getColumnIndex() + 1);
						anchor.setRow1(cell.getRowIndex());
						anchor.setRow2(cell.getRowIndex() + 3);

						Comment comment = drawing.createCellComment(anchor);
						RichTextString str = factory
								.createRichTextString(error.get(i).toString());
						comment.setVisible(Boolean.FALSE);
						comment.setString(str);
					}
				}
			}
			System.out.println(error);

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return workbook;
	}

}
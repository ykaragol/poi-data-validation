package com.github.ykaragol.datavalidation;


import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

public class DataValidation {

	public static final String PATH = "./test-files/";

	@Test
	public void testDataValidation() {
		String fileName = "DataValidationTest.xlsx";

		InputStream is = null;
		boolean isValid = true;
		try {
			is = new FileInputStream(new File(PATH + fileName));

			XSSFWorkbook wb = openWorkbook(convertEncryptedXlsx2DecryptedXlsx(is));
			XSSFSheet sheet = wb.getSheetAt(0);

			visitSheet(sheet);

		} catch (FileNotFoundException e) {
			fail("FileNotFound");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSilently(is);
		}
		Assert.assertEquals("isValid ", true, isValid);
	}

	public static XSSFWorkbook openWorkbook(InputStream is) {
		Workbook wb;
		try {
			wb = WorkbookFactory.create(is);
			if (wb instanceof XSSFWorkbook) {
				return (XSSFWorkbook) wb;
			}
			throw new RuntimeException();
		} catch (InvalidFormatException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static InputStream convertEncryptedXlsx2DecryptedXlsx(InputStream is) throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem(is);
		EncryptionInfo info = new EncryptionInfo(fs);
		Decryptor d = Decryptor.getInstance(info);
		try {
			d.verifyPassword(Decryptor.DEFAULT_PASSWORD);
			InputStream dataStream = d.getDataStream(fs);
			return dataStream;
		} catch (GeneralSecurityException e) {
			throw new IOException(e);
		}
	}
	
	private void visitSheet(XSSFSheet sheet) {
		List<XSSFDataValidation> dataValidations = sheet.getDataValidations();
		for (XSSFDataValidation xssfDataValidation : dataValidations) {
			DataValidationConstraint validationConstraint = xssfDataValidation.getValidationConstraint();
			CellRangeAddressList regions = xssfDataValidation.getRegions();
			CellRangeAddress[] cellRangeAddresses = regions.getCellRangeAddresses();
			for (CellRangeAddress cellRangeAddress : cellRangeAddresses) {
				int firstRow = getMax(cellRangeAddress.getFirstRow(),sheet.getFirstRowNum());
				int lastRow = getMin(cellRangeAddress.getLastRow(), sheet.getLastRowNum());
				
				for(int i = firstRow; i<=lastRow; i++){
					XSSFRow row = sheet.getRow(i);
					if (row == null || row.getFirstCellNum() < 0) {
						continue;
					}
					
					int firstColumn = getMax(cellRangeAddress.getFirstColumn(),row.getFirstCellNum());
					int lastColumn = getMin(cellRangeAddress.getLastColumn(), row.getLastCellNum());
					
					for (int j = firstColumn; j <= lastColumn; j++) {
						XSSFCell cell = row.getCell(j);
						if (cell == null) {
							continue;
						}
						boolean inRange = cellRangeAddress.isInRange(cell.getRowIndex(), cell.getColumnIndex());
						if (inRange) {
							checkCell(validationConstraint, cell);
						}
					}
				}
			}
		}
	}

	private int getMax(int a, int b) {
		return a > b ? a : b;
	}

	private int getMin(int a, int b) {
		return a < b ? a : b;
	}

	private void closeSilently(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				/* silent */
			}
		}
	}

	private void checkCell(DataValidationConstraint validationConstraint, XSSFCell cell) {
		System.err.println(cell.getReference() + ": "  + cell.getRawValue());
		
		int validationType = validationConstraint.getValidationType();
		int operator = validationConstraint.getOperator();
		
		int cellType = cell.getCellType();
		if(cellType == Cell.CELL_TYPE_BLANK){
			return;
		}
//		//Cell.CELL_TYPE_BLANK
//		String dataFormatString = cell.getCellStyle().getDataFormatString();
//		short dataFormat = cell.getCellStyle().getDataFormat();
		
		switch (validationType) {
			case ValidationType.ANY:
				break;
	
			case ValidationType.DATE:
				break;
	
			case ValidationType.DECIMAL:
				{
					if(cellType != Cell.CELL_TYPE_NUMERIC){
						System.err.println("HATALI - Decimal deðil: " + cell.getReference() + " - "+ cell.getRawValue());
						break;
					}
					
					double numericCellValue = cell.getNumericCellValue();
					String formula1 = validationConstraint.getFormula1();
					String formula2 = validationConstraint.getFormula2();
					
					double r1 = Double.parseDouble(formula1);
					double r2 = Double.parseDouble(formula2);
					
					if(numericCellValue < r1 || numericCellValue > r2){
						System.err.println("HATALI Aralýk Dýþý :" + cell.getReference() + ": " + numericCellValue + " aralýk: "+ r1 +"-"+ r2);
					}
				}
				break;
	
			case ValidationType.FORMULA:
				System.err.println("HATALI - FORMULA: " + cell.getReference());
				break;
				
			case ValidationType.INTEGER:
				{
					if(cellType != Cell.CELL_TYPE_NUMERIC){
						System.err.println("HATALI - Decimal deðil: " + cell.getReference() + " - "+ cell.getRawValue());
						break;
					}
					
					double numericCellValue = cell.getNumericCellValue();
					String formula1 = validationConstraint.getFormula1();
					String formula2 = validationConstraint.getFormula2();
					
					double r1 = Double.parseDouble(formula1);
					double r2 = Double.parseDouble(formula2);
					
					if(numericCellValue < r1 || numericCellValue > r2){
						System.err.println("HATALI" + cell.getReference() + ": " + numericCellValue + " aralýk: "+ r1 +"-"+ r2);
					}
					double d = numericCellValue - (int)numericCellValue;
					if(d > 0){
						System.err.println("HATALI" + cell.getReference() + ": " + numericCellValue + " tam sayý deðil ");
					}
				}
				break;
	
			case ValidationType.LIST:
				break;
	
			case ValidationType.TEXT_LENGTH:
				break;
	
			case ValidationType.TIME:
				break;
	
			default:
				break;
		}
		
	}
}
package com.github.ykaragol.datavalidation;


import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class DataValidator {
	
	public void visitSheet(XSSFSheet sheet) {
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
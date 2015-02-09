package com.github.ykaragol.datavalidation.validator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.Operator;
import com.github.ykaragol.datavalidation.Validator;

public class DecimalValidator implements Validator {

	private Operator operator;

	public DecimalValidator(Operator operator) {
		this.operator = operator;
	}

	@Override
	public void validate(XSSFCell cell) {
		int cellType = cell.getCellType();
		if (cellType == Cell.CELL_TYPE_BLANK) {
			return;
		}

		double cellValue;
		if (cellType == Cell.CELL_TYPE_NUMERIC) {
			cellValue = cell.getNumericCellValue();
		} else if (cellType == Cell.CELL_TYPE_STRING) {
			 String stringValue = cell.getStringCellValue();
			 try{
				 cellValue = Double.parseDouble(stringValue);
				 //Warning 
			 }catch(NumberFormatException e){
				 //Error
				 return;
			 }
		} else {
			return; //Error
		}
		
		if (operator.check(cellValue)) {
			//True
		} else {
			//Error
		}
	}

}

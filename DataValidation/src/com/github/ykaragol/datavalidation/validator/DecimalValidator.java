package com.github.ykaragol.datavalidation.validator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.ValidationResult;
import com.github.ykaragol.datavalidation.core.Operator;
import com.github.ykaragol.datavalidation.core.ValidationResultImpl;
import com.github.ykaragol.datavalidation.core.Validator;

public class DecimalValidator implements Validator {

	private Operator operator;

	public DecimalValidator(Operator operator) {
		this.operator = operator;
	}

	@Override
	public ValidationResult validate(XSSFCell cell) {
		int cellType = cell.getCellType();
		if (cellType == Cell.CELL_TYPE_BLANK) {
			return null;
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
				 return new ValidationResultImpl(false, "Value type is not as expected!");
			 }
		} else {
			return new ValidationResultImpl(false, "Value type is not as expected!"); //Error
		}
		
		if (operator.check(cellValue)) {
			//True
			return new ValidationResultImpl(true, null);
		} else {
			//Error
			return new ValidationResultImpl(false, "Error!");
		}
	}

}

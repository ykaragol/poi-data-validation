package com.github.ykaragol.datavalidation.validator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.ValidationResult;
import com.github.ykaragol.datavalidation.core.Operator;
import com.github.ykaragol.datavalidation.core.ValidationResultImpl;
import com.github.ykaragol.datavalidation.core.Validator;

public class NumericValidator implements Validator {
	
	private Operator operator;

	public NumericValidator(Operator operator) {
		this.operator = operator;
	}

	@Override
	public ValidationResult validate(XSSFCell cell) {
		//result ??
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
				 return new ValidationResultImpl(cell.getSheet().getSheetName(), cell.getReference(), false, "Error!");
			 }
		} else {
			//Error
			return new ValidationResultImpl(cell.getSheet().getSheetName(), cell.getReference(), false, "Error!");
		}
		//TODO : refactor above code, they are duplicate with DecimalValidator!
		
		int intRepresentation = (int) cellValue;
		if(Double.compare(cellValue, intRepresentation)==0){
			//Continue...
		}else{
			//Error
			return new ValidationResultImpl(cell.getSheet().getSheetName(), cell.getReference(), false, "Error!");
		}
		
		if (operator.check(cellValue)) {
			//True
		} else {
			return new ValidationResultImpl(cell.getSheet().getSheetName(), cell.getReference(), false, "Error!");
			//Error
		}
		return null;
	}

}

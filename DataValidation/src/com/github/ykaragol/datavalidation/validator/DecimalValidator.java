package com.github.ykaragol.datavalidation.validator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidationConstraint.OperatorType;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.Operator;
import com.github.ykaragol.datavalidation.Validator;

public class DecimalValidator implements Validator {


	public DecimalValidator(Operator operator) {
		// TODO Auto-generated constructor stub
	}



	@Override
	public void validate(XSSFCell cell) {
		int cellType = cell.getCellType();
		if (cellType == Cell.CELL_TYPE_BLANK) {
			return;
		}
		
		if (cellType != Cell.CELL_TYPE_NUMERIC) {
			
		}
		double cellValue = cell.getNumericCellValue();
		
	}

}

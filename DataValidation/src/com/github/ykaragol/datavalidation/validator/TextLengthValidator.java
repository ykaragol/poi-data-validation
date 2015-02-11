package com.github.ykaragol.datavalidation.validator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.ValidationResult;
import com.github.ykaragol.datavalidation.core.Operator;
import com.github.ykaragol.datavalidation.core.Validator;

public class TextLengthValidator implements Validator {

	



	private Operator operator;



	public TextLengthValidator(Operator operator) {
		this.operator = operator;
	}



	@Override
	public ValidationResult validate(XSSFCell cell) {
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String stringCellValue = cell.getStringCellValue();
			int length;
			if(stringCellValue == null){
				length = 0;
			}else{
				length = stringCellValue.length();
			}
			operator.check((double)length);
		} else {
			// TODO how to handle this situation
		}
		return null;
	}

}

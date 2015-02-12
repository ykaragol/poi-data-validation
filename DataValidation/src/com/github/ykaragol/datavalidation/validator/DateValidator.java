package com.github.ykaragol.datavalidation.validator;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.ValidationResult;
import com.github.ykaragol.datavalidation.core.Operator;
import com.github.ykaragol.datavalidation.core.Validator;

public class DateValidator implements Validator {

	private NumericValidator numericValidator;
	
	public DateValidator(Operator operator) {
		numericValidator = new NumericValidator(operator);
	}

	@Override
	public ValidationResult validate(XSSFCell cell) {
		return (numericValidator.validate(cell));
	}

}

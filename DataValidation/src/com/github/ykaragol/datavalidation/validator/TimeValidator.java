package com.github.ykaragol.datavalidation.validator;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.ValidationResult;
import com.github.ykaragol.datavalidation.core.Operator;
import com.github.ykaragol.datavalidation.core.Validator;

public class TimeValidator implements Validator {

	private DecimalValidator decimalValidator;

	public TimeValidator(Operator operator) {
		decimalValidator = new DecimalValidator(operator);
	}

	@Override
	public ValidationResult validate(XSSFCell cell) {
		return decimalValidator.validate(cell);
	}

}

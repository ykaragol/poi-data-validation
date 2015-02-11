package com.github.ykaragol.datavalidation.validator;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.ValidationResult;
import com.github.ykaragol.datavalidation.core.Validator;

public class NullValidator implements Validator {

	@Override
	public ValidationResult validate(XSSFCell cell) {
		//Nothing to validate
		return null;
	}

}

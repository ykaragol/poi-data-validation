package com.github.ykaragol.datavalidation.validator;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.Validator;

public class AnyValidator implements Validator {

	@Override
	public void validate(XSSFCell cell) {
		//Nothing to validate
	}

}

package com.github.ykaragol.datavalidation.core;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.ValidationResult;

public interface Validator {
	ValidationResult validate(XSSFCell cell);
}

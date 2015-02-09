package com.github.ykaragol.datavalidation;

import org.apache.poi.xssf.usermodel.XSSFCell;

public interface Validator {
	void validate(XSSFCell cell);
}

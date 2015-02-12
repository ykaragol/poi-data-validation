package com.github.ykaragol.datavalidation.core;

import com.github.ykaragol.datavalidation.ValidationResult;

public class ValidationResultImpl implements ValidationResult {

	private boolean result;
	private String message;
	private String sheetName;
	private String cellName;

	
	public ValidationResultImpl(String sheetName, String cellName, boolean result, String message) {
		this.sheetName = sheetName;
		this.cellName = cellName;
		this.result = result;
		this.message = message;
	}

	@Override
	public boolean getResult() {
		return result;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getSheetName() {
		return sheetName;
	}

	@Override
	public String getCellName() {
		return cellName;
	}

}

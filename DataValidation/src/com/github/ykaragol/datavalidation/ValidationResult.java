package com.github.ykaragol.datavalidation;

public interface ValidationResult {
	String getSheetName();
	String getCellName();
	boolean getResult();
	String getMessage();
}

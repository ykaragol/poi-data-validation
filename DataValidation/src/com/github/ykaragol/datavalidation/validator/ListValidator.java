package com.github.ykaragol.datavalidation.validator;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.Validator;

public class ListValidator implements Validator {

	private int operatorType;
	private String formula1;
	private String formula2;
	

	public ListValidator(int operatorType, String formula1, String formula2) {
		this.operatorType = operatorType;
		this.formula1 = formula1;
		this.formula2 = formula2;
	}



	@Override
	public void validate(XSSFCell cell) {
		// TODO Auto-generated method stub

	}

}

package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.Operator;

public class NotEqualOperator implements Operator {

	private EqualOperator equalOperator;
	
	public NotEqualOperator(String formula1) {
		equalOperator = new EqualOperator(formula1);
	}

	@Override
	public boolean check(Double value) {
		return ! equalOperator.check(value);
	}

}

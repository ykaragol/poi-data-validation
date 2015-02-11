package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.core.Operator;


public class GreaterOrEqualOperator implements Operator {

	private GreaterThanOperator greaterThanOperator;
	private EqualOperator equalOperator;

	public GreaterOrEqualOperator(String formula1) {
		greaterThanOperator = new GreaterThanOperator(formula1);
		equalOperator = new EqualOperator(formula1);
	}

	@Override
	public boolean check(Double value) {
		return greaterThanOperator.check(value) || equalOperator.check(value);
	}

}

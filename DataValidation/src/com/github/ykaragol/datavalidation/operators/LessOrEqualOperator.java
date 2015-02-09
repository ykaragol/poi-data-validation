package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.Operator;

public class LessOrEqualOperator implements Operator {

	private LessThanOperator lessThanOperator;
	private EqualOperator equalOperator;
	
	public LessOrEqualOperator(String formula1) {
		lessThanOperator = new LessThanOperator(formula1);
		equalOperator = new EqualOperator(formula1);
	}

	@Override
	public boolean check(Double value) {
		return lessThanOperator.check(value) || equalOperator.check(value);
	}

}

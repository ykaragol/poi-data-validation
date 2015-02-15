package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.core.Operator;

public class NotEqualOperator implements Operator {

	private EqualOperator equalOperator;

	public NotEqualOperator(Double formula1) {
		equalOperator = new EqualOperator(formula1);
	}

	@Override
	public boolean check(Double value) {
		return !equalOperator.check(value);
	}

}
